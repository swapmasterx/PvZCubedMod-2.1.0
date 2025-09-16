package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.missiletoe;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MissileToeEntityModel extends GeoModel<MissileToeEntity> {

    @Override
    public Identifier getModelResource(MissileToeEntity object)
    {
        return Identifier.of("pvzmod", "geo/missiletoe.geo.json");
    }

    @Override
    public Identifier getTextureResource(MissileToeEntity object)
    {
		return Identifier.of("pvzmod", "textures/entity/missiletoe/missiletoe.png");
    }

    @Override
    public Identifier getAnimationResource(MissileToeEntity object)
    {
        return Identifier.of ("pvzmod", "animations/missiletoe.json");
    }
}
