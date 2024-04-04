package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.fluid.*;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.cattail.CattailEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.LilypadHats;
import io.github.GrassyDev.pvzmod.items.seedpackets.CattailSeeds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.registry.tag.FluidTags;;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.*;
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


import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.ICE;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

;

public class LilyPadEntity extends PlantEntity implements GeoEntity {

	private static final TrackedData<Integer> DATA_ID_TYPE_HAT =
			DataTracker.registerData(LilyPadEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private String controllerName = "wallcontroller";


	private int amphibiousRaycastDelay;

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);


	public LilyPadEntity(EntityType<? extends LilyPadEntity> entityType, World world) {
        super(entityType, world);
		amphibiousRaycastDelay = 1;

		this.setNoGravity(true);
    }

	public LilyPadEntity(World world, double x, double y, double z) {
		this(PvZEntity.LILYPAD, world);
		this.setPosition(x, y, z);
		this.prevX = x;
		this.prevY = y;
		this.prevZ = z;
	}

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_COUNT, false);
		//Hat//
		this.dataTracker.startTracking(DATA_ID_TYPE_HAT, 0);
	}
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		this.dataTracker.set(DATA_ID_TYPE_COUNT, tag.getBoolean("Permanent"));
		//Hat//
		this.dataTracker.set(DATA_ID_TYPE_HAT, tag.getInt("Hat"));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.putBoolean("Permanent", this.getPuffshroomPermanency());
		//Variant//
		tag.putInt("Hat", this.getTypeHat());
	}

	static {
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		LilypadHats hat = Util.getRandom(LilypadHats.values(), this.random);
		setHat(hat);
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeHat() {
		return this.dataTracker.get(DATA_ID_TYPE_HAT);
	}

	public LilypadHats getHat() {
		return LilypadHats.byId(this.getTypeHat() & 255);
	}

	private void setHat(LilypadHats hat) {
		this.dataTracker.set(DATA_ID_TYPE_HAT, hat.getId() & 255);
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	private static final TrackedData<Boolean> DATA_ID_TYPE_COUNT =
			DataTracker.registerData(LilyPadEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

	public enum PuffPermanency {
		DEFAULT(false),
		PERMANENT(true);

		PuffPermanency(boolean id) {
			this.id = id;
		}

		private final boolean id;

		public boolean getId() {
			return this.id;
		}
	}

	private Boolean getPuffshroomPermanency() {
		return this.dataTracker.get(DATA_ID_TYPE_COUNT);
	}

	public void setPuffshroomPermanency(LilyPadEntity.PuffPermanency puffshroomPermanency) {
		this.dataTracker.set(DATA_ID_TYPE_COUNT, puffshroomPermanency.getId());
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
		if (this.getHat().equals(LilypadHats.LILY)){
			if (this.onWaterTile) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("lilypad.onground.lily2"));
			}
			else if (this.dryLand) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("lilypad.onground.lily"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("lilypad.idle.lily"));
			}
		}
		else {
			if (this.onWaterTile) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("lilypad.onground2"));
			}
			else if (this.dryLand) {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("lilypad.onground"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("lilypad.idle"));
			}
		}
        return PlayState.CONTINUE;
    }

	/** /~*~//~*POSITION*~//~*~/ **/

	public void setPosition(double x, double y, double z) {
		BlockPos blockPos = this.getBlockPos();
		if (this.hasVehicle()) {
			super.setPosition(x, y, z);
		} else {
			super.setPosition((double) MathHelper.floor(x) + 0.5, (double)MathHelper.floor(y + 0.5), (double)MathHelper.floor(z) + 0.5);
		}
	}


	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		super.tick();
		if (this.getVehicle() instanceof LilyPadEntity ||
				this.getVehicle() instanceof BubblePadEntity){
			this.getVehicle().discard();
		}
		BlockPos blockPos = this.getBlockPos();

		if (this.isInsideWaterOrBubbleColumn()){
			kill();
		}

		if (--amphibiousRaycastDelay <= 0 && age > 5) {
			amphibiousRaycastDelay = 20;
			HitResult hitResult = amphibiousRaycast(0.25);
			if (hitResult.getType() == HitResult.Type.MISS && !this.hasVehicle()) {
				kill();
			}
			if (this.age > 1) {
				BlockPos blockPos2 = this.getBlockPos();
				BlockState blockState = this.getLandingBlockState();
				FluidState fluidState = getWorld().getFluidState(this.getBlockPos().add(0, 0, 0));
				if (!(fluidState.getFluid() == Fluids.WATER) && !onWaterTile) {
					this.dryLand = true;
					onWater = false;
				} else {
					this.dryLand = false;
					onWater = true;
				}
				if (!blockPos2.equals(blockPos) || (!(fluidState.getFluid() == Fluids.WATER) && !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.LILYPAD_SEED_PACKET);
				}
				this.discard();
				}
			}
		}
		if (!this.onWater){
			this.setLowprof(LowProf.TRUE);
		}
		else {
			this.setLowprof(LowProf.FALSE);
		}
		if (this.age >= 900 && !this.getPuffshroomPermanency() && !this.hasPassengers()) {
			this.discard();
		}
		float time = 200 / this.getWorld().getLocalDifficulty(this.getBlockPos()).getLocalDifficulty();
		if (this.age > 4 && this.age <= time && !this.getPuffshroomPermanency() && !this.hasStatusEffect(StatusEffects.GLOWING)) {
			if (this.getWorld().getGameRules().getBoolean(PvZCubed.PLANTS_GLOW)) {
				this.addStatusEffect((new StatusEffectInstance(StatusEffects.GLOWING, (int) Math.floor(time), 1)));
			}
		}
    }


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.CATTAIL_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item) && !this.dryLand) {
			this.playSound(SoundEvents.ENTITY_PLAYER_SPLASH);
			if ((this.getWorld() instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.getWorld();
				CattailEntity plantEntity = (CattailEntity) PvZEntity.CATTAIL.create(getWorld());
				plantEntity.setTarget(this.getTarget());
				plantEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				plantEntity.initialize(serverWorld, getWorld().getLocalDifficulty(plantEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				plantEntity.setAiDisabled(this.isAiDisabled());
				plantEntity.setPersistent();
				if (this.hasCustomName()) {
					plantEntity.setCustomName(this.getCustomName());
					plantEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.hasVehicle()){
					plantEntity.startRiding(this.getVehicle(), true);
				}
				serverWorld.spawnEntityAndPassengers(plantEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				;
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !getWorld().getGameRules().getBoolean(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.CATTAIL_SEED_PACKET, CattailSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (!this.getHat().equals(LilypadHats.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setHat(LilypadHats.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getHat().equals(LilypadHats.LILY) && itemStack.isOf(Items.SPORE_BLOSSOM)) {
			this.setHat(LilypadHats.LILY);
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
		return ModItems.LILYPAD_SEED_PACKET.getDefaultStack();
	}

	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createLilyPadAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 8D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
    }

	protected boolean canAddPassenger(Entity passenger) {
		return this.getPassengerList().size() < this.getMaxPassengers() && !this.isSubmergedIn(FluidTags.WATER);
	}

	protected int getMaxPassengers() {
		return 1;
	}

	/**public boolean collidesWith(Entity other) {
		return canCollide(this, other);
	}

	public static boolean canCollide(Entity entity, Entity other) {
		return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
	}

	public double getMountedHeightOffset() {
		return 0;
	}
	 **/

	protected boolean canClimb() {
		return false;
	}

	public boolean collides() {
		return !this.isRemoved();
	}

	protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
		return 0.075F;
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

	public boolean isCollidable() {
		return false;
	}

	public boolean isPushable() {
		return false;
	}

	protected void pushAway(Entity entity) {
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

	public static boolean canLilyPadSpawn(EntityType<? extends LilyPadEntity> entityType, WorldAccess worldAccess, SpawnReason reason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos2 = pos.add(0, 0, 0);
		return ((worldAccess.getFluidState(pos.down()).isSource() && !worldAccess.getFluidState(blockPos2).isSource() && !worldAccess.getFluidState(pos.down()).isOf(Fluids.LAVA)) ||
				worldAccess.getBlockState(pos.down()).getBlock().equals(ICE)) &&
				Objects.requireNonNull(worldAccess.getServer()).getGameRules().getBoolean(PvZCubed.SHOULD_PLANT_SPAWN);
	}
}
