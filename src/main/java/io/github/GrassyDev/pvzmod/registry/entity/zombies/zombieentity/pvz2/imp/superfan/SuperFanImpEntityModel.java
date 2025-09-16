package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan;

import io.github.GrassyDev.pvzmod.registry.entity.variants.zombies.ImpVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

/*
 * A renderer is used to provide an entity model, shadow size, and texture.
 */
public class SuperFanImpEntityModel extends GeoModel<SuperFanImpEntity> {

    @Override
    public Identifier getModelResource(SuperFanImpEntity object)
    {
        return Identifier.of("pvzmod", "geo/superfanimp.geo.json");
    }

    @Override
    public Identifier getTextureResource(SuperFanImpEntity object) {
		Identifier identifier;
		if (object.getVariant().equals(ImpVariants.NEWYEAR) || object.getVariant().equals(ImpVariants.NEWYEARHYPNO)) {
			identifier = Identifier.of("pvzmod", "textures/entity/imp/newyearimp.png");
		}
		else{
			identifier = Identifier.of("pvzmod", "textures/entity/imp/superfanimp.png");
		}
		return identifier;
	}

    @Override
    public Identifier getAnimationResource(SuperFanImpEntity object)
    {
        return Identifier.of ("pvzmod", "animations/imp.json");
    }
}
