package io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FutureGraveModel extends GeoModel<FutureGraveEntity> {

    @Override
    public Identifier getModelResource(FutureGraveEntity object)
    {
        return Identifier.of("pvzmod", "geo/futuregravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(FutureGraveEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/gravestone/futuregravestone.png");
    }

    @Override
    public Identifier getAnimationResource(FutureGraveEntity object)
    {
        return Identifier.of ("pvzmod", "animations/gravestone.json");
    }
}
