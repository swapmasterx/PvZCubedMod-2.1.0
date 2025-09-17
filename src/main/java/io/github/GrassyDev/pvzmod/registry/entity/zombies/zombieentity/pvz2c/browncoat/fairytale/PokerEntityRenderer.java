package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.browncoat.fairytale;

import com.google.common.collect.Maps;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.darkages.PeasantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.pharaoh.PharaohEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.animatable.GeoAnimatable;

import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.AnimatableManager;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.ClientUtil;
import software.bernie.geckolib.util.Color;
import software.bernie.geckolib.util.GeckoLibUtil;
import io.github.GrassyDev.pvzmod.registry.entity.damage.PvZDamageTypes;

import software.bernie.geckolib.renderer.GeoEntityRenderer;

import java.util.Map;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class PokerEntityRenderer extends GeoEntityRenderer<PokerEntity> {

    public PokerEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new PokerEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BrowncoatVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.BROWNCOAT,
				        Identifier.of("pvzmod", "geo/pokerzombie.geo.json"));
				map.put(BrowncoatVariants.BROWNCOATHYPNO,
						Identifier.of("pvzmod", "geo/pokerzombie.geo.json"));
				map.put(BrowncoatVariants.CONEHEAD,
						Identifier.of("pvzmod", "geo/pokerconehead.geo.json"));
				map.put(BrowncoatVariants.CONEHEADHYPNO,
						Identifier.of("pvzmod", "geo/pokerconehead.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEAD,
						Identifier.of("pvzmod", "geo/pokerbuckethead.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEADHYPNO,
						Identifier.of("pvzmod", "geo/pokerbuckethead.geo.json"));
				map.put(BrowncoatVariants.POKERPAWN,
						Identifier.of("pvzmod", "geo/pokerpawn.geo.json"));
				map.put(BrowncoatVariants.POKERPAWNHYPNO,
						Identifier.of("pvzmod", "geo/pokerpawn.geo.json"));
				map.put(BrowncoatVariants.POKERKNIGHT,
						Identifier.of("pvzmod", "geo/pokerknight.geo.json"));
				map.put(BrowncoatVariants.POKERKNIGHTHYPNO,
						Identifier.of("pvzmod", "geo/pokerknight.geo.json"));
				map.put(BrowncoatVariants.POKERTOWER,
						Identifier.of("pvzmod", "geo/pokertower.geo.json"));
				map.put(BrowncoatVariants.POKERTOWERHYPNO,
						Identifier.of("pvzmod", "geo/pokertower.geo.json"));
				map.put(BrowncoatVariants.POKERBISHOP,
						Identifier.of("pvzmod", "geo/pokerbishop.geo.json"));
				map.put(BrowncoatVariants.POKERBISHOPHYPNO,
						Identifier.of("pvzmod", "geo/pokerbishop.geo.json"));
			});

	public Identifier getModelResource(PeasantEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}
	@Override
	public Color getRenderColor(PokerEntity animatable, float partialTick, int packedLight) {
		Color color = PokerEntityRenderer.super.getRenderColor(animatable, partialTick, packedLight);

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
