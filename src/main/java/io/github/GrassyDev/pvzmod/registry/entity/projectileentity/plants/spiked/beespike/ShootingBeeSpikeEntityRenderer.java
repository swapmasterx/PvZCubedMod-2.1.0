package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingBeeSpikeEntityRenderer extends GeoEntityRenderer {

	public ShootingBeeSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingBeeSpikeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
