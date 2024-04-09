package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.zombie;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.DefaultAndHypnoVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plantobstacle.WoodObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;

import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.*;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
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
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import org.jetbrains.annotations.Nullable;


import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class HawkerZombieEntity extends PvZombieEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";

	private int launchAnimation;
	public boolean inLaunchAnimation;

	public HawkerZombieEntity(EntityType<? extends HawkerZombieEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;

		this.experiencePoints = 3;
		this.getNavigation().setCanSwim(true);
		this.setPathfindingPenalty(PathNodeType.WATER_BORDER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.WATER, 0.0F);
		this.setPathfindingPenalty(PathNodeType.LAVA, -1.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_OTHER, 8.0F);
		this.setPathfindingPenalty(PathNodeType.POWDER_SNOW, 8.0F);
		this.setPathfindingPenalty(PathNodeType.DAMAGE_FIRE, 0.0F);
		this.setPathfindingPenalty(PathNodeType.DANGER_FIRE, 0.0F);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 104) {
			this.inLaunchAnimation = true;
		}
		else if (status == 103) {
			this.inLaunchAnimation = false;
		}
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(HawkerZombieEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.HAWKERPUSHERHYPNO)){
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(DefaultAndHypnoVariants.DEFAULT);
			this.createObstacle();
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public DefaultAndHypnoVariants getVariant() {
		return DefaultAndHypnoVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(DefaultAndHypnoVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	public void createObstacle(){
		WoodObstacleEntity woodObstacleEntity = new WoodObstacleEntity(PvZEntity.HAWKERCART, this.getWorld());
		woodObstacleEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
		woodObstacleEntity.startRiding(this);
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
		Entity entity = this.getFirstPassenger();
		if (this.isInsideWaterOrBubbleColumn()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("hawker.ducky"));
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		} else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (this.hasPassenger(entity) && entity instanceof ZombieShieldEntity){
					event.getController().setAnimation(RawAnimation.begin().thenLoop("hawker.walk"));
				}
				else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("hawker.walk2"));
				}
			} else {
				if (this.hasPassenger(entity) && entity instanceof ZombieShieldEntity){
					event.getController().setAnimation(RawAnimation.begin().thenLoop("hawker.idle"));
				}
				else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("hawker.idle2"));
				}
			}
			if (this.isFrozen || this.isStunned) {
				event.getController().setAnimationSpeed(0);
			}
			else if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.HAWKERPUSHERHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
	}

	protected void initCustomGoals() {
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));

		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof PlantEntity plantEntity &&
					!(plantEntity instanceof GravebusterEntity) && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground")) && !(plantEntity.getLowProfile());
		}));

		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));

		////////// Must-Protect Plants ///////
		this.targetSelector.add(3, new TargetGoal<>(this, GardenChallengeEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, GardenEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, SunflowerEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, TwinSunflowerEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, SunshroomEntity.class, false, true));
	}

	protected void initHypnoGoals(){

		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(1, new HypnoPvZombieAttackGoal(this, 1.0D, true));
		////////// Hypnotized Zombie targets ///////
		this.targetSelector.add(1, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof ZombiePropEntity zombiePropEntity && !(zombiePropEntity.getHypno()));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) &&
					(!(livingEntity instanceof ZombiePropEntity) || (livingEntity instanceof ZombieObstacleEntity));
		}));
		this.targetSelector.add(2, new TargetGoal<>(this, MobEntity.class, 0, true, true, (livingEntity) -> {
			return livingEntity instanceof Monster && !(livingEntity instanceof GeneralPvZombieEntity) && !(livingEntity instanceof GraveEntity);
		}));
	}

	public boolean tryAttack(Entity target) {
		if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE) && !this.inLaunchAnimation && this.getTarget() != null) {
			return super.tryAttack(this.getTarget());
		}
		else {
			return false;
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		var zombieObstacleEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombieObstacleEntity)
				.map(e -> (ZombieObstacleEntity) e)
				.findFirst();
		if (this.getAttacking() == null && !(this.getHypno()) && zombieObstacleEntity.isEmpty()){
			if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED) && !(this.CollidesWithPlant(0.1f, 0f) instanceof GravebusterEntity)){
				this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
				this.setTarget(CollidesWithPlant(0.1f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlayer(1.5f) != null && !this.CollidesWithPlayer(1.5f).isCreative()){
				this.setTarget(CollidesWithPlayer(1.5f));
				this.setStealthTag(Stealth.FALSE);
			}
		}
	}

	protected void mobTick() {
		super.mobTick();
		if (this.hasStatusEffect(PvZCubed.ICE)){
			this.animationMultiplier = 2;
		}
		else {
			this.animationMultiplier = 1;
		}
		var zombieObstacleEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombieObstacleEntity)
				.map(e -> (ZombieObstacleEntity) e)
				.findFirst();
		double random = Math.random();
		if (this.isInsideWaterOrBubbleColumn() && zombieObstacleEntity.isPresent()){
			zombieObstacleEntity.get().stopRiding();
		}
		if (zombieObstacleEntity.isEmpty() && this.CollidesWithObstacle(1f) != null && this.CollidesWithObstacle(1f).getType().equals(PvZEntity.HAWKERCART) && !this.CollidesWithObstacle(1f).hasVehicle() && !this.CollidesWithObstacle(1f).beingEaten && !this.isInsideWaterOrBubbleColumn()){
			this.CollidesWithObstacle(1f).startRiding(this, true);
		}
	}

	@Override
	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater){
		if (this.hasPassenger(passenger)) {
			float g = (float) ((this.isRemoved() ? 0.01F : this.method_52537(passenger)) + passenger.getHeightOffset(passenger));
			float f = 0.9F;

			Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
			passenger.setBodyYaw(this.bodyYaw);
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		itemStack = ModItems.HAWKEREGG.getDefaultStack();
		return itemStack;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	@Override
	protected float method_52537(Entity entity) {
		return 0.00F;
	}


	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createHawkerPusherAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.10D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.hawkerpusherH());
    }

	protected SoundEvent getAmbientSound() {
		if (!this.getHypno() && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.isFrozen && !this.isStunned && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			return PvZSounds.ZOMBIEMOANEVENT;
		}
		else {
			return null;
		}
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}



	protected SoundEvent getStepSound() {
		return SoundEvents.ENTITY_ZOMBIE_STEP;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}




	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;
	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.HAWKERPUSHER)){
			hypnoType = PvZEntity.HAWKERPUSHERHYPNO;
		}
		else {
			hypnoType = PvZEntity.HAWKERPUSHERHYPNO;
		}
	}

	public boolean damage(DamageSource source, float amount) {
		if (!super.damage(source, amount)) {
			return false;
		} else if (!(this.getWorld() instanceof ServerWorld)) {
			return false;
		} else {
			ServerWorld serverWorld = (ServerWorld)this.getWorld();
			LivingEntity livingEntity = this.getTarget();
			if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
				livingEntity = (LivingEntity)source.getAttacker();
			}

			if (this.getRecentDamageSource().isType(PvZDamageTypes.HYPNO_DAMAGE) && !(this.getHypno())) {
				checkHypno();
				this.playSound(PvZSounds.HYPNOTIZINGEVENT, 1.5F, 1.0F);
				BullyEntity hypnotizedZombie = (BullyEntity) hypnoType.create(getWorld());
				hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnotizedZombie.initialize(serverWorld, getWorld().getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData)null, (NbtCompound) null);
				hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
				if (this.hasCustomName()) {
					hypnotizedZombie.setCustomName(this.getCustomName());
					hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
				}
				for (Entity entity1 : this.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zpe.setHypno(IsHypno.TRUE);
						zpe.startRiding(hypnotizedZombie);
					}
				}

				hypnotizedZombie.setPersistent();

				hypnotizedZombie.setHeadYaw(this.getHeadYaw());
				serverWorld.spawnEntityAndPassengers(hypnotizedZombie);
				this.remove(RemovalReason.DISCARDED);
			}

			return true;
		}
	}

	public boolean killedEntity(ServerWorld world, LivingEntity entity) {
		boolean bl = super.killedEntity(world, entity);
		if ((world.getDifficulty() == Difficulty.NORMAL || world.getDifficulty() == Difficulty.HARD) && entity instanceof VillagerEntity villagerEntity) {
			if (world.getDifficulty() != Difficulty.HARD && this.random.nextBoolean()) {
				return bl;
			}

			ZombieVillagerEntity zombieVillagerEntity = (ZombieVillagerEntity)villagerEntity.convertTo(EntityType.ZOMBIE_VILLAGER, false);
			if (zombieVillagerEntity != null) {
				zombieVillagerEntity.initialize(world, world.getLocalDifficulty(zombieVillagerEntity.getBlockPos()), SpawnReason.CONVERSION, new ZombieEntity.ZombieData(false, true), (NbtCompound)null);
				zombieVillagerEntity.setVillagerData(villagerEntity.getVillagerData());
				zombieVillagerEntity.setGossipData((NbtElement)villagerEntity.getGossip().serialize(NbtOps.INSTANCE));
				zombieVillagerEntity.setOfferData(villagerEntity.getOffers().toNbt());
				zombieVillagerEntity.setXp(villagerEntity.getExperience());
				if (!this.isSilent()) {
					world.syncWorldEvent((PlayerEntity)null, 1026, this.getBlockPos(), 0);
				}

				bl = false;
			}
		}

		return bl;
	}
}
