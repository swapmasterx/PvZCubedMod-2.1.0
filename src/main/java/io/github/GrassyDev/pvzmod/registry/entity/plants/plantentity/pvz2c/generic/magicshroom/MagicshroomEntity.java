package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom.GambleshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.card.ShootingCardEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.items.seedpackets.GambleshroomSeeds;
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
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biomes;
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


import java.util.*;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class MagicshroomEntity extends PlantEntity implements GeoAnimatable, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private String controllerName = "magiccontroller";


	private boolean isFiring;

	private boolean isHatFiring;

    public MagicshroomEntity(EntityType<? extends MagicshroomEntity> entityType, World world) {
        super(entityType, world);

		this.nocturnal = true;
    }

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, true);
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Variant", this.hasHat());
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getBoolean("Variant"));
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
		if (status == 117){
			this.isHatFiring = true;
		}
		else if (status == 118){
			this.isHatFiring = false;
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(MagicshroomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	public enum MagicHat {
		FALSE(false),
		TRUE(true);

		MagicHat(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	public Boolean hasHat() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public void setHat(MagicshroomEntity.MagicHat magicHat) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, magicHat.getId());
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

	@Override
	public double getTick(Object object) {
		return 0;
	}


	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getIsAsleep()) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("magicshroom.asleep"));
		}
		else if (this.isHatFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("magicshroom.hat"));
		}
		else if (this.isFiring) {
			event.getController().setAnimation(RawAnimation.begin().thenPlay("magicshroom.shoot"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("magicshroom.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new MagicshroomEntity.FireBeamGoal(this));
	}

	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	protected List<HostileEntity> checkForZombiesHAT() {
		List<HostileEntity> list = this.getWorld().getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(10));
		List<HostileEntity> list2 = new ArrayList<>();
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			if (!var9.hasNext()) {
				return list2;
			}
			hostileEntity = (HostileEntity) var9.next();

			if (hostileEntity.squaredDistanceTo(this) <= 100) {
				if (!(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
					list2.add(hostileEntity);
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

	private int hatTicks = 600;


	public void tick() {
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
		if (this.getIsAsleep()){
			this.setTarget(null);
		}
		else {
			if (!this.checkForZombiesHAT().isEmpty() && this.hasHat()) {
				this.targetZombies(this.getPos(), 5, false, true, true);
			}
			else{
				this.targetZombies(this.getPos(), 5, false, false, true);
			}
		}
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.MAGICSHROOM_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (this.hasHat()){
			this.hatTicks = 600;
		}
		else {
			if (--this.hatTicks <= 0){
				this.setHat(MagicHat.TRUE);
			}
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
			dropItem(ModItems.MAGICSHROOM_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.GAMBLESHROOM_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT);
			if ((this.getWorld() instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.getWorld();
				GambleshroomEntity gambleshroomEntity = (GambleshroomEntity) PvZEntity.GAMBLESHROOM.create(getWorld());
				gambleshroomEntity.setTarget(this.getTarget());
				gambleshroomEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				gambleshroomEntity.initialize(serverWorld, getWorld().getLocalDifficulty(gambleshroomEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				gambleshroomEntity.setAiDisabled(this.isAiDisabled());
				gambleshroomEntity.setPersistent();
				if (this.hasCustomName()) {
					gambleshroomEntity.setCustomName(this.getCustomName());
					gambleshroomEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.hasVehicle()){
					gambleshroomEntity.startRiding(this.getVehicle(), true);
				}
				serverWorld.spawnEntityAndPassengers(gambleshroomEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !getWorld().getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.GAMBLESHROOM_SEED_PACKET, GambleshroomSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		return super.interactMob(player, hand);
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.MAGICSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createMagicshroomAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
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
		private final MagicshroomEntity plantEntity;
		private int beamTicks;
		private int animationTicks;
		private int cardsShot;

		private boolean shootingHat;

		public FireBeamGoal(MagicshroomEntity plantEntity) {
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
			this.beamTicks = -7;
			this.animationTicks = -16;
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(this.plantEntity.getTarget(), 90.0F, 90.0F);
			this.plantEntity.velocityDirty = true;
		}

		public void stop() {
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 118);
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
				if (!this.plantEntity.getIsAsleep()) {
					if (this.animationTicks == -16) {
						this.shootingHat = !this.plantEntity.checkForZombiesHAT().isEmpty() && this.plantEntity.hasHat();
					}
					++this.animationTicks;
					++this.beamTicks;
					Vec3d vec3d2 = Vec3d.ZERO;
					if (livingEntity != null && livingEntity.isAlive()){
						vec3d2 = new Vec3d((double) 1, 0.0, 0).rotateY(-livingEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					}
					Vec3d vec3d3 = Vec3d.ofCenter(livingEntity.getBlockPos());
					Vec3d blockpos = new Vec3d(vec3d3.x + vec3d2.x, vec3d3.y + vec3d2.y, vec3d3.z + vec3d2.z);
					List<PlantEntity> list = this.plantEntity.getWorld().getNonSpectatingEntities(PlantEntity.class, PvZEntity.MAGICHAT.getDimensions().getBoxAt(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
					if (this.shootingHat && list.isEmpty()) {
						this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 117);
						if (this.beamTicks >= 0 && this.animationTicks >= -5) {
							if (livingEntity != null && livingEntity.isAlive()) {
								this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 14);
								MagichatEntity hat = (MagichatEntity) PvZEntity.MAGICHAT.create(this.plantEntity.getWorld());
								hat.refreshPositionAndAngles(blockpos.getX(), livingEntity.getY(), blockpos.getZ(), 0, 0);
								if (this.plantEntity.getWorld() instanceof ServerWorld serverWorld && list.isEmpty()) {
									hat.initialize(serverWorld, this.plantEntity.getWorld().getLocalDifficulty(livingEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
									serverWorld.spawnEntityAndPassengers(hat);
									this.plantEntity.setHat(MagicHat.FALSE);
									this.plantEntity.getWorld().spawnEntity(hat);
								}
								this.beamTicks = -13;
								this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
							}
						} else if (this.animationTicks >= 0) {
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 118);
							this.beamTicks = -7;
							this.animationTicks = -16;
						}
					} else {
						this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
						if (this.beamTicks >= 0 && this.animationTicks <= -7 && cardsShot <= 3) {
							if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
								ShootingCardEntity proj = new ShootingCardEntity(PvZEntity.CARDPROJ, this.plantEntity.getWorld());
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
								proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
								proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
								proj.setOwner(this.plantEntity);
								proj.damageMultiplier = plantEntity.damageMultiplier;
								++this.cardsShot;
								if (livingEntity != null && livingEntity.isAlive()) {
									this.beamTicks = -1;
									this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.125F, 1);
									this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 111);
									this.plantEntity.getWorld().spawnEntity(proj);
								}
							}
						} else if (this.animationTicks >= 0) {
							this.cardsShot = 0;
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 110);
							this.plantEntity.getWorld().sendEntityStatus(this.plantEntity, (byte) 118);
							this.beamTicks = -7;
							this.animationTicks = -16;
						}
					}
					super.tick();
				}
			}
		}
	}
}
