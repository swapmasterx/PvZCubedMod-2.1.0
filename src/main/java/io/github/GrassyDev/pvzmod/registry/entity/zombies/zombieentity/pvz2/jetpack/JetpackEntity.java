package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.jetpack;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.JetpackVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
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
import net.minecraft.sound.SoundEvent;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class JetpackEntity extends PvZombieEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";


	public JetpackEntity(EntityType<? extends JetpackEntity> entityType, World world) {
        super(entityType, world);
		this.setFlying(Flying.TRUE);

        this.experiencePoints = 3;
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



	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(JetpackEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.BLASTRONAUT)){
			setVariant(JetpackVariants.BLASTRONAUT);
			createBlastronautProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BLASTRONAUTHYPNO)){
			setVariant(JetpackVariants.BLASTRONAUTHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.JETPACKHYPNO)){
			setVariant(JetpackVariants.JETPACKHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(JetpackVariants.JETPACK);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public JetpackVariants getVariant() {
		return JetpackVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(JetpackVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	public void createBlastronautProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.BLASTRONAUTGEAR, this.getWorld());
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
		if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("jetpack.flying"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("jetpack.idle"));
		}
		if (this.isFrozen || this.isStunned) {
			event.getController().setAnimationSpeed(0);
		} else if (this.isIced) {
			event.getController().setAnimationSpeed(0.5);
		} else {
			event.getController().setAnimationSpeed(1);
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.JETPACKHYPNO) ||
				this.getType().equals(PvZEntity.BLASTRONAUTHYPNO) ) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
    }

    protected void initCustomGoals() {

		this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));


		this.targetSelector.add(4, new TargetGoal<>(this, MerchantEntity.class, false, true));
		this.targetSelector.add(2, new TargetGoal<>(this, IronGolemEntity.class, false, true));

		////////// Jetpacker's ignore plants and go straight for gardens or players ///////
		this.targetSelector.add(5, new TargetGoal<>(this, PlayerEntity.class, false, false));
		this.targetSelector.add(3, new TargetGoal<>(this, GardenChallengeEntity.class, false, true));
		this.targetSelector.add(3, new TargetGoal<>(this, GardenEntity.class, false, true));
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

	/** /~*~//~*TICKING*~//~*~/ **/

	public boolean hovering;
	protected int hoverTicks = 20;

	@Override
	public void setOnGround(boolean onGround) {

	}

	@Override
	public boolean isOnGround() {
		return false;
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		super.applyDamage(source, amount);
	}

	public void tick() {
		if (this.hasStatusEffect(PvZCubed.DISABLE)){
			this.kill();
		}
		LivingEntity target = this.getTarget();
		this.setNoGravity(true);
		if (target instanceof PlayerEntity player && player.getAbilities().creativeMode){
			this.setTarget(null);
		}
		if (target != null){
			Vec3d lastPos = this.getPos();
			if (target.squaredDistanceTo(this) > 2.25) {
				if (this.getVariant().equals(JetpackVariants.BLASTRONAUT) ||
						this.getVariant().equals(JetpackVariants.BLASTRONAUTHYPNO)){
					this.getMoveControl().moveTo(target.getX(), target.getY(), target.getZ(), 2.0);
				}
				else {
					this.getMoveControl().moveTo(target.getX(), target.getY(), target.getZ(), 2.5);
				}
			}
			this.setFlying(Flying.TRUE);
			if (this.isInsideWaterOrBubbleColumn()) {
				this.addVelocity(0, 0.3, 0);
			}
			if (firstPos != null) {
				if (lastPos.squaredDistanceTo(firstPos) < 0.0001 && this.CollidesWithPlant(0.1f, 0f) == null && !this.hasStatusEffect(PvZCubed.BOUNCED) && this.getTarget() != null && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE) && !this.hasStatusEffect(PvZCubed.ICE) && this.age >= 30 && this.attackingTick <= 0) {
					this.setVelocity(0, 0, 0);
					this.addVelocity(0, 0.3, 0);
				}
			}
			if (--hoverTicks <= 0){
				this.hoverTicks = 20;
				this.firstPos = this.getPos();
			}
			if (this.getY() > target.getY() + 0.125){
				this.addVelocity(0, -0.004, 0);
			}
			else if (this.getY() <= target.getY()){
				this.addVelocity(0, 0.005, 0);
				this.hovering = true;
			}
			else if (this.hovering && !this.hasStatusEffect(PvZCubed.BOUNCED)) {
				this.setVelocity(0, 0, 0);
				this.hovering = false;
			}
		}
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED) && (PLANT_LOCATION.get(this.CollidesWithPlant(0.1f, 0f).getType()).orElse("normal").equals("maintarget") ||
					PLANT_LOCATION.get(this.CollidesWithPlant(0.1f, 0f).getType()).orElse("normal").equals("tall") || PLANT_LOCATION.get(this.CollidesWithPlant(0.1f, 0f).getType()).orElse("normal").equals("flying"))){
				this.setTarget(CollidesWithPlant(0.1f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlayer(1.5f) != null && !this.CollidesWithPlayer(1.5f).isCreative()){
				this.setTarget(CollidesWithPlayer(1.5f));
				this.setStealthTag(Stealth.FALSE);
			}
		}
		super.tick();
	}

	protected void mobTick() {
		super.mobTick();
		if (this.hasStatusEffect(PvZCubed.FROZEN)){
			this.kill();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(JetpackVariants.BLASTRONAUT) || this.getType().equals(PvZEntity.BLASTRONAUTHYPNO)){
			itemStack = ModItems.BLASTRONAUTEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.JETPACKEGG.getDefaultStack();
		}
		return itemStack;
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/
	@Override
	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater){
		float g = (float) ((this.isRemoved() ? 0.01F : this.method_52537(passenger)) + passenger.getHeightOffset(passenger));
		float f = 0.1F;

		Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
		passenger.setBodyYaw(this.bodyYaw);
	}
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

	public static DefaultAttributeContainer.Builder createJetpackAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.jetpackH());
    }

	public static DefaultAttributeContainer.Builder createBlastronautAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.blastronautH());
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
		super.fall(0, false, landedState, landedPosition);
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
		return PvZSounds.SILENCEVENET;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
	}




	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;
	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.JETPACK)){
			hypnoType = PvZEntity.JETPACKHYPNO;
		}
		else if (this.getType().equals(PvZEntity.BLASTRONAUT)){
			hypnoType = PvZEntity.BLASTRONAUTHYPNO;
		}
		else {
			hypnoType = PvZEntity.JETPACKHYPNO;
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
                JetpackEntity hypnotizedZombie = (JetpackEntity) hypnoType.create(getWorld());
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
