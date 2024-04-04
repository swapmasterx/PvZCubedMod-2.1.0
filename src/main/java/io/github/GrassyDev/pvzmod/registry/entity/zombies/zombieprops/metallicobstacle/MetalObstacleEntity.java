package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.items.ModItems;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.sound.PvZSounds;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import org.jetbrains.annotations.Nullable;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.*;

public class MetalObstacleEntity extends ZombieObstacleEntity implements GeoEntity {
    private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    private String controllerName = "shieldcontroller";

    public MetalObstacleEntity(EntityType<? extends MetalObstacleEntity> entityType, World world) {
        super(entityType, world);

        this.experiencePoints = 3;
	}

	public MetalObstacleEntity(World world) {
		this(PvZEntity.BASKETBALLBIN, world);
	}

	static {

	}

	@Environment(EnvType.CLIENT)
	public void handleStatus(byte status) {
		if (status != 2 && status != 60){
			super.handleStatus(status);
		}
	}

	/** /~*~//~*TICKING*~//~*~/ **/

	private int healTicks = 0;

	public void tick() {
		super.tick();
		if (this.getType().equals(PvZEntity.HEALSTATION)) {
			if (--healTicks <= 0 && !this.hasStatusEffect(DISABLE)) {
				this.healEntity();
				this.healTicks = 20;
				this.removeStatusEffect(ICE);
				this.removeStatusEffect(WARM);
				this.removeStatusEffect(BARK);
				this.removeStatusEffect(CHEESE);
				this.removeStatusEffect(GENERICSLOW);
				this.removeStatusEffect(SHADOW);
				this.removeStatusEffect(STUN);
				this.removeStatusEffect(FROZEN);
				this.removeStatusEffect(PVZPOISON);
				this.removeStatusEffect(MARIGOLD);
				this.extinguish();
				this.addStatusEffect((new StatusEffectInstance(PvZCubed.WET, 100, 1)));
			}
			if (!this.hasStatusEffect(DISABLE)) {
				for (int i = 0; i < 12; ++i) {
					double d = this.random.nextDouble() / 10 * this.random.range(-1, 1);
					double e = this.random.nextDouble() / 1200 * this.random.range(0, 1);
					double f = this.random.nextDouble() / 10 * this.random.range(-1, 1);
					this.getWorld().addParticle(ParticleTypes.DRAGON_BREATH, this.getX(), this.getY() + 0.75, this.getZ(), d, e + 0.1, f);
				}
			}
			else {
				for (int i = 0; i < 2; ++i) {
					double d = this.random.nextDouble() / 4 * this.random.range(-1, 1);
					double e = this.random.nextDouble() / 4 * this.random.range(0, 1);
					double f = this.random.nextDouble() / 4 * this.random.range(-1, 1);
					this.getWorld().addParticle(ParticleTypes.ELECTRIC_SPARK, this.getX() + d, this.getY() + 0.75 + e, this.getZ() + f, d, e + 0.1, f);
				}
			}
		}
		if (this.hasVehicle() && this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && (generalPvZombieEntity.getHealth() <= 0 || generalPvZombieEntity.isDead())){
			this.dismountVehicle();
		}
		if (!this.getHypno()) {
			if (this.CollidesWithPlant(0f, 0f) != null) {
				if (this.CollidesWithPlant(0f, 0f) instanceof SpikerockEntity) {
					if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
						this.CollidesWithPlant(0f, 0f).damage(getDamageSources().mobProjectile(this, this), 90);
						this.kill();
					} else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
						this.CollidesWithPlant(0f, 0f).damage(getDamageSources().mobProjectile(this, this), 90);
						this.kill();
					}
				} else if (this.CollidesWithPlant(0f, 0f) instanceof SpikeweedEntity) {
					if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
						this.CollidesWithPlant(0f, 0f).kill();
						this.kill();
					} else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
						this.CollidesWithPlant(0f, 0f).kill();
						this.kill();
					}
				} else if (this.CollidesWithPlant(0f, 0f) != null && !(this.CollidesWithPlant(0f, 0f) instanceof GravebusterEntity)) {
					if (this.getType().equals(PvZEntity.TRASHCANBIN) && !this.hasVehicle()) {
						this.CollidesWithPlant(0f, 0f).kill();
					} else if (!(this.getType().equals(PvZEntity.TRASHCANBIN))) {
						this.CollidesWithPlant(0f, 0f).kill();
					}
				}
			}
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
		if (beingEaten || (this.getType().equals(PvZEntity.TRASHCANBIN) && (this.hasVehicle() || (this.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.getHealth() <= 0)))){
			event.getController().setAnimation(RawAnimation.begin().thenLoop("obstacle.eating"));
		}
		else if (this.getType().equals(PvZEntity.HEALSTATION)){
			if (this.hasStatusEffect(DISABLE)){
				event.getController().setAnimation(RawAnimation.begin().thenLoop("healstation.disabled"));
			}
			else {
				event.getController().setAnimation(RawAnimation.begin().thenLoop("healstation.idle"));
			}
		}
		else {
			event.getController().setAnimation(RawAnimation.begin().thenLoop("gravestone.idle"));
		}
        return PlayState.CONTINUE;
    }


	/** /~*~//~*ATTRIBUTES*~//~*~/ **/

	public static DefaultAttributeContainer.Builder createBasketBallBinObstacleAttributes() {
        return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.basketballObstH());
    }

	public static DefaultAttributeContainer.Builder createTrashCanBinObstacleAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.trashcanObstH());
	}

	public static DefaultAttributeContainer.Builder createHealStationAttributes() {
		return HostileEntity.createAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 100.0D)
				.add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D)
				.add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0D)
				.add(EntityAttributes.GENERIC_MAX_HEALTH, PVZCONFIG.nestedZombieHealth.healstationObstH());
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ITEM_SHIELD_BREAK;
	}

	protected SoundEvent getAmbientSound() {
		return PvZSounds.SILENCEVENET;
	}

	public EntityGroup getGroup() {
		return EntityGroup.UNDEAD;
	}

	protected SoundEvent getStepSound() {
		return PvZSounds.SILENCEVENET;
	}

	@Nullable
	@Override
	public ItemStack getPickBlockStack() {
		ItemStack itemStack;
		if (this.getType().equals(PvZEntity.BASKETBALLBIN)){
			itemStack = ModItems.BASKETBALLCARRIEREGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.TRASHCANBIN)){
			itemStack = ModItems.TRASHCANEGG.getDefaultStack();
		}
		else if (this.getType().equals(PvZEntity.HEALSTATION)){
			itemStack = ModItems.SCIENTISTEGG.getDefaultStack();
		}
		else{
			itemStack = ModItems.BASKETBALLCARRIEREGG.getDefaultStack();
		}
		return itemStack;
	}

	protected List<LivingEntity> zombieList = new ArrayList<>();
	private void healEntity() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(4));
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
			} while (this.squaredDistanceTo(livingEntity) > 9);

			if (!this.getHypno() && (livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) && !(livingEntity instanceof ZombieShieldEntity) && (!(livingEntity instanceof MachinePvZombieEntity)) && !(IS_MACHINE.get(livingEntity.getType()).orElse(false).equals(true))) {
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
				if (zombiePropEntity2 == null ||
						zombiePropEntity2 instanceof ZombieShieldEntity) {
					float heal = 12;
					livingEntity.heal(heal);
					this.zombieList.add(livingEntity);
				}
			}
			else if (this.getHypno() && (livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& !(generalPvZombieEntity.getHypno()))) && !(livingEntity instanceof ZombieShieldEntity) && (!(livingEntity instanceof MachinePvZombieEntity)) && !(IS_MACHINE.get(livingEntity.getType()).orElse(false).equals(true))) {
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
				if (zombiePropEntity2 == null ||
						zombiePropEntity2 instanceof ZombieShieldEntity) {
					float heal = 12;
					livingEntity.heal(heal);
					this.zombieList.add(livingEntity);
				}
			}
			else {
				this.zombieList.remove(livingEntity);
			}
			if (livingEntity instanceof GeneralPvZombieEntity){
				livingEntity.removeStatusEffect(ICE);
				livingEntity.removeStatusEffect(WARM);
				livingEntity.removeStatusEffect(BARK);
				livingEntity.removeStatusEffect(CHEESE);
				livingEntity.removeStatusEffect(GENERICSLOW);
				livingEntity.removeStatusEffect(SHADOW);
				livingEntity.removeStatusEffect(STUN);
				livingEntity.removeStatusEffect(FROZEN);
				livingEntity.removeStatusEffect(PVZPOISON);
				livingEntity.removeStatusEffect(MARIGOLD);
				livingEntity.extinguish();
				livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.WET, 100, 1)));
			}
		}
	}
}
