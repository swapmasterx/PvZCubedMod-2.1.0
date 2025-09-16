package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.admiralnavybean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class AdmiralNavyBeanEntityModel extends GeoModel<AdmiralNavyBeanEntity> {

    @Override
    public Identifier getModelResource(AdmiralNavyBeanEntity object)
    {
        return Identifier.of("pvzmod", "geo/admiralnavybean.geo.json");
    }

	public Identifier getTextureResource(AdmiralNavyBeanEntity object) {
		return Identifier.of("pvzmod", "textures/entity/bean/admiralnavybean.png");
	}

    @Override
    public Identifier getAnimationResource(AdmiralNavyBeanEntity object)
    {
        return Identifier.of ("pvzmod", "animations/navybean.json");
    }
}
