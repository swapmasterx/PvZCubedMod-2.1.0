package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.octo;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingOctoEntityRenderer extends GeoEntityRenderer {

	public ShootingOctoEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingOctoEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}
}
