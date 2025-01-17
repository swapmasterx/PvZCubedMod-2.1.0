package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine;

import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
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
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class PotatomineEntity extends PlantEntity implements GeoEntity {
	private String controllerName = "potatocontroller";
    private static final TrackedData<Integer> FUSE_SPEED;
    private static final TrackedData<Boolean> CHARGED;
    private static final TrackedData<Boolean> IGNITED;
    private int currentFuseTime;
    private int fuseTime = 1;
	private int explosionRadius = 1;
	private int potatoAnimationTime;
	private int potatoPreparingTime;
	private boolean canAnimate;
	private boolean playSoundRise;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public PotatomineEntity(EntityType<? extends PotatomineEntity> entityType, World world) {
        super(entityType, world);

		this.potatoPreparingTime = 1200;
		potatoAnimationTime = 10;
		playSoundRise = true;
		this.isBurst = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(FUSE_SPEED, -1);
		this.dataTracker.startTracking(CHARGED, false);
		this.dataTracker.startTracking(IGNITED, false);
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		if ((Boolean)this.dataTracker.get(CHARGED)) {
			tag.putBoolean("powered", true);
		}

		tag.putShort("Fuse", (short)this.fuseTime);
		tag.putByte("ExplosionRadius", (byte)this.explosionRadius);
		tag.putBoolean("ignited", this.getIgnited());
		tag.putBoolean("Prepared", this.getPotatoStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(CHARGED, tag.getBoolean("powered"));
		if (tag.contains("Fuse", 99)) {
			this.fuseTime = tag.getShort("Fuse");
		}

		if (tag.contains("ExplosionRadius", 99)) {
			this.explosionRadius = tag.getByte("ExplosionRadius");
		}

		if (tag.getBoolean("ignited")) {
			this.ignite();
		}

		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Prepared"));
	}


	static {
		FUSE_SPEED = DataTracker.registerData(PotatomineEntity.class, TrackedDataHandlerRegistry.INTEGER);
		CHARGED = DataTracker.registerData(PotatomineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
		IGNITED = DataTracker.registerData(PotatomineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}

		ItemStack itemStack = Items.POTATO.getDefaultStack();
		if (status == 80) {
			for(int i = 0; i < 96; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX(), this.getY(), this.getZ(), d, e, f);
				this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX(), this.getY(), this.getZ(), d * -1, e, f * -1);
				this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX(), this.getY(), this.getZ(), d * -1, e, f);
				this.getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, itemStack), this.getX(), this.getY(), this.getZ(), d, e, f * -1);
			}
			for(int i = 0; i < 8; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1) * 0.33;
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1) * 2);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1) * 0.33;
				this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 8; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double e = this.random.nextDouble() / 2 * (this.random.range(-1, 1) * 0.5);
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (this.random.range(-1, 1)),
						this.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
		BlockState blockState = this.getLandingBlockState();
		if (status == 108) {
			for(int i = 0; i < 4; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double d = this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
				double e = this.getY();
				double f = this.getZ() + (double)MathHelper.nextBetween(randomGenerator, -0.7F, 0.7F);
				this.getWorld().addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, blockState), d, e, f, 0.0, 0.0, 0.0);
			}
		}
		if (status == 109) {
			canAnimate = true;
		}
		if (status == 107) {
			canAnimate = false;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(PotatomineEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum PotatoStage {
		UNARMED(false),
		PREPARED(true);

		PotatoStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getPotatoStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	private void setPotatoStage(PotatomineEntity.PotatoStage potatoStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, potatoStage.getId());
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
		if (canAnimate) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("potatomine.ready"));
		}
		else if (this.getPotatoStage()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("potatomine.idle"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("potatomine.unarmed"));
		}
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(2, new PotatoIgniteGoal(this));
	}


	public boolean tryAttack(Entity target) {
		return true;
	}

	public int getFuseSpeed() {
		return (Integer)this.dataTracker.get(FUSE_SPEED);
	}

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(FUSE_SPEED, fuseSpeed);
	}

	public boolean getIgnited() {
		return (Boolean)this.dataTracker.get(IGNITED);
	}

	public void ignite() {
		this.dataTracker.set(IGNITED, true);
	}
	List<LivingEntity> checkList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	private void raycastExplode() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(2.5));
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
			} while (this.squaredDistanceTo(livingEntity) > 6.25);

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
			if (((livingEntity instanceof Monster &&
					zombiePropEntity4 == null &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity2 && checkList.contains(generalPvZombieEntity2.getOwner())) &&
					(!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 && generalPvZombieEntity1.isFlying())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity))) {
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
				if (damage > livingEntity.getHealth() &&
						!(livingEntity instanceof ZombieShieldEntity) &&
						livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					float damage2 = damage - livingEntity.getHealth();
					livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					generalPvZombieEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage2);
					checkList.add(livingEntity);
					checkList.add(generalPvZombieEntity);
				} else if (livingEntity instanceof ZombieShieldEntity zombieShieldEntity && zombieShieldEntity.getVehicle() != null) {
					zombieShieldEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					checkList.add(zombieShieldEntity);
				} else if (livingEntity.getVehicle() instanceof ZombieShieldEntity zombieShieldEntity) {

					zombieShieldEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
					checkList.add(livingEntity);
					checkList.add(zombieShieldEntity);
				} else {
					if (livingEntity instanceof ZombiePropEntity zombiePropEntity && livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						checkList.add(livingEntity);
						checkList.add(generalPvZombieEntity);
					} else if (livingEntity instanceof ZombieVehicleEntity) {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						checkList.add(livingEntity);
					} else if (zombiePropEntity2 == null && !checkList.contains(livingEntity)) {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						checkList.add(livingEntity);
					} else if (livingEntity instanceof ZombieVehicleEntity && !checkList.contains(livingEntity)) {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
						checkList.add(livingEntity);
					}
				}
			}
		}
	}

	private void spawnEffectsCloud() {
		Collection<StatusEffectInstance> collection = this.getStatusEffects();
		if (!collection.isEmpty()) {
			AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
			areaEffectCloudEntity.setRadius(3.5F);
			areaEffectCloudEntity.setRadiusOnUse(-0.5F);
			areaEffectCloudEntity.setWaitTime(10);
			areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 2);
			areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float)areaEffectCloudEntity.getDuration());
			Iterator var3 = collection.iterator();

			while(var3.hasNext()) {
				StatusEffectInstance statusEffectInstance = (StatusEffectInstance)var3.next();
				areaEffectCloudEntity.addEffect(new StatusEffectInstance(statusEffectInstance));
			}

			this.getWorld().spawnEntity(areaEffectCloudEntity);
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


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getVehicle() instanceof LilyPadEntity ||
				this.getVehicle() instanceof BubblePadEntity){
			this.discard();
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.POTATOMINE_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (this.isAlive() && this.potatoPreparingTime > 0){
			--this.potatoPreparingTime;
		}
		if (this.getPotatoStage()){
			this.targetZombies(this.getPos(), 2, false, false, true);
		}
		else {
			this.setTarget(null);
		}
		if (this.isAlive() && this.potatoPreparingTime <= 0 && this.potatoAnimationTime > 0 && !this.getPotatoStage()) {
			--this.potatoAnimationTime;
			this.getWorld().sendEntityStatus(this, (byte) 109);
			this.getWorld().sendEntityStatus(this, (byte) 108);
		}
		if (this.isAlive() && this.potatoPreparingTime <= 0 && this.potatoAnimationTime > 0 && !this.getPotatoStage() && this.playSoundRise) {
			this.playSound(PvZSounds.ENTITYRISINGEVENT, 1.0F, 1.0F);
			this.playSoundRise = false;
		}
		if (this.isAlive() && this.potatoAnimationTime <= 0) {
			this.setPotatoStage(PotatoStage.PREPARED);
			this.getWorld().sendEntityStatus(this, (byte) 107);
		}
		if (this.isAlive() && this.getPotatoStage()) {
			if (this.getIgnited()) {
				this.setFuseSpeed(1);
			}

			int i = this.getFuseSpeed();
			if (i > 0 && this.currentFuseTime == 0) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999, 999999999)));
			}

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
				removeStatusEffect(StatusEffects.RESISTANCE);
			}

			if (this.currentFuseTime >= this.fuseTime) {
				this.currentFuseTime = this.fuseTime;
				this.raycastExplode();
				this.removeStatusEffect(StatusEffects.RESISTANCE);
				this.getWorld().sendEntityStatus(this, (byte) 80);
				this.playSound(PvZSounds.POTATOMINEEXPLOSIONEVENT, 1F, 1F);
				this.spawnEffectsCloud();
				this.dead = true;
				this.remove(RemovalReason.DISCARDED);
			}
		}
		if (this.getTarget() instanceof Entity && this.getPotatoStage()){
			this.addStatusEffect((new StatusEffectInstance(StatusEffects.RESISTANCE, 999999999, 999999999)));
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

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.SMALLSUN) && !this.getPotatoStage()) {
			this.potatoPreparingTime = 0;
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.POTATOMINE_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createPotatomineAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 0.75D)
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
		return PvZSounds.SILENCEVENET;
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
		}
		else if (this.getPotatoStage()) {
			this.raycastExplode();
			this.removeStatusEffect(StatusEffects.RESISTANCE);
			this.getWorld().sendEntityStatus(this, (byte) 80);
			this.playSound(PvZSounds.POTATOMINEEXPLOSIONEVENT, 1F, 1F);
			this.spawnEffectsCloud();
			this.dead = true;
			this.remove(RemovalReason.DISCARDED);
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		float damage = 180;
		LivingEntity attacker = (LivingEntity) source.getAttacker();
		if (attacker instanceof GargantuarEntity && this.getPotatoStage() && attacker.isAlive()){
			ZombiePropEntity zombiePropEntity2 = null;
			for (Entity entity1 : attacker.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe) {
					zombiePropEntity2 = zpe;
				}
			}
			if (zombiePropEntity2 != null){
				float damage2 = damage - zombiePropEntity2.getHealth();
				zombiePropEntity2.damage(getDamageSources().mobProjectile(this, this), damage);
				attacker.damage(getDamageSources().mobProjectile(this, this), damage2);
			}
			else {
				attacker.damage(getDamageSources().mobProjectile(this, this), damage);
			}
		}
		super.onDeath(source);
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
