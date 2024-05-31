package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.sunflowerseed;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.vampireflower.VampireFlowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.FumeshroomVariants;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
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

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.DISABLE;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SunflowerSeedEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private boolean sunProducerCheck;

    private String controllerName = "puffcontroller";
	public int sunProducingTime = (int) (PVZCONFIG.nestedSun.sunseedSec() * 20);
	public int growUpTime = 0;
	int raycastDelay = (int) (PVZCONFIG.nestedSun.sunseedSec() * 20);
	public boolean produceSun;

	public boolean isFiring;
	private static final TrackedData<Integer> SUN_SPEED;

	static {
		SUN_SPEED = DataTracker.registerData(SunflowerSeedEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}


	public SunflowerSeedEntity(EntityType<? extends SunflowerSeedEntity> entityType, World world) {
        super(entityType, world);

		this.isBurst = true;

    }
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		if (tag.contains("Fuse", 99)) {
			this.sunProducingTime = tag.getShort("Fuse");
		}
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putShort("Fuse", (short)this.sunProducingTime);
	}
	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SUN_SPEED, -1);
	}
	private int currentFuseTime;

	public void setFuseSpeed(int fuseSpeed) {

		this.dataTracker.set(SUN_SPEED, fuseSpeed);
	}

	public int getFuseSpeed() {

		return this.dataTracker.get(SUN_SPEED);
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
		if (status == 106) {
			for(int i = 0; i < 16; ++i) {
				this.getWorld().addParticle(ParticleTypes.CRIT, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.getWorld().addParticle(ParticleTypes.END_ROD, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.getWorld().addParticle(ParticleTypes.CRIT, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
				this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, 0, 0, 0);
			}
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
		if (entityData == null) {
			entityData = new PlantData(true);
		}
		if (entityData instanceof SunflowerSeedEntity.PlantData plantData && !spawnReason.equals(SpawnReason.SPAWN_EGG)) {
			this.naturalSpawn = true;
			if (plantData.tryLilyPad) {
				List<LilyPadEntity> list = world.getEntitiesByClass(
						LilyPadEntity.class, this.getBoundingBox().expand(12.5), EntityPredicates.NOT_MOUNTED
				);
				if (!list.isEmpty()) {
					LilyPadEntity lilyPadEntity = (LilyPadEntity) list.get(0);
					this.startRiding(lilyPadEntity);
				}
			}
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(SunflowerSeedEntity.class, TrackedDataHandlerRegistry.BOOLEAN);



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
			event.getController().setAnimation(RawAnimation.begin().thenPlay("small.sunflower.attack"));
		}
		else{
			event.getController().setAnimation(RawAnimation.begin().thenLoop("small.sunflower"));
		}
		return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(5, new SunflowerSeedEntity.FireBeamGoal(this));
		this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 20.0F));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {
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
			} while (this.squaredDistanceTo(livingEntity) > 9);

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
							case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
							case "plastic" -> PvZSounds.PEAHITEVENT;
							case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
							default -> PvZSounds.PEAHITEVENT;
						};
						livingEntity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
						float damage = 4F;
						if (damage > livingEntity.getHealth() &&
								!(livingEntity instanceof ZombieShieldEntity) &&
								livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							float damage2 = damage - ((LivingEntity) livingEntity).getHealth();
							livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
							livingEntity.damage(getDamageSources().mobProjectile(this, this), 0);
							generalPvZombieEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
							generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this), 0);
						} else {
							livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
							livingEntity.damage(getDamageSources().mobProjectile(this, this), 0);
						}
					}
				}
			}
		}
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
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead) {
					this.dropItem(ModItems.SUNFLOWER_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (this.growUpTime >= 48000){
			PlantEntity plant = (PlantEntity) PvZEntity.SUNFLOWER.create(getWorld());
			this.playSound(PvZSounds.PLANTPLANTEDEVENT);
			if ((this.getWorld() instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.getWorld();
				PlantEntity plantEntity = (PlantEntity) PvZEntity.SUNFLOWER.create(getWorld());
				plantEntity.setTarget(this.getTarget());
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, getWorld().getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				plantEntity.setAiDisabled(this.isAiDisabled());
				if (this.hasCustomName()) {
					plantEntity.setCustomName(this.getCustomName());
					plantEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.hasVehicle()){
					plantEntity.startRiding(this.getVehicle(), true);
				}

				plantEntity.setPersistent();
				serverWorld.spawnEntityAndPassengers(plantEntity);
				this.remove(RemovalReason.DISCARDED);
			}
		}
		if (this.isAlive()) {
			++this.growUpTime;
			this.setFuseSpeed(1);

			int i = this.getFuseSpeed();

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			if (this.currentFuseTime >= this.sunProducingTime) {
				if (!this.getWorld().isClient && this.isAlive() && this.sunProducerCheck && !this.isInsideWaterOrBubbleColumn()){

					this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
					this.dropItem(ModItems.SMALLSUN);
					this.sunProducingTime = (int) (PVZCONFIG.nestedSun.sunseedSec() * 20);
					this.sunProducerCheck = false;
					this.currentFuseTime = this.sunProducingTime;
				}
			}
		}

		this.targetZombies(this.getPos(), 3, true, false, true);
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && --this.sunProducingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && !this.hasStatusEffect(DISABLE)) {
			if (--raycastDelay >= 0){
				this.produceSun();
				raycastDelay = 60;
			}
		}

		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}

	protected void produceSun() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4));
		List<SunflowerSeedEntity> seedEntityList = this.getWorld().getNonSpectatingEntities(SunflowerSeedEntity.class, this.getBoundingBox().expand(4));
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
			} while (this.squaredDistanceTo(livingEntity) > 16);

			if (seedEntityList.size() <= 1 && this.getWorld().getAmbientDarkness() < 2 &&
				this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 2) {
						this.sunProducerCheck = true;
			}
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.SUNFLOWERSEED_SEED_PACKET.getDefaultStack();
	}
	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.BONE_MEAL)) {
			this.growUpTime += 12000;
			if (!player.getAbilities().creativeMode) {
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/


	public static DefaultAttributeContainer.Builder createSunflowerSeedAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3D);
    }

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
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


	/** /~*~//~*SPAWNING*~//~*~/ **/
	public static boolean canSunflowerSeedSpawn(EntityType<? extends SunflowerSeedEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		return !world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
			world.getBlockState(blockPos).isIn(BlockTags.ANIMALS_SPAWNABLE_ON) &&
					!checkPlant(Vec3d.ofCenter(pos), world, type) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() &&
				world.getBaseLightLevel(pos, 0) > 8 && Objects.requireNonNull(world.getServer()).getGameRules().getBooleanValue(PvZCubed.SHOULD_PLANT_SPAWN) && PVZCONFIG.nestedSpawns.spawnPlants();
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final SunflowerSeedEntity sunflowerSeedEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(SunflowerSeedEntity sunflowerSeedEntity) {
			this.sunflowerSeedEntity = sunflowerSeedEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.sunflowerSeedEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -6;
			this.animationTicks = -11;
			this.sunflowerSeedEntity.getNavigation().stop();
			this.sunflowerSeedEntity.getLookControl().lookAt(this.sunflowerSeedEntity.getTarget(), 90.0F, 90.0F);
			this.sunflowerSeedEntity.velocityDirty = true;
		}

		public void stop() {
			this.sunflowerSeedEntity.produceSun = false;
			this.sunflowerSeedEntity.getWorld().sendEntityStatus(this.sunflowerSeedEntity, (byte) 110);
			this.sunflowerSeedEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.sunflowerSeedEntity.getTarget();
			this.sunflowerSeedEntity.getNavigation().stop();
			this.sunflowerSeedEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.sunflowerSeedEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.sunflowerSeedEntity.setTarget((LivingEntity) null);
			} else {
				this.sunflowerSeedEntity.getWorld().sendEntityStatus(this.sunflowerSeedEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -5) {
					if (!this.sunflowerSeedEntity.isInsideWaterOrBubbleColumn()) {
						this.beamTicks = -6;
						this.sunflowerSeedEntity.getWorld().sendEntityStatus(this.sunflowerSeedEntity, (byte) 111);
						this.sunflowerSeedEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
						this.sunflowerSeedEntity.splashDamage();
						this.sunflowerSeedEntity.getWorld().sendEntityStatus(this.sunflowerSeedEntity, (byte) 106);
						this.sunflowerSeedEntity.produceSun = true;
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.sunflowerSeedEntity.produceSun = false;
					this.sunflowerSeedEntity.getWorld().sendEntityStatus(this.sunflowerSeedEntity, (byte) 110);
					this.beamTicks = -6;
					this.animationTicks = -11;
				}
				super.tick();
			}
		}
	}
}
