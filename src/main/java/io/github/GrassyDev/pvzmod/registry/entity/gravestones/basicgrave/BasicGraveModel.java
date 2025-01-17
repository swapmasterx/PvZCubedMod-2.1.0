package io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BasicGraveModel extends GeoModel<BasicGraveEntity> {

    @Override
    public Identifier getModelResource(BasicGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/basicgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(BasicGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/basicgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(BasicGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
