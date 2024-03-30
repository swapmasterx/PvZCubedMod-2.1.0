package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.armor;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MetalHelmetProjEntityModel extends GeoModel<MetalHelmetProjEntity> {

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
