package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.piggy;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PiggyEntityModel extends GeoModel<PiggyEntity> {

    @Override
    public Identifier getModelResource(PiggyEntity object)
    {
		return Identifier.of("pvzmod", "geo/zombiepiggy.geo.json");
    }

    @Override
    public Identifier getTextureResource(PiggyEntity object)
    {
		return Identifier.of("pvzmod", "textures/entity/hawker/piggy.png");
    }

    @Override
    public Identifier getAnimationResource(PiggyEntity object)
    {
        return Identifier.of ("pvzmod", "animations/piggy.json");
    }
}
