package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.football;


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
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut.TallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.FootballVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan.SuperFanImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
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

import org.jetbrains.annotations.Nullable;


import java.nio.charset.StandardCharsets;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class FootballEntity extends PvZombieEntity implements GeoEntity {

	private String controllerName = "walkingcontroller";

    private int attackTicksLeft;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);


	public boolean speedSwitch;

	public static final UUID MAX_SPEED_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

    public FootballEntity(EntityType<? extends FootballEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 12;
		this.getNavigation().setCanSwim(true);
		this.speedSwitch = false;
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
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
		tag.putBoolean("Tackle", this.getTackleStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Tackle"));
	}

	static {

	}



	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(FootballEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


	public enum TackleStage {
		TACKLING(true),
		EATING(false);

		TackleStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getTackleStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setTackleStage(FootballEntity.TackleStage tackleStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tackleStage.getId());
	}

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(FootballEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.BERSERKER)){
			setVariant(FootballVariants.BERSERKER);
			createBerserkerProp();
		}
		else if (this.getType().equals(PvZEntity.FOOTBALLHYPNO)){
			setVariant(FootballVariants.FOOTBALLHYPNO);
			setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.BERSERKERHYPNO)){
			setVariant(FootballVariants.BERSERKERHYPNO);
			setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(FootballVariants.DEFAULT);
			createFootballProp();
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public FootballVariants getVariant() {
		return FootballVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(FootballVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	public void createFootballProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.FOOTBALLGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createBerserkerProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.BERSERKERGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
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
		ZombiePropEntity zombiePropEntity = (ZombiePropEntity) this.getFirstPassenger();
		if (this.isInsideWaterOrBubbleColumn()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("football.ducky"));
			if (this.isFrozen || this.isStunned) {
				event.getController().setAnimationSpeed(0);
			}
			else if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if (!this.getTackleStage()) {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("football.running"));
					if (this.getType().equals(PvZEntity.BERSERKER) || this.getType().equals(PvZEntity.BERSERKERHYPNO)){
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
					event.getController().setAnimation(RawAnimation.begin().thenLoop("football.tackle"));
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
				event.getController().setAnimation(RawAnimation.begin().thenLoop("football.idle"));
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
		if (this.getType().equals(PvZEntity.FOOTBALLHYPNO) ||
				this.getType().equals(PvZEntity.BERSERKERHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
	}

	protected void initCustomGoals() {

		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));
		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof PlantEntity plantEntity && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("ground")) && !(plantEntity.getLowProfile()) && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying"));
		}));

		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));

		////////// Like Jetpacks prioritizes player and gardens ///////
		this.targetSelector.add(3, new TargetGoal<>(this, GardenChallengeEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, GardenEntity.class, false, true));
		this.targetSelector.add(4, new TargetGoal<>(this, PlayerEntity.class, false, true));
	}

	protected void initHypnoGoals(){
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

	public boolean tryAttack(Entity target) {
		if (target.getVehicle() instanceof PlantEntity.VineEntity vineEntity){
			target = vineEntity;
		}
		int i = this.attackTicksLeft;
		if (this.getTarget() != null &&
				(!(PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("ground")) && !(this.getTarget() instanceof PlantEntity plantEntity && plantEntity.getLowProfile()) && !(PLANT_LOCATION.get(this.getTarget().getType()).orElse("normal").equals("flying"))) && !((LivingEntity) target).hasStatusEffect(StatusEffects.RESISTANCE)) {
			if (!(this.getPassengerList().contains(target))) {
				if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
					if (this.getTackleStage() && !this.isInsideWaterOrBubbleColumn()) {
						if (i <= 0) {
							this.attackTicksLeft = 20;
							float f = 180f;
							if (target instanceof TallnutEntity || target instanceof GargantuarEntity) {
								f = 45;
							} else if (target instanceof PlayerEntity) {
								f = 25;
							}
							boolean bl = target.damage(getDamageSources().mobAttack(this), f);
							if (bl) {
								this.applyDamageEffects(this, target);
							}
							this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
							this.setTackleStage(TackleStage.EATING);
							return bl;
						}
						else {
							return false;
						}
					} else {
						if (i <= 0) {
							this.attackTicksLeft = 20;
							float f = this.getAttackDamage();
							boolean bl = target.damage(getDamageSources().mobAttack(this), f);
							if (bl && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
								target.playSound(PvZSounds.ZOMBIEBITEEVENT, 0.75f, 1f);
								this.setStealthTag(Stealth.FALSE);
								this.applyDamageEffects(this, target);
							}
							if (target instanceof HypnoshroomEntity hypnoshroomEntity && !hypnoshroomEntity.getIsAsleep()){
								hypnoshroomEntity.damage(getDamageSources().mobAttack(this), hypnoshroomEntity.getMaxHealth() * 5);
								this.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.HYPNO_DAMAGE), 0);
							}
							return bl;
						} else {
							return false;
						}
					}
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getAttacking() == null && !(this.getHypno())){
			LivingEntity zombie = this.CollidesWithZombie(1f);
			if (this.CollidesWithZombie(1f) instanceof SuperFanImpEntity superFanImpEntity && superFanImpEntity.CollidesWithPlant(1f, 0f) == null && superFanImpEntity.isOnGround() && !this.hasStatusEffect(PvZCubed.BOUNCED) && this.getTackleStage()){
				this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
				if (this.getVariant().equals(FootballVariants.DEFAULT) || this.getVariant().equals(FootballVariants.FOOTBALLHYPNO)) {
					this.setTackleStage(TackleStage.EATING);
				}
				Vec3d vec3d = new Vec3d(1.25, 0.8, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				superFanImpEntity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
			}
			else if (zombie instanceof GeneralPvZombieEntity generalPvZombieEntity && ZOMBIE_SIZE.get(generalPvZombieEntity.getType()).orElse("medium").equals("small") && generalPvZombieEntity.CollidesWithPlant(1f, 0f) == null && generalPvZombieEntity.isOnGround() && !this.hasStatusEffect(PvZCubed.BOUNCED) && this.getTackleStage()) {
				if (this.getVariant().equals(FootballVariants.BERSERKERHYPNO) || this.getVariant().equals(FootballVariants.BERSERKER)) {
					this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, 1F, 1.0F);
					Vec3d vec3d = new Vec3d(1.25, 0.8, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					generalPvZombieEntity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
				}
			}
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

	public void tickMovement() {
		super.tickMovement();
		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
	}

	protected void mobTick() {
		super.mobTick();
		var zombiePropEntity = this.getPassengerList()
				.stream()
				.filter(e -> e instanceof ZombiePropEntity)
				.map(e -> (ZombiePropEntity) e)
				.findFirst();
		if (zombiePropEntity.isEmpty()){
			this.setTackleStage(TackleStage.EATING);
		}



		if (this.getVariant().equals(FootballVariants.BERSERKER) ||
				this.getVariant().equals(FootballVariants.BERSERKERHYPNO) ) {
			EntityAttributeInstance maxSpeedAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
			if (this.getTackleStage() &&this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID) &&
					!this.hasStatusEffect(ICE) && !this.hasStatusEffect(CHEESE) && !this.hasStatusEffect(GENERICSLOW) &&
					!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(BARK) && !this.hasStatusEffect(SHADOW) &&
					!this.hasStatusEffect(DISABLE) && !this.hasStatusEffect(STUN)) {
				assert maxSpeedAttribute != null;
				maxSpeedAttribute.removeModifier(MAX_SPEED_UUID);
			} else if (!this.getTackleStage()) {
				if (!this.getAttributes().hasModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, MAX_SPEED_UUID)) {
					assert maxSpeedAttribute != null;
					maxSpeedAttribute.addPersistentModifier(createSpeedModifier(-0.09));
				}
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.FOOTBALLEGG.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/
	@Override
	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater) {
		float g = (float) ((this.isRemoved() ? 0.01F : this.method_52537(passenger)) + passenger.getHeightOffset(passenger));
		float f = 0.00F;
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

	public static EntityAttributeModifier createSpeedModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_SPEED_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static DefaultAttributeContainer.Builder createFootballAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.footballH());
	}

	public static DefaultAttributeContainer.Builder createBerserkerAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 75.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.berserkerH());
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



	private float getAttackDamage(){
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;
	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.BERSERKER)){
			hypnoType = PvZEntity.BERSERKERHYPNO;
		}
		else {
			hypnoType = PvZEntity.FOOTBALLHYPNO;
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
				FootballEntity hypnotizedZombie = (FootballEntity) hypnoType.create(getWorld());
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
