package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.shamrock;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShamrockEntityModel extends GeoModel<ShamrockEntity> {

    @Override
    public Identifier getModelResource(ShamrockEntity object)
    {
        return Identifier.of("pvzmod", "geo/shamrock.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShamrockEntity object)
    {
		return ShamrockEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getAnimationResource(ShamrockEntity object)
    {
        return Identifier.of ("pvzmod", "animations/shamrock.json");
    }
}
