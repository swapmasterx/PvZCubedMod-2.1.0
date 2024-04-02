package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spore;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SporeEntityRenderer extends GeoEntityRenderer<SporeEntity> {

	public SporeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SporeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
//		this.widthScale = 0.25F;
//		this.heightScale = 0.25F;
	}

}
