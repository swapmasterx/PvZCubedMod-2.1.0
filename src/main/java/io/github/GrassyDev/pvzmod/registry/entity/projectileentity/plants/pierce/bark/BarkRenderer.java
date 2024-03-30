package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.bark;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BarkRenderer extends GeoEntityRenderer {

	public BarkRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BarkModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
}
