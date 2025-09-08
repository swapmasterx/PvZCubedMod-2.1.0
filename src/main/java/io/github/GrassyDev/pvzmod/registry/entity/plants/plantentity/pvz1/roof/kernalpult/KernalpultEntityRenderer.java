package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.kernalpult;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class KernalpultEntityRenderer extends GeoEntityRenderer<KernalpultEntity> {

    public KernalpultEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new KernalpultEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
