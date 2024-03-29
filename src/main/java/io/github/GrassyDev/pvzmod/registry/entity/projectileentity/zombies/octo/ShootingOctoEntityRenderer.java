package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.octo;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class ShootingOctoEntityRenderer extends GeoProjectilesRenderer {

	public ShootingOctoEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingOctoEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}
}
