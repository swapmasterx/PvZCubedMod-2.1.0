package io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class LocustswarmEntityRenderer extends GeoEntityRenderer {

	public LocustswarmEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LocustswarmEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}
}
