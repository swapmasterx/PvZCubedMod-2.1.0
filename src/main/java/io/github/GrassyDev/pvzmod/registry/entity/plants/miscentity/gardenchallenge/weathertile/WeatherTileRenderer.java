package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.weathertile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class WeatherTileRenderer extends GeoEntityRenderer<WeatherTile> {

    public WeatherTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new WeatherTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

	protected int getBlockLight(WeatherTile tile, BlockPos blockPos) {
		return 15;
	}

}
