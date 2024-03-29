package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.burstshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BurstShroomEntityModel extends AnimatedGeoModel<BurstshroomEntity> {

    @Override
    public Identifier getModelResource(BurstshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/burstshroom.geo.json");
    }

	public Identifier getTextureResource(BurstshroomEntity object) {
		return new Identifier("pvzmod", "textures/entity/burstshroom/burstshroom.png");
	}

    @Override
    public Identifier getAnimationResource(BurstshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/burstshroom.json");
    }
}
