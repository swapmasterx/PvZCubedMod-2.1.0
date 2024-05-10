package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.PapershieldVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.explorer.ExplorerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.browncoat.sargeant.SargeantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class NewspaperShieldEntity extends ZombieShieldEntity implements GeoEntity {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "shieldcontroller";

    public NewspaperShieldEntity(EntityType<? extends NewspaperShieldEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, true);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Variant", this.getTypeVariant());
		nbt.putBoolean("Fire", this.getFireStage());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, nbt.getBoolean("Fire"));
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, nbt.getInt("Variant"));
	}

	static {
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


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(NewspaperShieldEntity.class, TrackedDataHandlerRegistry.BOOLEAN);


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
			DataTracker.registerData(NewspaperShieldEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.SUNDAYEDITIONSHIELD)){
			setVariant(PapershieldVariants.SUNDAYEDITIONSHIELD);
		}
		else if (this.getType().equals(PvZEntity.BOOKSHIELD)){
			setVariant(PapershieldVariants.BOOKSHIELD);
		}
		else {
			setVariant(PapershieldVariants.NEWSPAPERSHIELD);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public PapershieldVariants getVariant() {
		return PapershieldVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(PapershieldVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	private boolean isBeingRainedOn() {
		BlockPos blockPos = this.getBlockPos();
		return this.getWorld().hasRain(blockPos) || this.getWorld().hasRain(new BlockPos((int) blockPos.getX(), (int) this.getBoundingBox().maxY, (int) blockPos.getZ()));
	}

	public void tick() {
		if (this.getVariant().equals(PapershieldVariants.BOOKSHIELD)) {
			if (this.isBeingRainedOn() || this.hasStatusEffect(PvZCubed.ICE) || this.hasStatusEffect(PvZCubed.FROZEN) || this.hasStatusEffect(PvZCubed.WET) || this.isSubmergedInWater()) {
				this.setFireStage(ExplorerEntity.FireStage.EXTINGUISHED);
				if (this.getVehicle() instanceof SargeantEntity sargeantEntity){
					sargeantEntity.setFireStage(SargeantEntity.FireStage.EXTINGUISHED);
				}
			} else if (this.isOnFire() || this.hasStatusEffect(PvZCubed.WARM)) {
				this.setFireStage(ExplorerEntity.FireStage.FIRE);
				if (this.getVehicle() instanceof SargeantEntity sargeantEntity){
					sargeantEntity.setFireStage(SargeantEntity.FireStage.EXTINGUISHED);
				}
			}
			if (!(this.getHypno())) {
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
						if (bl) {
							vehicle = (LivingEntity) plantEntity.getVehicle();
							if (vehicle instanceof PlantEntity plantEntity2) {
								if (plantEntity2.onWater) {
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
					} else if (!this.hasStatusEffect(PvZCubed.BOUNCED)) {
						this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
						this.setTarget(CollidesWithPlant(0.1f, 0f));
						this.setStealthTag(Stealth.FALSE);
					}
				} else if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)) {
					this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				} else if (this.CollidesWithPlayer(1.5f) != null && !this.CollidesWithPlayer(1.5f).isCreative()) {
					this.setTarget(CollidesWithPlayer(1.5f));
					this.setStealthTag(Stealth.FALSE);
				}
			}
			this.setOnFire(false);
		}
		super.tick();
		if (this.getVehicle() == null){
			this.kill();
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
        return PlayState.CONTINUE;
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createNewspaperShieldAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.newspaperShieldH());
    }

	public static DefaultAttributeContainer.Builder createSundayEditionShieldAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.sundayShieldH());
	}

	public static DefaultAttributeContainer.Builder createSBookShieldAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.bookShieldH());
	}

	protected SoundEvent getAmbientSound() {
		return PvZSounds.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}
	protected SoundEvent getDeathSound() {return SoundEvents.BLOCK_GRASS_BREAK;}
	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getVariant().equals(PapershieldVariants.BOOKSHIELD)){
			itemStack = ModItems.BOOKBURNEREGG.getDefaultStack();
		}
		else if (this.getVariant().equals(PapershieldVariants.SUNDAYEDITIONSHIELD)){
			itemStack = ModItems.SUNDAYEDITIONEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.BOOKBURNEREGG.getDefaultStack();
		}
		return itemStack;
	}

}
