package io.github.GrassyDev.pvzmod.registry.entity.environment.springtile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SpringTileRenderer extends GeoEntityRenderer<SpringTile> {

    public SpringTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SpringTileModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }
}
