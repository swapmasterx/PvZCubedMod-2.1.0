package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.burstshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
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
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biomes;
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

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;
import static io.github.GrassyDev.pvzmod.PvZCubed.WET;

public class BurstshroomEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private int attackTicksLeft;
	private String controllerName = "burstshroom";
	public boolean isFiring;

	public BurstshroomEntity(EntityType<? extends BurstshroomEntity> entityType, World world) {
		super(entityType, world);
		this.setFireImmune(FireImmune.TRUE);
		this.nocturnal = true;
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
		if (this.getIsAsleep()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("burstshroom.asleep"));
		}
		else if (this.getIsAltFire()){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("burstshroom.exhausted"));
		}
		else if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("burstshroom.explode"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("burstshroom.idle"));
		}
		return PlayState.CONTINUE;
	}

	/**
	 * /~*~//~**~//~*~//
	 **/

	protected void initGoals() {
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {

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

	protected int exhaustTicks = 300;

	public void tick() {
		super.tick();
		if (!this.getWorld().isClient()){
			if (this.getIsAltFire()){
				--exhaustTicks;
			}
			if (exhaustTicks <= 0){
				this.setAltfire(AltFire.FALSE);
				exhaustTicks = 300;
			}
		}
		if (!this.getWorld().isClient && !this.getCofee()) {
			if ((this.getWorld().getAmbientDarkness() >= 2 ||
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
					this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(Biomes.MUSHROOM_FIELDS)))) {
				this.setIsAsleep(IsAsleep.FALSE);
			} else if (this.getWorld().getAmbientDarkness() < 2 &&
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
					!this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(Biomes.MUSHROOM_FIELDS))) {
				this.setIsAsleep(IsAsleep.TRUE);
			}
		}
		if (this.isWet() || this.hasStatusEffect(WET)){
			this.setAltfire(AltFire.TRUE);
			exhaustTicks = 5;
		}
		if (this.getIsAsleep() || this.getIsAltFire()){
			this.setTarget(null);
			this.setImmune(Immune.FALSE);
			this.illuminate = false;
		}
		else {
			if (!this.getIsAsleep() && !this.getIsAltFire()) {
				this.illuminate = true;
				this.targetZombies(this.getPos(), 3, true, false, true);
			}
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.BURSTSHROOM_SEED_PACKET);
				}
				this.discard();
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


	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.BURSTSHROOM_SEED_PACKET);
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
		return ModItems.BURSTSHROOM_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createBurstshroomAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 1.5D)
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


	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (!this.isFiring) {
			super.applyDamage(source, amount);
		}
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

	protected void splashDamage() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(2));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					if (!var9.hasNext()) {
						return;
					}

					livingEntity = (LivingEntity) var9.next();
				} while (livingEntity == this);
			} while (this.squaredDistanceTo(livingEntity) > 4);

			if (livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) {
				ZombiePropEntity zombiePropEntity2 = null;
				ZombiePropEntity zombiePropEntity3 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
						zombiePropEntity2 = zpe;
					}
					else if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity3 = zpe;
					}
				}
				if (livingEntity.getY() < (this.getY() + 1.5) && livingEntity.getY() > (this.getY() - 1.5)) {
					if (!getWorld().isClient &&
							!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
							!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
							!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying())) {
						String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
						SoundEvent sound;
						sound = switch (zombieMaterial) {
							case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
							case "plastic" -> PvZSounds.CONEHITEVENT;
							case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
							default -> PvZSounds.PEAHITEVENT;
						};
						livingEntity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
						float damage = 40F;
						if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
							if (!livingEntity.isWet() && !livingEntity.hasStatusEffect(PvZCubed.WET)) {
								livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
								livingEntity.setOnFireFor(4);
								if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
									generalPvZombieEntity.fireSplashTicks = 10;
								}
							}
							damage = damage * 2;
						}
						if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)){
							damage = damage / 2;
						}
						if (livingEntity.hasStatusEffect(PvZCubed.WET) || livingEntity.isWet() || (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())){
							damage = damage / 2;
						}
						if (damage > livingEntity.getHealth() &&
								!(livingEntity instanceof ZombieShieldEntity) &&
								livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							float damage2 = damage - ((LivingEntity) livingEntity).getHealth();
							livingEntity.damage(getDamageSources().mobProjectile(this, this), 0);
							livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
							generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this), damage2);
						} else {
							livingEntity.damage(getDamageSources().mobProjectile(this, this), 0);
							livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						}
						if (!livingEntity.isWet() && !livingEntity.hasStatusEffect(PvZCubed.WET)) {
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
							livingEntity.setOnFireFor(4);
							if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
								generalPvZombieEntity.fireSplashTicks = 10;
							}
						}
						Vec3d front = new Vec3d((double) 1, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d frontleft = new Vec3d((double) 1, 0.0, -1).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d left = new Vec3d((double) 0, 0.0, -1).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d bottomleft = new Vec3d((double) -1, 0.0, -1).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d bottom = new Vec3d((double) -1, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d bottomright = new Vec3d((double) -1, 0.0, 1).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d right = new Vec3d((double) 0, 0.0, 1).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						Vec3d frontright = new Vec3d((double) 1, 0.0, 1).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						List<LivingEntity> frontList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().offset(front));
						List<LivingEntity> frontleftList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().offset(frontleft));
						List<LivingEntity> leftList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().offset(left));
						List<LivingEntity> bottomleftList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().offset(bottomleft));
						List<LivingEntity> bottomList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().offset(bottom));
						List<LivingEntity> bottomrightList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().offset(bottomright));
						List<LivingEntity> rightList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().offset(right));
						List<LivingEntity> frontrightList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().offset(frontright));
						if (livingEntity.squaredDistanceTo(this) <= 1){
							Vec3d livingEntityVec = new Vec3d((double) -1, 0.0, 0).rotateY(-livingEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(livingEntityVec.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								Vec3d vehicleVec = new Vec3d((double) -1, 0.0, 0).rotateY(-vehicle.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(vehicleVec.add(new Vec3d(0, 0.5, 0)));
							}
						}
						else if (frontList.contains(livingEntity)){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(front.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(front.add(new Vec3d(0, 0.5, 0)));
							}
						}
						else if (frontleftList.contains(livingEntity)){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(frontleft.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(frontleft.add(new Vec3d(0, 0.5, 0)));
							}
						}
						else if (frontrightList.contains(livingEntity)){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(frontright.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(frontright.add(new Vec3d(0, 0.5, 0)));
							}
						}
						else if (leftList.contains(livingEntity)){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(left.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(left.add(new Vec3d(0, 0.5, 0)));
							}
						}
						else if (rightList.contains(livingEntity)){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(right.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(right.add(new Vec3d(0, 0.5, 0)));
							}
						}
						else if (bottomleftList.contains(livingEntity)){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(bottomleft.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(bottomleft.add(new Vec3d(0, 0.5, 0)));
							}
						}
						else if (bottomrightList.contains(livingEntity)){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(bottomright.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(bottomright.add(new Vec3d(0, 0.5, 0)));
							}
						}
						else if (bottomList.contains(livingEntity)){
							livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
							livingEntity.setVelocity(bottom.add(new Vec3d(0, 0.5, 0)));
							if (livingEntity.getVehicle() instanceof LivingEntity vehicle){
								vehicle.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
								vehicle.setVelocity(bottom.add(new Vec3d(0, 0.5, 0)));
							}
						}
					}
				}
			}
		}
	}

	int beamTicks;
	int animationTicks;

	boolean charge = false;

	boolean shootSwitch = true;
	boolean shot = false;

	public void FireBeamGoal() {
		LivingEntity livingEntity = this.getTarget();
		++this.beamTicks;
		++this.animationTicks;
		if ((livingEntity != null || animationTicks < 0) && !this.getIsAsleep() && !this.getIsAltFire()) {
			if (this.shootSwitch){
				this.charge = false;
				this.beamTicks = -15;
				this.animationTicks = -30;
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
			this.isFiring = true;
			this.setImmune(Immune.TRUE);
			if (this.animationTicks >= 0) {
				this.getWorld().sendEntityStatus(this, (byte) 110);
				this.isFiring = false;
				this.setImmune(Immune.FALSE);
				this.beamTicks = -15;
				this.animationTicks = -30;
				if (shot) {
					this.setAltfire(AltFire.TRUE);
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
				shot = false;
			}
			if (this.beamTicks >= 0) {
				if (!this.isInsideWaterOrBubbleColumn()) {
					splashDamage();
					this.beamTicks = -30;
					this.getWorld().sendEntityStatus(this, (byte) 111);
					this.isFiring = true;
					this.setImmune(Immune.TRUE);
					this.playSound(PvZSounds.FIREPEAHITEVENT, 5F, 1);
					this.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 0.25F, 1);
					shot = true;
				}
			}
		}
		else if (animationTicks >= 0){
			this.shootSwitch = true;
			this.getWorld().sendEntityStatus(this, (byte) 110);
			this.isFiring = false;
			this.setImmune(Immune.FALSE);
			if (this.getTarget() != null){
				this.attack(this.getTarget(), 0);
			}
			if (shot) {
				this.setAltfire(AltFire.TRUE);
				this.getWorld().sendEntityStatus(this, (byte) 121);
			}
			shot = false;
		}
	}
}
