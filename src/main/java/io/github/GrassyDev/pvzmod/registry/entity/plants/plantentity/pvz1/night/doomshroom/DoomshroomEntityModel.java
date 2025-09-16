package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DoomshroomEntityModel extends GeoModel<DoomshroomEntity> {

    @Override
    public Identifier getModelResource(DoomshroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/doomshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(DoomshroomEntity object)
    {
		if (object.getShadowPowered()) {
			return Identifier.of("pvzmod", "textures/entity/doomshroom/doomshroom_shadow.png");
		}
		else {
			return Identifier.of("pvzmod", "textures/entity/doomshroom/doomshroom.png");
		}
    }

    @Override
    public Identifier getAnimationResource(DoomshroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/doomshroom.json");
    }
}
