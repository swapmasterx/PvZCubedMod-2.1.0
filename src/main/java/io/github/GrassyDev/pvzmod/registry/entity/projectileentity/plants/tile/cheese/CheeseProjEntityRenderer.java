package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.cheese;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class CheeseProjEntityRenderer extends GeoProjectilesRenderer {

	public CheeseProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CheeseProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
