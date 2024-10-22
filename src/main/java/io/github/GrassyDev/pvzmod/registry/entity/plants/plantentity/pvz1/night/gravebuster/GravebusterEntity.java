package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster;

import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.speakervehicle.SpeakerVehicleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
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

import java.util.ArrayList;
import java.util.List;

public class GravebusterEntity extends PlantEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private int attackTicksLeft;
    private boolean notready;
    private boolean used;
	private String controllerName = "gravebustercontroller";

    public GravebusterEntity(EntityType<? extends GravebusterEntity> entityType, World world) {
        super(entityType, world);


        this.notready = true;
        this.attackTicksLeft = 80;
		this.isBurst = true;
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
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
		if (this.attackTicksLeft > 0) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("gravebuster.eating"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("gravebuster.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new MeleeAttackGoal(this, 0D, true));
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof GraveEntity || livingEntity instanceof SpeakerVehicleEntity || livingEntity instanceof ZombieObstacleEntity; }));
	}

	public boolean tryAttack(Entity target) {
		this.notready = false;
		int i = this.attackTicksLeft;
		if (i == 80) {
			this.playSound(PvZSounds.GRAVEBUSTEREATINGEVENT, 1F, 1.0F);
		}
		if (i > 0) {
			float f = 1;
			target.dismountVehicle();
			boolean bl = target.damage(getDamageSources().mobAttack(this), f);
			if (bl) {
				this.applyDamageEffects(this, target);
			}
		}
		if (i <= 1) {
			float f = this.getAttackDamage();
			boolean bl = target.damage(getDamageSources().mobAttack(this), f);if (bl) {
				this.applyDamageEffects(this, target);
			}
			this.used = true;
			target.damage(getDamageSources().mobAttack(this), Integer.MAX_VALUE);
			this.remove(RemovalReason.DISCARDED);
			return bl;
		} else {
			return false;
		}
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public EntityType<? extends GravebusterEntity> entityBox = PvZEntity.GRAVEBUSTER;

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		Box box = PvZEntity.GRAVEBUSTER.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ());
		List<LivingEntity> list = new ArrayList<>();
		list.addAll(getWorld().getNonSpectatingEntities(GraveEntity.class, box.expand(0)));
		list.addAll(getWorld().getNonSpectatingEntities(ZombieObstacleEntity.class, box.expand(0)));
		list.addAll(getWorld().getNonSpectatingEntities(SpeakerVehicleEntity.class, box.expand(0)));
		List<LivingEntity> list2 = list;
		if (list2.isEmpty() && this.age > 10){
			this.discard();
		}
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.GRAVEBUSTER_SEED_PACKET);
				}
				this.discard();
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.notready) {
			this.attackTicksLeft = 80;
		}

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
			RandomGenerator randomGenerator = this.getRandom();
			BlockState blockState = this.getLandingBlockState();
			for(int i = 0; i < 4; ++i) {
				double d = this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
				double e = this.getY() + 0.3;
				double f = this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
				this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.GRAVEBUSTER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createGravebusterAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 999.0D);
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

	private float getAttackDamage(){
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
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
