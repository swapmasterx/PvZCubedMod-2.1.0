package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.rainbowbullet;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class RainbowBulletEntityRenderer extends GeoEntityRenderer<RainbowBulletEntity> {
	@Override
	public void preRender(MatrixStack poseStack, RainbowBulletEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public RainbowBulletEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new RainbowBulletEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(RainbowBulletEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
