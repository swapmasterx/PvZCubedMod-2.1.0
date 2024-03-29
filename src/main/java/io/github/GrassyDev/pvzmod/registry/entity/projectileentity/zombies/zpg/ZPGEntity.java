package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.zpg;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class ZPGEntity extends PvZProjectileEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public int maxAge = 60;

	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		animationData.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable > PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("spike.idle"));
		return PlayState.CONTINUE;
	}

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "zpg");

    public ZPGEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public ZPGEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ZPGEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.FIREPEA, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
		setUuid(uuid);
	}

    public void tick() {
        super.tick();
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
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

        if (!this.getWorld().isClient && this.isWet()) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.getWorld().isClient && this.age >= maxAge) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

		for (int j = 0; j < 1; ++j) {
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
			this.getWorld().addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
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
			float damage = PVZCONFIG.nestedProjDMG.zpgDMG() * damageMultiplier;
			if (!world.isClient && entity instanceof Monster monster &&
					(monster instanceof GeneralPvZombieEntity zombie && zombie.getHypno()) &&
					!(zombiePropEntity2 instanceof ZombiePropEntity && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !zombie.isStealth() &&
					!zombie.isFlying() &&
					(!(ZOMBIE_SIZE.get(entity.getType()).orElse("medium").equals("small"))) && !hit) {
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				if ((entity instanceof ZombieShieldEntity || entity.isWet() || ((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) ||
						(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) && !"paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
					entity.playSound(sound, 0.2F, 1F);
				}
				entity.playSound(PvZSounds.FIREPEAHITEVENT, 0.2F, 1F);
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
				if (((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) || entity.isWet() || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())){
					damage = damage / 2;
				}
				if (damage > ((LivingEntity) entity).getHealth() &&
						!(entity instanceof ZombieShieldEntity) &&
						entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) {
					float damage2 = damage - ((LivingEntity) entity).getHealth();
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
				} else {
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
				}
				hit = true;
				if (!entity.isWet() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) &&
						!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) &&
						!(entity instanceof ZombieShieldEntity)) {
					((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
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
					((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
					((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
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
						} while (entity.squaredDistanceTo(livingEntity) > 3);
						if (livingEntity instanceof OilTile oilTile) {
							oilTile.makeFireTrail(oilTile.getBlockPos());
						}
						if (livingEntity instanceof Monster &&
								(livingEntity instanceof GeneralPvZombieEntity zombie1
										&& (zombie1.getHypno())) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet() && (zombie1.canBurn())) {
							if (livingEntity != entity) {
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
								if (!(zombiePropEntity4 instanceof ZombieShieldEntity)&& zombiePropEntity6 == null) {
									float damageSplash = PVZCONFIG.nestedProjDMG.zpgDMG()* damageMultiplier;
									String zombieMaterial2 = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
									if ("paper".equals(zombieMaterial2)) {
										damageSplash = damageSplash * 2;
									} else if ("plant".equals(zombieMaterial2)) {
										damageSplash = damageSplash * 2;
									}
									if (zombiePropEntity4 == null) {
										if (damageSplash > livingEntity.getHealth() &&
												!(livingEntity instanceof ZombieShieldEntity) &&
												livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) {
											float damageSplash2 = damageSplash - livingEntity.getHealth();
											livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damageSplash);
											generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damageSplash2);
										} else {
											livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damageSplash);
										}
										if (!livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet() && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) && !(livingEntity instanceof ZombieShieldEntity)) {
											livingEntity.setOnFireFor(4);
											if (!(livingEntity instanceof ZombieShieldEntity)) {
												livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 40, 1)));
											}
											livingEntity.removeStatusEffect(PvZCubed.FROZEN);
											livingEntity.removeStatusEffect(PvZCubed.ICE);
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
									}
								}
							}
							this.getWorld().sendEntityStatus(this, (byte) 3);
							this.remove(RemovalReason.DISCARDED);
						}
					}
				} else if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn() && !(generalPvZombieEntity instanceof ZombieShieldEntity) && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) && !entity.isWet()) {
					((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
					((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
					((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
				} else {
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
				}
			}
			if (!world.isClient && !(entity instanceof PlantEntity plantEntity && plantEntity.getImmune()) && (entity instanceof GolemEntity || entity instanceof VillagerEntity || entity instanceof PlayerEntity) && !(entity instanceof PlantEntity plantEntity2 && (plantEntity2.getLowProfile() || PLANT_LOCATION.get(plantEntity2.getType()).orElse("normal").equals("flying"))) && !(entity.getVehicle() instanceof BubblePadEntity)) {
				entity.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 0.2F, 1F);
				entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
				List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
				this.getWorld().sendEntityStatus(this, (byte) 3);
				this.remove(RemovalReason.DISCARDED);
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
					} while (entity.squaredDistanceTo(livingEntity) > 3);

					if (!world.isClient && (livingEntity instanceof GolemEntity || livingEntity instanceof VillagerEntity || livingEntity instanceof PlayerEntity)) {
						if (livingEntity != entity) {
							float damageSplash = PVZCONFIG.nestedProjDMG.zpgDMG() * damageMultiplier;
							livingEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damageSplash);
						}
						this.getWorld().sendEntityStatus(this, (byte) 3);
						this.remove(RemovalReason.DISCARDED);
					}
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

            for(int i = 0; i < 8; ++i) {
				double vx = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double vy = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double vz = this.random.nextDouble() / 2 * this.random.range(-1, 1);
                this.getWorld().addParticle(particleEffect, this.getX(), this.getY(), this.getZ(), vx, vy, vz);
            }

			for (int j = 0; j < 16; ++j) {

				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), d, e, f);
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


    public boolean collides() {
        return false;
    }
}
