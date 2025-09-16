package io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class ShootingPowerBeeSpikeEntityModel extends GeoModel<ShootingPowerBeeSpikeEntity> {

    @Override
    public Identifier getModelResource(ShootingPowerBeeSpikeEntity object)
    {
        return Identifier.of("pvzmod", "geo/spike.geo.json");
    }

    @Override
    public Identifier getTextureResource(ShootingPowerBeeSpikeEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/projectiles/powerbeespike.png");
    }

    @Override
    public Identifier getAnimationResource(ShootingPowerBeeSpikeEntity object)
    {
        return Identifier.of ("pvzmod", "animations/peashot.json");
    }
}
