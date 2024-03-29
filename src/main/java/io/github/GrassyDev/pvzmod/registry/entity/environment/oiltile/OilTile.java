package io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.FireTrailEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pepper.ShootingPepperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pumpkinproj.ShootingPumpkinEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.FirePiercePeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.flamingpea.ShootingFlamingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.plasmapea.ShootingPlasmaPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombiePropEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.BOUNCED;
import static io.github.GrassyDev.pvzmod.PvZCubed.STUN;

public class OilTile extends TileEntity {

	public OilTile(EntityType<? extends OilTile> entityType, World world) {
		super(entityType, world);

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
			} while (this.squaredDistanceTo(livingEntity) > 1);

			if (!onFire && livingEntity.isOnGround() && ((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
							generalPvZombieEntity.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) &&
					!(livingEntity instanceof ZombiePropEntity)) {
				boolean isMachine = PvZCubed.IS_MACHINE.get(livingEntity.getType()).orElse(false);
				if (!isMachine && !(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.isCovered())) {
					if (livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity && generalPvZombieEntity.canSlide && !livingEntity.hasStatusEffect(BOUNCED)) {
						Vec3d vec3d = new Vec3d((double) 1, -0.1, 0).rotateY(-livingEntity.getHeadYaw() * (float) (Math.PI / 180.0) - ((float) (Math.PI / 2)));
						livingEntity.setVelocity(0, 0, 0);

						if (!generalPvZombieEntity.hasStatusEffect(STUN) && generalPvZombieEntity.oilTicks <= 0) {
							generalPvZombieEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.STUN, 100, 5)));
							generalPvZombieEntity.oilTicks = 300;
						}
						livingEntity.setVelocity(vec3d.x, vec3d.y, vec3d.z);
					}
					ZombiePropEntity zombiePropEntity2 = null;
					int damage = 0;
					for (Entity entity1 : livingEntity.getPassengerList()) {
						if (entity1 instanceof ZombiePropEntity zpe) {
							zombiePropEntity2 = zpe;
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
					damage = 0;
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
			}
		}
	}

	private void setOnFire() {
		List<Entity> list = this.getWorld().getNonSpectatingEntities(Entity.class, this.getBoundingBox().expand(3));
		Iterator var9 = list.iterator();
		while (true) {
			Entity entity;
			do {
				if (!var9.hasNext()) {
					return;
				}
				entity = (Entity) var9.next();
			} while (this.squaredDistanceTo(entity) > 4);

			if (!this.isWet() && (entity.isOnFire() ||
					entity instanceof ShootingFlamingPeaEntity ||
					entity instanceof ShootingPlasmaPeaEntity ||
					entity instanceof ShootingPepperEntity ||
					entity instanceof FirePiercePeaEntity ||
					entity instanceof ShootingPumpkinEntity)){
				makeFireTrail(this.getBlockPos());
			}
		}
	}

	public void makeFireTrail(BlockPos blockPos){
		boolean bl = true;
		if (this.getWorld() instanceof ServerWorld serverWorld) {
			List<Entity> tileCheck = world.getNonSpectatingEntities(Entity.class, PvZEntity.PEASHOOTER.getDimensions().getBoxAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
			for (Entity tile : tileCheck){
				if (tile instanceof FireTrailEntity && tile.squaredDistanceTo(Vec3d.ofCenter(blockPos)) <= 0.5f) {
					bl = false;
				}
			}
			if (bl) {
				FireTrailEntity tile = (FireTrailEntity) PvZEntity.FIRETRAIL.create(world);
				tile.refreshPositionAndAngles(blockPos.getX(), blockPos.getY() + 0.5f, blockPos.getZ(), 0, 0);
				tile.initialize(serverWorld, world.getLocalDifficulty(blockPos), SpawnReason.SPAWN_EGG, (EntityData) null, (NbtCompound) null);
				tile.setHeadYaw(0);
				serverWorld.spawnEntityAndPassengers(tile);
				tile.ageMax = 300;
			}
		}
	}

	private boolean onFire;

	@Override
	public void tick() {
		List<LivingEntity> list = this.getWorld().getNonSpectatingEntities(LivingEntity.class, this.getBoundingBox().expand(1));
		onFire = false;
		for (LivingEntity livingEntity : list){
			if (livingEntity instanceof FireTrailEntity && this.squaredDistanceTo(livingEntity) <= 0.5f){
				onFire = true;
			}
			if (livingEntity instanceof OilTile && this.squaredDistanceTo(livingEntity) <= 0.5f && livingEntity != this){
				this.discard();
			}
		}
		super.tick();

		if (this.age >= 1200){
			this.discard();
		}
		BlockPos blockPos = this.getBlockPos();
		this.damageEntity();
		this.setOnFire();
	}
}
