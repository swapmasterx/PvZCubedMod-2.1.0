package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.modernday.shadowshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShadowShroomEntityModel extends GeoModel<ShadowShroomEntity> {

    @Override
    public Identifier getModelResource(ShadowShroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/shadowshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShadowShroomEntity object)
    {
		if (object.getShadowPowered()){
			return Identifier.of("pvzmod", "textures/entity/shadowshroom/shadowshroom_shadow.png");
		}
		else {
			return Identifier.of("pvzmod", "textures/entity/shadowshroom/shadowshroom.png");
		}
    }

    @Override
    public Identifier getAnimationResource(ShadowShroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/shadowshroom.json");
    }
}
