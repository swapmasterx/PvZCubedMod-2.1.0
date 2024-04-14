package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.peanut;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.pea.ShootingPeaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
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

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class PeanutEntity extends PlantEntity implements GeoEntity, RangedAttackMob {
    private String controllerName = "wallcontroller";

	private boolean isFiring;


	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public PeanutEntity(EntityType<? extends PeanutEntity> entityType, World world) {
        super(entityType, world);

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
	}

	/** /~*~//~*VARIANTS*~//~*~/ **/

	//Health Stage Counter

	public enum Crack {
		NONE(1.0F),
		MEDIUM(0.75F),
		HIGH(0.5F);

		private static final List<PeanutEntity.Crack> VALUES = (List) Stream.of(values()).sorted(Comparator.comparingDouble((crack) -> {
			return (double)crack.maxHealthFraction;
		})).collect(ImmutableList.toImmutableList());
		private final float maxHealthFraction;

		Crack(float maxHealthFraction) {
			this.maxHealthFraction = maxHealthFraction;
		}

		public static PeanutEntity.Crack from(float healthFraction) {
			Iterator var1 = VALUES.iterator();

			PeanutEntity.Crack crack;
			do {
				if (!var1.hasNext()) {
					return NONE;
				}

				crack = (PeanutEntity.Crack)var1.next();
			} while(!(healthFraction < crack.maxHealthFraction));

			return crack;
		}
	}

	public PeanutEntity.Crack getCrack() {
		return PeanutEntity.Crack.from(this.getHealth() / this.getMaxHealth());
	}

	public static final Map<Crack, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(Crack.class), (map) -> {
				map.put(Crack.NONE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/peanut.png"));
				map.put(Crack.MEDIUM,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/peanut_dmg1.png"));
				map.put(Crack.HIGH,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/wallnut/peanut_dmg2.png"));
			});


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
			if (this.getCrack().equals(Crack.HIGH)){
				event.getController().setAnimation(RawAnimation.begin().thenPlay("peanut.shoot2"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenPlay("peanut.shoot"));
			}
		} else {
			if (this.getCrack().equals(Crack.HIGH)){
				event.getController().setAnimation(RawAnimation.begin().thenLoop("peanut.idle2"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("peanut.idle"));
			}
		}
        return PlayState.CONTINUE;
    }



	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new ProjectileAttackGoal(this, 0D, 30, 15.0F));
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




	/** /~*~//~*TICKING*~//~*~/ **/

	public void tick() {
		this.targetZombies(this.getPos(), 5, false, !this.getCrack().equals(Crack.HIGH), false);
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.IMPATYENS_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (!this.getWorld().isClient()) {
			this.FireBeamGoal();
		}
	}

	public void tickMovement() {
		super.tickMovement();
		if (!this.getWorld().isClient && this.isAlive() && this.isInsideWaterOrBubbleColumn() && this.deathTime == 0) {
			this.discard();
		}
	}


	/** /~*~//~*INTERACTION*~//~*~/ **/

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		return ModItems.PEANUT_SEED_PACKET.getDefaultStack();
	}

	public ActionResult interactMob(PlayerEntity player, Hand hand) {
		ItemStack itemStack = player.getStackInHand(hand);
		if (itemStack.isOf(ModItems.GARDENINGGLOVE)) {
			dropItem(ModItems.PEANUT_SEED_PACKET);
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


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createPeanutAttributes() {
        return MobEntity.createAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 90D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 10D);
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


	/** /~*~//~*GOALS*~//~*~/ **/

	int beamTicks;
	int animationTicks;

	boolean charge = false;

	boolean shootSwitch = true;
	boolean shot = false;

	public void FireBeamGoal() {
		LivingEntity livingEntity = this.getTarget();
		++this.beamTicks;
		++this.animationTicks;
		if ((livingEntity != null || animationTicks < 0) && !this.getIsAsleep()) {
			if (this.shootSwitch){
				this.charge = false;
				this.beamTicks = -10;
				this.animationTicks = -30;
				this.getNavigation().stop();
				this.getLookControl().lookAt(this.getTarget(), 90.0F, 90.0F);
				this.velocityDirty = true;
				this.shootSwitch = false;
			}
			this.getNavigation().stop();
			if (livingEntity != null) {
				this.getLookControl().lookAt(livingEntity, 90.0F, 90.0F);
			}
			this.getWorld().sendEntityStatus(this, (byte) 111);
			if (this.animationTicks >= 0) {
				this.getWorld().sendEntityStatus(this, (byte) 110);
				this.beamTicks = -10;
				this.animationTicks = -30;
				if (shot) {
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
				shot = false;
			}
			if (this.beamTicks >= 0 && !this.getCrack().equals(Crack.HIGH)) {
				if (!this.isInsideWaterOrBubbleColumn()) {
					double time = 1;
					Vec3d noZombie = new Vec3d((double) +1, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
					Vec3d noZombie2 = new Vec3d(this.getX() + noZombie.x, this.getY() + noZombie.y, this.getZ() + noZombie.z);
					Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : noZombie2;
					Vec3d predictedPos = (livingEntity != null) ? targetPos.add(livingEntity.getVelocity().multiply(time)) : noZombie2;
					double d = this.squaredDistanceTo(predictedPos);
					float df = (float)d;
					double e = predictedPos.getX() - this.getX();
					double f = 0;
					if (livingEntity != null && livingEntity.isInsideWaterOrBubbleColumn()){
						f = livingEntity.getY() - this.getY() + 0.3595;
					}
					else if (livingEntity != null) {
						f = livingEntity.getY() - this.getY();
					}
					double g = predictedPos.getZ() - this.getZ();
					float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
					ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.getWorld());
					proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.5F, 0F);
					proj.updatePosition(this.getX(), this.getY() + 1.125D, this.getZ());
					proj.setOwner(this);
					proj.canHitFlying = true;
					this.beamTicks = -30;
					this.playSound(PvZSounds.PEASHOOTEVENT, 1F, 1);
					this.getWorld().spawnEntity(proj);
					shot = true;
				}
			}
			if (this.animationTicks == -7 && !this.getCrack().equals(Crack.HIGH)) {
				double time = 1;
				Vec3d noZombie = new Vec3d((double) +1, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				Vec3d noZombie2 = new Vec3d(this.getX() + noZombie.x, this.getY() + noZombie.y, this.getZ() + noZombie.z);
				Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : noZombie2;
				Vec3d predictedPos = (livingEntity != null) ? targetPos.add(livingEntity.getVelocity().multiply(time)) : noZombie2;
				double d = this.squaredDistanceTo(predictedPos);
				float df = (float)d;
				double e = predictedPos.getX() - this.getX();
				double f = 0;
				if (livingEntity != null && livingEntity.isInsideWaterOrBubbleColumn()){
					f = livingEntity.getY() - this.getY() + 0.3595;
				}
				else if (livingEntity != null) {
					f = livingEntity.getY() - this.getY();
				}
				double g = predictedPos.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.getWorld());
				proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.5F, 0F);
				proj.updatePosition(this.getX(), this.getY() + 0.33D, this.getZ());
				proj.setOwner(this);
				proj.lowProf = true;
				this.beamTicks = -30;
				this.playSound(PvZSounds.PEASHOOTEVENT, 1F, 1);
				this.getWorld().spawnEntity(proj);
				shot = true;
			}
			if (this.beamTicks >= 5 && this.getCrack().equals(Crack.HIGH)) {
				double time = 1;
				Vec3d noZombie = new Vec3d((double) +1, 0, 0).rotateY(-this.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
				Vec3d noZombie2 = new Vec3d(this.getX() + noZombie.x, this.getY() + noZombie.y, this.getZ() + noZombie.z);
				Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : noZombie2;
				Vec3d predictedPos = (livingEntity != null) ? targetPos.add(livingEntity.getVelocity().multiply(time)) : noZombie2;
				double d = this.squaredDistanceTo(predictedPos);
				float df = (float)d;
				double e = predictedPos.getX() - this.getX();
				double f = 0;
				if (livingEntity != null && livingEntity.isInsideWaterOrBubbleColumn()){
					f = livingEntity.getY() - this.getY() + 0.3595;
				}
				else if (livingEntity != null) {
					f = livingEntity.getY() - this.getY();
				}
				double g = predictedPos.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				ShootingPeaEntity proj = new ShootingPeaEntity(PvZEntity.PEA, this.getWorld());
				proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.5F, 0F);
				proj.updatePosition(this.getX(), this.getY() + 0.33D, this.getZ());
				proj.setOwner(this);
				proj.lowProf = true;
				this.beamTicks = -30;
				this.playSound(PvZSounds.PEASHOOTEVENT, 1F, 1);
				this.getWorld().spawnEntity(proj);
				shot = true;
			}
		}
		else if (animationTicks >= 0){
			this.shootSwitch = true;
			this.getWorld().sendEntityStatus(this, (byte) 110);
			if (this.getTarget() != null){
				this.attack(this.getTarget(), 0);
			}
			if (shot) {
				this.getWorld().sendEntityStatus(this, (byte) 121);
			}
			shot = false;
		}
	}
}
