package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.bombseedling;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class BombSeedlingEntityModel extends GeoModel<BombSeedlingEntity> {

    @Override
    public Identifier getModelResource(BombSeedlingEntity object)
    {
        return Identifier.of("pvzmod", "geo/bombseedling.geo.json");
    }

    @Override
    public Identifier getTextureResource(BombSeedlingEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/small/bombseedling.png");
    }

    @Override
    public Identifier getAnimationResource(BombSeedlingEntity object)
    {
        return Identifier.of ("pvzmod", "animations/small.json");
    }
}
