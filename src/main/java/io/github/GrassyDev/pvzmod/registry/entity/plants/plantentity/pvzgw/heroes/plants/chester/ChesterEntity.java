package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.chester;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.cheese.CheeseProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class ChesterEntity extends PlantEntity implements GeoAnimatable, RangedAttackMob {

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ChesterEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private int attackTicksLeft;
    public boolean notEating;
    public boolean eatingShield;
	public boolean isFiring;
	public boolean isFiringGoop;
	private String controllerName = "chompcontroller";

    public ChesterEntity(EntityType<? extends ChesterEntity> entityType, World world) {
        super(entityType, world);
		this.attackTicksLeft = 0;

		this.targetStrength = true;
		this.isBurst = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(CHEWTIME, 0);
		this.dataTracker.startTracking(GOOP, false);
	}
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		//Variant//
		this.dataTracker.set(CHEWTIME, tag.getInt("Count"));
		this.dataTracker.set(GOOP, tag.getBoolean("Goop"));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
		tag.putInt("Count", this.getTypeCount());
		tag.putBoolean("Goop", this.getGoop());
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
		if (status == 113) {
			this.isFiringGoop = true;
		} else if (status == 112) {
			this.isFiringGoop = false;
		}
		if (status == 104) {
			this.eatingShield = false;
			this.notEating = false;
		}
		if (status == 105) {
			this.eatingShield = true;
			this.notEating = false;
		}
		else if (status == 106) {
			this.eatingShield = false;
			this.notEating = true;
		}
	}


	/** /~*~//~*VARIANTS~//~*~// **/

	// Summoning Tag

	protected static final TrackedData<Boolean> GOOP =
			DataTracker.registerData(ChesterEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum HasGoop {
		TRUE(true),
		FALSE(false);

		HasGoop(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean getGoop() {
		return this.dataTracker.get(GOOP);
	}

	public void setGoop(ChesterEntity.HasGoop hasGoop) {
		this.dataTracker.set(GOOP, hasGoop.getId());
	}


	//Counter

	private static final TrackedData<Integer> CHEWTIME =
			DataTracker.registerData(ChesterEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public int getTypeCount() {
		return this.dataTracker.get(CHEWTIME);
	}

	public void setCount(Integer count) {
		this.dataTracker.set(CHEWTIME, count);
	}

	public void reduceCount(){
		int count = getTypeCount();
		this.dataTracker.set(CHEWTIME, count - 1);
	}


	/** /~*~//~*GECKOLIB ANIMATION~//~*~// **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	@Override
	public double getTick(Object object) {
		return 0;
	}


	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
        int i = this.getTypeCount();
		if (this.isFiringGoop){
			event.getController().setAnimation(RawAnimation.begin().thenPlay("chomper.goop"));
		}
		else if (this.notEating) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("chomper.chomp2"));
		}
		else if (this.isFiring){
			event.getController().setAnimation(RawAnimation.begin().thenPlay("chomper.chomp4"));
		}
		else if (i > 0 && this.eatingShield) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("chomper.chew2"));
		}
		else if (i > 0) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("chomper.chew"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("chomper.idle"));
		}
        return PlayState.CONTINUE;
    }

	/**
	 * /~*~//~**~//~*~//
	 **/

	protected void initGoals() {
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}



	public void smack(Entity target) {
		ZombiePropEntity passenger = null;
		boolean hasHelmet = false;
		boolean hasShield = false;
		Entity damaged = target;
		for (Entity entity1 : target.getPassengerList()) {
			if (entity1 instanceof ZombiePropEntity zpe) {
				passenger = zpe;
				hasHelmet = !(entity1 instanceof ZombieShieldEntity);
				hasShield = entity1 instanceof ZombieShieldEntity || passenger.isCovered();
				if (hasShield){
					damaged = passenger;
				}
			}
		}
		String zombieSize = PvZCubed.ZOMBIE_SIZE.get(damaged.getType()).orElse("medium");
		String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
		float damage = Integer.MAX_VALUE;
		if ((target instanceof GraveEntity ||
				IS_MACHINE.get(target.getType()).orElse(false).equals(true) ||
				zombieSize.equals("gargantuar") ||
				zombieSize.equals("big")) && !hasShield){
			damage = 32;
			this.attackTicksLeft = 25;
			this.setCount(25);
			this.getWorld().sendEntityStatus(this, (byte) 106);
		}
		else if (zombieSize.equals("small") && !hasShield){
			this.attackTicksLeft = 25;
			this.setCount(25);
			this.getWorld().sendEntityStatus(this, (byte) 106);
			if (damaged instanceof GeneralPvZombieEntity generalPvZombieEntity){
				generalPvZombieEntity.swallowed = true;
			}
		}
		else if (hasShield) {
			this.attackTicksLeft = 175;
			this.setCount(175);
			this.getWorld().sendEntityStatus(this, (byte) 105);
			if (damaged instanceof GeneralPvZombieEntity generalPvZombieEntity){
				generalPvZombieEntity.swallowed = true;
			}
		}
		else {
			this.attackTicksLeft = 175;
			this.setCount(175);
			this.getWorld().sendEntityStatus(this, (byte) 104);
			if (damaged instanceof GeneralPvZombieEntity generalPvZombieEntity){
				generalPvZombieEntity.swallowed = true;
			}
		}
		boolean bl = damaged.damage(getDamageSources().mobAttack(this), damage);
		if (bl) {
			this.applyDamageEffects(this, target);
		}
		SoundEvent sound;
		sound = switch (zombieMaterial) {
			case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
			case "plastic" -> PvZSounds.CONEHITEVENT;
			case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
			default -> PvZSounds.PEAHITEVENT;
		};
		target.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
		this.setGoop(HasGoop.TRUE);
		this.setTarget(null);
	}


	/** //~*~//~POSITION~//~*~// **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/** //~*~//~TICKING~//~*~// **/

	public void tick() {
		super.tick();
		if (!this.getWorld().isClient()) {
			this.FireBeamGoal();
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.CHESTER_SEED_PACKET);
				}
				this.discard();
			}
		}
		this.targetZombies(this.getPos(), 2, false, true, true);
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.getTypeCount() > 0) {
			--this.attackTicksLeft;
			this.reduceCount();
		}

		if (this.getTypeCount() <= 0 && !this.isFiring){
			this.eatingShield = false;
			this.notEating = false;
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)){
			dropItem(ModItems.CHESTER_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.CHESTER_SEED_PACKET.getDefaultStack();
	}


	/** //~*~//~ATTRIBUTES~//~*~// **/

	public static DefaultAttributeContainer.Builder createChesterAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 999.0D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.60F;
	}

	private float getAttackDamage(){
		return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
	}

	@Nullable
	protected SoundEvent getHurtSound(DamageSource source) {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	protected SoundEvent getDeathSound() {
		return PvZSounds.PLANTPLANTEDEVENT;
	}

	public boolean hurtByWater() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	public void onDeath(DamageSource source) {
		super.onDeath(source);
	}

	protected void pushAway(Entity entity) {
	}

	public boolean startRiding(Entity entity, boolean force) {
		return super.startRiding(entity, force);
	}

	public void stopRiding() {
		super.stopRiding();
		this.prevBodyYaw = 0.0F;
		this.bodyYaw = 0.0F;
	}


	/** //~*~//~DAMAGE HANDLER~//~*~// **/



	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}



	int beamTicks;
	int animationTicks;

	boolean charge = false;

	boolean shootSwitch = true;
	boolean shot = false;
	boolean goopshot = false;

	public void FireBeamGoal() {
		LivingEntity livingEntity = this.getTarget();
		++this.beamTicks;
		++this.animationTicks;
		if (this.getGoop() && (livingEntity != null || animationTicks < 0) && this.getTypeCount() <= 0){
			if (this.shootSwitch){
				this.beamTicks = -14;
				this.animationTicks = -32;
				this.getNavigation().stop();
				this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
				this.velocityDirty = true;
				this.shootSwitch = false;
			}
			this.getNavigation().stop();
			if (livingEntity != null) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			}
			this.getWorld().sendEntityStatus(this, (byte) 113);
			if (this.beamTicks >= 0 && this.animationTicks <= -10) {
				this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 2F, 1);
				if (!this.isInsideWaterOrBubbleColumn()) {
					for(float i = 3; i < 7; ++i) {
						RandomGenerator randomGenerator = this.random;
						double xr = (double) MathHelper.nextBetween(randomGenerator, 10F, 20F);
						float variance = MathHelper.nextBetween(randomGenerator, -0.2F, 0.2F);
						Vec3d vec3d2 = new Vec3d((double) xr, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						CheeseProjEntity proj = new CheeseProjEntity(PvZEntity.CHEESEPROJ, this.getWorld());
						float h = MathHelper.sqrt(MathHelper.sqrt(100)) * 0.5F;
						proj.setVelocity(vec3d2.x, -3.9200000762939453 + 28 / (h * 2.2), vec3d2.z, (i / 10) + variance, 0F);
						proj.updatePosition(this.getX(), this.getY() + 0.75D, this.getZ());
						proj.setOwner(this);
						this.getWorld().spawnEntity(proj);
						goopshot = true;
					}
					this.beamTicks = -14;
					this.getWorld().sendEntityStatus(this, (byte) 113);
					shot = true;
				}
			} else if (this.animationTicks >= 0) {
				this.shootSwitch = true;
				this.getWorld().sendEntityStatus(this, (byte) 112);
				this.beamTicks = -14;
				this.animationTicks = -32;
				this.setGoop(HasGoop.FALSE);
				shot = false;
				goopshot = false;
			}
		}
		else if (livingEntity != null && !this.getIsAsleep() && this.getTypeCount() <= 0 && livingEntity.squaredDistanceTo(this) <= 9) {
			if (this.shootSwitch){
				this.charge = false;
				this.beamTicks = -5;
				this.animationTicks = -20;
				this.getNavigation().stop();
				this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
				this.velocityDirty = true;
				this.shootSwitch = false;
				this.playSound(PvZSounds.CHOMPERBITEVENT, 1.0F, 1.0F);
			}
			this.getNavigation().stop();
			if (livingEntity != null && livingEntity.isAlive()) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
				if (livingEntity instanceof GraveEntity ||
						IS_MACHINE.get(livingEntity.getType()).orElse(false).equals(true) ||
						ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("gargantuar") ||
						ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("big") ||
						ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("small")){
					this.getWorld().sendEntityStatus(this, (byte) 106);
				}
			}
			this.getWorld().sendEntityStatus(this, (byte) 111);
			this.isFiring = true;
			if (this.animationTicks >= 0) {
				this.shootSwitch = true;
				this.getWorld().sendEntityStatus(this, (byte) 110);
				this.isFiring = false;
				this.beamTicks = -5;
				this.animationTicks = -20;
				if (shot) {
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
				shot = false;
			}
			if (this.beamTicks >= 0) {
				if (!this.isInsideWaterOrBubbleColumn()) {
					if (livingEntity != null && livingEntity.isAlive()) {
						this.smack(livingEntity);
					}
					this.beamTicks = -25;
					shot = true;
				}
			}
		}
		else if (animationTicks >= 0){
			this.shootSwitch = true;
			this.getWorld().sendEntityStatus(this, (byte) 110);
			this.getWorld().sendEntityStatus(this, (byte) 112);
			this.isFiring = false;
			if (this.getTarget() != null){
				this.attack(this.getTarget(), 0);
			}
			if (shot) {
				this.getWorld().sendEntityStatus(this, (byte) 121);
			}
			if (goopshot){
				this.setGoop(HasGoop.FALSE);
			}
			shot = false;
			goopshot = false;
		}
	}
}
