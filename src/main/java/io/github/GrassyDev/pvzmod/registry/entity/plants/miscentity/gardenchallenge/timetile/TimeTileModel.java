package io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.timetile;

import io.github.GrassyDev.pvzmod.registry.entity.variants.challenge.ChallengeTime;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;

public class TimeTileModel extends GeoModel<TimeTile> {

    @Override
    public Identifier getModelResource(TimeTile object)
    {
		Identifier identifier = new Identifier("pvzmod", "geo/suntile.geo.json");
		if (object.getTime().equals(ChallengeTime.FULLMOON)){
			identifier = new Identifier("pvzmod", "geo/moontile.geo.json");
		}
		else if (object.getTime().equals(ChallengeTime.HALFMOON)){
			identifier = new Identifier("pvzmod", "geo/halfmoontile.geo.json");
		}else if (object.getTime().equals(ChallengeTime.NEWMOON)){
			identifier = new Identifier("pvzmod", "geo/newmoontile.geo.json");
		}else if (object.getTime().equals(ChallengeTime.BOMB)){
			identifier = new Identifier("pvzmod", "geo/sunbombtile.geo.json");
		}else if (object.getTime().equals(ChallengeTime.DROUGHT)){
			identifier = new Identifier("pvzmod", "geo/droughttile.geo.json");
		}
		return identifier;
    }

    @Override
    public Identifier getTextureResource(TimeTile object)
    {
        return new Identifier("pvzmod", "textures/entity/environment/timetiles.png");
    }

    @Override
    public Identifier getAnimationResource(TimeTile object)
    {
        return new Identifier ("pvzmod", "animations/tile.json");
    }
}
