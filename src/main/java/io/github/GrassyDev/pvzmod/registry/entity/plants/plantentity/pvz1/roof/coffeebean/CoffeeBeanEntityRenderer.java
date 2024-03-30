package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.coffeebean;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class CoffeeBeanEntityRenderer extends GeoEntityRenderer<CoffeeBeanEntity> {

    public CoffeeBeanEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new CoffeeBeanEntityModel());
        this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
    }

}
