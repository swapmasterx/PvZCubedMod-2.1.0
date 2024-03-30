package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowqueenpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingSnowqueenPeaEntityRenderer extends GeoEntityRenderer {

	public ShootingSnowqueenPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSnowqueenPeaEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
