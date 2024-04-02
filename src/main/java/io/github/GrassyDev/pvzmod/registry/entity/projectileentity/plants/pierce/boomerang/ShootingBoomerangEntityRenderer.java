package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.boomerang;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingBoomerangEntityRenderer extends GeoEntityRenderer <ShootingBoomerangEntity>{

	public ShootingBoomerangEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingBoomerangEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}

}
