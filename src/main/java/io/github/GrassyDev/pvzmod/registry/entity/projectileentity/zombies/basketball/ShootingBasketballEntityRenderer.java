package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.basketball;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingBasketballEntityRenderer extends GeoEntityRenderer {

	public ShootingBasketballEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingBasketballEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}
}
