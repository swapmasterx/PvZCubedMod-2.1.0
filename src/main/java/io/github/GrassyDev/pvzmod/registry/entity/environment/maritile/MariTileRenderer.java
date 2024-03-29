package io.github.GrassyDev.pvzmod.registry.entity.environment.maritile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class MariTileRenderer extends GeoEntityRenderer<MariTile> {

    public MariTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MariTileModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }
}
