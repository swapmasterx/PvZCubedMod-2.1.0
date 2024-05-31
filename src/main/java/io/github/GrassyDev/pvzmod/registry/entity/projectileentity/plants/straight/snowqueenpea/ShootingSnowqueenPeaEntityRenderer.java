package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowqueenpea;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class ShootingSnowqueenPeaEntityRenderer extends GeoEntityRenderer <ShootingSnowqueenPeaEntity>{
	@Override
	public void preRender(MatrixStack poseStack, ShootingSnowqueenPeaEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public ShootingSnowqueenPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingSnowqueenPeaEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
}
