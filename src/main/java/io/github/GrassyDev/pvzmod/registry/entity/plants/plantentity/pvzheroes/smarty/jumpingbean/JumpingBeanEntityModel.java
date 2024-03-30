package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.jumpingbean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class JumpingBeanEntityModel extends GeoModel<JumpingBeanEntity> {

    @Override
    public Identifier getModelResource(JumpingBeanEntity object)
    {
        return new Identifier("pvzmod", "geo/jumpingbean.geo.json");
    }

    @Override
    public Identifier getTextureResource(JumpingBeanEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/springbean/jumpingbean.png");
    }

    @Override
    public Identifier getAnimationResource(JumpingBeanEntity object)
    {
        return new Identifier ("pvzmod", "animations/jumpingbean.json");
    }
}
