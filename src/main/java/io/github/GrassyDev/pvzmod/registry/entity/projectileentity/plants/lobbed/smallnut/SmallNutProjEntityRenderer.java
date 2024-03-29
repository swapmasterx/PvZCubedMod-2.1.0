package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smallnut;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class SmallNutProjEntityRenderer extends GeoProjectilesRenderer {

	public SmallNutProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SmallNutProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
