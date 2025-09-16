package io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.backupdancer;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BackupDancerEntityModel extends GeoModel<BackupDancerEntity> {

    @Override
    public Identifier getModelResource(BackupDancerEntity object)
    {
        return Identifier.of("pvzmod", "geo/backupdancer.geo.json");
    }

    @Override
    public Identifier getTextureResource(BackupDancerEntity object) {
		Identifier identifier;
		identifier = Identifier.of("pvzmod", "textures/entity/dancingzombie/backupdancer.png");
		if (object.armless && object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/dancingzombie/backupdancer_dmg1_geardmg1.png");
		} else if (object.armless && object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/dancingzombie/backupdancer_gearless_dmg1.png");
		} else if (object.gear1less) {
			identifier = Identifier.of("pvzmod", "textures/entity/dancingzombie/backupdancer_gearless.png");
		} else if (object.geardmg) {
			identifier = Identifier.of("pvzmod", "textures/entity/dancingzombie/backupdancer_geardmg1.png");
		} else if (object.armless) {
			identifier = Identifier.of("pvzmod", "textures/entity/dancingzombie/backupdancer_dmg1.png");
		}
		return identifier;
    }

    @Override
    public Identifier getAnimationResource(BackupDancerEntity object)
    {
        return Identifier.of ("pvzmod", "animations/backupdancer.json");
    }
}
