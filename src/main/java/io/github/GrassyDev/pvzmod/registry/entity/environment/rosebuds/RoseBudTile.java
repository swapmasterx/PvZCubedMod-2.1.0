package io.github.GrassyDev.pvzmod.registry.entity.environment.rosebuds;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZSounds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.Monster;
import net.minecraft.sound.SoundEvent;
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

public class RoseBudTile extends TileEntity {

	private AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
	private String controllerName = "firetrailcontroller";

	public RoseBudTile(EntityType<? extends RoseBudTile> entityType, World world) {
		super(entityType, world);

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
		event.getController().setAnimation(RawAnimation.begin().thenLoop("doomrose.hands"));
		return PlayState.CONTINUE;
	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {

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
			} while (this.squaredDistanceTo(livingEntity) > 1.5625);

			String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
			boolean hasHelmet = false;
			ZombiePropEntity zombiePropEntity2 = null;
			ZombiePropEntity zombiePropEntity3 = null;
			for (Entity entity1 : livingEntity.getPassengerList()) {
				if (entity1 instanceof ZombiePropEntity zpe && zombiePropEntity2 == null) {
					zombiePropEntity2 = zpe;
				}
				else if (entity1 instanceof ZombiePropEntity zpe) {
					zombiePropEntity3 = zpe;
				}
				if (entity1 instanceof ZombiePropEntity zpe && !(zpe instanceof ZombieShieldEntity)) {
					hasHelmet = true;
				}
			}
			float damage = 6 * damageMultiplier;
			if (!hasHelmet && !(livingEntity instanceof ZombiePropEntity) && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
				damage = damage * 2;
			}
			if ("crystal".equals(zombieMaterial)) {
				damage = damage * 2;
			}
			if ((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity1 &&
							generalPvZombieEntity1.isFlying())) && !livingEntity.isInsideWaterOrBubbleColumn() && !(livingEntity instanceof ZombieShieldEntity && !(livingEntity instanceof ZombieObstacleEntity))) {
				if (zombiePropEntity2 == null ||
						zombiePropEntity2 instanceof ZombieShieldEntity) {
					SoundEvent sound;
					sound = switch (zombieMaterial) {
						case "metallic", "electronic" -> PvZSounds.BUCKETHITEVENT;
						case "plastic" -> PvZSounds.CONEHITEVENT;
						case "stone", "crystal" -> PvZSounds.STONEHITEVENT;
						default -> PvZSounds.PEAHITEVENT;
					};
					livingEntity.playSound(sound, 0.1F, (float) (0.5F + Math.random()));
					if (damage > livingEntity.getHealth() &&
							!(livingEntity instanceof ZombieShieldEntity) &&
							livingEntity.getVehicle() instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
						float damage2 = damage - livingEntity.getHealth();
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
						generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
					} else {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
					}
				}
			}
		}
	}
	private int tickDamage = 15;

	@Override
	public void tick() {
		super.tick();
		if (this.getTarget() != null){
			this.setPosition(this.getTarget().getPos());
		}
		if (this.age >= 40){
			this.discard();
		}
		if (--tickDamage <= 0){
			this.damageEntity();
			tickDamage = 15;
		}
	}
<<<<<<< Updated upstream
=======

	@Override
	public double getTick(Object object) {
		return 0;
	}
>>>>>>> Stashed changes
}
