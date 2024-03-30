package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.peanut;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PeanutEntityModel extends GeoModel<PeanutEntity> {

    @Override
    public Identifier getModelResource(PeanutEntity object)
    {
        return new Identifier("pvzmod", "geo/peanut.geo.json");
    }

    @Override
    public Identifier getTextureResource(PeanutEntity object)
    {
		return PeanutEntity.LOCATION_BY_VARIANT.get(object.getCrack());
    }

    @Override
    public Identifier getAnimationResource(PeanutEntity object)
    {
        return new Identifier ("pvzmod", "animations/peanut.json");
    }
}
