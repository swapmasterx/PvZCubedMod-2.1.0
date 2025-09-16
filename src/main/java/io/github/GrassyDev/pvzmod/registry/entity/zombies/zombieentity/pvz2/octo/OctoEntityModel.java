package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.octo;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class OctoEntityModel extends GeoModel<OctoEntity> {

    @Override
    public Identifier getModelResource(OctoEntity object)
    {
		return Identifier.of("pvzmod", "geo/octo.geo.json");
    }

    @Override
    public Identifier getTextureResource(OctoEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/bully/octo.png");
		if (object.armless && object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/octo_dmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/octo_dmg1.png");
		} else if (object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/octo.png");
		} else if (object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/octo.png");
		} else if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/octo_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(OctoEntity object)
    {
        return Identifier.of ("pvzmod", "animations/bully.json");
    }
}
