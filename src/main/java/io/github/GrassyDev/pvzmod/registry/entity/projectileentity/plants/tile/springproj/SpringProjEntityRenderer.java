package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.springproj;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SpringProjEntityRenderer extends GeoProjectilesRenderer {

	public SpringProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SpringProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
