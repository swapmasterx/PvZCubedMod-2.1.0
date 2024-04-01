package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.armor;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.MetalHelmetVariants;
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
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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
import java.util.UUID;

public class MetalHelmetProjEntity extends PvZProjectileEntity implements GeoAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public boolean canHitFlying;

	public boolean magnetized;
	public boolean keepSize;

	public boolean damaged;

	public int reverseAge;

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(DAMAGE, 0f);
		this.dataTracker.startTracking(MAXHEALTH, 0f);
		this.dataTracker.startTracking(MAX_AGE, 60);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
		tag.putFloat("Damage", this.getDamage());
		tag.putFloat("MaxHealth", this.getMaxHealth());
		tag.putInt("MaxAge", this.getMaxAge());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		this.dataTracker.set(DAMAGE, tag.getFloat("Damage"));
		this.dataTracker.set(MAXHEALTH, tag.getFloat("MaxHealth"));
		this.dataTracker.set(MAX_AGE, tag.getInt("MaxAge"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(MetalHelmetProjEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public MetalHelmetVariants getVariant() {
		return MetalHelmetVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(MetalHelmetVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	private static final TrackedData<Integer> MAX_AGE =
			DataTracker.registerData(MetalHelmetProjEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public int getMaxAge() {
		return this.dataTracker.get(MAX_AGE);
	}

	public void setMaxAge(Integer integer) {
		this.dataTracker.set(MAX_AGE, integer);
	}

	private static final TrackedData<Float> DAMAGE =
			DataTracker.registerData(MetalHelmetProjEntity.class, TrackedDataHandlerRegistry.FLOAT);

	private float getDamage() {
		return this.dataTracker.get(DAMAGE);
	}

	public void setDamage(Float health) {
		this.dataTracker.set(DAMAGE, health);
	}

	private static final TrackedData<Float> MAXHEALTH =
			DataTracker.registerData(MetalHelmetProjEntity.class, TrackedDataHandlerRegistry.FLOAT);

	private float getMaxHealth() {
		return this.dataTracker.get(MAXHEALTH);
	}

	public void setMaxHealth(Float health) {
		this.dataTracker.set(MAXHEALTH, health);
	}

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

    public MetalHelmetProjEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
		this.canHitFlying = false;
    }

    public MetalHelmetProjEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public MetalHelmetProjEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.METALHELMETPROJ, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
        setUuid(uuid);
    }

    public void tick() {
		this.damaged = getDamage() <= getMaxHealth() / 2;
		if (this.age <= 1){
			reverseAge = this.getMaxAge();
		}
		else {
			--reverseAge;
		}
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

        if (!this.getWorld().isClient && this.age >= this.getMaxAge()) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
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
		if (!this.magnetized) {
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
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
					entity.playSound(PvZSounds.BUCKETHITEVENT, 0.5F, 1F);
					float damage = this.getDamage();
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
						generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage2);
					} else {
						entity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
					}
					this.getWorld().sendEntityStatus(this, (byte) 3);
					this.remove(RemovalReason.DISCARDED);
					if (!(entity instanceof ZombieShieldEntity)) {
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
									float damage3 = this.getDamage() / 2;
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
												livingEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage3);
												generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage4);
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
	}

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
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
