package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.modernday.shadowshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ShadowShroomEntityModel extends AnimatedGeoModel<ShadowShroomEntity> {

    @Override
    public Identifier getModelResource(ShadowShroomEntity object)
    {
        return new Identifier("pvzmod", "geo/shadowshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShadowShroomEntity object)
    {
		if (object.getShadowPowered()){
			return new Identifier("pvzmod", "textures/entity/shadowshroom/shadowshroom_shadow.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/shadowshroom/shadowshroom.png");
		}
    }

    @Override
    public Identifier getAnimationResource(ShadowShroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/shadowshroom.json");
    }
}
