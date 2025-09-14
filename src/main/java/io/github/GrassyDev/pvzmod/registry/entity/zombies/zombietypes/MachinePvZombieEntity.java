package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombietypes;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoAnimatable;

import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;


public class MachinePvZombieEntity extends GeneralPvZombieEntity{
	protected MachinePvZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
		super(entityType, world);
	}

	@Override
	public void tick() {
		this.setCanBurn(CanBurn.TRUE);
		if (this.hasStatusEffect(PvZCubed.ICE)) {
			this.removeStatusEffect(PvZCubed.ICE);
		}
		if (this.hasStatusEffect(PvZCubed.FROZEN)) {
			this.removeStatusEffect(PvZCubed.FROZEN);
		}
		super.tick();
	}
}
