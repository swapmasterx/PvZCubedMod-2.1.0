package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.explorer;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ExplorerVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
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
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
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

import org.jetbrains.annotations.Nullable;


import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ExplorerEntity extends PvZombieEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";


	public ExplorerEntity(EntityType<? extends ExplorerEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
		this.dataTracker.startTracking(SUMMON_TIMES, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", this.getTypeVariant());
		nbt.putBoolean("Fire", this.getFireStage());
		nbt.putInt("Count", this.getTypeCount());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, nbt.getBoolean("Fire"));
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
		this.dataTracker.set(SUMMON_TIMES, nbt.getInt("Count"));
	}


	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}

		if (status == 115) {
			Vec3d vec3d2 = new Vec3d((double) 1, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			Vec3d particlePos = Vec3d.ofCenter(this.getBlockPos().add((int) vec3d2.getX(), 0, (int) vec3d2.getZ()));
			RandomGenerator randomGenerator = this.getRandom();
			for(int i = 0; i < 32; ++i) {
				double d = this.random.nextDouble() / 10 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 10 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 10 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, particlePos.getX(), particlePos.getY(), particlePos.getZ(), d, e, f);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, particlePos.getX(), particlePos.getY(), particlePos.getZ(), d, e, f);
				this.getWorld().addParticle(ParticleTypes.SMOKE, particlePos.getX(), particlePos.getY() + this.random.range(0, 1), particlePos.getZ(), d, e, f);
				this.getWorld().addParticle(ParticleTypes.FLAME, particlePos.getX(), particlePos.getY() + this.random.range(0, 1), particlePos.getZ(), d, e, f);
			}
			for(int i = 0; i < 16; ++i) {
				double e = this.random.nextDouble() / 10 * (this.random.range(0, 1));
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, particlePos.getX() + (double) MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						particlePos.getY(),
						particlePos.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
			}
		}
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(ExplorerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum FireStage {
		FIRE(true),
		EXTINGUISHED(false);

		FireStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getFireStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setFireStage(ExplorerEntity.FireStage fireStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, fireStage.getId());
	}

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ExplorerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.TORCHLIGHT)){
			setVariant(ExplorerVariants.TORCHLIGHT);
			setFireStage(FireStage.FIRE);
			setCanBurn(CanBurn.FALSE);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.EXPLORERHYPNO)){
			setVariant(ExplorerVariants.EXPLORERHYPNO);
			this.setHypno(IsHypno.TRUE);
			setFireStage(FireStage.FIRE);
		}
		else if (this.getType().equals(PvZEntity.TORCHLIGHTHYPNO)){
			setVariant(ExplorerVariants.TORCHLIGHTHYPNO);
			setFireStage(FireStage.FIRE);
			setCanBurn(CanBurn.FALSE);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(ExplorerVariants.EXPLORER);
			setFireStage(FireStage.FIRE);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ExplorerVariants getVariant() {
		return ExplorerVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(ExplorerVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	//Charm Counter

	private static final TrackedData<Integer> SUMMON_TIMES =
			DataTracker.registerData(ExplorerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public int getTypeCount() {
		return this.dataTracker.get(SUMMON_TIMES);
	}

	public void setCount(Integer count) {
		this.dataTracker.set(SUMMON_TIMES, count);
	}

	public void addCount(){
		int count = getTypeCount();
		this.dataTracker.set(SUMMON_TIMES, count + 1);
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
		if (this.isInsideWaterOrBubbleColumn()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("flagzombie.ducky"));
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("flagzombie.walking"));
				if (this.isFrozen || this.isStunned) {
					event.getController().setAnimationSpeed(0);
				}
				else if (this.isIced) {
					event.getController().setAnimationSpeed(0.7);
				}
				else {
					event.getController().setAnimationSpeed(1.4);
				}
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("flagzombie.idle"));
				if (this.isFrozen || this.isStunned) {
					event.getController().setAnimationSpeed(0);
				} else if (this.isIced) {
					event.getController().setAnimationSpeed(0.5);
				} else {
					event.getController().setAnimationSpeed(1);
				}
			}
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.EXPLORERHYPNO) ||
				this.getType().equals(PvZEntity.TORCHLIGHTHYPNO)) {
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
			return livingEntity instanceof PlantEntity plantEntity && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground")) && !(plantEntity.getLowProfile()) && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying"));
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

	public void initHypnoGoals(){
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
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


	/** /~*~//~*TICKING*~//~*~/ **/

	private boolean isBeingRainedOn() {
		BlockPos blockPos = this.getBlockPos();
		return this.getWorld().hasRain(blockPos) || this.getWorld().hasRain(new BlockPos((int) blockPos.getX(), (int) this.getBoundingBox().maxY, (int) blockPos.getZ()));
	}

	protected int createTileTicks = 100;

	public void tick() {
		if (this.getType().equals(PvZEntity.TORCHLIGHT) || this.getType().equals(PvZEntity.TORCHLIGHTHYPNO)){
			setCanBurn(CanBurn.FALSE);
		}
		super.tick();
		if (this.getVariant().equals(ExplorerVariants.TORCHLIGHT) && this.getFireStage()) {
			double random = Math.random();
			if (--createTileTicks <= 0) {
				if (random <= 0.5 && HasTile(this.getBlockPos()) == null && this.getTarget() != null && this.isOnGround() && !this.isInsideWaterOrBubbleColumn() && this.getTypeCount() <= 3) {
					if (getWorld().getBlockState(this.getBlockPos()).isOf(Blocks.AIR) || getWorld().getBlockState(this.getBlockPos()).isOf(Blocks.CAVE_AIR)){
						createScorchedTile(this.getBlockPos());
					}
					this.addCount();
				}
				createTileTicks = 100;
			}
		}
		if (this.isBeingRainedOn() || this.hasStatusEffect(PvZCubed.ICE) || this.hasStatusEffect(PvZCubed.FROZEN) || this.hasStatusEffect(PvZCubed.WET) || this.isSubmergedInWater()){
			this.setFireStage(FireStage.EXTINGUISHED);
		}
		else if (this.isOnFire() || this.hasStatusEffect(PvZCubed.WARM)){
			this.setFireStage(FireStage.FIRE);
		}
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) instanceof PlantEntity plantEntity && !this.hasStatusEffect(PvZCubed.BOUNCED)) {
				if (!plantEntity.onWater && !plantEntity.getFireImmune() && this.getFireStage()) {
					BlockPos blockPos = plantEntity.getBlockPos();
					boolean bl = plantEntity.hasVehicle();
					boolean bl2 = false;
					boolean bl3 = plantEntity.onWater;
					LivingEntity vehicle = null;
					if (bl){
						vehicle = (LivingEntity) plantEntity.getVehicle();
						if (vehicle instanceof PlantEntity plantEntity2){
							if (plantEntity2.onWater){
								bl2 = true;
							}
						}
					}
					plantEntity.damage(getDamageSources().mobAttack(this), Integer.MAX_VALUE);
					if (vehicle != null) {
						vehicle.damage(getDamageSources().mobAttack(this), Integer.MAX_VALUE);
					}
					if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) == null) {
						this.getWorld().sendEntityStatus(this, (byte) 115);
					}
					if (!bl2 && !bl3 && this.getVariant().equals(ExplorerVariants.TORCHLIGHT) && this.CollidesWithPlant(0.1f, 0f) == null) {
						if (this.getWorld() instanceof ServerWorld) {
							if (getWorld().getBlockState(blockPos).isOf(Blocks.AIR) || getWorld().getBlockState(blockPos).isOf(Blocks.CAVE_AIR)){
								createScorchedTile(blockPos);
							}
							this.addCount();
						}
					}
				} else if (!this.hasStatusEffect(PvZCubed.BOUNCED)) {
					this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
					this.setTarget(CollidesWithPlant(0.1f, 0f));
				this.setStealthTag(Stealth.FALSE);
				}
			}
			else if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)) {
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

	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(ExplorerVariants.TORCHLIGHT) || this.getVariant().equals(ExplorerVariants.TORCHLIGHTHYPNO)){
			itemStack = ModItems.TORCHLIGHTEGG.getDefaultStack();
		}
		else {
			itemStack = ModItems.EXPLOREREGG.getDefaultStack();
		}
		return itemStack;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/


	@Override
	protected float method_52537(Entity entity) {
		return 0.0F;
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createExplorerAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.explorerH());
    }

	public static DefaultAttributeContainer.Builder createTorchlightAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.torchlightH());
	}

	protected SoundEvent getAmbientSound() {
		if (!this.getHypno() && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.isFrozen && !this.isStunned && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			return PvZSounds.PVZOMBIEMOANEVENT;
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
		if (this.getType().equals(PvZEntity.TORCHLIGHT)){
			hypnoType = PvZEntity.TORCHLIGHTHYPNO;
		}
		else {
			hypnoType = PvZEntity.EXPLORERHYPNO;
		}
	}

	public boolean damage(DamageSource source, float amount) {
        if (!super.damage(source, amount)) {
            return false;
        } else if (!(this.getWorld() instanceof ServerWorld)) {
            return false;
        }
		else {
            ServerWorld serverWorld = (ServerWorld)this.getWorld();
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

			if (this.getRecentDamageSource().isType(PvZDamageTypes.HYPNO_DAMAGE) && !(this.getHypno())) {
				checkHypno();
                this.playSound(PvZSounds.HYPNOTIZINGEVENT, 1.5F, 1.0F);
                ExplorerEntity hypnotizedZombie = (ExplorerEntity) hypnoType.create(getWorld());
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
