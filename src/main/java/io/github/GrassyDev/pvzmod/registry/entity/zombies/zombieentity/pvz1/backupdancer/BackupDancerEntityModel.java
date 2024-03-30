package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.backupdancer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BackupDancerEntityModel extends GeoModel<BackupDancerEntity> {

    @Override
    public Identifier getModelResource(BackupDancerEntity object)
    {
        return new Identifier("pvzmod", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureResource(BackupDancerEntity object) {
		Identifier identifier;
		identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer.png");
		if (object.armless && object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_gearless.png");
		} else if (object.geardmg) {
			identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_geardmg1.png");
		} else if (object.armless) {
			identifier = new Identifier("pvzmod", "textures/entity/dancingzombie/backupdancer_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BackupDancerEntity object)
    {
        return new Identifier ("pvzmod", "animations/backupdancer.json");
    }
}
