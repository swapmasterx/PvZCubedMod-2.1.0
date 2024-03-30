package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.peanut;

import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShootingPeaVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PeanutBowlingEntityModel extends GeoModel<PeanutBowlingEntity> {

    @Override
    public Identifier getModelResource(PeanutBowlingEntity object)
    {
        return new Identifier("pvzmod", "geo/peanut.geo.json");
    }

    @Override
    public Identifier getTextureResource(PeanutBowlingEntity object)
    {
		if (object.getVariant().equals(ShootingPeaVariants.DEFAULT)) {
			return new Identifier("pvzmod", "textures/entity/wallnut/peanut.png");
		}
		else if (object.getVariant().equals(ShootingPeaVariants.BLACK)) {
			return new Identifier("pvzmod", "textures/entity/wallnut/peanut_dmg1.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/wallnut/peanut_dmg2.png");
		}
    }

    @Override
    public Identifier getAnimationResource(PeanutBowlingEntity object)
    {
        return new Identifier ("pvzmod", "animations/peanut.json");
    }
}
