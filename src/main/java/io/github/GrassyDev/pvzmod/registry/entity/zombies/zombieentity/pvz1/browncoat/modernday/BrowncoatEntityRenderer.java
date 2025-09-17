package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday;

import com.google.common.collect.Maps;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.BrowncoatVariants;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntityRenderer;
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
public class BrowncoatEntityRenderer extends GeoEntityRenderer<BrowncoatEntity> {

    public BrowncoatEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new BrowncoatEntityModel());
        this.shadowRadius = 0.7F; //change 0.7 to the desired shadow size.
    }

	public static final Map<BrowncoatVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(BrowncoatVariants.class), (map) -> {
				map.put(BrowncoatVariants.BROWNCOAT,
				        Identifier.of("pvzmod", "geo/browncoat.geo.json"));
				map.put(BrowncoatVariants.BROWNCOATHYPNO,
						Identifier.of("pvzmod", "geo/browncoat.geo.json"));
				map.put(BrowncoatVariants.CONEHEAD,
						Identifier.of("pvzmod", "geo/conehead.geo.json"));
				map.put(BrowncoatVariants.CONEHEADHYPNO,
						Identifier.of("pvzmod", "geo/conehead.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEAD,
						Identifier.of("pvzmod", "geo/buckethead.geo.json"));
				map.put(BrowncoatVariants.BUCKETHEADHYPNO,
						Identifier.of("pvzmod", "geo/buckethead.geo.json"));
				map.put(BrowncoatVariants.SCREENDOOR,
						Identifier.of("pvzmod", "geo/screendoor.geo.json"));
				map.put(BrowncoatVariants.SCREENDOORHYPNO,
						Identifier.of("pvzmod", "geo/screendoor.geo.json"));
				map.put(BrowncoatVariants.TRASHCAN,
						Identifier.of("pvzmod", "geo/trashcan.geo.json"));
				map.put(BrowncoatVariants.TRASHCANHYPNO,
						Identifier.of("pvzmod", "geo/trashcan.geo.json"));
				map.put(BrowncoatVariants.BRICKHEAD,
						Identifier.of("pvzmod", "geo/brickhead.geo.json"));
				map.put(BrowncoatVariants.BRICKHEADHYPNO,
						Identifier.of("pvzmod", "geo/brickhead.geo.json"));
			});

	public Identifier getModelResource(BrowncoatEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}


	@Override
	public Color getRenderColor(BrowncoatEntity animatable, float partialTick, int packedLight) {
		Color color = BrowncoatEntityRenderer.super.getRenderColor(animatable, partialTick, packedLight);

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


	@Override
	protected int getBlockLight(BrowncoatEntity entity, BlockPos pos) {
		if (entity.getType().equals(PvZEntity.SUMMERBASIC) ||
				entity.getType().equals(PvZEntity.SUMMERBASICHYPNO) ||
				entity.getType().equals(PvZEntity.SUMMERCONEHEAD) ||
				entity.getType().equals(PvZEntity.SUMMERCONEHEADHYPNO) ||
				entity.getType().equals(PvZEntity.SUMMERBUCKETHEAD) ||
				entity.getType().equals(PvZEntity.SUMMERBUCKETHEADHYPNO)){
			return 15;
		}
		else {
			return super.getBlockLight(entity, pos);
		}
	}
}
