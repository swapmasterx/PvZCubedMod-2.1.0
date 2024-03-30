package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.endurian;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class EndurianEntityRenderer extends GeoEntityRenderer<EndurianEntity> {

    public EndurianEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new EndurianEntityModel());
        this.shadowRadius = 0.6F; //change 0.7 to the desired shadow size.
    }

}
