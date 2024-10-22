package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.meteorhammer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MeteorHammerEntityModel extends GeoModel<MeteorHammerEntity> {

    @Override
    public Identifier getModelResource(MeteorHammerEntity object)
    {
        return new Identifier("pvzmod", "geo/meteorhammer.geo.json");
    }

	public Identifier getTextureResource(MeteorHammerEntity object) {
		return new Identifier("pvzmod", "textures/entity/meteorhammer/meteorhammer.png");
	}

    @Override
    public Identifier getAnimationResource(MeteorHammerEntity object)
    {
        return new Identifier ("pvzmod", "animations/meteorhammer.json");
    }
}
