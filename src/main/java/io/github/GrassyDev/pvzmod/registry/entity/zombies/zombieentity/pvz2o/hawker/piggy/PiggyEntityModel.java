package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.piggy;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PiggyEntityModel extends AnimatedGeoModel<PiggyEntity> {

    @Override
    public Identifier getModelResource(PiggyEntity object)
    {
		return new Identifier("pvzmod", "geo/zombiepiggy.geo.json");
    }

    @Override
    public Identifier getTextureResource(PiggyEntity object)
    {
		return new Identifier("pvzmod", "textures/entity/hawker/piggy.png");
    }

    @Override
    public Identifier getAnimationResource(PiggyEntity object)
    {
        return new Identifier ("pvzmod", "animations/piggy.json");
    }
}
