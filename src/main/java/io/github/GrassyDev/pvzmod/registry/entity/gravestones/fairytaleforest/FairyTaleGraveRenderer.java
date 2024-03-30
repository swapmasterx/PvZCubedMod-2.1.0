package io.github.GrassyDev.pvzmod.registry.entity.gravestones.fairytaleforest;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FairyTaleGraveRenderer extends GeoEntityRenderer<FairyTaleGraveEntity> {

    public FairyTaleGraveRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FairyTaleGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(FairyTaleGraveEntity graveEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(graveEntity, blockPos) + 12, 15);
	}

}
