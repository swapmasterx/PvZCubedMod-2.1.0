package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.peanut;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class PeanutBowlingEntityRenderer extends GeoProjectilesRenderer {

	public PeanutBowlingEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeanutBowlingEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}

}
