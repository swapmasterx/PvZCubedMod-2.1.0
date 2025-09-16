package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TangleKelpEntityModel extends GeoModel<TangleKelpEntity> {

    @Override
    public Identifier getModelResource(TangleKelpEntity object)
    {
        return Identifier.of("pvzmod", "geo/tanglekelp.geo.json");
    }

    @Override
    public Identifier getTextureResource(TangleKelpEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/tanglekelp/tanglekelp.png");
    }

    @Override
    public Identifier getAnimationResource(TangleKelpEntity object)
    {
        return Identifier.of ("pvzmod", "animations/tanglekelp.json");
    }
}
