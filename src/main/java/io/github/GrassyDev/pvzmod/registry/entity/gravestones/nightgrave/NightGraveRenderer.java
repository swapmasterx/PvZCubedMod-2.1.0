package io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class NightGraveRenderer extends GeoEntityRenderer<NightGraveEntity> {

    public NightGraveRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new NightGraveModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(NightGraveEntity graveEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(graveEntity, blockPos) + 6, 15);
	}

}
