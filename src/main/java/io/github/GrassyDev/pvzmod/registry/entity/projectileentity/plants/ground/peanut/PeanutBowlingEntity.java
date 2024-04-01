package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.peanut;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.PvZProjectileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShootingPeaVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PeanutBowlingEntity extends PvZProjectileEntity implements GeoAnimatable {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(PeanutBowlingEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ShootingPeaVariants getVariant() {
		return ShootingPeaVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(ShootingPeaVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
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
		event.getController().setAnimation(RawAnimation.begin().thenLoop("peanut.roll"));
		return PlayState.CONTINUE;
	}

    public PeanutBowlingEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.noClip = false;
    }

    public PeanutBowlingEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public PeanutBowlingEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.GROUNDBOUNCE, world);
        updatePosition(x, y, z);
        updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
        setUuid(uuid);
    }

	private boolean canBounce;
	private int airTicks = 10;

	@Override
	protected float getGravity() {
		return (this.age > 1)? 1 : super.getGravity();
	}

	public void tick() {
        super.tick();
		if (this.isInsideWaterOrBubbleColumn()){
			this.remove(RemovalReason.DISCARDED);
		}
		RandomGenerator randomGenerator = this.getWorld().getRandom();
		int l = MathHelper.floor(this.getPos().x);
		int m = MathHelper.floor(this.getPos().y - (double)0.25);
		int n = MathHelper.floor(this.getPos().z);
		BlockPos blockPos2 = new BlockPos(l, m, n);
		if (!this.getWorld().getFluidState(blockPos2).isSource() && !this.getWorld().getFluidState(blockPos2).isSource()) {
			this.setVelocity(this.getVelocity().getX(), 0, this.getVelocity().getZ());
			this.canBounce = true;
			this.airTicks = 10;
		}
		else {
			if (--airTicks <= 0) {
				this.canBounce = false;
			}
		}
		for(int i = 0; i < 4; ++i) {
			double d = this.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
			double e = this.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 0.5F);
			double f = this.getZ() + (double) MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
			this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, this.getWorld().getBlockState(blockPos2)), d, e, f, 0.0, 2, 0.0);
		}

        if (!this.getWorld().isClient && this.isInsideWaterOrBubbleColumn()) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }

        if (!this.getWorld().isClient && this.age >= 60) {
            this.getWorld().sendEntityStatus(this, (byte) 3);
            this.remove(RemovalReason.DISCARDED);
        }
    }


    @Override
    protected Item getDefaultItem() {
        return null;
    }

	Entity prevZombie = null;
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
			Entity et = null;
			for (Entity entityHitList : entityStore) {
				if (entityHitList == entity) {
					et = entity;
					break;
				}
			}
			if (!getWorld().isClient && entity instanceof Monster monster &&
					!(monster instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHypno())) &&
					!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel()) && !(entity instanceof GeneralPvZombieEntity generalPvZombieEntity3 && generalPvZombieEntity3.isStealth()) &&
					(!(entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) && !(entity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) && this.canBounce && prevZombie != entity) {
				if (et == null) {
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
					SoundEvent sound;
					sound = switch (zombieMaterial) {
						case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
						case "plastic" -> PvZSounds.CONEHITEVENT;
						case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
					float damage = 30f;
					if ("metallic".equals(zombieMaterial) || "stone".equals(zombieMaterial) || "electronic".equals(zombieMaterial) || "crystal".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
						damage = damage * 2;
					}
					if ("paper".equals(zombieMaterial) || "rubber".equals(zombieMaterial) || "cloth".equals(zombieMaterial)) {
						damage = damage / 2;
					}
					if (damage > ((LivingEntity) entity).getHealth() &&
							!(entity instanceof ZombieShieldEntity) &&
							entity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - ((LivingEntity) entity).getHealth();
						entity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
						generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage2);
					} else {
						entity.damage(getDamageSources().mobProjectile(this, this.getPrimaryPassenger()), damage);
					}
					entityStore.add((LivingEntity) entity);
				}
				entityStore.add((LivingEntity) entity);
			}
		}
	}

	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (!this.getWorld().isClient) {
			this.remove(RemovalReason.DISCARDED);
		}
	}

    @Environment(EnvType.CLIENT)
    private ParticleEffect getParticleParameters() {
        ItemStack itemStack = this.getItem();
        return (ParticleEffect)(itemStack.isEmpty() ? ParticleTypes.ITEM_SLIME : new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack));
    }


    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
    }

    public boolean collides() {
        return false;
    }
}
