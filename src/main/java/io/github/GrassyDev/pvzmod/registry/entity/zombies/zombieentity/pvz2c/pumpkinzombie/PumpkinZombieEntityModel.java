package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.pumpkinzombie;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class PumpkinZombieEntityModel extends GeoModel<PumpkinZombieEntity> {

    @Override
    public Identifier getModelResource(PumpkinZombieEntity object)
    {
		return new Identifier("pvzmod", "geo/pumpkinzombie.geo.json");
    }

    @Override
    public Identifier getTextureResource(PumpkinZombieEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_gearless.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_geardmg1.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/browncoat/pumpkin/pumpkinzombie_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(PumpkinZombieEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
