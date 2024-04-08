package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ChomperVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
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
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class ChomperEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ChomperEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private int attackTicksLeft;
    public boolean notEating;
    public boolean eatingShield;
	public boolean isFiring;
	private String controllerName = "chompcontroller";

    public ChomperEntity(EntityType<? extends ChomperEntity> entityType, World world) {
        super(entityType, world);
		this.attackTicksLeft = 0;

		this.targetStrength = true;
		this.isBurst = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(CHEWTIME, 0);
	}
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		//Variant//
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		this.dataTracker.set(CHEWTIME, tag.getInt("Count"));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
		tag.putInt("Variant", this.getTypeVariant());
		tag.putInt("Count", this.getTypeCount());
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

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ChomperVariants getVariant() {
		return ChomperVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(ChomperVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}

	//Counter

	private static final TrackedData<Integer> CHEWTIME =
			DataTracker.registerData(ChomperEntity.class, TrackedDataHandlerRegistry.INTEGER);

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


	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
        int i = this.getTypeCount();
		if (this.notEating) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("chomper.chomp2"));
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
				zombieSize.equals("big") ||
				target instanceof ZombieObstacleEntity) && !hasShield){
			damage = 32;
			this.attackTicksLeft = 25;
			this.setCount(25);
			this.getWorld().sendEntityStatus(this, (byte) 106);
			if (damaged instanceof GeneralPvZombieEntity generalPvZombieEntity){
				generalPvZombieEntity.swallowed = false;
			}
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
			this.attackTicksLeft = 250;
			this.setCount(250);
			this.getWorld().sendEntityStatus(this, (byte) 105);
			if (damaged instanceof GeneralPvZombieEntity generalPvZombieEntity){
				generalPvZombieEntity.swallowed = true;
			}
		}
		else {
			this.attackTicksLeft = 250;
			this.setCount(250);
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
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.CHOMPER_SEED_PACKET);
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
			dropItem(ModItems.CHOMPER_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		if (!this.getVariant().equals(ChomperVariants.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setVariant(ChomperVariants.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ChomperVariants.ENBY) &&
				(itemStack.isOf(Items.BLACK_DYE) || itemStack.isOf(Items.YELLOW_DYE) || itemStack.isOf(Items.PURPLE_DYE))) {
			this.setVariant(ChomperVariants.ENBY);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ChomperVariants.DEMIGIRL) &&
				(itemStack.isOf(Items.GRAY_DYE) || itemStack.isOf(Items.LIGHT_GRAY_DYE) || itemStack.isOf(Items.PINK_DYE))) {
			this.setVariant(ChomperVariants.DEMIGIRL);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ChomperVariants.PIRANHAPLANT) &&
				(itemStack.isOf(Items.FIRE_CHARGE) || itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.HOPPER))) {
			this.setVariant(ChomperVariants.PIRANHAPLANT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.CHOMPER_SEED_PACKET.getDefaultStack();
	}


	/** //~*~//~ATTRIBUTES~//~*~// **/

	public static DefaultAttributeContainer.Builder createChomperAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 36.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3D)
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

	public void FireBeamGoal() {
		LivingEntity livingEntity = this.getTarget();
		++this.beamTicks;
		++this.animationTicks;
		if ((livingEntity != null || animationTicks < 0) && !this.getIsAsleep() && this.getTypeCount() <= 0) {
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
			if (livingEntity != null) {
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
			this.isFiring = false;
			if (this.getTarget() != null){
				this.attack(this.getTarget(), 0);
			}
			if (shot) {
				this.getWorld().sendEntityStatus(this, (byte) 121);
			}
			shot = false;
		}
	}
}
