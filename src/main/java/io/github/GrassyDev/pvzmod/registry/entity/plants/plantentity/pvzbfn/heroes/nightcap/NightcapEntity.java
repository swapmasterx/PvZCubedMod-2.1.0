package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.nightcap;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercespore.PierceSporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShadowSporeVariants;
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
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
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
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biomes;
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

import java.util.*;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class NightcapEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private String controllerName = "shamrockcontroller";

	private boolean isFiring;

	private boolean isAfraid;

	private int animationScare;

    public NightcapEntity(EntityType<? extends NightcapEntity> entityType, World world) {
        super(entityType, world);

		this.nocturnal = true;
        this.animationScare = 30;
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
		if (status == 14) {
			this.isAfraid = false;
			this.animationScare = 30;
		}
		if (status == 104) {
			this.isAfraid = true;
			this.isFiring = false;
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(NightcapEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
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
		if (this.getIsAsleep()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("nightcap.asleep"));
		}
		else if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("nightcap.shoot"));
		}
		else if (this.animationScare <= 0 && this.isAfraid){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("nightcap.hiding"));
		}
		else if (this.isAfraid){
			event.getController().setAnimation(RawAnimation.begin().thenPlay("nightcap.hide"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("nightcap.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new NightcapEntity.FireBeamGoal(this));
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	protected List<HostileEntity> checkForZombies() {
		List<HostileEntity> list = this.getWorld().getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(10));
		List<HostileEntity> list2 = new ArrayList<>();
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
				if (!var9.hasNext()) {
					return list2;
				}
				hostileEntity = (HostileEntity) var9.next();

			if (!this.getShadowPowered() && !(hostileEntity instanceof ZombiePropEntity) && this.squaredDistanceTo(hostileEntity) <= 36) {
				if (hostileEntity.getY() < (this.getY() + 2) && hostileEntity.getY() > (this.getY() - 2)) {
					list2.add(hostileEntity);
					return list2;
				}
			}
			else if (this.getShadowPowered() && !(hostileEntity instanceof ZombiePropEntity) && this.squaredDistanceTo(hostileEntity) <= 9) {
				if (hostileEntity.getY() < (this.getY() + 2) && hostileEntity.getY() > (this.getY() - 2)) {
					list2.add(hostileEntity);
					return list2;
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

	/** /~*~//~**TICKING**~//~*~/ **/
	protected List<LivingEntity> zombieList = new ArrayList<>();
	private void damageEntity() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5));
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

			if ((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && !livingEntity.isInsideWaterOrBubbleColumn()) {
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
				if (zombiePropEntity2 == null ||
						zombiePropEntity2 instanceof ZombieShieldEntity) {
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
					SoundEvent sound;
					livingEntity.playSound(PvZSounds.PEAHITEVENT, 0.1F, (float) (0.5F + Math.random()));
					float damage = 1;
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
						generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this), damage2);
					} else {
						livingEntity.damage(getDamageSources().mobProjectile(this, this), damage);
					}
					livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.PVZPOISON, 60, 6)));
					livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.SHADOW, 60, 1)));
					this.zombieList.add(livingEntity);
				}
			}
			else {
				this.zombieList.remove(livingEntity);
			}
		}
	}

	private int tickDamage = 20;

	public void tick() {
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			Vec3d vec3d = Vec3d.ofCenter(this.getBlockPos()).add(0, -0.5, 0);
			List<ShadowTile> tileCheck = getWorld().getNonSpectatingEntities(ShadowTile.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(vec3d.getX(), vec3d.getY(), vec3d.getZ()));
			if (tileCheck.isEmpty()) {
				if (this.getWorld().getMoonSize() < 0.1 && this.getWorld().isSkyVisible(this.getBlockPos())) {
					if (serverWorld.isNight()) {
						this.setShadowPowered(Shadow.TRUE);
					}
				} else {
					this.setShadowPowered(Shadow.FALSE);
				}
			}
			if (!tileCheck.isEmpty()) {
				this.setShadowPowered(Shadow.TRUE);
			}
		}
		if (!this.getWorld().isClient && !this.getCofee()) {
			if ((this.getWorld().getAmbientDarkness() >= 2 ||
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) < 2 ||
					this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(Biomes.MUSHROOM_FIELDS)))) {
				this.setIsAsleep(IsAsleep.FALSE);
			} else if (this.getWorld().getAmbientDarkness() < 2 &&
					this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 2 &&
					!this.getWorld().getBiome(this.getBlockPos()).getKey().equals(Optional.ofNullable(Biomes.MUSHROOM_FIELDS))) {
				this.setIsAsleep(IsAsleep.TRUE);
			}
		}
		if (this.getIsAsleep() || !this.checkForZombies().isEmpty()){
			this.setTarget(null);
			if (!this.checkForZombies().isEmpty()) {
				this.setLowprof(LowProf.TRUE);
			}
		}
		else {
			this.targetZombies(this.getPos(), 10, false, false, false);
			this.setLowprof(LowProf.FALSE);
		}
		if (!this.checkForZombies().isEmpty()){
			this.isAfraid = true;
			this.getWorld().sendEntityStatus(this, (byte) 104);
		}
		else {
			this.isAfraid = false;
			this.getWorld().sendEntityStatus(this, (byte) 14);
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.NIGHTCAP_SEED_PACKET);
				}
				this.discard();
			}
		}
		super.tick();

		if (this.isAfraid && this.getShadowPowered()) {
			double dx = (double) (180 & 255) / 255.0;
			double ex = (double) (30 & 255) / 255.0;
			double fx = (double) (200 & 255) / 255.0;
			for (int i = 0; i < 16; ++i) {
				RandomGenerator randomGenerator = this.getRandom();
				RandomGenerator randomGenerator2 = this.getRandom();
				this.getWorld().addParticle(ParticleTypes.ENTITY_EFFECT, this.getX() + (double) MathHelper.nextBetween(randomGenerator, -2.5F, 2.5F),
						this.getY() + (this.random.range(-1, 1)),
						this.getZ() + (double) MathHelper.nextBetween(randomGenerator2,
								-2.5F, 2.5F), dx, ex, fx);
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}

		if (this.animationScare > 0 && this.isAfraid) {
			--this.animationScare;
		}
		this.checkForZombies();
		if (this.isAfraid && this.getShadowPowered()) {
			if (--tickDamage <= 0) {
				this.zombieList.clear();
				if (!this.hasStatusEffect(PvZCubed.DISABLE)) {
					this.damageEntity();
				}
				tickDamage = 20;
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.SHAMROCK_SEED_PACKET);
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
		return ModItems.SHAMROCK_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createNightcapAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15.0D);
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
		private final NightcapEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(NightcapEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
		}

		public boolean canStart() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			return livingEntity != null && livingEntity.isAlive() && !this.plantEntity.getIsAsleep();
		}

		public boolean shouldContinue() {
			return super.shouldContinue();
		}

		public void start() {
			this.beamTicks = -8;
			this.animationTicks = -16;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 14);
			this.plantEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 14);
				this.plantEntity.setTarget((LivingEntity) null);
			}
			else {
				if (!this.plantEntity.getIsAsleep() && !this.plantEntity.isAfraid) {
					this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
					++this.animationTicks;
					++this.beamTicks;
					if (this.beamTicks >= 0 && this.animationTicks >= -8){
						if (!(this.plantEntity.checkForZombies().isEmpty())){
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 104);
						}
						else {
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 14);
						}
					}
					if (this.plantEntity.checkForZombies().isEmpty())  {
						if (this.beamTicks >= 0 && this.animationTicks >= -8) {
							if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
								this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 14);
								PierceSporeEntity proj = new PierceSporeEntity(PvZEntity.PIERCESPORE, this.plantEntity.getWorld());
								double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 225) ? 50 : 5;
								Vec3d targetPos = livingEntity.getPos();
								double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
								double d = this.plantEntity.squaredDistanceTo(predictedPos);
								float df = (float)d;
								double e = predictedPos.getX() - this.plantEntity.getX();
								double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
								if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying()){
									f = f + 0.5594666671753;
								}
								double g = predictedPos.getZ() - this.plantEntity.getZ();
								float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
								proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.5F, 0F);
								proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
								proj.setOwner(this.plantEntity);
								proj.damageMultiplier = plantEntity.damageMultiplier;
								if (this.plantEntity.getShadowPowered()){
									proj.setVariant(ShadowSporeVariants.SHADOW);
								}
								if (livingEntity != null && livingEntity.isAlive()) {
									this.beamTicks = -13;
									this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
									this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
									this.plantEntity.getWorld().spawnEntity(proj);
								}
							}
						}
						else if (this.animationTicks >= 0)
						{
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
							this.beamTicks = -8;
							this.animationTicks = -16;
						}
					}
				}
				super.tick();
			}
		}
	}
}
