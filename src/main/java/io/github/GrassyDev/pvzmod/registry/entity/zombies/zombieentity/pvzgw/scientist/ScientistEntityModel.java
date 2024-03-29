package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.scientist;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ScientistEntityModel extends AnimatedGeoModel<ScientistEntity> {

    @Override
    public Identifier getModelResource(ScientistEntity object)
    {
		return new Identifier("pvzmod", "geo/scientist.geo.json");
    }

    @Override
    public Identifier getTextureResource(ScientistEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/scientist/scientist.png");
		if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/scientist/scientist_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(ScientistEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
