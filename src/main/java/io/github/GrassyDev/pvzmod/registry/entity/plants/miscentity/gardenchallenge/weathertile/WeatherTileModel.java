package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.weathertile;

import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.ChallengeWeather;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WeatherTileModel extends AnimatedGeoModel<WeatherTile> {

    @Override
    public Identifier getModelResource(WeatherTile object)
    {
		Identifier identifier = new Identifier("pvzmod", "geo/cloudtile.geo.json");
		if (object.getWeather().equals(ChallengeWeather.RAIN)){
			identifier = new Identifier("pvzmod", "geo/raintile.geo.json");
		}
		else if (object.getWeather().equals(ChallengeWeather.THUNDER)){
			identifier = new Identifier("pvzmod", "geo/thundertile.geo.json");
		}
		return identifier;
    }

    @Override
    public Identifier getTextureResource(WeatherTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/weathertiles.png");
    }

    @Override
    public Identifier getAnimationResource(WeatherTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
