package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.summer;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FlagZombieVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
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
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import org.jetbrains.annotations.Nullable;


import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class FlagSummerEntity extends SummonerEntity implements GeoEntity {

	private String controllerName = "walkingcontroller";

    private boolean isAggro;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);


    public FlagSummerEntity(EntityType<? extends FlagSummerEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 12;
        this.isAggro = false;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	static {
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}



	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(FlagSummerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.FLAGSUMMERHYPNO)){
			setVariant(FlagZombieVariants.DEFAULTHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(FlagZombieVariants.DEFAULT);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public FlagZombieVariants getVariant() {
		return FlagZombieVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(FlagZombieVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
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
		if (this.getType().equals(PvZEntity.FLAGSUMMERHYPNO)){
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
	}

	protected void initCustomGoals() {
		this.goalSelector.add(1, new FlagSummerEntity.summonZombieGoal(this));
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

	protected void initHypnoGoals(){
		this.goalSelector.add(1, new FlagSummerEntity.summonZombieGoal(this));
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

	public void tick() {
		this.removeStatusEffect(PvZCubed.ICE);
		super.tick();
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
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
		return ModItems.FLAGSUMMEREGG.getDefaultStack();
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

	public static DefaultAttributeContainer.Builder createFlagSummerAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.flagSummerH());
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



	protected SoundEvent getCastSpellSound() {
		return PvZSounds.ENTITYRISINGEVENT;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;
	protected void checkHypno(){
		hypnoType = PvZEntity.FLAGSUMMERHYPNO;
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
				FlagSummerEntity hypnotizedZombie = (FlagSummerEntity) hypnoType.create(getWorld());
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

			if (source.getAttacker() != null) {
				this.isAggro = true;
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


	/** /~*~//~*GOALS*~//~*~/ **/

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = FlagSummerEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive() && !FlagSummerEntity.this.hasStatusEffect(PvZCubed.FROZEN) && !FlagSummerEntity.this.hasStatusEffect(PvZCubed.STUN)) {
				if (FlagSummerEntity.this.isSpellcasting()) {
					return false;
				} else {
					return FlagSummerEntity.this.age >= this.startTime && FlagSummerEntity.this.getTypeCount() < 4;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = FlagSummerEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0 && !FlagSummerEntity.this.hasStatusEffect(PvZCubed.FROZEN) && !FlagSummerEntity.this.hasStatusEffect(PvZCubed.STUN) && FlagSummerEntity.this.getTypeCount() < 4;
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			FlagSummerEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = FlagSummerEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				FlagSummerEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			FlagSummerEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				FlagSummerEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				FlagSummerEntity.this.playSound(FlagSummerEntity.this.getCastSpellSound(), 1.0F, 1.0F);
			}

		}

		protected abstract void castSpell();

		protected int getInitialCooldown() {
			return 20;
		}

		protected abstract int getSpellTicks();

		protected abstract int startTimeDelay();

		@Nullable
		protected abstract SoundEvent getSoundPrepare();

		protected abstract Spell getSpell();
	}

    class summonZombieGoal extends CastSpellGoal {
        private final TargetPredicate closeZombiePredicate;
		private final FlagSummerEntity flagSummerEntity;

        private summonZombieGoal(FlagSummerEntity flagSummerEntity) {
            super();
			this.flagSummerEntity = flagSummerEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
        }

        public boolean canStart() {
            if (FlagSummerEntity.this.isAggro) {
				return super.canStart();
            } else {
                return false;
            }
        }

		protected int getSpellTicks() {
			return 100;
		}

		protected int startTimeDelay() {
			return 1180;
		}

        protected void castSpell() {

			EntityType<?> screen = PvZEntity.SCREENDOOR;
			EntityType<?> cone = PvZEntity.SUMMERCONEHEAD;
			EntityType<?> bucket = PvZEntity.SUMMERBUCKETHEAD;
			EntityType<?> coat = PvZEntity.SUMMERBASIC;
			if (this.flagSummerEntity.getHypno()){
				screen = PvZEntity.SCREENDOORHYPNO;
				cone = PvZEntity.SUMMERCONEHEADHYPNO;
				bucket = PvZEntity.SUMMERBUCKETHEADHYPNO;
				coat = PvZEntity.SUMMERBASICHYPNO;
			}

            ServerWorld serverWorld = (ServerWorld) FlagSummerEntity.this.getWorld();
            for(int b = 0; b < 1; ++b) { // 1 Screendoor
				RandomGenerator randomGenerator = FlagSummerEntity.this.getRandom();
				float random = MathHelper.nextBetween(randomGenerator, -4, 4);
				Vec3d vec3d = new Vec3d((double)-2 - FlagSummerEntity.this.random.nextInt(10), 0.0, random).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
                BlockPos blockPos = FlagSummerEntity.this.getBlockPos().add((int) vec3d.getX(), 0, (int) vec3d.getZ());
				Vec3d vec3d2 = new Vec3d((double)-2 - FlagSummerEntity.this.random.nextInt(10), 1, random).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				BlockPos blockPos2 = FlagSummerEntity.this.getBlockPos().add((int) vec3d2.getX(), 1, (int) vec3d2.getZ());
				if (!getWorld().getBlockState(blockPos).isOf(Blocks.AIR) && !getWorld().getBlockState(blockPos).isOf(Blocks.CAVE_AIR) && !getWorld().getBlockState(blockPos2).isOf(Blocks.AIR) && !getWorld().getBlockState(blockPos2).isOf(Blocks.CAVE_AIR)){
					vec3d = new Vec3d((double)-2 - FlagSummerEntity.this.random.range(0, 1), 0.0, 0.0).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					blockPos = FlagSummerEntity.this.getBlockPos().add((int) vec3d.getX(), 0, (int) vec3d.getZ());
				}
				BrowncoatEntity screendoorEntity = (BrowncoatEntity) screen.create(FlagSummerEntity.this.getWorld());
				screendoorEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
				screendoorEntity.initialize(serverWorld, FlagSummerEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				screendoorEntity.setOwner(FlagSummerEntity.this);
				if (this.flagSummerEntity.getHypno()){
					screendoorEntity.createShield();
				}
				serverWorld.spawnEntityAndPassengers(screendoorEntity);
            }
            for(int p = 0; p < 3; ++p) { // 3 Conehead
				RandomGenerator randomGenerator = FlagSummerEntity.this.getRandom();
				float random = MathHelper.nextBetween(randomGenerator, -4, 4);
				Vec3d vec3d = new Vec3d((double)-2 - FlagSummerEntity.this.random.nextInt(10), 0.0, random).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
                BlockPos blockPos = FlagSummerEntity.this.getBlockPos().add((int) vec3d.getX(), 0, (int) vec3d.getZ());
				Vec3d vec3d2 = new Vec3d((double)-2 - FlagSummerEntity.this.random.nextInt(10), 1, random).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				BlockPos blockPos2 = FlagSummerEntity.this.getBlockPos().add((int) vec3d2.getX(), 1, (int) vec3d2.getZ());
				if (!getWorld().getBlockState(blockPos).isOf(Blocks.AIR) && !getWorld().getBlockState(blockPos).isOf(Blocks.CAVE_AIR) && !getWorld().getBlockState(blockPos2).isOf(Blocks.AIR) && !getWorld().getBlockState(blockPos2).isOf(Blocks.CAVE_AIR)){
					vec3d = new Vec3d((double)-2 - FlagSummerEntity.this.random.range(0, 1), 0.0, 0.0).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					blockPos = FlagSummerEntity.this.getBlockPos().add((int) vec3d.getX(), 0, (int) vec3d.getZ());
				}
				BrowncoatEntity coneheadEntity = (BrowncoatEntity) cone.create(FlagSummerEntity.this.getWorld());
                coneheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                coneheadEntity.initialize(serverWorld, FlagSummerEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                coneheadEntity.setOwner(FlagSummerEntity.this);
				if (this.flagSummerEntity.getHypno()){
					coneheadEntity.createConeheadProp();
				}
					serverWorld.spawnEntityAndPassengers(coneheadEntity);
            }
            for(int d = 0; d < 2; ++d) { // 2 Buckethead
				RandomGenerator randomGenerator = FlagSummerEntity.this.getRandom();
				float random = MathHelper.nextBetween(randomGenerator, -4, 4);
				Vec3d vec3d = new Vec3d((double)-2 - FlagSummerEntity.this.random.nextInt(10), 0.0, random).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
                BlockPos blockPos = FlagSummerEntity.this.getBlockPos().add((int) vec3d.getX(), 0, (int) vec3d.getZ());
				Vec3d vec3d2 = new Vec3d((double)-2 - FlagSummerEntity.this.random.nextInt(10), 1, random).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				BlockPos blockPos2 = FlagSummerEntity.this.getBlockPos().add((int) vec3d2.getX(), 1, (int) vec3d2.getZ());
				if (!getWorld().getBlockState(blockPos).isOf(Blocks.AIR) && !getWorld().getBlockState(blockPos).isOf(Blocks.CAVE_AIR) && !getWorld().getBlockState(blockPos2).isOf(Blocks.AIR) && !getWorld().getBlockState(blockPos2).isOf(Blocks.CAVE_AIR)){
					vec3d = new Vec3d((double)-2 - FlagSummerEntity.this.random.range(0, 1), 0.0, 0.0).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					blockPos = FlagSummerEntity.this.getBlockPos().add((int) vec3d.getX(), 0, (int) vec3d.getZ());
				}
				BrowncoatEntity bucketheadEntity = (BrowncoatEntity) bucket.create(FlagSummerEntity.this.getWorld());
                bucketheadEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                bucketheadEntity.initialize(serverWorld, FlagSummerEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                bucketheadEntity.setOwner(FlagSummerEntity.this);
				if (this.flagSummerEntity.getHypno()){
					bucketheadEntity.createBucketProp();
				}
						serverWorld.spawnEntityAndPassengers(bucketheadEntity);
            }
            for(int t = 0; t < 6; ++t) { // 6 Browncoat
				RandomGenerator randomGenerator = FlagSummerEntity.this.getRandom();
				float random = MathHelper.nextBetween(randomGenerator, -4, 4);
				Vec3d vec3d = new Vec3d((double)-2 - FlagSummerEntity.this.random.nextInt(10), 0.0, random).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
                BlockPos blockPos = FlagSummerEntity.this.getBlockPos().add((int) vec3d.getX(), 0, (int) vec3d.getZ());Vec3d vec3d2 = new Vec3d((double)-2 - FlagSummerEntity.this.random.nextInt(10), 1, random).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				BlockPos blockPos2 = FlagSummerEntity.this.getBlockPos().add((int) vec3d2.getX(), 1, (int) vec3d2.getZ());
				if (!getWorld().getBlockState(blockPos).isOf(Blocks.AIR) && !getWorld().getBlockState(blockPos).isOf(Blocks.CAVE_AIR) && !getWorld().getBlockState(blockPos2).isOf(Blocks.AIR) && !getWorld().getBlockState(blockPos2).isOf(Blocks.CAVE_AIR)){
					vec3d = new Vec3d((double)-2 - FlagSummerEntity.this.random.range(0, 1), 0.0, 0.0).rotateY(-FlagSummerEntity.this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					blockPos = FlagSummerEntity.this.getBlockPos().add((int) vec3d.getX(), 0, (int) vec3d.getZ());
				}
                BrowncoatEntity browncoatEntity = (BrowncoatEntity) coat.create(FlagSummerEntity.this.getWorld());
                browncoatEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                browncoatEntity.initialize(serverWorld, FlagSummerEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null, (NbtCompound)null);
                browncoatEntity.setOwner(FlagSummerEntity.this);
				((ServerWorld) getWorld()).spawnEntityAndPassengers(browncoatEntity);
            }
			FlagSummerEntity.this.addCount();
        }

        protected SoundEvent getSoundPrepare() {
            return PvZSounds.GRAVERISINGEVENT;
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
