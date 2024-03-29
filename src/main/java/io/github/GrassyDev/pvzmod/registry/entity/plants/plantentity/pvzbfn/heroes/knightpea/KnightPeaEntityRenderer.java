package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.knightpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class KnightPeaEntityRenderer extends GeoEntityRenderer {

    public KnightPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new KnightPeaEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
