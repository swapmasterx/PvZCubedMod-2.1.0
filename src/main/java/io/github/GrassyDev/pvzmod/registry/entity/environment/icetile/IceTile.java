package io.github.GrassyDev.pvzmod.registry.entity.environment.icetile;

import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.GeneralPvZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes.ZombieShieldEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.Monster;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class IceTile extends TileEntity {

	public IceTile(EntityType<? extends IceTile> entityType, World world) {
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
					if (!(livingEntity instanceof ZombieShieldEntity)) {
						livingEntity.addStatusEffect((new StatusEffectInstance(PvZCubed.ICE, 120, 1)));
					}
				}
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.age >= 600){
			this.discard();
		}
		this.damageEntity();
	}

	@Override
	public double getTick(Object object) {
		return 0;
	}
}
