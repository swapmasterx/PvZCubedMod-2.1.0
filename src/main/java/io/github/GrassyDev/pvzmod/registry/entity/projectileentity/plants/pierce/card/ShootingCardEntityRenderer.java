package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.card;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingCardEntityRenderer extends GeoEntityRenderer {

	public ShootingCardEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingCardEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}

}