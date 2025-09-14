package io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib.animatable.GeoAnimatable;

import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;


public class SnowTile extends TileEntity {

	public SnowTile(EntityType<? extends SnowTile> entityType, World world) {
		super(entityType, world);

		this.setInvulnerable(true);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.age >= 2400){
			this.discard();
		}
	}

	@Override
	public double getTick(Object object) {
		return 0;
	}
}
