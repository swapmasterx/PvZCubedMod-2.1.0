package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.frisbee;

import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class ShootingFrisbeeEntityRenderer extends GeoEntityRenderer <ShootingFrisbeeEntity>{

	public ShootingFrisbeeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingFrisbeeEntityModel());
		this.shadowRadius = 0.4F; //change 0.7 to the desired shadow size.
	}

}
