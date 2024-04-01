package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.missiletoe;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.environment.target.missiletoe.MissileToeTarget;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.items.targets.MissileToeTargetItem;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;


import java.util.EnumSet;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class MissileToeEntity extends PlantEntity implements GeoAnimatable, RangedAttackMob {
    private String controllerName = "manualcontroller";

	public boolean isFiring;
	public boolean blink;
	public int rechargeTime;
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private boolean recharged;

	public MissileToeEntity(EntityType<? extends MissileToeEntity> entityType, World world) {
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
		if (status == 115){
			for (int j = 0; j < 1; ++j) {
				RandomGenerator randomGenerator = this.random;
				double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
				double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
				double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
				this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY() + 0.6, this.getZ(), d, e, f);
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

	@Override
	public double getTick(Object object) {
		return 0;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("missiletoe.shoot"));
		}
		else if (!this.recharged){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("missiletoe.building"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("missiletoe.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		customGoals();
	}

	protected void customGoals(){
		this.goalSelector.add(1, new MissileToeEntity.FireBeamGoal(this));
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
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.MISSILETOE_SEED_PACKET);
				}
				this.discard();
			}
		}
		--rechargeTime;
		if (rechargeTime <= 0) {
			this.getWorld().sendEntityStatus(this, (byte) 88);
		}

		List<MissileToeTarget> targetList = this.getWorld().getNonSpectatingEntities(MissileToeTarget.class, this.getBoundingBox().expand(30));
		boolean targetIdBool = false;
		for (MissileToeTarget missileToeTarget : targetList) {
			if (missileToeTarget.getTargetID() == this.getId()) {
				targetIdBool = true;
				this.setTarget(missileToeTarget);
			}
		}
		if (targetIdBool && !this.getWorld().isClient) {
			if (rechargeTime <= 0 && !this.isFiring) {
				startShooting = true;
			}
		}

		if (!this.getWorld().isClient()) {
			if (this.rechargeTime <= 0 && !isFiring) {
				double random = Math.random();
				if (random <= 0.33) {
					this.getWorld().sendEntityStatus(this, (byte) 115);
				}
			}
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
		return ModItems.MISSILETOE_SEED_PACKET.getDefaultStack();
	}

	protected boolean startShooting;


	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.MISSILETOE_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		else if (!this.getWorld().isClient && !itemStack.isOf(ModItems.DAVES_SHOVEL)) {
			if (!player.getInventory().contains(ModItems.MISSILETOE_TARGET.getDefaultStack()) && rechargeTime <= 0 && !isFiring) {
				MissileToeTargetItem missileToeTargetItem = (MissileToeTargetItem) ModItems.MISSILETOE_TARGET;
				missileToeTargetItem.targetID = this.getId();
				player.getInventory().addPickBlock(missileToeTargetItem.getDefaultStack());
			}
		}
		return super.interactMob(player, hand);
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createMissileToeAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0D)
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
		private final MissileToeEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(MissileToeEntity plantEntity) {
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
			this.beamTicks = -8;
			this.animationTicks = -20;
			this.plantEntity.getNavigation().stop();
			if (this.plantEntity.startShooting) {
				this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			}
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.isFiring = false;
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 87);
			if (this.plantEntity.rechargeTime <= 0 && this.plantEntity.attacked) {
				this.plantEntity.attacked = false;
				this.plantEntity.rechargeTime = 280;
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
			if (this.plantEntity.startShooting) {
				this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
				this.plantEntity.isFiring = true;
				if (this.plantEntity.rechargeTime <= 0){
					++this.beamTicks;
					++this.animationTicks;
				}
				if (this.beamTicks >= 0 && this.plantEntity.rechargeTime <= 0) {
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						List<MissileToeTarget> targetList = this.plantEntity.getWorld().getNonSpectatingEntities(MissileToeTarget.class, this.plantEntity.getBoundingBox().expand(30));
						for (MissileToeTarget missileToeTarget : targetList) {
							if (missileToeTarget.getTargetID() == this.plantEntity.getId()) {
								missileToeTarget.canShoot = true;
							}
						}
						this.beamTicks = -30;
						this.plantEntity.attacked = true;
						this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
						this.plantEntity.isFiring = true;
						this.plantEntity.playSound(PvZSounds.SNOWPEASHOOTEVENT, 4F, 1);
					}
				}
				else if (this.animationTicks >= 0 && this.plantEntity.rechargeTime <= 0){
					this.plantEntity.attacked = false;
					this.plantEntity.rechargeTime = 280;
				}
				else if (this.plantEntity.rechargeTime > 0) {
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
					this.plantEntity.isFiring = false;
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 87);
					this.beamTicks = -8;
					this.animationTicks = -20;
					this.plantEntity.startShooting = false;
				}
				super.tick();
			}
		}
	}
}
