package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.charmshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CharmshroomEntityModel extends GeoModel<CharmshroomEntity> {

    @Override
    public Identifier getModelResource(CharmshroomEntity object)
    {
        return new Identifier("pvzmod", "geo/charmshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(CharmshroomEntity object)
    {
		if (object.getMoonPowered()) {
			return new Identifier("pvzmod", "textures/entity/hypnoshroom/charmshroom_shadow.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/hypnoshroom/charmshroom.png");
		}
    }

    @Override
    public Identifier getAnimationResource(CharmshroomEntity object)
    {
        return new Identifier ("pvzmod", "animations/charmshroom.json");
    }
}
