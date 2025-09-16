package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class HypnoshroomEntityModel extends GeoModel<HypnoshroomEntity> {

    @Override
    public Identifier getModelResource(HypnoshroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/hypnoshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(HypnoshroomEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/hypnoshroom/hypnoshroom.png");
    }

    @Override
    public Identifier getAnimationResource(HypnoshroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/hypnoshroom.json");
    }
}
