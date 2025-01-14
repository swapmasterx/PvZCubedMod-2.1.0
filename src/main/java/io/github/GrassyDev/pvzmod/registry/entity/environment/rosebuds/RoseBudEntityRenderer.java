package io.github.GrassyDev.pvzmod.registry.entity.environment.rosebuds;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class RoseBudEntityRenderer extends GeoEntityRenderer<RoseBudTile> {

    public RoseBudEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RoseBudEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }
}
