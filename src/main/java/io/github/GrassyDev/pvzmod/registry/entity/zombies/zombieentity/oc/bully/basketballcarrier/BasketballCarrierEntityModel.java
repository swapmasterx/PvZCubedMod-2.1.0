package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basketballcarrier;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BasketballCarrierEntityModel extends GeoModel<BasketballCarrierEntity> {

    @Override
    public Identifier getModelResource(BasketballCarrierEntity object)
    {
		return BasketballCarrierEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(BasketballCarrierEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/bully/basketballcarrier.png");
		if (object.armless && object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/basketballcarrier_dmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/basketballcarrier_dmg1.png");
		} else if (object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/basketballcarrier.png");
		} else if (object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/basketballcarrier.png");
		} else if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/bully/basketballcarrier_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BasketballCarrierEntity object)
    {
        return Identifier.of ("pvzmod", "animations/bully.json");
    }
}
