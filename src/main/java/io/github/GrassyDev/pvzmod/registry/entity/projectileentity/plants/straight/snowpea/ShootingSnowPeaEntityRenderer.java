package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingSnowPeaEntityRenderer extends GeoEntityRenderer<ShootingSnowPeaEntity> {

	public ShootingSnowPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSnowPeaEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
