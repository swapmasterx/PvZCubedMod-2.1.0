package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.knightpea;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class KnightPeaEntityModel extends GeoModel<KnightPeaEntity> {

    @Override
    public Identifier getModelResource(KnightPeaEntity object)
    {
        return new Identifier("pvzmod", "geo/knightpea.geo.json");
    }

    @Override
    public Identifier getTextureResource(KnightPeaEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/peashooter/knightpea.png");
    }

    @Override
    public Identifier getAnimationResource(KnightPeaEntity object)
    {
        return new Identifier ("pvzmod", "animations/peashooter.json");
    }
}
