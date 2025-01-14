package io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BasicGraveRenderer extends GeoEntityRenderer<BasicGraveEntity> {

    public BasicGraveRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BasicGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(BasicGraveEntity graveEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(graveEntity, blockPos) + 6, 15);
	}

}
