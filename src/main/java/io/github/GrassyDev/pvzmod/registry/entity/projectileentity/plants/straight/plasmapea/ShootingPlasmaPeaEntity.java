package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.plasmapea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ShootingPlasmaPeaEntity extends PvZProjectileEntity implements GeoEntity {

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

	private <P extends GeoAnimatable > PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "plasmapea");

    public ShootingPlasmaPeaEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public ShootingPlasmaPeaEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingPlasmaPeaEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.PLASMAPEA, world);
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

        if (!this.getWorld().isClient && this.isInsideWaterOrBubbleColumn()) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.getWorld().isClient && this.age >= 60) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
		double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double dd = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
		double ee = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);;
		double ff = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);;

		for (int j = 0; j < 1; ++j) {
			this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), dd, ee, ff);
			this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
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
		Iterator var9 = hitEntities.iterator();
		while (true) {
			Entity entity;
			do {
				if (!var9.hasNext()) {
					return;
				}

				entity = (Entity) var9.next();
			} while (entity == this.getOwner());

			ZombiePropEntity zombiePropEntity = null;
			ZombiePropEntity zombiePropEntity3 = null;
			for (Entity entity1 : entity.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity == null) {
					zombiePropEntity = zpe;
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
			if (!getWorld().isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(zombiePropEntity != null && !(zombiePropEntity instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) &&
					!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying())) {
				float damage = PVZCONFIG.nestedProjDMG.plasmaPeaDMG();
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
					damage = damage * 2;
				}
				if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)){
					damage = damage / 2;
				}
				if (((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) || entity.isWet() || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
					damage = damage / 2;
				}
				if (et == null) {
					entity.playSound(PvZSounds.FIREPEAHITEVENT, 0.2F, (float) (0.5F + Math.random()));
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage);
						generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage2);
					} else {
						entity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damage);
					}
					entityStore.add((LivingEntity) entity);
				}
				if (!entity.isWet() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) &&
						!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn()) &&
						!(entity instanceof ZombieShieldEntity)) {
					((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
					((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
					((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
					entity.setOnFireFor(4);
					if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity) {
						generalPvZombieEntity.fireSplashTicks = 10;
					}
					if (entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity) {
						generalPvZombieEntity.fireSplashTicks = 10;
					}
				}
				entityStore.add((LivingEntity) entity);
			}
		}
	}

        @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.SOUL_FIRE_FLAME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
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

				double dd = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double ee = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double ff = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), d, e, f);
				this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), dd, ee, ff);
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
