package io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RoofGraveModel extends GeoModel<RoofGraveEntity> {

    @Override
    public Identifier getModelResource(RoofGraveEntity object)
    {
        return Identifier.of("pvzmod", "geo/roofgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(RoofGraveEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(RoofGraveEntity object)
    {
        return Identifier.of ("pvzmod", "animations/gravestone.json");
    }
}
