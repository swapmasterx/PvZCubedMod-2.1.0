package io.github.GrassyDev.pvzmod.registry.entity.environment.sunbomb;

import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.watertile.WaterTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.List;

public class SunBombEntity extends PathAwareEntity implements GeoAnimatable {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "sunbombcontroller";

    public SunBombEntity(EntityType<? extends SunBombEntity> entityType, World world) {
        super(entityType, world);

    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		RandomGenerator randomGenerator = this.getRandom();
		if (status == 106) {
			for(int i = 0; i < 128; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.DRAGON_BREATH, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 12; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.FLAME, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
		}
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}
	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}
	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("sunbomb"));
		return PlayState.CONTINUE;
	}

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}


	/** /~*~//~*TICKING*~//~*~/ **/



	public void tick() {
		this.clearStatusEffects();
		this.setVelocity(0, -0.008, 0);
		if (this.age >= 1200){
			this.discard();
		}
		super.tick();
	}

	@Override
	protected ActionResult interactMob(PlayerEntity player, Hand hand) {
		this.kill();
		return super.interactMob(player, hand);
	}

	public void tickMovement() {
        super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
			this.discard();
		}
    }
	List<LivingEntity> checkList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	private void raycastExplode() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					if (!var9.hasNext()) {
						return;
					}

					livingEntity = (LivingEntity) var9.next();
				} while (livingEntity == this);
			} while (this.squaredDistanceTo(livingEntity) > 16);
			if (livingEntity instanceof IceTile || livingEntity instanceof SnowTile || livingEntity instanceof WaterTile) {
				livingEntity.discard();
			}
			float damage = 90;
			if (livingEntity instanceof OilTile oilTile){
				oilTile.makeFireTrail(oilTile.getBlockPos());
			}
			ZombiePropEntity zombiePropEntity4 = null;
			if (livingEntity.hasVehicle()) {
				for (Entity entity1 : livingEntity.getVehicle().getPassengerList()) {
					if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
						zombiePropEntity4 = zpe;
					}
				}
			}
			for (Entity entity1 : livingEntity.getPassengerList()) {
				if (entity1 instanceof ZombieShieldEntity zpe && zpe != livingEntity) {
					zombiePropEntity4 = zpe;
				}
			}
			if ((((livingEntity instanceof Monster || livingEntity instanceof PlantEntity) &&
					zombiePropEntity4 == null &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity2 && checkList.contains(generalPvZombieEntity2.getOwner())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity))) {
				ZombiePropEntity zombiePropEntity2 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
						zombiePropEntity2 = zpe;
					}
				}
				if (damage > livingEntity.getHealth() &&
						!(livingEntity instanceof ZombieShieldEntity) &&
						livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - livingEntity.getHealth();
					livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
					generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
					checkList.add(livingEntity);
					checkList.add(generalPvZombieEntity);
				} else if (livingEntity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.getVehicle() != null) {
					zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), damage);
					checkList.add((LivingEntity) zombieShieldEntity.getVehicle());
					checkList.add(zombieShieldEntity);
				} else if (livingEntity.getVehicle() instanceof ZombieShieldEntity zombieShieldEntity) {

					zombieShieldEntity.damage(DamageSource.thrownProjectile(this, this), damage);
					checkList.add(livingEntity);
					checkList.add(zombieShieldEntity);
				} else {
					if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (zombiePropEntity2 == null && !checkList.contains(livingEntity)) {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						checkList.add(livingEntity);
					}  else if (livingEntity instanceof ZombieVehicleEntity && !checkList.contains(livingEntity)) {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						checkList.add(livingEntity);
					}
				}
			}
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	@Override
	public boolean canBeLeashedBy(PlayerEntity player) {
		return false;
	}

	@Override
	public boolean hasCustomName() {
		return super.hasCustomName();
	}

	public static DefaultAttributeContainer.Builder createSunBombAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 0.1D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.05F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return null	;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return null;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}

	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		this.playSound(PvZSounds.SUNDROPEVENT, 0.5f, 1);
		dropItem(ModItems.SUN);
		this.discard();
		return super.handleFallDamage(fallDistance, damageMultiplier, damageSource);
	}

	@Override
	public void onDeath(DamageSource source) {
		this.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 0.5f, 1);
		this.getWorld().sendEntityStatus(this, (byte) 106);
		raycastExplode();
		this.discard();
		super.onDeath(source);
	}
<<<<<<< Updated upstream
=======


	@Override
	public double getTick(Object object) {
		return 0;
	}
>>>>>>> Stashed changes
}
