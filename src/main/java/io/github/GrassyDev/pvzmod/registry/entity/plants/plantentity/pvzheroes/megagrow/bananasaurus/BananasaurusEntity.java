package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.megagrow.bananasaurus;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.banana.BananaProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class BananasaurusEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private int attackTicksLeft;
	private String controllerName = "bananasaurus";
	public boolean isFiring;
	protected int bananaCount;
	public float meleeSpeed = 1;

	public BananasaurusEntity(EntityType<? extends BananasaurusEntity> entityType, World world) {
		super(entityType, world);

	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 106) {
			this.attackTicksLeft = MathHelper.floor(30 * meleeSpeed);
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
		if (status == 119) {
			this.meleeSpeed -= 0.1;
		}
		if (status == 120) {
			this.meleeSpeed = 1;
		}
		if (status == 121) {
			this.meleeSpeed += 0.1;
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
		if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("bananasaurus.burp"));
			event.getController().setAnimationSpeed(1);
		} else if (i <= 0) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("bananasaurus.idle"));
			event.getController().setAnimationSpeed(1);
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("bananasaurus.bite"));
			event.getController().setAnimationSpeed(1 / this.meleeSpeed);
		}
		return PlayState.CONTINUE;
	}

	/**
	 * /~*~//~**~//~*~//
	 **/

	protected void initGoals() {
		/**this.goalSelector.add(1, new BananasaurusEntity.FireBeamGoal(this));**/
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}

	public void smack(Entity target) {
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
			if (this.meleeSpeed > 0.3) {
				this.meleeSpeed -= 0.1f;
				this.getWorld().sendEntityStatus(this, (byte) 119);
			}
			this.attackTicksLeft = MathHelper.floor(30 * meleeSpeed);
			this.getWorld().sendEntityStatus(this, (byte) 106);
			if (bananaCount <= 6) {
				++this.bananaCount;
			}
			this.setTarget(null);
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
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/**
	 * //~*~//~TICKING~//~*~//
	 **/

	private int chomperAudioDelay = -1;

	public void tick() {
		super.tick();
		if (--this.chomperAudioDelay == 0) {
			this.playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.3F, 1.0F);
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.BANANASAURUS_SEED_PACKET);
				}
				this.discard();
			}
		}
		this.targetZombies(this.getPos(), 2, false, false, !checkForZombiesMelee().isEmpty());
		LivingEntity target = this.getTarget();
		if (target != null && !this.getWorld().isClient()) {
			if (target.squaredDistanceTo(this) <= 25 && this.attackTicksLeft <= 0) {
				this.isBurst = true;
				this.smack(target);
			} else if (target.squaredDistanceTo(this) > 25) {
				this.isBurst = false;
			}
		}
		if (target == null || target.squaredDistanceTo(this) > 25) {
			if (!this.getWorld().isClient()) {
				if (this.meleeSpeed < 1) {
					this.meleeSpeed += 0.1f;
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
			}
		}
		if (!this.getWorld().isClient()) {
			this.FireBeamGoal();
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
			dropItem(ModItems.BANANASAURUS_SEED_PACKET);
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
		return ModItems.BANANASAURUS_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createBananasaurusAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D);
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


	/** /~*~//~*GOALS*~//~*~/ **/

	/**static class FireBeamGoal extends Goal {
		private final BananasaurusEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(BananasaurusEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && livingEntity.squaredDistanceTo(plantEntity) > 25 && this.plantEntity.bananaCount >= 1 && !this.plantEntity.isBurst;
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
			this.plantEntity.bananaCount = 0;
			if (plantEntity.getTarget() != null){
				this.plantEntity.attack(plantEntity.getTarget(), 0);
			}
			this.plantEntity.setTarget(null);
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
				if (this.beamTicks >= 0 && this.animationTicks <= -5) {
					for (int x = 0; x <= this.plantEntity.bananaCount; ++x) {
						if (this.plantEntity.bananaCount > 0){
							this.plantEntity.playSound(SoundEvents.ENTITY_PLAYER_BURP, 2F, 1);
						}
						if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
							RandomGenerator randomGenerator = this.plantEntity.random;
							double xr = (double) MathHelper.nextBetween(randomGenerator, 0F, 30F);
							double zr = (double) MathHelper.nextBetween(randomGenerator, -5f, 5f);
							Vec3d vec3d2 = new Vec3d((double) xr, 0.0, zr).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
							BananaProjEntity proj = new BananaProjEntity(PvZEntity.BANANAPROJ, this.plantEntity.getWorld());
							float h = MathHelper.sqrt(MathHelper.sqrt(100)) * 0.5F;
							proj.setVelocity(vec3d2.x, -3.9200000762939453 + 28 / (h * 2.2), vec3d2.z, 0.5F, 0F);
							proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
							proj.setOwner(this.plantEntity);
							if (livingEntity != null && livingEntity.isAlive()) {
								this.beamTicks = -7;
								this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
								this.plantEntity.getWorld().spawnEntity(proj);
							}
						}
					}
				} else if (this.animationTicks >= 0) {
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -7;
					this.animationTicks = -16;
					this.plantEntity.bananaCount = 0;
					this.plantEntity.setTarget(null);
				}
				super.tick();
			}
		}
	}**/

	int beamTicks;
	int animationTicks;

	boolean shootSwitch = true;
	boolean shot = false;

	public void FireBeamGoal() {
		LivingEntity livingEntity = this.getTarget();
		++this.beamTicks;
		++this.animationTicks;
		if (((livingEntity != null && livingEntity.squaredDistanceTo(this) > 25) || animationTicks < 0) && this.bananaCount >= 1) {
			if (this.shootSwitch){
				this.beamTicks = -14;
				this.animationTicks = -32;
				this.getNavigation().stop();
				this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
				this.velocityDirty = true;
				this.shootSwitch = false;
			}
			this.getNavigation().stop();
			if (livingEntity != null) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			}
			this.getWorld().sendEntityStatus(this, (byte) 111);
			if (this.beamTicks >= 0 && this.animationTicks <= -10) {
				for (int x = 0; x <= this.bananaCount; ++x) {
					if (this.bananaCount > 0) {
						this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 2F, 1);
					}
					if (!this.isInsideWaterOrBubbleColumn()) {
						RandomGenerator randomGenerator = this.random;
						double xr = (double) MathHelper.nextBetween(randomGenerator, 0F, 30F);
						double zr = (double) MathHelper.nextBetween(randomGenerator, -5f, 5f);
						Vec3d vec3d2 = new Vec3d((double) xr, 0.0, zr).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						BananaProjEntity proj = new BananaProjEntity(PvZEntity.BANANAPROJ, this.getWorld());
						float h = MathHelper.sqrt(MathHelper.sqrt(100)) * 0.5F;
						proj.setVelocity(vec3d2.x, -3.9200000762939453 + 28 / (h * 2.2), vec3d2.z, 0.4F, 0F);
						proj.updatePosition(this.getX(), this.getY() + 0.75D, this.getZ());
						proj.setOwner(this);
						this.beamTicks = -14;
						this.getWorld().sendEntityStatus(this, (byte) 111);
						this.getWorld().spawnEntity(proj);
						shot = true;
					}
				}
			} else if (this.animationTicks >= 0) {
				this.getWorld().sendEntityStatus(this, (byte) 110);
				this.beamTicks = -14;
				this.animationTicks = -32;
				this.bananaCount = 0;
				shot = false;
			}
		}
		else if (animationTicks >= 0){
			if (shot){
				this.bananaCount = 0;
			}
			shot = false;
			this.shootSwitch = true;
			this.getWorld().sendEntityStatus(this, (byte) 110);
			if (this.getTarget() != null){
				this.attack(this.getTarget(), 0);
			}
		}
	}
}
