package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.seapea;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SeapeaEntityRenderer extends GeoEntityRenderer<SeapeaEntity> {


    public SeapeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new SeapeaEntityModel());
        this.shadowRadius = 0.5F; //change 0.7 to the desired shadow size.
    }

}
