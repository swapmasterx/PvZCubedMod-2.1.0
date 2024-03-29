package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.timetile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class TimeTileRenderer extends GeoEntityRenderer<TimeTile> {

    public TimeTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new TimeTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(TimeTile tile, BlockPos blockPos) {
		return 15;
	}

}
