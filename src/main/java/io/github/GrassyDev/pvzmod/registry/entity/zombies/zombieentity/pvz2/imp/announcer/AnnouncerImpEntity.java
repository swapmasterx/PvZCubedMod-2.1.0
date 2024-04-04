package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.DefaultAndHypnoVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ZombieKingVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.zombieking.ZombieKingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
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
import net.minecraft.sound.SoundCategory;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class AnnouncerImpEntity extends SummonerEntity implements GeoEntity {

	private String controllerName = "walkingcontroller";

    private boolean isAggro;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);


    public AnnouncerImpEntity(EntityType<? extends AnnouncerImpEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 12;
        this.isAggro = false;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(COLOR, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
		tag.putInt("Color", this.getColorVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		this.dataTracker.set(COLOR, tag.getInt("Color"));
	}

	static {
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}



	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(AnnouncerImpEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> COLOR =
			DataTracker.registerData(AnnouncerImpEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.ANNOUNCERIMPHYPNO)){
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.REDANNOUNCERIMP)){
			this.setColor(ZombieKingVariants.RED);
		}
		else if (this.getType().equals(PvZEntity.REDANNOUNCERIMPHYPNO)){
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setHypno(IsHypno.TRUE);
			this.setColor(ZombieKingVariants.RED);
		}
		else if (this.getType().equals(PvZEntity.BLACKANNOUNCERIMP)){
			this.setColor(ZombieKingVariants.BLACK);
		}
		else if (this.getType().equals(PvZEntity.BLACKANNOUNCERIMPHYPNO)){
			setVariant(DefaultAndHypnoVariants.HYPNO);
			this.setHypno(IsHypno.TRUE);
			this.setColor(ZombieKingVariants.BLACK);
		}
		else {
			setVariant(DefaultAndHypnoVariants.DEFAULT);
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

	private int getColorVariant() {
		return this.dataTracker.get(COLOR);
	}

	public ZombieKingVariants getColor() {
		return ZombieKingVariants.byId(this.getColorVariant() & 255);
	}

	public void setColor(ZombieKingVariants variant) {
		this.dataTracker.set(COLOR, variant.getId() & 255);
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
			event.getController().setAnimation(RawAnimation.begin().thenLoop("imp.ducky"));
			if (this.isIced) {
				event.getController().setAnimationSpeed(0.5);
			}
			else {
				event.getController().setAnimationSpeed(1);
			}
		}else {
			if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("imp.run"));
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					}
					else if (this.isIced) {
						event.getController().setAnimationSpeed(0.25);
					}
					else {
						event.getController().setAnimationSpeed(0.5);
					}
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("imp.idle"));
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
		if (this.getType().equals(PvZEntity.ANNOUNCERIMPHYPNO)){
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
	}

	protected void initCustomGoals() {
		this.goalSelector.add(1, new AnnouncerImpEntity.summonZombieGoal(this));

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
		this.goalSelector.add(1, new AnnouncerImpEntity.summonZombieGoal(this));

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
		super.tick();
		if (this.isAggro && !this.hasStatusEffect(PvZCubed.BOUNCED)){
			this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
		}
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
		if (this.getColor().equals(ZombieKingVariants.RED)) {
			return ModItems.REDANNOUNCERIMPEGG.getDefaultStack();
		} else if (this.getColor().equals(ZombieKingVariants.BLACK)) {
			return ModItems.BLACKANNOUNCERIMPEGG.getDefaultStack();
		} else {
			return ModItems.ANNOUNCERIMPEGG.getDefaultStack();
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	@Override
	public double getMountedHeightOffset() {
		return 0;
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createAnnouncerImpAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.announcerH());
	}

	protected SoundEvent getAmbientSound() {
		return null;
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
		return PvZSounds.SILENCEVENET;
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;
	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.REDANNOUNCERIMP)) {
			hypnoType = PvZEntity.REDANNOUNCERIMPHYPNO;
		}
		else if (this.getType().equals(PvZEntity.BLACKANNOUNCERIMP)) {

			hypnoType = PvZEntity.BLACKANNOUNCERIMPHYPNO;
		}
		else {
			hypnoType = PvZEntity.ANNOUNCERIMPHYPNO;
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
				AnnouncerImpEntity hypnotizedZombie = (AnnouncerImpEntity) hypnoType.create(getWorld());
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

	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.random.nextTriangular(0.0, 0.0172275 * (double)divergence), this.random.nextTriangular(0.0, 0.0172275 * (double)divergence), this.random.nextTriangular(0.0, 0.0172275 * (double)divergence)).multiply((double)speed);
		this.setVelocity(vec3d);
		double d = vec3d.horizontalLength();
		this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
		this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
		this.prevYaw = this.getYaw();
		this.prevPitch = this.getPitch();
	}


	/** /~*~//~*GOALS*~//~*~/ **/

	protected abstract class CastSpellGoal extends Goal {
		protected int spellCooldown;
		protected int startTime;

		protected CastSpellGoal() {
		}

		public boolean canStart() {
			LivingEntity livingEntity = AnnouncerImpEntity.this.getTarget();
			if (livingEntity != null && livingEntity.isAlive() && !AnnouncerImpEntity.this.hasStatusEffect(PvZCubed.FROZEN) && !AnnouncerImpEntity.this.hasStatusEffect(PvZCubed.STUN)) {
				if (AnnouncerImpEntity.this.isSpellcasting()) {
					return false;
				} else {
					return AnnouncerImpEntity.this.age >= this.startTime;
				}
			} else {
				return false;
			}
		}

		public boolean shouldContinue() {
			LivingEntity livingEntity = AnnouncerImpEntity.this.getTarget();
			return livingEntity != null && livingEntity.isAlive() && this.spellCooldown > 0 && !AnnouncerImpEntity.this.hasStatusEffect(PvZCubed.FROZEN) && !AnnouncerImpEntity.this.hasStatusEffect(PvZCubed.STUN);
		}

		public void start() {
			this.spellCooldown = this.getTickCount(this.getInitialCooldown());
			AnnouncerImpEntity.this.spellTicks = this.getSpellTicks();
			this.startTime = AnnouncerImpEntity.this.age + this.startTimeDelay();
			SoundEvent soundEvent = this.getSoundPrepare();
			if (soundEvent != null) {
				AnnouncerImpEntity.this.playSound(soundEvent, 1.0F, 1.0F);
			}

			AnnouncerImpEntity.this.setSpell(this.getSpell());
		}

		public void tick() {
			--this.spellCooldown;
			if (this.spellCooldown == 0) {
				this.castSpell();
				AnnouncerImpEntity.this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, 70, 1)));
				AnnouncerImpEntity.this.playSound(AnnouncerImpEntity.this.getCastSpellSound(), 1.0F, 1.0F);
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
		private final AnnouncerImpEntity announcerImpEntity;

        private summonZombieGoal(AnnouncerImpEntity announcerImpEntity) {
            super();
			this.announcerImpEntity = announcerImpEntity;
			this.closeZombiePredicate = (TargetPredicate.createNonAttackable().setBaseMaxDistance(16.0D).ignoreVisibility().ignoreDistanceScalingFactor());
        }

        public boolean canStart() {
            if (AnnouncerImpEntity.this.isAggro) {
				return super.canStart();
            } else {
                return false;
            }
        }

		protected int getSpellTicks() {
			return 100;
		}

		protected int startTimeDelay() {
			return 320;
		}

        protected void castSpell() {

			EntityType<?> king = PvZEntity.ZOMBIEKING;
			if (this.announcerImpEntity.getColor().equals(ZombieKingVariants.RED)){
				king = PvZEntity.REDZOMBIEKING;
				if (this.announcerImpEntity.getHypno()){
					king = PvZEntity.REDZOMBIEKINGHYPNO;
				}
			}
			else if (this.announcerImpEntity.getColor().equals(ZombieKingVariants.BLACK)){
				king = PvZEntity.BLACKZOMBIEKING;
				if (this.announcerImpEntity.getHypno()){
					king = PvZEntity.BLACKZOMBIEKINGHYPNO;
				}
			}
			else {
				if (this.announcerImpEntity.getHypno()) {
					king = PvZEntity.ZOMBIEKINGHYPNO;
				}
			}

            ServerWorld serverWorld = (ServerWorld) AnnouncerImpEntity.this.getWorld();
            for(int b = 0; b < 1; ++b) { // 1 ZombieKing
                BlockPos blockPos = AnnouncerImpEntity.this.getBlockPos();
				ZombieKingEntity zombieKingEntity = (ZombieKingEntity) king.create(AnnouncerImpEntity.this.getWorld());
				zombieKingEntity.initialize(serverWorld, AnnouncerImpEntity.this.getWorld().getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				zombieKingEntity.refreshPositionAndAngles(blockPos, AnnouncerImpEntity.this.getYaw(), 0.0F);
				zombieKingEntity.setOwner(AnnouncerImpEntity.this);
				zombieKingEntity.setHeadYaw(AnnouncerImpEntity.this.getHeadYaw());
				zombieKingEntity.setYaw(AnnouncerImpEntity.this.getYaw());
				zombieKingEntity.setBodyYaw(AnnouncerImpEntity.this.bodyYaw);
				serverWorld.spawnEntityAndPassengers(zombieKingEntity);
				if (this.announcerImpEntity.isInsideWaterOrBubbleColumn()) {
					getWorld().playSound((PlayerEntity) null, announcerImpEntity.getX(), announcerImpEntity.getY(), announcerImpEntity.getZ(), SoundEvents.ENTITY_PLAYER_SPLASH, SoundCategory.HOSTILE, 1F, 1F);
				}
				zombieKingEntity.spawningTicks = 15;
				AnnouncerImpEntity.this.discard();
            }
        }

        protected SoundEvent getSoundPrepare() {
			if (this.announcerImpEntity.isInsideWaterOrBubbleColumn()){
				return PvZSounds.IMPANNOUNCERALTEVENT;
			}
			else {
				return PvZSounds.IMPANNOUNCEREVENT;
			}
        }

        protected Spell getSpell() {
            return Spell.SUMMON_VEX;
        }
    }
}
