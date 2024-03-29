package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.ghostpepper;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GhostPepperEntityRenderer extends GeoEntityRenderer<GhostpepperEntity> {

    public GhostPepperEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GhostpepperEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(GhostpepperEntity plantEntity, BlockPos blockPos) {
		if (plantEntity.getShadowPowered() || plantEntity.getMoonPowered()){
			return 15;
		}
		else {
			return Math.min(super.getBlockLight(plantEntity, blockPos) + 6, 15);
		}
	}
}
