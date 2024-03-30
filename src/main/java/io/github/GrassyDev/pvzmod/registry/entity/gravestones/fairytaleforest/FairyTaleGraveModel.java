package io.github.GrassyDev.pvzmod.registry.entity.gravestones.fairytaleforest;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FairyTaleGraveModel extends GeoModel<FairyTaleGraveEntity> {

    @Override
    public Identifier getModelResource(FairyTaleGraveEntity object)
    {
        return new Identifier("pvzmod", "geo/fairytalegravestone.geo.json");
    }

    @Override
    public Identifier getTextureResource(FairyTaleGraveEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/gravestone/fairytalegravestone.png");
    }

    @Override
    public Identifier getAnimationResource(FairyTaleGraveEntity object)
    {
        return new Identifier ("pvzmod", "animations/gravestone.json");
    }
}
