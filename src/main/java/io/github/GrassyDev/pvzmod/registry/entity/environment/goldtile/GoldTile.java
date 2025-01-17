package io.github.GrassyDev.pvzmod.registry.entity.environment.goldtile;

import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
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

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class GoldTile extends TileEntity {
	public GoldTile(EntityType<? extends TileEntity> entityType, World world) {
		super(entityType, world);
	}

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "firetrailcontroller";

	public boolean powered;

	private static final TrackedData<Integer> SUN_SPEED;
	public int sunProducingTime;


	int raycastDelay = (int) (PVZCONFIG.nestedSun.goldtileSec() * 20);

	Entity prevZombie;
	private boolean zombieSunCheck;

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(SUN_SPEED, -1);
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		//Variant//
		if (tag.contains("Fuse", 99)) {
			this.sunProducingTime = tag.getShort("Fuse");
		}
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		//Variant//
		tag.putShort("Fuse", (short) this.sunProducingTime);
	}

	static {
		SUN_SPEED = DataTracker.registerData(GoldTile.class, TrackedDataHandlerRegistry.INTEGER);
	}


	/**
	 * /~*~//~*GECKOLIB ANIMATION*~//~*~/
	 **/

	private <P extends GeoAnimatable> PlayState predicate(AnimationState<P> event) {
		if (powered) {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("tile.active"));
		} else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("tile.anim"));
		}
		return PlayState.CONTINUE;
	}

	@Override
	public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
		controllers.add(new AnimationController<>(this, controllerName, 0, this::predicate));
	}

	@Override
	public AnimatableInstanceCache getAnimatableInstanceCache() {
		return this.factory;
	}

	/**
	 * /~*~//~*TICKING*~//~*~/
	 **/

	private int currentFuseTime;

	public void setFuseSpeed(int fuseSpeed) {
		this.dataTracker.set(SUN_SPEED, fuseSpeed);
	}

	public int getFuseSpeed() {
		return (Integer) this.dataTracker.get(SUN_SPEED);
	}

	public void tick() {
		super.tick();
		List<PlantEntity> list = getWorld().getNonSpectatingEntities(PlantEntity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(this.getX(), this.getY(), this.getZ()));
		this.powered = false;
		for (PlantEntity plantEntity : list) {
			if (!PLANT_LOCATION.get(plantEntity.getType()).orElse("normal").equals("flying")) {
				powered = true;
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
				if (!this.getWorld().isClient && this.isAlive() && this.zombieSunCheck && !this.isInsideWaterOrBubbleColumn()) {
					this.playSound(PvZSounds.SUNDROPEVENT, 0.5F, (this.random.nextFloat() - this.random.nextFloat()) + 0.75F);
					this.dropItem(ModItems.SMALLSUN);
					this.sunProducingTime = (int) (PVZCONFIG.nestedSun.goldtileSec() * 20);
					this.zombieSunCheck = false;
					this.currentFuseTime = this.sunProducingTime;
				}
			}
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && --this.sunProducingTime <= 0 && !this.isInsideWaterOrBubbleColumn() && this.powered && !this.hasStatusEffect(DISABLE)) {
			if (--raycastDelay >= 0) {
				this.produceSun();
				raycastDelay = 60;
			}
		}
	}

	protected void produceSun() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(15));
		List<GeneralPvZombieEntity> zombieList = this.getWorld().getNonSpectatingEntities(GeneralPvZombieEntity.class, this.getBoundingBox().expand(15));
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
			} while (this.squaredDistanceTo(livingEntity) > 100);

			if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
				if (livingEntity.getY() < (this.getY() + 5) && livingEntity.getY() > (this.getY() - 5)) {
					if ((this.prevZombie == null || zombieList.get(0) != prevZombie) && !zombieList.isEmpty()) {
						prevZombie = zombieList.get(0);
						this.zombieSunCheck = true;
					}
				}
			}
		}
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


	@Override
	public double getTick(Object object) {
		return 0;
	}
}
