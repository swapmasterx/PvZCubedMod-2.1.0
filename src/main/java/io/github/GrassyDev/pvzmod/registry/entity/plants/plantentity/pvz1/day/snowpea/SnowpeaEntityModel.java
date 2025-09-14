package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SnowpeaEntityModel extends GeoModel<SnowpeaEntity> {

    @Override
    public Identifier getModelResource(SnowpeaEntity object)
    {
        return Identifier.of("pvzmod", "geo/snowpea.geo.json");
    }

	public Identifier getTextureResource(SnowpeaEntity object) {
		return SnowpeaEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(SnowpeaEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashooter.json");
    }
}
