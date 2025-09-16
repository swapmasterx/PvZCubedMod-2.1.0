package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.kernalpult;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class KernalpultEntityModel extends GeoModel<KernalpultEntity> {

    @Override
    public Identifier getModelResource(KernalpultEntity object)
    {
        return Identifier.of("pvzmod", "geo/kernalpult.geo.json");
    }

    @Override
    public Identifier getTextureResource(KernalpultEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/cabbagepult/kernalpult.png");
    }

    @Override
    public Identifier getAnimationResource(KernalpultEntity object)
    {
        return Identifier.of ("pvzmod", "animations/cabbagepult.json");
    }
}
