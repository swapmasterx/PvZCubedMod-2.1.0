package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.future;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class FutureZombieEntityModel extends GeoModel<FutureZombieEntity> {

    @Override
    public Identifier getModelResource(FutureZombieEntity object)
    {
		return FutureZombieEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(FutureZombieEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/browncoat/future/future.png");
		if (object.armless && object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/future/future_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/future/future_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/future/future_gearless.png");
		} else if (object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/future/future_geardmg1.png");
		} else if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/future/future_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(FutureZombieEntity object)
    {
        return Identifier.of ("pvzmod", "animations/newbrowncoat.json");
    }
}
