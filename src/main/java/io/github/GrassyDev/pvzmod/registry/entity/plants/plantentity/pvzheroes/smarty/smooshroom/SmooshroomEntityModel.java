package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.smooshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SmooshroomEntityModel extends AnimatedGeoModel<SmooshroomEntity> {

    @Override
    public Identifier getModelResource(SmooshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/smooshroom.geo.json");
    }

	public Identifier getTextureResource(SmooshroomEntity object) {
		return new Identifier("pvzmod", "textures/entity/smooshroom/smooshroom.png");
	}

    @Override
    public Identifier getAnimationResource(SmooshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/smooshroom.json");
    }
}
