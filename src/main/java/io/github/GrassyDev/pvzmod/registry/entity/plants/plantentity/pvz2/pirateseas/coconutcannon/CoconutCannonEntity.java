package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.coconut.CoconutEntity;
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
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class CoconutCannonEntity extends PlantEntity implements GeoEntity, RangedAttackMob {
    private String controllerName = "cococontroller";

	public boolean isFiring;
	public boolean blink;
	public int rechargeTime;
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private boolean recharged;

	public CoconutCannonEntity(EntityType<? extends CoconutCannonEntity> entityType, World world) {
        super(entityType, world);

		this.targetStrength = true;
		this.isBurst = true;
    }

	static {
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
		if (status == 88){
			this.recharged = true;
		}
		else if (status == 87) {
			this.recharged = false;
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
			event.getController().setAnimation(RawAnimation.begin().thenPlay("coconutcannon.fire"));
		}
		else if (!this.recharged){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("coconutcannon.recharge"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("coconutcannon.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		customGoals();
	}

	protected void customGoals(){
		this.goalSelector.add(1, new CoconutCannonEntity.FireBeamGoal(this));
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
			super.setPosition((double)MathHelper.floor(x), (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z));
		}

		if (this.age > 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockPos blockPos3 = this.getBlockPos().add(-0, 0, 0);
			BlockPos blockPos4 = this.getBlockPos().add(0, 0, -0);
			BlockPos blockPos5 = this.getBlockPos().add(0, 0, 0);
			BlockPos blockPos6 = this.getBlockPos().add(-0, 0, -0);
			BlockState blockState = this.getLandingBlockState();
			BlockState blockState3 = this.getWorld().getBlockState(this.getSteppingPosition().add(-0, 0, 0));
			BlockState blockState4 = this.getWorld().getBlockState(this.getSteppingPosition().add(0, 0, -0));
			BlockState blockState5 = this.getWorld().getBlockState(this.getSteppingPosition().add(0, 0, 0));
			BlockState blockState6 = this.getWorld().getBlockState(this.getSteppingPosition().add(-0, 0, -0));
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this) ||
					!blockState3.hasSolidTopSurface(getWorld(), blockPos3, this) ||
					!blockState4.hasSolidTopSurface(getWorld(), blockPos4, this) ||
					!blockState6.hasSolidTopSurface(getWorld(), blockPos4, this) ||
					!blockState5.hasSolidTopSurface(getWorld(), blockPos5, this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.COCONUTCANNON_SEED_PACKET);
				}
				this.discard();
			}

		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getVehicle() instanceof LilyPadEntity ||
				this.getVehicle() instanceof BubblePadEntity){
			this.discard();
		}
		List<PlantEntity> list = this.getWorld().getNonSpectatingEntities(PlantEntity.class, this.getBoundingBox());
		for (PlantEntity plantEntity : list){
			if (plantEntity != this){
				plantEntity.discard();
			}
		}
		if (tickDelay <= 1) {
			if (!this.isAiDisabled() && this.isAlive()) {
				setPosition(this.getX(), this.getY(), this.getZ());
			}
			this.targetZombies(this.getPos(), 10, false, false, false);
		}
		--rechargeTime;
		if (rechargeTime <= 0){
			this.getWorld().sendEntityStatus(this, (byte) 88);
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.COCONUTCANNON_SEED_PACKET.getDefaultStack();
	}

	protected boolean startShooting;


	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.COCONUTCANNON_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		else if (!this.getWorld().isClient) {
			if (rechargeTime <= 0 && !this.isFiring && this.getTarget() != null) {
				startShooting = true;
				return ActionResult.SUCCESS;
			} else {
				return ActionResult.PASS;
			}
		}
		return super.interactMob(player, hand);
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createCoconutCannonAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0D);
    }

	protected boolean canClimb() {return false;}

	public boolean collides() {return true;}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {return PvZSounds.SILENCEVENET;}

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

	private boolean attacked;


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final CoconutCannonEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(CoconutCannonEntity plantEntity) {
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
			this.beamTicks = -9;
			this.animationTicks = -26;
			this.plantEntity.getNavigation().stop();
			if (this.plantEntity.startShooting) {
				this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			}
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 87);
			if (this.plantEntity.rechargeTime <= 0 && this.plantEntity.attacked) {
				this.plantEntity.attacked = false;
				this.plantEntity.rechargeTime = 300;
				this.plantEntity.startShooting = false;
			}
			this.plantEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			if (this.plantEntity.startShooting) {
				this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			}
			if (((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0)) {
				this.plantEntity.setTarget((LivingEntity) null);
			} else if (this.plantEntity.startShooting) {
				this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
				if (this.plantEntity.rechargeTime <= 0){
					++this.beamTicks;
					++this.animationTicks;
				}
				if (this.beamTicks >= 0 && this.plantEntity.rechargeTime <= 0) {
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						CoconutEntity proj = new CoconutEntity(PvZEntity.COCONUTPROJ, this.plantEntity.getWorld());
						double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 75 : 1;
						Vec3d targetPos = livingEntity.getPos();
						double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
						double d = this.plantEntity.squaredDistanceTo(predictedPos);
						float df = (float) d;
						double e = predictedPos.getX() - this.plantEntity.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
						double g = predictedPos.getZ() - this.plantEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 1.00F, 0F);
						proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
						proj.setOwner(this.plantEntity);
						if (livingEntity != null && livingEntity.isAlive()) {
							this.beamTicks = -30;
							this.plantEntity.attacked = true;
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
							this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
							this.plantEntity.getWorld().spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0 && this.plantEntity.rechargeTime <= 0){
					this.plantEntity.attacked = false;
					this.plantEntity.rechargeTime = 300;
				}
				else if (this.plantEntity.rechargeTime > 0) {
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 87);
					this.beamTicks = -9;
					this.animationTicks = -26;
					this.plantEntity.startShooting = false;
				}
				super.tick();
			}
		}
	}
}
