package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.zpg;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ZPGEntityRenderer extends GeoProjectilesRenderer {

	public ZPGEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ZPGEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
