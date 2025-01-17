package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowpea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood.TorchwoodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.pea.ShootingPeaEntity;
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

public class ShootingSnowPeaEntity extends PvZProjectileEntity implements GeoEntity {

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
	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "snowpeaproj");

    public ShootingSnowPeaEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public ShootingSnowPeaEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public ShootingSnowPeaEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, int id, UUID uuid) {
        super(PvZEntity.SNOWPEAPROJ, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation);
		setId(id);
		setUuid(uuid);
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

		if (!this.getWorld().isClient && this.age >= 60) {
			this.getWorld().sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		for (int j = 0; j < 2; ++j) {
			RandomGenerator randomGenerator = this.random;
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
			this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
		}

		if (checkTorchwood(this.getPos()) != null) {
			if (!checkTorchwood(this.getPos()).isWet()) {
				ShootingPeaEntity shootingPeaEntity = (ShootingPeaEntity) PvZEntity.PEA.create(getWorld());
				shootingPeaEntity.torchwoodMemory = checkTorchwood(this.getPos());
				shootingPeaEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				shootingPeaEntity.setVelocity(this.getVelocity());
				shootingPeaEntity.setOwner(this.getOwner());
				shootingPeaEntity.damageMultiplier = damageMultiplier;
				getWorld().spawnEntity(shootingPeaEntity);
				shootingPeaEntity.age = this.age;
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}

    @Override
    protected Item getDefaultItem() {
        return null;
    }

	public TorchwoodEntity checkTorchwood(Vec3d pos) {
		List<TorchwoodEntity> list = getWorld().getNonSpectatingEntities(TorchwoodEntity.class, PvZEntity.PEA.getDimensions().getBoxAt(pos));
		if (!list.isEmpty()){
			return list.get(0);
		}
		else {
			return null;
		}
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
			if (!getWorld().isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) &&
					!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying())) {
				if (!((LivingEntity) entity).hasStatusEffect(PvZCubed.WARM) && !entity.isOnFire() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)) {
					((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
				}
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic", "electronic" -> PvZSounds.PEAHITEVENT;
					case "plastic" -> PvZSounds.PEAHITEVENT;
					case "stone", "crystal" -> PvZSounds.PEAHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				if (entity instanceof ZombieShieldEntity || (entity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
					entity.playSound(sound, 0.2F, 1F);
				} else {
					entity.playSound(PvZSounds.SNOWPEAHITEVENT, 0.2F, 1F);
				}
				float damage = PVZCONFIG.nestedProjDMG.snowPeaDMG();
				if ("crystal".equals(zombieMaterial) || "gold".equals(zombieMaterial) || "cloth".equals(zombieMaterial)) {
					damage = damage / 2;
				}
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
				this.getWorld().sendEntityStatus(this, (byte) 3);
				this.remove(RemovalReason.DISCARDED);
				break;
			}
		}
	}

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SNOWBALL : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
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

			double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
			double e = this.random.nextDouble() / 2 * this.random.range(-1, 1);
			double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);

			for (int j = 0; j < 16; ++j) {
				this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
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
