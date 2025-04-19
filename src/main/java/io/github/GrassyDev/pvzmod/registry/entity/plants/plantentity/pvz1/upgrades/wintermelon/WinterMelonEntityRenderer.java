package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.wintermelon;

import net.minecraft.client.render.entity.EntityRendererFactory;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class WinterMelonEntityRenderer extends GeoEntityRenderer<WinterMelonEntity> {

    public WinterMelonEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new WinterMelonEntityModel());
        this.shadowRadius = 0.9F; //change 0.7 to the desired shadow size.
    }

}
