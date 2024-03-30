package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.iceberg;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingIcebergEntityRenderer extends GeoEntityRenderer {

	public ShootingIcebergEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingIcebergEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
