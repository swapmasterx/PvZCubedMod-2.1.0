package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.potted.acidshroom;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class AcidshroomEntityModel extends GeoModel<AcidshroomEntity> {

    @Override
    public Identifier getModelResource(AcidshroomEntity object)
    {
        return Identifier.of("pvzmod", "geo/acidshroom.geo.json");
    }

	public Identifier getTextureResource(AcidshroomEntity object) {
		return Identifier.of(PvZCubed.MOD_ID, "textures/entity/fumeshroom/acidshroom.png");
	}

    @Override
    public Identifier getAnimationResource(AcidshroomEntity object)
    {
        return Identifier.of ("pvzmod", "animations/fumeshroom.json");
    }
}
