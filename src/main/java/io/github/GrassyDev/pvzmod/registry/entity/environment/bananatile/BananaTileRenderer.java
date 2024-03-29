package io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BananaTileRenderer extends GeoEntityRenderer<BananaTile> {

    public BananaTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BananaTileModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }
}
