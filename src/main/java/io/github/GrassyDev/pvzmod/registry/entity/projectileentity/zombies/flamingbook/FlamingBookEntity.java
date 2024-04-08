package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
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

import java.util.Iterator;
import java.util.UUID;

public class FlamingBookEntity extends PvZProjectileEntity implements GeoEntity {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private LivingEntity target;

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}


	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putBoolean("Fire", this.getFireStage());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, nbt.getBoolean("Fire"));
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("book.idle"));
		return PlayState.CONTINUE;
	}

    public FlamingBookEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(false);
    }

    public FlamingBookEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public FlamingBookEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.FLAMINGBOOK, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation);
		setId(id);
        setUuid(uuid);
    }


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(FlamingBookEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum FireStage {
		FIRE(true),
		EXTINGUISHED(false);

		FireStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getFireStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setFireStage(FireStage fireStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, fireStage.getId());
	}

	public LivingEntity getTarget (LivingEntity target){
		return this.target = target;
	}

	public boolean doExtinguish = false;

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

        if (!this.getWorld().isClient && this.age >= 120) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
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

		if (this.isWet() || this.doExtinguish){
			this.setFireStage(FireStage.EXTINGUISHED);
		}
		if (this.getFireStage()) {
			for (int j = 0; j < 1; ++j) {
				RandomGenerator randomGenerator = this.random;
				double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
				double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
				double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
				this.getWorld().addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), d, e, f);
				this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
			}
		}
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }

	public boolean isHypno = false;


	@Override
	public void hitEntities() {
		super.hitEntities();
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
			if (entity instanceof LivingEntity livingEntity) {
				if (entity != this && !(entity instanceof PlantEntity plantEntity && plantEntity.getImmune())) {
					if (!this.isHypno) {
						if (!getWorld().isClient &&
								((entity instanceof Monster monster &&
										(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
										!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
										!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
										!(entity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.hasVehicle())) || entity instanceof PlantEntity) && !(entity.getVehicle() instanceof BubblePadEntity)) {
							String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
							if ((entity.isWet() || !this.getFireStage()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
								SoundEvent sound;
								sound = switch (zombieMaterial) {
									case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
									case "plastic" -> PvZSounds.CONEHITEVENT;
									case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
									default -> PvZSounds.PEAHITEVENT;
								};
								entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
							} else {
								entity.playSound(PvZSounds.FIREPEAHITEVENT, 0.2F, 1F);
							}
							float damage = 16 * damageMultiplier;
							if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
								if (!entity.isWet() && this.getFireStage() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET)) {
									((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
									entity.setOnFireFor(4);
									if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
										generalPvZombieEntity.fireSplashTicks = 10;
									}
								}
								damage = damage * 2;
							}
							if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)){
								damage = damage / 2;
							}
							if (((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) || entity.isWet() || !this.getFireStage() || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) || (entity instanceof PlantEntity plantEntity && plantEntity.getFireImmune())) {
								damage = damage / 2;
							}
							if (damage > ((LivingEntity) entity).getHealth() &&
									!(entity instanceof ZombieShieldEntity) &&
									entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) {
								float damage2 = damage - ((LivingEntity) entity).getHealth();
								entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage);
								generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
							} else {
								entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage);
							}
							if (!entity.isWet() && this.getFireStage() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) && !(entity instanceof ZombieShieldEntity)) {
								((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
								((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
								entity.setOnFireFor(4);
								if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
									generalPvZombieEntity.fireSplashTicks = 10;
								}
								if (entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
									generalPvZombieEntity.fireSplashTicks = 10;
								}
								if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
									generalPvZombieEntity.fireSplashTicks = 10;
								}
								((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
							}
							this.getWorld().sendEntityStatus(this, (byte) 3);
							this.remove(RemovalReason.DISCARDED);
						}
					} else {
						if (!getWorld().isClient && entity instanceof Monster monster &&
								!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
								!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity))) {
							String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
							if ((entity.isWet() || !this.getFireStage()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
								SoundEvent sound;
								sound = switch (zombieMaterial) {
									case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
									case "plastic" -> PvZSounds.CONEHITEVENT;
									case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
									default -> PvZSounds.PEAHITEVENT;
								};
								entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
							} else {
								entity.playSound(PvZSounds.FIREPEAHITEVENT, 0.2F, 1F);
							}
							float damage = 16 * damageMultiplier;
							if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
								if (!entity.isWet() && this.getFireStage() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET)) {
									((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
									entity.setOnFireFor(4);
									if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
										generalPvZombieEntity.fireSplashTicks = 10;
									}
								}
								damage = damage * 2;
							}
							if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)){
								damage = damage / 2;
							}
							if (((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) || entity.isWet() || !this.getFireStage() || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
								damage = damage / 2;
							}
							if (damage > ((LivingEntity) entity).getHealth() &&
									!(entity instanceof ZombieShieldEntity) &&
									entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
								float damage2 = damage - ((LivingEntity) entity).getHealth();
								entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage);
								generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
							} else {
								entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage);
							}
							if (!entity.isWet() && this.getFireStage() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) && !(entity instanceof ZombieShieldEntity)) {
								((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
								((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
								entity.setOnFireFor(4);
								if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
									generalPvZombieEntity.fireSplashTicks = 10;
								}
								if (entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
									generalPvZombieEntity.fireSplashTicks = 10;
								}
								if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
									generalPvZombieEntity.fireSplashTicks = 10;
								}
								((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
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
        return (ParticleEffect)(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.ORANGE_TERRACOTTA.getDefaultState()));
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
        if (status == 3) {
            ParticleEffect particleEffect = this.getParticleParameters();

            for(int i = 0; i < 8; ++i) {
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
            }
        }

    }

    public boolean collides() {
        return false;
    }
}
