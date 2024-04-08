package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.rocket;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntity;
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

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class RocketEntity extends PvZProjectileEntity implements GeoEntity {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public int maxAge = 60;

@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	private <P extends GeoAnimatable > PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("spike.idle"));
		return PlayState.CONTINUE;
	}

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "zpg");

    public RocketEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
    }

    public RocketEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public RocketEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.FIREPEA, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation);
		setId(id);
		setUuid(uuid);
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
		this.damageAll();
		this.getWorld().sendEntityStatus(this, (byte) 3);
		this.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 0.2F, 1F);
		this.remove(RemovalReason.DISCARDED);

	}

	protected void damageAll(){
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
			} while (this.squaredDistanceTo(livingEntity) > 3);
			if (livingEntity instanceof OilTile oilTile) {
				oilTile.makeFireTrail(oilTile.getBlockPos());
			}
			if (this.getOwner() instanceof BullyEntity bullyEntity && bullyEntity.getHypno()) {
				if (livingEntity instanceof Monster &&
						(livingEntity instanceof GeneralPvZombieEntity zombie1
								&& (zombie1.getHypno())) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet() && (zombie1.canBurn())) {
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
					if (!(zombiePropEntity4 instanceof ZombieShieldEntity) && zombiePropEntity6 == null) {
						float damageSplash = PVZCONFIG.nestedProjDMG.rocketDMG() * damageMultiplier;
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
								livingEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damageSplash);
								generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damageSplash2);
							} else {
								livingEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damageSplash);
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
						}
					}
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
				}
			} else {
				if (!getWorld().isClient && (livingEntity instanceof GolemEntity || livingEntity instanceof VillagerEntity || livingEntity instanceof PlayerEntity)) {
					float damageSplash = PVZCONFIG.nestedProjDMG.rocketDMG() * damageMultiplier;
					livingEntity.damage(getDamageSources().mobProjectile(this, (LivingEntity) this.getOwner()), damageSplash);
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
				}
			}
		}
	}

    public boolean collides() {
        return false;
    }
}
