package io.github.GrassyDev.pvzmod.registry.entity.gravestones.mausoleum;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class MausoleumGraveModel extends AnimatedGeoModel<MausoleumGraveEntity> {

    @Override
    public Identifier getModelResource(MausoleumGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/mausoleumgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(MausoleumGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(MausoleumGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
