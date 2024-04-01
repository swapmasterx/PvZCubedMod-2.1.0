package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.superchomper;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.GraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieVehicleEntity;
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
import net.minecraft.entity.mob.Monster;
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
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class SuperChomperEntity extends PlantEntity implements GeoAnimatable, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    private int attackTicksLeft;
    public boolean notEating;
    public boolean eatingShield;
	public boolean isFiring;
	private String controllerName = "chompcontroller";

    public SuperChomperEntity(EntityType<? extends SuperChomperEntity> entityType, World world) {
        super(entityType, world);
		this.attackTicksLeft = 0;

		this.targetStrength = true;
		this.isBurst = true;
		this.targetNotObstacle = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(CHEWTIME, 0);
	}
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		//Variant//
		this.dataTracker.set(CHEWTIME, tag.getInt("Count"));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
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

	//Counter

	private static final TrackedData<Integer> CHEWTIME =
			DataTracker.registerData(SuperChomperEntity.class, TrackedDataHandlerRegistry.INTEGER);

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
		if (this.isFiring){
			event.getController().setAnimation(RawAnimation.begin().thenPlay("superchomper.chomp"));
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

	private int chomperAudioDelay = -1;

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
					this.dropItem(ModItems.SUPERCHOMPER_SEED_PACKET);
				}
				this.discard();
			}
		}
		this.targetZombies(this.getPos(), 2, true, true, true);
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
			dropItem(ModItems.SUPERCHOMPER_SEED_PACKET);
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
		return ModItems.SUPERCHOMPER_SEED_PACKET.getDefaultStack();
	}


	/** //~*~//~ATTRIBUTES~//~*~// **/

	public static DefaultAttributeContainer.Builder createSuperChomperAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 64.0D)
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


	List<LivingEntity> checkList = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().shrink(0.5, 0, 0));

	private void raycastExplode(float boxOffset) {
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

			boolean bl = true;
			for (Entity entity : livingEntity.getPassengerList()){
				if (entity instanceof ZombieObstacleEntity){
					bl = false;
					break;
				}
				if (entity instanceof ZombieVehicleEntity){
					bl = false;
					break;
				}
			}

			if (((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity2 && checkList.contains(generalPvZombieEntity2.getOwner())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && checkList != null && !checkList.contains(livingEntity)) &&
					!(livingEntity instanceof ZombieObstacleEntity) &&
					!(livingEntity instanceof ZombieVehicleEntity) && bl) {
				ZombiePropEntity zombiePropEntity2 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity2 = zpe;
					}
				}

				if (livingEntity.getVehicle() instanceof ZombieObstacleEntity || livingEntity.getVehicle() instanceof ZombieVehicleEntity){
					livingEntity.dismountVehicle();
				}

				if (livingEntity.squaredDistanceTo(this) <= 2 && livingEntity.isAlive()){
					if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity){
						generalPvZombieEntity.swallowed = true;
					}
					livingEntity.damage(getDamageSources().mobAttack(this), Float.MAX_VALUE);
					if (livingEntity.hasVehicle()){
						if (livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity){
							generalPvZombieEntity.swallowed = true;
						}
						livingEntity.getVehicle().damage(getDamageSources().mobAttack(this), Float.MAX_VALUE);
					}
					if (livingEntity.hasPassengers()){
						for (Entity entity : livingEntity.getPassengerList()){
							if (entity instanceof GeneralPvZombieEntity generalPvZombieEntity){
								generalPvZombieEntity.swallowed = true;
							}
							entity.damage(getDamageSources().mobAttack(this), Float.MAX_VALUE);
						}
					}
					ateZombie = true;
					this.playSound(SoundEvents.ENTITY_PLAYER_BURP, 0.5F, 1.0F);
				}
				else {
					Vec3d vec3d3 = new Vec3d((double) -0.5, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					livingEntity.setVelocity(vec3d3);
					livingEntity.addStatusEffect(new StatusEffectInstance(BOUNCED, 5, 1));
					livingEntity.addStatusEffect(new StatusEffectInstance(STUN, 5, 1));
				}
			}
		}
	}


	int beamTicks;
	int animationTicks;

	boolean charge = false;

	boolean shootSwitch = true;
	boolean shot = false;

	boolean ateZombie = false;

	public void FireBeamGoal() {
		LivingEntity livingEntity = this.getTarget();
		++this.beamTicks;
		++this.animationTicks;
		if ((livingEntity != null || animationTicks < 0) && !this.getIsAsleep() && this.getTypeCount() <= 0) {
			if (livingEntity != null){
				this.getLookControl().lookAt(livingEntity, 90, 90);
			}
			if (this.shootSwitch){
				this.charge = false;
				this.beamTicks = -5;
				this.animationTicks = -60;
				this.getNavigation().stop();
				this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
				this.velocityDirty = true;
				this.shootSwitch = false;
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
				if (ateZombie){
					this.attackTicksLeft = 400;
					this.setCount(400);
					this.getWorld().sendEntityStatus(this, (byte) 104);
					ateZombie = false;
				}
				this.getWorld().sendEntityStatus(this, (byte) 110);
				this.isFiring = false;
				this.beamTicks = -5;
				this.animationTicks = -60;
				if (shot) {
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
				shot = false;
			}
			if (this.beamTicks >= 0 && this.beamTicks <= 45) {
				if (!this.isInsideWaterOrBubbleColumn()) {
					for (int u = 0; u < 6; ++u) {
						this.raycastExplode(u);
					}
					shot = true;
				}
			}
		}
		else if (animationTicks >= 0){
			if (ateZombie){
				this.attackTicksLeft = 400;
				this.setCount(400);
				this.getWorld().sendEntityStatus(this, (byte) 104);
				ateZombie = false;
			}
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
