package io.github.GrassyDev.pvzmod.registry.entity.gravestones.mausoleum;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MausoleumGraveModel extends GeoModel<MausoleumGraveEntity> {

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
