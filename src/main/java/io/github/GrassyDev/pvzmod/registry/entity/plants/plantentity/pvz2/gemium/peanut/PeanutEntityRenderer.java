package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.peanut;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PeanutEntityRenderer extends GeoEntityRenderer<PeanutEntity> {

    public PeanutEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PeanutEntityModel());
        this.shadowRadius = 0.75F; //change 0.7 to the desired shadow size.
    }

}
