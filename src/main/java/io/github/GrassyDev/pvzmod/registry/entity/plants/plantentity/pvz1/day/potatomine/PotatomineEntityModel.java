package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PotatomineEntityModel extends GeoModel<PotatomineEntity> {

    @Override
    public Identifier getModelResource(PotatomineEntity object)
    {
        return new Identifier("pvzmod", "geo/potatomine.geo.json");
    }

    @Override
    public Identifier getTextureResource(PotatomineEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/potatomine/potatomine.png");
    }

    @Override
    public Identifier getAnimationResource(PotatomineEntity object)
    {
        return new Identifier ("pvzmod", "animations/potatomine.json");
    }
}
