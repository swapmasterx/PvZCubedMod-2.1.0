package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.vampireflower;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
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
import net.minecraft.entity.effect.StatusEffectInstance;
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

public class VampireFlowerEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(VampireFlowerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private int attackTicksLeft;
    public boolean notEating;
    public boolean eatingShield;
	public boolean isFiring;
	public boolean holding;
	private String controllerName = "chompcontroller";

    public VampireFlowerEntity(EntityType<? extends VampireFlowerEntity> entityType, World world) {
        super(entityType, world);
		this.attackTicksLeft = 0;

		this.targetStrength = true;
		this.isBurst = true;
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
		if (status == 107) {
			this.holding = true;
		} else if (status == 108) {
			this.holding = false;
		}
	}

	LivingEntity heldEntity = null;


	/** /~*~//~*VARIANTS~//~*~// **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
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
		if (this.notEating) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("sunflower.bite2"));
		}
		else if (this.isFiring){
			event.getController().setAnimation(RawAnimation.begin().thenPlay("sunflower.bite"));
		}
		else if (holding) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("sunflower.sucking"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("sunflower.idle"));
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
		Entity damaged = target;
		for (Entity entity1 : target.getPassengerList()) {
			if (entity1 instanceof ZombieShieldEntity || (entity1 instanceof ZombiePropEntity zombiePropEntity && zombiePropEntity.isCovered())) {
				damaged = entity1;
			}
		}
		String zombieSize = PvZCubed.ZOMBIE_SIZE.get(damaged.getType()).orElse("medium");
		String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(damaged.getType()).orElse("flesh");
		float damage = 6;
		if ((damaged instanceof GraveEntity ||
				IS_MACHINE.get(damaged.getType()).orElse(false).equals(true) ||
				zombieSize.equals("gargantuar") ||
				zombieSize.equals("big") ||
				damaged instanceof ZombiePropEntity)){
			this.attackTicksLeft = 25;
			this.getWorld().sendEntityStatus(this, (byte) 106);
		}
		else {
			this.attackTicksLeft = 250;
			this.getWorld().sendEntityStatus(this, (byte) 104);
			if (damaged.isAlive() && !(damaged instanceof ZombiePropEntity)){
				this.heldEntity = (LivingEntity) damaged;
				((LivingEntity) damaged).addStatusEffect(new StatusEffectInstance(STUN, 100 , 1));
			}
		}
		boolean bl = damaged.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), damage);
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
					this.dropItem(ModItems.VAMPIREFLOWER_SEED_PACKET);
				}
				this.discard();
			}
		}
		this.targetZombies(this.getPos(), 2, false, false, true);
	}

	int damageTicks = 20;

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (!this.getWorld().isClient()) {
			if (heldEntity != null && heldEntity.isAlive()) {
				this.getWorld().sendEntityStatus(this, (byte) 107);
				holding = true;
				if (--damageTicks <= 0) {
					heldEntity.damage(getDamageSources().mobAttack(this), 0);
					heldEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), 12);
					heldEntity.addStatusEffect(new StatusEffectInstance(STUN, 100 , 1));
					heldEntity.dropItem(ModItems.SMALLSUN, 0);
					damageTicks = 20;
					heldEntity.playSound(PvZSounds.SUNDROPEVENT, 1, 1);
					heldEntity.playSound(PvZSounds.PEAHITEVENT, 1, 1);
				}
			} else if (heldEntity == null || !heldEntity.isAlive()) {
				this.getWorld().sendEntityStatus(this, (byte) 108);
				holding = false;
				heldEntity = null;
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)){
			dropItem(ModItems.VAMPIREFLOWER_SEED_PACKET);
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
		return ModItems.VAMPIREFLOWER_SEED_PACKET.getDefaultStack();
	}


	/** //~*~//~ATTRIBUTES~//~*~// **/

	public static DefaultAttributeContainer.Builder createVampireFlowerAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
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
		if ((livingEntity != null || animationTicks < 0) && !this.getIsAsleep() && !this.holding) {
			if (this.shootSwitch){
				this.charge = false;
				this.beamTicks = -19;
				this.animationTicks = -30;
				this.getNavigation().stop();
				this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
				this.velocityDirty = true;
				this.shootSwitch = false;
			}
			this.getNavigation().stop();
			if (livingEntity != null) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
				if (livingEntity instanceof GraveEntity || livingEntity instanceof ZombiePropEntity ||
						IS_MACHINE.get(livingEntity.getType()).orElse(false).equals(true) ||
						ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("gargantuar") ||
						ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium").equals("big")){
					this.getWorld().sendEntityStatus(this, (byte) 106);
					this.notEating = true;
				}
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombieShieldEntity || (entity1 instanceof ZombiePropEntity zombiePropEntity && zombiePropEntity.isCovered())) {
						this.getWorld().sendEntityStatus(this, (byte) 106);
						this.notEating = true;
					}
				}
			}
			this.getWorld().sendEntityStatus(this, (byte) 111);
			this.isFiring = true;
			if (this.animationTicks >= 0) {
				this.getWorld().sendEntityStatus(this, (byte) 104);
				this.getWorld().sendEntityStatus(this, (byte) 110);
				this.notEating = false;
				this.isFiring = false;
				this.beamTicks = -19;
				this.animationTicks = -30;
				if (shot) {
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
				shot = false;
			}
			if (this.beamTicks >= 0) {
				if (!this.isInsideWaterOrBubbleColumn()) {
					if (livingEntity != null && livingEntity.isAlive()) {
						this.smack(livingEntity);
						if (!this.notEating){
							this.animationTicks = 0;
						}
					}
					this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 1.0F, 1.0F);
					this.beamTicks = -25;
					shot = true;
				}
			}
		}
		else if (animationTicks >= 0){
			this.shootSwitch = true;
			this.getWorld().sendEntityStatus(this, (byte) 104);
			this.getWorld().sendEntityStatus(this, (byte) 110);
			this.notEating = false;
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
