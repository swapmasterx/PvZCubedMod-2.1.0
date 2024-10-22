package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SunshroomEntityRenderer extends GeoEntityRenderer<SunshroomEntity> {

    public SunshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SunshroomEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(SunshroomEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 7, 15);
	}

}
