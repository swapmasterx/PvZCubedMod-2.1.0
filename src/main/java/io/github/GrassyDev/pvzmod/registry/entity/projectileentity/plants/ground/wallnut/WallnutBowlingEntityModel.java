package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.wallnut;

import io.github.GrassyDev.pvzmod.registry.entity.variants.projectiles.ShootingPeaVariants;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WallnutBowlingEntityModel extends GeoModel<WallnutBowlingEntity> {

    @Override
    public Identifier getModelResource(WallnutBowlingEntity object)
    {
        return new Identifier("pvzmod", "geo/wallnut.geo.json");
    }

    @Override
    public Identifier getTextureResource(WallnutBowlingEntity object)
    {
		if (object.getVariant().equals(ShootingPeaVariants.DEFAULT)) {
			return new Identifier("pvzmod", "textures/entity/wallnut/wallnut.png");
		}
		else if (object.getVariant().equals(ShootingPeaVariants.BLACK)) {
			return new Identifier("pvzmod", "textures/entity/wallnut/wallnut_dmg1.png");
		}
		else {
			return new Identifier("pvzmod", "textures/entity/wallnut/wallnut_dmg2.png");
		}
    }

    @Override
    public Identifier getAnimationResource(WallnutBowlingEntity object)
    {
        return new Identifier ("pvzmod", "animations/wallnut.json");
    }
}
