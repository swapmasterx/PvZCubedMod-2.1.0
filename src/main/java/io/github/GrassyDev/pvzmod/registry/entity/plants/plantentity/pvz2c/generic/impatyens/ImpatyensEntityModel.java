package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.impatyens;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ImpatyensEntityModel extends GeoModel<ImpatyensEntity> {

    @Override
    public Identifier getModelResource(ImpatyensEntity object)
    {
        return new Identifier("pvzmod", "geo/impatyens.geo.json");
    }

    @Override
    public Identifier getTextureResource(ImpatyensEntity object){
		return ImpatyensEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(ImpatyensEntity object)
    {
        return new Identifier ("pvzmod", "animations/impatyens.json");
    }
}
