package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.cheese;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CheeseProjEntityRenderer extends GeoEntityRenderer {

	public CheeseProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CheeseProjEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
