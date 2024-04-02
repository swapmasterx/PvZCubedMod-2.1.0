package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.bone;


import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BoneProjEntityRenderer extends GeoEntityRenderer<BoneProjEntity> {

	public BoneProjEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BonProjEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}
}
