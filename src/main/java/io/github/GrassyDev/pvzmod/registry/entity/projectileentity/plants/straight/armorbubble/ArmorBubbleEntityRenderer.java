package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.armorbubble;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ArmorBubbleEntityRenderer extends GeoEntityRenderer {

	public ArmorBubbleEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ArmorBubbleEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
