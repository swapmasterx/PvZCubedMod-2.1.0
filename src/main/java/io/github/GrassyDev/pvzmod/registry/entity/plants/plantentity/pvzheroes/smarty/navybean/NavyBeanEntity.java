package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.navybean;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spit.SpitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
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
import net.minecraft.util.math.Vec3d;
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
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class NavyBeanEntity extends PlantEntity implements GeoEntity, RangedAttackMob {


	private int amphibiousRaycastDelay;



	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private int attackTicksLeft;
	private String controllerName = "beancontroller";
	public boolean isFiring;

	public NavyBeanEntity(EntityType<? extends NavyBeanEntity> entityType, World world) {
		super(entityType, world);

		amphibiousRaycastDelay = 1;

		this.setNoGravity(true);
	}

	public NavyBeanEntity(World world, double x, double y, double z) {
		this(PvZEntity.NAVYBEAN, world);
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
		} else {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
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
		if (this.dryLand) {
			if (this.isFiring) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("navybean.shoot"));
			} else if (i <= 0) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("navybean.idle"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("navybean.hit"));
			}
		}
		else {
			if (this.isFiring) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("navybean.shoot2"));
			} else if (i <= 0) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("navybean.idle2"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("navybean.hit2"));
			}
		}
		return PlayState.CONTINUE;
	}

	/** /~*~//~AI~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(1, new NavyBeanEntity.FireBeamGoal(this));
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {
		if (target.squaredDistanceTo(this) <= 25) {
			this.smack(target);
			this.isBurst = true;
		}
		else {

			this.isBurst = false;
		}
	}

	public boolean smack(Entity target) {
		int i = this.attackTicksLeft;
		ZombiePropEntity passenger = null;
		for (Entity entity1 : target.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				passenger = zpe;
			}
		}
		Entity damaged = target;
		if (passenger != null){
			damaged = passenger;
		}
		if (i <= 0) {
			this.attackTicksLeft = 20;
			this.getWorld().sendEntityStatus(this, (byte) 106);
			boolean bl = damaged.damage(getDamageSources().mobAttack(this), this.getAttackDamage());
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
			return bl;
		} else {
			return false;
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
		if (this.getVehicle() instanceof LilyPadEntity){
			this.getVehicle().discard();
		}
		if (--this.chomperAudioDelay == 0) {
			this.playSound(PvZSounds.PEASHOOTEVENT, 1.0F, 1.0F);
		}
		BlockPos blockPos = this.getBlockPos();
		this.targetZombies(this.getPos(), 2, false, false, !checkForZombiesMelee().isEmpty());
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
				} else {
					this.dryLand = false;
					onWater = true;
				}
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
					if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.NAVYBEAN_SEED_PACKET);
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

	protected List<HostileEntity> checkForZombiesMelee() {
		List<HostileEntity> list = this.getWorld().getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(6));
		List<HostileEntity> list2 = new ArrayList<>();
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			if (!var9.hasNext()) {
				return list2;
			}
			hostileEntity = (HostileEntity) var9.next();

			if (hostileEntity.squaredDistanceTo(this) <= 25) {
				if (!(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
					list2.add(hostileEntity);
				}
			}
		}
	}


	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.NAVYBEAN_SEED_PACKET);
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
		return ModItems.NAVYBEAN_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createNavyBeanAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D);
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


	/**
	 * /~*~//~*GOALS*~//~*~/
	 **/

	static class FireBeamGoal extends Goal {
		private final NavyBeanEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(NavyBeanEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && livingEntity.squaredDistanceTo(plantEntity) > 25 && !plantEntity.dryLand;
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
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
			if (plantEntity.getTarget() != null){
				this.plantEntity.attack(plantEntity.getTarget(), 0);
			}
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
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						SpitEntity proj = new SpitEntity(PvZEntity.SPIT, this.plantEntity.getWorld());
						double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
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
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
						proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
						proj.setOwner(this.plantEntity);
						proj.damageMultiplier = plantEntity.damageMultiplier;
						if (livingEntity != null && livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
							this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
							this.plantEntity.getWorld().spawnEntity(proj);
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
