package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.frostbitecaves.pepperpult;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PepperpultEntityModel extends GeoModel<PepperpultEntity> {

    @Override
    public Identifier getModelResource(PepperpultEntity object)
    {
        return Identifier.of("pvzmod", "geo/pepperpult.geo.json");
    }

    @Override
    public Identifier getTextureResource(PepperpultEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/cabbagepult/pepperpult.png");
    }

    @Override
    public Identifier getAnimationResource(PepperpultEntity object)
    {
        return Identifier.of ("pvzmod", "animations/cabbagepult.json");
    }
}
