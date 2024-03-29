package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.coffeebean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class CoffeeBeanEntityModel extends AnimatedGeoModel<CoffeeBeanEntity> {

    @Override
    public Identifier getModelResource(CoffeeBeanEntity object)
    {
        return new Identifier("pvzmod", "geo/coffeebean.geo.json");
    }

    @Override
    public Identifier getTextureResource(CoffeeBeanEntity object)
    {
        return new Identifier("pvzmod", "textures/entity/bean/coffeebean.png");
    }

    @Override
    public Identifier getAnimationResource(CoffeeBeanEntity object)
    {
        return new Identifier ("pvzmod", "animations/coffeebean.json");
    }
}
