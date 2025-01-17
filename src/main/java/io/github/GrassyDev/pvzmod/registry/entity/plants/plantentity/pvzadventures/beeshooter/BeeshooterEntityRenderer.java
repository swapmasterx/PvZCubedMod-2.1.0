package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class BeeshooterEntityRenderer extends GeoEntityRenderer<BeeshooterEntity> {

    public BeeshooterEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BeeshooterEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
