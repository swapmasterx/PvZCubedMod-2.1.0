package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ScaredyshroomEntityModel extends GeoModel<ScaredyshroomEntity> {

    @Override
    public Identifier getModelResource(ScaredyshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/scaredyshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScaredyshroomEntity object)
    {
		return ScaredyshroomEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ScaredyshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/scaredyshroom.json");
    }
}
