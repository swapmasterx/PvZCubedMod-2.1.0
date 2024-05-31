package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.icespike;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class ShootingPowerIceSpikeEntityRenderer extends GeoEntityRenderer<ShootingPowerIcespikeEntity> {
	@Override
	public void preRender(MatrixStack poseStack, ShootingPowerIcespikeEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public ShootingPowerIceSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPowerIcespikeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPowerIcespikeEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
