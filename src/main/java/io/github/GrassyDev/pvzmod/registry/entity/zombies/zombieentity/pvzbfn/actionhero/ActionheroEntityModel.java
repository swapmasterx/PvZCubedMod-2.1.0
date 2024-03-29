package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.actionhero;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ActionheroEntityModel extends AnimatedGeoModel<ActionheroEntity> {

    @Override
    public Identifier getModelResource(ActionheroEntity object)
    {
		return new Identifier("pvzmod", "geo/80sactionhero.geo.json");
    }

    @Override
    public Identifier getTextureResource(ActionheroEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/bully/80sactionhero.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/80sactionhero_dmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/80sactionhero_dmg1.png");
		} else if (object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/80sactionhero.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/80sactionhero.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/80sactionhero_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(ActionheroEntity object)
    {
        return new Identifier ("pvzmod", "animations/bully.json");
    }
}
