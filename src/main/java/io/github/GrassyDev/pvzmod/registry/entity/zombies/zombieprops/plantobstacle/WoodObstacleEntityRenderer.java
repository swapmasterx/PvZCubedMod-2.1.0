package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plantobstacle;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class WoodObstacleEntityRenderer extends GeoEntityRenderer<WoodObstacleEntity> {

    public WoodObstacleEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new WoodObstacleEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
