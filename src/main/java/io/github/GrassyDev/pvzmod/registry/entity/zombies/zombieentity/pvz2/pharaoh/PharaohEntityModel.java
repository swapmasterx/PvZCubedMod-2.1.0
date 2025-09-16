package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.pharaoh;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.PharaohVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PharaohEntityModel extends GeoModel<PharaohEntity> {

    @Override
    public Identifier getModelResource(PharaohEntity object)
    {
		Identifier identifier;
		if (object.getVariant().equals(PharaohVariants.SUMMONED) || object.getVariant().equals(PharaohVariants.SUMMONEDHYPNO)){
			identifier = Identifier.of ("pvzmod", "geo/pharaoh.geo.json");
		}
		else {
			identifier = Identifier.of ("pvzmod", "geo/undyingpharaoh.geo.json");
		}
		return identifier;
    }

    @Override
    public Identifier getTextureResource(PharaohEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/browncoat/mummy/mummy.png");
		if (object.armless && object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/mummy/mummy_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/mummy/mummy_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/mummy/mummy_gearless.png");
		} else if (object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/mummy/mummy_geardmg1.png");
		} else if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/browncoat/mummy/mummy_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(PharaohEntity object)
    {
        return Identifier.of ("pvzmod", "animations/pharaoh.json");
    }
}
