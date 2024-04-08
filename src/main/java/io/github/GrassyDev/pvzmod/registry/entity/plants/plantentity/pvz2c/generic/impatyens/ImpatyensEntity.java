package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.impatyens;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dye.ShootingDyeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.DyeVariants;
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
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
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

public class ImpatyensEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private String controllerName = "empatyenscontroller";

	private boolean isFiring;

	public ImpatyensEntity(EntityType<? extends ImpatyensEntity> entityType, World world) {
		super(entityType, world);

	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
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
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(ImpatyensEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public DyeVariants getVariant() {
		return DyeVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(DyeVariants variant) {
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
		if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("impatyens.shoot"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("impatyens.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}


	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double)MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		if (!this.getWorld().isClient()) {
			Vec3d front = new Vec3d((double) 1, 0.0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
			List<PlantEntity> frontList = this.getWorld().getNonSpectatingEntities(PlantEntity.class, this.getBoundingBox().offset(front).shrink(0.5, 0.5, 0.5));
			double dist = 0;
			LivingEntity plant = null;
			for (PlantEntity plantEntity : frontList) {
				if (!plantEntity.hasPassengers()) {
					if (this.squaredDistanceTo(plantEntity) <= dist){
						plant = plantEntity;
						dist = plantEntity.squaredDistanceTo(this);
					}
					else if (dist == 0){
						plant = plantEntity;
						dist = plantEntity.squaredDistanceTo(this);
					}
				}
			}
			if (plant != null) {
				String plantType = PvZCubed.PLANT_TYPE.get(plant.getType()).orElse("appease");
				DyeVariants dyeVariants = switch (plantType) {
					case "contain" -> DyeVariants.CONTAIN;
					case "spear" -> DyeVariants.SPEAR;
					case "conceal" -> DyeVariants.CONCEAL;
					case "enforce" -> DyeVariants.ENFORCE;
					case "enchant" -> DyeVariants.ENCHANT;
					case "ailment" -> DyeVariants.AILMENT;
					case "bombard" -> DyeVariants.BOMBARD;
					case "reinforce" -> DyeVariants.REINFORCE;
					case "enlighten" -> DyeVariants.ENLIGHTEN;
					case "winter" -> DyeVariants.WINTER;
					case "pepper" -> DyeVariants.PEPPER;
					case "filament" -> DyeVariants.FILAMENT;
					case "arma" -> DyeVariants.ARMA;
					default -> DyeVariants.APPEASE;
				};
				this.setVariant(dyeVariants);
			}
			if (frontList.isEmpty()){
				this.setVariant(DyeVariants.CONTAIN);
			}
		}
		if (this.getVariant().equals(DyeVariants.ARMA)) {
			this.targetZombies(this.getPos(), 5, false, false, false);
		}
		else if (this.getVariant().equals(DyeVariants.ENLIGHTEN)) {
			this.targetZombies(this.getPos(), 5, false, false, true);
		}
		else {
			this.targetZombies(this.getPos(), 5, true, true, false);
		}
		if (this.getVariant().equals(DyeVariants.ENFORCE) && this.getTarget() != null && this.squaredDistanceTo(this.getTarget()) > 49){
			this.setTarget(null);
		}
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.IMPATYENS_SEED_PACKET);
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
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.IMPATYENS_SEED_PACKET);
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
		return ModItems.IMPATYENS_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createImpatyensAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 18.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D);
	}

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return true;
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.5F;
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


	/** /~*~//~*DAMAGE HANDLER*~//~*~/ **/



	public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
		if (fallDistance > 0F) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT, 0.4F, 1.0F);
			this.discard();
		}
		this.playBlockFallSound();
		return true;
	}


	/** /~*~//~*GOALS*~//~*~/ **/

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
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
				shot = false;
			}
			if (this.beamTicks >= 0 && !this.getVariant().equals(DyeVariants.ARMA)) {
				double time = 1;
				Vec3d noZombie = new Vec3d((double) +1, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				Vec3d noZombie2 = new Vec3d(this.getX() + noZombie.x, this.getY() + noZombie.y, this.getZ() + noZombie.z);
				Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : noZombie2;
				Vec3d predictedPos = (livingEntity != null) ? targetPos.add(livingEntity.getVelocity().multiply(time)) : noZombie2;
				double d = this.squaredDistanceTo(predictedPos);
				float df = (float)d;
				double e = predictedPos.getX() - this.getX();
				double f = 0;
				if (livingEntity != null && livingEntity.isInsideWaterOrBubbleColumn()){
					f = livingEntity.getY() - this.getY() + 0.3595;
				}
				else if (livingEntity != null) {
					f = livingEntity.getY() - this.getY();
				}
				double g = predictedPos.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				ShootingDyeEntity proj = new ShootingDyeEntity(PvZEntity.DYEPROJ, this.getWorld());
				proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.5F, 0F);
				proj.updatePosition(this.getX(), this.getY() + 0.5D, this.getZ());
				proj.setOwner(this);
				proj.setVariant(this.getVariant());
				proj.damageMultiplier = damageMultiplier;
				this.beamTicks = -30;
				this.playSound(PvZSounds.PEASHOOTEVENT, 1F, 1);
				this.getWorld().spawnEntity(proj);
			}
			else if (this.beamTicks >= 0) {
				if (!this.isInsideWaterOrBubbleColumn()) {
					ShootingDyeEntity proj = new ShootingDyeEntity(PvZEntity.DYEPROJ, this.getWorld());
					double time = (livingEntity != null) ? ((this.squaredDistanceTo(livingEntity) > 36) ? 50 : 1) : 1;
					Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : this.getPos();
					double predictedPosX = (livingEntity != null) ? targetPos.getX() + (livingEntity.getVelocity().x * time) : this.getX();
					double predictedPosZ = (livingEntity != null) ? targetPos.getZ() + (livingEntity.getVelocity().z * time) : this.getZ();
					Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
					float dist = (this.squaredDistanceTo(predictedPos) >= 729) ? 1.1f : 1f;
					double d = this.squaredDistanceTo(predictedPos);
					float df = (float)d;
					float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
					Vec3d projPos = new Vec3d(this.getX(), this.getY() + 1.75D, this.getZ());
					Vec3d vel = this.solve_ballistic_arc_lateral(projPos, 1F, predictedPos, 5);
					proj.setVelocity(vel.getX(), -3.9200000762939453 + 28 / (h * 2.2), vel.getZ(),dist, 0F);
					proj.updatePosition(projPos.getX(), projPos.getY(), projPos.getZ());
					proj.setOwner(this);
					proj.damageMultiplier = damageMultiplier;
					if (this.getTarget() != null){
						proj.getTarget(this.getTarget());
					}
					proj.setVariant(this.getVariant());
					proj.canHitFlying = true;
					this.beamTicks = -30;
					this.getWorld().sendEntityStatus(this, (byte) 111);
					this.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
					this.getWorld().spawnEntity(proj);
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
