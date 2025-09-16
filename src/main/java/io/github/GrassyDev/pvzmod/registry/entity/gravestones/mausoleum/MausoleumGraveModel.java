package io.github.GrassyDev.pvzmod.registry.entity.gravestones.mausoleum;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MausoleumGraveModel extends GeoModel<MausoleumGraveEntity> {

    @Override
    public Identifier getModelResource(MausoleumGraveEntity object)
    {
        return Identifier.of("pvzmod", "geo/mausoleumgravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(MausoleumGraveEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/gravestone/nightgravestone.png");
    }

    @Override
    public Identifier getAnimationResource(MausoleumGraveEntity object)
    {
        return Identifier.of ("pvzmod", "animations/gravestone.json");
    }
}
