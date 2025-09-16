package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SunshroomEntityModel extends GeoModel<SunshroomEntity> {

    @Override
    public Identifier getModelResource(SunshroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/sunshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(SunshroomEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/sunshroom/sunshroom.png");
    }

    @Override
    public Identifier getAnimationResource(SunshroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/sunshroom.json");
    }
}
