package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday;

import io.github.GrassyDev.pvzmod.registry.PvZEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BrowncoatEntityModel extends GeoModel<BrowncoatEntity> {

    @Override
    public Identifier getModelResource(BrowncoatEntity object)
    {
		if (object.getType().equals(PvZEntity.SUMMERBASIC) ||
				object.getType().equals(PvZEntity.SUMMERBASICHYPNO)){
			return Identifier.of("pvzmod", "geo/summerbasic.geo.json");
		}
		else if (object.getType().equals(PvZEntity.SUMMERCONEHEAD) ||
				object.getType().equals(PvZEntity.SUMMERCONEHEADHYPNO)){
			return Identifier.of("pvzmod", "geo/summercone.geo.json");
		}
		else if (object.getType().equals(PvZEntity.SUMMERBUCKETHEAD) ||
				object.getType().equals(PvZEntity.SUMMERBUCKETHEADHYPNO)){
			return Identifier.of("pvzmod", "geo/summerbucket.geo.json");
		}
		else {
			return BrowncoatEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
		}
    }

    @Override
    public Identifier getTextureResource(BrowncoatEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat.png");
		if (object.armless && object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat_gearless.png");
		} else if (object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat_geardmg1.png");
		} else if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BrowncoatEntity object)
    {
        return Identifier.of ("pvzmod", "animations/newbrowncoat.json");
    }
}
