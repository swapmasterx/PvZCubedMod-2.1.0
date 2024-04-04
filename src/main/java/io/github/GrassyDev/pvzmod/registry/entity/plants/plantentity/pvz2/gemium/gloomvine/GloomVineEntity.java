package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.gloomvine;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowFullTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
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

import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class GloomVineEntity extends PlantEntity.VineEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private String controllerName = "fumecontroller";

	private boolean isTired;

	private boolean isFiring;

	public GloomVineEntity(EntityType<? extends GloomVineEntity> entityType, World world) {
		super(entityType, world);

		this.isBurst = true;
	}

	protected void initDataTracker() {
		super.initDataTracker();
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 113) {
			this.isTired = true;
			this.isFiring = false;
		} else if (status == 112) {
			this.isTired = false;
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
		if (status == 106) {
			// PURPLE
			double d = (double)(180 & 255) / 255.0;
			double e = (double)(30 & 255) / 255.0;
			double f = (double)(200 & 255) / 255.0;

			for(int j = 0; j < 32; ++j) {
				this.getWorld().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (this.random.range(-2, 2)) * 0.85F, this.getY() + (this.random.range(-1, 1)) * 0.5F, this.getZ() + (this.random.range(-2, 2)) * 0.85F, d, e, f);
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
		if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("gloomvine.shoot"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("gloomvine.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new GloomVineEntity.FireBeamGoal(this));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {

	}

	protected void splashDamage() {
		Vec3d vec3d = this.getPos();
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(2));
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
			} while (this.squaredDistanceTo(livingEntity) > 9);

			if (livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) {
				ZombiePropEntity zombiePropEntity2 = null;
				ZombiePropEntity zombiePropEntity3 = null;
				for (Entity entity1 : livingEntity.getPassengerList()) {
					if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
						zombiePropEntity2 = zpe;
					}
					else if (entity1 instanceof ZombiePropEntity zpe) {
						zombiePropEntity3 = zpe;
					}
				}
				if (livingEntity.getY() < (this.getY() + 1.5) && livingEntity.getY() > (this.getY() - 1.5)) {
					if (!getWorld().isClient &&
							!(zombiePropEntity2 != null && !(zombiePropEntity2 instanceof ZombieShieldEntity)) &&
					!(zombiePropEntity3 != null && !(zombiePropEntity3 instanceof ZombieShieldEntity)) &&
							!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()) &&
							!(livingEntity instanceof GeneralPvZombieEntity zombie1 && zombie1.isHovering())) {
						String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
						SoundEvent sound;
						sound = switch (zombieMaterial) {
							case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
							case "plastic" -> PvZSounds.CONEHITEVENT;
							case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
							default -> PvZSounds.PEAHITEVENT;
						};
						livingEntity.playSound(sound, 0.2F, (float) (0.5F + Math.random()));
						float damage = 2F;
						if (damage > livingEntity.getHealth() &&
								!(livingEntity instanceof ZombieShieldEntity) &&
								livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							float damage2 = damage - ((LivingEntity) livingEntity).getHealth();
							livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
							generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this), damage2);
						} else {
							livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
						}
					}
				}
			}
		}
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

	public void createShadowTile(BlockPos blockPos){
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			ShadowTile tile = (ShadowTile) PvZEntity.SHADOWTILE.create(getWorld());
			tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0, 0);
			tile.initialize(serverWorld, getWorld().getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);

			Vec3d vec3d = Vec3d.ofCenter(blockPos).add(0, -0.5, 0);

			List<ShadowFullTile> fullCheck = getWorld().getNonSpectatingEntities(ShadowFullTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			List<ShadowTile> tileCheck = getWorld().getNonSpectatingEntities(ShadowTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));

			for (ShadowFullTile shadowFullTile : fullCheck){
				shadowFullTile.discard();
			}
			if (tileCheck.isEmpty()) {
				tile.setHeadYaw(0);
				tile.setPersistent();
				serverWorld.spawnEntityAndPassengers(tile);
			}
		}
	}

	public void tick() {
		this.targetZombies(this.getPos(), 3, true, false, true);
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.GLOOMVINE_SEED_PACKET);
				}
				this.discard();
			}
		}
		this.createShadowTile(this.getBlockPos());
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}

	protected void mobTick() {
		super.mobTick();
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.GLOOMVINE_SEED_PACKET);
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
		return ModItems.GLOOMVINE_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createGloomVineAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 32.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 3D);
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

	static class FireBeamGoal extends Goal {
		private final GloomVineEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(GloomVineEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive();
		}

		public boolean shouldContinue() {
			return super.shouldContinue() && !this.plantEntity.isTired;
		}

		public void start() {
			this.beamTicks = -8;
			this.animationTicks = -21;
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
			if ((!this.plantEntity.canSee(livingEntity) && this.animationTicks >= 0) || this.plantEntity.isTired){
				this.plantEntity.setTarget((LivingEntity) null);
			} else {
				this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
				++this.beamTicks;
				++this.animationTicks;
				if (this.beamTicks >= 0 && this.animationTicks <= -4) {
					if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
						this.beamTicks = -2;
						this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
						this.plantEntity.playSound(PvZSounds.FUMESHROOMSHOOTEVENT, 0.3F, 1);
						this.plantEntity.splashDamage();
						this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 106);
					}
				}
				if (this.animationTicks >= 0) {
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
					this.beamTicks = -8;
					this.animationTicks = -21;
				}
				super.tick();
			}
		}
	}
}
