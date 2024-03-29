package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.heian.dripphylleia;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DripphylleiaEntityRenderer extends GeoEntityRenderer<DripphylleiaEntity> {

    public DripphylleiaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DripphylleiaEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
