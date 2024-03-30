package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GambleshroomEntityRenderer extends GeoEntityRenderer<GambleshroomEntity> {

    public GambleshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GambleshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(GambleshroomEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 10, 15);
	}
}
