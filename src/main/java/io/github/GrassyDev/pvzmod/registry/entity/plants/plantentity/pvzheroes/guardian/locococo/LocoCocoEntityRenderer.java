package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.locococo;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class LocoCocoEntityRenderer extends GeoEntityRenderer<LocoCocoEntity> {

    public LocoCocoEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new LocoCocoEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
