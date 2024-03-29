package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.octo;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class OctoEntityModel extends AnimatedGeoModel<OctoEntity> {

    @Override
    public Identifier getModelResource(OctoEntity object)
    {
		return new Identifier("pvzmod", "geo/octo.geo.json");
    }

    @Override
    public Identifier getTextureResource(OctoEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/bully/octo.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/octo_dmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/octo_dmg1.png");
		} else if (object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/octo.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/octo.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/octo_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(OctoEntity object)
    {
        return new Identifier ("pvzmod", "animations/bully.json");
    }
}
