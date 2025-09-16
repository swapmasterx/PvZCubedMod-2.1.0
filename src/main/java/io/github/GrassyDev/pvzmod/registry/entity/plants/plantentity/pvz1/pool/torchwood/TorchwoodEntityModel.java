package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TorchwoodEntityModel extends GeoModel<TorchwoodEntity> {

    @Override
    public Identifier getModelResource(TorchwoodEntity object)
    {
        return Identifier.of("pvzmod", "geo/torchwood.geo.json");
    }

    @Override
    public Identifier getTextureResource(TorchwoodEntity object)
    {
		return object.isWet()? Identifier.of ("pvzmod", "textures/entity/torchwood/torchwood_extinguished.png") :
				Identifier.of ("pvzmod", "textures/entity/torchwood/torchwood.png");
    }

    @Override
    public Identifier getAnimationResource(TorchwoodEntity object)
    {
        return Identifier.of ("pvzmod", "animations/torchwood.json");
    }
}
