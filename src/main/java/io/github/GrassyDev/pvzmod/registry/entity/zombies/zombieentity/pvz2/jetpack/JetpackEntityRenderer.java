package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.jetpack;

import com.google.common.collect.Maps;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.JetpackVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer.AnnouncerImpEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;

import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.Color;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class JetpackEntityRenderer extends GeoEntityRenderer<JetpackEntity> {

    public JetpackEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new JetpackEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<JetpackVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(JetpackVariants.class), (map) -> {
				map.put(JetpackVariants.JETPACK,
				        Identifier.of("pvzmod", "geo/jetpack.geo.json"));
				map.put(JetpackVariants.JETPACKHYPNO,
						Identifier.of("pvzmod", "geo/jetpack.geo.json"));
				map.put(JetpackVariants.BLASTRONAUT,
						Identifier.of("pvzmod", "geo/blastronaut.geo.json"));
				map.put(JetpackVariants.BLASTRONAUTHYPNO,
						Identifier.of("pvzmod", "geo/blastronaut.geo.json"));
			});

	public Identifier getModelResource(JetpackEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	protected int getBlockLight(JetpackEntity zombieEntity, BlockPos blockPos) {
		return Math.min(super.getBlockLight(zombieEntity, blockPos) + 7, 15);
	}


	@Override
	public Color getRenderColor(JetpackEntity animatable, float partialTick, int packedLight) {
		Color color = JetpackEntityRenderer.super.getRenderColor(animatable, partialTick, packedLight);

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
