package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.springproj;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SpringProjEntityRenderer extends GeoEntityRenderer {

	public SpringProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SpringProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
