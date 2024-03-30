package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.fume;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FumeEntityRenderer extends GeoEntityRenderer {

	public FumeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FumeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
