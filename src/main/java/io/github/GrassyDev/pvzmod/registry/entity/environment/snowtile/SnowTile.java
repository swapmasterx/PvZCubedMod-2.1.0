package io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.TileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;


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
}
