package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.mummy;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class MummyEntityModel extends GeoModel<MummyEntity> {

    @Override
    public Identifier getModelResource(MummyEntity object)
    {
		return MummyEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(MummyEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_gearless.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_geardmg1.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/mummy/mummy_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(MummyEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
