package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday;

import com.google.common.collect.Maps;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.GargantuarVariants;
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
public class GargantuarEntityRenderer extends GeoEntityRenderer<GargantuarEntity> {

    public GargantuarEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new GargantuarEntityModel());
        this.shadowRadius = 1.5F; //change 0.7 to the desired shadow size.
    }

	public static final Map<GargantuarVariants, Identifier> LOCATION_MODEL_BY_VARIANT =
			Util.make(Maps.newEnumMap(GargantuarVariants.class), (map) -> {
				map.put(GargantuarVariants.GARGANTUAR,
						Identifier.of("pvzmod", "geo/gargantuar.geo.json"));
				map.put(GargantuarVariants.GARGANTUARHYPNO,
						Identifier.of("pvzmod", "geo/gargantuar.geo.json"));
				map.put(GargantuarVariants.MUMMY,
						Identifier.of("pvzmod", "geo/mummygargantuar.geo.json"));
				map.put(GargantuarVariants.MUMMYHYPNO,
						Identifier.of("pvzmod", "geo/mummygargantuar.geo.json"));
				map.put(GargantuarVariants.UNICORNGARGANTUAR,
						Identifier.of("pvzmod", "geo/unicorngargantuar.geo.json"));
				map.put(GargantuarVariants.UNICORNGARGANTUARHYPNO,
						Identifier.of("pvzmod", "geo/unicorngargantuar.geo.json"));
				map.put(GargantuarVariants.DEFENSIVEEND,
						Identifier.of("pvzmod", "geo/defensiveend.geo.json"));
				map.put(GargantuarVariants.DEFENSIVEENDHYPNO,
						Identifier.of("pvzmod", "geo/defensiveend.geo.json"));
				map.put(GargantuarVariants.DEFENSIVEEND_NEWYEAR,
						Identifier.of("pvzmod", "geo/defensiveend.geo.json"));
				map.put(GargantuarVariants.DEFENSIVEEND_NEWYEARHYPNO,
						Identifier.of("pvzmod", "geo/defensiveend.geo.json"));
				map.put(GargantuarVariants.GARGOLITH,
						Identifier.of("pvzmod", "geo/gargolith.geo.json"));
				map.put(GargantuarVariants.GARGOLITHHYPNO,
						Identifier.of("pvzmod", "geo/gargolith.geo.json"));
			});

	public Identifier getModelResource(GargantuarEntity object) {
		return LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
	}

	@Override
	public Color getRenderColor(GargantuarEntity animatable, float partialTick, int packedLight) {
		Color color = GargantuarEntityRenderer.super.getRenderColor(animatable, partialTick, packedLight);

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
