package io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RoofGraveModel extends GeoModel<RoofGraveEntity> {

    @Override
    public Identifier getModelResource(RoofGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/roofgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(RoofGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(RoofGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
