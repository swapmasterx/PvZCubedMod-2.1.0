package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.pea.ShootingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.PeapodCountVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.PeapodVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShootingPeaVariants;
import io.github.GrassyDev.pvzmod.items.seedpackets.PeaPodSeeds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.UUID;

import static io.github.GrassyDev.pvzmod.PvZCubed.MOD_ID;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PeapodEntity extends PlantEntity implements RangedAttackMob, GeoEntity {
	private String controllerName = "peacontroller";



	public boolean isFiring;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	public static final	UUID MAX_HEALTH_UUID = UUID.nameUUIDFromBytes(MOD_ID.getBytes(StandardCharsets.UTF_8));

	public PeapodEntity(EntityType<? extends PeapodEntity> entityType, World world) {
		super(entityType, world);

	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, 0);
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putInt("Variant", this.getTypeVariant());
		tag.putInt("Count", this.getTypeCount());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getInt("Count"));
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
	}

	static {
	}

	@Override
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

	//Pea Pod Counter

	private static final TrackedData<Integer> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(PeapodEntity.class, TrackedDataHandlerRegistry.INTEGER);
	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(PeapodEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		//Set Count
		PeapodCountVariants count = PeapodCountVariants.ONE;
		setCount(count);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeCount() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public PeapodCountVariants getCount() {
		return PeapodCountVariants.byId(this.getTypeCount() & 255);
	}

	private void setCount(PeapodCountVariants count) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count.getId() & 255);
	}

	private void addCount(){
		PeapodCountVariants count = this.getCount();
		this.dataTracker.set(DATA_ID_TYPE_COUNT, count.getId() + 1 & 255);
	}


	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public PeapodVariants getVariant() {
		return PeapodVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(PeapodVariants variant) {
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
		if (this.getCount().equals(PeapodCountVariants.ONE)) {
			if (this.isFiring) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("peapod.shoot"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("peapod.idle"));
			}
		}
		else if (this.getCount().equals(PeapodCountVariants.TWO)) {
			if (this.isFiring) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("peapod.shoot2"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("peapod.idle2"));
			}
		}
		else if (this.getCount().equals(PeapodCountVariants.THREE)) {
			if (this.isFiring) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("peapod.shoot3"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("peapod.idle3"));
			}
		}
		else if (this.getCount().equals(PeapodCountVariants.FOUR)) {
			if (this.isFiring) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("peapod.shoot4"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("peapod.idle4"));
			}
		}
		else if (this.getCount().equals(PeapodCountVariants.FIVE)) {
			if (this.isFiring) {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("peapod.shoot5"));
			} else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("peapod.idle5"));
			}
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("peapod.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new PeapodEntity.FireBeamGoal(this));
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
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.PEAPOD_SEED_PACKET);
				}
				this.discard();
			}
		}
		this.targetZombies(this.getPos(), 7, false, this.getCount().equals(PeapodCountVariants.FIVE), false);
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
			dropItem(ModItems.PEAPOD_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.PEAPOD_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item) && !this.getCount().equals(PeapodCountVariants.FIVE)) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT);
			this.addCount();
			EntityAttributeInstance maxHealthAttribute = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
			double health = this.getMaxHealth() - 5;
			maxHealthAttribute.removeModifier(MAX_HEALTH_UUID);
			maxHealthAttribute.addPersistentModifier(createHealthModifier(health + 5));
			heal(5);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.PEAPOD_SEED_PACKET, PeaPodSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (!this.getVariant().equals(PeapodVariants.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setVariant(PeapodVariants.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(PeapodVariants.PLURAL) &&
				(itemStack.isOf(Items.CYAN_DYE) || itemStack.isOf(Items.BLUE_DYE) || itemStack.isOf(Items.PURPLE_DYE) || itemStack.isOf(Items.BLACK_DYE))) {
			this.setVariant(PeapodVariants.PLURAL);
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
		return ModItems.PEAPOD_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	//Credits to Spice of Fabric for the Health Attribute modifier: https://github.com/Siphalor/spiceoffabric

	public static EntityAttributeModifier createHealthModifier(double amount) {
		return new EntityAttributeModifier(
				MAX_HEALTH_UUID,
				MOD_ID,
				amount,
				EntityAttributeModifier.Operation.ADDITION
		);
	}

	public static DefaultAttributeContainer.Builder createPeapodAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 5)
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
		return 0.60F;
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

	static class FireBeamGoal extends Goal {
		private final PeapodEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(PeapodEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.setTarget((LivingEntity) null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -9) {
					double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
					Vec3d targetPos = livingEntity.getPos();
					double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						// Bottom Pea
						ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.plantEntity.getWorld());
						double d = this.plantEntity.squaredDistanceTo(predictedPos);
						float df = (float)d;
						double e = predictedPos.getX() - this.plantEntity.getX();
						double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
						double g = predictedPos.getZ() - this.plantEntity.getZ();
						float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
						proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.3F, 0F);
						proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.33D, this.plantEntity.getZ());
						proj.setOwner(this.plantEntity);
						proj.lowProf = true;
						proj.damageMultiplier = plantEntity.damageMultiplier;
						if (livingEntity != null && livingEntity.isAlive()) {
							this.beamTicks = -16;
							this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
							this.plantEntity.getWorld().spawnEntity(proj);
						}
						if (plantEntity.getCount().getId() >= 1) {
							// Right Pea
							ShootingPeaEntity proj3 = new ShootingPeaEntity(PvZEntity.PEA, this.plantEntity.getWorld());
							Vec3d vec3d3 = new Vec3d((double) 0.0, 0.0, 0.8).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
							double d3 = this.plantEntity.squaredDistanceTo(predictedPos);
							float df3 = (float) d3;
							double e3 = predictedPos.getX() - this.plantEntity.getX();
							double f3 = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
							double g3 = predictedPos.getZ() - this.plantEntity.getZ();
							float h3 = MathHelper.sqrt(MathHelper.sqrt(df3)) * 0.5F;
							proj3.setVelocity(e3 * (double) h3, f3 * (double) h3, g3 * (double) h3, 0.4F, 0F);
							proj3.updatePosition(this.plantEntity.getX() + vec3d3.x, this.plantEntity.getY() + 0.3, this.plantEntity.getZ() + vec3d3.z);
							proj3.setOwner(this.plantEntity);
							proj3.lowProf = true;
							proj3.damageMultiplier = plantEntity.damageMultiplier;
							if (livingEntity != null && livingEntity.isAlive()) {
								if (this.plantEntity.getVariant().equals(PeapodVariants.PLURAL)){
									proj3.setVariant(ShootingPeaVariants.BLACK);
								}
								this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
								this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
								this.plantEntity.getWorld().spawnEntity(proj3);
							}
						}
						if (plantEntity.getCount().getId() >= 2) {
							// Left Pea
							ShootingPeaEntity proj2 = new ShootingPeaEntity(PvZEntity.PEA, this.plantEntity.getWorld());
							Vec3d vec3d2 = new Vec3d((double) 0.0, 0.0, -0.8).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
							double d2 = this.plantEntity.squaredDistanceTo(predictedPos);
							float df2 = (float) d2;
							double e2 = predictedPos.getX() - this.plantEntity.getX();
							double f2 = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
							double g2 = predictedPos.getZ() - this.plantEntity.getZ();
							float h2 = MathHelper.sqrt(MathHelper.sqrt(df2)) * 0.5F;
							proj2.setVelocity(e2 * (double) h2, f2 * (double) h2, g2 * (double) h2, 0.5F, 0);
							proj2.updatePosition(this.plantEntity.getX() + vec3d2.x, this.plantEntity.getY() + 0.3, this.plantEntity.getZ() + vec3d2.z);
							proj2.lowProf = true;
							proj2.setOwner(this.plantEntity);
							proj2.damageMultiplier = plantEntity.damageMultiplier;
							if (livingEntity != null && livingEntity.isAlive()) {
								if (this.plantEntity.getVariant().equals(PeapodVariants.PLURAL)){
									proj2.setVariant(ShootingPeaVariants.PURPLE);
								}
								this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
								this.plantEntity.getWorld().spawnEntity(proj2);
							}
						}
						if (plantEntity.getCount().getId() >= 3) {
							// Middle Pea
							ShootingPeaEntity proj4 = new ShootingPeaEntity(PvZEntity.PEA, this.plantEntity.getWorld());
							double d4 = this.plantEntity.squaredDistanceTo(predictedPos);
							float df4 = (float) d4;
							double e4 = predictedPos.getX() - this.plantEntity.getX();
							double f4 = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
							double g4 = predictedPos.getZ() - this.plantEntity.getZ();
							float h4 = MathHelper.sqrt(MathHelper.sqrt(df4)) * 0.5F;
							proj4.setVelocity(e4 * (double) h4, f4 * (double) h4, g4 * (double) h4, 0.6F, 0F);
							proj4.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
							proj4.setOwner(this.plantEntity);
							proj4.damageMultiplier = plantEntity.damageMultiplier;
							if (livingEntity != null && livingEntity.isAlive()) {
								if (this.plantEntity.getVariant().equals(PeapodVariants.PLURAL)){
									proj4.setVariant(ShootingPeaVariants.BLUE);
								}
								this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
								this.plantEntity.getWorld().spawnEntity(proj4);
							}
						}
						if (plantEntity.getCount().getId() >= 4) {
							// Top Pea
							ShootingPeaEntity proj5 = new ShootingPeaEntity(PvZEntity.PEA, this.plantEntity.getWorld());
							double d5 = this.plantEntity.squaredDistanceTo(predictedPos);
							float df5 = (float) d5;
							double e5 = predictedPos.getX() - this.plantEntity.getX();
							double f5 = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
							double g5 = predictedPos.getZ() - this.plantEntity.getZ();
							float h5 = MathHelper.sqrt(MathHelper.sqrt(df5)) * 0.5F;
							proj5.setVelocity(e5 * (double) h5, f5 * (double) h5, g5 * (double) h5, 7.0F, 0F);
							proj5.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 1.25D, this.plantEntity.getZ());
							proj5.setOwner(this.plantEntity);
							proj5.canHitFlying = true;
							proj5.damageMultiplier = plantEntity.damageMultiplier;
							if (livingEntity != null && livingEntity.isAlive()) {
								if (this.plantEntity.getVariant().equals(PeapodVariants.PLURAL)){
									proj5.setVariant(ShootingPeaVariants.CYAN);
								}
								this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
								this.plantEntity.getWorld().spawnEntity(proj5);
							}
						}
					}
				}
				else if (this.animationTicks >= 0) {
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -7;
					this.animationTicks = -16;
				}
				super.tick();
			}
		}
	}
}
