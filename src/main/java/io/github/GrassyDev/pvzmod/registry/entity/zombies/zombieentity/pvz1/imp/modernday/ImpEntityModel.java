package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ImpEntityModel extends GeoModel<ImpEntity> {

    @Override
    public Identifier getModelResource(ImpEntity object)
    {
		return ImpEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
    }

    @Override
    public Identifier getTextureResource(ImpEntity object)
    {
		Identifier identifier;
		if (object.getVariant().equals(ImpVariants.IMPDRAGON) || object.getVariant().equals(ImpVariants.IMPDRAGONHYPNO)) {
			identifier = Identifier.of("pvzmod", "textures/entity/imp/impdragon.png");
		}
		else if (object.getVariant().equals(ImpVariants.MUMMY) || object.getVariant().equals(ImpVariants.MUMMYHYPNO)) {
			identifier = Identifier.of("pvzmod", "textures/entity/imp/impmummy.png");
		}
		else if (object.getVariant().equals(ImpVariants.BASSIMP) || object.getVariant().equals(ImpVariants.BASSIMPHYPNO)) {
			identifier = Identifier.of("pvzmod", "textures/entity/imp/rainbowbassimp.png");
		}
		else if (object.getVariant().equals(ImpVariants.SCRAP) || object.getVariant().equals(ImpVariants.SCRAPHYPNO)) {
			if (object.getHealth() <= 0) {
				identifier = Identifier.of("pvzmod", "textures/entity/imp/scrapimp_headless.png");
			}
			else {
				identifier = Identifier.of("pvzmod", "textures/entity/imp/scrapimp.png");
			}
		}
		else if (object.getVariant().equals(ImpVariants.CINDERELLA) || object.getVariant().equals(ImpVariants.CINDERELLAHYPNO)) {
			if (object.gear1less) {
				identifier = Identifier.of("pvzmod", "textures/entity/imp/cinderellaimp_gearless.png");
			}
			else {
				identifier = Identifier.of("pvzmod", "textures/entity/imp/cinderellaimp.png");
			}
		}
		else {
			identifier = Identifier.of("pvzmod", "textures/entity/imp/imp.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(ImpEntity object)
    {
        return Identifier.of ("pvzmod", "animations/imp.json");
    }
}
