package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.loquat;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class LoquatEntityRenderer extends GeoEntityRenderer<LoquatEntity> {

    public LoquatEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LoquatEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
