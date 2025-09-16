package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.zombie;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class HawkerZombieEntityModel extends GeoModel<HawkerZombieEntity> {

    @Override
    public Identifier getModelResource(HawkerZombieEntity object)
    {
		return Identifier.of("pvzmod", "geo/hawker.geo.json");
    }

    @Override
    public Identifier getTextureResource(HawkerZombieEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/hawker/hawker.png");
		if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/hawker/hawker_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(HawkerZombieEntity object)
    {
        return Identifier.of ("pvzmod", "animations/hawker.json");
    }
}
