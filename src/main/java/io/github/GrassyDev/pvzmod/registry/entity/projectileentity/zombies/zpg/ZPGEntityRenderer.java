package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.zpg;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ZPGEntityRenderer extends GeoEntityRenderer {

	public ZPGEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ZPGEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
