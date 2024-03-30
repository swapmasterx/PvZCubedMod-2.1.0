package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.megagrow.bananasaurus;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BananasaurusEntityRenderer extends GeoEntityRenderer<BananasaurusEntity> {

    public BananasaurusEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BananasaurusEntityModel());
        this.shadowRadius = 0.8F; //change 0.7 to the desired shadow size.
    }

}
