package io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class NightGraveModel extends GeoModel<NightGraveEntity> {

    @Override
    public Identifier getModelResource(NightGraveEntity object)
    {
        return Identifier.of("pvzmod", "geo/nightgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(NightGraveEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(NightGraveEntity object)
    {
        return Identifier.of ("pvzmod", "animations/gravestone.json");
    }
}
