package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.laser;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class LaserRenderer extends GeoProjectilesRenderer {

	public LaserRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LaserModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(LaserEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
