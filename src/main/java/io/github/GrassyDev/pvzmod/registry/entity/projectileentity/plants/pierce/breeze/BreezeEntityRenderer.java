package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.breeze;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BreezeEntityRenderer extends GeoEntityRenderer<BreezeEntity> {

	public BreezeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BreezeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
