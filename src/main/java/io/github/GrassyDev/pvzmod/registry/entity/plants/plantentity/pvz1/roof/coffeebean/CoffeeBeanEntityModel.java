package io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.coffeebean;

import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class CoffeeBeanEntityModel extends GeoModel<CoffeeBeanEntity> {

    @Override
    public Identifier getModelResource(CoffeeBeanEntity object)
    {
        return Identifier.of("pvzmod", "geo/coffeebean.geo.json");
    }

    @Override
    public Identifier getTextureResource(CoffeeBeanEntity object)
    {
        return Identifier.of("pvzmod", "textures/entity/bean/coffeebean.png");
    }

    @Override
    public Identifier getAnimationResource(CoffeeBeanEntity object)
    {
        return Identifier.of ("pvzmod", "animations/coffeebean.json");
    }
}
