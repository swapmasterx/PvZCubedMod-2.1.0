package io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.BOUNCED;

public class BananaTile extends TileEntity {

	public BananaTile(EntityType<? extends BananaTile> entityType, World world) {
		super(entityType, world);

	}

	@Override
	protected void applyDamage(DamageSource source, float amount) {
		super.applyDamage(source, 555);
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
			} while (this.squaredDistanceTo(livingEntity) > 1);

			if (((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
							generalPvZombieEntity.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) &&
					!(livingEntity instanceof ZombiePropEntity)) {
				boolean isMachine = PvZCubed.IS_MACHINE.get(livingEntity.getType()).orElse(false);
				if (!isMachine) {
					if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered()){
						livingEntity.setVelocity(0, 0, 0);
					}
					else if (!livingEntity.hasStatusEffect(BOUNCED)) {
						livingEntity.setVelocity(0, 0, 0);
						Vec3d vec3d = new Vec3d((double) 1, 0, 0).rotateY(-livingEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.STUN, 200, 5)));
						livingEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z);
					}
					ZombiePropEntity zombiePropEntity2 = null;
					int damage = 4;
					for (Entity entity1 : livingEntity.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe) {
							if (zombiePropEntity2 == null) {
								zombiePropEntity2 = zpe;
							}
							damage = damage + 8;
							if (zpe.isHeavy) {
								if (zpe instanceof ZombieObstacleEntity zombieObstacle){
									if (!zombieObstacle.dragger){
										damage = damage + 16;
									}
								}
								else {
									damage = damage + 16;
								}
							}
							String helmetMaterial = PvZCubed.ZOMBIE_MATERIAL.get(zpe.getType()).orElse("flesh");
							damage = switch (helmetMaterial) {
								case "plastic" -> damage + 4;
								case "metallic", "electronic" -> damage + 8;
								case "stone" -> damage + 16;
								default -> damage;
							};
						}
					}
					String zombieHeight = PvZCubed.ZOMBIE_SIZE.get(livingEntity.getType()).orElse("medium");
					damage = switch (zombieHeight) {
						case "medium" -> damage + 4;
						case "tall" -> damage + 8;
						case "big" -> damage + 16;
						case "gargantuar" -> damage + 20;
						default -> damage;
					};
					String zombieMaterial = PvZCubed.ZOMBIE_MATERIAL.get(livingEntity.getType()).orElse("flesh");
					damage = switch (zombieMaterial) {
						case "plastic" -> damage + 4;
						case "metallic", "electronic" -> damage + 8;
						case "stone" -> damage + 16;
						case "paper" -> damage - 8;
						case "rubber" -> damage - 16;
						default -> damage;
					};
					if (damage < 8){
						this.playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 0.5f, 1f);
					}
					else if (damage <= 16){
						this.playSound(SoundEvents.ENTITY_GENERIC_SMALL_FALL, 1f, 1f);
					}
					else if (damage <= 32){
						this.playSound(SoundEvents.ENTITY_GENERIC_BIG_FALL, 2f, 1f);
					}
					else {
						this.playSound(SoundEvents.ENTITY_GENERIC_BIG_FALL, 3f, 1f);
					}
					if (damage <= 0){
						damage = 0;
					}
					if (zombiePropEntity2 == null ||
							zombiePropEntity2 instanceof ZombieShieldEntity) {
						livingEntity.damage(DamageSource.thrownProjectile(this, this), damage);
					} else {
						if (damage > zombiePropEntity2.getHealth() &&
								livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && !(generalPvZombieEntity.getHypno())) {
							float damage2 = damage - zombiePropEntity2.getHealth();
							generalPvZombieEntity.damage(DamageSource.thrownProjectile(this, this), damage2);
							zombiePropEntity2.damage(DamageSource.thrownProjectile(this, this), damage);
						} else {
							zombiePropEntity2.damage(DamageSource.thrownProjectile(this, this), damage);
						}
					}
				}
				this.discard();
				break;
			}
		}
	}

	@Override
	public void tick() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof BananaTile && this.squaredDistanceTo(livingEntity) <= 0.5f && livingEntity != this){
				this.discard();
			}
		}
		super.tick();

		if (this.age >= 1200){
			this.discard();
		}
		BlockPos blockPos = this.getBlockPos();
		this.damageEntity();
	}
}
