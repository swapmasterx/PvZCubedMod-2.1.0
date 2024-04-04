package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.chillypepper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
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

public class ChillyPepperEntity extends PlantEntity implements GeoAnimatable {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private static final TrackedData<Integer> FUSE_SPEED;
	private static final TrackedData<Boolean> CHARGED;
	private static final TrackedData<Boolean> IGNITED;
	private int currentFuseTime;
	private int fuseTime = 30;
	private int explosionRadius = 1;
	private String controllerName = "bombcontroller";

	public ChillyPepperEntity(EntityType<? extends ChillyPepperEntity> entityType, World world) {
		super(entityType, world);

		this.isBurst = true;
		this.setImmune(Immune.TRUE);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		if ((Boolean) this.dataTracker.get(CHARGED)) {
			nbt.putBoolean("powered", true);
		}

		nbt.putShort("Fuse", (short) this.fuseTime);
		nbt.putByte("ExplosionRadius", (byte) this.explosionRadius);
		nbt.putBoolean("ignited", this.getIgnited());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(CHARGED, nbt.getBoolean("powered"));
		if (nbt.contains("Fuse", 99)) {
			this.fuseTime = nbt.getShort("Fuse");
		}

		if (nbt.contains("ExplosionRadius", 99)) {
			this.explosionRadius = nbt.getByte("ExplosionRadius");
		}

		if (nbt.getBoolean("ignited")) {
			this.ignite();
		}

	}

	static {
		FUSE_SPEED = DataTracker.registerData(ChillyPepperEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(ChillyPepperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(ChillyPepperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}


	/**
	 * /~*~//~*GECKOLIB ANIMATION*~//~*~/
	 **/

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
		int i = this.getFuseSpeed();
		if (i > 0) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("jalapeno.explosion"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("jalapeno.idle"));
		}
		return PlayState.CONTINUE;
	}


	/**
	 * /~*~//~*AI*~//~*~/
	 **/

	protected void initGoals() {
		this.goalSelector.add(2, new ChillyPepperIgniteGoal(this));
	}

	public boolean tryAttack(Entity target) {
		return true;
	}

	public int getFuseSpeed() {
		return (Integer) this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean getIgnited() {
		return (Boolean) this.dataTracker.get(IGNITED);
	}

	public void ignite() {
		this.dataTracker.set(IGNITED, true);
	}

	private float boxOffset;
	List<LivingEntity> checkList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	private void raycastExplode() {
		Vec3d vec3d2 = new Vec3d((double) 0, 0.0, boxOffset).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1, 4, 1).offset(vec3d2).offset(0, -1.5, 0));
		Vec3d vec3d3 = this.getBoundingBox().offset(vec3d2).getCenter();
		IceTile iceTile = (IceTile) PvZEntity.ICETILE.create(getWorld());
		iceTile.refreshPositionAndAngles(vec3d3.getX(), this.getY(), vec3d3.getZ(), 0, 0);
		iceTile.setHeadYaw(0);
		List<IceTile> listFlames = this.getWorld().getNonSpectatingEntities(IceTile.class, iceTile.getBoundingBox());
		if (listFlames.isEmpty()) {
			getWorld().spawnEntity(iceTile);
		}
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
			} while (this.squaredDistanceTo(livingEntity) > 100);


			if (livingEntity instanceof ScorchedTile){
				livingEntity.discard();
			}
			float damage = 90;
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
			if ("crystal".equals(zombieMaterial) || "gold".equals(zombieMaterial) || "cloth".equals(zombieMaterial)) {
				damage = damage / 2;
			}
			if (livingEntity instanceof ScorchedTile) {
				livingEntity.discard();
			}
			if (((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering()) &&
						!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity2 && checkList.contains(generalPvZombieEntity2.getOwner())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity))) {
				ZombiePropEntity zombiePropEntity2 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity2 = zpe;
					}
				}
				if (damage > livingEntity.getHealth() &&
						!(livingEntity instanceof ZombieShieldEntity) &&
						livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - livingEntity.getHealth();
					livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
					generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this), damage2);
					checkList.add(livingEntity);
					checkList.add(generalPvZombieEntity);
				} else if (zombiePropEntity2 instanceof ZombieShieldEntity) {
					livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
					checkList.add(livingEntity);
				} else {
					if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (zombiePropEntity2 == null && !checkList.contains(livingEntity)) {
						livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
						checkList.add(livingEntity);
					}
				}
				if (!(livingEntity instanceof ZombieShieldEntity) && !(zombiePropEntity2 instanceof ZombieShieldEntity)) {
					livingEntity.removeStatusEffect(PvZCubed.FROZEN);
					livingEntity.removeStatusEffect(PvZCubed.ICE);
					if (livingEntity.hasStatusEffect(PvZCubed.WARM) || livingEntity.isOnFire()) {
						livingEntity.removeStatusEffect(PvZCubed.WARM);
						livingEntity.extinguish();
					}
					livingEntity.removeStatusEffect(PvZCubed.STUN);
					livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.FROZEN, 200, 5)));
				}
			}
		}
	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		if (this.getTarget() == null || source.getAttacker() instanceof PlayerEntity || source.isTypeIn(DamageTypeTags.BYPASSES_COOLDOWN)) {
			super.applyDamage(source, amount);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/


	public void tick() {
		super.tick();
		this.targetZombies(this.getPos(), 6, true, false, true);
		RandomGenerator randomGenerator = this.getRandom();
		if (this.getTarget() != null){
			this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.CHILLYPEPPER_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (this.isAlive()) {
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
				for(int j = 0; j < 16; ++j) {
					double e = (double)MathHelper.nextBetween(randomGenerator, 0.025F, 0.075F);
					this.getWorld().addParticle(ParticleTypes.SNOWFLAKE, this.getX(), this.getY() + 0.75, this.getZ(), 0, e, 0);
				}
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				this.playSound(PvZSounds.JALAPENOEXPLOSIONEVENT, 0.25F, 1F);
				this.playSound(PvZSounds.ICEBERGEXPLOSIONEVENT, 3.5F, 1F);
				this.playSound(PvZSounds.SNOWPEAHITEVENT, 2.5F, 1F);
				for (int u = -9; u < 10; ++u) {
					this.boxOffset = (float) u;
					this.raycastExplode();
				}
				this.getWorld().sendEntityStatus(this, (byte) 106);
				this.dead = true;
				this.remove(RemovalReason.DISCARDED);
			}
		}
	}


	public void tickMovement() {
        super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.clearStatusEffects();
			this.discard();
		}
    }


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.CHILLYPEPPER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder creatChillyPepperAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 90);
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
		return PvZSounds.PLANTPLANTEDEVENT;
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
			PlayerEntity playerEntity = (PlayerEntity) attacker;
			this.clearStatusEffects();
			return this.damage(getDamageSources().playerAttack(playerEntity), 9999.0F);
		} else {
			return false;
		}
	}

	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}
}
