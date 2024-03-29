package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.boomerang.frisbloom;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class FrisbloomEntityRenderer extends GeoEntityRenderer<FrisbloomEntity> {

    public FrisbloomEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new FrisbloomEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
