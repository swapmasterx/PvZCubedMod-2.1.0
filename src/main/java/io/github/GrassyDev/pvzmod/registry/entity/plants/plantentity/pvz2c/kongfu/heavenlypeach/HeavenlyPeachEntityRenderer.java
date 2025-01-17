package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.kongfu.heavenlypeach;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HeavenlyPeachEntityRenderer extends GeoEntityRenderer<HeavenlyPeachEntity> {

    public HeavenlyPeachEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HeavenlyPeachEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
