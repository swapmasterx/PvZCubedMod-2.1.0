package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WallnutEntityModel extends GeoModel<WallnutEntity> {

    @Override
    public Identifier getModelResource(WallnutEntity object)
    {
        return new Identifier("pvzmod", "geo/wallnut.geo.json");
    }

    @Override
    public Identifier getTextureResource(WallnutEntity object)
    {
		return WallnutEntity.LOCATION_BY_VARIANT.get(object.getCrack());
    }

    @Override
    public Identifier getAnimationResource(WallnutEntity object)
    {
        return new Identifier ("pvzmod", "animations/wallnut.json");
    }
}
