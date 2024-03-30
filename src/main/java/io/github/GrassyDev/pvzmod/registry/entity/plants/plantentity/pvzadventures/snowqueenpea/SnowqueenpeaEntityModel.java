package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SnowqueenpeaEntityModel extends GeoModel<SnowqueenpeaEntity> {

    @Override
    public Identifier getModelResource(SnowqueenpeaEntity object)
    {
        return new Identifier("pvzmod", "geo/snowqueenpea.geo.json");
    }

	public Identifier getTextureResource(SnowqueenpeaEntity object) {
		return SnowqueenpeaEntityRenderer.LOCATION_BY_VARIANT.get(object.getVariant());
	}

    @Override
    public Identifier getAnimationResource(SnowqueenpeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
