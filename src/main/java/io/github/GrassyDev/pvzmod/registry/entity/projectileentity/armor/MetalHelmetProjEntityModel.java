package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.armor;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MetalHelmetProjEntityModel extends AnimatedGeoModel<MetalHelmetProjEntity> {

    @Override
    public Identifier getModelResource(MetalHelmetProjEntity object)
    {
		return MetalHelmetProjEntityRenderer.MODEL_LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(MetalHelmetProjEntity object) {
		if (object.damaged) {
			return MetalHelmetProjEntityRenderer.LOCATION_BY_VARIANT_DAMAGED.get(object.getVariant());
		} else {
			return MetalHelmetProjEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
		}
	}

    @Override
    public Identifier getAnimationResource(MetalHelmetProjEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
