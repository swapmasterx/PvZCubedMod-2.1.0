package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BullyEntityModel extends GeoModel<BullyEntity> {

    @Override
    public Identifier getModelResource(BullyEntity object)
    {
		return BullyEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(BullyEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/bully/bully.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/bully_dmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/bully_dmg1.png");
		} else if (object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/bully.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/bully.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/bully/bully_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BullyEntity object)
    {
        return new Identifier ("pvzmod", "animations/bully.json");
    }
}
