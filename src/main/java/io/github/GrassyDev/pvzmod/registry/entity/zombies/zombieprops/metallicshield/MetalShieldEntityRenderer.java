package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class MetalShieldEntityRenderer extends GeoEntityRenderer<MetalShieldEntity> {

    public MetalShieldEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MetalShieldEntityModel());
        this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}