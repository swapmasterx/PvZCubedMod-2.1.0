package io.github.GrassyDev.pvzmod.registry.entity.environment.solarwinds;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.random.RandomGenerator;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


import java.util.List;
import java.util.Objects;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;
import static io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity.checkPlant;

public class SolarWinds extends TileEntity {
	public SolarWinds(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
	}

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "firetrailcontroller";
	private static final TrackedData<Integer> ALIVE_TIME;

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(ALIVE_TIME, 0);
	}

	public void writeCustomDataToNbt(NbtCompound nbt) {
		super.writeCustomDataToNbt(nbt);
		nbt.putInt("Alive", this.getAlive());
	}

	public void readCustomDataFromNbt(NbtCompound nbt) {
		super.readCustomDataFromNbt(nbt);
		this.dataTracker.set(ALIVE_TIME, nbt.getInt("Alive"));
	}

	static {
		ALIVE_TIME = DataTracker.registerData(SolarWinds.class, TrackedDataHandlerRegistry.INTEGER);
	}


	/**
	 * /~*~//~*GECKOLIB ANIMATION*~//~*~/
	 **/

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}


	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		event.getController().setAnimation(RawAnimation.begin().thenLoop("tile.anim"));
		return PlayState.CONTINUE;
	}


	/**
	 * /~*~//~*TICKING*~//~*~/
	 **/

	public Integer getAlive() {
		return this.dataTracker.get(ALIVE_TIME);
	}

	public void addAlive() {
		this.dataTracker.set(ALIVE_TIME, getAlive() + 1);
	}

	public void tick() {
		super.tick();
		this.addAlive();
		List<ItemEntity> list = getWorld().getNonSpectatingEntities(ItemEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		for (ItemEntity item : list) {
			if (item.getStack().isOf(ModItems.PLANTFOOD)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_AIR)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_AQUATIC)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.LARGESUN.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_COLD)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_ELEC)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_FIRE)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_FLOWER)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.LARGESUN.getDefaultStack(), 3);
				this.dropStack(ModItems.LARGESUN.getDefaultStack(), 3);
				this.dropStack(ModItems.LARGESUN.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_MUSHROOM)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_TOUGH)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
			}
			if (item.getStack().isOf(ModItems.PLANTFOOD_SHADOW)) {
				item.discard();
				this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
				this.dropStack(ModItems.PLANTFOOD_FLOWER.getDefaultStack(), 3);
			}
		}

		if (getAlive() >= 2400) {
			this.discard();
		}
		RandomGenerator randomGenerator = this.getRandom();
		for (int i = 0; i < 1; ++i) {
			double random = Math.random();
			if (random <= 0.25) {
				Vec3d particlePos = Vec3d.ofCenter(this.getBlockPos());
				double d = this.random.nextDouble() / 10 * this.random.range(-1, 1);
				double e = this.random.nextDouble() / 10 * this.random.range(0, 1);
				double f = this.random.nextDouble() / 10 * this.random.range(-1, 1);
				this.getWorld().addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, particlePos.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
					particlePos.getY() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
					particlePos.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F), d, e, f);
				this.getWorld().addParticle(ParticleTypes.POOF, particlePos.getX() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
					particlePos.getY() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F),
					particlePos.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1F, 1F), d, e, f);
			}
		}
		for (int i = 0; i < 8; ++i) {
			Vec3d particlePos = Vec3d.ofCenter(this.getBlockPos());
			double d = this.random.nextDouble() / 10 * this.random.range(-2, 2);
			double e = this.random.nextDouble() / 10 * this.random.range(0, 2);
			double f = this.random.nextDouble() / 10 * this.random.range(-2, 2);
			this.getWorld().addParticle(ParticleTypes.PORTAL, particlePos.getX() + (double) MathHelper.nextBetween(randomGenerator, -1.5F, 1.5F),
				particlePos.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
				particlePos.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1.5F, 1.5F), d, e, f);
		}
		for (int i = 0; i < 32; ++i) {
			Vec3d particlePos = Vec3d.ofCenter(this.getBlockPos());
			double d = this.random.nextDouble() / 10 * this.random.range(-1, 1);
			double e = this.random.nextDouble() / 30 * this.random.range(-1, 1);
			double f = this.random.nextDouble() / 10 * this.random.range(-1, 1);
			this.getWorld().addParticle(ParticleTypes.SMOKE, particlePos.getX() + (double) MathHelper.nextBetween(randomGenerator, -1.5F, 1.5F),
				particlePos.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
				particlePos.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1.5F, 1.5F), d, e, f);
		}
		for (int i = 0; i < 1; ++i) {
			Vec3d particlePos = Vec3d.ofCenter(this.getBlockPos());
			double d = this.random.nextDouble() / 30 * this.random.range(-2, 2);
			double e = this.random.nextDouble() / 30 * this.random.range(0, 2);
			double f = this.random.nextDouble() / 30 * this.random.range(-2, 2);
			this.getWorld().addParticle(ParticleTypes.WAX_ON, particlePos.getX() + (double) MathHelper.nextBetween(randomGenerator, -1.5F, 1.5F),
				particlePos.getY() + (double) MathHelper.nextBetween(randomGenerator, 0F, 1.5F),
				particlePos.getZ() + (double) MathHelper.nextBetween(randomGenerator, -1.5F, 1.5F), d, e, f);
		}
	}

	public void tickMovement() {
		super.tickMovement();
	}

	/**
	 * /~*~//~*INTERACTION*~//~*~/
	 **/


	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(Items.GOLDEN_SHOVEL)) {
			this.playSound(PvZSounds.PLANTPLANTEDEVENT);
			this.remove(RemovalReason.DISCARDED);
			return ActionResult.SUCCESS;
		} else {
			return super.interactMob(player, hand);
		}
	}


	/**
	 * /~*~//~*SPAWNING*~//~*~/
	 **/

	public static boolean canSolarWindsSpawn(EntityType<? extends SolarWinds> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, RandomGenerator random) {
		BlockPos blockPos = pos.down();
		return !world.getBlockState(blockPos).isOf(Blocks.AIR) && !world.getBlockState(blockPos).isOf(Blocks.CAVE_AIR) &&
			!checkPlant(Vec3d.ofCenter(pos), world, type) &&
			world.isSkyVisible(pos) && Objects.requireNonNull(world.getServer()).getGameRules().getBooleanValue(PvZCubed.SHOULD_PLANT_SPAWN) && PVZCONFIG.nestedSpawns.spawnPlants();
	}


	@Override
	public double getTick(Object object) {
		return 0;
	}
}
