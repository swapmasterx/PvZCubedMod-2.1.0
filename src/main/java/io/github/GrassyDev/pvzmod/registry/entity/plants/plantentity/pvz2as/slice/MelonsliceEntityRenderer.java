package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.slice;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class MelonsliceEntityRenderer extends GeoEntityRenderer<MelonsliceEntity> {

    public MelonsliceEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MelonsliceEntityModel());
        this.shadowRadius = 0.8F; //change 0.7 to the desired shadow size.
    }

}
