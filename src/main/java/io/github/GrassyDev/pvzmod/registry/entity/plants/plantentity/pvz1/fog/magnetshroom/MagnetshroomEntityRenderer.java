package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.fog.magnetshroom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class MagnetshroomEntityRenderer extends GeoEntityRenderer<MagnetshroomEntity> {

    public MagnetshroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MagnetshroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(MagnetshroomEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 7, 15);
	}
}
