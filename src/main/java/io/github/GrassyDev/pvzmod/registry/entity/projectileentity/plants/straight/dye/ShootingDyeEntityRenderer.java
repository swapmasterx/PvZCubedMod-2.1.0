package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dye;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.plants.DyeVariants;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Map;

public class ShootingDyeEntityRenderer extends GeoEntityRenderer<ShootingDyeEntity> {

	public static final Map<DyeVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(DyeVariants.class), (map) -> {
				map.put(DyeVariants.CONTAIN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye.png"));
				map.put(DyeVariants.APPEASE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_appease.png"));
				map.put(DyeVariants.SPEAR,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_spear.png"));
				map.put(DyeVariants.CONCEAL,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_conceal.png"));
				map.put(DyeVariants.ENFORCE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_enforce.png"));
				map.put(DyeVariants.ENCHANT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_enchant.png"));
				map.put(DyeVariants.AILMENT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_ailment.png"));
				map.put(DyeVariants.BOMBARD,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_bombard.png"));
				map.put(DyeVariants.REINFORCE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_reinforce.png"));
				map.put(DyeVariants.ENLIGHTEN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_enlighten.png"));
				map.put(DyeVariants.WINTER,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_winter.png"));
				map.put(DyeVariants.PEPPER,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_pepper.png"));
				map.put(DyeVariants.FILAMENT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_filament.png"));
				map.put(DyeVariants.ARMA,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/dye_arma.png"));
			});

	public Identifier getTextureResource(ShootingDyeEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}
	@Override
	public void preRender(MatrixStack poseStack, ShootingDyeEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public ShootingDyeEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingDyeEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
