package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.weathertile;

import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.ChallengeWeather;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class WeatherTileModel extends GeoModel<WeatherTile> {

    @Override
    public Identifier getModelResource(WeatherTile object)
    {
		Identifier identifier = Identifier.of("pvzmod", "geo/cloudtile.geo.json");
		if (object.getWeather().equals(ChallengeWeather.RAIN)){
			identifier = Identifier.of("pvzmod", "geo/raintile.geo.json");
		}
		else if (object.getWeather().equals(ChallengeWeather.THUNDER)){
			identifier = Identifier.of("pvzmod", "geo/thundertile.geo.json");
		}
		return identifier;
    }

    @Override
    public Identifier getTextureResource(WeatherTile object)
    {
        return Identifier.of("pvzmod", "textures/entity/environment/weathertiles.png");
    }

    @Override
    public Identifier getAnimationResource(WeatherTile object)
    {
        return Identifier.of ("pvzmod", "animations/tile.json");
    }
}
