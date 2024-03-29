package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PumpkinCarEntityModel extends AnimatedGeoModel<PumpkinCarEntity> {

    @Override
    public Identifier getModelResource(PumpkinCarEntity object)
    {
		return new Identifier("pvzmod", "geo/pumpkincar.geo.json");
    }

    @Override
    public Identifier getTextureResource(PumpkinCarEntity object) {
		Identifier identifier = new Identifier("pvzmod", "textures/entity/pumpkincar/pumpkincar.png");
		return identifier;
	}

    @Override
    public Identifier getAnimationResource(PumpkinCarEntity object)
    {
        return new Identifier ("pvzmod", "animations/pumpkincar.json");
    }
}
