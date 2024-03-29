package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.magnet;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.armor.MetalHelmetProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;


import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class MagnetoShroomEntity extends PlantEntity implements IAnimatable, RangedAttackMob {

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	private String controllerName = "fumecontroller";

	private int attractTicks;
	public boolean magnetized;
	public boolean canShoot;
	public boolean isFiring;
	private int untarget;

	public MagnetoShroomEntity(EntityType<? extends MagnetoShroomEntity> entityType, World world) {
		super(entityType, world);

		this.targetHelmet = true;
		this.magnetoshroom = true;
		this.magnetOffsetY = 0.75f;
		this.magnetOffsetX = 0.4f;
		this.isBurst = true;
		this.nocturnal = true;
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 111){
			this.attractTicks = 30;
		}
		if (status == 110){
			this.attractTicks = 0;
		}
		if (status == 113) {
			this.isFiring = true;
		} else if (status == 112) {
			this.isFiring = false;
		}
		if (status == 109){
			this.magnetized = true;
		}
		if (status == 108){
			this.magnetized = false;
		}
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isFiring){
			event.getController().setAnimation(new AnimationBuilder().playOnce("magnetshroom.shoot"));
		}
		else if (this.attractTicks > 0) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("magnetshroom.pull"));
		}
		else if (this.magnetized) {
			event.getController().setAnimation(new AnimationBuilder().loop("magnetshroom.idle2"));
		}
		else if (this.getIsAsleep()) {
			event.getController().setAnimation(new AnimationBuilder().loop("magnetshroom.asleep"));
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("magnetshroom.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, GeneralPvZombieEntity.class, 13F));
		this.goalSelector.add(1, new MagnetoShroomEntity.FireBeamGoal(this));
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
		--this.attractTicks;
		++this.untarget;
		if (!this.getWorld().isClient && !this.getCofee()) {
			if ((this.getWorld().getAmbientDarkness() >= 2 ||
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
					this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS)))) {
				this.setIsAsleep(IsAsleep.FALSE);
			} else if (this.getWorld().getAmbientDarkness() < 2 &&
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
					!this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(BiomeKeys.MUSHROOM_FIELDS))) {
				this.setIsAsleep(IsAsleep.TRUE);
			}
		}
		if (this.getIsAsleep()){
			this.setTarget(null);
		}
		else {
			if (this.untarget >= 0) {
				if (this.magnetized) {
					this.magnetoshroom = false;
					this.targetHelmet = false;
					this.targetZombies(this.getPos(), 5, false, false, false);
				} else {
					this.magnetoshroom = true;
					this.targetHelmet = true;
					this.targetZombies(this.getPos(), 5, true, true, true);
				}
			}
			else {
				this.getWorld().sendEntityStatus(this, (byte) 112);
				this.setTarget(null);
			}
		}
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.MAGNETOSHROOM_SEED_PACKET);
				}
				this.discard();
			}
		}
		List<Entity> helmets = this.getWorld().getNonSpectatingEntities(Entity.class, this.getBoundingBox().stretch(0, 0, 0));
		List<Entity> helmets2 = new ArrayList<>();
		for (Entity entity : helmets){
			if (entity instanceof MetalHelmetProjEntity metalHelmetProjEntity){
				if (metalHelmetProjEntity.getOwner() == this){
					helmets2.add(entity);
					metalHelmetProjEntity.magnetized = !this.canShoot;
					metalHelmetProjEntity.keepSize = true;
					if (metalHelmetProjEntity.age >= 100){
						this.canShoot = true;
					}
				}
			}
		}
		if (helmets2.isEmpty()){
			this.magnetized = false;
			this.canShoot = false;
			this.getWorld().sendEntityStatus(this, (byte) 108);
		}
		else {
			this.magnetized = true;
			this.getWorld().sendEntityStatus(this, (byte) 109);
		}
	}

	@Override
	public void onDeath(DamageSource source) {
		List<Entity> helmets = this.getWorld().getNonSpectatingEntities(Entity.class, this.getBoundingBox().stretch(0, 0, 0));
		for (Entity entity : helmets){
			if (entity instanceof MetalHelmetProjEntity metalHelmetProjEntity){
				if (metalHelmetProjEntity.getOwner() == this){
					metalHelmetProjEntity.magnetized = false;
					metalHelmetProjEntity.discard();
				}
			}
		}
		super.onDeath(source);
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
			dropItem(ModItems.MAGNETOSHROOM_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
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
		return ModItems.MAGNETOSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createMagnetoshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0D)
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

	@Override
	public double getMountedHeightOffset() {
		return 1.35f;
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
		private final MagnetoShroomEntity plantEntity;
		private int beamTicks;
		private int animationTicks;


		public FireBeamGoal(MagnetoShroomEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue() && !this.plantEntity.getIsAsleep();
		}

		public void start() {
			this.beamTicks = -8;
			this.animationTicks = -15;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity) && this.animationTicks >= 0) || this.plantEntity.getIsAsleep()){
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				if (this.plantEntity.magnetized && this.plantEntity.canShoot && this.plantEntity.untarget >= 0){
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 113);
				}
				else if (!this.plantEntity.magnetized) {
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
				}
				++this.beamTicks;
				++this.animationTicks;
				if (this.plantEntity.magnetized && this.plantEntity.canShoot){
					if (this.beamTicks >= 0 && this.animationTicks <= -7) {
						if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
							List<Entity> helmets = this.plantEntity.world.getNonSpectatingEntities(Entity.class, this.plantEntity.getBoundingBox().stretch(0, 0, 0));
							MetalHelmetProjEntity helmetProj = null;
							MetalHelmetProjEntity helmetProj2 = null;
							MetalHelmetProjEntity helmetProj3 = null;
							for (Entity entity : helmets){
								if (entity instanceof MetalHelmetProjEntity metalHelmetProjEntity){
									if (metalHelmetProjEntity.getOwner() == this.plantEntity){
										if (helmetProj == null) {
											helmetProj = metalHelmetProjEntity;
										}
										else if (helmetProj2 == null) {
											helmetProj2 = metalHelmetProjEntity;
										}
										else if (helmetProj3 == null) {
											helmetProj3 = metalHelmetProjEntity;
										}
									}
								}
							}
							if (helmetProj != null) {
								double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
								Vec3d targetPos = livingEntity.getPos();
								double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
								double d = this.plantEntity.squaredDistanceTo(predictedPos);
								float df = (float) d;
								double e = predictedPos.getX() - this.plantEntity.getX();
								double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
								double g = predictedPos.getZ() - this.plantEntity.getZ();
								float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
								helmetProj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.66F, 0F);
								helmetProj.setOwner(this.plantEntity);
								helmetProj.setMaxAge(helmetProj.age + 58);
								if (livingEntity != null && livingEntity.isAlive()) {
									this.beamTicks = -7;
									this.plantEntity.playSound(PvZSounds.MAGNETATTRACTEVENT, 0.2F, 1);
									this.plantEntity.untarget = -10;
								}
							}
							if (helmetProj2 != null) {
								Vec3d vec3d4 = new Vec3d((double) 10, 0.0, 1.5).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
								double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
								Vec3d targetPos = livingEntity.getPos();
								double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
								double d = this.plantEntity.squaredDistanceTo(predictedPos);
								float df = (float) d;
								double e = predictedPos.getX() - this.plantEntity.getX();
								double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
								double g = predictedPos.getZ() - this.plantEntity.getZ();
								float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
								helmetProj2.setVelocity(e * (double) h + vec3d4.x, f * (double) h, g * (double) h + vec3d4.z, 0.66F, 0F);
								helmetProj2.setOwner(this.plantEntity);
								helmetProj2.setMaxAge(helmetProj2.age + 58);
								if (livingEntity != null && livingEntity.isAlive()) {
									this.beamTicks = -7;
									this.plantEntity.playSound(PvZSounds.MAGNETATTRACTEVENT, 0.2F, 1);
									this.plantEntity.untarget = -10;
								}
							}
							if (helmetProj3 != null) {
								Vec3d vec3d4 = new Vec3d((double) 10, 0.0, -1.5).rotateY(-this.plantEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
								double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 36) ? 50 : 1;
								Vec3d targetPos = livingEntity.getPos();
								double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
								double d = this.plantEntity.squaredDistanceTo(predictedPos);
								float df = (float) d;
								double e = predictedPos.getX() - this.plantEntity.getX();
								double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
								double g = predictedPos.getZ() - this.plantEntity.getZ();
								float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
								helmetProj3.setVelocity(e * (double) h + vec3d4.x, f * (double) h, g * (double) h + vec3d4.z, 0.66F, 0F);
								helmetProj3.setOwner(this.plantEntity);
								helmetProj3.setMaxAge(helmetProj3.age + 58);
								if (livingEntity != null && livingEntity.isAlive()) {
									this.beamTicks = -7;
									this.plantEntity.playSound(PvZSounds.MAGNETATTRACTEVENT, 0.2F, 1);
									this.plantEntity.untarget = -10;
								}
							}
						}
					}
					else if (this.animationTicks >= 0)
					{
						this.beamTicks = -7;
						this.animationTicks = -16;
					}
				}
				else if (!this.plantEntity.magnetized) {
					if (this.animationTicks == -14) {
						this.plantEntity.magnetize();
					}
				}
				super.tick();
			}
		}
	}
}
