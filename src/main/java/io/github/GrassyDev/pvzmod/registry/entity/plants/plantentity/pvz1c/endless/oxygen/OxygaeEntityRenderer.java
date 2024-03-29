package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class OxygaeEntityRenderer extends GeoEntityRenderer<OxygaeEntity> {

    public OxygaeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new OxygaeEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
