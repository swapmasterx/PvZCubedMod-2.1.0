package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.dolphinrider;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class DolphinRiderEntityModel extends GeoModel<DolphinRiderEntity> {

    @Override
    public Identifier getModelResource(DolphinRiderEntity object)
    {
        return new Identifier("pvzmod", "geo/dolphinrider.geo.json");
    }

    @Override
    public Identifier getTextureResource(DolphinRiderEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/dolphinrider/dolphinrider.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/dolphinrider/dolphinrider_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(DolphinRiderEntity object)
    {
        return new Identifier ("pvzmod", "animations/dolphinrider.json");
    }
}
