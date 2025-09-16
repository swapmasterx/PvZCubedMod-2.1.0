package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpikerockEntityModel extends GeoModel<SpikerockEntity> {

    @Override
    public Identifier getModelResource(SpikerockEntity object)
    {
        return Identifier.of("pvzmod", "geo/spikerock.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpikerockEntity object)
    {
		return SpikerockEntity.LOCATION_BY_VARIANT.get(object.getCrack());
    }

    @Override
    public Identifier getAnimationResource(SpikerockEntity object)
    {
        return Identifier.of ("pvzmod", "animations/spikeweed.json");
    }
}
