package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.bubbles;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BubbleEntityRenderer extends GeoEntityRenderer {

	public BubbleEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BubbleEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
