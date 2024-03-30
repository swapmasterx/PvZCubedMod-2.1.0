package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TwinSunflowerEntityModel extends GeoModel<TwinSunflowerEntity> {

    @Override
    public Identifier getModelResource(TwinSunflowerEntity object)
    {
        return new Identifier("pvzmod", "geo/twinsunflower.geo.json");
    }

    @Override
    public Identifier getTextureResource(TwinSunflowerEntity object)
    {
		return TwinSunflowerEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(TwinSunflowerEntity object)
    {
        return new Identifier ("pvzmod", "animations/sunflower.json");
    }
}
