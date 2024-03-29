package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.wallnut;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class WallnutBowlingEntityRenderer extends GeoProjectilesRenderer {

	public WallnutBowlingEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new WallnutBowlingEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}

}
