package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GardenChallengeEntityModel extends GeoModel<GardenChallengeEntity> {

    @Override
    public Identifier getModelResource(GardenChallengeEntity object)
    {
        return new Identifier("pvzmod", "geo/garden.geo.json");
    }

    @Override
    public Identifier getTextureResource(GardenChallengeEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/misc/garden.png");
    }

    @Override
    public Identifier getAnimationResource(GardenChallengeEntity object)
    {
        return new Identifier ("pvzmod", "animations/garden.json");
    }
}
