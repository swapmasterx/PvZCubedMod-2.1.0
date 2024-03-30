package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercespore;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class PierceSporeEntityRenderer extends GeoEntityRenderer {

	public PierceSporeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PierceSporeEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
	}
}
