package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.vampireflower;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class VampireFlowerEntityRenderer extends GeoEntityRenderer<VampireFlowerEntity> {

    public VampireFlowerEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new VampireFlowerEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

}
