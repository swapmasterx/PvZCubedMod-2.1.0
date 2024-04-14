package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.SunflowerVariants;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.TwinSunflowerVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.items.seedpackets.TwinSunflowerSeeds;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
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

import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.DISABLE;
import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SunflowerEntity extends PlantEntity implements GeoEntity {

	private static final TrackedData<Integer> DATA_ID_TYPE_VARIANT =
			DataTracker.registerData(SunflowerEntity.class, TrackedDataHandlerRegistry.INTEGER);

	private static final TrackedData<Integer> SUN_SPEED;

    private String controllerName = "suncontroller";
    public int sunProducingTime;


	int raycastDelay = (int) (PVZCONFIG.nestedSun.sunflowerSec() * 20);

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private boolean sunProducerCheck;

	public SunflowerEntity(EntityType<? extends SunflowerEntity> entityType, World world) {
		super(entityType, world);

	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(DATA_ID_TYPE_VARIANT, 0);
		this.dataTracker.startTracking(SUN_SPEED, -1);
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		//Variant//
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, tag.getInt("Variant"));
		if (tag.contains("Fuse", 99)) {
			this.sunProducingTime = tag.getShort("Fuse");
		}
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
		tag.putInt("Variant", this.getTypeVariant());
		tag.putShort("Fuse", (short)this.sunProducingTime);
	}

	static {
		SUN_SPEED = DataTracker.registerData(SunflowerEntity.class, TrackedDataHandlerRegistry.INTEGER);
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}


	/** /~*~//~*VARIANTS*~//~*~/ **/

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public SunflowerVariants getVariant() {
		return SunflowerVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(SunflowerVariants variant) {
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
        event.getController().setAnimation(RawAnimation.begin().thenLoop("sunflower.idle"));
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {

		this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 20.0F));
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

	private int currentFuseTime;

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(SUN_SPEED, fuseSpeed);
	}

	public int getFuseSpeed() {
		return (Integer)this.dataTracker.get(SUN_SPEED);
	}

	public void tick() {
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			if (!this.isAiDisabled() && this.isAlive()) {
				BlockPos blockPos2 = this.getBlockPos();
				BlockState blockState = this.getLandingBlockState();
				if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
					this.discard();
				}
			}
		}

		if (this.isAlive()) {
			this.setFuseSpeed(1);

			int i = this.getFuseSpeed();

			this.currentFuseTime += i;
			if (this.currentFuseTime < 0) {
				this.currentFuseTime = 0;
			}

			if (this.currentFuseTime >= this.sunProducingTime) {
				if (!this.getWorld().isClient && this.isAlive() && this.sunProducerCheck && !this.isInsideWaterOrBubbleColumn()){
					this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
					if (this.getWorld().getAmbientDarkness() >= 2 ||
							this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) < 2){
						this.dropItem(ModItems.SMALLSUN);
					}
					else if (this.getWorld().getAmbientDarkness() < 2 &&
							this.getWorld().getLightLevel(LightType.SKY, this.getBlockPos()) >= 2) {
						this.dropItem(ModItems.SUN);
					}
					this.sunProducingTime = (int) (PVZCONFIG.nestedSun.sunflowerSec() * 20);
					this.sunProducerCheck = false;
					this.currentFuseTime = this.sunProducingTime;
				}
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && --this.sunProducingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && !this.hasStatusEffect(DISABLE)) {
			if (--raycastDelay >= 0){
				this.produceSun();
				raycastDelay = 60;
			}
		}

		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}

	protected void produceSun() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(5));
		List<SunflowerEntity> sunflowerList = this.getWorld().getNonSpectatingEntities(SunflowerEntity.class, this.getBoundingBox().expand(5));
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
			} while (this.squaredDistanceTo(livingEntity) > 225);

			if (livingEntity instanceof SunflowerEntity) {
				if (livingEntity.getY() < (this.getY() + 4) && livingEntity.getY() > (this.getY() - 4)) {
					if (sunflowerList.size() <= 1) {
						this.sunProducerCheck = true;
					}
				}
			}
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.SUNFLOWER_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		Item item = itemStack.getItem();
		if (itemStack.isOf(ModItems.TWINSUNFLOWER_SEED_PACKET) && !player.getItemCooldownManager().isCoolingDown(item)) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT);
			if ((this.getWorld() instanceof ServerWorld)) {
				ServerWorld serverWorld = (ServerWorld) this.getWorld();
				TwinSunflowerEntity twinSunflowerEntity = (TwinSunflowerEntity) PvZEntity.TWINSUNFLOWER.create(getWorld());
				twinSunflowerEntity.setTarget(this.getTarget());
				twinSunflowerEntity.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
				twinSunflowerEntity.initialize(serverWorld, getWorld().getLocalDifficulty(twinSunflowerEntity.getBlockPos()), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				twinSunflowerEntity.setAiDisabled(this.isAiDisabled());
				twinSunflowerEntity.setPersistent();
				if (this.hasCustomName()) {
					twinSunflowerEntity.setCustomName(this.getCustomName());
					twinSunflowerEntity.setCustomNameVisible(this.isCustomNameVisible());
				}
				if (this.hasVehicle()){
					twinSunflowerEntity.startRiding(this.getVehicle(), true);
				}
				if (this.getVariant().equals(SunflowerVariants.LESBIAN)){
					double random = Math.random();
					if (random <= 0.5) {
						twinSunflowerEntity.setVariant(TwinSunflowerVariants.LESBIAN);
					}
					else {
						twinSunflowerEntity.setVariant(TwinSunflowerVariants.LESBIAN_WLW);
					}
				}
				else if (this.getVariant().equals(SunflowerVariants.WLW)){
					double random = Math.random();
					if (random <= 0.33) {
						twinSunflowerEntity.setVariant(TwinSunflowerVariants.WLW);
					}
					else if (random <= 0.66) {
						twinSunflowerEntity.setVariant(TwinSunflowerVariants.WLW_MLM);
					}
					else {
						twinSunflowerEntity.setVariant(TwinSunflowerVariants.LESBIAN_WLW);
					}
				}
				else if (this.getVariant().equals(SunflowerVariants.MLM)){
					double random = Math.random();
					if (random <= 0.5) {
						twinSunflowerEntity.setVariant(TwinSunflowerVariants.MLM);
					}
					else {
						twinSunflowerEntity.setVariant(TwinSunflowerVariants.WLW_MLM);
					}
				}
				else {
					twinSunflowerEntity.setVariant(TwinSunflowerVariants.DEFAULT);
				}
				serverWorld.spawnEntityAndPassengers(twinSunflowerEntity);
				this.remove(RemovalReason.DISCARDED);
			}
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
				if (!PVZCONFIG.nestedSeeds.instantRecharge() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INSTANT_RECHARGE)) {
					player.getItemCooldownManager().set(ModItems.TWINSUNFLOWER_SEED_PACKET, TwinSunflowerSeeds.cooldown);
				}
			}
			return ActionResult.SUCCESS;
		}
		if (!this.getVariant().equals(SunflowerVariants.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setVariant(SunflowerVariants.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(SunflowerVariants.LESBIAN) &&
				(itemStack.isOf(Items.RED_DYE) || itemStack.isOf(Items.ORANGE_DYE) || itemStack.isOf(Items.MAGENTA_DYE))) {
			this.setVariant(SunflowerVariants.LESBIAN);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(SunflowerVariants.WLW) &&
				(itemStack.isOf(Items.PINK_DYE) || itemStack.isOf(Items.PURPLE_DYE))) {
			this.setVariant(SunflowerVariants.WLW);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(SunflowerVariants.MLM) &&
				(itemStack.isOf(Items.LIME_DYE) || itemStack.isOf(Items.GREEN_DYE))) {
			this.setVariant(SunflowerVariants.MLM);
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
		return ModItems.SUNFLOWER_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createSunflowerAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0);
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
}
