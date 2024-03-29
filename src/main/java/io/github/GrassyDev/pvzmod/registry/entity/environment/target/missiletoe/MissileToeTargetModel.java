package io.github.GrassyDev.pvzmod.registry.entity.environment.target.missiletoe;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MissileToeTargetModel extends AnimatedGeoModel<MissileToeTarget> {

    @Override
    public Identifier getModelResource(MissileToeTarget object)
    {
        return new Identifier("pvzmod", "geo/missiletoetarget.geo.json");
    }

    @Override
    public Identifier getTextureResource(MissileToeTarget object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/tiles.png");
    }

    @Override
    public Identifier getAnimationResource(MissileToeTarget object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
