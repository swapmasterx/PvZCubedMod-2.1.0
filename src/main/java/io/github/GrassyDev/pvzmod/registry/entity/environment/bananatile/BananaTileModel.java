package io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BananaTileModel extends AnimatedGeoModel<BananaTile> {

    @Override
    public Identifier getModelResource(BananaTile object)
    {
        return new Identifier("pvzmod", "geo/bananapeel.geo.json");
    }

    @Override
    public Identifier getTextureResource(BananaTile object)
    {
        return new Identifier("pvzmod", "textures/entity/bananasaurus/bananapeel.png");
    }

    @Override
    public Identifier getAnimationResource(BananaTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
