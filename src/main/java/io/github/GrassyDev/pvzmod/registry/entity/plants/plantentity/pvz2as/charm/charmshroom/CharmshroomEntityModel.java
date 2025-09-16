package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.charmshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CharmshroomEntityModel extends GeoModel<CharmshroomEntity> {

    @Override
    public Identifier getModelResource(CharmshroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/charmshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(CharmshroomEntity object)
    {
		if (object.getMoonPowered()) {
			return Identifier.of("pvzmod", "textures/entity/hypnoshroom/charmshroom_shadow.png");
		}
		else {
			return Identifier.of("pvzmod", "textures/entity/hypnoshroom/charmshroom.png");
		}
    }

    @Override
    public Identifier getAnimationResource(CharmshroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/charmshroom.json");
    }
}
