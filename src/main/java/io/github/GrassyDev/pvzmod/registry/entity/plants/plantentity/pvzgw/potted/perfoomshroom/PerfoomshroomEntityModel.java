package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.potted.perfoomshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PerfoomshroomEntityModel extends GeoModel<PerfoomshroomEntity> {

    @Override
    public Identifier getModelResource(PerfoomshroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/doomshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(PerfoomshroomEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/doomshroom/doomshroom_perfume.png");
    }

    @Override
    public Identifier getAnimationResource(PerfoomshroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/doomshroom.json");
    }
}
