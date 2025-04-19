package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.melonpult;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
/*    */
/*    */
/*    */
public class MelonpultEntityRenderer extends GeoEntityRenderer<MelonpultEntity> {

	public MelonpultEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new MelonpultEntityModel());
		this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
	}

}
