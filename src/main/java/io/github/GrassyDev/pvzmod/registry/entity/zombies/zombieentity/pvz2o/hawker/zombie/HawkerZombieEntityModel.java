package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.zombie;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HawkerZombieEntityModel extends AnimatedGeoModel<HawkerZombieEntity> {

    @Override
    public Identifier getModelResource(HawkerZombieEntity object)
    {
		return new Identifier("pvzmod", "geo/hawker.geo.json");
    }

    @Override
    public Identifier getTextureResource(HawkerZombieEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/hawker/hawker.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/hawker/hawker_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(HawkerZombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/hawker.json");
    }
}
