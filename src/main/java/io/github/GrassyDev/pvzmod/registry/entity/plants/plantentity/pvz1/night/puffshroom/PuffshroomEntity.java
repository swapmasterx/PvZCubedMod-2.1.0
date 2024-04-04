package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spore.SporeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biomes;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PuffshroomEntity extends PlantEntity implements GeoAnimatable, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private String controllerName = "puffcontroller";

	public boolean isFiring;

    public PuffshroomEntity(EntityType<? extends PuffshroomEntity> entityType, World world) {
        super(entityType, world);

		this.nocturnal = true;
		this.targetPoison = true;
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
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
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
		if (entityData instanceof PuffshroomEntity.PlantData plantData && !spawnReason.equals(SpawnReason.SPAWN_EGG)) {
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
			DataTracker.registerData(PuffshroomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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

	public void setPuffshroomPermanency(PuffshroomEntity.PuffPermanency puffshroomPermanency) {
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

	@Override
	public double getTick(Object object) {
		return 0;
	}

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getIsAsleep()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("puffshroom.asleep"));
		} else if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("puffshroom.shoot"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("puffshroom.idle"));
		}
		return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new PuffshroomEntity.FireBeamGoal(this));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {

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

	boolean sleepSwitch = false;
	boolean awakeSwitch = false;

	public void tick() {
		if (!this.getWorld().isClient && !this.getCofee()) {
			if ((this.getWorld().getAmbientDarkness() >= 2 ||
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
					this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(Biomes.MUSHROOM_FIELDS)))) {
				this.setIsAsleep(IsAsleep.FALSE);
			} else if (this.getWorld().getAmbientDarkness() < 2 &&
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
					!this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(Biomes.MUSHROOM_FIELDS))) {
				this.setIsAsleep(IsAsleep.TRUE);
			}
		}
		if (this.getIsAsleep()){
			this.setTarget(null);
		}
		else {
			this.targetZombies(this.getPos(), 3, false, false, false);
		}
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.PUFFSHROOM_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (this.age >= 900 && !this.getPuffshroomPermanency()) {
			this.discard();
		}
		float time = 200 / this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
		if (this.age > 4 && this.age <= time && !this.getPuffshroomPermanency() && !this.hasStatusEffect(StatusEffects.GLOWING)) {
			if (this.getWorld().getGameRules().getBoolean(PvZCubed.PLANTS_GLOW)) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, (int) Math.floor(time), 1)));
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.PUFFSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/


	public static DefaultAttributeContainer.Builder createPuffshroomAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6D);
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

	public static boolean canPuffshroomSpawn(EntityType<? extends PuffshroomEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		float cavespawn = random.nextFloat();
		if (cavespawn <= 0.66) {
			return (!world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
					!checkPlant(Vec3d.ofCenter(pos), world, type) &&
					pos.getY() > 50 &&
					(!world.getBlockState(blockPos).getBlock().hasDynamicBounds() && world.getAmbientDarkness() >= 2 ||
					world.getLightLevel(LightType.SKY, pos) < 2 ||
					(world.getBiome(blockPos).getKey().equals(Optional.ofNullable(Biomes.MUSHROOM_FIELDS))) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_SPAWN) && PVZCONFIG.nestedSpawns.spawnPlants()));
		}
		else {
			return (!world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
					!checkPlant(Vec3d.ofCenter(pos), world, type) &&
					(!world.getBlockState(blockPos).getBlock().hasDynamicBounds() && world.getAmbientDarkness() >= 2 ||
					world.getLightLevel(LightType.SKY, pos) < 2 ||
					(world.getBiome(blockPos).getKey().equals(Optional.ofNullable(Biomes.MUSHROOM_FIELDS))) && Objects.requireNonNull(world.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_SPAWN) && PVZCONFIG.nestedSpawns.spawnPlants()));
		}
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	static class FireBeamGoal extends Goal {
		private final PuffshroomEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(PuffshroomEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue() && !this.plantEntity.getIsAsleep();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity) && this.animationTicks >= 0) || this.plantEntity.getIsAsleep()){
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -7) {
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						SporeEntity proj = new SporeEntity(PvZEntity.SPORE, this.plantEntity.getWorld());
						double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
						Vec3d targetPos = livingEntity.getPos();
						double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
						double d = this.plantEntity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.plantEntity.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
						double g = predictedPos.getZ() - this.plantEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.sporeAge = 20;
						proj.setVelocity(e * (double)h, f * (double)h, g * (double)h, 0.33F, 0F);
						proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.25D, this.plantEntity.getZ());
						proj.setOwner(this.plantEntity);
						if (livingEntity != null && livingEntity.isAlive()) {
							this.beamTicks = -7;
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
							this.plantEntity.playSound(PvZSounds.MUSHROOMSHOOTEVENT, 0.3F, 1);
							this.plantEntity.getWorld().spawnEntity(proj);
						}
					}
				}
				else if (this.animationTicks >= 0)
				{
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
