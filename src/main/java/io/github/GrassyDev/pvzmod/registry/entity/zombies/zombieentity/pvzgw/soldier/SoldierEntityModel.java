package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.soldier;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class SoldierEntityModel extends GeoModel<SoldierEntity> {

    @Override
    public Identifier getModelResource(SoldierEntity object)
    {
		return Identifier.of("pvzmod", "geo/soldier.geo.json");
    }

    @Override
    public Identifier getTextureResource(SoldierEntity object) {
		Identifier identifier;
		if (object.getZPGStage()){
			identifier = Identifier.of("pvzmod", "textures/entity/soldier/soldier.png");
			if (object.armless && object.geardmg) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/soldier_dmg1_geardmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/soldier_gearless_dmg1.png");
			} else if (object.gear1less) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/soldier_gearless.png");
			} else if (object.geardmg) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/soldier_geardmg1.png");
			} else if (object.armless) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/soldier_dmg1.png");
			}
		}
		else {
			identifier = Identifier.of("pvzmod", "textures/entity/soldier/rocketless/soldier.png");
			if (object.armless && object.geardmg) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/rocketless/soldier_dmg1_geardmg1.png");
			} else if (object.armless && object.gear1less) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/rocketless/soldier_gearless_dmg1.png");
			} else if (object.gear1less) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/rocketless/soldier_gearless.png");
			} else if (object.geardmg) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/rocketless/soldier_geardmg1.png");
			} else if (object.armless) {
				identifier = Identifier.of("pvzmod", "textures/entity/soldier/rocketless/soldier_dmg1.png");
			}
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(SoldierEntity object)
    {
        return Identifier.of ("pvzmod", "animations/newbrowncoat.json");
    }
}
