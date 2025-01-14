package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class GatlingpeaEntityRenderer extends GeoEntityRenderer<GatlingpeaEntity> {

    public GatlingpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GatlingpeaEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
