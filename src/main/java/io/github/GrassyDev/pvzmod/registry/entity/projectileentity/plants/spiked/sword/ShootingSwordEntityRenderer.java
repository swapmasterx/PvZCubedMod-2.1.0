package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingSwordEntityRenderer extends GeoEntityRenderer<ShootingSwordEntity> {

	public ShootingSwordEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSwordEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
