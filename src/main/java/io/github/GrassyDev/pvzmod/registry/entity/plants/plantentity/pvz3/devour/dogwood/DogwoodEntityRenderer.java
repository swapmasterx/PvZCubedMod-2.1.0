package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz3.devour.dogwood;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class DogwoodEntityRenderer extends GeoEntityRenderer<DogwoodEntity> {

    public DogwoodEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new DogwoodEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
