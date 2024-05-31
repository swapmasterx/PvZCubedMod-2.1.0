package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.pea;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.GrassyDev.pvzmod.PvZCubed;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShootingPeaVariants;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.util.RenderUtils;

import java.util.Map;

public class ShootingPeaEntityRenderer extends GeoEntityRenderer<ShootingPeaEntity> {

	public static final Map<ShootingPeaVariants, Identifier> LOCATION_BY_VARIANT =
			Util.make(Maps.newEnumMap(ShootingPeaVariants.class), (map) -> {
				map.put(ShootingPeaVariants.DEFAULT,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot.png"));
				map.put(ShootingPeaVariants.BLACK,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot_black.png"));
				map.put(ShootingPeaVariants.PURPLE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot_purple.png"));
				map.put(ShootingPeaVariants.BLUE,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot_blue.png"));
				map.put(ShootingPeaVariants.CYAN,
						new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/peashot_cyan.png"));
			});

	public Identifier getTextureResource(ShootingPeaEntity object) {
		return LOCATION_BY_VARIANT.get(object.getVariant());
	}
	@Override
	public void preRender(MatrixStack poseStack, ShootingPeaEntity animatable, BakedGeoModel model, VertexConsumerProvider bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue,
						  float alpha) {
		RenderUtils.faceRotation(poseStack, animatable, partialTick);

	}
	public ShootingPeaEntityRenderer(EntityRendererFactory.Context ctx) {
		super(ctx, new ShootingPeaEntityModel());
		this.shadowRadius = 0.3F; //change 0.7 to the desired shadow size.
	}

}
