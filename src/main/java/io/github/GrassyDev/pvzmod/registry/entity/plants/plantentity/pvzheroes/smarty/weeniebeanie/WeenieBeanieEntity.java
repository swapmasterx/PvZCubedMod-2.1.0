package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.weeniebeanie;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
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


import java.util.List;
import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class WeenieBeanieEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private String controllerName = "puffcontroller";

	private int attackTicksLeft;



    public WeenieBeanieEntity(EntityType<? extends WeenieBeanieEntity> entityType, World world) {
        super(entityType, world);

		this.isBurst = true;

    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Permanent", this.getPuffshroomPermanency());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Permanent"));
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 106) {
			this.attackTicksLeft = 20;
		} else {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	@Nullable
	@Override
	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
		entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
		if (entityData == null) {
			entityData = new PlantData(true);
		}
		if (entityData instanceof WeenieBeanieEntity.PlantData plantData && !spawnReason.equals(SpawnReason.SPAWN_EGG)) {
			this.naturalSpawn = true;
			if (plantData.tryLilyPad) {
				List<LilyPadEntity> list = world.getEntitiesByClass(
						LilyPadEntity.class, this.getBoundingBox().expand(12.5), EntityPredicates.NOT_MOUNTED
				);
				if (!list.isEmpty()) {
					LilyPadEntity lilyPadEntity = (LilyPadEntity) list.get(0);
					this.startRiding(lilyPadEntity);
				}
			}
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(WeenieBeanieEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum PuffPermanency {
		DEFAULT(false),
		PERMANENT(true);

		PuffPermanency(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getPuffshroomPermanency() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setPuffshroomPermanency(WeenieBeanieEntity.PuffPermanency puffshroomPermanency) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, puffshroomPermanency.getId());
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
		int i = this.attackTicksLeft;
		if (i <= 0) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("navybean.idle"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("navybean.hit"));
		}
		return PlayState.CONTINUE;
    }

	/** /~*~//~AI~//~*~// **/

	protected void initGoals() {
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {
		if (target.squaredDistanceTo(this) <= 9) {
			this.smack(target);
		}
	}

	public boolean smack(Entity target) {
		int i = this.attackTicksLeft;
		ZombiePropEntity passenger = null;
		for (Entity entity1 : target.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				passenger = zpe;
			}
		}
		Entity damaged = target;
		if (passenger != null){
			damaged = passenger;
		}
		if (i <= 0) {
			this.attackTicksLeft = 20;
			this.getWorld().sendEntityStatus(this, (byte) 106);
			boolean bl = damaged.damage(getDamageSources().mobAttack(this), this.getAttackDamage());
			if (bl) {
				this.applyDamageEffects(this, target);
			}
			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
			SoundEvent sound;
			sound = switch (zombieMaterial) {
				case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
				case "plastic" -> PvZSounds.CONEHITEVENT;
				case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
				default -> PvZSounds.PEAHITEVENT;
			};
			target.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
			this.chomperAudioDelay = 3;
			return bl;
		} else {
			return false;
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

	private int chomperAudioDelay = -1;

	public void tick() {
		super.tick();
		if (--this.chomperAudioDelay == 0) {
			this.playSound(PvZSounds.PEASHOOTEVENT, 1.0F, 1.0F);
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.WEENIEBEANIE_SEED_PACKET);
				}
				this.discard();
			}
		}
		this.targetZombies(this.getPos(), 2, false, false, true);
		if (this.age >= 900 && !this.getPuffshroomPermanency()) {
			this.discard();
		}
		float time = 200 / this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
		if (this.age > 4 && this.age <= time && !this.getPuffshroomPermanency() && !this.hasStatusEffect(StatusEffects.GLOWING)) {
			if (this.getWorld().getGameRules().getBooleanValue(PvZCubed.PLANTS_GLOW)) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, (int) Math.floor(time), 1)));
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.WEENIEBEANIE_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/


	public static DefaultAttributeContainer.Builder createWeenieBeanieAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8D);
    }

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
	}

	private float getAttackDamage() {
		return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
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



	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*SPAWNING*~//~*~/ **/

	public static boolean canWeenieBeanieSpawn(EntityType<? extends WeenieBeanieEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		return !world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
					!checkPlant(Vec3d.ofCenter(pos), world, type) &&
					!world.getBlockState(blockPos).getBlock().hasDynamicBounds() && world.getAmbientDarkness() < 4 &&
				world.getLightLevel(LightType.SKY, pos) > 10 && Objects.requireNonNull(world.getServer()).getGameRules().getBooleanValue(PvZCubed.SHOULD_PLANT_SPAWN) && PVZCONFIG.nestedSpawns.spawnPlants();
	}
}
