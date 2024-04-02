package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dropea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingDropEntityRenderer extends GeoEntityRenderer<ShootingDropEntity> {

	public ShootingDropEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingDropEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
