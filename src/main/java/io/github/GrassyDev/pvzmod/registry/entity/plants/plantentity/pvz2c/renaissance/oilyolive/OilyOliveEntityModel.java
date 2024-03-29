package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.renaissance.oilyolive;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OilyOliveEntityModel extends AnimatedGeoModel<OilyOliveEntity> {

    @Override
    public Identifier getModelResource(OilyOliveEntity object)
    {
        return new Identifier("pvzmod", "geo/oilyolive.geo.json");
    }

    @Override
    public Identifier getTextureResource(OilyOliveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/oilyolive/oilyolive.png");
    }

    @Override
    public Identifier getAnimationResource(OilyOliveEntity object)
    {
        return new Identifier ("pvzmod", "animations/small.json");
    }
}
