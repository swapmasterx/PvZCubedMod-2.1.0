package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.locococo;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity.WallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut.TallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.peanut.PeanutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smallnut.SmallNutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.peanut.PeanutBowlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.wallnut.WallnutBowlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.peanut.PeaNutProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smallnut.SmallNutProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShootingPeaVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
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
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
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

import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class LocoCocoEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private int attackTicksLeft;
	private String controllerName = "locococo";
	public boolean isFiring;

	public LocoCocoEntity(EntityType<? extends LocoCocoEntity> entityType, World world) {
		super(entityType, world);
		this.setFireImmune(FireImmune.TRUE);
		this.nocturnal = true;
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
		if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("locococo.slap"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("locococo.idle"));
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
		this.targetZombies(this.getPos(), 3, false, true, false);
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.LOCOCOCO_SEED_PACKET);
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
			dropItem(ModItems.LOCOCOCO_SEED_PACKET);
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
		return ModItems.LOCOCOCO_SEED_PACKET.getDefaultStack();
	}


	/**
	 * //~*~//~ATTRIBUTES~//~*~//
	 **/

	public static DefaultAttributeContainer.Builder createLocoCocoAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 42.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6D)
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
		Vec3d front = new Vec3d((double) 1, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<PlantEntity> frontList = this.getWorld().getNonSpectatingEntities(PlantEntity.class, this.getBoundingBox().offset(front).shrink(0.5, 0.5, 0.5));
		double dist = 0;
		LivingEntity plant = null;
		for (PlantEntity plantEntity : frontList) {
			if (plantEntity instanceof WallnutEntity ||
					plantEntity instanceof SmallNutEntity ||
					plantEntity instanceof TallnutEntity ||
					plantEntity instanceof PeanutEntity) {
				if (!plantEntity.hasPassengers()) {
					if (this.squaredDistanceTo(plantEntity) <= dist) {
						plant = plantEntity;
						dist = plantEntity.squaredDistanceTo(this);
					} else if (dist == 0) {
						plant = plantEntity;
						dist = plantEntity.squaredDistanceTo(this);
					}
				}
			}
		}
		Vec3d frontBox = new Vec3d((double) 5, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
		List<GeneralPvZombieEntity> zombieList = this.getWorld().getNonSpectatingEntities(GeneralPvZombieEntity.class, this.getBoundingBox().offset(frontBox).expand(5));
		zombieList.removeIf(Entity::hasVehicle);
		if ((livingEntity != null || animationTicks < 0) && !this.getIsAsleep() && plant != null &&
				!(plant instanceof WallnutEntity && zombieList.size() < 6) &&
				!(plant instanceof TallnutEntity tallnutEntity && tallnutEntity.fall)) {
			if (this.shootSwitch){
				this.charge = false;
				this.beamTicks = -15;
				this.animationTicks = -30;
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
			if (this.animationTicks >= 0) {
				this.getWorld().sendEntityStatus(this, (byte) 110);
				this.beamTicks = -15;
				this.animationTicks = -30;
				if (shot) {
					charge = false;
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
				shot = false;
			}
			if (this.beamTicks >= 0) {
				if (!this.isInsideWaterOrBubbleColumn()) {
					if (plant instanceof WallnutEntity wallnutEntity){
						WallnutBowlingEntity proj = new WallnutBowlingEntity(PvZEntity.WALLNUTBOWLING, this.getWorld());
						double time = (livingEntity != null) ? ((this.squaredDistanceTo(livingEntity) > 36) ? 50 : 1) : 1;
						Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : this.getPos();
						double predictedPosX = (livingEntity != null) ? targetPos.getX() + (livingEntity.getVelocity().x * time) : this.getX();
						double predictedPosZ = (livingEntity != null) ? targetPos.getZ() + (livingEntity.getVelocity().z * time) : this.getZ();
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
						double d = this.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.getX();
						double f = (livingEntity != null) ? ((livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.getY() + 0.3595 : livingEntity.getY() - this.getY()) : 0;
						double g = predictedPos.getZ() - this.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double)h, f * (double)h, g * (double)h, 0.33F, 0F);
						proj.updatePosition(this.getX(), this.getY() + 0.25D, this.getZ());
						proj.setOwner(this);
						if (wallnutEntity.getCrack().equals(WallnutEntity.Crack.MEDIUM)){
							proj.setVariant(ShootingPeaVariants.BLACK);
						}
						else if (wallnutEntity.getCrack().equals(WallnutEntity.Crack.HIGH)){
							proj.setVariant(ShootingPeaVariants.PURPLE);
						}
						proj.damageMultiplier = damageMultiplier;
						this.getWorld().spawnEntity(proj);
						plant.discard();
					}
					if (plant instanceof TallnutEntity tallnutEntity){
						tallnutEntity.fall = true;
						this.getWorld().sendEntityStatus(tallnutEntity, (byte) 101);
					}
					if (plant instanceof SmallNutEntity){
						SmallNutProjEntity proj = new SmallNutProjEntity(PvZEntity.SMALLNUTPROJ, this.getWorld());
						double time = (livingEntity != null) ? ((this.squaredDistanceTo(livingEntity) > 36) ? 50 : 1) : 1;
						Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : this.getPos();
						double predictedPosX = (livingEntity != null) ? targetPos.getX() + (livingEntity.getVelocity().x * time) : this.getX();
						double predictedPosZ = (livingEntity != null) ? targetPos.getZ() + (livingEntity.getVelocity().z * time) : this.getZ();
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
						float dist2 = (this.squaredDistanceTo(predictedPos) >= 729) ? 1.1f : 1f;
						double d = this.squaredDistanceTo(predictedPos);
						float df = (float)d;
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						Vec3d projPos = new Vec3d(plant.getX(), plant.getY(), plant.getZ());
						Vec3d vel = this.solve_ballistic_arc_lateral(projPos, 1F, predictedPos, 5);
						proj.setVelocity(vel.getX(), -3.9200000762939453 + 28 / (h * 2.2), vel.getZ(),dist2, 0F);
						proj.updatePosition(projPos.getX(), projPos.getY(), projPos.getZ());
						proj.setOwner(this);
						proj.damageMultiplier = damageMultiplier;
						proj.getTarget(this.getTarget());
						this.getWorld().spawnEntity(proj);
						plant.discard();
					}
					if (plant instanceof PeanutEntity peanut && peanut.getCrack().equals(PeanutEntity.Crack.HIGH)){
						PeaNutProjEntity proj = new PeaNutProjEntity(PvZEntity.PEANUTPROJ, this.getWorld());
						double time = (livingEntity != null) ? ((this.squaredDistanceTo(livingEntity) > 36) ? 50 : 1) : 1;
						Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : this.getPos();
						double predictedPosX = (livingEntity != null) ? targetPos.getX() + (livingEntity.getVelocity().x * time) : this.getX();
						double predictedPosZ = (livingEntity != null) ? targetPos.getZ() + (livingEntity.getVelocity().z * time) : this.getZ();
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
						float dist2 = (this.squaredDistanceTo(predictedPos) >= 729) ? 1.1f : 1f;
						double d = this.squaredDistanceTo(predictedPos);
						float df = (float)d;
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						Vec3d projPos = new Vec3d(plant.getX(), plant.getY(), plant.getZ());
						Vec3d vel = this.solve_ballistic_arc_lateral(projPos, 1F, predictedPos, 5);
						proj.setVelocity(vel.getX(), -3.9200000762939453 + 28 / (h * 2.2), vel.getZ(),dist2, 0F);
						proj.updatePosition(projPos.getX(), projPos.getY(), projPos.getZ());
						proj.setOwner(this);
						proj.damageMultiplier = damageMultiplier;
						proj.getTarget(this.getTarget());
						this.getWorld().spawnEntity(proj);
						plant.discard();
					}
					else if (plant instanceof PeanutEntity peanut){
						PeanutBowlingEntity proj = new PeanutBowlingEntity(PvZEntity.PEANUTBOWLING, this.getWorld());
						double time = (livingEntity != null) ? ((this.squaredDistanceTo(livingEntity) > 36) ? 50 : 1) : 1;
						Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : this.getPos();
						double predictedPosX = (livingEntity != null) ? targetPos.getX() + (livingEntity.getVelocity().x * time) : this.getX();
						double predictedPosZ = (livingEntity != null) ? targetPos.getZ() + (livingEntity.getVelocity().z * time) : this.getZ();
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
						double d = this.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.getX();
						double f = (livingEntity != null) ? ((livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.getY() + 0.3595 : livingEntity.getY() - this.getY()) : 0;
						double g = predictedPos.getZ() - this.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double)h, f * (double)h, g * (double)h, 0.33F, 0F);
						proj.updatePosition(this.getX(), this.getY() + 0.25D, this.getZ());
						proj.setOwner(this);
						if (peanut.getCrack().equals(PeanutEntity.Crack.MEDIUM)){
							proj.setVariant(ShootingPeaVariants.BLACK);
						}
						proj.damageMultiplier = damageMultiplier;
						this.getWorld().spawnEntity(proj);
						plant.discard();
					}
					this.beamTicks = -30;
					this.getWorld().sendEntityStatus(this, (byte) 111);
					this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, 1F, 1);
					shot = true;
				}
			}
		}
		else if (animationTicks >= 0){
			this.shootSwitch = true;
			this.getWorld().sendEntityStatus(this, (byte) 110);
			if (this.getTarget() != null){
				this.attack(this.getTarget(), 0);
			}
			if (shot) {
				charge = false;
				this.getWorld().sendEntityStatus(this, (byte) 121);
			}
			shot = false;
		}
	}

	public Vec3d fire_velocity;


	// Solve the firing arc with a fixed lateral speed. Vertical speed and gravity varies.
	// This enables a visually pleasing arc.
	//
	// proj_pos (Vector3): point projectile will fire from
	// lateral_speed (float): scalar speed of projectile along XZ plane
	// target_pos (Vector3): point projectile is trying to hit
	// max_height (float): height above Max(proj_pos, impact_pos) for projectile to peak at
	//
	// fire_velocity (out Vector3): firing velocity
	// gravity (out float): gravity necessary to projectile to hit precisely max_height
	//
	// return (bool): true if a valid solution was found
	public Vec3d solve_ballistic_arc_lateral(Vec3d proj_pos, float lateral_speed, Vec3d target_pos, float max_height) {

		Vec3d diff = target_pos.subtract(proj_pos);
		Vec3d diffXZ = new Vec3d(diff.x, 0f, diff.z);
		float lateralDist = (float) diffXZ.length();

		if (lateralDist == 0)
			return fire_velocity = Vec3d.ZERO;

		float time = lateralDist / lateral_speed;

		fire_velocity = diffXZ.normalize().multiply(lateral_speed);

		// System of equations. Hit max_height at t=.5*time. Hit target at t=time.
		//
		// peak = y0 + vertical_speed*halfTime + .5*gravity*halfTime^2
		// end = y0 + vertical_speed*time + .5*gravity*time^s
		// Wolfram Alpha: solve b = a + .5*v*t + .5*g*(.5*t)^2, c = a + vt + .5*g*t^2 for g, v
		float a = (float) proj_pos.y;       // initial
		float b = max_height;       // peak
		float c = (float) target_pos.y;     // final

		return fire_velocity.subtract(0, -(3*a - 4*b + c) / time, 0);
	}
}
