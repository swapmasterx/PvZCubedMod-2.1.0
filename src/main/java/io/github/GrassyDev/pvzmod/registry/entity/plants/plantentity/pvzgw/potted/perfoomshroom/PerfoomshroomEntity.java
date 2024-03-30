package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.potted.perfoomshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import net.minecraft.world.biome.BiomeKeys;
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
import java.util.Optional;

public class PerfoomshroomEntity extends PlantEntity implements GeoAnimatable {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> CHARGED;
    private static final TrackedData<Boolean> IGNITED;
    private int lastFuseTime;
    private int currentFuseTime;
    private int fuseTime = 40;
    private int explosionRadius = 1;
	private String controllerName = "doomcontroller";

    public PerfoomshroomEntity(EntityType<? extends PerfoomshroomEntity> entityType, World world) {
        super(entityType, world);

		this.isBurst = true;
		this.nocturnal = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if ((Boolean)this.dataTracker.get(CHARGED)) {
			nbt.putBoolean("powered", true);
		}

		nbt.putShort("Fuse", (short)this.fuseTime);
		nbt.putByte("ExplosionRadius", (byte)this.explosionRadius);
		nbt.putBoolean("ignited", this.getIgnited());
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

	}

	static {
		FUSE_SPEED = DataTracker.registerData(PerfoomshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(PerfoomshroomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(PerfoomshroomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}


	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 106) {
			for(int i = 0; i < 256; ++i) {
				double d = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 256; ++i) {
				double d = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 256; ++i) {
				double d = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 1.5);
				this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 128; ++i) {
				double e = (double)MathHelper.nextBetween(randomGenerator, 0.04F, 0.06F);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() +  (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (double)MathHelper.nextBetween(randomGenerator, 0F, 4.5F),
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						0, e, 0);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (double)MathHelper.nextBetween(randomGenerator, 0F, 4.5F),
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						0, e, 0);
			}
			for (int i = 0; i < 128; ++i){
				double e = (double)MathHelper.nextBetween(randomGenerator, 0.04F, 0.06F);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -3F, 3F),
						this.getY() + 5,
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -3F, 3F),
						0, e, 0);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -4F, 4F),
						this.getY() + (this.random.range(3, 7)),
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -4F, 4F),
						0, e, 0);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -5F, 5F),
						this.getY() + 5,
						this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -5F, 5F),
						0, e, 0);
			}
			for(int i = 0; i < 128; ++i) {
				double d = this.random.nextDouble() / 2 * 0.75;
				double f = this.random.nextDouble() / 2 * 0.75;
				double d1 = this.random.nextDouble() / 2 * 0.75;
				double f1 = this.random.nextDouble() / 2 * 0.75;
				double d2 = this.random.nextDouble() / 2 * 0.75;
				double f2 = this.random.nextDouble() / 2 * 0.75;
				double d3 = this.random.nextDouble() / 2 * 0.75;
				double f3 = this.random.nextDouble() / 2 * 0.75;
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						d, 0, f);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						d1, 0, f1 * -1);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						d2 * -1, 0, f2 * -1);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						this.getY() + MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
						d3 * -1, 0, f3);
			}
		}
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimatableManager data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimatableInstanceCache getFactory() {
		return this.factory;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
        int i = this.getFuseSpeed();
        if (this.getIsAsleep()){
            event.getController().setAnimation(new RawAnimation().loop("doomshroom.asleep"));
        }
        else if (i > 0) {
            event.getController().setAnimation(new RawAnimation().playOnce("doomshroom.explode"));
        } else {
            event.getController().setAnimation(new RawAnimation().loop("doomshroom.idle"));
        }
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(2, new PerfoomIgniteGoal(this));
	}

	public boolean tryAttack(Entity target) {
		return true;
	}

	@Environment(EnvType.CLIENT)
	public float getClientFuseTime(float timeDelta) {
		return MathHelper.lerp(timeDelta, (float)this.lastFuseTime, (float)this.currentFuseTime) / (float)(this.fuseTime - 2);
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
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(10));
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
			} while (this.squaredDistanceTo(livingEntity) > 81);
			float damage = 50;

			ZombiePropEntity zombiePropEntity4 = null;
			boolean hasHelmet = false;
			if (livingEntity.hasVehicle()) {
				for (Entity entity1 : livingEntity.getVehicle().getPassengerList()) {
					if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
						zombiePropEntity4 = zpe;
					}
					if (entity1 instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
						hasHelmet = true;
					}
				}
			}
			for (Entity entity1 : livingEntity.getPassengerList()) {
				if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
					zombiePropEntity4 = zpe;
				}
				if (entity1 instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
					hasHelmet = true;
				}
			}
			if (livingEntity instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
				hasHelmet = true;
			}
			if (!hasHelmet && !(livingEntity instanceof ZombiePropEntity) && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
				damage = damage * 2;
			}
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
			if ("crystal".equals(zombieMaterial)) {
				damage = damage * 2;
			}
			if (((livingEntity instanceof Monster &&
					zombiePropEntity4 == null &&
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
					livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
					generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
					livingEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
					generalPvZombieEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
					checkList.add(livingEntity);
					checkList.add(generalPvZombieEntity);
				} else if (livingEntity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.getVehicle() != null) {
					zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), damage);
					zombieShieldEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
					checkList.add((LivingEntity) zombieShieldEntity.getVehicle());
					checkList.add(zombieShieldEntity);
				} else if (livingEntity.getVehicle() instanceof ZombieShieldEntity zombieShieldEntity) {
					zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), damage);
					zombieShieldEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
					checkList.add(livingEntity);
					checkList.add(zombieShieldEntity);
				} else {
					if (livingEntity instanceof ZombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						livingEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (zombiePropEntity2 == null && !checkList.contains(livingEntity)) {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						livingEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
						checkList.add(livingEntity);
					} else if (livingEntity instanceof ZombieVehicleEntity && !checkList.contains(livingEntity)) {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						livingEntity.damage(PvZCubed.HYPNO_DAMAGE, 0);
						checkList.add(livingEntity);
					}
				}
			}
		}
	}

	private void spawnEffectsCloud() {
		AreaEffectCloudEntity areaEffectCloudEntity2 = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity2.setColor(0xFF66FF);
		areaEffectCloudEntity2.setRadius(6F);
		areaEffectCloudEntity2.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity2.setWaitTime(5);
		areaEffectCloudEntity2.setDuration(areaEffectCloudEntity2.getDuration() / 6);
		areaEffectCloudEntity2.setRadiusGrowth(-areaEffectCloudEntity2.getRadius() / (float)areaEffectCloudEntity2.getDuration());
		this.getWorld().spawnEntity(areaEffectCloudEntity2);
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

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		int i = this.getFuseSpeed();
		if (i <= 0 || source.getAttacker() instanceof PlayerEntity || source.isOutOfWorld()) {
			super.applyDamage(source, amount);
		}
	}

	public void tick() {
		if (!this.getWorld().isClient && !this.getCofee()) {
			if ((this.getWorld().getAmbientDarkness() >= 2 ||
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
					this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS)))) {
				this.setIsAsleep(IsAsleep.FALSE);
			} else if (this.getWorld().getAmbientDarkness() < 2 &&
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
					!this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS))) {
				this.setIsAsleep(IsAsleep.TRUE);
			}
		}
		if (this.getIsAsleep()){
			this.setTarget(null);
		}
		else {
			this.targetZombies(this.getPos(), 10, true, true, true);
		}
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.PERFOOMSHROOM_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (this.getIsAsleep()){
			this.setFuseSpeed(-1);
		}
		if (this.getFuseSpeed() > 0){
			this.setImmune(Immune.TRUE);
		}
		else {
			this.setImmune(Immune.FALSE);
		}
		if (this.isAlive() && !this.getIsAsleep()) {
			this.lastFuseTime = this.currentFuseTime;
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			if (this.currentFuseTime >= this.fuseTime && !this.getIsAsleep()) {
				this.currentFuseTime = this.fuseTime;
				this.raycastExplode();
				this.getWorld().sendEntityStatus(this, (byte) 106);
				this.playSound(PvZSounds.DOOMSHROOMEXPLOSIONEVENT, 1F, 1F);
				this.spawnEffectsCloud();
				this.dead = true;
				this.remove(RemovalReason.DISCARDED);
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
		return ModItems.PERFOOMSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createPerfoomshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 5D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 180);
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

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			this.clearStatusEffects();
			return this.damage(DamageSource.player(playerEntity), 9999.0F);
		} else {
			return false;
		}
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}
}
