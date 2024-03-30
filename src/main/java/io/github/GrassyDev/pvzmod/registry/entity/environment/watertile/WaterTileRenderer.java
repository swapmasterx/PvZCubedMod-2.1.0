package io.github.GrassyDev.pvzmod.registry.entity.environment.watertile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class WaterTileRenderer extends GeoEntityRenderer<WaterTile> {

    public WaterTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new WaterTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }
}
