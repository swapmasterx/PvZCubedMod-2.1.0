package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smackadamia;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;
import static io.github.GrassyDev.pvzmod.PvZCubed.ZOMBIE_SIZE;

public class SmackadamiaEntity extends PlantEntity implements GeoEntity {


	private int amphibiousRaycastDelay;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private int attackTicksLeft;

	private String controllerName = "smackcontroller";

	private boolean isTargetFlying;

	public SmackadamiaEntity(EntityType<? extends SmackadamiaEntity> entityType, World world) {
		super(entityType, world);

		amphibiousRaycastDelay = 1;

		this.setNoGravity(true);
		this.isBurst = true;
	}

	public SmackadamiaEntity(World world, double x, double y, double z) {
		this(PvZEntity.SMACKADAMIA, world);
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
		if (status == 106) {
			this.attackTicksLeft = 20;
		}
		if (status == 108) {
			this.isTargetFlying = true;
		}
		else if (status == 109) {
			this.isTargetFlying = false;
		}
	}

	/**
	 * /~*~//~*GECKOLIB ANIMATION~//~*~//
	 **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}


	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		int i = this.attackTicksLeft;
		LivingEntity livingEntity = this.getTarget();
		if (i <= 0){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("smackadamia.idle"));
		}
		else if (this.isTargetFlying) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("smackadamia.smack2"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("smackadamia.smack"));
		}
		return PlayState.CONTINUE;
	}

	/** /~*~//~*AIT*~//~*~// **/

	protected void initGoals() {
	}

	public void smack(Entity target) {
		int i = this.attackTicksLeft;
		if ((target instanceof GeneralPvZombieEntity generalPvZombieEntity &&
				(generalPvZombieEntity.isFlying() || generalPvZombieEntity.isHovering() || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("big")|| ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("tall"))
				&& target.squaredDistanceTo(this) <= 25) ||
		this.squaredDistanceTo(target) <= 1.5625) {
			ZombiePropEntity passenger = null;
			for (Entity entity1 : target.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe && passenger == null) {
					passenger = zpe;
				}
			}
			Entity damaged = target;
			if (passenger != null && !(passenger instanceof ZombieShieldEntity)) {
				damaged = passenger;
			}
			if (i <= 0) {
				this.attackTicksLeft = 20;
				this.getWorld().sendEntityStatus(this, (byte) 106);
				boolean bl = damaged.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), getAttackDamage());
				if (bl) {
					this.applyDamageEffects(this, target);
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
					case "plastic" -> PvZSounds.PEAHITEVENT;
					case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				target.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				this.chomperAudioDelay = 3;
			}
		}
	}


	/**
	 * //~*~//~POSITION~//~*~//
	 **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/**
	 * //~*~//~TICKING~//~*~//
	 **/

	private int chomperAudioDelay = -1;

	public void tick() {
		super.tick();
		this.targetZombies(this.getPos(), 2, false, true, true);
		if (!(this.getTarget() instanceof GeneralPvZombieEntity generalPvZombieEntity &&
				(generalPvZombieEntity.isFlying() || generalPvZombieEntity.isHovering() || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("big")|| ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("tall")))){
			if (this.getTarget() != null && this.getTarget().squaredDistanceTo(this) > 1.5625){
				this.setTarget(null);
			}
		}
		if (this.getTarget() != null){
			this.smack(this.getTarget());
		}
		if (--this.chomperAudioDelay == 0){
			this.playSound(PvZSounds.PEASHOOTEVENT, 1.0F, 1.0F);
		}
		BlockPos blockPos = this.getBlockPos();
		if (--amphibiousRaycastDelay <= 0 && age > 1) {
			amphibiousRaycastDelay = 20;
			HitResult hitResult = amphibiousRaycast(0.25);
			if (hitResult.getType() == HitResult.Type.MISS && !this.hasVehicle()) {
				kill();
			}
			if (this.age > 1) {
				BlockPos blockPos2 = this.getBlockPos();
				BlockState blockState = this.getLandingBlockState();
				FluidState fluidState = getWorld().getFluidState(this.getBlockPos().add(0, 0, 0));
				onWater = fluidState.getFluid() == Fluids.WATER;
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
					if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
						this.dropItem(ModItems.SMACKADAMIA_SEED_PACKET);
					}
					this.discard();
				}
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
	}

	@Override
	protected void mobTick() {
		super.mobTick();
		if (this.getTarget() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.isFlying() || generalPvZombieEntity.isHovering() || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("gargantuar") || ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("big")|| ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("tall"))){
			getWorld().sendEntityStatus(this, (byte) 108);
		}
		else {
			getWorld().sendEntityStatus(this, (byte) 109);
		}
	}

	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.SMACKADAMIA_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.SMACKADAMIA_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createSmackadamiaAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 65.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 4D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 9.0D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	private float getAttackDamage() {
		return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZSounds.PLANTPLANTEDEVENT;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	public void onDeath(DamageSource source) {
		super.onDeath(source);
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


	/**
	 * //~*~//~DAMAGE HANDLER~//~*~//
	 **/



	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}
}
