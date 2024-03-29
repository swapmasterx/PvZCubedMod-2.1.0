package io.github.GrassyDev.pvzmod.registry.entity.environment.springtile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpringTileModel extends AnimatedGeoModel<SpringTile> {

    @Override
    public Identifier getModelResource(SpringTile object)
    {
        return new Identifier("pvzmod", "geo/springtile.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpringTile object)
    {
        return new Identifier("pvzmod", "textures/entity/springprincess/springprincess.png");
    }

    @Override
    public Identifier getAnimationResource(SpringTile object)
    {
        return new Identifier ("pvzmod", "animations/springprincess.json");
    }
}
