package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
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
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;
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
import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class IcebergLettuceEntity extends PlantEntity implements GeoAnimatable {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> CHARGED;
    private static final TrackedData<Boolean> IGNITED;
    private int currentFuseTime;
    private int fuseTime = 16;
    private int explosionRadius = 1;
	private String controllerName = "bombcontroller";

    public IcebergLettuceEntity(EntityType<? extends IcebergLettuceEntity> entityType, World world) {
        super(entityType, world);

		this.isBurst = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if ((Boolean)this.dataTracker.get(CHARGED)) {
			nbt.putBoolean("powered", true);
		}

		nbt.putShort("Fuse", (short)this.fuseTime);
		nbt.putByte("ExplosionRadius", (byte)this.explosionRadius);
		nbt.putBoolean("ignited", this.getIgnited());
		nbt.putBoolean("Permanent", this.getPuffshroomPermanency());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(CHARGED, nbt.getBoolean("powered"));
		if (nbt.contains("Fuse", 99)) {
			this.fuseTime = nbt.getShort("Fuse");
		}

		if (nbt.contains("ExplosionRadius", 99)) {
			this.explosionRadius = nbt.getByte("ExplosionRadius");
		}

		if (nbt.getBoolean("ignited")) {
			this.ignite();
		}
		this.dataTracker.set(DATA_ID_TYPE_COUNT, nbt.getBoolean("Permanent"));

	}

	static {
		FUSE_SPEED = DataTracker.registerData(IcebergLettuceEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(IcebergLettuceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(IcebergLettuceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 106) {
			for(int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 6 * (this.random.range(-1, 1) * 1.5);
				this.getWorld().addParticle(ParticleTypes.ITEM_SNOWBALL, this.getX(), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
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
		if (entityData instanceof IcebergLettuceEntity.PlantData plantData && !spawnReason.equals(SpawnReason.SPAWN_EGG)) {
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
			DataTracker.registerData(IcebergLettuceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum PuffPermanency {
		DEFAULT(false),
		PERMANENT(true);

		PuffPermanency(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getPuffshroomPermanency() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setPuffshroomPermanency(IcebergLettuceEntity.PuffPermanency puffshroomPermanency) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, puffshroomPermanency.getId());
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
        int i = this.getFuseSpeed();
        if (i > 0) {
            event.getController().setAnimation(RawAnimation.begin().thenPlay("small.seedling.bomb"));
        } else {
            event.getController().setAnimation(RawAnimation.begin().thenLoop("small.idle"));
        }
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(2, new IcebergIgniteGoal(this));
	}

	public boolean tryAttack(Entity target) {
		return true;
	}

	public int getFuseSpeed() {
		return (Integer)this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean getIgnited() {
		return (Boolean)this.dataTracker.get(IGNITED);
	}

	public void ignite() {
		this.dataTracker.set(IGNITED, true);
	}
	List<LivingEntity> checkList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	private void raycastExplode() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(3));
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

			float damage = 4;
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
			if ("crystal".equals(zombieMaterial) || "gold".equals(zombieMaterial) || "cloth".equals(zombieMaterial)) {
				damage = damage / 2;
			}
			if (livingEntity instanceof ScorchedTile) {
				livingEntity.discard();
			}
			ZombiePropEntity zombiePropEntity4 = null;
			if (livingEntity.hasVehicle()) {
				for (Entity entity1 : livingEntity.getVehicle().getPassengerList()) {
					if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
						zombiePropEntity4 = zpe;
					}
				}
			}
			for (Entity entity1 : livingEntity.getPassengerList()) {
				if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
					zombiePropEntity4 = zpe;
				}
			}
			if (((livingEntity instanceof Monster &&
					zombiePropEntity4 == null &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering()) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity2 && checkList.contains(generalPvZombieEntity2.getOwner())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity))) {
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
				if (damage > livingEntity.getHealth() &&
						!(livingEntity instanceof ZombieShieldEntity) &&
						livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - livingEntity.getHealth();
					livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
					generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this), damage2);
					checkList.add(livingEntity);
					checkList.add(generalPvZombieEntity);
				} else if (livingEntity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.getVehicle() != null) {
					zombieShieldEntity.damage(getDamageSources().mobProjectile(this, this), damage);
					checkList.add((LivingEntity) zombieShieldEntity.getVehicle());
					checkList.add(zombieShieldEntity);
				} else if (livingEntity.getVehicle() instanceof ZombieShieldEntity zombieShieldEntity) {

					zombieShieldEntity.damage(getDamageSources().mobProjectile(this, this), damage);
					checkList.add(livingEntity);
					checkList.add(zombieShieldEntity);
				} else {
					if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (zombiePropEntity2 == null && !checkList.contains(livingEntity)) {
						livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
						checkList.add(livingEntity);
					} else if (livingEntity instanceof ZombieVehicleEntity && !checkList.contains(livingEntity)) {
						livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
						checkList.add(livingEntity);
					}
				}
				if (!(livingEntity instanceof ZombieShieldEntity) && !(zombiePropEntity2 instanceof ZombieShieldEntity)) {
					livingEntity.removeStatusEffect(PvZCubed.FROZEN);
					livingEntity.removeStatusEffect(PvZCubed.ICE);
					if (livingEntity.hasStatusEffect(PvZCubed.WARM) || livingEntity.isOnFire()) {
						livingEntity.removeStatusEffect(PvZCubed.WARM);
						livingEntity.extinguish();
					}
					livingEntity.removeStatusEffect(PvZCubed.STUN);
					livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
				}
			}
		}
	}

	private void spawnEffectsCloud() {
		AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity.setParticleType(ParticleTypes.SNOWFLAKE);
		areaEffectCloudEntity.setRadius(1.5F);
		areaEffectCloudEntity.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity.setWaitTime(1);
		areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 40);
		areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
		this.getWorld().spawnEntity(areaEffectCloudEntity);
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
		this.targetZombies(this.getPos(), 3, false, false, false);
		LivingEntity target = this.getTarget();
		if (target instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()){
			this.setTarget(null);
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.ICEBERGLETTUCE_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (this.isAlive()) {
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				this.raycastExplode();
				this.getWorld().sendEntityStatus(this, (byte) 106);
				this.playSound(PvZSounds.ICEBERGEXPLOSIONEVENT, 1F, 1F);
				this.spawnEffectsCloud();
				this.dead = true;
				this.remove(RemovalReason.DISCARDED);
			}
		}
		if (this.age >= 900 && !this.getPuffshroomPermanency()) {
			this.discard();
		}
		float time = 200 / this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
		if (this.age > 4 && this.age <= time && !this.getPuffshroomPermanency() && !this.hasStatusEffect(StatusEffects.GLOWING)) {
			if (this.getWorld().getGameRules().getBoolean(PvZCubed.PLANTS_GLOW)) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, (int) Math.floor(time), 1)));
			}
		}
	}


	public void tickMovement() {
        super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
			this.discard();
		}
    }


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.ICEBERGLETTUCE_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createIcebergLettuceAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4);
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
		return null	;
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

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			this.clearStatusEffects();
			return this.damage(getDamageSources().playerAttack(playerEntity), 9999.0F);
		}
		else {
			this.getWorld().sendEntityStatus(this, (byte) 106);
			this.playSound(PvZSounds.ICEBERGEXPLOSIONEVENT, 1F, 1F);
			this.spawnEffectsCloud();
			this.dead = true;
			this.remove(RemovalReason.DISCARDED);
			return true;
		}
	}


	@Override
	protected void applyDamage(DamageSource source, float amount) {
		int i = this.getFuseSpeed();
		if (i <= 0 || source.getAttacker() instanceof PlayerEntity || source.isOutOfWorld()) {
			super.applyDamage(source, amount);
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		LivingEntity attacker = (LivingEntity) source.getAttacker();
		if (attacker instanceof GargantuarEntity && attacker.isAlive()){
			attacker.damage(getDamageSources().mobProjectile(this, this), 4);
			attacker.removeStatusEffect(PvZCubed.FROZEN);
			attacker.removeStatusEffect(PvZCubed.ICE);
			if (attacker.hasStatusEffect(PvZCubed.WARM) || attacker.isOnFire()) {
				attacker.removeStatusEffect(PvZCubed.WARM);
				attacker.extinguish();
			}
			attacker.removeStatusEffect(PvZCubed.STUN);
			attacker.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
			attacker.removeStatusEffect(PvZCubed.FROZEN);
			attacker.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
		}
		super.onDeath(source);
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canIcebergLettuceSpawn(EntityType<? extends IcebergLettuceEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float nightchance = random.nextFloat();
		if (nightchance <= 0.5){
			return !world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
					!checkPlant(Vec3d.ofCenter(pos), world, type) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_SPAWN) && PVZCONFIG.nestedSpawns.spawnPlants();
		}
		else {
			return !world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
					!checkPlant(Vec3d.ofCenter(pos), world, type) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() && world.getAmbientDarkness() < 4 &&
					world.getLightLevel(LightType.SKY, pos) > 10 && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_SPAWN) && PVZCONFIG.nestedSpawns.spawnPlants();
		}
	}
}
