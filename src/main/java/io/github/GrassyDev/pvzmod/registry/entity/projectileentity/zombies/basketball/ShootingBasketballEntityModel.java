package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.basketball;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingBasketballEntityModel extends GeoModel<ShootingBasketballEntity> {

    @Override
    public Identifier getModelResource(ShootingBasketballEntity object)
    {
        return new Identifier("pvzmod", "geo/basketball.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingBasketballEntity object){
			return new Identifier("pvzmod", "textures/entity/obstacles/basketballbin.png");
	}

    @Override
    public Identifier getAnimationResource(ShootingBasketballEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
