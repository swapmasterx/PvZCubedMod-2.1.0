package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pumpkinproj;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingPumpkinEntityRenderer extends GeoEntityRenderer<ShootingPumpkinEntity> {

	public ShootingPumpkinEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPumpkinEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
