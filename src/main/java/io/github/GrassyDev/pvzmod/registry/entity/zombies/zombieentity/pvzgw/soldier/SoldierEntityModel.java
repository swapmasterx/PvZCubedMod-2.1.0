package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.soldier;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SoldierEntityModel extends AnimatedGeoModel<SoldierEntity> {

    @Override
    public Identifier getModelResource(SoldierEntity object)
    {
		return new Identifier("pvzmod", "geo/soldier.geo.json");
    }

    @Override
    public Identifier getTextureResource(SoldierEntity object) {
		Identifier identifier;
		if (object.getZPGStage()){
			identifier = new Identifier("pvzmod", "textures/entity/soldier/soldier.png");
			if (object.armless && object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/soldier_dmg1_geardmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/soldier_gearless_dmg1.png");
			} else if (object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/soldier_gearless.png");
			} else if (object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/soldier_geardmg1.png");
			} else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/soldier_dmg1.png");
			}
		}
		else {
			identifier = new Identifier("pvzmod", "textures/entity/soldier/rocketless/soldier.png");
			if (object.armless && object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/rocketless/soldier_dmg1_geardmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/rocketless/soldier_gearless_dmg1.png");
			} else if (object.gear1less) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/rocketless/soldier_gearless.png");
			} else if (object.geardmg) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/rocketless/soldier_geardmg1.png");
			} else if (object.armless) {
				identifier = new Identifier("pvzmod", "textures/entity/soldier/rocketless/soldier_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(SoldierEntity object)
    {
        return new Identifier ("pvzmod", "animations/newbrowncoat.json");
    }
}
