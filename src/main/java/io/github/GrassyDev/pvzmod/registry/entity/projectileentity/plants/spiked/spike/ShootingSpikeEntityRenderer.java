package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.spike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingSpikeEntityRenderer extends GeoEntityRenderer <ShootingSpikeEntity>{

	public ShootingSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSpikeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
