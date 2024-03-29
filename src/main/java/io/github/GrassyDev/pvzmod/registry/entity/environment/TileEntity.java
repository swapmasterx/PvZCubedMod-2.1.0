package io.github.GrassyDev.pvzmod.registry.entity.environment;

import io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile.BananaTile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


public abstract class TileEntity extends PathAwareEntity implements IAnimatable {

    private AnimationFactory factory = GeckoLibUtil.createFactory(this);
	private String controllerName = "firetrailcontroller";

	public float damageMultiplier = 1;

    public TileEntity(EntityType<? extends TileEntity> entityType, World world) {
        super(entityType, world);

    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SHADOW, false);
		this.dataTracker.startTracking(MOON, false);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Shadow", this.getShadowPowered());
		tag.putBoolean("Moon", this.getMoonPowered());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(SHADOW, tag.getBoolean("Shadow"));
		this.dataTracker.set(MOON, tag.getBoolean("Moon"));
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	protected static final TrackedData<Boolean> SHADOW =
			DataTracker.registerData(TileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum Shadow {
		FALSE(false),
		TRUE(true);

		Shadow(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getShadowPowered() {
		return this.dataTracker.get(SHADOW);
	}

	public void setShadowPowered(Shadow shadowPowered) {
		this.dataTracker.set(SHADOW, shadowPowered.getId());
	}

	protected static final TrackedData<Boolean> MOON =
			DataTracker.registerData(TileEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum Moon {
		FALSE(false),
		TRUE(true);

		Moon(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getMoonPowered() {
		return this.dataTracker.get(MOON);
	}

	public void setMoonPowered(Moon shadowPowered) {
		this.dataTracker.set(MOON, shadowPowered.getId());
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);
		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		event.getController().setAnimation(new AnimationBuilder().loop("tile.anim"));
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (this instanceof BananaTile){
			super.applyDamage(source, amount);
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/



	public void tick() {
		this.clearStatusEffects();
		if (this.isInsideWall()){
			this.setPosition(this.getX(), this.getY() + 1, this.getZ());
		}
		if (!this.hasNoGravity()) {
			if (!this.onGround && this.age > 5) {
				this.setPosition(this.getX(), this.getY() - 1, this.getZ());
			}
			if (!this.isAiDisabled() && this.isAlive()) {
				setPosition(this.getX(), this.getY(), this.getZ());
			}
		}
		super.tick();
	}


	public void tickMovement() {
        super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
			this.discard();
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

	public static DefaultAttributeContainer.Builder createTileAttributes() {
        return MobEntity.createMobAttributes()
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

	public boolean handleAttack(Entity attacker) {
		return false;
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		return false;
	}
}
