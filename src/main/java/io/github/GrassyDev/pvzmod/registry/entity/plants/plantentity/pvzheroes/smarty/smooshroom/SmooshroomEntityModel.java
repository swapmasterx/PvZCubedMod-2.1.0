package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.smooshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SmooshroomEntityModel extends GeoModel<SmooshroomEntity> {

    @Override
    public Identifier getModelResource(SmooshroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/smooshroom.geo.json");
    }

	public Identifier getTextureResource(SmooshroomEntity object) {
		return Identifier.of("pvzmod", "textures/entity/smooshroom/smooshroom.png");
	}

    @Override
    public Identifier getAnimationResource(SmooshroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/smooshroom.json");
    }
}
