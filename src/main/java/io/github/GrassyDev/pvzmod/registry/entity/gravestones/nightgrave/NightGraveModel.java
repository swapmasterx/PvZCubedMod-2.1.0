package io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class NightGraveModel extends GeoModel<NightGraveEntity> {

    @Override
    public Identifier getModelResource(NightGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/nightgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(NightGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(NightGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
