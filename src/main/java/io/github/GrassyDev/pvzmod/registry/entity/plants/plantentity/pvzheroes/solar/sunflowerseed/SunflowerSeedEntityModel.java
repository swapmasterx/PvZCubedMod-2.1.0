package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.sunflowerseed;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SunflowerSeedEntityModel extends GeoModel<SunflowerSeedEntity> {

    @Override
    public Identifier getModelResource(SunflowerSeedEntity object)
    {
        return Identifier.of("pvzmod", "geo/sunflowerseed.geo.json");
    }

    @Override
    public Identifier getTextureResource(SunflowerSeedEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/small/sunflowerseed.png");
    }

    @Override
    public Identifier getAnimationResource(SunflowerSeedEntity object)
    {
        return Identifier.of ("pvzmod", "animations/small.json");
    }
}
