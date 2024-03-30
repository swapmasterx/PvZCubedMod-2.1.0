package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.gloomvine;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GloomVineEntityRenderer extends GeoEntityRenderer<GloomVineEntity> {

    public GloomVineEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GloomVineEntityModel());
        this.shadowRadius = 0.85F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(GloomVineEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 12, 15);
	}

}
