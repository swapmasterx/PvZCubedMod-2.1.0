package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.doomrose;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.rosebuds.RoseBudTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowFullTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
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

import org.jetbrains.annotations.Nullable;


import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class DoomRoseEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private int attackTicksLeft;
	private String controllerName = "doomrose";
	public boolean isFiring;

	public DoomRoseEntity(EntityType<? extends DoomRoseEntity> entityType, World world) {
		super(entityType, world);
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
		if (status == 120) {
			this.charge = true;
		} else if (status == 121) {
			this.charge = false;
		}
	}


	/**
	 * /~*~//~*GECKOLIB ANIMATION~//~*~//
	 **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers){
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}



	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		int i = this.attackTicksLeft;
		if (this.isFiring && !this.charge) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("doomrose.start"));
		}  else if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("doomrose.thump"));
		} else if (i <= 0) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("doomrose.idle"));
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

	/**
	 * //~*~//~POSITION~//~*~//
	 **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/**
	 * //~*~//~TICKING~//~*~//
	 **/

	public void tick() {
		super.tick();
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			Vec3d vec3d = Vec3d.ofCenter(this.getBlockPos()).add(0, -0.5, 0);
			List<ShadowFullTile> fullCheck = getWorld().getNonSpectatingEntities(ShadowFullTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			List<ShadowTile> tileCheck = getWorld().getNonSpectatingEntities(ShadowTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			if (fullCheck.isEmpty() && tileCheck.isEmpty()) {
				if (this.getWorld().getMoonSize() < 0.1 && this.getWorld().isSkyVisible(this.getBlockPos())) {
					if (serverWorld.isNight()) {
						this.setShadowPowered(Shadow.TRUE);
					}
				} else {
					this.setShadowPowered(Shadow.FALSE);
				}
				if (this.getWorld().getMoonSize() > 0.9 && this.getWorld().isSkyVisible(this.getBlockPos())) {
					if (serverWorld.isNight()) {
						this.setMoonPowered(Moon.TRUE);
					}
				} else {
					this.setMoonPowered(Moon.FALSE);
				}
			}
			if (!fullCheck.isEmpty()) {
				this.setMoonPowered(Moon.TRUE);
			}
			if (!tileCheck.isEmpty()) {
				this.setShadowPowered(Shadow.TRUE);
			}
		}
		this.targetZombies(this.getPos(), 3, false, false, false);
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.DOOMROSE_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (!this.getWorld().isClient()) {
			this.FireBeamGoal();
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.attackTicksLeft > 0) {
			--this.attackTicksLeft;
		}
	}


	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.DOOMROSE_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
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
		return ModItems.DOOMROSE_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createDoomRoseAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 28.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 22.0D);
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

	private float getAttackDamage() {
		return (float) this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
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


	/**
	 * //~*~//~DAMAGE HANDLER~//~*~//
	 **/



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
		if ((livingEntity != null || animationTicks < 0) && !this.getIsAsleep()) {
			if (this.shootSwitch){
				this.charge = false;
				this.beamTicks = -10;
				this.animationTicks = -15;
				this.getNavigation().stop();
				this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
				this.velocityDirty = true;
				this.shootSwitch = false;
			}
			this.getNavigation().stop();
			if (livingEntity != null) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			}
			this.getWorld().sendEntityStatus(this, (byte) 111);
			if (!charge){
				if (this.animationTicks >= 0) {
					charge = true;
					this.beamTicks = -15;
					this.animationTicks = -40;
					this.getWorld().sendEntityStatus(this, (byte) 120);
				}
			}
			else if (animationTicks >= 0 && this.getTarget() == null){
				this.shootSwitch = true;
				this.getWorld().sendEntityStatus(this, (byte) 110);
				charge = false;
				this.getWorld().sendEntityStatus(this, (byte) 121);
				shot = false;
			}
			else if (animationTicks >= 0) {
				this.beamTicks = -15;
				this.animationTicks = -40;
				shot = false;
			}
			if (this.charge && this.animationTicks == -40 && this.getTarget() != null) {
				if (this.getWorld() instanceof ServerWorld serverWorld) {
					RoseBudTile tile = (RoseBudTile) PvZEntity.ROSEBUDS.create(getWorld());
					tile.refreshPositionAndAngles(this.getTarget().getBlockPos().getX(), this.getTarget().getBlockPos().getY(), this.getTarget().getBlockPos().getZ(), 0, 0);
					tile.initialize(serverWorld, getWorld().getLocalDifficulty(this.getTarget().getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
					tile.setPersistent();
					tile.setHeadYaw(0);
					tile.setTarget(this.getTarget());
					tile.damageMultiplier = damageMultiplier;
					if (this.getMoonPowered()){
						tile.setMoonPowered(TileEntity.Moon.TRUE);
					}
					if (this.getShadowPowered()){
						tile.setShadowPowered(TileEntity.Shadow.TRUE);
					}
					serverWorld.spawnEntityAndPassengers(tile);
					if (this.getShadowPowered()){
						Vec3d vec3d2 = new Vec3d((double) -1, 0.0, 0).rotateY(-this.getTarget().getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						RoseBudTile tile2 = (RoseBudTile) PvZEntity.ROSEBUDS.create(getWorld());
						tile2.refreshPositionAndAngles(this.getTarget().getBlockPos().getX() + vec3d2.x, this.getTarget().getBlockPos().getY(), this.getTarget().getBlockPos().getZ() + vec3d2.z, 0, 0);
						tile2.initialize(serverWorld, getWorld().getLocalDifficulty(this.getTarget().getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
						tile2.setPersistent();
						tile2.setHeadYaw(0);
						tile2.setShadowPowered(TileEntity.Shadow.TRUE);
						tile2.damageMultiplier = damageMultiplier;
						serverWorld.spawnEntityAndPassengers(tile2);
					}
				}
			}
		}
		else if (animationTicks >= 0 && this.getTarget() == null){
			this.shootSwitch = true;
			this.getWorld().sendEntityStatus(this, (byte) 110);
			charge = false;
			this.getWorld().sendEntityStatus(this, (byte) 121);
			shot = false;
		}
		else if (animationTicks >= 0) {
			this.shootSwitch = true;
			this.getWorld().sendEntityStatus(this, (byte) 110);
			shot = false;
		}
	}
}
