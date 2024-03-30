package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.tulimpeter;

import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntityRenderer;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TulimpeterEntityModel extends GeoModel<TulimpeterEntity> {

    @Override
    public Identifier getModelResource(TulimpeterEntity object)
    {
        return new Identifier("pvzmod", "geo/tulimpeter.geo.json");
    }

	public Identifier getTextureResource(TulimpeterEntity object) {
		return TulimpeterEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(TulimpeterEntity object)
    {
        return new Identifier ("pvzmod", "animations/tulimpeter.json");
    }
}
