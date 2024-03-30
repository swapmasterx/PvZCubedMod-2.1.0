package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.magnet;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class MagnetoShroomEntityRenderer extends GeoEntityRenderer<MagnetoShroomEntity> {

    public MagnetoShroomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MagnetoShroomEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(MagnetoShroomEntity plantEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(plantEntity, blockPos) + 10, 15);
	}
}
