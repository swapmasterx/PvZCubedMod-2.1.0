package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.springbean;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SpringbeanEntity extends PlantEntity implements GeoEntity, RangedAttackMob {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

	private String controllerName = "springcontroller";

	private boolean isFiring;

	private int bounceAnimation;

	private boolean isAfraid;

	private int animationScare;
	private int asleepTicks;

	private boolean onlyFlying;

    public SpringbeanEntity(EntityType<? extends SpringbeanEntity> entityType, World world) {
        super(entityType, world);


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
		}
		if (status == 111) {
			this.isFiring = true;
		} else if (status == 110) {
			this.isFiring = false;
		}
		if (status == 112) {
			this.bounced = true;
		} else if (status == 113) {
			this.bounced = false;
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
			event.getController().setAnimation(RawAnimation.begin().thenPlay("springbean.spring.attack"));
		}
		else if (this.getIsAsleep()){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("springbean.idle.asleep"));
		}
		else if (this.animationScare > 0 && this.isAfraid){
			event.getController().setAnimation(RawAnimation.begin().thenPlay("springbean.prepare"));
		}
		else if (this.isAfraid){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("springbean.spring.idle"));
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("springbean.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
	}


	@Override
	public void attack(LivingEntity target, float pullProgress) {
	}


	protected List<HostileEntity> checkForZombies(float distance) {
		this.onlyFlying = true;
		List<HostileEntity> list = this.getWorld().getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(3));
		List<HostileEntity> list2 = new ArrayList<>();
		List<GargantuarEntity> list3 = this.getWorld().getNonSpectatingEntities(GargantuarEntity.class, this.getBoundingBox().expand(1));
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			if (!var9.hasNext()) {
				return list2;
			}
			hostileEntity = (HostileEntity) var9.next();

			if (!(hostileEntity instanceof ZombiePropEntity) && !(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
				if (this.squaredDistanceTo(hostileEntity) < distance || !list3.isEmpty()) {
					if (hostileEntity.getY() < (this.getY() + 2) && hostileEntity.getY() > (this.getY() - 2)) {
						list2.add(hostileEntity);
						if (hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && (!generalPvZombieEntity.isFlying() && !generalPvZombieEntity.isHovering())) {
							this.onlyFlying = false;
						}
						return list2;
					}
				}
			}
		}
	}


	protected void bounceZombies() {
		List<HostileEntity> list = this.getWorld().getNonSpectatingEntities(HostileEntity.class, this.getBoundingBox().expand(3));
		Iterator var9 = list.iterator();
		while (true) {
			HostileEntity hostileEntity;
			do {
				if (!var9.hasNext()) {
					return;
				}
				hostileEntity = (HostileEntity) var9.next();
			} while (this.squaredDistanceTo(hostileEntity) > 3.0625);

			if (!(hostileEntity instanceof ZombiePropEntity) && !(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHypno())) {
				if (hostileEntity.getY() < (this.getY() + 2) && hostileEntity.getY() > (this.getY() - 2) &&
						!(hostileEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isFlying())) {
					Vec3d vec3d = new Vec3d((double) -1, +0.5, 0).rotateY(-hostileEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					hostileEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.BOUNCED, 20, 1)));
					hostileEntity.setVelocity(Vec3d.ZERO);
					hostileEntity.addVelocity(vec3d.getX(), vec3d.getY(), vec3d.getZ());
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

	private boolean bounced;

	/** /~*~//~**TICKING**~//~*~/ **/

	public void tick() {
		if (this.getVehicle() instanceof LilyPadEntity ||
				this.getVehicle() instanceof BubblePadEntity){
			this.discard();
		}
		if (this.animationScare > 0 && this.isAfraid) {
			--this.animationScare;
		}
		if (!this.checkForZombies(0.04f).isEmpty() && !this.onlyFlying && !this.getIsAsleep()){
			this.animationScare = 0;
		}
		super.tick();
		if (!this.checkForZombies(6.25f).isEmpty() && !this.bounced && !this.getIsAsleep()){
			this.isAfraid = true;
			this.getWorld().sendEntityStatus(this, (byte) 104);
			this.setLowprof(LowProf.TRUE);
		}
		else if (!this.getIsAsleep()) {
			this.isAfraid = false;
			this.animationScare = 30;
			this.getWorld().sendEntityStatus(this, (byte) 14);
			if (this.bounceAnimation <= 0) {
				this.setLowprof(LowProf.FALSE);
			}
		}
		if (!this.getIsAsleep()){
			this.targetZombies(this.getPos(), 2, false, true, true);
		}
		if (!this.checkForZombies(0.2025f).isEmpty() && !this.isFiring && !this.onlyFlying && !this.getIsAsleep()) {
			this.bounceAnimation = 28;
		}
		--bounceAnimation;
		if (animationScare <= 0 && this.isAfraid && !this.getIsAsleep()){
			if (!this.checkForZombies(0.2025f).isEmpty() && !this.onlyFlying){
				this.getWorld().sendEntityStatus(this, (byte) 111);
				this.isFiring = true;
			}
			if (bounceAnimation == 24) {
				this.bounceZombies();
				this.playSound(PvZSounds.POLEVAULTEVENT, 0.5f, 1.5f);
				this.isAfraid = false;
				this.animationScare = 30;
				this.getWorld().sendEntityStatus(this, (byte) 14);
				this.getWorld().sendEntityStatus(this, (byte) 112);
				this.bounced = true;
			}
		}
		else if (bounced && this.bounceAnimation <= 0 && !this.getIsAsleep()) {
			this.setLowprof(LowProf.FALSE);
			this.bounced = false;
			this.setIsAsleep(IsAsleep.TRUE);
			this.getWorld().sendEntityStatus(this, (byte) 110);
			this.getWorld().sendEntityStatus(this, (byte) 113);
		}
		if (this.getIsAsleep()){
			--this.asleepTicks;
			this.setLowprof(LowProf.FALSE);
		}
		else {
			this.asleepTicks = 400;
		}
		if (asleepTicks <= 0 && this.getIsAsleep()){
			this.isFiring = false;
			this.isAfraid = false;
			this.animationScare = 30;
			this.getWorld().sendEntityStatus(this, (byte) 14);
			this.bounced = false;
			this.getWorld().sendEntityStatus(this, (byte) 110);
			this.getWorld().sendEntityStatus(this, (byte) 113);
			this.setIsAsleep(IsAsleep.FALSE);
		}
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.SPRINGBEAN_SEED_PACKET);
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
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.SPRINGBEAN_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !getWorld().getGameRules().getBooleanValue(PvZCubed.INFINITE_SEEDS)) {
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
		return ModItems.SPRINGBEAN_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createSpringBeanAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 10.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10.0D);
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
}
