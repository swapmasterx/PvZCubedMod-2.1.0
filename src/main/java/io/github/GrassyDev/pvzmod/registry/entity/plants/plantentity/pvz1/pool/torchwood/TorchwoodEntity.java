package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.config.ModItems;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.PlantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.PVZCONFIG;

public class TorchwoodEntity extends PlantEntity implements GeoEntity {
	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "torchcontroller";

	public TorchwoodEntity(EntityType<? extends TorchwoodEntity> entityType, World world) {
        super(entityType, world);
		this.setFireImmune(FireImmune.TRUE);

		this.illuminate = true;
		this.isBurst = true;
    }

	static {
	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
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
		event.getController().setAnimation(RawAnimation.begin().thenLoop("torchwood.idle"));
		return PlayState.CONTINUE;
	}

	/** /~*~//~*AI*~//~*~/ **/

	protected void initGoals() {
		this.goalSelector.add(1, new LookAtEntityGoal(this, GeneralPvZombieEntity.class, 15F));
	}

	private void damageEntity() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
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
			} while (this.squaredDistanceTo(livingEntity) > 4);
			if (livingEntity instanceof OilTile oilTile){
				oilTile.makeFireTrail(oilTile.getBlockPos());
			}

			if (((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && (!livingEntity.isWet() && !livingEntity.hasStatusEffect(PvZCubed.WET))) && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
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
				float damage = 4 * damageMultiplier;
				if (zombiePropEntity2 == null ||
						zombiePropEntity2 instanceof ZombieShieldEntity) {
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
					if ("paper".equals(zombieMaterial) || "plant".equals(zombieMaterial) || "cloth".equals(zombieMaterial) || "gold".equals(zombieMaterial)) {
						damage = damage * 2;
					}
					if ("rubber".equals(zombieMaterial) || "crystal".equals(zombieMaterial)){
						damage = damage / 2;
					}
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), (float) (damage*0.5));
						livingEntity.damage(getDamageSources().mobProjectile(this, this), (float) (damage*0.5));
						generalPvZombieEntity.damage(getDamageSources().mobProjectile(this, this), damage2);
					} else {
						livingEntity.damage(PvZDamageTypes.of(getWorld(), PvZDamageTypes.GENERIC_ANTI_IFRAME), (float) (damage*0.5));
						livingEntity.damage(getDamageSources().mobProjectile(this, this), (float) (damage*0.5));
					}
					if (!(livingEntity instanceof ZombieShieldEntity) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet() && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn())) {
						livingEntity.removeStatusEffect(PvZCubed.FROZEN);
						livingEntity.removeStatusEffect(PvZCubed.ICE);
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
						livingEntity.setOnFireFor(4);
					}
				}
			}
			else if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !generalPvZombieEntity.canBurn() && !(generalPvZombieEntity instanceof ZombieShieldEntity) && !livingEntity.hasStatusEffect(PvZCubed.WET) && !livingEntity.isWet()){
				livingEntity.removeStatusEffect(PvZCubed.FROZEN);
				livingEntity.removeStatusEffect(PvZCubed.ICE);
				livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WARM, 60, 1)));
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


	/** /~*~//~*TICKING*~//~*~/ **/
	private int tickDamage = 10;

	public void tick() {
		super.tick();
		targetZombies(this.getPos(), 4, true, false, true);
		BlockPos blockPos = this.getBlockPos();
		if (tickDelay <= 1) {
			BlockPos blockPos2 = this.getBlockPos();
			BlockState blockState = this.getLandingBlockState();
			if ((!blockPos2.equals(blockPos) || !blockState.hasSolidTopSurface(getWorld(), this.getBlockPos(), this)) && !this.hasVehicle()) {
				if (!this.getWorld().isClient && this.getWorld().getGameRules().getBooleanValue(GameRules.DO_MOB_LOOT) && !this.naturalSpawn && this.age <= 10 && !this.dead){
					this.dropItem(ModItems.TORCHWOOD_SEED_PACKET);
				}
				this.discard();
			}
		}
		if (!this.isWet()){
			if (--tickDamage <= 0){
				if (!this.hasStatusEffect(PvZCubed.DISABLE)) {
					this.damageEntity();
				}
				tickDamage = 10;
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
			dropItem(ModItems.TORCHWOOD_SEED_PACKET);
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
		return ModItems.TORCHWOOD_SEED_PACKET.getDefaultStack();
	}


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createTorchwoodAttributes() {
		return MobEntity.createAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 65.0D)
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


	public boolean isOnFire() {
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
