package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.pharaoh;


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
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.PharaohVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.stonehelmet.StoneHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
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

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class PharaohEntity extends PvZombieEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";

	public boolean speedSwitch;
	protected int summonTicks = 60;
	protected int animationTicks;
	public static final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

	public PharaohEntity(EntityType<? extends PharaohEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(DATA_ID_SUMMON, false);
		this.dataTracker.startTracking(SUMMON_TIMES, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", this.getTypeVariant());
		nbt.putBoolean("Summoning", this.getSummoning());
		nbt.putInt("Count", this.getTypeCount());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
		this.dataTracker.set(DATA_ID_SUMMON, nbt.getBoolean("Summoning"));
		this.dataTracker.set(SUMMON_TIMES, nbt.getInt("Count"));
	}



	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	// Summoning Tag

	protected static final TrackedData<Boolean> DATA_ID_SUMMON =
			DataTracker.registerData(PharaohEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum IsSummoning {
		FALSE(false),
		TRUE(true);

		IsSummoning(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getSummoning() {
		return this.dataTracker.get(DATA_ID_SUMMON);
	}

	public void setSummoning(PharaohEntity.IsSummoning summoning) {
		this.dataTracker.set(DATA_ID_SUMMON, summoning.getId());
	}

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(PharaohEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		setCount(0);
		if (this.getType().equals(PvZEntity.UNDYINGPHARAOH)){
			setVariant(PharaohVariants.UNDYING);
			createSarcophagusProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.PHARAOH)){
			setVariant(PharaohVariants.SUMMONED);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.UNDYINGPHARAOHHYPNO)){
			setVariant(PharaohVariants.UNDYINGHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.PHARAOHHYPNO)){
			setVariant(PharaohVariants.SUMMONEDHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(PharaohVariants.SUMMONED);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public PharaohVariants getVariant() {
		return PharaohVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(PharaohVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	//Charm Counter

	private static final TrackedData<Integer> SUMMON_TIMES =
			DataTracker.registerData(PharaohEntity.class, TrackedDataHandlerRegistry.INTEGER);

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


	public void createSarcophagusProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			StoneHelmetEntity propentity = new StoneHelmetEntity(PvZEntity.SARCOPHAGUS, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createPharaoh(){
		Vec3d vec3d2 = new Vec3d((double) 0.5, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		if (getWorld() instanceof ServerWorld serverWorld) {
			PharaohEntity pharaohEntity = new PharaohEntity(PvZEntity.PHARAOH, this.getWorld());
			Vec3d vec3d3 = new Vec3d(this.getPos().getX() + vec3d2.getX(), this.getPos().getY() + vec3d2.getY(), this.getPos().getZ() + vec3d2.getZ());
			pharaohEntity.refreshPositionAndAngles(vec3d3.getX(), this.getY(), vec3d3.getZ(), this.bodyYaw, 0.0F);
			pharaohEntity.setPosition(vec3d3.getX(), this.getY(), vec3d3.getZ());
			pharaohEntity.setHeadYaw(this.getHeadYaw());
			pharaohEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			pharaohEntity.setRainbowTag(Rainbow.TRUE);
			pharaohEntity.rainbowTicks = 40;
			serverWorld.spawnEntityAndPassengers(pharaohEntity);
		}
	}

	public void createHypnoPharaoh(){
		Vec3d vec3d2 = new Vec3d((double) 1, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		if (getWorld() instanceof ServerWorld serverWorld) {
			PharaohEntity pharaohEntity = new PharaohEntity(PvZEntity.PHARAOHHYPNO, this.getWorld());
			Vec3d vec3d3 = new Vec3d(this.getPos().getX() + vec3d2.getX(), this.getPos().getY() + vec3d2.getY(), this.getPos().getZ() + vec3d2.getZ());
			pharaohEntity.refreshPositionAndAngles(vec3d3.getX(), this.getY(), vec3d3.getZ(), this.bodyYaw, 0.0F);
			pharaohEntity.setPosition(vec3d3.getX(), this.getY(), vec3d3.getZ());
			pharaohEntity.setHeadYaw(this.getHeadYaw());
			pharaohEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			pharaohEntity.setRainbowTag(Rainbow.TRUE);
			pharaohEntity.rainbowTicks = 30;
			serverWorld.spawnEntityAndPassengers(pharaohEntity);
		}
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
		if (this.getSummoning()){
			if (this.animationTicks > 0){
				event.getController().setAnimation(RawAnimation.begin().thenPlay("pharaoh.summon"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("pharaoh.summon.idle"));
			}
			if (this.isFrozen || this.isStunned) {
				event.getController().setAnimationSpeed(0);
			}
			else if (this.isIced) {
				event.getController().setAnimationSpeed(0.25);
			}
			else {
				event.getController().setAnimationSpeed(0.5);
			}
		}
		else if (this.isInsideWaterOrBubbleColumn()) {
			if (this.hasPassenger(entity)){
				event.getController().setAnimation(RawAnimation.begin().thenLoop("pharaoh.ducky2"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("pharaoh.ducky"));
			}
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		} else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (this.hasPassenger(entity)){
					event.getController().setAnimation(RawAnimation.begin().thenLoop("pharaoh.walking2"));
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.25);
					}
					else {
						event.getController().setAnimationSpeed(0.5);
					}
				}
				else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("pharaoh.walking"));
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
			} else {
				if (this.hasPassenger(entity)){
					event.getController().setAnimation(RawAnimation.begin().thenLoop("pharaoh.idle2"));
				}
				else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("pharaoh.idle"));
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
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.PHARAOHHYPNO) ||
				this.getType().equals(PvZEntity.UNDYINGPHARAOHHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
    }

    protected void initCustomGoals() {
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0));
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

	@Override
	public boolean tryAttack(Entity target) {
		if (!this.getSummoning()) {
			return super.tryAttack(target);
		}
		else {
			return false;
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		ZombiePropEntity sarcophagusEntity = null;
		for (Entity sarcophagus : this.getPassengerList()) {
			if (sarcophagus.getType().equals(PvZEntity.SARCOPHAGUS)) {
				sarcophagusEntity = (ZombiePropEntity) sarcophagus;
			}
		}
		if (sarcophagusEntity != null && sarcophagusEntity.isCovered()){
			this.setCoveredTag(Covered.TRUE);
		}
		else {
			this.setCoveredTag(Covered.FALSE);
		}
		if (!this.getWorld().isClient()) {
			if (this.getTypeCount() < 13) {
				if ((this.getVariant().equals(PharaohVariants.UNDYING) || this.getVariant().equals(PharaohVariants.UNDYINGHYPNO)) && sarcophagusEntity != null) {
					for (int x = 0; x <= 5; ++x){
						if ((this.CollidesWithPlant(x + 0.5f, 0f) != null)
								&& !this.hasStatusEffect(PvZCubed.BOUNCED)) {
							this.setSummoning(IsSummoning.TRUE);
						} else {
							this.setSummoning(IsSummoning.FALSE);
						}
					}
				} else {
					this.setSummoning(IsSummoning.FALSE);
				}
			}
			else {
				this.setSummoning(IsSummoning.FALSE);
			}
		}
		if (!this.getSummoning()){
			this.summonTicks = 60 * animationMultiplier;
			this.animationTicks = 0;
		}
		if (this.isIced){
			this.animationMultiplier = 2;
		}
		else {
			this.animationMultiplier = 1;
		}
		if (this.hasStatusEffect(FROZEN) || this.hasStatusEffect(PvZCubed.STUN) || this.hasStatusEffect(PvZCubed.DISABLE) || this.isFrozen || this.isStunned) {
			this.summonTicks = 160 * animationMultiplier;
			this.animationTicks = 0;
		}
		else if (this.getSummoning() && !this.isFrozen && !this.isStunned) {
			if (this.isIced) {
				if (--summonTicks <= 20) {
					if (this.getHypno()) {
						createHypnoPharaoh();
					} else {
						createPharaoh();
					}
					this.addCount();
					summonTicks = 160 * animationMultiplier;
				}
			}
			else {
				if (--summonTicks <= 0) {
					if (this.getHypno()) {
						createHypnoPharaoh();
					} else {
						createPharaoh();
					}
					this.addCount();
					summonTicks = 160 * animationMultiplier;
				}
			}
			if (this.getWorld().isClient) {
				if (summonTicks == 18 * animationMultiplier){
					this.animationTicks = 60 * animationMultiplier;
				}
				if (animationTicks > 0) {
					--this.animationTicks;
				}
			}
			assert sarcophagusEntity != null;
			this.setVelocity(Vec3d.ZERO);
			this.setMovementSpeed(0);
			this.getNavigation().stop();
		}
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

	@Override
	public void handleStatus(byte status) {
		super.handleStatus(status);
	}

	protected void mobTick() {
		super.mobTick();
		if (this.hasStatusEffect(PvZCubed.ICE)){
			this.animationMultiplier = 2;
			this.isIced = true;
		}
		else {
			this.isIced = false;
			this.animationMultiplier = 1;
		}

		ZombiePropEntity sarcophagusEntity = null;
		for (Entity sarcophagus : this.getPassengerList()) {
			if (sarcophagus.getType().equals(PvZEntity.SARCOPHAGUS)) {
				sarcophagusEntity = (ZombiePropEntity) sarcophagus;
			}
		}

		EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
		if (sarcophagusEntity == null &&
				this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID) &&
					!this.hasStatusEffect(ICE) && !this.hasStatusEffect(CHEESE) && !this.hasStatusEffect(GENERICSLOW) &&
					!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(BARK) && !this.hasStatusEffect(SHADOW) &&
					!this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN)) {
			assert maxSpeedAttribute != null;
			maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
			this.setRainbowTag(Rainbow.TRUE);
			this.rainbowTicks = 30;
		} else if (sarcophagusEntity != null) {
			if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID)) {
				assert maxSpeedAttribute != null;
				maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.08));
			}
		}
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(PharaohVariants.UNDYING) || this.getVariant().equals(PharaohVariants.UNDYINGHYPNO)){
			itemStack = ModItems.UNDYINGEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.PHARAOHEGG.getDefaultStack();
		}
		return itemStack;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static EntityAttributeModifier createSpeedModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_SPEED_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}
	@Override
	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater){
		float g = (float) ((this.isRemoved() ? 0.01F : this.method_52537(passenger)) + passenger.getHeightOffset(passenger));
		float f = 0.0F;

		Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
		passenger.setBodyYaw(this.bodyYaw);
	}
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

	public static DefaultAttributeContainer.Builder createUndyingPharaohAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.undyingPharaohH());
    }

	public static DefaultAttributeContainer.Builder createPharaohAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.pharaohH());
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
		if (this.getType().equals(PvZEntity.PHARAOH)){
			hypnoType = PvZEntity.PHARAOHHYPNO;
		}
		else {
			hypnoType = PvZEntity.UNDYINGPHARAOHHYPNO;
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
                PharaohEntity hypnotizedZombie = (PharaohEntity) hypnoType.create(getWorld());
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
