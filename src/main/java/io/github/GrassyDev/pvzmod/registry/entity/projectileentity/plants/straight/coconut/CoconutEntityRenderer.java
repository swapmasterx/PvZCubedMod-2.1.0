package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.coconut;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CoconutEntityRenderer extends GeoEntityRenderer {

	public CoconutEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CoconutEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}
}
