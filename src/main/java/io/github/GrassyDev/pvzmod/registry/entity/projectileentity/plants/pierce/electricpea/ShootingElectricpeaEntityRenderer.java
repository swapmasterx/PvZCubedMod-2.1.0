package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.electricpea;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class ShootingElectricpeaEntityRenderer extends GeoEntityRenderer<ShootingElectricPeaEntity> {
	@Override
	public void preRender(MatrixStack poseStack, ShootingElectricPeaEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public ShootingElectricpeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingElectricPeaEntityModel());
		this.shadowRadius = 0.1F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingElectricPeaEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
