package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import org.jetbrains.annotations.Nullable;


import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.MOD_ID;
import static io.github.GrassyDev.pvzmod.sound.PvZSounds.SILENCEVENET;

public class TangleKelpEntity extends PlantEntity implements GeoEntity {

    private String controllerName = "kelpcontroller";

	public boolean isFiring;
	private int animationTicksLeft;
	public boolean firstAttack;
	public boolean inAnimation;
	public boolean attackLock;
	private boolean stopAnimation;
	private int amphibiousRaycastDelay;
	public static final UUID MAX_RANGE_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);



	public Vec3d originalVec3d = this.getPos();

    public TangleKelpEntity(EntityType<? extends TangleKelpEntity> entityType, World world) {
        super(entityType, world);

		amphibiousRaycastDelay = 1;
		this.setNoGravity(true);
		this.noBiggie = true;
		this.targetNotObstacle = true;
		this.isBurst = true;
    }

	public TangleKelpEntity(World world, double x, double y, double z) {
		this(PvZEntity.TANGLE_KELP, world);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 113) {
			this.inAnimation = true;
		} else if (status == 112) {
			this.inAnimation = false;
		}
		if (status == 107) {
			for(int i = 0; i < 128; ++i) {
				double e = (double) MathHelper.nextBetween(randomGenerator, 5F, 20F);
				this.getWorld().addParticle(ParticleTypes.WATER_SPLASH, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 3F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						0, e, 0);
			}
		}
		if (status == 100) {
			for(int i = 0; i < 64; ++i) {
				double e = (double) MathHelper.nextBetween(randomGenerator, 5F, 20F);
				this.getWorld().addParticle(ParticleTypes.WATER_SPLASH, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 1F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						0, e, 0);
			}
		}
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.dryLand) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("tanglekelp.onground"));
		}
		else if (inAnimation && !stopAnimation) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("tanglekelp.attack"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("tanglekelp.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
    }


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}

		if (this.age > 1 && !this.getWorld().isClient()) {
			if (this.animationTicksLeft <= 0) {
				BlockPos blockPos2 = this.getBlockPos();
				if (!this.getWorld().isClient()) {
					if (!blockPos2.equals(blockPos)) {
						this.discard();
					}
				}
			}
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getVehicle() instanceof LilyPadEntity){
			this.getVehicle().discard();
		}
		LivingEntity target = this.getTarget();
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && target != null) {
			if (this.firstAttack && this.animationTicksLeft <= 0 && ((target.isInsideWaterOrBubbleColumn() || !(target instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.dontWater)) && !this.dryLand)) {
				this.animationTicksLeft = 65;
				if (!attackLock){
					this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH);
					getWorld().sendEntityStatus(this, (byte) 100);
				}
				this.firstAttack = false;
			}
		}
		this.targetZombies(originalVec3d, 2, true, false, true);
		if (age <= 5){
			this.originalVec3d = this.getPos();
		}
		if (this.animationTicksLeft > 0 && this.animationTicksLeft <= 35 && !this.attackLock && !onWaterTile) {
			Entity entity = this.getTarget();
			if (entity != null && !this.isInsideWaterOrBubbleColumn()){
				this.setPosition(entity.getX(), entity.getY(), entity.getZ());
			}
			else if (entity != null && this.isInsideWaterOrBubbleColumn()){
				this.setPosition(entity.getX(), entity.getY() - 0.25, entity.getZ());
			}
			if (this.hasVehicle()){
				this.dismountVehicle();
				this.setPosition(this.getX(), getY(), getZ());
			}
		}
		else {
			this.setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.animationTicksLeft > 0 && this.animationTicksLeft > 25) {
			Entity entity = this.getTarget();
			if (entity == null) {
				this.firstAttack = true;
				this.stopAnimation = true;
			}
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) && !this.hasVehicle())) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.TANGLEKELP_SEED_PACKET);
				}
				this.discard();
			}
		}

		if (this.isInsideWaterOrBubbleColumn()){
			kill();
		}
		if (animationTicksLeft > 0){
			this.setImmune(Immune.TRUE);
		}
		else {
			this.setImmune(Immune.FALSE);
		}

		if (--amphibiousRaycastDelay <= 0 && age > 5) {
			amphibiousRaycastDelay = 20;
			HitResult hitResult = amphibiousRaycast(1);
			if (hitResult.getType() == HitResult.Type.MISS && !this.hasVehicle()) {
				kill();
			}
			if (this.age > 1) {
				BlockPos blockPos2 = this.getBlockPos();
				BlockState blockState = this.getLandingBlockState();
				FluidState fluidState = getWorld().getFluidState(this.getBlockPos().add(0, -1, 0));
				if (!(fluidState.getFluid() == Fluids.WATER) && !onWaterTile) {
					this.dryLand = true;
					onWater = false;
					this.discard();
				} else {
					this.dryLand = false;
					onWater = true;
				}
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.TANGLEKELP_SEED_PACKET);
				}
				this.discard();
				}
			}
		}
	}

	public void mobTick() {
		super.mobTick();
		if (this.animationTicksLeft == 2) {
			this.discard();
		}
		LivingEntity livingEntity = this.getTarget();
		if (livingEntity != null && (livingEntity.isInsideWaterOrBubbleColumn()|| !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.dontWater))) {
			if (this.animationTicksLeft <= 43){
				livingEntity.setVelocity(Vec3d.ZERO);
			}
			if (this.animationTicksLeft == 22){
				getWorld().sendEntityStatus(this, (byte) 107);
				this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1.5F, 1.0F);
			}
			if (this.animationTicksLeft == 6) {
				this.attackLock = true;
				getWorld().sendEntityStatus(this, (byte) 107);
				this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1.5F, 1.0F);
				if (getTarget() != null) {
					this.firstAttack = true;
				}

				float damage = 9999f;
				boolean bl2 = livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
				if (bl2) {
					this.applyDamageEffects(this, livingEntity);
				}
				else {
					this.discard();
				}
			}
		}
		if (getTarget() == null){
			this.firstAttack = true;
		}
		if (this.animationTicksLeft > 0) {
			this.stopAnimation = false;
			--this.animationTicksLeft;
			this.getWorld().sendEntityStatus(this, (byte) 113);
		}
		else{
			this.getWorld().sendEntityStatus(this, (byte) 112);
		}
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (this.getTarget() == null || source.getAttacker() instanceof PlayerEntity || source.isTypeIn(DamageTypeTags.BYPASSES_COOLDOWN)) {
			super.applyDamage(source, amount);
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.TANGLEKELP_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static EntityAttributeModifier createRangeAttribute(double amount) {
		return new EntityAttributeModifier(
				MAX_RANGE_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static DefaultAttributeContainer.Builder createTangleKelpAttributes() {
        return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 4D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 0D);
    }

	protected boolean canClimb() {return false;}

	public boolean collides() {return true;}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.075F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {return PvZSounds.PLANTPLANTEDEVENT;}

	public boolean hurtByWater() {return false;}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {

	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/



	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}
}
