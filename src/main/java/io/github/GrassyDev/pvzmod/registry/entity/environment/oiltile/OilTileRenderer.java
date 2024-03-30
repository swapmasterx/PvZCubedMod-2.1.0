package io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class OilTileRenderer extends GeoEntityRenderer<OilTile> {

    public OilTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new OilTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }
}
