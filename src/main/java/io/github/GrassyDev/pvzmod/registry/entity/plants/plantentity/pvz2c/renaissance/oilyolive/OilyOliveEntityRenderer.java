package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.renaissance.oilyolive;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class OilyOliveEntityRenderer extends GeoEntityRenderer<OilyOliveEntity> {

    public OilyOliveEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new OilyOliveEntityModel());
        this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
    }

}
