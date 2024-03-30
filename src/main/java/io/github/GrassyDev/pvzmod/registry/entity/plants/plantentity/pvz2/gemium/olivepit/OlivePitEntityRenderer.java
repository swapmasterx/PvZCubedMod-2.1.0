package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class OlivePitEntityRenderer extends GeoEntityRenderer<OlivePitEntity> {

    public OlivePitEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new OlivePitEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
