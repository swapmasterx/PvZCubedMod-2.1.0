package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.seapea;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dropea.ShootingDropEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class SeapeaEntity extends PlantEntity implements RangedAttackMob, IAnimatable {
	private String controllerName = "peacontroller";



	public boolean isFiring;

	private AnimationFactory factory = GeckoLibUtil.createFactory(this);

	public SeapeaEntity(EntityType<? extends SeapeaEntity> entityType, World world) {
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


	/** /~*~//~*GECKOLIB ANIMATION*~//~*~/ **/

	@Override
	public void registerControllers(AnimationData data) {
		AnimationController controller = new AnimationController(this, controllerName, 0, this::predicate);

		data.addAnimationController(controller);
	}

	@Override
	public AnimationFactory getFactory() {
		return this.factory;
	}

	private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
		if (this.isFiring) {
			event.getController().setAnimation(new AnimationBuilder().playOnce("peashooter.shoot2"));
		} else {
			event.getController().setAnimation(new AnimationBuilder().loop("peashooter.idle"));
		}
		return PlayState.CONTINUE;
	}


	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
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

	public void tick() {
		super.tick();
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(world, this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.SEAPEA_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (!this.getWorld().isClient()) {
			this.FireBeamGoal();
		}
		this.targetZombies(this.getPos(), 7, false, false, false);
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
			dropItem(ModItems.SEAPEA_SEED_PACKET);
			if (!player.getAbilities().creativeMode) {
				if (!PVZCONFIG.nestedSeeds.infiniteSeeds() && !world.getGameRules().getBoolean(PvZCubed.INFINITE_SEEDS)) {
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
		return ModItems.SEAPEA_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createSeapeaAttributes() {
		return MobEntity.createMobAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 24.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15D);
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
				this.beamTicks = -13;
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
				this.beamTicks = -13;
				this.animationTicks = -30;
				if (shot) {
					this.getWorld().sendEntityStatus(this, (byte) 121);
				}
				shot = false;
			}
			if (this.beamTicks >= 0) {
				double time = (livingEntity != null) ? ((this.squaredDistanceTo(livingEntity) > 36) ? 50 : 1) : 1;
				Vec3d targetPos = (livingEntity != null) ? livingEntity.getPos() : this.getPos();
				double predictedPosX = (livingEntity != null) ? targetPos.getX() + (livingEntity.getVelocity().x * time) : this.getX();
				double predictedPosZ = (livingEntity != null) ? targetPos.getZ() + (livingEntity.getVelocity().z * time) : this.getZ();
				Vec3d predictedPos = new Vec3d(predictedPosX, targetPos.getY(), predictedPosZ);
				double d = this.squaredDistanceTo(predictedPos);
				float df = (float)d;
				double e = predictedPos.getX() - this.getX();
				double f = (livingEntity != null) ? ((livingEntity.isInsideWaterOrBubbleColumn()) ? livingEntity.getY() - this.getY() + 0.3595 : livingEntity.getY() - this.getY()) : 0;
				double g = predictedPos.getZ() - this.getZ();
				float h = MathHelper.sqrt(MathHelper.sqrt(df)) * 0.5F;
				ShootingDropEntity proj = new ShootingDropEntity(PvZEntity.DROP, this.getWorld());
				proj.setVelocity(e * (double) h, f * (double) h, g * (double) h, 0.33F, 0F);
				proj.updatePosition(this.getX(), this.getY() + 0.75D, this.getZ());
				proj.setOwner(this);
				proj.damageMultiplier = damageMultiplier;
				if (shot){
					this.beamTicks = -30;
				}
				else {
					this.beamTicks = -4;
				}
				shot = true;
				this.playSound(PvZSounds.PEASHOOTEVENT, 1F, 1);
				this.getWorld().spawnEntity(proj);
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
