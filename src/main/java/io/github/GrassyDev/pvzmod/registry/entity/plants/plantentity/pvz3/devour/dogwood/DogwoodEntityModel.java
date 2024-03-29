package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz3.devour.dogwood;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class DogwoodEntityModel extends AnimatedGeoModel<DogwoodEntity> {

    @Override
    public Identifier getModelResource(DogwoodEntity object)
    {
        return new Identifier("pvzmod", "geo/dogwood.geo.json");
    }

	public Identifier getTextureResource(DogwoodEntity object) {
		return new Identifier("pvzmod", "textures/entity/dogwood/dogwood.png");
	}

    @Override
    public Identifier getAnimationResource(DogwoodEntity object)
    {
        return new Identifier ("pvzmod", "animations/dogwood.json");
    }
}
