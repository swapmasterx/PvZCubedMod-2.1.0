package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.fog.seashroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SeashroomEntityModel extends GeoModel<SeashroomEntity> {

    @Override
    public Identifier getModelResource(SeashroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/seashroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(SeashroomEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/puffshroom/seashroom.png");
    }

    @Override
    public Identifier getAnimationResource(SeashroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/puffshroom.json");
    }
}
