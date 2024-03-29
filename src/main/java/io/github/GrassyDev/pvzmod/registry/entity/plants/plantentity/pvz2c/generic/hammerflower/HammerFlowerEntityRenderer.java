package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.hammerflower;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HammerFlowerEntityRenderer extends GeoEntityRenderer<HammerFlowerEntity> {

    public HammerFlowerEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HammerFlowerEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
