package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.rainbowbullet;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class RainbowBulletEntityModel extends GeoModel<RainbowBulletEntity> {

    @Override
    public Identifier getModelResource(RainbowBulletEntity object)
    {
        return new Identifier("pvzmod", "geo/peashot.geo.json");
    }

    @Override
    public Identifier getTextureResource(RainbowBulletEntity object){
		return new Identifier(PvZCubed.MOD_ID, "textures/entity/projectiles/rainbowbullet.png");
	}

    @Override
    public Identifier getAnimationResource(RainbowBulletEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashot.json");
    }
}
