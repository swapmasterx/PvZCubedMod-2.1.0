package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.hovergoat;


import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.actionhero.ActionheroEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.Color;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class HoverGoatEntityRenderer extends GeoEntityRenderer<HoverGoatEntity> {

    public HoverGoatEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new HoverGoatEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }


	protected int getBlockLight(HoverGoatEntity zombieEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(zombieEntity, blockPos) + 9, 15);
	}

	@Override
	public Color getRenderColor(HoverGoatEntity animatable, float partialTick, int packedLight) {
		Color color = HoverGoatEntityRenderer.super.getRenderColor(animatable, partialTick, packedLight);

		if (animatable.isInvisible() && !animatable.isInvisibleTo(ClientUtil.getClientPlayer()))
			color = Color.ofARGB(MathHelper.ceil(color.getAlpha() * 38 / 255f), color.getRed(), color.getGreen(), color.getBlue());

		else if (animatable.getHypno())
			color = Color.ofRGB(1, 255, 1);
		else if (animatable.fireSplashTicks > 0)
			color = Color.ofRGB(1, 255, 225);
		else if (animatable.isIced || animatable.isFrozen)
			color = Color.ofRGB(225, 75, 1);
		else if (animatable.isPoisoned)
			color = Color.ofRGB(100, 255, 1);
		else
			color = Color.ofRGB(color.getRed(), color.getGreen(), color.getBlue());

		return color;
	}
}
