package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BubblePadEntityRenderer extends GeoEntityRenderer<BubblePadEntity> {

    public BubblePadEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BubblePadEntityModel());
		this.shadowRadius = 0F; //change 0.7 to the desired shadow size.
    }

}
