package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.ghostpepper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowFullTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.watertile.WaterTile;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
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
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
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

import java.util.Iterator;
import java.util.List;

public class GhostpepperEntity extends PlantEntity implements GeoEntity {

	private int amphibiousRaycastDelay;
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "ghostpepper";

    public GhostpepperEntity(EntityType<? extends GhostpepperEntity> entityType, World world) {
        super(entityType, world);

		amphibiousRaycastDelay = 1;

		this.targetHelmet = true;
		this.targetNotObstacle = true;
		this.setNoGravity(true);
		this.setImmune(Immune.TRUE);
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FRONTX, 0);
		this.dataTracker.startTracking(FRONTZ, 0);
		this.dataTracker.startTracking(BACKX, 0);
		this.dataTracker.startTracking(BACKZ, 0);
		this.dataTracker.startTracking(LIFETIME, 160);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("FX", this.getFX());
		tag.putInt("FZ", this.getFZ());
		tag.putInt("BX", this.getBX());
		tag.putInt("BZ", this.getBZ());
		tag.putInt("Lifetime", this.getLiftime());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(FRONTX, tag.getInt("FX"));
		this.dataTracker.set(FRONTZ, tag.getInt("FZ"));
		this.dataTracker.set(BACKX, tag.getInt("BX"));
		this.dataTracker.set(LIFETIME, tag.getInt("Lifetime"));
	}

	public GhostpepperEntity(World world, double x, double y, double z) {
		this(PvZEntity.GHOSTPEPPER, world);
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
		if (status == 101){
			--this.transTick;
		}
		if (status == 102) {
			this.transTick = 12;
		}
		if (status == 103){
			this.attacking = true;
		}
		if (status == 104) {
			this.attacking = false;
		}
		if (status == 106) {
			double shadow = 1;
			if (this.getShadowPowered()){
				shadow = 2;
			}
			for(int i = 0; i < 3; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double d = 0;
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = 0;
				this.getWorld().addParticle(ParticleTypes.SOUL, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SCULK_SOUL, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
			}
			for(int i = 0; i < 4; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1));
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow,
						this.getY() + (this.random.range(-1, 1)),
						this.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
		if (status == 107) {
			double shadow = 1;
			if (this.getShadowPowered()){
				shadow = 2;
			}
			for(int i = 0; i < 3; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double d = 0;
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = 0;
				this.getWorld().addParticle(ParticleTypes.SOUL, this.getFX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getFZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getFX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getFZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SCULK_SOUL, this.getFX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getFZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getFX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getFZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
			}
			for(int i = 0; i < 4; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1));
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getFX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow,
						this.getY() + (this.random.range(-1, 1)),
						this.getFZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
		if (status == 108) {
			double shadow = 1;
			if (this.getShadowPowered()){
				shadow = 2;
			}
			for(int i = 0; i < 3; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double d = 0;
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = 0;
				this.getWorld().addParticle(ParticleTypes.SOUL, this.getBX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getBZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getBX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getBZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SCULK_SOUL, this.getBX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getBZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getBX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, this.getY() + (this.random.range(-1, 1)), this.getBZ() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow, d, e, f);
			}
			for(int i = 0; i < 4; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1));
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getBX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F) * shadow,
						this.getY() + (this.random.range(-1, 1)),
						this.getBZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}

		if (status == 109) {
			for(int i = 0; i < 6; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double d = 0;
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = 0;
				this.getWorld().addParticle(ParticleTypes.SOUL, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F), this.getY() + (this.random.range(-1, 1)), this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F), d, e, f);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F), this.getY() + (this.random.range(-1, 1)), this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F), d, e, f);
				this.getWorld().addParticle(ParticleTypes.SCULK_SOUL, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F), this.getY() + (this.random.range(-1, 1)), this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F), d, e, f);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F), this.getY() + (this.random.range(-1, 1)), this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F), d, e, f);
			}
			for(int i = 0; i < 6; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double e = this.random.nextDouble() / 6 * (this.random.range(0, 1));
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -3F, 3F),
						this.getY() + (this.random.range(-1, 1)),
						this.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
	}

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		this.setLifetime(160);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private static final TrackedData<Integer> FRONTX =
			DataTracker.registerData(GhostpepperEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> FRONTZ =
			DataTracker.registerData(GhostpepperEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> BACKX =
			DataTracker.registerData(GhostpepperEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> BACKZ =
			DataTracker.registerData(GhostpepperEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public int getFX() {
		return this.dataTracker.get(FRONTX);
	}
	public int getFZ() {
		return this.dataTracker.get(FRONTZ);
	}
	public int getBX() {
		return this.dataTracker.get(BACKX);
	}
	public int getBZ() {
		return this.dataTracker.get(BACKZ);
	}

	public void setFX(Integer count) {
		this.dataTracker.set(FRONTX, count);
	}
	public void setFZ(Integer count) {
		this.dataTracker.set(FRONTZ, count);
	}
	public void setBX(Integer count) {
		this.dataTracker.set(BACKX, count);
	}
	public void setBZ(Integer count) {
		this.dataTracker.set(BACKZ, count);
	}

	//Charm Counter

	private static final TrackedData<Integer> LIFETIME =
			DataTracker.registerData(GhostpepperEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public int getLiftime() {
		return this.dataTracker.get(LIFETIME);
	}

	public void setLifetime(Integer count) {
		this.dataTracker.set(LIFETIME, count);
	}

	public void removeLife(){
		int count = getLiftime();
		this.dataTracker.set(LIFETIME, count - 1);
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
		if (this.exploding) {
			event.getController().setAnimation(RawAnimation.begin().thenPlayAndHold("ghostpepper.explode"));
		}
		else if (this.attacking && this.transTick > 0){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("ghostpepper.transform"));
		}
		else if (this.attacking){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("ghostpepper.attack"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("ghostpepper.idle"));
		}
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected boolean attacking = false;
	List<LivingEntity> checkList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	private void raycastExplode(float boxOffset, float zOffset) {
		Vec3d vec3d2 = new Vec3d((double) boxOffset, 0.0, zOffset).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1, 4, 1).offset(vec3d2).offset(0, -1.5, 0));
		Vec3d vec3d3 = this.getBoundingBox().offset(vec3d2).getCenter();
		if (boxOffset < 0){
			this.setBX((int) vec3d3.getX());
			this.setBZ((int) vec3d3.getZ());
		}
		if (boxOffset > 0){
			this.setFX((int) vec3d3.getX());
			this.setFZ((int) vec3d3.getZ());
		}
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
			} while (this.squaredDistanceTo(livingEntity) > 100);

			float damage = 12;

			if (((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering()) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity2 && checkList.contains(generalPvZombieEntity2.getOwner())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity)) &&
					!(livingEntity instanceof ZombiePropEntity) &&
					!(livingEntity instanceof GraveEntity)) {
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
				if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
					damage = damage * 2;
				}
				if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)){
					damage = damage / 2;
				}
				if (!checkList.contains(livingEntity)) {
					this.playSound(PvZSounds.PEAHITEVENT, 1, 1);
					livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					checkList.add(livingEntity);
				}
			}
		}
	}

	private void raycastExplode2() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5));
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
			if (livingEntity instanceof IceTile || livingEntity instanceof SnowTile || livingEntity instanceof WaterTile) {
				livingEntity.discard();
			}
			float damage = 90;
			if (livingEntity instanceof OilTile oilTile){
				oilTile.makeFireTrail(oilTile.getBlockPos());
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
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity2 && checkList.contains(generalPvZombieEntity2.getOwner())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity))) {
				ZombiePropEntity zombiePropEntity2 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
						zombiePropEntity2 = zpe;
					}
				}
				if (damage > livingEntity.getHealth() &&
						!(livingEntity instanceof ZombieShieldEntity) &&
						livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - livingEntity.getHealth();
					livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					generalPvZombieEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage2);
					checkList.add(livingEntity);
					checkList.add(generalPvZombieEntity);
				} else if (livingEntity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.getVehicle() != null) {
					zombieShieldEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					checkList.add((LivingEntity) zombieShieldEntity.getVehicle());
					checkList.add(zombieShieldEntity);
				} else if (livingEntity.getVehicle() instanceof ZombieShieldEntity zombieShieldEntity) {

					zombieShieldEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					checkList.add(livingEntity);
					checkList.add(zombieShieldEntity);
				} else {
					if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (zombiePropEntity2 == null && !checkList.contains(livingEntity)) {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						checkList.add(livingEntity);
					}  else if (livingEntity instanceof ZombieVehicleEntity && !checkList.contains(livingEntity)) {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						checkList.add(livingEntity);
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
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (source.getSource() instanceof PlayerEntity) {
			super.applyDamage(source, amount);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/
	private int tickDamage = 20;
	private int transTick = 12;

	private int explodeTick = 15;

	private boolean exploding = false;

	public void tick() {
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			Vec3d vec3d = Vec3d.ofCenter(this.getBlockPos()).add(0, -0.5, 0);
			List<ShadowFullTile> fullCheck = getWorld().getNonSpectatingEntities(ShadowFullTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			List<ShadowTile> tileCheck = getWorld().getNonSpectatingEntities(ShadowTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			if (fullCheck.isEmpty() && tileCheck.isEmpty()) {
				if (this.getWorld().getMoonSize() < 0.1 && this.getWorld().isSkyVisible(this.getBlockPos())) {
					if (serverWorld.isNight()) {
						this.setShadowPowered(Shadow.TRUE);
					}
				} else {
					this.setShadowPowered(Shadow.FALSE);
				}
				if (this.getWorld().getMoonSize() > 0.9 && this.getWorld().isSkyVisible(this.getBlockPos())) {
					if (serverWorld.isNight()) {
						this.setMoonPowered(Moon.TRUE);
					}
				} else {
					this.setMoonPowered(Moon.FALSE);
				}
			}
			if (!fullCheck.isEmpty()) {
				this.setMoonPowered(Moon.TRUE);
			}
			if (!tileCheck.isEmpty()) {
				this.setShadowPowered(Shadow.TRUE);
			}
		}
		super.tick();
		if (this.getLiftime() <= 0){
			if (this.getMoonPowered()){
				this.exploding = true;
				this.getWorld().sendEntityStatus(this, (byte) 109);
				if (--explodeTick <= 0){
					this.checkList.clear();
					this.raycastExplode2();
					this.playSound(PvZSounds.POTATOMINEEXPLOSIONEVENT, 0.5f, 1);
					this.discard();
				}
			}
			else {
				this.discard();
			}
		}
		BlockPos blockPos = this.getBlockPos();
		this.targetZombies(this.getPos(), 5, true, false, true);
		if (!this.getWorld().isClient()) {
			this.attacking = this.getTarget() != null && this.squaredDistanceTo(this.getTarget()) <= 9;
		}
		if (this.getTarget() != null){
			this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
		}
		if (transTick == 0){
			this.playSound(PvZSounds.GHOSTPEPPERPLANTEVENT, 1F, 1F);
		}
		if (this.attacking && !this.getWorld().isClient()){
			this.removeLife();
			this.getWorld().sendEntityStatus(this, (byte) 103);
			--this.transTick;
			this.getWorld().sendEntityStatus(this, (byte) 101);
			if (--tickDamage <= 0){
				this.checkList.clear();
				if (this.getShadowPowered()) {
					for (int u = -2; u <= 2; ++u) {
						this.raycastExplode(u, -1);
						this.raycastExplode(u, 1);
						this.raycastExplode(u, 0);
						this.getWorld().sendEntityStatus(this, (byte) 106);
						this.getWorld().sendEntityStatus(this, (byte) 107);
						this.getWorld().sendEntityStatus(this, (byte) 108);
					}
				}
				else {
					for (int u = -2; u <= 2; ++u) {
						this.raycastExplode(u, 0);
						this.getWorld().sendEntityStatus(this, (byte) 106);
						this.getWorld().sendEntityStatus(this, (byte) 107);
						this.getWorld().sendEntityStatus(this, (byte) 108);
					}
				}
				tickDamage = 20;
			}
		}
		else if (!this.getWorld().isClient()) {
			this.getWorld().sendEntityStatus(this, (byte) 104);
			this.getWorld().sendEntityStatus(this, (byte) 102);
			transTick = 12;
		}
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
				onWater = fluidState.getFluid() == Fluids.WATER;
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
					if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
						this.dropItem(ModItems.GHOSTPEPPER_SEED_PACKET);
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
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.GHOSTPEPPER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createGhostPepperAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1D)
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
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {return PvZSounds.SILENCEVENET;}

	@Nullable
	protected SoundEvent getDeathSound() {return PvZSounds.PLANTPLANTEDEVENT;}

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
}
