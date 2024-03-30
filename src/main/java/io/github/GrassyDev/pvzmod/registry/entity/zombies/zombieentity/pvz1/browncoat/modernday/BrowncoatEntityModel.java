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
			return new Identifier("pvzmod", "geo/summerbasic.geo.json");
		}
		else if (object.getType().equals(PvZEntity.SUMMERCONEHEAD) ||
				object.getType().equals(PvZEntity.SUMMERCONEHEADHYPNO)){
			return new Identifier("pvzmod", "geo/summercone.geo.json");
		}
		else if (object.getType().equals(PvZEntity.SUMMERBUCKETHEAD) ||
				object.getType().equals(PvZEntity.SUMMERBUCKETHEADHYPNO)){
			return new Identifier("pvzmod", "geo/summerbucket.geo.json");
		}
		else {
			return BrowncoatEntityRenderer.LOCATION_MODEL_BY_VARIANT.get(object.getVariant());
		}
    }

    @Override
    public Identifier getTextureResource(BrowncoatEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_gearless.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_geardmg1.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/browncoat_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BrowncoatEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
