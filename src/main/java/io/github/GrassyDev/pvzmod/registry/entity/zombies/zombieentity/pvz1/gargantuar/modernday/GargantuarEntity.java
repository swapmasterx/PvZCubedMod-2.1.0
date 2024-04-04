package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.superchomper.SuperChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit.OlivePitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.chester.ChesterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.GargantuarVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan.SuperFanImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.pharaoh.PharaohEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class GargantuarEntity extends PvZombieEntity implements GeoEntity {
	private String controllerName = "walkingcontroller";



	private int animationTicksLeft;
	private int launchAnimation;
    public boolean firstAttack;
    public boolean inAnimation;
	public boolean inLaunchAnimation;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	protected ImpEntity impEntity;

	protected float healthImp;

	public GargantuarEntity(EntityType<? extends GargantuarEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 100;
        this.firstAttack = true;
		this.entityBox = PvZEntity.GARGANTUAR;
		this.impEntity = new ImpEntity(PvZEntity.IMP, this.getWorld());
		this.healthImp = 180;
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
		tag.putBoolean("Imp", this.getImpStage());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Imp"));
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}

		RandomGenerator randomGenerator = this.getRandom();
		if (status == 107) {
			Entity target = this.getTarget();
			if (target != null) {
				for (int i = 0; i < 128; ++i) {
					double e = (double) MathHelper.nextBetween(randomGenerator, 5F, 20F);
					this.getWorld().addParticle(ParticleTypes.WATER_SPLASH, target.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
							target.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 3F),
							target.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
							0, e, 0);
				}
			}
		}
		if (status == 113) {
			this.inAnimation = true;
		}
		else if (status == 112) {
			this.inAnimation = false;
		}
		if (status == 104) {
			this.inLaunchAnimation = true;
		}
		else if (status == 103) {
			this.inLaunchAnimation = false;
		}
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(GargantuarEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		setImpStage(ImpStage.IMP);
		if (this.getType().equals(PvZEntity.UNICORNGARGANTUAR)){
			setVariant(GargantuarVariants.UNICORNGARGANTUAR);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEEND)){
			setVariant(GargantuarVariants.DEFENSIVEEND);
			this.initCustomGoals();
			createProp();
		}
		else if (this.getType().equals(PvZEntity.MUMMYGARGANTUAR)){
			setVariant(GargantuarVariants.MUMMY);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.CURSEDGARGOLITH)){
			setVariant(GargantuarVariants.GARGOLITH);
			this.initCustomGoals();
			this.setImpStage(ImpStage.NOIMP);
		}
		else if (this.getType().equals(PvZEntity.CURSEDGARGOLITHHYPNO)){
			setVariant(GargantuarVariants.GARGOLITHHYPNO);
			this.setHypno(IsHypno.TRUE);
			this.setImpStage(ImpStage.NOIMP);
		}
		else if (this.getType().equals(PvZEntity.GARGANTUARHYPNO)){
			setVariant(GargantuarVariants.GARGANTUARHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.MUMMYGARGANTUARHYPNO)){
			setVariant(GargantuarVariants.MUMMYHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.UNICORNGARGANTUARHYPNO)){
			setVariant(GargantuarVariants.UNICORNGARGANTUARHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEENDHYPNO)){
			setVariant(GargantuarVariants.DEFENSIVEENDHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEAR)){
			setVariant(GargantuarVariants.DEFENSIVEEND_NEWYEAR);
			this.initCustomGoals();
			createProp();
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO)){
			setVariant(GargantuarVariants.DEFENSIVEEND_NEWYEARHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(GargantuarVariants.GARGANTUAR);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public enum ImpStage {
		IMP(true),
		NOIMP(false);

		ImpStage(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getImpStage() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	private void setImpStage(ImpStage impStage) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, impStage.getId());
	}

	@Override
	public void setHypno(IsHypno hypno) {
		super.setHypno(hypno);
	}

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(GargantuarEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public GargantuarVariants getVariant() {
		return GargantuarVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(GargantuarVariants variant) {
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
		if (this.isFrozen || this.isStunned) {
			event.getController().setAnimationSpeed(0);
		} else if (this.isIced) {
			event.getController().setAnimationSpeed(0.5);
		} else {
			event.getController().setAnimationSpeed(1);
		}
		if (this.isInsideWaterOrBubbleColumn()) {
			if (inDyingAnimation){
				event.getController().setAnimation(RawAnimation.begin().thenPlay("gargantuar.ducky.death"));
			}
			else if (inLaunchAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("gargantuar.ducky.throw"));
			} else if (this.getImpStage()) {
				if (inAnimation) {
					event.getController().setAnimation(RawAnimation.begin().thenPlay("gargantuar.ducky.smash"));
				} else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("gargantuar.ducky"));
				}
			} else {
				if (inAnimation) {
					event.getController().setAnimation(RawAnimation.begin().thenPlay("gargantuar.ducky.smash2"));
				} else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("gargantuar.ducky2"));
				}
			}
		} else {
			if (inDyingAnimation){
				event.getController().setAnimation(RawAnimation.begin().thenPlay("gargantuar.death"));
				event.getController().setAnimationSpeed(1);
			}
			else if (inLaunchAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("gargantuar.throw"));
			} else if (this.getImpStage()) {
				if (inAnimation) {
					event.getController().setAnimation(RawAnimation.begin().thenPlay("gargantuar.smash"));
				} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("gargantuar.walk"));
				} else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("gargantuar.idle"));
				}
			} else {
				if (inAnimation) {
					event.getController().setAnimation(RawAnimation.begin().thenPlay("gargantuar.smash2"));
				} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("gargantuar.walk2"));
				} else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("gargantuar.idle2"));
				}
			}
		}
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		if (this.getType().equals(PvZEntity.GARGANTUARHYPNO) ||
				this.getType().equals(PvZEntity.DEFENSIVEENDHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYGARGANTUARHYPNO) ||
				this.getType().equals(PvZEntity.CURSEDGARGOLITHHYPNO) ||
				this.getType().equals(PvZEntity.UNICORNGARGANTUARHYPNO) ||
				this.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO)) {
			initHypnoGoals();
		}
		else {
			initCustomGoals();
		}
	}

	protected void initCustomGoals() {

		this.goalSelector.add(1, new GargantuarEntity.AttackGoal());
		this.goalSelector.add(8, new LookAroundGoal(this));
		this.targetSelector.add(6, new RevengeGoal(this, new Class[0]));
		this.goalSelector.add(1, new PvZombieAttackGoal(this, 1.0D, true));

		this.targetSelector.add(5, new TargetGoal<>(this, MobEntity.class, 0, false, false, (livingEntity) -> {
			return livingEntity instanceof PlantEntity plantEntity;
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

		this.goalSelector.add(1, new GargantuarEntity.AttackGoal());
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


	//Smash
	public boolean tryAttack(Entity target) {
		if (!this.getPassengerList().contains(target)) {
			if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE) && !this.inLaunchAnimation) {
				boolean bl = false;
				if (this.firstAttack && this.animationTicksLeft <= 0 && this.squaredDistanceTo(target) < 16D) {
					this.animationTicksLeft = 90 * animationMultiplier;
					this.firstAttack = false;
				} else if (this.animationTicksLeft == 40 * animationMultiplier) {
					if (target.hasVehicle()){
						target.getVehicle().damage(getDamageSources().mobAttack(this), 360);
					}
					if (target instanceof SpikerockEntity && this.squaredDistanceTo(target) < 16D) {
						bl = true;
					}
					else if (this.squaredDistanceTo(target) < 16D) {
						target.damage(getDamageSources().mobAttack(this), 360);
						return true;
					}
				}
				if (bl) {
					target.damage(getDamageSources().mobAttack(this), 90);
					this.applyDamageEffects(this, target);
					return true;
				}
			}
		}
		return false;
	}

	protected void setImp(){
		if (this.getType().equals(PvZEntity.GARGANTUARHYPNO)){
			this.impEntity = new ImpEntity(PvZEntity.IMPHYPNO, this.getWorld());
			this.healthImp = this.getMaxHealth() / 2;
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEEND)){
			this.impEntity = new SuperFanImpEntity(PvZEntity.SUPERFANIMP, this.getWorld());
			this.healthImp = this.getMaxHealth();
		}
		else if (this.getType().equals(PvZEntity.MUMMYGARGANTUAR)){
			this.impEntity = new ImpEntity(PvZEntity.MUMMYIMP, this.getWorld());
			this.healthImp = this.getMaxHealth() / 2;
		}
		else if (this.getType().equals(PvZEntity.UNICORNGARGANTUAR)){
			this.impEntity = new ImpEntity(PvZEntity.BASSIMP, this.getWorld());
			this.healthImp = this.getMaxHealth() / 2;
		}
		else if (this.getType().equals(PvZEntity.UNICORNGARGANTUARHYPNO)){
			this.impEntity = new ImpEntity(PvZEntity.BASSIMPHYPNO, this.getWorld());
			this.healthImp = this.getMaxHealth() / 2;
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEENDHYPNO)){
			this.impEntity = new SuperFanImpEntity(PvZEntity.SUPERFANIMPHYPNO, this.getWorld());
			this.healthImp = this.getMaxHealth();
		}
		else if (this.getType().equals(PvZEntity.MUMMYGARGANTUARHYPNO)){
			this.impEntity = new ImpEntity(PvZEntity.MUMMYIMPHYPNO, this.getWorld());
			this.healthImp = this.getMaxHealth();
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEAR)){
			this.impEntity = new SuperFanImpEntity(PvZEntity.NEWYEARIMP, this.getWorld());
			this.healthImp = this.getMaxHealth();
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO)){
			this.impEntity = new SuperFanImpEntity(PvZEntity.NEWYEARIMPHYPNO, this.getWorld());
			this.healthImp = this.getMaxHealth();
		}
		else {
			this.impEntity = new ImpEntity(PvZEntity.IMP, this.getWorld());
			this.healthImp = this.getMaxHealth() / 2;
		}
	}

	//Launch Imp
	public void tryLaunch(Entity target){
		this.setImp();
		if (this.getImpStage().equals(Boolean.TRUE) && launchAnimation == 20 * animationMultiplier && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)){
			if (target != null){
				double d = this.squaredDistanceTo(target);
				float df = (float) d;
				double e = target.getX() - this.getX();
				double f = target.getY() - this.getY();
				double g = target.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				impEntity.setVelocity(e * (double) h, f * (double) h, g * (double) h, 2.25F, 0F);
			}
			else {
				impEntity.setVelocity(random.range(-1, 1), 0, random.range(-1, 1), 2.25F, 0F);
			}
			impEntity.updatePosition(this.getX(), this.getY() + 3.75D, this.getZ());
			impEntity.setOwner(this);
			this.setImpStage(ImpStage.NOIMP);
			this.playSound(PvZSounds.IMPLAUNCHEVENT, 1F, 1);
			if (this.getHypno()){
				impEntity.setHypno(IsHypno.TRUE);
			}
			impEntity.initialize((ServerWorldAccess) getWorld(), getWorld().getLocalDifficulty(impEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData)null, (NbtCompound) null);
			impEntity.setRainbowTag(Rainbow.TRUE);
			impEntity.rainbowTicks = 40;
			if (impEntity instanceof SuperFanImpEntity){
				impEntity.rainbowTicks = 30;
			}
			if (impEntity.getType().equals(PvZEntity.BASSIMP) || impEntity.getType().equals(PvZEntity.BASSIMPHYPNO)){
				impEntity.rainbowTicks = 10;
			}
			this.getWorld().spawnEntity(impEntity);
		}
	}

	@Override
	public void setYaw(float yaw) {
		if (!this.inDyingAnimation) {
			super.setYaw(yaw);
		}
	}

	private void rainbowZombies(int boxOffset) {
		Vec3d vec3d2 = new Vec3d((double) boxOffset, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1, 4, 1).offset(vec3d2).offset(0, -1.5, 0));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				if (!var9.hasNext()) {
					return;
				}

				livingEntity = (LivingEntity) var9.next();
			} while (livingEntity == this);

			if (this.getHypno() && livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno()) {
				generalPvZombieEntity.setRainbowTag(Rainbow.TRUE);
				generalPvZombieEntity.rainbowTicks = 5;
			}
			else if (!this.getHypno() && livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.getHypno()) {
				generalPvZombieEntity.setRainbowTag(Rainbow.TRUE);
				generalPvZombieEntity.rainbowTicks = 5;
			}
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		if (!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(STUN)) {
			if (this.getVariant().equals(GargantuarVariants.UNICORNGARGANTUAR) || this.getVariant().equals(GargantuarVariants.UNICORNGARGANTUARHYPNO)) {
				for (int x = -2; x >= -9; --x) {
					rainbowZombies(x);
				}
			}
		}
		if (this.getHealth() <= 0 && !inDyingAnimation){
			this.inDyingAnimation = true;
			this.deathTicks = 80;
		}
		if (this.inDyingAnimation){
			this.getNavigation().stop();
			this.setHealth(1);
		}
		--deathTicks;
		if (deathTicks == 40){
			if (this.isInsideWaterOrBubbleColumn()){
				this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1F, 1.0F);
			}
			else {
				this.playSound(PvZSounds.GARGANTUARSMASHEVENT, 1F, 1.0F);
			}
		}
		if (deathTicks == 1){
			onDeath(PvZDamageTypes.of(getWorld(), PvZDamageTypes.SELF_TERMINATE_DAMAGE));
		}
		super.tick();
		if (this.getVariant().equals(GargantuarVariants.GARGOLITH) && this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
				this.setVelocity(0, -0.3, 0);
				this.getNavigation().stop();
				this.setTarget(CollidesWithPlant(0.1f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlayer(0.1f) != null && !this.CollidesWithPlayer(0.1f).isCreative()){
				this.setTarget(CollidesWithPlayer(0.1f));
				this.setStealthTag(Stealth.FALSE);
			}
		}
		else if (!this.getVariant().equals(GargantuarVariants.GARGOLITH) && this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
				this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
				this.setTarget(CollidesWithPlant(0f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlant(1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
				this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
				this.setTarget(CollidesWithPlant(1f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlayer(1f) != null && !this.CollidesWithPlayer(1f).isCreative()){
				this.setTarget(CollidesWithPlayer(1f));
				this.setStealthTag(Stealth.FALSE);
			}
		}
	}

	public void mobTick() {
		if (!this.inDyingAnimation) {
			super.mobTick();
			if (this.hasStatusEffect(PvZCubed.ICE)) {
				if (this.animationTicksLeft <= 0) {
					this.animationMultiplier = 2;
					this.isIced = true;
					this.getWorld().sendEntityStatus(this, (byte) 71);
				}
			} else {
				this.isIced = false;
				this.getWorld().sendEntityStatus(this, (byte) 72);
				this.animationMultiplier = 1;
			}
			if (this.animationTicksLeft <= 0) {
				this.setImp();
				ZombiePropEntity zombiePropEntity = null;
				for (Entity entity : this.getPassengerList()) {
					if (entity instanceof ZombiePropEntity zpe) {
						zombiePropEntity = zpe;
					}
				}
				if (this.getHealth() <= this.healthImp && (zombiePropEntity == null) && getTarget() != null && this.getImpStage().equals(Boolean.TRUE) && !this.inLaunchAnimation) {
					this.launchAnimation = 50 * animationMultiplier;
					this.inLaunchAnimation = true;
					this.getWorld().sendEntityStatus(this, (byte) 104);
				}
				if (this.launchAnimation > 0) {
					this.getNavigation().stop();
					--launchAnimation;
					tryLaunch(getTarget());
					this.inLaunchAnimation = true;
					this.getWorld().sendEntityStatus(this, (byte) 104);
				} else {
					this.inLaunchAnimation = false;
					this.getWorld().sendEntityStatus(this, (byte) 103);
				}
			}
			if (this.animationTicksLeft == 40 * animationMultiplier && !inLaunchAnimation) {
				if (!this.isInsideWaterOrBubbleColumn() && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
					this.playSound(PvZSounds.GARGANTUARSMASHEVENT, 1F, 1.0F);
				} else if (!this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
					getWorld().sendEntityStatus(this, (byte) 107);
					this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH_HIGH_SPEED, 1.5F, 1.0F);
				}
				if (getTarget() != null) {
					this.firstAttack = true;
					if (!this.isIced) {
						tryAttack(getTarget());
					}
				}
			} else if (getTarget() == null) {
				this.firstAttack = true;
			}
			if (this.animationTicksLeft > 0) {
				this.getNavigation().stop();
				--this.animationTicksLeft;
				this.getWorld().sendEntityStatus(this, (byte) 113);
			}
			if (this.animationTicksLeft <= 0) {
				this.getWorld().sendEntityStatus(this, (byte) 112);
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		if (this.getType().equals(PvZEntity.GARGANTUAR) || this.getType().equals(PvZEntity.GARGANTUARHYPNO)){
			return ModItems.GARGANTUAREGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.UNICORNGARGANTUAR) || this.getType().equals(PvZEntity.UNICORNGARGANTUARHYPNO)){
			return ModItems.UNICORNGARGANTUAREGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.CURSEDGARGOLITH) || this.getType().equals(PvZEntity.CURSEDGARGOLITHHYPNO)){
			return ModItems.CURSEDGARGOLITHEGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEEND) ||
				this.getType().equals(PvZEntity.DEFENSIVEENDHYPNO) ||
				this.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEAR) ||
				this.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO)){
			return ModItems.DEFENSIVEENDEGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.MUMMYGARGANTUAR) || this.getType().equals(PvZEntity.MUMMYGARGANTUARHYPNO)){
			return ModItems.DEFENSIVEENDEGG.getDefaultStack();
		}
		else {
			return ModItems.GARGANTUAREGG.getDefaultStack();
		}
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		if (this.getHypno()){
			player.startRiding(this, true);
			return ActionResult.success(this.getWorld().isClient);
		}
		else {
			return ActionResult.FAIL;
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	@Override
	public double getMountedHeightOffset() {
		return 0;
	}

//	@Override
//	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater){
//		if (passenger instanceof PlayerEntity){
//			passenger.setPosition(this.getX(), this.getY() + 3.25, this.getZ());
//		}
//		else {
//			super.updatePassengerPosition(passenger);
//		}
//	}

	public void createProp(){
		if (getWorld() instanceof ServerWorld serverWorld) {
			MetalHelmetEntity propentity = new MetalHelmetEntity(PvZEntity.DEFENSIVEENDGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createGargantuarAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 90.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.gargantuarH());
    }

	public static DefaultAttributeContainer.Builder createMummyGargantuarAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 90.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.mummygargantuarH());
	}

	public static DefaultAttributeContainer.Builder createDefensiveendAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 90.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.defensiveendH());
	}

	public static DefaultAttributeContainer.Builder createUnicornGargantuarAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.16D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 90.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.unicorngargantuarH());
	}

	public static DefaultAttributeContainer.Builder createGargolithAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 90.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.cursedgargolithH());
	}

	protected SoundEvent getAmbientSound() {
		if (!this.getHypno() && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.isFrozen && !this.isStunned && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			return PvZSounds.GARGANTUARMOANEVENT;
		}
		else {
			return PvZSounds.SILENCEVENET;
		}
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}



	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	@Override
	public void onDeath(DamageSource source) {
		if ((inDyingAnimation && deathTicks <= 1) || ((source.getSource() instanceof SuperChomperEntity ||
				source.getSource() instanceof ChomperEntity ||
				source.getSource() instanceof ChesterEntity ||
				source.getSource() instanceof OlivePitEntity) && this.swallowed)) {
			if (this.getVariant().equals(GargantuarVariants.MUMMY) || this.getVariant().equals(GargantuarVariants.MUMMYHYPNO)) {
				EntityType<?> type = PvZEntity.UNDYINGPHARAOH;
				if (this.getHypno()) {
					type = PvZEntity.UNDYINGPHARAOHHYPNO;
				}
				if (this.getWorld() instanceof ServerWorld serverWorld) {
					BlockPos blockPos = this.getBlockPos().add((int) this.getX(), 0, (int) this.getZ());
					PharaohEntity zombie = (PharaohEntity) type.create(getWorld());
					zombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), 0, 0);
					zombie.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
					zombie.setOwner(this);
					zombie.setRainbowTag(Rainbow.TRUE);
					zombie.rainbowTicks = 60;
					serverWorld.spawnEntityAndPassengers(zombie);
				}
			}
			super.onDeath(source);
		}
	}

	protected EntityType<?> hypnoType;

	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.DEFENSIVEEND)){
			hypnoType = PvZEntity.DEFENSIVEENDHYPNO;
		}
		else if (this.getType().equals(PvZEntity.MUMMYGARGANTUAR)){
			hypnoType = PvZEntity.MUMMYGARGANTUARHYPNO;
		}
		else if (this.getType().equals(PvZEntity.CURSEDGARGOLITH)){
			hypnoType = PvZEntity.CURSEDGARGOLITHHYPNO;
		}
		else if (this.getType().equals(PvZEntity.UNICORNGARGANTUAR)){
			hypnoType = PvZEntity.UNICORNGARGANTUARHYPNO;
		}
		else if (this.getType().equals(PvZEntity.DEFENSIVEEND_NEWYEAR)){
			hypnoType = PvZEntity.DEFENSIVEEND_NEWYEARHYPNO;
		}
		else {
			hypnoType = PvZEntity.GARGANTUARHYPNO;
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
				GargantuarEntity hypnotizedZombie = (GargantuarEntity) hypnoType.create(getWorld());
				hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnotizedZombie.initialize(serverWorld, getWorld().getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData)null, (NbtCompound) null);
				hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
				if (this.hasCustomName()) {
					hypnotizedZombie.setCustomName(this.getCustomName());
					hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.getImpStage().equals(Boolean.TRUE)){
					hypnotizedZombie.setImpStage(GargantuarEntity.ImpStage.IMP);
				}
				else {
					hypnotizedZombie.setImpStage(GargantuarEntity.ImpStage.NOIMP);
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

	private class AttackGoal extends PvZombieAttackGoal {
		public AttackGoal() {
			super(GargantuarEntity.this, 1.0, true);
		}

		@Override
		protected double getSquaredMaxAttackDistance(LivingEntity entity) {
			float f = GargantuarEntity.this.getWidth() - 0.1F;
			return (double)(f * 4F * f * 4F + entity.getWidth());
		}
	}

}
