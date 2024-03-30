package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class LilyPadEntityModel extends GeoModel<LilyPadEntity> {

    @Override
    public Identifier getModelResource(LilyPadEntity object)
    {
        return new Identifier("pvzmod", "geo/lilypad.geo.json");
    }

    @Override
    public Identifier getTextureResource(LilyPadEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/lilypad.png");
    }

    @Override
    public Identifier getAnimationResource(LilyPadEntity object)
    {
        return new Identifier ("pvzmod", "animations/lilypad.json");
    }
}
