package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.sky.missiletoeproj;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile.BananaTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.target.missiletoe.MissileToeTarget;
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
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;



import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class MissileToeProjEntity extends PvZProjectileEntity implements GeoAnimatable {

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

	@Override
	public double getTick(Object object) {
		return 0;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("peashot.idle"));
		return PlayState.CONTINUE;
	}

    public MissileToeProjEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(false);
    }

    public MissileToeProjEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public MissileToeProjEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.PEPPERPROJ, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
        setUuid(uuid);
    }

	public LivingEntity getTarget (LivingEntity target){
		return this.target = target;
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
			this.getWorld().sendEntityStatus(this, (byte)3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.getWorld().isClient && this.age >= 120) {
			this.getWorld().sendEntityStatus(this, (byte)3);
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

		double d = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);
		double e = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;
		double f = (double) MathHelper.nextBetween(randomGenerator, -0.1F, 0.1F);;

		for (int j = 0; j < 1; ++j) {
			this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
			this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e * -1, f);
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
			}while (entity == this);
			if (!getWorld().isClient && entity instanceof MissileToeTarget missileToeTarget) {
				for (int x = -1; x <= 1; ++x) {
					for (int z = -1; z <= 1; ++z) {
						createIceTile(new BlockPos(entity.getBlockPos().getX() + x, (int) entity.getY(), entity.getBlockPos().getZ() + z));
					}
				}
				this.playSound(PvZSounds.SNOWPEAHITEVENT, 0.2f, 1f);
				createIceTile(new BlockPos(entity.getBlockPos().getX() + 2, (int) entity.getY(), entity.getBlockPos().getZ()));
				createIceTile(new BlockPos(entity.getBlockPos().getX(), (int) entity.getY(), entity.getBlockPos().getZ() + 2));
				createIceTile(new BlockPos(entity.getBlockPos().getX() - 2, (int) entity.getY(), entity.getBlockPos().getZ()));
				createIceTile(new BlockPos(entity.getBlockPos().getX(), (int) entity.getY(), entity.getBlockPos().getZ() - 2));
				missileToeTarget.discard();
				List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5.0));
				boolean hasZombie = false;
				for (LivingEntity livingEntity : list) {
					if (livingEntity instanceof Monster &&
							!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
									&& (generalPvZombieEntity.getHypno()))) {
						hasZombie = true;
					}
					if (livingEntity instanceof ScorchedTile){
						hasZombie = true;
					}
				}
				if (hasZombie) {
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
						} while (entity.squaredDistanceTo(livingEntity) > 9);

						if (livingEntity instanceof ScorchedTile){
							livingEntity.discard();
						}
						if (livingEntity instanceof Monster &&
								!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
										&& (generalPvZombieEntity.getHypno()))) {
							if (livingEntity != entity) {
								float damageNear = PVZCONFIG.nestedProjDMG.missileToeDMGNear();
								float damageFar = PVZCONFIG.nestedProjDMG.missileToeDMGFar();
								float rangeNear = PVZCONFIG.nestedProjDMG.missileToeDMGRangeNear();
								float rangeFar = PVZCONFIG.nestedProjDMG.missileToeDMGRangeFar();
								float positionFromCenter = (float) Math.sqrt(entity.squaredDistanceTo(livingEntity));
								float n = (positionFromCenter - rangeNear) / (rangeFar - rangeNear);
								n = MathHelper.clamp(n, 0, 1);
								float damage = n * damageFar + (1 - n) * damageNear;
								String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
								if ("crystal".equals(zombieMaterial) || "gold".equals(zombieMaterial) || "cloth".equals(zombieMaterial)) {
									damage = damage / 2;
								}
								ZombiePropEntity zombiePropEntity3 = null;
								for (Entity entity1 : livingEntity.getPassengerList()) {
									if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity3 == null) {
										zombiePropEntity3 = zpe;
									}
								}
								if (!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity))) {
									if (damage > livingEntity.getHealth() &&
											!(livingEntity instanceof ZombieShieldEntity) &&
											livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
										float damage2 = damage - livingEntity.getHealth();
										livingEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
										generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage2);
									} else {
										livingEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
									}
									if (!livingEntity.hasStatusEffect(PvZCubed.WARM) && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)) {
										livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
									}
								}
							}
							this.getWorld().sendEntityStatus(this, (byte) 3);
							this.remove(RemovalReason.DISCARDED);
						}
					}
				}
				else {
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
				}
			}
		}
	}

	public void createIceTile(BlockPos blockPos){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			List<TileEntity> tileCheck = getWorld().getNonSpectatingEntities(TileEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()).expand(-0.5f, -0.5f, -0.5f));
			tileCheck.removeIf(tile -> tile instanceof MissileToeTarget);
			tileCheck.removeIf(tile -> tile instanceof ScorchedTile);
			tileCheck.removeIf(tile -> tile instanceof BananaTile);
			if (tileCheck.isEmpty()) {
				IceTile tile = (IceTile) PvZEntity.ICETILE.create(getWorld());
				tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
				tile.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				tile.setPersistent();
				tile.setHeadYaw(0);
				serverWorld.spawnEntityAndPassengers(tile);
			}
		}
	}

	@Environment(EnvType.CLIENT)
	private ParticleEffect getParticleParameters() {
		ItemStack itemStack = this.getItem();
		return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.SNOWFLAKE : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
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
				this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY(), this.getZ(), d, e, f);
			}
		}

    }
    protected void onBlockHit(BlockHitResult blockHitResult) {

    }

    public boolean collides() {
        return false;
    }
}
