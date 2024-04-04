package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.breeze;

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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.thrown.ThrownItemEntity;
import net.minecraft.item.Item;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class BreezeEntity extends PvZProjectileEntity implements GeoEntity {

	private String controllerName = "projectilecontroller";
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public static final Identifier PacketID = new Identifier(PvZEntity.ModID, "breeze");


	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("fume.idle"));
		return PlayState.CONTINUE;
	}

    public BreezeEntity(EntityType<? extends ThrownItemEntity> entityType, World world) {
        super(entityType, world);
		this.setNoGravity(true);
    }

    public BreezeEntity(World world, LivingEntity owner) {
        super(EntityType.SNOWBALL, owner, world);
    }

    @Environment(EnvType.CLIENT)
    public BreezeEntity(World world, double x, double y, double z, float yaw, float pitch, int interpolation, boolean interpolate, int id, UUID uuid) {
        super(PvZEntity.BREEZE, world);
		updatePosition(x, y, z);
		updateTrackedPositionAndAngles(x, y, z, yaw, pitch, interpolation, interpolate);
		setId(id);
		setUuid(uuid);
	}

    public void tick() {
		super.tick();
		if (!this.getWorld().isClient && this.isInsideWaterOrBubbleColumn()) {
			this.getWorld().sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		if (!this.getWorld().isClient && this.age >= 7) {
			this.getWorld().sendEntityStatus(this, (byte) 3);
			this.remove(RemovalReason.DISCARDED);
		}

		for (int j = 0; j < 8; ++j) {
			RandomGenerator randomGenerator = this.random;
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double g = (double) MathHelper.nextBetween(randomGenerator, 0.25F, 0.66F);
			this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY() + g, this.getZ(), d, e, f);
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
					!(entity instanceof SnorkelEntity snorkelEntity && snorkelEntity.isInvisibleSnorkel())) {
				float damage = PVZCONFIG.nestedProjDMG.breezeDMG();
				String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(entity.getType()).orElse("flesh");
				if ("paper".equals(zombieMaterial)) {
					damage = damage * 2;
				}
				if ("crystal".equals(zombieMaterial)) {
					damage = damage / 4;
				}
				if ("gold".equals(zombieMaterial)) {
					damage = damage / 2;
				}
				if ("stone".equals(zombieMaterial)) {
					damage = damage / 2;
				}
				if ("cloth".equals(zombieMaterial)){
					if (PvZCubed.ZOMBIE_SIZE.get(entity.getType()).orElse("medium").equals("small")) {
						damage = Integer.MAX_VALUE;
						Vec3d vec3d = new Vec3d((double) 0.03, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						entity.setVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
					}
				}
				SoundEvent sound;
				sound = switch (zombieMaterial) {
					case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
					case "plastic" -> PvZSounds.CONEHITEVENT;
					case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
					default -> PvZSounds.PEAHITEVENT;
				};
				if (((entity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) || (entity instanceof GeneralPvZombieEntity zombie1 && zombie1.isHovering())) && et == null) {
					Vec3d vec3d = new Vec3d((double) 0.01875, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					if (this.getOwner() != null) {
						vec3d = new Vec3d((double) 0.01875, 0.0, 0.0).rotateY(-this.getOwner().getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
					entity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
				}
				if (et == null) {
					entity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
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
				if (!((LivingEntity) entity).hasStatusEffect(PvZCubed.WARM) && !((LivingEntity) entity).isOnFire() && !((LivingEntity) entity).hasStatusEffect(PvZCubed.FROZEN)) {
					((LivingEntity) entity).addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
					((LivingEntity) entity).playSound(PvZSounds.ICEBERGEXPLOSIONEVENT, 0.1f, 1);
				}
				entityStore.add((LivingEntity) entity);
			}
		}
	}

    @Environment(EnvType.CLIENT)
    public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
        if (status == 3) {
			RandomGenerator randomGenerator = this.random;
			double d = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double e = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double f = (double) MathHelper.nextBetween(randomGenerator, -0.05F, 0.05F);
			double g = (double) MathHelper.nextBetween(randomGenerator, 0.25F, 0.66F);
			this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY() + g, this.getZ(), d, e, f);
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
