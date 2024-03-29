package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.laser;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
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
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class LaserEntity extends PvZProjectileEntity implements IAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public LaserEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
		super(entityType, world);
		this.setNoGravity(true);
	}

	public LaserEntity(World world, LivingEntity owner) {
		super(EntityType.SNOWBALL, owner, world);
	}

	@Environment(EnvType.CLIENT)
	public LaserEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
		super(PvZEntity.SPIT, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
		setUuid(uuid);
	}

	@Override
	public void registerControllers(AnimationData animationData) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		animationData.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public void tick() {
        super.tick();
		HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
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

        if (!this.getWorld().isClient && this.age >= 20) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

		for (int j = 0; j < 1; ++j) {
			RandomGenerator randomGenerator = this.random;
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double dd = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double ee = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);;
			double ff = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);;
			this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX(), this.getY(), this.getZ(), dd, ee, ff);
			this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.getWorld().addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
		}
    }

    @Override
    protected Item getDefaultItem() {
        return null;
    }


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
			float damage = PVZCONFIG.nestedProjDMG.laserDMG() * damageMultiplier;
			if (this.getOwner() instanceof GeneralPvZombieEntity owner && owner.getHypno()){
				if (!world.isClient && entity instanceof Monster monster &&
						!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
						!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
						!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) &&
						!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying())) {
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
					entity.playSound(PvZSounds.FIREPEAHITEVENT, 0.2F, (float) (0.5F + Math.random()));
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
						generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
					} else {
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
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
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
					break;
				}
			}
			else {
				if (!world.isClient && entity instanceof Monster monster &&
						(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
						!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
						!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
						!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(generalPvZombieEntity.isStealth()) &&
						!(generalPvZombieEntity.isFlying())) {
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
					if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
						damage = damage * 2;
					}
					if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)) {
						damage = damage / 2;
					}
					if (((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) || entity.isWet() || !generalPvZombieEntity.canBurn()) {
						damage = damage / 2;
					}
					entity.playSound(PvZSounds.FIREPEAHITEVENT, 0.2F, (float) (0.5F + Math.random()));
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity3 && !(generalPvZombieEntity3.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
						generalPvZombieEntity3.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage2);
					} else {
						entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					}
					if (!entity.isWet() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.WET) &&
							!generalPvZombieEntity.canBurn() &&
							!(entity instanceof ZombieShieldEntity)) {
						((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
						((LivingEntity) entity).removeStatusEffect(PvZCubed.FROZEN);
						((LivingEntity) entity).removeStatusEffect(PvZCubed.ICE);
						entity.setOnFireFor(4);
						generalPvZombieEntity.fireSplashTicks = 10;
					}
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
					break;
				}
				else if (entity instanceof LilyPadEntity && entity.hasPassengers()) {

				} else if (!world.isClient && !(entity instanceof PlantEntity plantEntity && plantEntity.getImmune()) && !(entity instanceof PlantEntity plantEntity1 && plantEntity1.getFireImmune()) && (entity instanceof GolemEntity || entity instanceof VillagerEntity || entity instanceof PlayerEntity) && !(entity.getVehicle() instanceof PlantEntity.VineEntity)) {
					entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), damage);
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
					break;
				}
				else if (entity instanceof PlantEntity plantEntity1 && plantEntity1.getFireImmune()){
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
					break;
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
            this.getWorld().sendEntityStatus(this, (byte)3);
			this.remove(RemovalReason.DISCARDED);
        }
    }

    public boolean collides() {
        return false;
    }
}
