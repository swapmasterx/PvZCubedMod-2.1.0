package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTile;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
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

public class FireTrailEntity extends PathAwareEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "firetrailcontroller";

	public float ageMax = 150;

    public FireTrailEntity(EntityType<? extends FireTrailEntity> entityType, World world) {
        super(entityType, world);

		this.setInvulnerable(true);
    }

	static {
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("firetrail.idle"));
		event.getController().setAnimationSpeed(1.25);
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}

	private void damageEntity() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
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
			} while (this.squaredDistanceTo(livingEntity) > 9);

			if (((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && (!livingEntity.isWet() && !livingEntity.hasStatusEffect(PvZCubed.WET) && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
							generalPvZombieEntity.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering()))) {
				ZombiePropEntity zombiePropEntity2 = null;
				ZombiePropEntity zombiePropEntity3 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
						zombiePropEntity2 = zpe;
					}
					else if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity3 = zpe;
					}
				}
				if (zombiePropEntity2 == null ||
				zombiePropEntity2 instanceof ZombieShieldEntity) {
					int damage = 1;
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
					if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
						damage = damage * 2;
					}
					if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)){
						damage = damage / 2;
					}
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						generalPvZombieEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage2);
					} else {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					}
					if (!(livingEntity instanceof ZombieShieldEntity)) {
						livingEntity.removeStatusEffect(PvZCubed.FROZEN);
						livingEntity.removeStatusEffect(PvZCubed.ICE);
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
						livingEntity.setOnFireFor(60);
					}
				}
			}
			else if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn() && !(generalPvZombieEntity instanceof ZombieShieldEntity) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet()){
				livingEntity.removeStatusEffect(PvZCubed.FROZEN);
				livingEntity.removeStatusEffect(PvZCubed.ICE);
				livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
				livingEntity.setOnFireFor(60);
			}
		}
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/
	private int tickDamage = 5;

	public void tick() {
		super.tick();
		RandomGenerator randomGenerator = this.getRandom();
		for(int i = 0; i < 3; ++i) {
			double e = this.random.nextDouble() / 4 * ((this.random.range(0, 1)) + 0.1f);
			this.getWorld().addParticle(ParticleTypes.FLAME, this.getX()  + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
					this.getY(), this.getZ() + (double)MathHelper.nextBetween(randomGenerator,
							-0.5F, 0.5F), 0, e, 0);
		}
		if (this.isInsideWall()){
			this.setPosition(this.getX(), this.getY() + 1, this.getZ());
		}
		if (!this.isOnGround()){
			this.setPosition(this.getX(), this.getY() - 1, this.getZ());
		}
		if (this.getTarget() != null){
			this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
		}
		if (!this.isAiDisabled() && this.isAlive()) {
			setPosition(this.getX(), this.getY(), this.getZ());
		}
		if (this.getWorld() instanceof ServerWorld) {
			if (this.age <= ageMax) {
				if (--tickDamage <= 0) {
					this.damageEntity();
					tickDamage = 5;
				}
			}
			else {
				this.discard();
			}
		}
		if (this.isWet()){
			playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH);
			this.discard();
		}
		if (!this.getWorld().isClient()) {
			List<GeneralPvZombieEntity> list = getWorld().getNonSpectatingEntities(GeneralPvZombieEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(5));
			for (GeneralPvZombieEntity generalPvZombieEntity : list) {
				if (generalPvZombieEntity.squaredDistanceTo(this) < 36) {
					generalPvZombieEntity.setStealthTag(GeneralPvZombieEntity.Stealth.FALSE);
				}
			}
		}
		if (--heatTicks <= 0) {
			List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(1.5));
			for (LivingEntity tileEntity : list) {
				if (tileEntity instanceof SnowTile) {
					tileEntity.discard();
				}
				if (tileEntity instanceof IceTile) {
					tileEntity.discard();
				}
				if (tileEntity instanceof FireTrailEntity && this.squaredDistanceTo(tileEntity) <= 0.5f && tileEntity != this) {
					tileEntity.discard();
				}
			}
			heatTicks = 20;
		}
	}
	protected int heatTicks = 20;


	public void tickMovement() {
        super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
			this.discard();
		}
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createFireTrailAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 2D)
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
		return 0.60F;
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return null	;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_FIRE_EXTINGUISH;
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

	public boolean handleAttack(Entity attacker) {
		if (attacker instanceof PlayerEntity) {
			this.clearStatusEffects();
			playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH);
			this.discard();
		}
		return false;
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}
}
