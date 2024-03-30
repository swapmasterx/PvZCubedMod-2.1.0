package io.github.GrassyDev.pvzmod.registry.entity.environment.sunbomb;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SunBombEntityRenderer extends GeoEntityRenderer<SunBombEntity> {

    public SunBombEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SunBombModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }
}
