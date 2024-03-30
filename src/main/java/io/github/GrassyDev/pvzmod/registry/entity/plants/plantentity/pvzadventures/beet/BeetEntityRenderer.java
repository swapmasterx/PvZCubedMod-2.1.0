package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beet;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BeetEntityRenderer extends GeoEntityRenderer<BeetEntity> {

    public BeetEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BeetEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
