package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.peanut;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class PeaNutProjEntityRenderer extends GeoProjectilesRenderer {

	public PeaNutProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeaNutProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
