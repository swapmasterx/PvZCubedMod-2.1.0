package io.github.GrassyDev.pvzmod.registry.entity.environment.cheesetile;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class CheeseTile extends TileEntity {

	public CheeseTile(EntityType<? extends CheeseTile> entityType, World world) {
		super(entityType, world);

		this.setInvulnerable(true);
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

			if ((livingEntity instanceof Monster &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity &&
							generalPvZombieEntity.isFlying()) && !(livingEntity instanceof GeneralPvZombieEntity zombie && zombie.isHovering())) &&
					!(livingEntity instanceof GeneralPvZombieEntity generalPvZombieEntity
							&& (generalPvZombieEntity.getHypno()))) {
				if (!livingEntity.hasStatusEffect(PvZCubed.WARM) && !livingEntity.isOnFire() && !livingEntity.hasStatusEffect(PvZCubed.FROZEN)){
					livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.CHEESE, 20, 1)));
				}
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.age >= 500){
			this.discard();
		}
		this.damageEntity();
	}
}
