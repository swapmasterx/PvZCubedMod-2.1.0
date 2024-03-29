package io.github.GrassyDev.pvzmod.registry.entity.environment.sunbomb;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SunBombModel extends AnimatedGeoModel<SunBombEntity> {

    @Override
    public Identifier getModelResource(SunBombEntity object)
    {
        return new Identifier("pvzmod", "geo/sunbomb.geo.json");
    }

    @Override
    public Identifier getTextureResource(SunBombEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/sunbomb.png");
    }

    @Override
    public Identifier getAnimationResource(SunBombEntity object)
    {
        return new Identifier ("pvzmod", "animations/sunbomb.json");
    }
}
