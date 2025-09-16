package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ThreepeaterEntityModel extends GeoModel<ThreepeaterEntity> {

    @Override
    public Identifier getModelResource(ThreepeaterEntity object)
    {
        return Identifier.of("pvzmod", "geo/threepeater.geo.json");
    }

    @Override
    public Identifier getTextureResource(ThreepeaterEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/peashooter/peashooter.png");
    }

    @Override
    public Identifier getAnimationResource(ThreepeaterEntity object)
    {
        return Identifier.of ("pvzmod", "animations/threepeater.json");
    }
}
