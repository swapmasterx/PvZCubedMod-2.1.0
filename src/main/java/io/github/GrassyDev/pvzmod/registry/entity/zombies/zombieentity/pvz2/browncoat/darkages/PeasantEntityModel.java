package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.darkages;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PeasantEntityModel extends GeoModel<PeasantEntity> {

    @Override
    public Identifier getModelResource(PeasantEntity object)
    {
		return PeasantEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(PeasantEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_gearless.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_geardmg1.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/peasant/peasant_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(PeasantEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
