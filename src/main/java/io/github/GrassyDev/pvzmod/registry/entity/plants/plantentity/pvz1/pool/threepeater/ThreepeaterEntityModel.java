package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ThreepeaterEntityModel extends GeoModel<ThreepeaterEntity> {

    @Override
    public Identifier getModelResource(ThreepeaterEntity object)
    {
        return new Identifier("pvzmod", "geo/threepeater.geo.json");
    }

    @Override
    public Identifier getTextureResource(ThreepeaterEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationResource(ThreepeaterEntity object)
    {
        return new Identifier ("pvzmod", "animations/threepeater.json");
    }
}
