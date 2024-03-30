package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.banana;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BananaProjEntityRenderer extends GeoEntityRenderer<BananaProjEntity> {

	public BananaProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BananaProjEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
}
