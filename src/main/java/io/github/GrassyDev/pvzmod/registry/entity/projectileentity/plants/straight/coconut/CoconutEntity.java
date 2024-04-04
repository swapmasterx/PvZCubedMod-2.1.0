package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.coconut;

import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class CoconutEntity extends PvZProjectileEntity implements GeoAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

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

	private <P extends GeoAnimatable > PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public CoconutEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
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

        if (!this.getWorld().isClient && this.age >= 60) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
		double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
		double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;

		for (int j = 0; j < 3; ++j) {
			this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.getWorld().addParticle(ParticleTypes.SMALL_FLAME, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
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
			if (!getWorld().isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) &&
					!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) && !hit) {
				entity.playSound(PvZSounds.POTATOMINEEXPLOSIONEVENT, 0.8F, 1F);
				float damage = PVZCONFIG.nestedProjDMG.coconutDMGv2();
				if (damage > ((LivingEntity) entity).getHealth() &&
						!(entity instanceof ZombieShieldEntity) &&
						entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - ((LivingEntity) entity).getHealth();
					entity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
					generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage2);
				} else {
					entity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
				}
				hit = true;
				this.getWorld().sendEntityStatus(this, (byte) 3);
				this.remove(RemovalReason.DISCARDED);
				Vec3d vec3d = this.getPos();
				if (!(entity instanceof ZombieShieldEntity)) {
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
						} while (this.squaredDistanceTo(livingEntity) > 6);

						if (livingEntity instanceof OilTile oilTile) {
							oilTile.makeFireTrail(oilTile.getBlockPos());
						}
						if (livingEntity instanceof Monster &&
								!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
										&& (generalPvZombieEntity.getHypno()))) {
							if (livingEntity != entity) {
								float damage3 = PVZCONFIG.nestedProjDMG.coconutSDMG() * damageMultiplier;
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
											float damage2 = damage3 - livingEntity.getHealth();
											livingEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage3);
											generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage2);
										} else {
											livingEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage3);
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
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.SMOKE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
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

			for (int j = 0; j < 256; ++j) {

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
