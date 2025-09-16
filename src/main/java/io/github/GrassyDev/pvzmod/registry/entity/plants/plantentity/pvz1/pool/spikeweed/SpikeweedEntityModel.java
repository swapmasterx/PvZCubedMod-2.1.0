package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SpikeweedEntityModel extends GeoModel<SpikeweedEntity> {

    @Override
    public Identifier getModelResource(SpikeweedEntity object)
    {
        return Identifier.of("pvzmod", "geo/spikeweed.geo.json");
    }

    @Override
    public Identifier getTextureResource(SpikeweedEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/spikeweed/spikeweed.png");
    }

    @Override
    public Identifier getAnimationResource(SpikeweedEntity object)
    {
        return Identifier.of ("pvzmod", "animations/spikeweed.json");
    }
}
