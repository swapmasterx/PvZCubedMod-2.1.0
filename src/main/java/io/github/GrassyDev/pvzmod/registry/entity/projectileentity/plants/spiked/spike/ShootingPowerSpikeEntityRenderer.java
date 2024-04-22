package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.spike;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

public class ShootingPowerSpikeEntityRenderer extends GeoEntityRenderer<ShootingPowerSpikeEntity> {
	@Override
	public void preRender(MatrixStack poseStack, ShootingPowerSpikeEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public ShootingPowerSpikeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPowerSpikeEntityModel());
		this.shadowRadius = 0.2F; //change 0.7 to the desired shadow size.
	}
	protected int getBlockLight(ShootingPowerSpikeEntity plantEntity, BlockPos blockPos) {
		return 15;
	}
}
