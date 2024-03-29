package io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class ShadowTileRenderer extends GeoEntityRenderer<ShadowTile> {

    public ShadowTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShadowTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(ShadowTile tile, BlockPos blockPos) {
		return 15;
	}

}
