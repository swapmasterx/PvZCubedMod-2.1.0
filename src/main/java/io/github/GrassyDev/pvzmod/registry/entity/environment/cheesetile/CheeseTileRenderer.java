package io.github.GrassyDev.pvzmod.registry.entity.environment.cheesetile;

import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTileModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CheeseTileRenderer extends GeoEntityRenderer<CheeseTile> {

    public CheeseTileRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CheeseTileModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }
}
