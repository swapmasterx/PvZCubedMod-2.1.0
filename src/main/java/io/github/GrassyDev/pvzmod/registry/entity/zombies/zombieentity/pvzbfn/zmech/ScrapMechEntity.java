package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.zmech;


import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
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
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.laser.LaserEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.PvZombieAttackGoal;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.PolarBearEntity;
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

import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PLANT_LOCATION;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class ScrapMechEntity extends MachinePvZombieEntity implements GeoEntity {
	private String controllerName = "walkingcontroller";



	private int animationTicksLeft;
	private int launchAnimation;
	public boolean firstAttack;
	public boolean inAnimation;
	public boolean inLaunchAnimation;
	private boolean isDisabled = false;
	private int disableTicks = 60;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	protected ImpEntity impEntity;

	public ScrapMechEntity(EntityType<? extends ScrapMechEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 100;
        this.firstAttack = true;
		this.setCoveredTag(Covered.TRUE);
    }


	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}

		RandomGenerator randomGenerator = this.getRandom();

		if (status == 73) {
			this.isDisabled = true;
		}
		else if (status == 74) {
			this.isDisabled = false;
			this.disableTicks = 60;
		}
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

		if (status == 106) {
			for(int i = 0; i < 170; ++i) {
				double d = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 2 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 2 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.getWorld().addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.getWorld().addParticle(ParticleTypes.SMOKE, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
				this.getWorld().addParticle(ParticleTypes.FLAME, this.getX() + (this.random.range(-1, 1)), this.getY() + (this.random.range(-1, 1)), this.getZ() + (this.random.range(-1, 1)), d, e, f);
			}
			for(int i = 0; i < 16; ++i) {
				double e = this.random.nextDouble() / 2 * (this.random.range(0, 1));
				this.getWorld().addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, this.getX() + (double)MathHelper.nextBetween(randomGenerator, -0.5F, 0.5F),
						this.getY() + (this.random.range(-1, 1)),
						this.getZ()  + (double)MathHelper.nextBetween(randomGenerator,
								-0.5F, 0.5F), 0, e, 0);
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
		if (this.isFrozen || this.isStunned) {
			event.getController().setAnimationSpeed(0);
		} else if (this.isIced) {
			event.getController().setAnimationSpeed(0.5);
		} else {
			event.getController().setAnimationSpeed(1);
		}
		if (this.isInsideWaterOrBubbleColumn()) {
			if (inDyingAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("scrapmech.ducky.explode"));
				event.getController().setAnimationSpeed(1);
			} else if (inLaunchAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("scrapmech.ducky.shoot"));
			} else if (inAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("scrapmech.ducky.smash"));
			} else if (this.isDisabled && this.disableTicks > 0) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("scrapmech.ducky.stun"));
				event.getController().setAnimationSpeed(1);
			} else if (this.isDisabled) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("scrapmech.ducky.stun.idle"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("scrapmech.ducky"));
			}
		}
		else {
			if (inDyingAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("scrapmech.explode"));
				event.getController().setAnimationSpeed(1);
			} else if (inLaunchAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("scrapmech.shoot"));
			} else if (inAnimation) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("scrapmech.smash"));
			} else if (!(event.getLimbSwingAmount() > -0.01F && event.getLimbSwingAmount() < 0.01F)) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("scrapmech.walk"));
			} else if (this.isDisabled && this.disableTicks > 0) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("scrapmech.stun"));
				event.getController().setAnimationSpeed(1);
			} else if (this.isDisabled) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("scrapmech.stun.idle"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("scrapmech.idle"));
			}
		}
        return PlayState.CONTINUE;
    }

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		initCustomGoals();
	}

	protected void initCustomGoals() {

		this.goalSelector.add(1, new ScrapMechEntity.AttackGoal());
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

		this.goalSelector.add(1, new ScrapMechEntity.AttackGoal());
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

	//Launch Basket
	public void tryShoot(Entity target) {
		LaserEntity proj = new LaserEntity(PvZEntity.LASER, this.getWorld());
		List<LivingEntity> list = getWorld().getNonSpectatingEntities(LivingEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getPos()).expand(this.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE) + 1));
		double targetDist = 0;
		proj.damageMultiplier = this.damageMultiplier;
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof PlantEntity plantEntity && !(plantEntity instanceof GardenChallengeEntity) && !(plantEntity instanceof GardenEntity) && !plantEntity.getImmune() && !(PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying"))){
				if (targetDist == 0){
					targetDist = this.squaredDistanceTo(plantEntity);
					target = plantEntity;
				}
				else if (this.squaredDistanceTo(plantEntity) <= targetDist){
					targetDist = this.squaredDistanceTo(plantEntity);
					target = plantEntity;
				}
			}
		}
		if (launchAnimation == 16 * animationMultiplier && !this.hasStatusEffect(PvZCubed.FROZEN) && !this.hasStatusEffect(PvZCubed.STUN) && !this.hasStatusEffect(PvZCubed.DISABLE)) {
			if (target instanceof ZombiePropEntity zombiePropEntity && zombiePropEntity.hasVehicle()){
				target = zombiePropEntity.getVehicle();
			}
			if (target != null) {
				double time = 1;

				Vec3d vec3d = new Vec3d((double) 0.5, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				Vec3d targetPos = target.getPos() ;
				Vec3d predictedPos = targetPos.add(target.getVelocity().multiply(time));
				double d = this.squaredDistanceTo(predictedPos);
				float df = (float)d;
				double e = predictedPos.getX() - this.getX();
				double f = target.getY() - this.getY();
				double g = predictedPos.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				if (target instanceof PlantEntity plantEntity && plantEntity.getLowProfile()){
					f = f - 0.5;
				}
				proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
				proj.updatePosition(this.getX() + vec3d.x, this.getY() + 1D, this.getZ() + vec3d.z);
				proj.setOwner(this);
			}
			else {
				Vec3d noZombie = new Vec3d((double) +1, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				Vec3d noZombie2 = new Vec3d(this.getX() + noZombie.x, this.getY() + noZombie.y, this.getZ() + noZombie.z);
				double d = this.squaredDistanceTo(noZombie2);
				float df = (float)d;
				double e = noZombie2.getX() - this.getX();
				double f = 0;
				double g = noZombie2.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.85F, 0F);
			}

			proj.setOwner(this);
			this.playSound(PvZSounds.MECHSHOOTEVENT, 1F, 1);
			this.getWorld().spawnEntity(proj);
		}
	}

	@Override
	public void setYaw(float yaw) {
		if (!this.inDyingAnimation) {
			super.setYaw(yaw);
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	private void raycastExplode() {
		double squaredDist;
		squaredDist = 9;
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
			} while (this.squaredDistanceTo(livingEntity) > squaredDist);

			if (livingEntity instanceof OilTile oilTile){
				oilTile.makeFireTrail(oilTile.getBlockPos());
			}
			if (this.getHypno()) {
				if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
					if (livingEntity.getFirstPassenger() != null) {
						livingEntity.getFirstPassenger().damage(getDamageSources().explosion(this,this), 30);
					} else {
						livingEntity.damage(getDamageSources().explosion(this,this), 30);
					}
				}
			} else {
				if (livingEntity instanceof PlantEntity || (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
					if (livingEntity.getFirstPassenger() != null && livingEntity instanceof GeneralPvZombieEntity) {
						livingEntity.getFirstPassenger().damage(getDamageSources().explosion(this,this), 30);
					} else {
						livingEntity.damage(getDamageSources().explosion(this,this), 30);
					}
				}
			}
		}
	}

	private void spawnEffectsCloud() {
		AreaEffectCloudEntity areaEffectCloudEntity = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity.setParticleType(ParticleTypes.FLAME);
		areaEffectCloudEntity.setRadius(6F);
		areaEffectCloudEntity.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity.setWaitTime(5);
		areaEffectCloudEntity.setDuration(areaEffectCloudEntity.getDuration() / 35);
		areaEffectCloudEntity.setRadiusGrowth(-areaEffectCloudEntity.getRadius() / (float) areaEffectCloudEntity.getDuration());
		this.getWorld().spawnEntity(areaEffectCloudEntity);
		AreaEffectCloudEntity areaEffectCloudEntity2 = new AreaEffectCloudEntity(this.getWorld(), this.getX(), this.getY(), this.getZ());
		areaEffectCloudEntity2.setParticleType(ParticleTypes.SMOKE);
		areaEffectCloudEntity2.setRadius(2F);
		areaEffectCloudEntity2.setRadiusOnUse(-0.5F);
		areaEffectCloudEntity2.setWaitTime(5);
		areaEffectCloudEntity2.setDuration(areaEffectCloudEntity2.getDuration() / 80);
		areaEffectCloudEntity2.setRadiusGrowth(-areaEffectCloudEntity2.getRadius() / (float)areaEffectCloudEntity2.getDuration());
		this.getWorld().spawnEntity(areaEffectCloudEntity2);
	}

	public void tick() {

		if (this.isDisabled){
			--disableTicks;
		}
		if (this.getHealth() <= 0 && !inDyingAnimation){
			this.inDyingAnimation = true;
			this.deathTicks = 60;
			this.playSound(PvZSounds.MECHALARMEVENT, 0.33f, 1);
		}
		if (this.inDyingAnimation){
			this.getNavigation().stop();
			this.setHealth(1);
		}
		--deathTicks;
		if (deathTicks == 2){
			this.raycastExplode();
			this.getWorld().sendEntityStatus(this, (byte) 106);
			this.playSound(PvZSounds.CHERRYBOMBEXPLOSIONEVENT, 0.5F, 1F);
			this.spawnEffectsCloud();
		}
		if (deathTicks == 1){
			onDeath(PvZDamageTypes.of(getWorld(), PvZDamageTypes.SELF_TERMINATE_DAMAGE));
		}
		super.tick();
		if (this.getAttacking() == null && !(this.getHypno())){
			if (this.CollidesWithPlant(0f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
				this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
				this.setTarget(CollidesWithPlant(0f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlant(2f, 0f) != null && !this.hasStatusEffect(PvZCubed.BOUNCED)){
				this.setVelocity(0, -0.3, 0);
						this.getNavigation().stop();
				this.setTarget(CollidesWithPlant(2f, 0f));
				this.setStealthTag(Stealth.FALSE);
			}
			else if (this.CollidesWithPlayer(2f) != null && !this.CollidesWithPlayer(2f).isCreative()){
				this.setTarget(CollidesWithPlayer(2f));
				this.setStealthTag(Stealth.FALSE);
			}
		}
	}

	public void mobTick() {
		if (this.hasStatusEffect(PvZCubed.DISABLE)){
			this.isDisabled = true;
			this.getWorld().sendEntityStatus(this, (byte) 73);
		}
		else {
			this.isDisabled = false;
			this.disableTicks = 60;
			this.getWorld().sendEntityStatus(this, (byte) 74);
		}
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
				double random = Math.random();
				for (int x = 0; x <= 7; ++x){
					if ((this.CollidesWithPlant((float)x, 0f) != null)
							&& !this.hasStatusEffect(PvZCubed.BOUNCED)) {
						if (random <= 0.01 && getTarget() != null && !this.inLaunchAnimation) {
							this.launchAnimation = 50 * animationMultiplier;
							this.inLaunchAnimation = true;
							this.getWorld().sendEntityStatus(this, (byte) 104);
						}
					}
				}
				if (this.launchAnimation == 35) {
					this.playSound(PvZSounds.MECHWINDUPEVENT, 0.5f, 1);
				}
				if (this.launchAnimation > 0) {
					this.getNavigation().stop();
					--launchAnimation;
					tryShoot(getTarget());
					this.inLaunchAnimation = true;
					this.getWorld().sendEntityStatus(this, (byte) 104);
				}
				else {
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
		return ModItems.SCRAPMECHEGG.getDefaultStack();
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
	protected float method_52537(Entity entity) {
		return 0.00F;
	}


	@Override
	protected void updatePassengerPosition(Entity passenger, PositionUpdater positionUpdater){
		if (passenger instanceof PlayerEntity){
			passenger.setPosition(this.getX(), this.getY() + 3.25, this.getZ());
		}
		else {
			super.updatePassengerPosition(passenger);
		}
	}

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

	public static DefaultAttributeContainer.Builder createScrapMechAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)

                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.12D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 90.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.scrapmechH());
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
		return PvZSounds.SILENCEVENET;
	}
	protected void playStepSound(BlockPos pos, BlockState state) {
		this.playSound(this.getStepSound(), 0.15F, 1.0F);
	}


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/

	@Override
	public void onDeath(DamageSource source) {
		if (inDyingAnimation && deathTicks <= 1) {
			EntityType<?> type = PvZEntity.SCRAPIMP;
			if (this.getHypno()) {
				type = PvZEntity.SCRAPIMPHYPNO;
			}
			if (this.getWorld() instanceof ServerWorld serverWorld) {
				BlockPos blockPos = this.getBlockPos().add((int) this.getX(), 0, (int) this.getZ());
				ImpEntity imp = (ImpEntity) type.create(getWorld());
				imp.refreshPositionAndAngles(this.getX(), this.getY() + 2, this.getZ(), 0, 0);
				imp.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				imp.setOwner(this);
				imp.setVelocity(0, 1, 0);
				this.playSound(PvZSounds.IMPLAUNCHEVENT, 1F, 1);
				imp.setStealthTag(Stealth.TRUE);
				serverWorld.spawnEntityAndPassengers(imp);
			}
		}
		if ((inDyingAnimation && deathTicks <= 1) || ((source.getSource() instanceof SuperChomperEntity ||
				source.getSource() instanceof ChomperEntity ||
				source.getSource() instanceof ChesterEntity ||
				source.getSource() instanceof OlivePitEntity) && this.swallowed)) {
			super.onDeath(source);
		}
	}

	private class AttackGoal extends PvZombieAttackGoal {
		public AttackGoal() {

			super(ScrapMechEntity.this, 1.0, true);
		}

		@Override
		protected void attack(LivingEntity target) {
			float f = ScrapMechEntity.this.getWidth() - 0.1F;
			if (this.mob.squaredDistanceTo(target) < (double) (f * 4F * f * 4F + target.getWidth()) * (double) (f * 4F * f * 4F + target.getWidth())) {
				this.resetCooldown();
			}
		}

	}
}
