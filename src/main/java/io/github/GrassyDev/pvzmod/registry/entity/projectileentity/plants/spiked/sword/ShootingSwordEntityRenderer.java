package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingSwordEntityRenderer extends GeoProjectilesRenderer {

	public ShootingSwordEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSwordEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
