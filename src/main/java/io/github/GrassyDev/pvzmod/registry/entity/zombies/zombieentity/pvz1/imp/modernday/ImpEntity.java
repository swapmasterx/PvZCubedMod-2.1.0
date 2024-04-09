package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer.AnnouncerImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.crystalhelmet.CrystalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
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
import net.minecraft.particle.ParticleTypes;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class ImpEntity extends PvZombieEntity implements GeoEntity {

    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "walkingcontroller";

    public ImpEntity(EntityType<? extends ImpEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
    }

	static {

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

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 105) {
			for(int i = 0; i < 32; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				double d = this.random.nextDouble() * this.random.range(-2, 2);
				double e = this.random.nextDouble() * this.random.range(0, 2);
				double f = this.random.nextDouble() * this.random.range(-2, 2);
				// RAINBOW
				double dx = (double) (this.random.range(0, 255) & 255) / 255.0;
				double ex = (double) (this.random.range(0, 255) & 255) / 255.0;
				double fx = (double) (this.random.range(0, 255) & 255) / 255.0;
				this.getWorld().addParticle(ParticleTypes.NOTE, this.getX() + d, this.getY() + e, this.getZ() + f, dx, ex, fx);
			}
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ImpEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		if (this.getType().equals(PvZEntity.SUPERFANIMP)){
			setVariant(ImpVariants.SUPERFAN);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.IMPDRAGON)){
			setCanBurn(CanBurn.FALSE);
			setVariant(ImpVariants.IMPDRAGON);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.MUMMYIMP)){
			setVariant(ImpVariants.MUMMY);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.BASSIMP)){
			setVariant(ImpVariants.BASSIMP);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.NEWYEARIMP)){
			setVariant(ImpVariants.NEWYEAR);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.IMPTHROWER)){
			setVariant(ImpVariants.THROWER);
			this.initCustomGoals();
			createRandomImp();
		}
		else if (this.getType().equals(PvZEntity.SCRAPIMP)){
			setVariant(ImpVariants.SCRAP);
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.CINDERELLAIMP)){
			setVariant(ImpVariants.CINDERELLA);
			createCrsytalShoeProp();
			this.initCustomGoals();
		}
		else if (this.getType().equals(PvZEntity.IMPHYPNO)){
			setVariant(ImpVariants.DEFAULTHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.SCRAPIMPHYPNO)){
			setVariant(ImpVariants.SCRAPHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.CINDERELLAIMPHYPNO)){
			setVariant(ImpVariants.CINDERELLAHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.IMPDRAGONHYPNO)){
			setVariant(ImpVariants.IMPDRAGONHYPNO);
			setCanBurn(CanBurn.FALSE);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.MUMMYIMPHYPNO)){
			setVariant(ImpVariants.MUMMYHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.BASSIMPHYPNO)){
			setVariant(ImpVariants.BASSIMPHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.SUPERFANIMPHYPNO)){
			setVariant(ImpVariants.SUPERFANHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.NEWYEARIMPHYPNO)){
			setVariant(ImpVariants.NEWYEARHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else if (this.getType().equals(PvZEntity.IMPTHROWERHYPNO)){
			setVariant(ImpVariants.THROWERHYPNO);
			this.setHypno(IsHypno.TRUE);
		}
		else {
			setVariant(ImpVariants.DEFAULT);
		}
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ImpVariants getVariant() {
		return ImpVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(ImpVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	public void createCrsytalShoeProp() {
		if (getWorld() instanceof ServerWorld serverWorld) {
			CrystalHelmetEntity propentity = new CrystalHelmetEntity(PvZEntity.CRYSTALSHOEGEAR, this.getWorld());
			propentity.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
			propentity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
			propentity.startRiding(this);
		}
	}

	public void createRandomImp(){
		List<EntityType<?>> impList = new ArrayList<>();
		List<EntityType<?>> impListHypno = new ArrayList<>();
		impList.add(PvZEntity.IMP);
		impListHypno.add(PvZEntity.IMPHYPNO);
		impList.add(PvZEntity.IMPDRAGON);
		impListHypno.add(PvZEntity.IMPDRAGONHYPNO);
		impList.add(PvZEntity.MUMMYIMP);
		impListHypno.add(PvZEntity.MUMMYIMPHYPNO);
		impList.add(PvZEntity.BASSIMP);
		impListHypno.add(PvZEntity.BASSIMPHYPNO);
		impList.add(PvZEntity.SUPERFANIMP);
		impListHypno.add(PvZEntity.SUPERFANIMPHYPNO);
		impList.add(PvZEntity.NEWYEARIMP);
		impListHypno.add(PvZEntity.NEWYEARIMPHYPNO);
		impList.add(PvZEntity.ANNOUNCERIMP);
		impListHypno.add(PvZEntity.ANNOUNCERIMPHYPNO);
		impList.add(PvZEntity.SCRAPIMP);
		impListHypno.add(PvZEntity.SCRAPIMPHYPNO);
		impList.add(PvZEntity.CINDERELLAIMP);
		impListHypno.add(PvZEntity.CINDERELLAIMPHYPNO);
		EntityType<?> impEntity = impList.get(random.range(0, impList.size() - 1));
		EntityType<?> impEntityHypno = impListHypno.get(random.range(0, impList.size() - 1));
		if (getWorld() instanceof ServerWorld serverWorld) {
			GeneralPvZombieEntity imp = null;
			if (this.getHypno()){
				imp = (GeneralPvZombieEntity) impEntityHypno.create(getWorld());
			}
			else {
				imp = (GeneralPvZombieEntity) impEntity.create(getWorld());
			}
			if (imp != null){
				imp.setFlying(Flying.TRUE);
				imp.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				imp.initialize(serverWorld, this.getWorld().getLocalDifficulty(this.getBlockPos()), SpawnReason.MOB_SUMMONED, (EntityData) null, (NbtCompound) null);
				imp.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.bodyYaw, 0.0F);
				imp.startRiding(this);
			}
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
		if (this.isInsideWaterOrBubbleColumn()) {
			if ((this.getVariant().equals(ImpVariants.THROWER) || this.getVariant().equals(ImpVariants.THROWERHYPNO)) && this.hasPassengers()) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("impthrow.ducky"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("imp.ducky"));
			}
		}else {
			if (!this.isOnGround()) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("imp.ball"));
				if (this.hasVehicle()){
					event.getController().setAnimationSpeed(0);
				}
				else if (this.getVariant().equals(ImpVariants.IMPDRAGON) || this.getVariant().equals(ImpVariants.IMPDRAGONHYPNO)) {
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.375);
					} else {
						event.getController().setAnimationSpeed(0.75);
					}
				} else {
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					} else {
						event.getController().setAnimationSpeed(1);
					}
				}
			} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				if ((this.getVariant().equals(ImpVariants.THROWER) || this.getVariant().equals(ImpVariants.THROWERHYPNO)) && this.hasPassengers()) {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("impthrow.run"));
				} else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("imp.run"));
				}
				if (this.getVariant().equals(ImpVariants.IMPDRAGON) || this.getVariant().equals(ImpVariants.IMPDRAGONHYPNO)) {
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.5);
					} else {
						event.getController().setAnimationSpeed(1);
					}
				} else {
					if (this.isFrozen || this.isStunned) {
						event.getController().setAnimationSpeed(0);
					} else if (this.isIced) {
						event.getController().setAnimationSpeed(0.75);
					} else {
						event.getController().setAnimationSpeed(1.5);
					}
				}
			} else {
				if ((this.getVariant().equals(ImpVariants.THROWER) || this.getVariant().equals(ImpVariants.THROWERHYPNO)) && this.hasPassengers()) {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("impthrow.idle"));
				} else {
					event.getController().setAnimation(RawAnimation.begin().thenLoop("imp.idle"));
				}
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
		if (this.getType().equals(PvZEntity.IMPHYPNO) ||
				this.getType().equals(PvZEntity.SUPERFANIMPHYPNO) ||
				this.getType().equals(PvZEntity.NEWYEARIMPHYPNO) ||
				this.getType().equals(PvZEntity.IMPDRAGONHYPNO) ||
				this.getType().equals(PvZEntity.MUMMYIMPHYPNO) ||
				this.getType().equals(PvZEntity.BASSIMPHYPNO) ||
				this.getType().equals(PvZEntity.IMPTHROWERHYPNO) ||
				this.getType().equals(PvZEntity.SCRAPIMPHYPNO) ||
				this.getType().equals(PvZEntity.CINDERELLAIMPHYPNO)) {
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

	@Override
	public boolean tryAttack(Entity target) {
		if (!(this.getVariant().equals(ImpVariants.BASSIMP) || this.getVariant().equals(ImpVariants.BASSIMPHYPNO))){
			return super.tryAttack(target);
		}
		else {
			return false;
		}
	}

	private void bassExplode() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5));
		Iterator var9 = list.iterator();
		while (true) {
			LivingEntity livingEntity;
			do {
				do {
					if (!var9.hasNext()) {
						return;
					}

					livingEntity = (LivingEntity) var9.next();
				} while (livingEntity == this);
			} while (this.squaredDistanceTo(livingEntity) > 9);

			if (this.getHypno()) {
				if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					if (livingEntity.getFirstPassenger() != null) {
						livingEntity.getFirstPassenger().damage(getDamageSources().explosion(this, this), 15);
					} else {
						livingEntity.damage(getDamageSources().explosion(this, this), 15);
					}
				}
			} else {
				if (livingEntity instanceof PlantEntity || (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
					if (livingEntity.getFirstPassenger() != null && livingEntity instanceof GeneralPvZombieEntity) {
						livingEntity.getFirstPassenger().damage(getDamageSources().explosion(this, this), 15);
					} else {
						livingEntity.damage(getDamageSources().explosion(this, this), 15);
					}
				}
			}
		}
	}

	protected int bassTime = 20;

	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		if (this.getType().equals(PvZEntity.IMPDRAGON) || this.getType().equals(PvZEntity.IMPDRAGONHYPNO)){
			setCanBurn(CanBurn.FALSE);
		}
		super.tick();
		if (!this.hasStatusEffect(FROZEN) && !this.hasStatusEffect(STUN) && !this.getRainbow()) {
			if (age > 40 && this.isAlive() && this.getTarget() != null) {
				if (--bassTime <= 0 && (this.getVariant().equals(ImpVariants.BASSIMP) || this.getVariant().equals(ImpVariants.BASSIMPHYPNO))) {
					this.bassExplode();
					this.bassTime = 20;
					this.playSound(PvZSounds.BASSPLAYEVENT, 0.125f, (float) (0.5F + Math.random()));
					this.getWorld().sendEntityStatus(this, (byte) 105);
				}
			}
		}
		if (this.getTarget() == null){
			this.bassTime = 20;
		}
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0.1f, 0f) instanceof GardenChallengeEntity){
					this.setTarget(CollidesWithPlant(0.1f, 0f));
					this.setStealthTag(Stealth.FALSE);
				}
				else if (this.CollidesWithPlant(0.1f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
				if (this.isOnGround() || this.isInsideWaterOrBubbleColumn()){
					this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
					this.setTarget(CollidesWithPlant(0.1f, 0f));
				}
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlayer(1.5f) != null && !this.CollidesWithPlayer(1.5f).isCreative()){
				this.setTarget(CollidesWithPlayer(1.5f));
				this.setStealthTag(Stealth.FALSE);
			}
			for (int x = 0; x <= 6; ++x) {
				if ((this.CollidesWithPlant((float) x, 0f) != null)
						&& !this.hasStatusEffect(PvZCubed.BOUNCED)) {
					tryLaunch(this.getTarget());
				}
			}
		}
		List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, PvZEntity.IMP.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()).expand(0.25));
		List<PlantEntity> list1 = new ArrayList<>();
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof PlantEntity plantEntity && (PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("tall") || PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying"))){
				list1.add(plantEntity);
			}
		}
		if (!list1.isEmpty() && !this.hasStatusEffect(PvZCubed.BOUNCED) && !this.isOnGround() && !this.isInsideWaterOrBubbleColumn()){
			this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
			this.setTarget(list1.get(0));
		}
	}

	protected void mobTick() {
		super.mobTick();

	}

	//Launch Imp
	public void tryLaunch(Entity target) {
		ImpEntity impEntity = null;
		AnnouncerImpEntity announcerImp = null;
		for (Entity entity : this.getPassengerList()) {
			if (entity instanceof ImpEntity imp) {
				impEntity = imp;
			}
			if (entity instanceof AnnouncerImpEntity imp) {
				announcerImp = imp;
			}
		}
		if (impEntity != null) {
			impEntity.dismountVehicle();
			if (target != null) {
				double d = this.squaredDistanceTo(target);
				float df = (float) d;
				double e = target.getX() - this.getX();
				double f = target.getY() - this.getY();
				double g = target.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				if (this.isAlive()) {
					impEntity.setVelocity(e * (double) h, f * (double) h, g * (double) h, 1.5F, 0F);
				}
				else {
					impEntity.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.75F, 0F);
				}
			} else {
				impEntity.setVelocity(random.range(-1, 1), 0, random.range(-1, 1), 1.5F, 0F);
			}
			impEntity.updatePosition(this.getX(), this.getY() + 3.75D, this.getZ());
			impEntity.setOwner(this);
			this.playSound(PvZSounds.IMPLAUNCHEVENT, 1F, 1);
			if (this.getHypno()) {
				impEntity.setHypno(IsHypno.TRUE);
			}
			impEntity.setRainbowTag(Rainbow.TRUE);
			impEntity.rainbowTicks = 10;
			impEntity.bassTime = 40;
			impEntity.setFlying(Flying.FALSE);
		}
		if (announcerImp != null) {
			announcerImp.dismountVehicle();
			if (target != null) {
				double d = this.squaredDistanceTo(target);
				float df = (float) d;
				double e = target.getX() - this.getX();
				double f = target.getY() - this.getY();
				double g = target.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				if (this.isAlive()) {
					announcerImp.setVelocity(e * (double) h, f * (double) h, g * (double) h, 1.5F, 0F);
				}
				else {
					announcerImp.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.75F, 0F);
				}
			} else {
				announcerImp.setVelocity(random.range(-1, 1), 0, random.range(-1, 1), 1.5F, 0F);
			}
			announcerImp.updatePosition(this.getX(), this.getY() + 3.75D, this.getZ());
			announcerImp.setOwner(this);
			this.playSound(PvZSounds.IMPLAUNCHEVENT, 1F, 1);
			if (this.getHypno()) {
				announcerImp.setHypno(IsHypno.TRUE);
			}
			announcerImp.setRainbowTag(Rainbow.TRUE);
			announcerImp.rainbowTicks = 10;
			announcerImp.setFlying(Flying.FALSE);
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		for (Entity entity : this.getPassengerList()){
			if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity){
				generalPvZombieEntity.setFlying(Flying.FALSE);
			}
		}
		super.onDeath(source);
	}

	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		if (this.getVariant().equals(ImpVariants.SCRAP) || this.getVariant().equals(ImpVariants.SCRAPHYPNO)){
			return ModItems.SCRAPIMPEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(ImpVariants.BASSIMP) || this.getVariant().equals(ImpVariants.BASSIMPHYPNO)){
			return ModItems.BASSIMPEGG.getDefaultStack();
		}
		else if (this.getVariant().equals(ImpVariants.CINDERELLA) || this.getVariant().equals(ImpVariants.CINDERELLAHYPNO)){
			return ModItems.BASSIMPEGG.getDefaultStack();
		}
		else{
			return ModItems.IMPEGG.getDefaultStack();
		}
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/
	@Override
	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater){
		float g = (float) ((this.isRemoved() ? 0.01F : this.method_52537(passenger)) + passenger.getHeightOffset(passenger));
		float f = 0.05F;

		Vec3d vec3d = new Vec3d((double) f, 0.0, 0.0).rotateY(-this.getYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		passenger.setPosition(this.getX() + vec3d.x, this.getY() + (double) g, this.getZ() + vec3d.z);
		passenger.setBodyYaw(this.bodyYaw);
	}
	@Override
	protected float method_52537(Entity entity) {
		return 1.00F;
	}

	public boolean canWalkOnFluid(FluidState state) {
		return state.isIn(FluidTags.WATER);
	}

	protected boolean shouldSwimInFluids() {
		return true;
	}

	public static DefaultAttributeContainer.Builder createImpAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.impH());
    }

	public static DefaultAttributeContainer.Builder createScrapImpAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.26D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.scrapimpH());
	}

	public static DefaultAttributeContainer.Builder createImpThrowAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.21D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.impH());
	}

	public static DefaultAttributeContainer.Builder createImpDragonAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.impdragonH());
	}

	public static DefaultAttributeContainer.Builder createBassImpAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.18D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.bassimpH());
	}

	public static DefaultAttributeContainer.Builder createCinderellaImpAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 8.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.impH());
	}

	@Override
	public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
		return false;
	}

	@Override
	public boolean shouldSpawnSprintingParticles() {
		return false;
	}

	@Override
	protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
		super.fall(heightDifference, onGround, landedState, landedPosition);
	}

	protected SoundEvent getAmbientSound() {
		if (!this.getHypno() && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.isFrozen && !this.isStunned && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			return PvZSounds.IMPMOANEVENT;
		}
		else {
			return PvZSounds.SILENCEVENET;
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



	public void setVelocity(double x, double y, double z, float speed, float divergence) {
		Vec3d vec3d = (new Vec3d(x, y, z)).normalize().add(this.random.nextTriangular(0.0, 0.0172275 * (double)divergence), this.random.nextTriangular(0.0, 0.0172275 * (double)divergence), this.random.nextTriangular(0.0, 0.0172275 * (double)divergence)).multiply((double)speed);
		this.setVelocity(vec3d);
		double d = vec3d.horizontalLength();
		this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
		this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
		this.prevYaw = this.getYaw();
		this.prevPitch = this.getPitch();
	}



	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	protected EntityType<?> hypnoType;

	protected EntityType<?> hypnoType2;
	protected void checkHypno(){
		if (this.getType().equals(PvZEntity.SUPERFANIMP)){
			hypnoType = PvZEntity.SUPERFANIMPHYPNO;
		}
		else if (this.getType().equals(PvZEntity.IMPDRAGON)){
			hypnoType = PvZEntity.IMPDRAGONHYPNO;
		}
		else if (this.getType().equals(PvZEntity.MUMMYIMP)){
			hypnoType = PvZEntity.MUMMYIMPHYPNO;
		}
		else if (this.getType().equals(PvZEntity.BASSIMP)){
			hypnoType = PvZEntity.BASSIMPHYPNO;
		}
		else if (this.getType().equals(PvZEntity.NEWYEARIMP)){
			hypnoType = PvZEntity.NEWYEARIMPHYPNO;
		}
		else if (this.getType().equals(PvZEntity.IMPTHROWER)){
			hypnoType = PvZEntity.IMPTHROWERHYPNO;
		}
		else if (this.getType().equals(PvZEntity.SCRAPIMP)){
			hypnoType = PvZEntity.SCRAPIMPHYPNO;
		}
		else if (this.getType().equals(PvZEntity.CINDERELLAIMP)){
			hypnoType = PvZEntity.CINDERELLAIMPHYPNO;
		}
		else {
			hypnoType = PvZEntity.IMPHYPNO;
		}
	}

	protected void checkPassanger(){
		for (Entity entity : this.getPassengerList()) {
			if (entity instanceof ImpEntity || entity instanceof AnnouncerImpEntity) {
				if (entity.getType().equals(PvZEntity.SUPERFANIMP)) {
					hypnoType2 = PvZEntity.SUPERFANIMPHYPNO;
				} else if (entity.getType().equals(PvZEntity.IMPDRAGON)) {
					hypnoType2 = PvZEntity.IMPDRAGONHYPNO;
				} else if (entity.getType().equals(PvZEntity.MUMMYIMP)) {
					hypnoType2 = PvZEntity.MUMMYIMPHYPNO;
				} else if (entity.getType().equals(PvZEntity.BASSIMP)) {
					hypnoType2 = PvZEntity.BASSIMPHYPNO;
				} else if (entity.getType().equals(PvZEntity.NEWYEARIMP)) {
					hypnoType2 = PvZEntity.NEWYEARIMPHYPNO;
				} else if (entity.getType().equals(PvZEntity.IMPTHROWER)) {
					hypnoType2 = PvZEntity.IMPTHROWERHYPNO;
				} else if (entity.getType().equals(PvZEntity.SCRAPIMP)) {
					hypnoType2 = PvZEntity.SCRAPIMPHYPNO;
				} else if (entity.getType().equals(PvZEntity.CINDERELLAIMP)) {
					hypnoType2 = PvZEntity.CINDERELLAIMPHYPNO;
				} else if (entity.getType().equals(PvZEntity.ANNOUNCERIMP)) {
					hypnoType2 = PvZEntity.ANNOUNCERIMPHYPNO;
				} else {
					hypnoType2 = PvZEntity.IMPHYPNO;
				}
			}
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
				ImpEntity hypnotizedZombie = (ImpEntity) hypnoType.create(getWorld());
				hypnotizedZombie.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				hypnotizedZombie.initialize(serverWorld, getWorld().getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData)null, (NbtCompound) null);
				hypnotizedZombie.setAiDisabled(this.isAiDisabled());
				hypnotizedZombie.setHealth(this.getHealth());
				ImpEntity hypnoPassenger = null;
				if (this.hasCustomName()) {
					hypnotizedZombie.setCustomName(this.getCustomName());
					hypnotizedZombie.setCustomNameVisible(this.isCustomNameVisible());
				}
				for (Entity entity1 : this.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zpe.setHypno(IsHypno.TRUE);
						zpe.startRiding(hypnotizedZombie);
					}
					else if (entity1 instanceof GeneralPvZombieEntity zombie){
						checkPassanger();
						hypnoPassenger = (ImpEntity) hypnoType2.create(getWorld());
						hypnoPassenger.refreshPositionAndAngles(zombie.getX(), zombie.getY(), zombie.getZ(), zombie.getYaw(), zombie.getPitch());
						hypnoPassenger.initialize(serverWorld, getWorld().getLocalDifficulty(hypnotizedZombie.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData)null, (NbtCompound) null);
						hypnoPassenger.setAiDisabled(zombie.isAiDisabled());
						hypnoPassenger.setHealth(zombie.getHealth());
						zombie.remove(RemovalReason.DISCARDED);
					}
				}

				hypnotizedZombie.setPersistent();


				hypnotizedZombie.setHeadYaw(this.getHeadYaw());
				if (hypnoPassenger != null){
					hypnoPassenger.setPersistent();
					hypnoPassenger.setHeadYaw(hypnotizedZombie.getHeadYaw());
					hypnoPassenger.startRiding(hypnotizedZombie);
				}
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
