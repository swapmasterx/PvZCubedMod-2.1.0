package io.github.GrassyDev.pvzmod.registry.entity.environment.rifttile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class RiftTileRenderer extends GeoEntityRenderer<RiftTile> {

    public RiftTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RiftTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(RiftTile tile, BlockPos blockPos) {
		return 15;
	}

}
