package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smooshproj;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SmooshProjEntityRenderer extends GeoProjectilesRenderer {

	public SmooshProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SmooshProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
