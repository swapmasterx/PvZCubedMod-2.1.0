package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dye;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.DyeVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ShootingDyeEntity extends PvZProjectileEntity implements GeoEntity {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public boolean canHitFlying;

	public int maxAge = 60;
	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID2;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID2;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID3;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID3;
	private static final TrackedData<Integer> HYPNO_BEAM_TARGET_ID4;
	private static final TrackedData<Integer> ELECTRIC_BEAM_TARGET_ID4;
	private static final TrackedData<Integer> SPARK_TARGET;
	private LivingEntity cachedBeamTarget;
	private LivingEntity cachedBeamTarget2;
	private LivingEntity cachedBeamTarget3;
	private LivingEntity cachedBeamTarget4;
	private LivingEntity cachedBeamTarget5;
	private LivingEntity cachedBeamTarget6;
	private LivingEntity cachedBeamTarget7;
	private LivingEntity cachedBeamTarget8;
	private LivingEntity cachedSparkTarget;
	private int beamTicks;

	private PlantEntity plantOwner;

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID2, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID2, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID3, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID3, 0);
		this.dataTracker.startTracking(HYPNO_BEAM_TARGET_ID4, 0);
		this.dataTracker.startTracking(ELECTRIC_BEAM_TARGET_ID4, 0);
		this.dataTracker.startTracking(SPARK_TARGET, 0);
	}

	public void onTrackedDataSet(TrackedData<?> data) {
		if (HYPNO_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget2 = null;
		}
		if (HYPNO_BEAM_TARGET_ID2.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget3 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID2.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget4 = null;
		}
		if (HYPNO_BEAM_TARGET_ID3.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget5 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID3.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget6 = null;
		}
		if (HYPNO_BEAM_TARGET_ID4.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget7 = null;
		}
		if (ELECTRIC_BEAM_TARGET_ID4.equals(data)) {
			this.beamTicks = 0;
			this.cachedBeamTarget8 = null;
		}
		if (SPARK_TARGET.equals(data)) {
			this.beamTicks = 0;
			this.cachedSparkTarget = null;
		}

		super.onTrackedDataUpdate(data);
	}

	static {
		HYPNO_BEAM_TARGET_ID = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID2 = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID2 = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID3 = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID3 = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
		HYPNO_BEAM_TARGET_ID4 = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
		ELECTRIC_BEAM_TARGET_ID4 = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
		SPARK_TARGET = DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ShootingDyeEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public DyeVariants getVariant() {
		return DyeVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(DyeVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}


@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public ShootingDyeEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
		this.canHitFlying = false;
    }

    public ShootingDyeEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingDyeEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, int id, UUID uuid) {
        super(PvZEntity.PEA, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation);
		setId(id);
        setUuid(uuid);
    }

	private LivingEntity target;

	public LivingEntity getTarget (LivingEntity target){
		return this.target = target;
	}

    public void tick() {
        super.tick();
		HitResult hitResult = ProjectileUtil.method_49997(this, this::canHit);
		boolean bl = false;
		if (hitResult.getType() == HitResult.Type.BLOCK) {
			BlockPos blockPos = ((BlockHitResult)hitResult).getBlockPos();
			BlockState blockState = this.getWorld().getBlockState(blockPos);
			if (blockState.isOf(Blocks.NETHER_PORTAL)) {
				this.setInNetherPortal(blockPos);
				bl = true;
			} else if (blockState.isOf(Blocks.END_GATEWAY)) {
				BlockEntity blockEntity = this.getWorld().getBlockEntity(blockPos);
				if (blockEntity instanceof EndGatewayBlockEntity && EndGatewayBlockEntity.canTeleport(this)) {
					EndGatewayBlockEntity.tryTeleportingEntity(this.getWorld(), blockPos, blockState, this, (EndGatewayBlockEntity)blockEntity);
				}

				bl = true;
			}
		}

		if (hitResult.getType() != HitResult.Type.MISS && !bl) {
			this.onCollision(hitResult);
		}

        if (!this.getWorld().isClient && this.isInsideWaterOrBubbleColumn()) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

		if (this.getVariant().equals(DyeVariants.ENFORCE)){
			this.maxAge = 30;
		}

		if (this.getVariant().equals(DyeVariants.ARMA)){
			this.maxAge = 120;
			this.setNoGravity(false);
			if (!this.getWorld().isClient && this.age > 50 && target != null) {
				if (target.getHealth() > 0) {
					this.setVelocity(0,this.getVelocity().getY(), 0);
					this.setPosition(target.getPos().getX(), this.getY() - 0.0005, target.getZ());
				}
			}
			if (target != null){
				if ((target.getHealth() > 0 && (this.getPos().getX() <= target.getPos().getX() + 0.3 && this.getPos().getX() >= target.getPos().getX() - 0.3) &&
						this.getPos().getZ() <= target.getPos().getZ() + 0.3 && this.getPos().getZ() >= target.getPos().getZ() - 0.3)){
					this.setVelocity(0,this.getVelocity().getY(), 0);
					this.setPosition(target.getPos().getX(), this.getY() - 0.0005, target.getZ());
				}
			}
		}

        if (!this.getWorld().isClient && this.age >= maxAge) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

		if (this.hasBeamTarget()) {
			if (this.beamTicks < this.getWarmupTime()) {
				++this.beamTicks;
			}

			LivingEntity livingEntity = this.getBeamTarget();
			LivingEntity anchorEntity = this.getElectricBeamTarget();

			LivingEntity livingEntity2 = this.getBeamTarget2();
			LivingEntity anchorEntity2 = this.getElectricBeamTarget2();

			LivingEntity livingEntity3 = this.getBeamTarget3();
			LivingEntity anchorEntity3 = this.getElectricBeamTarget3();

			LivingEntity livingEntity4 = this.getBeamTarget4();
			LivingEntity anchorEntity4 = this.getElectricBeamTarget4();
			if (livingEntity != null && anchorEntity != null) {
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity.getX() - anchorEntity.getX();
				double f = livingEntity.getBodyY(0.5D) - anchorEntity.getEyeY();
				double g = livingEntity.getZ() - anchorEntity.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					double random = this.random.nextDouble();
					if (random <= 0.5) {
						this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
								anchorEntity.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
								anchorEntity.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
					}
				}
			}
			if (livingEntity2 != null && anchorEntity2 != null) {
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity2.getX() - anchorEntity2.getX();
				double f = livingEntity2.getBodyY(0.5D) - anchorEntity2.getEyeY();
				double g = livingEntity2.getZ() - anchorEntity2.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					double random = this.random.nextDouble();
					if (random <= 0.5) {
						this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity2.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
								anchorEntity2.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
								anchorEntity2.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
					}
				}
			}
			if (livingEntity3 != null && anchorEntity3 != null) {
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity3.getX() - anchorEntity3.getX();
				double f = livingEntity3.getBodyY(0.5D) - anchorEntity3.getEyeY();
				double g = livingEntity3.getZ() - anchorEntity3.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					double random = this.random.nextDouble();
					if (random <= 0.5) {
						this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity3.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
								anchorEntity3.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
								anchorEntity3.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
					}
				}
			}
			if (livingEntity4 != null && anchorEntity4 != null) {
				double d = (double)this.getBeamProgress(0.0F);
				double e = livingEntity4.getX() - anchorEntity4.getX();
				double f = livingEntity4.getBodyY(0.5D) - anchorEntity4.getEyeY();
				double g = livingEntity4.getZ() - anchorEntity4.getZ();
				double h = Math.sqrt(e * e + f * f + g * g);
				e /= h;
				f /= h;
				g /= h;
				double j = this.random.nextDouble();

				while(j < h) {
					j += d + this.random.nextDouble() * d;
					double random = this.random.nextDouble();
					if (random <= 0.5) {
						this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, anchorEntity4.getX() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + e * j,
								anchorEntity4.getEyeY() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + f * j,
								anchorEntity4.getZ() + (this.random.nextFloat() / 5 * this.random.range(-1, 1)) + g * j, 0, 0, 0);
					}
				}
			}
		}

		if (!this.getWorld().isClient && enemiesHit >= 3 && this.getVariant().equals(DyeVariants.SPEAR)) {
			this.getWorld().sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		if (!this.getWorld().isClient && enemiesHit > 0 && this.getVariant().equals(DyeVariants.FILAMENT)){
			if (--lightningDespawn <= 0){
				this.getWorld().sendEntityStatus(this, (byte) 3);
				this.remove(RemovalReason.DISCARDED);
			}
		}
    }

	protected int enemiesHit = 0;

	protected int lightningDespawn = 2;

	protected int lightningCounter;

	public void lightning(LivingEntity origin){
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, origin.getBoundingBox().expand(10.0));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					if (!var9.hasNext()) {
						return;
					}

					livingEntity = (LivingEntity) var9.next();
				} while (livingEntity == origin);
			} while (origin.squaredDistanceTo(livingEntity) > 25);

			if (lightningCounter > 0 && livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno())) && !(livingEntity instanceof GraveEntity graveEntity && graveEntity.decorative)) {
				float damage = PVZCONFIG.nestedProjDMG.dyeDMG() * damageMultiplier;
				ZombiePropEntity passenger = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && !(entity1 instanceof ZombieShieldEntity)) {
						passenger = zpe;
					}
				}
				LivingEntity damaged = livingEntity;
				if (passenger != null) {
					damaged = passenger;
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
					case "plastic" -> PvZSounds.PEAHITEVENT;
					case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				damaged.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
				if (livingEntity.isWet() || livingEntity.hasStatusEffect(PvZCubed.WET)){
					damaged.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.ELECTRIC_DAMAGE), damage * 2);
				}
				else {
					damaged.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.ELECTRIC_DAMAGE), damage);
				}
				damaged.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
				setSparkTarget(damaged.getId());
				this.getWorld().sendEntityStatus(this, (byte) 121);
				if (zombieMaterial.equals("plastic") || zombieMaterial.equals("plant")){
					this.lightningCounter -= 2;
				}
				else if (zombieMaterial.equals("rubber")){
					this.lightningCounter = 0;
				}
				else if (!zombieMaterial.equals("metallic") && !zombieMaterial.equals("electronic") && !zombieMaterial.equals("gold") && !zombieMaterial.equals("crystal")){
					--this.lightningCounter;
				}
				if (getBeamTarget2() == null && getElectricBeamTarget2() == null){
					this.setHypnoBeamTarget2(damaged.getId());
					this.setElectricBeamTargetId2(origin.getId());
				}
				else if (getBeamTarget3() == null && getElectricBeamTarget3() == null && getElectricBeamTarget2() != null){
					this.setHypnoBeamTarget3(damaged.getId());
					this.setElectricBeamTargetId3(this.getElectricBeamTarget2().getId());
				}
				else if (getBeamTarget4() == null && getElectricBeamTarget4() == null && getElectricBeamTarget3() != null){
					this.setHypnoBeamTarget4(this.getElectricBeamTarget3().getId());
					this.setElectricBeamTargetId4(damaged.getId());
				}
			}
		}
	}


    @Override
    protected Item getDefaultItem() {
        return null;
    }
	public List<Entity> entityStore = new ArrayList<>();

	@Override
	public void hitEntities() {
		super.hitEntities();
		if (!this.getVariant().equals(DyeVariants.ARMA) && !(this.enemiesHit >= 3 && this.getVariant().equals(DyeVariants.CONCEAL))) {
			boolean hit = false;
			Iterator var9 = hitEntities.iterator();
			while (true) {
				Entity entity;
				do {
					if (!var9.hasNext()) {
						return;
					}

					entity = (Entity) var9.next();
				} while (entity == this.getOwner());
				ZombiePropEntity zombiePropEntity2 = null;
				ZombiePropEntity zombiePropEntity3 = null;
				for (Entity entity1 : entity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
						zombiePropEntity2 = zpe;
					}
					if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity3 = zpe;
					}
				}
				Entity et = null;
				for (Entity entityHitList : entityStore) {
					if (entityHitList == entity) {
						et = entity;
						break;
					}
				}
				if (this.getVariant().equals(DyeVariants.CONCEAL)){
					enemiesHit = 0;
				}
				if (!getWorld().isClient && entity instanceof Monster monster &&
						!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
						!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
						!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
						!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth() && !this.getVariant().equals(DyeVariants.ENLIGHTEN)) &&
						!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) && !hit) {
					if (et == null) {
						if (this.getVariant().equals(DyeVariants.FILAMENT)) {
							this.setElectricBeamTargetId(this.getId());
							this.setHypnoBeamTarget(entity.getId());
						}
						String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
						SoundEvent sound;
						sound = switch (zombieMaterial) {
							case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
							case "plastic" -> PvZSounds.PEAHITEVENT;
							case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
							default -> PvZSounds.PEAHITEVENT;
						};
						entity.playSound(sound, 0.2F, 1F);
						entity.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 0.2F, 1F);
						float damage = PVZCONFIG.nestedProjDMG.peaDMG();
						if (this.getVariant().equals(DyeVariants.CONCEAL) || this.getVariant().equals(DyeVariants.ENLIGHTEN)) {
							damage = damage - 2f;
						}
						if (this.getVariant().equals(DyeVariants.APPEASE) || this.getVariant().equals(DyeVariants.PEPPER) || this.getVariant().equals(DyeVariants.ARMA)) {
							damage = damage + 2f;
						}
						if (this.getVariant().equals(DyeVariants.ENFORCE)) {
							damage = damage + 6f;
						}
						if (this.getVariant().equals(DyeVariants.REINFORCE)) {
							((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.STUN, 10, 5)));
						}
						if (this.getVariant().equals(DyeVariants.AILMENT)) {
							((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.PVZPOISON, 60, 1)));
						}
						if (this.getVariant().equals(DyeVariants.CONTAIN)) {
							((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.GENERICSLOW, 10, 1)));
						}
						if (this.getVariant().equals(DyeVariants.WINTER)) {
							if ("crystal".equals(zombieMaterial) || "gold".equals(zombieMaterial) || "cloth".equals(zombieMaterial)) {
								damage = damage / 2;
							}
							((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
						} else if (this.getVariant().equals(DyeVariants.PEPPER) || this.getVariant().equals(DyeVariants.BOMBARD)) {
							if (!entity.isWet() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET)) {
								((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
								entity.setOnFireFor(4);
								if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
									generalPvZombieEntity.fireSplashTicks = 10;
								}
							}
							if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
								damage = damage * 2;
							}
							if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)) {
								damage = damage / 2;
							}
							if (((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) || entity.isWet() || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
								damage = damage / 2;
							}
						} else if (this.getVariant().equals(DyeVariants.ENCHANT)) {
							boolean hasHelmet = false;
							for (Entity entity1 : entity.getPassengerList()) {
								if (entity1 instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
									hasHelmet = true;
								}
							}
							if (!hasHelmet && !(entity instanceof ZombiePropEntity) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
								damage = damage * 2;
							}
							if ("crystal".equals(zombieMaterial)) {
								damage = damage * 2;
							}
						} else if (this.getVariant().equals(DyeVariants.FILAMENT)) {
							this.setElectricBeamTargetId(this.getId());
							this.setHypnoBeamTarget(entity.getId());
							if (entity.isWet() || ((LivingEntity) entity).hasStatusEffect(PvZCubed.WET)) {
								damage = damage * 2;
							}
							if ("electronic".equals(zombieMaterial) || "crystal".equals(zombieMaterial)) {
								damage = damage / 2;
							}
							if ("rubber".equals(zombieMaterial)) {
								damage = 0;
							}
						}
						if (damage > ((LivingEntity) entity).getHealth() &&
								!(entity instanceof ZombieShieldEntity) &&
								entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							float damage2 = damage - ((LivingEntity) entity).getHealth();
							if (this.getVariant().equals(DyeVariants.FILAMENT)) {
								this.lightningCounter = 3;
								if (zombieMaterial.equals("plastic") || zombieMaterial.equals("plant")) {
									this.lightningCounter -= 2;
								} else if (zombieMaterial.equals("rubber")) {
									this.lightningCounter = 0;
								}
								this.lightning((LivingEntity) entity);
								this.lightningCounter = 3;
								if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
								entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.ELECTRIC_DAMAGE), damage);
								if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
									entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
								}
								generalPvZombieEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.ELECTRIC_DAMAGE), damage2);
								if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
									generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
								}
								this.enemiesHit = 123;
								entityStore.add((LivingEntity) entity);
							} else {
								if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
								entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
								generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
								entityStore.add((LivingEntity) entity);
							}
						} else {
							if (this.getVariant().equals(DyeVariants.FILAMENT)) {
								this.lightningCounter = 3;
								if (zombieMaterial.equals("plastic") || zombieMaterial.equals("plant")) {
									this.lightningCounter -= 2;
								} else if (zombieMaterial.equals("rubber")) {
									this.lightningCounter = 0;
								}
								this.lightning((LivingEntity) entity);
								this.lightningCounter = 3;
								entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.ELECTRIC_DAMAGE), damage);
								if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
								this.enemiesHit = 123;
								entityStore.add((LivingEntity) entity);
							} else {
								if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
								entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
								entityStore.add((LivingEntity) entity);
							}
						}
						if (!this.getVariant().equals(DyeVariants.CONCEAL) && !this.getVariant().equals(DyeVariants.SPEAR) && !this.getVariant().equals(DyeVariants.FILAMENT)) {
							this.getWorld().sendEntityStatus(this, (byte) 3);
							this.remove(RemovalReason.DISCARDED);
						}
						++enemiesHit;
						entityStore.add((LivingEntity) entity);
						if (!(entity instanceof ZombieShieldEntity) && !this.getVariant().equals(DyeVariants.SPEAR) && !this.getVariant().equals(DyeVariants.CONCEAL) && !this.getVariant().equals(DyeVariants.ENFORCE) && !this.getVariant().equals(DyeVariants.FILAMENT)) {
							Vec3d vec3d = this.getPos();
							hit = true;
							List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
							Iterator var10 = list.iterator();
							while (true) {
								LivingEntity livingEntity;
								do {
									do {
										if (!var10.hasNext()) {
											return;
										}

										livingEntity = (LivingEntity) var10.next();
									} while (livingEntity == this.getOwner());
								} while (entity.squaredDistanceTo(livingEntity) > 2.25);

								if (livingEntity instanceof Monster &&
										!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
												&& (generalPvZombieEntity.getHypno()))) {
									if (livingEntity != entity) {
										String zombieMaterial2 = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
										float damage3 = PVZCONFIG.nestedProjDMG.dyeDMG() * damageMultiplier;
										if (this.getVariant().equals(DyeVariants.BOMBARD) || this.getVariant().equals(DyeVariants.ENLIGHTEN)) {
											damage3 = damage3 + 2f;
										}
										if (this.getVariant().equals(DyeVariants.AILMENT)) {
											livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.PVZPOISON, 60, 1)));
										}
										if (this.getVariant().equals(DyeVariants.CONTAIN)) {
											livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.GENERICSLOW, 10, 1)));
										}
										if (this.getVariant().equals(DyeVariants.WINTER)) {
											if ("crystal".equals(zombieMaterial2) || "gold".equals(zombieMaterial2) || "cloth".equals(zombieMaterial2)) {
												damage3 = damage3 / 2;
											}
											livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 60, 1)));
										} else if (this.getVariant().equals(DyeVariants.PEPPER) || this.getVariant().equals(DyeVariants.BOMBARD)) {
											if (!livingEntity.isWet() && !livingEntity.hasStatusEffect(PvZCubed.WET)) {
												livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
												livingEntity.setOnFireFor(4);
												if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
													generalPvZombieEntity.fireSplashTicks = 10;
												}
											}
											if ("paper".equals(zombieMaterial2) || "plant".equals(zombieMaterial2) || "cloth".equals(zombieMaterial2) || "gold".equals(zombieMaterial2)) {
												damage3 = damage3 * 2;
											}
											if ("rubber".equals(zombieMaterial2) || "crystal".equals(zombieMaterial2)) {
												damage3 = damage3 / 2;
											}
											if (livingEntity.hasStatusEffect(PvZCubed.WET) || livingEntity.isWet() || (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
												damage3 = damage3 / 2;
											}
										} else if (this.getVariant().equals(DyeVariants.ENCHANT)) {
											boolean hasHelmet = false;
											for (Entity entity1 : livingEntity.getPassengerList()) {
												if (entity1 instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
													hasHelmet = true;
												}
											}
											if (!hasHelmet && !(livingEntity instanceof ZombiePropEntity) && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
												damage3 = damage3 * 2;
											}
											if ("crystal".equals(zombieMaterial2)) {
												damage3 = damage3 * 2;
											}
										}
										ZombiePropEntity zombiePropEntity4 = null;
										for (Entity entity1 : livingEntity.getPassengerList()) {
											if (entity1 instanceof ZombiePropEntity zpe) {
												zombiePropEntity4 = zpe;
											}
										}
										ZombiePropEntity zombiePropEntity6 = null;
										if (livingEntity.hasVehicle()) {
											for (Entity entity1 : livingEntity.getVehicle().getPassengerList()) {
												if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
													zombiePropEntity6 = zpe;
												}
											}
										}
										if (!(zombiePropEntity4 instanceof ZombieShieldEntity)) {
											if (zombiePropEntity4 == null && zombiePropEntity6 == null) {
												if (damage3 > livingEntity.getHealth() &&
														!(livingEntity instanceof ZombieShieldEntity) &&
														livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
													float damage4 = damage3 - livingEntity.getHealth();
													if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
													entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage3);
													generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage4);
												} else {
													if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
													entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage3);
												}
											}
										}
									}
									if (!this.getVariant().equals(DyeVariants.CONCEAL) && !this.getVariant().equals(DyeVariants.SPEAR)) {
										this.getWorld().sendEntityStatus(this, (byte) 3);
										this.remove(RemovalReason.DISCARDED);
									}
								}
							}
						}
					}
				}
			}
		}
		else if (this.getVariant().equals(DyeVariants.ARMA)) {
			boolean hit = false;
			Iterator var9 = hitEntities.iterator();
			while (true) {
				Entity entity;
				do {
					if (!var9.hasNext()) {
						return;
					}

					entity = (Entity) var9.next();
				} while (entity == this.getOwner());
				ZombiePropEntity zombiePropEntity2 = null;
				ZombiePropEntity zombiePropEntity3 = null;
				for (Entity entity1 : entity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
						zombiePropEntity2 = zpe;
					}
					if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity3 = zpe;
					}
				}
				if (!getWorld().isClient && entity instanceof Monster monster &&
						!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
						!(zombiePropEntity2 instanceof ZombiePropEntity && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
						!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
						!(entity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.hasVehicle()) &&
						!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) && !hit) {
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
					SoundEvent sound;
					sound = switch (zombieMaterial) {
						case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
						case "plastic" -> PvZSounds.PEAHITEVENT;
						case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
					float damage = PVZCONFIG.nestedProjDMG.dyeDMG();
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
						entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
					} else {
						if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
						entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					}
					hit = true;
					Vec3d vec3d = this.getPos();
					List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
					Iterator var10 = list.iterator();
					while (true) {
						LivingEntity livingEntity;
						do {
							do {
								if (!var10.hasNext()) {
									return;
								}

								livingEntity = (LivingEntity) var10.next();
							} while (livingEntity == this.getOwner());
						} while (entity.squaredDistanceTo(livingEntity) > 2.25);

						if (livingEntity instanceof Monster &&
								!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
										&& (generalPvZombieEntity.getHypno()))) {
							if (livingEntity != entity) {
								float damage3 = PVZCONFIG.nestedProjDMG.dyeDMG() * damageMultiplier;
								ZombiePropEntity zombiePropEntity4 = null;
								for (Entity entity1 : livingEntity.getPassengerList()) {
									if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity4 == null) {
										zombiePropEntity4 = zpe;
									}
								}
								ZombiePropEntity zombiePropEntity6 = null;
								if (livingEntity.hasVehicle()) {
									for (Entity entity1 : livingEntity.getVehicle().getPassengerList()) {
										if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
											zombiePropEntity6 = zpe;
										}
									}
								}
								if (!(zombiePropEntity4 instanceof ZombieShieldEntity)) {
									if (zombiePropEntity4 == null && zombiePropEntity6 == null) {
										if (damage3 > livingEntity.getHealth() &&
												!(livingEntity instanceof ZombieShieldEntity) &&
												livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
											float damage2 = damage3 - livingEntity.getHealth();
											if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
											entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage3);
											generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
										} else {
											if (!(entity instanceof ZombiePropEntity zombiePropEntity)){
							entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						}
											entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage3);
										}
									}
								}
							}
							this.getWorld().sendEntityStatus(this, (byte) 3);
							this.remove(RemovalReason.DISCARDED);
						}
					}
				}
			}
		}
	}

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 121) {
			if (this.getSparkTarget() != null) {
				LivingEntity livingEntity = this.getSparkTarget();
				for (int i = 0; i < 64; ++i) {
					double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
					double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
					double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
					this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, livingEntity.getX() + (this.random.range(-1, 1)), livingEntity.getY() + 0.5 + (this.random.range(-1, 1)), livingEntity.getZ() + (this.random.range(-1, 1)), d, e, f);
				}
			}
		}
        if (status == 3) {

			if (this.getVariant().equals(DyeVariants.APPEASE) || this.getVariant().equals(DyeVariants.ENFORCE)) {
				this.getWorld().addParticle(ParticleTypes.ITEM_SLIME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
			else if (this.getVariant().equals(DyeVariants.CONCEAL) || this.getVariant().equals(DyeVariants.ENCHANT)) {
				this.getWorld().addParticle(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
			}
			else if (this.getVariant().equals(DyeVariants.AILMENT)) {
				double d = (double)(180 & 255) / 255.0;
				double e = (double)(30 & 255) / 255.0;
				double f = (double)(200 & 255) / 255.0;
				for(int j = 0; j < 16; ++j) {
					this.getWorld().addParticle(ParticleTypes.ENTITY_EFFECT, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), d, e, f);
				}
			}
			else if (this.getVariant().equals(DyeVariants.BOMBARD) || this.getVariant().equals(DyeVariants.PEPPER)) {
				for (int i = 0; i < 16; ++i) {
					this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
				}
			}
			else if (this.getVariant().equals(DyeVariants.ENLIGHTEN) || this.getVariant().equals(DyeVariants.FILAMENT)) {
				for (int i = 0; i < 16; ++i) {
					this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
				}
			}
			else if (this.getVariant().equals(DyeVariants.WINTER)) {
				for (int i = 0; i < 16; ++i) {
					this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
				}
			}
			else {
				for (int i = 0; i < 16; ++i) {
					this.getWorld().addParticle(ParticleTypes.WATER_SPLASH, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
				}
			}
        }

    }
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
            this.getWorld().sendEntityStatus(this, (byte)3);
			this.remove(RemovalReason.DISCARDED);
        }
    }



	public int getWarmupTime() {
		return 20;
	}

	private void setHypnoBeamTarget(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID, entityId);
	}

	public boolean hasBeamTarget() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID) != 0;
	}

	private void setElectricBeamTargetId(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID, entityId);
	}

	public boolean hasElectricBeamTarget() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID) != 0;
	}

	private void setHypnoBeamTarget2(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID2, entityId);
	}

	public boolean hasBeamTarget2() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID2) != 0;
	}

	private void setElectricBeamTargetId2(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID2, entityId);
	}

	public boolean hasElectricBeamTarget2() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID2) != 0;
	}

	private void setHypnoBeamTarget3(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID3, entityId);
	}

	public boolean hasBeamTarget3() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID3) != 0;
	}

	private void setElectricBeamTargetId3(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID3, entityId);
	}

	public boolean hasElectricBeamTarget3() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID3) != 0;
	}

	private void setHypnoBeamTarget4(int entityId) {
		this.dataTracker.set(HYPNO_BEAM_TARGET_ID4, entityId);
	}

	public boolean hasBeamTarget4() {
		return (Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID4) != 0;
	}

	private void setElectricBeamTargetId4(int entityId) {
		this.dataTracker.set(ELECTRIC_BEAM_TARGET_ID4, entityId);
	}

	public boolean hasElectricBeamTarget4() {
		return (Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID4) != 0;
	}

	private void setSparkTarget(int entityId) {
		this.dataTracker.set(SPARK_TARGET, entityId);
	}

	public boolean hasSparkTarget() {
		return (Integer)this.dataTracker.get(SPARK_TARGET) != 0;
	}

	@Nullable
	public LivingEntity getBeamTarget() {
		if (!this.hasBeamTarget()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget != null) {
				return this.cachedBeamTarget;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget = (LivingEntity)entity;
					return this.cachedBeamTarget;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget() {
		if (!this.hasElectricBeamTarget()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget2 != null) {
				return this.cachedBeamTarget2;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget2 = (LivingEntity)entity;
					return this.cachedBeamTarget2;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getBeamTarget2() {
		if (!this.hasBeamTarget2()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget3 != null) {
				return this.cachedBeamTarget3;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID2));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget3 = (LivingEntity)entity;
					return this.cachedBeamTarget3;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget2() {
		if (!this.hasElectricBeamTarget2()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget4 != null) {
				return this.cachedBeamTarget4;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID2));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget4 = (LivingEntity)entity;
					return this.cachedBeamTarget4;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getBeamTarget3() {
		if (!this.hasBeamTarget3()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget5 != null) {
				return this.cachedBeamTarget5;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID3));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget5 = (LivingEntity)entity;
					return this.cachedBeamTarget5;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget3() {
		if (!this.hasElectricBeamTarget3()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget6 != null) {
				return this.cachedBeamTarget6;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID3));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget6 = (LivingEntity)entity;
					return this.cachedBeamTarget6;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getBeamTarget4() {
		if (!this.hasBeamTarget4()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget7 != null) {
				return this.cachedBeamTarget7;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(HYPNO_BEAM_TARGET_ID4));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget7 = (LivingEntity)entity;
					return this.cachedBeamTarget7;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getElectricBeamTarget4() {
		if (!this.hasElectricBeamTarget4()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedBeamTarget8 != null) {
				return this.cachedBeamTarget8;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(ELECTRIC_BEAM_TARGET_ID4));
				if (entity instanceof LivingEntity) {
					this.cachedBeamTarget8 = (LivingEntity)entity;
					return this.cachedBeamTarget8;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	@Nullable
	public LivingEntity getSparkTarget() {
		if (!this.hasSparkTarget()) {
			return null;
		} else if (this.getWorld().isClient) {
			if (this.cachedSparkTarget != null) {
				return this.cachedSparkTarget;
			} else {
				Entity entity = this.getWorld().getEntityById((Integer)this.dataTracker.get(SPARK_TARGET));
				if (entity instanceof LivingEntity) {
					this.cachedSparkTarget = (LivingEntity)entity;
					return this.cachedSparkTarget;
				} else {
					return null;
				}
			}
		} else {
			if (plantOwner != null) {
				return plantOwner.getTarget();
			}
			else {
				return null;
			}
		}
	}

	public float getBeamProgress(float tickDelta) {
		return ((float)this.beamTicks + tickDelta) / (float)this.getWarmupTime();
	}

    public boolean collides() {
        return false;
    }
}
