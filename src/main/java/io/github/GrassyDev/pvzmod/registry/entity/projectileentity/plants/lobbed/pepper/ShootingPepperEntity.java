package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pepper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
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
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ShootingPepperEntity extends PvZProjectileEntity implements GeoEntity {

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

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("cabbage.idle"));
		return PlayState.CONTINUE;
	}

    public ShootingPepperEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(false);
    }

    public ShootingPepperEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingPepperEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.PEPPERPROJ, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation);
		setId(id);
        setUuid(uuid);
    }

	public LivingEntity getTarget (LivingEntity target){
		return this.target = target;
	}

    public void tick() {
        super.tick();
		HitResult hitResult = ProjectileUtil.method_49997(this, this::canHit);
		RandomGenerator randomGenerator = this.random;
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
			if (!this.isWet()){
				this.getWorld().sendEntityStatus(this, (byte)3);
			}
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.getWorld().isClient && this.age >= 120) {
			if (!this.isWet()){
				this.getWorld().sendEntityStatus(this, (byte)3);
			}
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

		if (!this.isWet()){
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;

			for (int j = 0; j < 1; ++j) {
				this.getWorld().addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), d, e, f);
				this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
			}
		}
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }


	@Override
	public void hitEntities() {
		super.hitEntities();
		boolean hit = false;
		Iterator var9 = hitEntities.iterator();
		while (true) {
			Entity entity;
			do {
				if (!var9.hasNext()) {
					return;
				}

				entity = (Entity) var9.next();
			} while (entity == this.getPrimaryPassenger());
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
					if (entity.isWet() && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
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
					float damage = PVZCONFIG.nestedProjDMG.pepperDMGv2();
					if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
						if (!entity.isWet() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET)) {
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
					if (((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) || entity.isWet() || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
						damage = damage / 2;
					}
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
					} else {
						entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), 0);
						entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					}
					hit = true;
					if (!entity.isWet() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) && !(entity instanceof ZombieShieldEntity)) {
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
							} while (entity.squaredDistanceTo(livingEntity) > 6.25);


							if (livingEntity instanceof OilTile oilTile) {
								oilTile.makeFireTrail(oilTile.getBlockPos());
							}
							if (livingEntity instanceof Monster &&
									!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
											&& (generalPvZombieEntity.getHypno())) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet() && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && !generalPvZombieEntity1.canBurn())) {
								if (livingEntity != entity) {
									float damageSplash = PVZCONFIG.nestedProjDMG.pepperSDMG() * damageMultiplier;
									String zombieMaterial2 = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
									if ("paper".equals(zombieMaterial2)) {
										damageSplash = damageSplash * 2;
									} else if ("plant".equals(zombieMaterial2)) {
										damageSplash = damageSplash * 2;
									}
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
											if (damageSplash > livingEntity.getHealth() &&
													!(livingEntity instanceof ZombieShieldEntity) &&
													livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
												float damageSplash2 = damageSplash - livingEntity.getHealth();
												entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), (float) (damageSplash2*0.5));
												entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), (float) (damageSplash*0.5));
												generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damageSplash2);
											} else if (livingEntity instanceof ZombiePropEntity zombiePropEntity) {
												entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), (float) (damageSplash*0.5));
												entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), (float) (damageSplash*0.5));
											} else {
												entity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), (float) (damageSplash*0.5));
												entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), (float) (damageSplash*0.5));
											}
											if (!livingEntity.hasStatusEffect(PvZCubed.WET) && !entity.isWet() && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
												livingEntity.removeStatusEffect(PvZCubed.FROZEN);
												livingEntity.removeStatusEffect(PvZCubed.ICE);
												if (!(livingEntity instanceof ZombieShieldEntity)) {
													livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 40, 1)));
													livingEntity.setOnFireFor(4);
												} else if (livingEntity instanceof ZombieShieldEntity && (PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh").equals("paper") || PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh").equals("plant"))) {
													livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 40, 1)));
													livingEntity.setOnFireFor(4);
												}
											} else if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn() && !(generalPvZombieEntity instanceof ZombieShieldEntity) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet()) {
												livingEntity.removeStatusEffect(PvZCubed.FROZEN);
												livingEntity.removeStatusEffect(PvZCubed.ICE);
												livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
											}
											if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
												generalPvZombieEntity.fireSplashTicks = 10;
											}
											if (livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
												generalPvZombieEntity.fireSplashTicks = 10;
											}
											if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
												generalPvZombieEntity.fireSplashTicks = 10;
											}
											if (!entity.isWet()) {
												this.getWorld().sendEntityStatus(this, (byte) 3);
											}
										}
									}
								}
								if (!entity.isWet()) {
									this.getWorld().sendEntityStatus(this, (byte) 3);
								}
								this.remove(RemovalReason.DISCARDED);
							}
						}
					} else if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn() && !(generalPvZombieEntity instanceof ZombieShieldEntity) && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) && !entity.isWet()) {
						((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
						((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
						((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
						if (!entity.isWet()) {
							this.getWorld().sendEntityStatus(this, (byte) 3);
						}
						this.remove(RemovalReason.DISCARDED);
					} else {
						if (!entity.isWet()) {
							this.getWorld().sendEntityStatus(this, (byte) 3);
						}
						this.remove(RemovalReason.DISCARDED);
					}
			}
		}
	}

	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.FLAME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
	}



	@Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 3) {
			ParticleEffect particleEffect = this.getParticleParameters();

			for(int i = 0; i < 6; ++i) {
				double vx = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double vy = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double vz = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), vx, vy, vz);
			}

			for (int j = 0; j < 8; ++j) {

				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.FLAME, this.getX(), this.getY(), this.getZ(), d, e, f);
			}
		}

    }
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (!this.getWorld().isClient) {
			if (!this.isWet()){
				this.getWorld().sendEntityStatus(this, (byte)3);
			}
			this.remove(RemovalReason.DISCARDED);
        }
    }

    public boolean collides() {
        return false;
    }
}
