package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.fairytale.springprincess;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SpringPrincessEntityRenderer extends GeoEntityRenderer<SpringPrincessEntity> {

    public SpringPrincessEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SpringPrincessEntityModel());
        this.shadowRadius = 0.8F; //change 0.7 to the desired shadow size.
    }

}
