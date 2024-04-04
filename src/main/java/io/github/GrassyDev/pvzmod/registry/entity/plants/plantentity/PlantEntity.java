package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.watertile.WaterTile;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.armor.MetalHelmetProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.MetalHelmetVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.PokerVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.bass.BassZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.browncoat.fairytale.PokerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.speakervehicle.SpeakerVehicleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield.MetalShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;

import java.util.ArrayList;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public abstract class PlantEntity extends GolemEntity {

	public boolean onWater;

	public boolean naturalSpawn;

	public boolean isBurst;

	public boolean nocturnal;

	protected boolean dryLand;

	protected int tickDelay;

	public float damageMultiplier = 1;

	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return false;
	}

	protected PlantEntity(EntityType<? extends GolemEntity> entityType, World world) {
		super(entityType, world);
	}

	public void rideLilyPad(LivingEntity livingEntity){
		this.refreshPositionAndAngles(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), livingEntity.bodyYaw, 0.0F);
		this.startRiding(livingEntity);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_ASLEEP, false);
		this.dataTracker.startTracking(COFFEE, false);
		this.dataTracker.startTracking(SHADOW, false);
		this.dataTracker.startTracking(MOON, false);
		this.dataTracker.startTracking(DATA_ALTFIRE, false);
		this.dataTracker.startTracking(DATA_ID_LOWPROF, false);
		this.dataTracker.startTracking(DATA_ID_FIREIMMUNE, false);
		this.dataTracker.startTracking(DATA_ID_IMMUNE, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Asleep", this.getIsAsleep());
		tag.putBoolean("Coffee", this.getCofee());
		tag.putBoolean("Shadow", this.getShadowPowered());
		tag.putBoolean("Moon", this.getMoonPowered());
		tag.putBoolean("AltFire", this.getIsAltFire());
		tag.putBoolean("lowProf", this.getLowProfile());
		tag.putBoolean("fireImmune", this.getFireImmune());
		tag.putBoolean("Immune", this.getImmune());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_ASLEEP, tag.getBoolean("Asleep"));
		this.dataTracker.set(COFFEE, tag.getBoolean("Coffee"));
		this.dataTracker.set(SHADOW, tag.getBoolean("Shadow"));
		this.dataTracker.set(MOON, tag.getBoolean("Moon"));
		this.dataTracker.set(DATA_ALTFIRE, tag.getBoolean("AltFire"));
		this.dataTracker.set(DATA_ID_LOWPROF, tag.getBoolean("lowProf"));
		this.dataTracker.set(DATA_ID_FIREIMMUNE, tag.getBoolean("fireImmune"));
		this.dataTracker.set(DATA_ID_IMMUNE, tag.getBoolean("Immune"));
	}

	@Override
	public void handleStatus(byte status) {
		if (status != 2 && status != 60) {
			super.handleStatus(status);
		}
		if (status == 69) {
			for (int i = 0; i < 16; ++i) {
				double d = this.random.nextDouble() / 2.5 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(0, 3);
				double f = this.random.nextDouble() / 2.5 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + d, this.getY() + 0.5 + e, this.getZ() + f, d, e, f);
			}
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/


	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (PLANT_LOCATION.get(this.getType()).orElse("normal").equals("ground")){
			this.setLowprof(LowProf.TRUE);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}


	//Low Profile Tag

	protected static final TrackedData<Boolean> DATA_ID_LOWPROF =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum LowProf {
		FALSE(false),
		TRUE(true);

		LowProf(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getLowProfile() {
		return this.dataTracker.get(DATA_ID_LOWPROF);
	}

	public void setLowprof(PlantEntity.LowProf lowprof) {
		this.dataTracker.set(DATA_ID_LOWPROF, lowprof.getId());
	}

	// Fire Immune

	protected static final TrackedData<Boolean> DATA_ID_FIREIMMUNE =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum FireImmune {
		FALSE(false),
		TRUE(true);

		FireImmune(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getFireImmune() {
		return this.dataTracker.get(DATA_ID_FIREIMMUNE);
	}

	public void setFireImmune(PlantEntity.FireImmune fireImmune) {
		this.dataTracker.set(DATA_ID_FIREIMMUNE, fireImmune.getId());
	}

	// Immune

	protected static final TrackedData<Boolean> DATA_ID_IMMUNE =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum Immune {
		FALSE(false),
		TRUE(true);

		Immune(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getImmune() {
		return this.dataTracker.get(DATA_ID_IMMUNE);
	}

	public void setImmune(PlantEntity.Immune immune) {
		this.dataTracker.set(DATA_ID_IMMUNE, immune.getId());
	}


	protected static final TrackedData<Boolean> DATA_ID_ASLEEP =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum IsAsleep {
		FALSE(false),
		TRUE(true);

		IsAsleep(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getIsAsleep() {
		return this.dataTracker.get(DATA_ID_ASLEEP);
	}

	public void setIsAsleep(PlantEntity.IsAsleep asleep) {
		this.dataTracker.set(DATA_ID_ASLEEP, asleep.getId());
	}

	protected static final TrackedData<Boolean> SHADOW =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum Shadow {
		FALSE(false),
		TRUE(true);

		Shadow(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getShadowPowered() {
		return this.dataTracker.get(SHADOW);
	}

	public void setShadowPowered(Shadow shadowPowered) {
		this.dataTracker.set(SHADOW, shadowPowered.getId());
	}

	protected static final TrackedData<Boolean> MOON =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum Moon {
		FALSE(false),
		TRUE(true);

		Moon(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getMoonPowered() {
		return this.dataTracker.get(MOON);
	}

	public void setMoonPowered(Moon shadowPowered) {
		this.dataTracker.set(MOON, shadowPowered.getId());
	}

	protected static final TrackedData<Boolean> COFFEE =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum Coffee {
		FALSE(false),
		TRUE(true);

		Coffee(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getCofee() {
		return this.dataTracker.get(COFFEE);
	}

	public void setCoffee(Coffee coffee) {
		this.dataTracker.set(COFFEE, coffee.getId());
	}


	protected static final TrackedData<Boolean> DATA_ALTFIRE =
			DataTracker.registerData(PlantEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum AltFire {
		FALSE(false),
		TRUE(true);

		AltFire(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getIsAltFire() {
		return this.dataTracker.get(DATA_ALTFIRE);
	}

	public void setAltfire(PlantEntity.AltFire fireImmune) {
		this.dataTracker.set(DATA_ALTFIRE, fireImmune.getId());
	}

	/** ----------------------------------------------------------------------- **/

	public boolean targetStrength;
	public boolean lobbedTarget;
	public boolean targetPoison;
	public boolean targetIce;
	public boolean targetHelmet;
	public boolean magnetshroom;
	public boolean magnetoshroom;
	public boolean targetNoHelmet;
	public boolean targetChilled;
	public boolean illuminate;
	public boolean targetMedium;
	public boolean targetNotCovered;
	public boolean targetNotObstacle;
	public boolean noBiggie;

	protected void targetZombies(Vec3d pos, int yDiff, boolean canHitSnorkel, boolean canHitFlying, boolean canHitStealth){
		List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE) + 1));
		int zombieStrength = 0;
		int prioritizedStrength = 0;
		Vec3d prevZombiePosition = Vec3d.ZERO;
		boolean isIced;
		boolean isPoisoned;
		boolean prevIced = false;
		boolean hasHelmet = false;
		boolean hasShield = false;
		boolean prevHelmet = false;
		boolean hasMetalGear = false;
		boolean tooHeavy = true;
		LivingEntity targeted = null;
		LivingEntity prioritizedTarget = null;
		boolean hasZombie = false;
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity instanceof ZombiePropEntity)){
				hasZombie = true;
			}
		}
		if (!this.getWorld().isClient() && !this.hasStatusEffect(DISABLE)) {
			for (LivingEntity hostileEntity : list) {
				if (hostileEntity.isAlive() && this.getVisibilityCache().canSee(hostileEntity) &&
						!(hostileEntity instanceof GraveEntity && targetNotObstacle) &&
						!(hostileEntity instanceof ZombiePropEntity && lobbedTarget && hasZombie)) {
					if (hostileEntity instanceof Monster && !(hostileEntity instanceof GraveEntity graveEntity && graveEntity.decorative)) {
						if (illuminate && hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isStealth() && hostileEntity.squaredDistanceTo(this) < 36) {
							if (PLANT_TYPE.get(this.getType()).orElse("appease").equals("pepper") && this.isWet()) {

							} else {
								generalPvZombieEntity.setStealthTag(GeneralPvZombieEntity.Stealth.FALSE);
							}
						}
						if (!(hostileEntity instanceof ZombiePropEntity && !(hostileEntity instanceof ZombieObstacleEntity))) {
							if (hasHelmet) {
								prevHelmet = true;
								hasHelmet = false;
							}
							if (hasShield) {
								prevHelmet = true;
								hasShield = false;
							}
							for (Entity zombiePropEntity : hostileEntity.getPassengerList()) {
								if (zombiePropEntity instanceof ZombiePropEntity && !(zombiePropEntity instanceof ZombieShieldEntity)) {
									hasHelmet = true;
								}
								if (zombiePropEntity instanceof ZombieShieldEntity) {
									hasShield = true;
								}
								if (zombiePropEntity instanceof MetalHelmetEntity || zombiePropEntity instanceof MetalShieldEntity) {
									hasMetalGear = true;
								}
								if (zombiePropEntity instanceof ZombiePropEntity zombiePropEntity1 && !zombiePropEntity1.isHeavy) {
									tooHeavy = false;
								}
							}
							if (this.noBiggie && (ZOMBIE_SIZE.get(hostileEntity.getType()).orElse("medium").equals("big") || ZOMBIE_SIZE.get(hostileEntity.getType()).orElse("medium").equals("gargantuar"))) {

							} else if (magnetshroom) {
								for (Entity zombiePropEntity : hostileEntity.getPassengerList()) {
									if ((zombiePropEntity instanceof MetalHelmetEntity || zombiePropEntity instanceof MetalShieldEntity) &&
											zombiePropEntity instanceof ZombiePropEntity zombiePropEntity1 && !zombiePropEntity1.isHeavy) {
										if (hostileEntity.squaredDistanceTo(pos) <= Math.pow(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE), 2) &&
												(hostileEntity.getY() < (this.getY() + yDiff) && hostileEntity.getY() > (this.getY() - yDiff)) && hostileEntity.isAlive()) {
											if (hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
													!(generalPvZombieEntity.getHypno())) {
												int currentStrength = ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0);
												if (zombieStrength < currentStrength) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												}
											}
										}
									}
								}
								if (hostileEntity instanceof BassZombieEntity bassZombieEntity && bassZombieEntity.hasVehicle() && bassZombieEntity.getVehicle() instanceof SpeakerVehicleEntity){
									int currentStrength = ZOMBIE_STRENGTH.get(bassZombieEntity.getType()).orElse(0);
									if (zombieStrength < currentStrength) {
										zombieStrength = currentStrength;
										prevZombiePosition = hostileEntity.getPos();
										targeted = hostileEntity;
										prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
									}
								}
								if (hostileEntity instanceof ImpEntity impEntity && impEntity.getType().equals(PvZEntity.SCRAPIMP)){
									int currentStrength = ZOMBIE_STRENGTH.get(impEntity.getType()).orElse(0);
									if (zombieStrength < currentStrength) {
										zombieStrength = currentStrength;
										prevZombiePosition = hostileEntity.getPos();
										targeted = hostileEntity;
										prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
									}
								}
							} else if (magnetoshroom) {
								for (Entity zombiePropEntity : hostileEntity.getPassengerList()) {
									if ((zombiePropEntity instanceof MetalHelmetEntity || zombiePropEntity instanceof MetalShieldEntity)) {
										if (hostileEntity.squaredDistanceTo(pos) <= Math.pow(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE), 2) &&
												(hostileEntity.getY() < (this.getY() + yDiff) && hostileEntity.getY() > (this.getY() - yDiff)) && hostileEntity.isAlive()) {
											if (hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
													!(generalPvZombieEntity.getHypno())) {
												int currentStrength = ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0);
												if (zombieStrength < currentStrength) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												}
											}
										}
									}
								}
								if (hostileEntity instanceof BassZombieEntity bassZombieEntity && bassZombieEntity.hasVehicle() && bassZombieEntity.getVehicle() instanceof SpeakerVehicleEntity){
									int currentStrength = ZOMBIE_STRENGTH.get(bassZombieEntity.getType()).orElse(0);
									if (zombieStrength < currentStrength) {
										zombieStrength = currentStrength;
										prevZombiePosition = hostileEntity.getPos();
										targeted = hostileEntity;
										prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
									}
								}
								if (hostileEntity instanceof ImpEntity impEntity && impEntity.getType().equals(PvZEntity.SCRAPIMP)){
									int currentStrength = ZOMBIE_STRENGTH.get(impEntity.getType()).orElse(0);
									if (zombieStrength < currentStrength) {
										zombieStrength = currentStrength;
										prevZombiePosition = hostileEntity.getPos();
										targeted = hostileEntity;
										prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
									}
								}
							} else if (hostileEntity.squaredDistanceTo(pos) <= Math.pow(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE), 2) &&
									(hostileEntity.getY() < (this.getY() + yDiff) && hostileEntity.getY() > (this.getY() - yDiff)) && hostileEntity.isAlive()) {
								if (hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
										!(generalPvZombieEntity.getHypno())) {
									int currentStrength = ZOMBIE_STRENGTH.get(generalPvZombieEntity.getType()).orElse(0);
									if (!(!ZOMBIE_SIZE.get(hostileEntity.getType()).orElse("medium").equals("medium") && targetMedium) &&
											!(generalPvZombieEntity.isCovered() && targetNotCovered) &&
											!(generalPvZombieEntity instanceof ZombieVehicleEntity && targetNotCovered) &&
											!(generalPvZombieEntity instanceof ZombieObstacleEntity && targetNotObstacle) &&
											!(generalPvZombieEntity instanceof ZombieVehicleEntity && targetNotObstacle)) {
										isIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
										isPoisoned = hostileEntity.hasStatusEffect(PvZCubed.PVZPOISON);
										if (zombieStrength < currentStrength && this.targetStrength) {
											if (canHitFlying && generalPvZombieEntity.hasPassengers() && generalPvZombieEntity.getFirstPassenger() instanceof ZombieRidersEntity zombieRiderEntity){
												zombieStrength = currentStrength;
												prevZombiePosition = zombieRiderEntity.getPos();
												targeted = zombieRiderEntity;
												prevIced = zombieRiderEntity.hasStatusEffect(PvZCubed.ICE) || zombieRiderEntity.hasStatusEffect(PvZCubed.FROZEN);
											}
											else if (canHitFlying && generalPvZombieEntity.isFlying()) {
												zombieStrength = currentStrength;
												prevZombiePosition = hostileEntity.getPos();
												targeted = hostileEntity;
												prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													}
												}
											}
										} else if ((zombieStrength == currentStrength || !this.targetStrength) &&
												this.squaredDistanceTo(prevZombiePosition) > this.squaredDistanceTo(hostileEntity.getPos())) {
											if (!(targetChilled && prevIced && !isIced)) {
												if (canHitFlying && generalPvZombieEntity.hasPassengers() && generalPvZombieEntity.getFirstPassenger() instanceof ZombieRidersEntity zombieRiderEntity){
													zombieStrength = currentStrength;
													prevZombiePosition = zombieRiderEntity.getPos();
													targeted = zombieRiderEntity;
													prevIced = zombieRiderEntity.hasStatusEffect(PvZCubed.ICE) || zombieRiderEntity.hasStatusEffect(PvZCubed.FROZEN);
												}
												else if (canHitFlying && generalPvZombieEntity.isFlying()) {
													zombieStrength = currentStrength;
													prevZombiePosition = hostileEntity.getPos();
													targeted = hostileEntity;
													prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
												} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														}
													}
												} else if (canHitFlying) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														zombieStrength = currentStrength;
														prevZombiePosition = hostileEntity.getPos();
														targeted = hostileEntity;
														prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															zombieStrength = currentStrength;
															prevZombiePosition = hostileEntity.getPos();
															targeted = hostileEntity;
															prevIced = hostileEntity.hasStatusEffect(PvZCubed.ICE) || hostileEntity.hasStatusEffect(PvZCubed.FROZEN);
														}
													}
												}
											}
										}
										if (prioritizedTarget != null && targetIce && this.squaredDistanceTo(prioritizedTarget) > this.squaredDistanceTo(hostileEntity.getPos())) {
											if (targetIce && !isIced) {
												if (canHitFlying && generalPvZombieEntity.hasPassengers() && generalPvZombieEntity.getFirstPassenger() instanceof ZombieRidersEntity zombieRiderEntity){
													prioritizedTarget = zombieRiderEntity;
												}
												else if (canHitFlying && generalPvZombieEntity.isFlying()) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														prioritizedTarget = hostileEntity;
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															prioritizedTarget = hostileEntity;
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														}
													}
												} else if (canHitFlying) {
													if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
														prioritizedTarget = hostileEntity;
													} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
															!(generalPvZombieEntity instanceof SnorkelEntity)) {
														if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
																generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
															prioritizedTarget = hostileEntity;
														} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
															prioritizedTarget = hostileEntity;
														}
													}
												}
											}
										} else if (targetIce && !isIced && prioritizedTarget == null ) {
											if (canHitFlying && generalPvZombieEntity.hasPassengers() && generalPvZombieEntity.getFirstPassenger() instanceof ZombieRidersEntity zombieRiderEntity){
												prioritizedTarget = zombieRiderEntity;
											}
											else if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
										if (targetPoison && !isPoisoned) {
											if (canHitFlying && generalPvZombieEntity.hasPassengers() && generalPvZombieEntity.getFirstPassenger() instanceof ZombieRidersEntity zombieRiderEntity){
												prioritizedTarget = zombieRiderEntity;
											}
											else if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
										if (targetNoHelmet && hasHelmet && prioritizedTarget == hostileEntity) {
											prioritizedTarget = null;
										}
										if (targetNoHelmet && !(generalPvZombieEntity instanceof ZombiePropEntity) && !hasHelmet && prioritizedStrength < currentStrength) {
											if (canHitFlying && generalPvZombieEntity.hasPassengers() && generalPvZombieEntity.getFirstPassenger() instanceof ZombieRidersEntity zombieRiderEntity){
												prioritizedStrength = currentStrength;
												prioritizedTarget = zombieRiderEntity;
											}
											else if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedStrength = currentStrength;
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
										if (targetHelmet && hasHelmet && prioritizedTarget == hostileEntity) {
											prioritizedTarget = null;
										}
										if (targetHelmet && hasHelmet && prioritizedStrength < currentStrength) {
											if (canHitFlying && generalPvZombieEntity.hasPassengers() && generalPvZombieEntity.getFirstPassenger() instanceof ZombieRidersEntity zombieRiderEntity){
												prioritizedStrength = currentStrength;
												prioritizedTarget = zombieRiderEntity;
											}
											else if (canHitFlying && generalPvZombieEntity.isFlying()) {
												prioritizedStrength = currentStrength;
												prioritizedTarget = hostileEntity;
											} else if (!canHitFlying && !generalPvZombieEntity.isFlying()) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													}
												}
											} else if (canHitFlying) {
												if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (canHitSnorkel && generalPvZombieEntity instanceof SnorkelEntity snorkelEntity) {
													prioritizedStrength = currentStrength;
													prioritizedTarget = hostileEntity;
												} else if (!canHitSnorkel && (generalPvZombieEntity instanceof SnorkelEntity snorkelEntity && !snorkelEntity.isInvisibleSnorkel()) ||
														!(generalPvZombieEntity instanceof SnorkelEntity)) {
													if ((canHitStealth && generalPvZombieEntity.isStealth()) ||
															generalPvZombieEntity.isStealth() && this.squaredDistanceTo(generalPvZombieEntity) <= 4) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													} else if (!canHitStealth && !generalPvZombieEntity.isStealth()) {
														prioritizedStrength = currentStrength;
														prioritizedTarget = hostileEntity;
													}
												}
											}
										}
									}
									if (targeted == null && prioritizedTarget == null && !(hostileEntity instanceof GeneralPvZombieEntity)) {
										targeted = hostileEntity;
									}
								} else if (!(hostileEntity instanceof GeneralPvZombieEntity) && targeted == null && !this.naturalSpawn) {
									targeted = hostileEntity;
								}
							}
						}
					}
				}
			}
			if (prioritizedTarget != null){
				this.setTarget(prioritizedTarget);
			}
			else {
				this.setTarget(targeted);
			}
		}
	}

	protected int heatTicks = 40;

	protected boolean onWaterTile = false;

    public void tick() {
		if (tickDelay <= -1){
			tickDelay = 5;
		}
		if (this.getFireImmune()){
			this.setFireTicks(0);
		}

		List<WaterTile> waterTiles = getWorld().getNonSpectatingEntities(WaterTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		for (WaterTile waterTile : waterTiles) {
			this.onWaterTile = true;
		}
		if (this.hasStatusEffect(DISABLE)){
			this.setTarget(null);
		}
		super.tick();
		if (this.getCofee()){
			this.setIsAsleep(IsAsleep.FALSE);
			double random = Math.random();
			if (random <= 0.05) {
				for (int i = 0; i < 1; ++i) {
					double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
					double e = this.random.nextDouble() * this.random.range(0, 2);
					double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
					this.getWorld().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getX() + d, this.getY() + e, this.getZ() + f, d, e + 0.1, f);
				}
			}
		}
		Entity vehicle = this.getVehicle();
		if (vehicle instanceof PlantEntity){
			vehicle.setBodyYaw(this.bodyYaw);
		}
		if (PLANT_TYPE.get(this.getType()).orElse("appease").equals("pepper") && !this.isWet()){
			if (--heatTicks <= 0) {
				List<TileEntity> list = getWorld().getNonSpectatingEntities(TileEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(1.5));
				for (TileEntity tileEntity : list) {
					if (tileEntity instanceof SnowTile) {
						tileEntity.discard();
					}
					boolean waterTile = false;
					if (tileEntity instanceof WaterTile waterTile2) {
						List<LivingEntity> list2 = getWorld().getNonSpectatingEntities(LivingEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(waterTile2.getX(), waterTile2.getY(), waterTile2.getZ()));
						for (LivingEntity livingEntity : list2){
							if (livingEntity.squaredDistanceTo(waterTile2) <= 0.5 && livingEntity != waterTile2){
								waterTile = true;
							}
						}
						if (!waterTile) {
							tileEntity.discard();
						}
					}
					if (tileEntity instanceof OilTile oilTile) {
						oilTile.makeFireTrail(oilTile.getBlockPos());
					}
				}
				heatTicks = 40;
			}
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		for (Entity entity : this.getPassengerList()){
			if (this.hasVehicle()){
				entity.startRiding(this.getVehicle(), true);
				if (entity instanceof PlantEntity plantEntity){
					plantEntity.tickDelay = 20;
				}
			}
		}
		if (!PLANT_LOCATION.get(this.getType()).orElse("normal").equals("flying")){
			RandomGenerator randomGenerator = this.getRandom();
			BlockState blockState = this.getLandingBlockState();
			for (int i = 0; i < 4; ++i) {
				double d = this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
				double e = this.getY() + 0.3;
				double f = this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.4F, 0.4F);
				this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
			}
		}
		super.onDeath(source);
		super.discard();
	}

	@Override
	protected void dropLoot(DamageSource source, boolean causedByPlayer) {
		if (this.getWorld().getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_DROP)){
			super.dropLoot(source, causedByPlayer);
		}
	}

	public void magnetize(){
		LivingEntity livingEntity = this.getTarget();
		LivingEntity livingEntity2 = null;
		LivingEntity livingEntity3 = null;
		MetalHelmetVariants helmetProj = MetalHelmetVariants.BUCKET;
		MetalHelmetVariants helmetProj2 = MetalHelmetVariants.BUCKET;
		MetalHelmetVariants helmetProj3 = MetalHelmetVariants.BUCKET;
		ZombiePropEntity setGear = null;
		ZombiePropEntity setGear2 = null;
		ZombiePropEntity setGear3 = null;

		if (livingEntity != null){
			List<LivingEntity> magnetList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, livingEntity.getBoundingBox().expand(2));
			EntityType<?> entityType = null;
			EntityType<?> entityType2 = null;
			EntityType<?> entityType3 = null;
			for (Entity entity : livingEntity.getPassengerList()){
				if (entity instanceof ZombiePropEntity zombiePropEntity) {
					if (entity instanceof MetalShieldEntity metalShieldEntity || entity instanceof MetalHelmetEntity metalHelmetEntity) {
						setGear = (ZombiePropEntity) entity;
						break;
					}
				}
			}
			if (livingEntity instanceof BassZombieEntity bassZombieEntity && bassZombieEntity.hasVehicle() && bassZombieEntity.getVehicle() instanceof SpeakerVehicleEntity){
				setGear = new MetalHelmetEntity(PvZEntity.BASSGEAR, this.getWorld());
			}
			if (livingEntity instanceof ImpEntity impEntity && impEntity.getType().equals(PvZEntity.SCRAPIMP)){
				setGear = new MetalHelmetEntity(PvZEntity.SCRAPIMPGEAR, this.getWorld());
				setGear.setHealth(impEntity.getHealth());
			}
			if (magnetoshroom) {
				for (Entity entity : magnetList) {
					if (entity instanceof ZombiePropEntity zombiePropEntity && entity.squaredDistanceTo(livingEntity) <= 4 && entity != setGear) {
						if (entity instanceof MetalShieldEntity metalShieldEntity || entity instanceof MetalHelmetEntity metalHelmetEntity) {
							if (setGear2 == null) {
								setGear2 = (ZombiePropEntity) entity;
								livingEntity2 = (LivingEntity) entity.getVehicle();
							} else if (setGear3 == null) {
								setGear3 = (ZombiePropEntity) entity;
								livingEntity3 = (LivingEntity) entity.getVehicle();
							}
						}
					}
					if (entity instanceof BassZombieEntity bassZombieEntity && bassZombieEntity.hasVehicle() && bassZombieEntity.getVehicle() instanceof SpeakerVehicleEntity
							&& entity.squaredDistanceTo(livingEntity) <= 4 && entity != livingEntity){
						if (setGear2 == null) {
							setGear2 = new MetalHelmetEntity(PvZEntity.BASSGEAR, this.getWorld());
							livingEntity2 = (LivingEntity) entity;
						} else if (setGear3 == null) {
							setGear3 = new MetalHelmetEntity(PvZEntity.BASSGEAR, this.getWorld());
							livingEntity3 = (LivingEntity) entity;
						}
					}
					if (entity.getType().equals(PvZEntity.SCRAPIMP) && entity.squaredDistanceTo(livingEntity) <= 4 && entity != livingEntity){
						if (setGear2 == null) {
							setGear2 = new MetalHelmetEntity(PvZEntity.SCRAPIMPGEAR, this.getWorld());
							livingEntity2 = (LivingEntity) entity;
							setGear2.setHealth(livingEntity2.getHealth());
						} else if (setGear3 == null) {
							setGear3 = new MetalHelmetEntity(PvZEntity.SCRAPIMPGEAR, this.getWorld());
							livingEntity3 = (LivingEntity) entity;
							setGear3.setHealth(livingEntity3.getHealth());
						}
					}
				}
			}
			if (setGear != null) {
				entityType = setGear.getType();
				if (entityType.equals(PvZEntity.BUCKETGEAR)) {
					if (livingEntity.getType().equals(PvZEntity.PEASANTBUCKET)) {
						helmetProj = MetalHelmetVariants.PEASANTBUCKET;
					} else if (livingEntity.getType().equals(PvZEntity.MUMMYBUCKET)) {
						helmetProj = MetalHelmetVariants.MUMMYBUCKET;
					} else if (livingEntity.getType().equals(PvZEntity.SUMMERBUCKETHEAD)) {
						helmetProj = MetalHelmetVariants.SUMMERBUCKET;
					} else if (livingEntity.getType().equals(PvZEntity.FUTUREBUCKET)) {
						helmetProj = MetalHelmetVariants.FUTUREBUCKET;
					} else if (livingEntity.getType().equals(PvZEntity.POKERBUCKET) && livingEntity instanceof PokerEntity pokerEntity) {
						if (pokerEntity.getPoker().equals(PokerVariants.HEART) || pokerEntity.getPoker().equals(PokerVariants.DIAMOND)){
							helmetProj = MetalHelmetVariants.POKERLIDRED;
						}
						else {
							helmetProj = MetalHelmetVariants.POKERLIDBLACK;
						}
					} else {
						helmetProj = MetalHelmetVariants.BUCKET;
					}
				} else if (entityType.equals(PvZEntity.SCREENDOORSHIELD)) {
					helmetProj = MetalHelmetVariants.SCREENDOOR;
				} else if (entityType.equals(PvZEntity.SERGEANTSHIELDGEAR)) {
					helmetProj = MetalHelmetVariants.SARGEAMTSHIELD;
				} else if (entityType.equals(PvZEntity.FOOTBALLGEAR)) {
					helmetProj = MetalHelmetVariants.FOOTBALL;
				} else if (entityType.equals(PvZEntity.BERSERKERGEAR)) {
					helmetProj = MetalHelmetVariants.BERSERKER;
				} else if (entityType.equals(PvZEntity.DEFENSIVEENDGEAR)) {
					helmetProj = MetalHelmetVariants.DEFENSIVEEND;
				} else if (entityType.equals(PvZEntity.TRASHCANBIN)) {
					helmetProj = MetalHelmetVariants.TRASHCAN;
				} else if (entityType.equals(PvZEntity.BLASTRONAUTGEAR)) {
					helmetProj = MetalHelmetVariants.BLASTRONAUT ;
				} else if (entityType.equals(PvZEntity.KNIGHTGEAR)) {
					helmetProj = MetalHelmetVariants.KNIGHT;
				} else if (entityType.equals(PvZEntity.MEDALLIONGEAR)) {
					helmetProj = MetalHelmetVariants.MEDALLION;
				} else if (entityType.equals(PvZEntity.HELMETGEAR)) {
					helmetProj = MetalHelmetVariants.SERGEANTHELMET;
				} else if (entityType.equals(PvZEntity.SOLDIERGEAR)) {
					helmetProj = MetalHelmetVariants.SOLDIERHELMET;
				} else if (entityType.equals(PvZEntity.BASSGEAR)) {
					helmetProj = MetalHelmetVariants.BASSPROP;
				} else if (entityType.equals(PvZEntity.SCRAPIMPGEAR)) {
					helmetProj = MetalHelmetVariants.SCRAPIMP;
				}
				if (magnetoshroom) {
					if (setGear2 != null) {
						entityType2 = setGear2.getType();
						if (entityType2.equals(PvZEntity.BUCKETGEAR)) {
							if (livingEntity2 != null) {
								if (livingEntity2.getType().equals(PvZEntity.PEASANTBUCKET)) {
									helmetProj2 = MetalHelmetVariants.PEASANTBUCKET;
								} else if (livingEntity2.getType().equals(PvZEntity.MUMMYBUCKET)) {
									helmetProj2 = MetalHelmetVariants.MUMMYBUCKET;
								} else if (livingEntity2.getType().equals(PvZEntity.SUMMERBUCKETHEAD)) {
									helmetProj2 = MetalHelmetVariants.SUMMERBUCKET;
								} else if (livingEntity2.getType().equals(PvZEntity.FUTUREBUCKET)) {
									helmetProj2 = MetalHelmetVariants.FUTUREBUCKET;
								} else if (livingEntity2.getType().equals(PvZEntity.POKERBUCKET) && livingEntity2 instanceof PokerEntity pokerEntity) {
									if (pokerEntity.getPoker().equals(PokerVariants.HEART) || pokerEntity.getPoker().equals(PokerVariants.DIAMOND)){
										helmetProj2 = MetalHelmetVariants.POKERLIDRED;
									}
									else {
										helmetProj2 = MetalHelmetVariants.POKERLIDBLACK;
									}
								} else {
									helmetProj2 = MetalHelmetVariants.BUCKET;
								}
							}
						} else if (entityType2.equals(PvZEntity.SCREENDOORSHIELD)) {
							helmetProj2 = MetalHelmetVariants.SCREENDOOR;
						} else if (entityType2.equals(PvZEntity.SERGEANTSHIELDGEAR)) {
							helmetProj2 = MetalHelmetVariants.SARGEAMTSHIELD;
						} else if (entityType2.equals(PvZEntity.FOOTBALLGEAR)) {
							helmetProj2 = MetalHelmetVariants.FOOTBALL;
						} else if (entityType2.equals(PvZEntity.BERSERKERGEAR)) {
							helmetProj2 = MetalHelmetVariants.BERSERKER;
						} else if (entityType2.equals(PvZEntity.DEFENSIVEENDGEAR)) {
							helmetProj2 = MetalHelmetVariants.DEFENSIVEEND;
						} else if (entityType2.equals(PvZEntity.TRASHCANBIN)) {
							helmetProj2 = MetalHelmetVariants.TRASHCAN;
						} else if (entityType2.equals(PvZEntity.BLASTRONAUTGEAR)) {
							helmetProj2 = MetalHelmetVariants.BERSERKER;
						} else if (entityType2.equals(PvZEntity.KNIGHTGEAR)) {
							helmetProj2 = MetalHelmetVariants.KNIGHT;
						} else if (entityType2.equals(PvZEntity.MEDALLIONGEAR)) {
							helmetProj2 = MetalHelmetVariants.MEDALLION;
						} else if (entityType2.equals(PvZEntity.HELMETGEAR)) {
							helmetProj2 = MetalHelmetVariants.SERGEANTHELMET;
						} else if (entityType2.equals(PvZEntity.SOLDIERGEAR)) {
							helmetProj2 = MetalHelmetVariants.SOLDIERHELMET;
						} else if (entityType2.equals(PvZEntity.BASSGEAR)) {
							helmetProj2 = MetalHelmetVariants.BASSPROP;
						} else if (entityType2.equals(PvZEntity.SCRAPIMPGEAR)) {
							helmetProj2 = MetalHelmetVariants.SCRAPIMP;
						}
					}
					if (setGear3 != null) {
						entityType3 = setGear3.getType();
						if (entityType3.equals(PvZEntity.BUCKETGEAR)) {
							if (livingEntity3 != null) {
								if (livingEntity3.getType().equals(PvZEntity.PEASANTBUCKET)) {
									helmetProj3 = MetalHelmetVariants.PEASANTBUCKET;
								} else if (livingEntity3.getType().equals(PvZEntity.MUMMYBUCKET)) {
									helmetProj3 = MetalHelmetVariants.MUMMYBUCKET;
								} else if (livingEntity3.getType().equals(PvZEntity.SUMMERBUCKETHEAD)) {
									helmetProj3 = MetalHelmetVariants.SUMMERBUCKET;
								} else if (livingEntity3.getType().equals(PvZEntity.FUTUREBUCKET)) {
									helmetProj3 = MetalHelmetVariants.FUTUREBUCKET;
								} else if (livingEntity3.getType().equals(PvZEntity.POKERBUCKET) && livingEntity3 instanceof PokerEntity pokerEntity) {
									if (pokerEntity.getPoker().equals(PokerVariants.HEART) || pokerEntity.getPoker().equals(PokerVariants.DIAMOND)){
										helmetProj3 = MetalHelmetVariants.POKERLIDRED;
									}
									else {
										helmetProj3 = MetalHelmetVariants.POKERLIDBLACK;
									}
								} else {
									helmetProj3 = MetalHelmetVariants.BUCKET;
								}
							}
						} else if (entityType3.equals(PvZEntity.SCREENDOORSHIELD)) {
							helmetProj3 = MetalHelmetVariants.SCREENDOOR;
						}  else if (entityType3.equals(PvZEntity.SERGEANTSHIELDGEAR)) {
							helmetProj3 = MetalHelmetVariants.SARGEAMTSHIELD;
						} else if (entityType3.equals(PvZEntity.FOOTBALLGEAR)) {
							helmetProj3 = MetalHelmetVariants.FOOTBALL;
						} else if (entityType3.equals(PvZEntity.BERSERKERGEAR)) {
							helmetProj3 = MetalHelmetVariants.BERSERKER;
						} else if (entityType3.equals(PvZEntity.DEFENSIVEENDGEAR)) {
							helmetProj3 = MetalHelmetVariants.DEFENSIVEEND;
						} else if (entityType3.equals(PvZEntity.TRASHCANBIN)) {
							helmetProj3 = MetalHelmetVariants.TRASHCAN;
						} else if (entityType3.equals(PvZEntity.BLASTRONAUTGEAR)) {
							helmetProj3 = MetalHelmetVariants.BERSERKER;
						} else if (entityType3.equals(PvZEntity.KNIGHTGEAR)) {
							helmetProj3 = MetalHelmetVariants.KNIGHT;
						} else if (entityType3.equals(PvZEntity.MEDALLIONGEAR)) {
							helmetProj3 = MetalHelmetVariants.MEDALLION;
						} else if (entityType3.equals(PvZEntity.HELMETGEAR)) {
							helmetProj3 = MetalHelmetVariants.SERGEANTHELMET;
						} else if (entityType3.equals(PvZEntity.SOLDIERGEAR)) {
							helmetProj3 = MetalHelmetVariants.SOLDIERHELMET;
						} else if (entityType3.equals(PvZEntity.BASSGEAR)) {
							helmetProj3 = MetalHelmetVariants.BASSPROP;
						} else if (entityType3.equals(PvZEntity.SCRAPIMPGEAR)) {
							helmetProj3 = MetalHelmetVariants.SCRAPIMP;
						}
					}
				}
			}
		}
		if (setGear != null && !(this.magnetshroom && setGear.isHeavy)) {
			playSound(PvZSounds.MAGNETATTRACTEVENT);
			MetalHelmetProjEntity helmetProjEntity = (MetalHelmetProjEntity) PvZEntity.METALHELMETPROJ.create(getWorld());
			helmetProjEntity.setOwner(this);
			if (this.getType().equals(PvZEntity.MAGNETOSHROOM)) {
				helmetProjEntity.setMaxAge(150);
			}
			else {
				helmetProjEntity.setMaxAge(200);
			}
			Vec3d vec3d = new Vec3d((double) magnetOffsetX, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			helmetProjEntity.refreshPositionAndAngles(this.getX() + vec3d.getX(), this.getY() + vec3d.getY() + magnetOffsetY, this.getZ() + vec3d.getZ(), 0, 0);
			helmetProjEntity.setVariant(helmetProj);
			helmetProjEntity.setDamage(setGear.getHealth());
			helmetProjEntity.setMaxHealth(setGear.getMaxHealth());
			setGear.discard();
			if (livingEntity instanceof BassZombieEntity){
				livingEntity.dismountVehicle();
			}
			if (livingEntity.getType().equals(PvZEntity.SCRAPIMP)){
				livingEntity.kill();
			}
			((ServerWorld) this.getWorld()).spawnEntityAndPassengers(helmetProjEntity);
		}
		if (magnetoshroom) {
			if (setGear2 != null) {
				MetalHelmetProjEntity helmetProjEntity = (MetalHelmetProjEntity) PvZEntity.METALHELMETPROJ.create(getWorld());
				helmetProjEntity.setOwner(this);
				if (this.getType().equals(PvZEntity.MAGNETOSHROOM)) {
					helmetProjEntity.setMaxAge(150);
				}
				else {
					helmetProjEntity.setMaxAge(200);
				}
				Vec3d vec3d = new Vec3d((double) magnetOffsetX, 0, 0.5).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				helmetProjEntity.refreshPositionAndAngles(this.getX() + vec3d.getX(), this.getY() + vec3d.getY() + magnetOffsetY, this.getZ() + vec3d.getZ(), 0, 0);
				helmetProjEntity.setVariant(helmetProj2);
				helmetProjEntity.setDamage(setGear2.getHealth());
				helmetProjEntity.setMaxHealth(setGear2.getMaxHealth());
				setGear2.discard();
				if (livingEntity2 instanceof BassZombieEntity){
					livingEntity2.dismountVehicle();
				}
				if (livingEntity2.getType().equals(PvZEntity.SCRAPIMP)){
					livingEntity2.kill();
				}
				((ServerWorld) this.getWorld()).spawnEntityAndPassengers(helmetProjEntity);
			}
			if (setGear3 != null) {
				MetalHelmetProjEntity helmetProjEntity = (MetalHelmetProjEntity) PvZEntity.METALHELMETPROJ.create(getWorld());
				helmetProjEntity.setOwner(this);
				if (this.getType().equals(PvZEntity.MAGNETOSHROOM)) {
					helmetProjEntity.setMaxAge(150);
				}
				else {
					helmetProjEntity.setMaxAge(200);
				}
				Vec3d vec3d = new Vec3d((double) magnetOffsetX, 0, -0.5).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				helmetProjEntity.refreshPositionAndAngles(this.getX() + vec3d.getX(), this.getY() + vec3d.getY() + magnetOffsetY, this.getZ() + vec3d.getZ(), 0, 0);
				helmetProjEntity.setVariant(helmetProj3);
				helmetProjEntity.setDamage(setGear3.getHealth());
				helmetProjEntity.setMaxHealth(setGear3.getMaxHealth());
				setGear3.discard();
				if (livingEntity3 instanceof BassZombieEntity){
					livingEntity3.dismountVehicle();
				}
				if (livingEntity3.getType().equals(PvZEntity.SCRAPIMP)){
					livingEntity3.kill();
				}
				((ServerWorld) this.getWorld()).spawnEntityAndPassengers(helmetProjEntity);
			}
		}
	}

	protected float magnetOffsetY = 1.5f;
	protected float magnetOffsetX = 0f;

	public HitResult amphibiousRaycast(double maxDistance) {
		Vec3d vec3d1 = this.getPos();
		Vec3d vec3d2 = new Vec3d(vec3d1.x, vec3d1.y - maxDistance, vec3d1.z);
		return this.getWorld().raycast(new RaycastContext(vec3d1, vec3d2, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.ANY, this));
	}

	public static boolean checkPlant(Vec3d pos, ServerWorldAccess world, EntityType<?> type) {
		List<PlantEntity> list = world.getNonSpectatingEntities(PlantEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(pos).expand(20));
		List<PlantEntity> list1 = new ArrayList<>();
		for (PlantEntity plantEntity : list){
			if (plantEntity.getType() != type){
				list1.add(plantEntity);
			}
		}
		return !list1.isEmpty();
	}

	public static class PlantData implements EntityData {
		public final boolean tryLilyPad;

		public PlantData(boolean tryLilyPad) {
			this.tryLilyPad = tryLilyPad;
		}
	}


	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (source.getSource() instanceof GeneralPvZombieEntity generalPvZombieEntity){
			super.applyDamage(source, amount * generalPvZombieEntity.damageMultiplier);
		}
		else if (source.getSource() instanceof PlayerEntity){
			super.applyDamage(source, Integer.MAX_VALUE);
		}
		else {
			super.applyDamage(source, amount);
		}
	}


	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	public abstract static class VineEntity extends PlantEntity implements GeoAnimatable {

		public VineEntity(EntityType<? extends VineEntity> entityType, World world) {
			super(entityType, world);
		}

		static {
		}

		@Environment(EnvType.CLIENT)
		public void handleStatus(byte status) {
			if (status != 2 && status != 60){
				super.handleStatus(status);
			}
		}

		protected void initDataTracker() {
			super.initDataTracker();

		}
		public void readCustomDataFromNbt(NbtCompound tag) {
			super.readCustomDataFromNbt(tag);

		}

		public void writeCustomDataToNbt(NbtCompound tag) {
			super.writeCustomDataToNbt(tag);

		}

		static {
		}

		/** /~*~//~*VARIANTS*~//~*~/ **/

		public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
									 SpawnReason spawnReason, @Nullable EntityData entityData,
									 @Nullable NbtCompound entityNbt) {
			return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
		}


		/** /~*~//~*TICKING*~//~*~/ **/

		public void tick() {
			super.tick();
		}


		/** /~*~//~*ATTRIBUTES*~//~*~/ **/

		protected boolean canAddPassenger(Entity passenger) {
			return this.getPassengerList().size() < this.getMaxPassengers() && !this.isSubmergedIn(FluidTags.WATER);
		}

		protected int getMaxPassengers() {
			return 1;
		}

		protected boolean canClimb() {
			return false;
		}

		public boolean collides() {
			return !this.isRemoved();
		}

		protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
			return 0.075F;
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

		public boolean isCollidable() {
			return false;
		}

		public boolean isPushable() {
			return false;
		}

		protected void pushAway(Entity entity) {
		}

		@Override
		public double getMountedHeightOffset() {
			return 0;
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
}
