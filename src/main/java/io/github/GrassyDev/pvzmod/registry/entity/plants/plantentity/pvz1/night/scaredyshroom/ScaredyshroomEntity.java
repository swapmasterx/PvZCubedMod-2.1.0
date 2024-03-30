package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spore.SporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.ScaredyshroomVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
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
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeKeys;
import org.jetbrains.annotations.Nullable;
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

public class ScaredyshroomEntity extends PlantEntity implements GeoAnimatable, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private String controllerName = "scaredycontroller";


	private boolean isFiring;

	private boolean isAfraid;

	private int animationScare;

    public ScaredyshroomEntity(EntityType<? extends ScaredyshroomEntity> entityType, World world) {
        super(entityType, world);


        this.animationScare = 30;
		this.targetPoison = true;
		this.nocturnal = true;
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
			DataTracker.registerData(ScaredyshroomEntity.class, TrackedDataHandlerRegistry.INTEGER);

	public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty,
								 SpawnReason spawnReason, @Nullable EntityData entityData,
								 @Nullable NbtCompound entityNbt) {
		return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
	}

	private int getTypeVariant() {
		return this.dataTracker.get(DATA_ID_TYPE_VARIANT);
	}

	public ScaredyshroomVariants getVariant() {
		return ScaredyshroomVariants.byId(this.getTypeVariant() & 255);
	}

	public void setVariant(ScaredyshroomVariants variant) {
		this.dataTracker.set(DATA_ID_TYPE_VARIANT, variant.getId() & 255);
	}


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimatableManager data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimatableInstanceCache getFactory() {
		return this.factory;
	}


	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (this.getIsAsleep()) {
			event.getController().setAnimation(new RawAnimation().loop("scaredyshroom.asleep"));
		}
		else if (this.isFiring) {
			event.getController().setAnimation(new RawAnimation().playOnce("scaredyshroom.attack"));
		}
		else if (this.animationScare <= 0 && this.isAfraid){
			event.getController().setAnimation(new RawAnimation().loop("scaredyshroom.afraid"));
		}
		else if (this.isAfraid){
			event.getController().setAnimation(new RawAnimation().playOnce("scaredyshroom.hiding"));
		}
		else {
			event.getController().setAnimation(new RawAnimation().loop("scaredyshroom.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new ScaredyshroomEntity.FireBeamGoal(this));
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
			do {
				if (!var9.hasNext()) {
					return list2;
				}
				hostileEntity = (HostileEntity) var9.next();
			} while (this.squaredDistanceTo(hostileEntity) > 16);

			if (!(hostileEntity instanceof ZombiePropEntity)) {
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

	public void tick() {
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
		if (this.getIsAsleep() || !this.checkForZombies().isEmpty()){
			this.setTarget(null);
		}
		else {
			this.targetZombies(this.getPos(), 10, false, false, false);
		}
		if (!this.checkForZombies().isEmpty()){
			this.getWorld().sendEntityStatus(this, (byte) 104);
		}
		else {
			this.getWorld().sendEntityStatus(this, (byte) 14);
		}
		super.tick();
		this.checkForZombies();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.SCAREDYSHROOM_SEED_PACKET);
				}
				this.discard();
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
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.SCAREDYSHROOM_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
					itemStack.decrement(1);
				}
			}
			this.discard();
			return ActionResult.SUCCESS;
		}
		if (!this.getVariant().equals(ScaredyshroomVariants.DEFAULT) && itemStack.isOf(Items.WHITE_DYE)) {
			this.setVariant(ScaredyshroomVariants.DEFAULT);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ScaredyshroomVariants.DEMIBOY) &&
				(itemStack.isOf(Items.GRAY_DYE) || itemStack.isOf(Items.LIGHT_GRAY_DYE) || itemStack.isOf(Items.LIGHT_BLUE_DYE))) {
			this.setVariant(ScaredyshroomVariants.DEMIBOY);
			if (!player.getAbilities().creativeMode){
				itemStack.decrement(1);
			}
			return ActionResult.SUCCESS;
		}
		else if (!this.getVariant().equals(ScaredyshroomVariants.LINK) &&
				(itemStack.isOf(Items.WOODEN_SWORD) && !itemStack.hasEnchantments() ||
						itemStack.isOf(Items.IRON_SWORD) && !itemStack.hasEnchantments() ||
						itemStack.isOf(Items.SHIELD) && !itemStack.hasEnchantments() ||
						itemStack.isOf(Items.BOW) && !itemStack.hasEnchantments() ||
						itemStack.isOf(Items.TNT))) {
			this.setVariant(ScaredyshroomVariants.LINK);
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
		return ModItems.SCAREDYSHROOM_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createScaredyshroomAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 12.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 30.0D);
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
		private final ScaredyshroomEntity plantEntity;
		private int beamTicks;
		private int animationTicks;

		public FireBeamGoal(ScaredyshroomEntity plantEntity) {
			this.plantEntity = plantEntity;
			this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
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
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
			this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
			this.plantEntity.setTarget((LivingEntity)null);
		}

		public void tick() {
			LivingEntity livingEntity = this.plantEntity.getTarget();
			this.plantEntity.getNavigation().stop();
			this.plantEntity.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			if ((!this.plantEntity.canSee(livingEntity)) &&
					this.animationTicks >= 0) {
				this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
				this.plantEntity.setTarget((LivingEntity) null);
			}
			else {
				if (!this.plantEntity.getIsAsleep() && !this.plantEntity.isAfraid) {
					this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
					++this.animationTicks;
					++this.beamTicks;
					if (this.plantEntity.checkForZombies().isEmpty())  {
						if (this.beamTicks >= 0 && this.animationTicks >= -7) {
							if (!this.plantEntity.isInsideWaterOrBubbleColumn()) {
								this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 14);
								SporeEntity proj = new SporeEntity(PvZEntity.SPORE, this.plantEntity.world);
								double time = (this.plantEntity.squaredDistanceTo(livingEntity) > 225) ? 50 : 5;
								Vec3d targetPos = livingEntity.getPos();
								double predictedPosX = targetPos.getX() + (livingEntity.getVelocity().x * time);
						double predictedPosZ = targetPos.getZ() + (livingEntity.getVelocity().z * time);
						Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
								double d = this.plantEntity.squaredDistanceTo(predictedPos);
								float df = (float)d;
								double e = predictedPos.getX() - this.plantEntity.getX();
								double f = (livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.plantEntity.getY() + 0.3595 : livingEntity.getY() - this.plantEntity.getY();
								double g = predictedPos.getZ() - this.plantEntity.getZ();
								float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
								proj.sporeAge = 58;
								proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.66F, 0F);
								proj.updatePosition(this.plantEntity.getX(), this.plantEntity.getY() + 0.75D, this.plantEntity.getZ());
								proj.setOwner(this.plantEntity);
								proj.damageMultiplier = plantEntity.damageMultiplier;
								if (livingEntity != null && livingEntity.isAlive()) {
									this.beamTicks = -13;
									this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 111);
									this.plantEntity.playSound(PvZSounds.PEASHOOTEVENT, 0.2F, 1);
									this.plantEntity.world.spawnEntity(proj);
								}
							}
						}
						else if (this.animationTicks >= 0)
						{
							this.plantEntity.world.sendEntityStatus(this.plantEntity, (byte) 110);
							this.beamTicks = -7;
							this.animationTicks = -16;
						}
					}
				}
				super.tick();
			}
		}
	}
}
