package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.pea.ShootingPeaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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


import java.util.EnumSet;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ThreepeaterEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private String controllerName = "threepeacontroller";



	public boolean isFiring;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public ThreepeaterEntity(EntityType<? extends ThreepeaterEntity> entityType, World world) {
        super(entityType, world);


    }

	static  {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
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
		if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("threepeater.shoot"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("threepeater.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new ThreepeaterEntity.FireBeamGoal(this));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
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
					this.dropItem(ModItems.THREEPEATER_SEED_PACKET);
				}
				this.discard();
			}
		}
		this.targetZombies(this.getPos(), 7, false, false, false);
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.THREEPEATER_SEED_PACKET);
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
		return ModItems.THREEPEATER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createThreepeaterAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D);
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

	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final ThreepeaterEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(ThreepeaterEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -9) {
					double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
					Vec3d targetPos = livingEntity.getPos();
					double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						// Middle Pea
						ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.plantEntity.getWorld());
						double d = this.plantEntity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.plantEntity.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
						double g = predictedPos.getZ() - this.plantEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.5F, 0F);
						proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.8D, this.plantEntity.getZ());
						proj.setOwner(this.plantEntity);
						proj.damageMultiplier = plantEntity.damageMultiplier;
						if (livingEntity != null && livingEntity.isAlive()) {
							this.beamTicks = -16;
							this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
							this.plantEntity.getWorld().spawnEntity(proj);
						}
						// Right Pea
						ShootingPeaEntity proj3 = new ShootingPeaEntity(PvZEntity.PEA, this.plantEntity.getWorld());
						Vec3d vec3d2 = new Vec3d((double) 0.0, 0.0, 0.5).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d vec3d4 = new Vec3d((double) 10, 0.0, 1.5).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						double d3 = this.plantEntity.squaredDistanceTo(predictedPos);
						float df3 = (float) d3;
						double e3 = predictedPos.getX() - this.plantEntity.getX();
						double f3 = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
						double g3 = predictedPos.getZ() - this.plantEntity.getZ();
						float h3 = MathHelper.sqrt(MathHelper.sqrt(df3)) * 0.5F;
						proj3.setVelocity(e3 * (double) h + vec3d4.x, f3 * (double) h3, g3 * (double) h +vec3d4.z, 0.5F, 0F);
						proj3.updatePosition(this.plantEntity.getX() + vec3d2.x, this.plantEntity.getY() + 0.5, this.plantEntity.getZ() + vec3d2.z);
						proj3.setOwner(this.plantEntity);
						proj3.damageMultiplier = plantEntity.damageMultiplier;
						if (livingEntity != null && livingEntity.isAlive()) {
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
							this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
							this.plantEntity.getWorld().spawnEntity(proj3);
						}
						// Left Pea
						ShootingPeaEntity proj2 = new ShootingPeaEntity(PvZEntity.PEA, this.plantEntity.getWorld());
						Vec3d vec3d5 = new Vec3d((double) 0.0, 0.0, -0.5).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d vec3d6 = new Vec3d((double) 10.0, 0.0, -1.5).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						double d2 = this.plantEntity.squaredDistanceTo(predictedPos);
						float df2 = (float) d2;
						double e2 = predictedPos.getX() - this.plantEntity.getX();
						double f2 = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
						double g2 = predictedPos.getZ() - this.plantEntity.getZ();
						float h2 = MathHelper.sqrt(MathHelper.sqrt(df2)) * 0.5F;
						proj2.setVelocity(e2 * (double) h + vec3d6.x, f2 * (double) h2, g2 * (double) h + vec3d6.z, 0.5F, 0);
						proj2.updatePosition(this.plantEntity.getX() + vec3d5.x, this.plantEntity.getY() + 0.5, this.plantEntity.getZ() + vec3d5.z);
						proj2.setOwner(this.plantEntity);
						proj2.damageMultiplier = plantEntity.damageMultiplier;
						if (livingEntity != null && livingEntity.isAlive()) {
							this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
							this.plantEntity.getWorld().spawnEntity(proj2);
						}
					}
				} else if (this.animationTicks >= 0) {
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
