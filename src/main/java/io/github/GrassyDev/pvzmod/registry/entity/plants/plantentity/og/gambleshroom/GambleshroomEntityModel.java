package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class GambleshroomEntityModel extends GeoModel<GambleshroomEntity> {

    @Override
    public Identifier getModelResource(GambleshroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/gambleshroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(GambleshroomEntity object)
    {
		Identifier identifier = Identifier.of("pvzmod", "textures/entity/magicshroom/gambleshroom.png");
		if (!object.hasHat()){
			identifier = Identifier.of("pvzmod", "textures/entity/magicshroom/gambleshroom_hatless.png");
		}
        return identifier;
    }

    @Override
    public Identifier getAnimationResource(GambleshroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/magicshroom.json");
    }
}
