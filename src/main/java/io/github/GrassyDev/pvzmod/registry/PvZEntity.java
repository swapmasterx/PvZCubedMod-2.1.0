package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile.BananaTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.cheesetile.CheeseTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.cratertile.CraterTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.goldtile.GoldTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.maritile.MariTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.rifttile.RiftTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.rosebuds.RoseBudTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowFullTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.solarwinds.SolarWinds;
import io.github.GrassyDev.pvzmod.registry.entity.environment.springtile.SpringTile;
import io.github.GrassyDev.pvzmod.registry.entity.environment.sunbomb.SunBombEntity;
import io.github.GrassyDev.pvzmod.registry.entity.environment.target.missiletoe.MissileToeTarget;
import io.github.GrassyDev.pvzmod.registry.entity.environment.watertile.WaterTile;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave.DarkAgesGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone.EgyptGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.fairytaleforest.FairyTaleGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave.FutureGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.mausoleum.MausoleumGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave.RoofGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.timetile.TimeTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.weathertile.WeatherTile;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom.GamblehatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom.GambleshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb.CherrybombEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine.PotatomineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.repeater.RepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea.SnowpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity.WallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.fog.magnetshroom.MagnetshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.fog.seashroom.SeashroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom.DoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom.IceshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom.ScaredyshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.FireTrailEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.JalapenoEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilyPadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut.TallnutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp.TangleKelpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood.TorchwoodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.coffeebean.CoffeeBeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.icebergpult.IcebergpultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.cattail.CattailEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea.GatlingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gloomshroom.GloomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.OxygaeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.breezeshroom.BreezeshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.burstshroom.BurstshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.superchomper.SuperChomperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.bloomerang.BloomerangEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.farfuture.empeach.EMPeachEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.frostbitecaves.pepperpult.PepperpultEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.electropea.ElectropeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.ghostpepper.GhostpepperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.gloomvine.GloomVineEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.missiletoe.MissileToeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit.OlivePitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.peanut.PeanutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.endurian.EndurianEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.goldleaf.GoldLeafEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.modernday.shadowshroom.ShadowShroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon.CoconutCannonEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.springbean.SpringbeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.lightningreed.LightningReedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.frisbloom.FrisbloomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.beautyshroom.BeautyshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.charmshroom.CharmshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.magnet.MagnetoShroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.fairytale.springprincess.SpringPrincessEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.hammerflower.HammerFlowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.impatyens.ImpatyensEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom.MagichatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom.MagicshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.meteorhammer.MeteorHammerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.narcissus.NarcissusEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.pumpkinwitch.PumpkinWitchEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.tulimpeter.TulimpeterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.heian.dripphylleia.DripphylleiaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.kongfu.heavenlypeach.HeavenlyPeachEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.renaissance.oilyolive.OilyOliveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.saucer.SaucerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz3.devour.dogwood.DogwoodEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter.BeeshooterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beet.BeetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.chillypepper.ChillyPepperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.shamrock.ShamrockEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea.SnowqueenpeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.doomrose.DoomRoseEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.knightpea.KnightPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.nightcap.NightcapEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.chester.ChesterEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.retrogatling.RetroGatlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.vampireflower.VampireFlowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.potted.acidshroom.AcidshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.potted.perfoomshroom.PerfoomshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.summon.plants.dandelionweed.DandelionWeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smackadamia.SmackadamiaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.bombseedling.BombSeedlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.zapricot.ZapricotEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.megagrow.bananasaurus.BananasaurusEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.admiralnavybean.AdmiralNavyBeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.jumpingbean.JumpingBeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.navybean.NavyBeanEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.smooshroom.SmooshroomEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.weeniebeanie.WeenieBeanieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.bellflower.BellflowerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.sunflowerseed.SunflowerSeedEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.armor.MetalHelmetProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.groundbounce.GroundBounceEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.peanut.PeanutBowlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.wallnut.WallnutBowlingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.cabbage.ShootingCabbageEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.iceberg.ShootingIcebergEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.peanut.PeaNutProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pepper.ShootingPepperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pumpkinproj.ShootingPumpkinEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smooshproj.SmooshProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.acidfume.AcidFumeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.acidspore.AcidSporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.bark.BarkEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.boomerang.ShootingBoomerangEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.breeze.BreezeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.bubbles.BubbleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.card.ShootingCardEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.electricpea.ShootingElectricPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.frisbee.ShootingFrisbeeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.fume.FumeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.jingle.JingleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercespore.PierceSporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.FirePiercePeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.sky.missiletoeproj.MissileToeProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike.ShootingBeeSpikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike.ShootingPowerBeeSpikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.icespike.ShootingIcespikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.icespike.ShootingPowerIcespikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.spike.ShootingPowerSpikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.spike.ShootingSpikeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword.ShootingPowerSwordEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword.ShootingSwordEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.armorbubble.ArmorBubbleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.coconut.CoconutEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dye.ShootingDyeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.flamingpea.ShootingFlamingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.hypnoproj.HypnoProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.pea.ShootingPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.plasmapea.ShootingPlasmaPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.rainbowbullet.RainbowBulletEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowpea.ShootingSnowPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowqueenpea.ShootingSnowqueenPeaEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spit.SpitEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spore.SporeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.banana.BananaProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.cheese.CheeseProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.springproj.SpringProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.basketball.ShootingBasketballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.bone.BoneProjEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook.FlamingBookEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.laser.LaserEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.octo.ShootingOctoEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.rocket.RocketEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.soundwave.SoundwaveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.zpg.ZPGEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm.LocustSwarmEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basketballcarrier.BasketballCarrierEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.backupdancer.BackupDancerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.bobsledteam.BobsledRiderEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.dancingzombie.DancingZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.dolphinrider.DolphinRiderEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.flagzombie.modernday.FlagzombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.football.FootballEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.newspaper.NewspaperEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.polevaulting.PoleVaultingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.zomboni.ZomboniEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.darkages.PeasantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.future.FutureZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.mummy.MummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.explorer.ExplorerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.darkages.FlagPeasantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.future.FlagFutureEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.mummy.FlagMummyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.summer.FlagSummerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer.AnnouncerImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan.SuperFanImpEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.jetpack.JetpackEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.octo.OctoEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.pharaoh.PharaohEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.zombieking.ZombieKingEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.bass.BassZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.browncoat.fairytale.PokerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.flagzombie.fairytale.FlagPokerEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.pumpkinzombie.PumpkinZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.browncoat.sargeant.SargeantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.flagzombie.sargeant.FlagSargeantEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.piggy.PiggyEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.zombie.HawkerZombieEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.actionhero.ActionheroEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.zmech.ScrapMechEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.hovergoat.HoverGoatEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.scientist.ScientistEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.soldier.SoldierEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzh.zomblob.ZomblobEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiegrave.ZombieGraveEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.MetalVehicleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.speakervehicle.SpeakerVehicleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.robocone.RoboConeEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.crystalhelmet.CrystalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.fleshobstacle.FleshObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle.MetalObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield.MetalShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield.NewspaperShieldEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.planthelmet.PlantHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plantobstacle.WoodObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plastichelmet.PlasticHelmetEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.rockobstacle.RockObstacleEntity;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.stonehelmet.StoneHelmetEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.entity.extensions.api.QuiltEntityTypeBuilder;

import java.util.ArrayList;
import java.util.List;

import static io.github.GrassyDev.pvzmod.PvZCubed.MOD_ID;

public class PvZEntity{

	public static final String ModID = "pvzmod"; // This is just so we can refer to our ModID easier.



	// REMEMBER TO REGISTER NEW PLANTS HERE TOO

	//List for bomb seedling
	public static void setPlantList(){
		PLANT_LIST.add(PvZEntity.PEASHOOTER);
		PLANT_LIST.add(PvZEntity.SUNFLOWER);
		PLANT_LIST.add(PvZEntity.SNOWPEA);
		PLANT_LIST.add(PvZEntity.CHOMPER);
		PLANT_LIST.add(PvZEntity.REPEATER);
		PLANT_LIST.add(PvZEntity.SUNSHROOM);
		PLANT_LIST.add(PvZEntity.FUMESHROOM);
		PLANT_LIST.add(PvZEntity.SCAREDYSHROOM);
		PLANT_LIST.add(PvZEntity.SQUASH);
		PLANT_LIST.add(PvZEntity.SEASHROOM);
		PLANT_LIST.add(PvZEntity.MAGNETSHROOM);
		PLANT_LIST.add(PvZEntity.CABBAGEPULT);
		PLANT_LIST.add(PvZEntity.BEET);
		PLANT_LIST.add(PvZEntity.SHAMROCK);
		PLANT_LIST.add(PvZEntity.FRISBLOOM);
		PLANT_LIST.add(PvZEntity.VAMPIREFLOWER);
		PLANT_LIST.add(PvZEntity.DANDELIONWEED);
		PLANT_LIST.add(PvZEntity.SMACKADAMIA);
		PLANT_LIST.add(PvZEntity.BOMBSEEDLING);
		PLANT_LIST.add(PvZEntity.DOGWOOD);
	}

	public static List<EntityType<?>> PLANT_LIST = new ArrayList<>();

	public static final EntityType<GardenEntity> GARDEN = Registry.register(Registries.ENTITY_TYPE,
		new Identifier(ModID, "garden"),
		QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, GardenEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.0f)).build()
	);

	public static final EntityType<PeashooterEntity> PEASHOOTER = Registry.register(Registries.ENTITY_TYPE,
		new Identifier(MOD_ID, "peashooter"),
		QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, PeashooterEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);
	public static final EntityType<PuffshroomEntity> PUFFSHROOM = Registry.register(Registries.ENTITY_TYPE,
		new Identifier(ModID, "puffshroom"),
		QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, PuffshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<GardenChallengeEntity> GARDENCHALLENGE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "gardenchallenge"),
			QuiltEntityTypeBuilder.create(SpawnGroup.CREATURE, GardenChallengeEntity::new).setDimensions(EntityDimensions.fixed(3f, 5f)).build()
	);

	public static final EntityType<CoffeeBeanEntity> COFFEEBEAN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "coffeebean"),
			QuiltEntityTypeBuilder.<CoffeeBeanEntity>create(SpawnGroup.CREATURE, CoffeeBeanEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

    public static final EntityType<SunflowerEntity> SUNFLOWER = Registry.register((
            Registries.ENTITY_TYPE),
            new Identifier(ModID, "sunflower"),
            QuiltEntityTypeBuilder.<SunflowerEntity>create(SpawnGroup.CREATURE, SunflowerEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
            );

    public static final EntityType<CherrybombEntity> CHERRYBOMB = Registry.register((
                    Registries.ENTITY_TYPE),
            new Identifier(ModID, "cherrybomb"),
            QuiltEntityTypeBuilder.<CherrybombEntity>create(SpawnGroup.CREATURE, CherrybombEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

    public static final EntityType<WallnutEntity> WALLNUT = Registry.register((
                    Registries.ENTITY_TYPE),
            new Identifier(ModID, "wallnut"),
            QuiltEntityTypeBuilder.<WallnutEntity>create(SpawnGroup.CREATURE, WallnutEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
    );

    public static final EntityType<PotatomineEntity> POTATOMINE = Registry.register((
                    Registries.ENTITY_TYPE),
            new Identifier(ModID, "potatomine"),
            QuiltEntityTypeBuilder.<PotatomineEntity>create(SpawnGroup.CREATURE, PotatomineEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

    public static final EntityType<SnowpeaEntity> SNOWPEA = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "snowpea"),
            QuiltEntityTypeBuilder.<SnowpeaEntity>create(SpawnGroup.CREATURE, SnowpeaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

    public static final EntityType<ChomperEntity> CHOMPER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "chomper"),
            QuiltEntityTypeBuilder.<ChomperEntity>create(SpawnGroup.CREATURE, ChomperEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
    );

	public static final EntityType<ChesterEntity> CHESTER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "chester"),
			QuiltEntityTypeBuilder.<ChesterEntity>create(SpawnGroup.CREATURE, ChesterEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType<VampireFlowerEntity> VAMPIREFLOWER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "vampireflower"),
			QuiltEntityTypeBuilder.<VampireFlowerEntity>create(SpawnGroup.CREATURE, VampireFlowerEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

    public static final EntityType<RepeaterEntity> REPEATER = Registry.register((
                    Registries.ENTITY_TYPE),
            new Identifier(ModID, "repeater"),
            QuiltEntityTypeBuilder.<RepeaterEntity>create(SpawnGroup.CREATURE, RepeaterEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );



    public static final EntityType<SunshroomEntity> SUNSHROOM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "sunshroom"),
            QuiltEntityTypeBuilder.<SunshroomEntity>create(SpawnGroup.CREATURE, SunshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

    public static final EntityType <FumeshroomEntity> FUMESHROOM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "fumeshroom"),
            QuiltEntityTypeBuilder.<FumeshroomEntity>create(SpawnGroup.CREATURE, FumeshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
    );

	public static final EntityType<OxygaeEntity> OXYGAE = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "oxygae"),
			QuiltEntityTypeBuilder.<OxygaeEntity>create(SpawnGroup.CREATURE, OxygaeEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.4f)).build()
	);
	public static final EntityType<BubblePadEntity> BUBBLEPAD = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "bubblepad"),
			QuiltEntityTypeBuilder.<BubblePadEntity>create(SpawnGroup.CREATURE, BubblePadEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.5f)).build()
	);

	public static final EntityType <BreezeshroomEntity> BREEZESHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "breezeshroom"),
			QuiltEntityTypeBuilder.<BreezeshroomEntity>create(SpawnGroup.CREATURE, BreezeshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType <BurstshroomEntity> BURSTSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "burstshroom"),
			QuiltEntityTypeBuilder.<BurstshroomEntity>create(SpawnGroup.CREATURE, BurstshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1f)).build()
	);

	public static final EntityType<SuperChomperEntity> SUPERCHOMPER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "superchomper"),
			QuiltEntityTypeBuilder.<SuperChomperEntity>create(SpawnGroup.CREATURE, SuperChomperEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType<GravebusterEntity> GRAVEBUSTER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "gravebuster"),
            QuiltEntityTypeBuilder.<GravebusterEntity>create(SpawnGroup.CREATURE, GravebusterEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
    );

    public static final EntityType<HypnoshroomEntity> HYPNOSHROOM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "hypnoshroom"),
            QuiltEntityTypeBuilder.<HypnoshroomEntity>create(SpawnGroup.CREATURE, HypnoshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

    public static final EntityType<ScaredyshroomEntity> SCAREDYSHROOM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "scaredyshroom"),
            QuiltEntityTypeBuilder.<ScaredyshroomEntity>create(SpawnGroup.CREATURE, ScaredyshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

    public static final EntityType<IceshroomEntity> ICESHROOM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "iceshroom"),
            QuiltEntityTypeBuilder.<IceshroomEntity>create(SpawnGroup.CREATURE, IceshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

    public static final EntityType<DoomshroomEntity> DOOMSHROOM = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "doomshroom"),
            QuiltEntityTypeBuilder.<DoomshroomEntity>create(SpawnGroup.CREATURE, DoomshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

	public static final EntityType<LilyPadEntity> LILYPAD = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "lilypad"),
			QuiltEntityTypeBuilder.<LilyPadEntity>create(SpawnGroup.CREATURE, LilyPadEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.25f)).build()
	);

	public static final EntityType<SquashEntity> SQUASH = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "squash"),
			QuiltEntityTypeBuilder.<SquashEntity>create(SpawnGroup.CREATURE, SquashEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

    public static final EntityType<ThreepeaterEntity> THREEPEATER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "threepeater"),
            QuiltEntityTypeBuilder.<ThreepeaterEntity>create(SpawnGroup.CREATURE, ThreepeaterEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

	public static final EntityType<TangleKelpEntity> TANGLE_KELP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "tanglekelp"),
			QuiltEntityTypeBuilder.<TangleKelpEntity>create(SpawnGroup.CREATURE, TangleKelpEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.1f)).build()
	);

	public static final EntityType<JalapenoEntity> JALAPENO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "jalapeno"),
			QuiltEntityTypeBuilder.<JalapenoEntity>create(SpawnGroup.CREATURE, JalapenoEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1f)).build()
	);
	public static final EntityType<FireTrailEntity> FIRETRAIL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "firetrail"),
			QuiltEntityTypeBuilder.<FireTrailEntity>create(SpawnGroup.CREATURE, FireTrailEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<SpikeweedEntity> SPIKEWEED = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "spikeweed"),
			QuiltEntityTypeBuilder.<SpikeweedEntity>create(SpawnGroup.CREATURE, SpikeweedEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.125f)).build()
	);

	public static final EntityType<TorchwoodEntity> TORCHWOOD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "torchwood"),
			QuiltEntityTypeBuilder.<TorchwoodEntity>create(SpawnGroup.CREATURE, TorchwoodEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 2f)).build()
	);

	public static final EntityType<TallnutEntity> TALLNUT = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "tallnut"),
			QuiltEntityTypeBuilder.<TallnutEntity>create(SpawnGroup.CREATURE, TallnutEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 3.75f)).build()
	);

	public static final EntityType<SeashroomEntity> SEASHROOM = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "seashroom"),
			QuiltEntityTypeBuilder.<SeashroomEntity>create(SpawnGroup.CREATURE, SeashroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType <MagnetshroomEntity> MAGNETSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "magnetshroom"),
			QuiltEntityTypeBuilder.<MagnetshroomEntity>create(SpawnGroup.CREATURE, MagnetshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType <MagnetoShroomEntity> MAGNETOSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "magnetoshroom"),
			QuiltEntityTypeBuilder.<MagnetoShroomEntity>create(SpawnGroup.CREATURE, MagnetoShroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType<CabbagepultEntity> CABBAGEPULT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cabbagepult"),
			QuiltEntityTypeBuilder.<CabbagepultEntity>create(SpawnGroup.CREATURE, CabbagepultEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<GatlingpeaEntity> GATLINGPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "gatlingpea"),
			QuiltEntityTypeBuilder.<GatlingpeaEntity>create(SpawnGroup.CREATURE, GatlingpeaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<TwinSunflowerEntity> TWINSUNFLOWER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "twinsunflower"),
			QuiltEntityTypeBuilder.<TwinSunflowerEntity>create(SpawnGroup.CREATURE, TwinSunflowerEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType<GloomshroomEntity> GLOOMSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "gloomshroom"),
			QuiltEntityTypeBuilder.<GloomshroomEntity>create(SpawnGroup.CREATURE, GloomshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<CattailEntity> CATTAIL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cattail"),
			QuiltEntityTypeBuilder.<CattailEntity>create(SpawnGroup.CREATURE, CattailEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<SpikerockEntity> SPIKEROCK = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "spikerock"),
			QuiltEntityTypeBuilder.<SpikerockEntity>create(SpawnGroup.CREATURE, SpikerockEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.125f)).build()
	);

	public static final EntityType<IcebergpultEntity> ICEBERGPULT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "icebergpult"),
			QuiltEntityTypeBuilder.<IcebergpultEntity>create(SpawnGroup.CREATURE, IcebergpultEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<BeetEntity> BEET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "beet"),
			QuiltEntityTypeBuilder.<BeetEntity>create(SpawnGroup.CREATURE, BeetEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<ShamrockEntity> SHAMROCK = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "shamrock"),
			QuiltEntityTypeBuilder.<ShamrockEntity>create(SpawnGroup.CREATURE, ShamrockEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<ChillyPepperEntity> CHILLYPEPPER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "chillypepper"),
			QuiltEntityTypeBuilder.<ChillyPepperEntity>create(SpawnGroup.CREATURE, ChillyPepperEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<BeeshooterEntity> BEESHOOTER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "beeshooter"),
			QuiltEntityTypeBuilder.<BeeshooterEntity>create(SpawnGroup.CREATURE, BeeshooterEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<SnowqueenpeaEntity> SNOWQUEENPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "snowqueenpea"),
			QuiltEntityTypeBuilder.<SnowqueenpeaEntity>create(SpawnGroup.CREATURE, SnowqueenpeaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<RetroGatlingEntity> RETROGATLING = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "retrogatling"),
			QuiltEntityTypeBuilder.<RetroGatlingEntity>create(SpawnGroup.CREATURE, RetroGatlingEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType <AcidshroomEntity> ACIDSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "acidshroom"),
			QuiltEntityTypeBuilder.<AcidshroomEntity>create(SpawnGroup.CREATURE, AcidshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType<DandelionWeedEntity> DANDELIONWEED = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "dandelionweed"),
			QuiltEntityTypeBuilder.<DandelionWeedEntity>create(SpawnGroup.CREATURE, DandelionWeedEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType<PerfoomshroomEntity> PERFOOMSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "perfoomshroom"),
			QuiltEntityTypeBuilder.<PerfoomshroomEntity>create(SpawnGroup.CREATURE, PerfoomshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<BloomerangEntity> BLOOMERANG = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bloomerang"),
			QuiltEntityTypeBuilder.<BloomerangEntity>create(SpawnGroup.CREATURE, BloomerangEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1f)).build()
	);

	public static final EntityType<IcebergLettuceEntity> ICEBERGLETTUCE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "iceberglettuce"),
			QuiltEntityTypeBuilder.<IcebergLettuceEntity>create(SpawnGroup.CREATURE, IcebergLettuceEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<SpringbeanEntity> SPRINGBEAN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "springbean"),
			QuiltEntityTypeBuilder.<SpringbeanEntity>create(SpawnGroup.CREATURE, SpringbeanEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<CoconutCannonEntity> COCONUTCANNON = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "coconutcannon"),
			QuiltEntityTypeBuilder.<CoconutCannonEntity>create(SpawnGroup.CREATURE, CoconutCannonEntity::new).setDimensions(EntityDimensions.fixed(2f, 1.8f)).build()
	);

	public static final EntityType<LightningReedEntity> LIGHTNINGREED = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "lightningreed"),
			QuiltEntityTypeBuilder.<LightningReedEntity>create(SpawnGroup.CREATURE, LightningReedEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<PeapodEntity> PEAPOD = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "peapod"),
			QuiltEntityTypeBuilder.<PeapodEntity>create(SpawnGroup.CREATURE, PeapodEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.8f)).build()
	);

	public static final EntityType<EMPeachEntity> EMPEACH = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "empeach"),
			QuiltEntityTypeBuilder.<EMPeachEntity>create(SpawnGroup.CREATURE, EMPeachEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<PepperpultEntity> PEPPERPULT = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "pepperpult"),
			QuiltEntityTypeBuilder.<PepperpultEntity>create(SpawnGroup.CREATURE, PepperpultEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

    public static final EntityType<FlamingpeaEntity> FLAMINGPEA = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "flamingpea"),
            QuiltEntityTypeBuilder.<FlamingpeaEntity>create(SpawnGroup.CREATURE, FlamingpeaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
    );

	public static final EntityType<EndurianEntity> ENDURIAN = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "endurian"),
			QuiltEntityTypeBuilder.<EndurianEntity>create(SpawnGroup.CREATURE, EndurianEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType<GoldLeafEntity> GOLDLEAF = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "goldleaf"),
			QuiltEntityTypeBuilder.<GoldLeafEntity>create(SpawnGroup.CREATURE, GoldLeafEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<ShadowShroomEntity> SHADOWSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "shadowshroom"),
			QuiltEntityTypeBuilder.<ShadowShroomEntity>create(SpawnGroup.CREATURE, ShadowShroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<MissileToeEntity> MISSILETOE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "missiletoe"),
			QuiltEntityTypeBuilder.<MissileToeEntity>create(SpawnGroup.CREATURE, MissileToeEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<ElectropeaEntity> ELECTROPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "electropea"),
			QuiltEntityTypeBuilder.<ElectropeaEntity>create(SpawnGroup.CREATURE, ElectropeaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<PeanutEntity> PEANUT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peanut"),
			QuiltEntityTypeBuilder.<PeanutEntity>create(SpawnGroup.CREATURE, PeanutEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.95f)).build()
	);


	public static final EntityType<GhostpepperEntity> GHOSTPEPPER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "ghostpepper"),
			QuiltEntityTypeBuilder.<GhostpepperEntity>create(SpawnGroup.CREATURE, GhostpepperEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<OlivePitEntity> OLIVEPIT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "olivepit"),
			QuiltEntityTypeBuilder.<OlivePitEntity>create(SpawnGroup.CREATURE, OlivePitEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.125f)).build()
	);
	public static final EntityType<GloomVineEntity> GLOOMVINE = Registry.register((
					Registries.ENTITY_TYPE),
			new Identifier(ModID, "gloomvine"),
			QuiltEntityTypeBuilder.<GloomVineEntity>create(SpawnGroup.CREATURE, GloomVineEntity::new).setDimensions(EntityDimensions.fixed(1f, 0.5f)).build()
	);

	public static final EntityType<HeavenlyPeachEntity> HEAVENLYPEACH = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "heavenlypeach"),
			QuiltEntityTypeBuilder.<HeavenlyPeachEntity>create(SpawnGroup.CREATURE, HeavenlyPeachEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.4f)).build()
	);

	public static final EntityType<MagicshroomEntity> MAGICSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "magicshroom"),
			QuiltEntityTypeBuilder.<MagicshroomEntity>create(SpawnGroup.CREATURE, MagicshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1f)).build()
	);

	public static final EntityType<MagichatEntity> MAGICHAT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "magichat"),
			QuiltEntityTypeBuilder.<MagichatEntity>create(SpawnGroup.CREATURE, MagichatEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 2.6f)).build()
	);

	public static final EntityType<GambleshroomEntity> GAMBLESHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "gambleshroom"),
			QuiltEntityTypeBuilder.<GambleshroomEntity>create(SpawnGroup.CREATURE, GambleshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1f)).build()
	);

	public static final EntityType<GamblehatEntity> GAMBLEHAT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "gamblehat"),
			QuiltEntityTypeBuilder.<GamblehatEntity>create(SpawnGroup.CREATURE, GamblehatEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 2.6f)).build()
	);

	public static final EntityType<SaucerEntity> SAUCER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "saucer"),
			QuiltEntityTypeBuilder.<SaucerEntity>create(SpawnGroup.CREATURE, SaucerEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 2.65f)).build()
	);

	public static final EntityType<DripphylleiaEntity> DRIPPHYLLEIA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "dripphylleia"),
			QuiltEntityTypeBuilder.<DripphylleiaEntity>create(SpawnGroup.CREATURE, DripphylleiaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1f)).build()
	);

	public static final EntityType<PumpkinWitchEntity> PUMPKINWITCH = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pumpkinwitch"),
			QuiltEntityTypeBuilder.<PumpkinWitchEntity>create(SpawnGroup.CREATURE, PumpkinWitchEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1f)).build()
	);

	public static final EntityType<TulimpeterEntity> TULIMPETER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "tulimpeter"),
			QuiltEntityTypeBuilder.<TulimpeterEntity>create(SpawnGroup.CREATURE, TulimpeterEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<NarcissusEntity> NARCISSUS = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "narcissus"),
			QuiltEntityTypeBuilder.<NarcissusEntity>create(SpawnGroup.CREATURE, NarcissusEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);

	public static final EntityType<ImpatyensEntity> IMPATYENS = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "impatyens"),
			QuiltEntityTypeBuilder.<ImpatyensEntity>create(SpawnGroup.CREATURE, ImpatyensEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<HammerFlowerEntity> HAMMERFLOWER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "hammerflower"),
			QuiltEntityTypeBuilder.<HammerFlowerEntity>create(SpawnGroup.CREATURE, HammerFlowerEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<MeteorHammerEntity> METEORHAMMER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "meteorhammer"),
			QuiltEntityTypeBuilder.<MeteorHammerEntity>create(SpawnGroup.CREATURE, MeteorHammerEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<FrisbloomEntity> FRISBLOOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "frisbloom"),
			QuiltEntityTypeBuilder.<FrisbloomEntity>create(SpawnGroup.CREATURE, FrisbloomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1f)).build()
	);

	public static final EntityType<BeautyshroomEntity> BEAUTYSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "beautyshroom"),
			QuiltEntityTypeBuilder.<BeautyshroomEntity>create(SpawnGroup.CREATURE, BeautyshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<CharmshroomEntity> CHARMSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "charmshroom"),
			QuiltEntityTypeBuilder.<CharmshroomEntity>create(SpawnGroup.CREATURE, CharmshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);


	public static final EntityType<SmackadamiaEntity> SMACKADAMIA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "smackadamia"),
			QuiltEntityTypeBuilder.<SmackadamiaEntity>create(SpawnGroup.CREATURE, SmackadamiaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 2.65f)).build()
	);

	public static final EntityType<BombSeedlingEntity> BOMBSEEDLING = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bombseedling"),
			QuiltEntityTypeBuilder.<BombSeedlingEntity>create(SpawnGroup.CREATURE, BombSeedlingEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<OilyOliveEntity> OILYOLIVE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "oilyolive"),
			QuiltEntityTypeBuilder.<OilyOliveEntity>create(SpawnGroup.CREATURE, OilyOliveEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<SpringPrincessEntity> SPRINGPRINCESS = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "springprincess"),
			QuiltEntityTypeBuilder.<SpringPrincessEntity>create(SpawnGroup.CREATURE, SpringPrincessEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<ZapricotEntity> ZAPRICOT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zapricot"),
			QuiltEntityTypeBuilder.<ZapricotEntity>create(SpawnGroup.CREATURE, ZapricotEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<BananasaurusEntity> BANANASAURUS = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bananasaurus"),
			QuiltEntityTypeBuilder.<BananasaurusEntity>create(SpawnGroup.CREATURE, BananasaurusEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.3f)).build()
	);

	public static final EntityType<WeenieBeanieEntity> WEENIEBEANIE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "weeniebeanie"),
			QuiltEntityTypeBuilder.<WeenieBeanieEntity>create(SpawnGroup.CREATURE, WeenieBeanieEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<SmooshroomEntity> SMOOSHROOM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "smooshroom"),
			QuiltEntityTypeBuilder.<SmooshroomEntity>create(SpawnGroup.CREATURE, SmooshroomEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<NavyBeanEntity> NAVYBEAN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "navybean"),
			QuiltEntityTypeBuilder.<NavyBeanEntity>create(SpawnGroup.CREATURE, NavyBeanEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<AdmiralNavyBeanEntity> ADMIRALNAVYBEAN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "admiralnavybean"),
			QuiltEntityTypeBuilder.<AdmiralNavyBeanEntity>create(SpawnGroup.CREATURE, AdmiralNavyBeanEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<JumpingBeanEntity> JUMPINGBEAN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "jumpingbean"),
			QuiltEntityTypeBuilder.<JumpingBeanEntity>create(SpawnGroup.CREATURE, JumpingBeanEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<SunflowerSeedEntity> SUNFLOWERSEED = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sunflowerseed"),
			QuiltEntityTypeBuilder.<SunflowerSeedEntity>create(SpawnGroup.CREATURE, SunflowerSeedEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<BellflowerEntity> BELLFLOWER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bellflower"),
			QuiltEntityTypeBuilder.<BellflowerEntity>create(SpawnGroup.CREATURE, BellflowerEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<KnightPeaEntity> KNIGHTPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "knightpea"),
			QuiltEntityTypeBuilder.<KnightPeaEntity>create(SpawnGroup.CREATURE, KnightPeaEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<NightcapEntity> NIGHTCAP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "nightcap"),
			QuiltEntityTypeBuilder.<NightcapEntity>create(SpawnGroup.CREATURE, NightcapEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 0.8f)).build()
	);

	public static final EntityType<DoomRoseEntity> DOOMROSE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "doomrose"),
			QuiltEntityTypeBuilder.<DoomRoseEntity>create(SpawnGroup.CREATURE, DoomRoseEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.25f)).build()
	);

	public static final EntityType<DogwoodEntity> DOGWOOD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "dogwood"),
			QuiltEntityTypeBuilder.<DogwoodEntity>create(SpawnGroup.CREATURE, DogwoodEntity::new).setDimensions(EntityDimensions.fixed(0.99f, 1.55f)).build()
	);


    /////////////////////////////////////////////////////////////////////////////////////////////////

    public static final EntityType<ShootingPeaEntity> PEA = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "pea"),
            QuiltEntityTypeBuilder.<ShootingPeaEntity>create(SpawnGroup.MISC, ShootingPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
    );

	public static final EntityType<SpitEntity> SPIT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "spit"),
			QuiltEntityTypeBuilder.<SpitEntity>create(SpawnGroup.MISC, SpitEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingSnowPeaEntity> SNOWPEAPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "snowpeaproj"),
			QuiltEntityTypeBuilder.<ShootingSnowPeaEntity>create(SpawnGroup.MISC, ShootingSnowPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingSnowqueenPeaEntity> SNOWQUEENPEAPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "snowqueenpeaproj"),
			QuiltEntityTypeBuilder.<ShootingSnowqueenPeaEntity>create(SpawnGroup.MISC, ShootingSnowqueenPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<CoconutEntity> COCONUTPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "coconutproj"),
			QuiltEntityTypeBuilder.<CoconutEntity>create(SpawnGroup.MISC, CoconutEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<PiercePeaEntity> PIERCEPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "piercepea"),
			QuiltEntityTypeBuilder.<PiercePeaEntity>create(SpawnGroup.MISC, PiercePeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<FirePiercePeaEntity> FIREPIERCEPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "firepiercepea"),
			QuiltEntityTypeBuilder.<FirePiercePeaEntity>create(SpawnGroup.MISC, FirePiercePeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingSpikeEntity> SPIKEPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "spikeproj"),
			QuiltEntityTypeBuilder.<ShootingSpikeEntity>create(SpawnGroup.MISC, ShootingSpikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingPowerSpikeEntity> POWERSPIKE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "elecspike"),
			QuiltEntityTypeBuilder.<ShootingPowerSpikeEntity>create(SpawnGroup.MISC, ShootingPowerSpikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingSwordEntity> SWORDPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "swordproj"),
			QuiltEntityTypeBuilder.<ShootingSwordEntity>create(SpawnGroup.MISC, ShootingSwordEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingPowerSwordEntity> POWERSWORDPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "powerswordproj"),
			QuiltEntityTypeBuilder.<ShootingPowerSwordEntity>create(SpawnGroup.MISC, ShootingPowerSwordEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingPowerBeeSpikeEntity> POWERBEESPIKE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "powerbeespike"),
			QuiltEntityTypeBuilder.<ShootingPowerBeeSpikeEntity>create(SpawnGroup.MISC, ShootingPowerBeeSpikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingPowerIcespikeEntity> POWERICESPIKE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "powericespike"),
			QuiltEntityTypeBuilder.<ShootingPowerIcespikeEntity>create(SpawnGroup.MISC, ShootingPowerIcespikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<RainbowBulletEntity> RAINBOWBULLET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "rainbowbullet"),
			QuiltEntityTypeBuilder.<RainbowBulletEntity>create(SpawnGroup.MISC, RainbowBulletEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingBeeSpikeEntity> BEESPIKE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "beespike"),
			QuiltEntityTypeBuilder.<ShootingBeeSpikeEntity>create(SpawnGroup.MISC, ShootingBeeSpikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingIcespikeEntity> ICESPIKEPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "icespikeproj"),
			QuiltEntityTypeBuilder.<ShootingIcespikeEntity>create(SpawnGroup.MISC, ShootingIcespikeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingFlamingPeaEntity> FIREPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "firepea"),
			QuiltEntityTypeBuilder.<ShootingFlamingPeaEntity>create(SpawnGroup.MISC, ShootingFlamingPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingPlasmaPeaEntity> PLASMAPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "plasmapea"),
			QuiltEntityTypeBuilder.<ShootingPlasmaPeaEntity>create(SpawnGroup.MISC, ShootingPlasmaPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingElectricPeaEntity> ELECTRICPEA = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "electricpea"),
			QuiltEntityTypeBuilder.<ShootingElectricPeaEntity>create(SpawnGroup.MISC, ShootingElectricPeaEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

    public static final EntityType<SporeEntity> SPORE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "spore"),
            QuiltEntityTypeBuilder.<SporeEntity>create(SpawnGroup.MISC, SporeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
    );

	public static final EntityType<FumeEntity> FUME = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "fume"),
			QuiltEntityTypeBuilder.<FumeEntity>create(SpawnGroup.MISC, FumeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<BreezeEntity> BREEZE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "breeze"),
			QuiltEntityTypeBuilder.<BreezeEntity>create(SpawnGroup.MISC, BreezeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<AcidFumeEntity> ACIDFUME = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "acidfume"),
			QuiltEntityTypeBuilder.<AcidFumeEntity>create(SpawnGroup.MISC, AcidFumeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<AcidSporeEntity> ACIDSPORE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "acidspore"),
			QuiltEntityTypeBuilder.<AcidSporeEntity>create(SpawnGroup.MISC, AcidSporeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingCabbageEntity> CABBAGE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cabbage"),
			QuiltEntityTypeBuilder.<ShootingCabbageEntity>create(SpawnGroup.MISC, ShootingCabbageEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<ShootingIcebergEntity> ICEBERG = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "iceberg"),
			QuiltEntityTypeBuilder.<ShootingIcebergEntity>create(SpawnGroup.MISC, ShootingIcebergEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<SmooshProjEntity> SMOOSHPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "smooshproj"),
			QuiltEntityTypeBuilder.<SmooshProjEntity>create(SpawnGroup.MISC, SmooshProjEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<MissileToeProjEntity> MISSILETOEPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "missiletoeproj"),
			QuiltEntityTypeBuilder.<MissileToeProjEntity>create(SpawnGroup.MISC, MissileToeProjEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<BananaProjEntity> BANANAPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bananaproj"),
			QuiltEntityTypeBuilder.<BananaProjEntity>create(SpawnGroup.MISC, BananaProjEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<CheeseProjEntity> CHEESEPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cheeseproj"),
			QuiltEntityTypeBuilder.<CheeseProjEntity>create(SpawnGroup.MISC, CheeseProjEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingBoomerangEntity> BOOMERANGPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "boomerangproj"),
			QuiltEntityTypeBuilder.<ShootingBoomerangEntity>create(SpawnGroup.MISC, ShootingBoomerangEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingCardEntity> CARDPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cardproj"),
			QuiltEntityTypeBuilder.<ShootingCardEntity>create(SpawnGroup.MISC, ShootingCardEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingPepperEntity> PEPPERPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pepperproj"),
			QuiltEntityTypeBuilder.<ShootingPepperEntity>create(SpawnGroup.MISC, ShootingPepperEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<SpringProjEntity> SPRINGPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "springproj"),
			QuiltEntityTypeBuilder.<SpringProjEntity>create(SpawnGroup.MISC, SpringProjEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<ShootingPumpkinEntity> PUMPKINPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pumpkinproj"),
			QuiltEntityTypeBuilder.<ShootingPumpkinEntity>create(SpawnGroup.MISC, ShootingPumpkinEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<HypnoProjEntity> HYPNOPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "hypnoproj"),
			QuiltEntityTypeBuilder.<HypnoProjEntity>create(SpawnGroup.MISC, HypnoProjEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<BubbleEntity> BUBBLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bubble"),
			QuiltEntityTypeBuilder.<BubbleEntity>create(SpawnGroup.MISC, BubbleEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ArmorBubbleEntity> ARMORBUBBLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "armorbubble"),
			QuiltEntityTypeBuilder.<ArmorBubbleEntity>create(SpawnGroup.MISC, ArmorBubbleEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingDyeEntity> DYEPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "dyeproj"),
			QuiltEntityTypeBuilder.<ShootingDyeEntity>create(SpawnGroup.MISC, ShootingDyeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<ShootingFrisbeeEntity> FRISBEEPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "frisbeeproj"),
			QuiltEntityTypeBuilder.<ShootingFrisbeeEntity>create(SpawnGroup.MISC, ShootingFrisbeeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<GroundBounceEntity> GROUNDBOUNCE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "groundbounce"),
			QuiltEntityTypeBuilder.<GroundBounceEntity>create(SpawnGroup.MISC, GroundBounceEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<WallnutBowlingEntity> WALLNUTBOWLING = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "wallnutbowling"),
			QuiltEntityTypeBuilder.<WallnutBowlingEntity>create(SpawnGroup.MISC, WallnutBowlingEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<PeanutBowlingEntity> PEANUTBOWLING = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peanutbowling"),
			QuiltEntityTypeBuilder.<PeanutBowlingEntity>create(SpawnGroup.MISC, PeanutBowlingEntity::new).setDimensions(EntityDimensions.fixed(2.5f,.5f)).build()
	);

	public static final EntityType<PeaNutProjEntity> PEANUTPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peanutproj"),
			QuiltEntityTypeBuilder.<PeaNutProjEntity>create(SpawnGroup.MISC, PeaNutProjEntity::new).setDimensions(EntityDimensions.fixed(1f,.5f)).build()
	);

	public static final EntityType<JingleEntity> JINGLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "jingle"),
			QuiltEntityTypeBuilder.<JingleEntity>create(SpawnGroup.MISC, JingleEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<PierceSporeEntity> PIERCESPORE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "piercespore"),
			QuiltEntityTypeBuilder.<PierceSporeEntity>create(SpawnGroup.MISC, PierceSporeEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	public static final EntityType<BarkEntity> BARK = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bark"),
			QuiltEntityTypeBuilder.<BarkEntity>create(SpawnGroup.MISC, BarkEntity::new).setDimensions(EntityDimensions.fixed(.5f,.5f)).build()
	);

	/////////////////////////////////////////////////////////////////////////////////////////////////

    public static final EntityType<BrowncoatEntity> BROWNCOAT = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "browncoat"),
            QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
    );
	public static final EntityType<BrowncoatEntity> BROWNCOATHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "browncoat_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> CONEHEAD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "conehead"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> CONEHEADHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "conehead_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> BUCKETHEAD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "buckethead"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> BUCKETHEADHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "buckethead_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<BrowncoatEntity> SUMMERBASIC = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "summerbasic"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> SUMMERBASICHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "summerbasic_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> SUMMERCONEHEAD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "summerconehead"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> SUMMERCONEHEADHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "summerconehead_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> SUMMERBUCKETHEAD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "summerbuckethead"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> SUMMERBUCKETHEADHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "summerbuckethead_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FlagSummerEntity> FLAGSUMMER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagsummer"),
			QuiltEntityTypeBuilder.<FlagSummerEntity>create(SpawnGroup.MONSTER, FlagSummerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<FlagSummerEntity> FLAGSUMMERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagsummer_hypnotized"),
			QuiltEntityTypeBuilder.<FlagSummerEntity>create(SpawnGroup.CREATURE, FlagSummerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<BrowncoatEntity> SCREENDOOR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "screendoor"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<BrowncoatEntity> SCREENDOORHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "screendoor_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> BRICKHEAD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "brickhead"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<BrowncoatEntity> BRICKHEADHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "brickhead_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<MummyEntity> MUMMY = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummy"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> MUMMYHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummy_hypnotized"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<FlagMummyEntity> FLAGMUMMY = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagmummy"),
			QuiltEntityTypeBuilder.<FlagMummyEntity>create(SpawnGroup.MONSTER, FlagMummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<FlagMummyEntity> FLAGMUMMYHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagmummy_hypnotized"),
			QuiltEntityTypeBuilder.<FlagMummyEntity>create(SpawnGroup.MONSTER, FlagMummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> MUMMYCONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummycone"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> MUMMYCONEHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummycone_hypnotized"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> MUMMYBUCKET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummybucket"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> MUMMYBUCKETHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummybucket_hypnotized"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> TOMBRAISER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "tombraiser"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> TOMBRAISERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "tombraiser_hypnotized"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> PYRAMIDHEAD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pyramidhead"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MummyEntity> PYRAMIDHEADHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pyramidhead_hypnotized"),
			QuiltEntityTypeBuilder.<MummyEntity>create(SpawnGroup.MONSTER, MummyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<PharaohEntity> UNDYINGPHARAOH = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "undyingpharaoh"),
			QuiltEntityTypeBuilder.<PharaohEntity>create(SpawnGroup.MONSTER, PharaohEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PharaohEntity> UNDYINGPHARAOHHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "undyingpharaoh_hypnotized"),
			QuiltEntityTypeBuilder.<PharaohEntity>create(SpawnGroup.MONSTER, PharaohEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<PharaohEntity> PHARAOH = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pharaoh"),
			QuiltEntityTypeBuilder.<PharaohEntity>create(SpawnGroup.MONSTER, PharaohEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PharaohEntity> PHARAOHHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pharaoh_hypnotized"),
			QuiltEntityTypeBuilder.<PharaohEntity>create(SpawnGroup.MONSTER, PharaohEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<ExplorerEntity> EXPLORER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "explorer"),
			QuiltEntityTypeBuilder.<ExplorerEntity>create(SpawnGroup.MONSTER, ExplorerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<ExplorerEntity> EXPLORERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "explorer_hypnotized"),
			QuiltEntityTypeBuilder.<ExplorerEntity>create(SpawnGroup.MONSTER, ExplorerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<ExplorerEntity> TORCHLIGHT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "torchlight"),
			QuiltEntityTypeBuilder.<ExplorerEntity>create(SpawnGroup.MONSTER, ExplorerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<ExplorerEntity> TORCHLIGHTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "torchlight_hypnotized"),
			QuiltEntityTypeBuilder.<ExplorerEntity>create(SpawnGroup.MONSTER, ExplorerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<PeasantEntity> PEASANT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peasant"),
			QuiltEntityTypeBuilder.<PeasantEntity>create(SpawnGroup.MONSTER, PeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PeasantEntity> PEASANTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peasant_hypnotized"),
			QuiltEntityTypeBuilder.<PeasantEntity>create(SpawnGroup.MONSTER, PeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<FlagPeasantEntity> FLAGPEASANT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagpeasant"),
			QuiltEntityTypeBuilder.<FlagPeasantEntity>create(SpawnGroup.MONSTER, FlagPeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<FlagPeasantEntity> FLAGPEASANTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagpeasant_hypnotized"),
			QuiltEntityTypeBuilder.<FlagPeasantEntity>create(SpawnGroup.MONSTER, FlagPeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PeasantEntity> PEASANTCONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peasantcone"),
			QuiltEntityTypeBuilder.<PeasantEntity>create(SpawnGroup.MONSTER, PeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PeasantEntity> PEASANTCONEHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peasantcone_hypnotized"),
			QuiltEntityTypeBuilder.<PeasantEntity>create(SpawnGroup.MONSTER, PeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PeasantEntity> PEASANTBUCKET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peasantbucket"),
			QuiltEntityTypeBuilder.<PeasantEntity>create(SpawnGroup.MONSTER, PeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PeasantEntity> PEASANTBUCKETHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peasantbucket_hypnotized"),
			QuiltEntityTypeBuilder.<PeasantEntity>create(SpawnGroup.MONSTER, PeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PeasantEntity> PEASANTKNIGHT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peasantknight"),
			QuiltEntityTypeBuilder.<PeasantEntity>create(SpawnGroup.MONSTER, PeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PeasantEntity> PEASANTKNIGHTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "peasantknight_hypnotized"),
			QuiltEntityTypeBuilder.<PeasantEntity>create(SpawnGroup.MONSTER, PeasantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<FutureZombieEntity> FUTUREZOMBIE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "futurezombie"),
			QuiltEntityTypeBuilder.<FutureZombieEntity>create(SpawnGroup.MONSTER, FutureZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FutureZombieEntity> FUTUREHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "futurezombie_hypnotized"),
			QuiltEntityTypeBuilder.<FutureZombieEntity>create(SpawnGroup.MONSTER, FutureZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<FlagFutureEntity> FLAGFUTURE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagfuture"),
			QuiltEntityTypeBuilder.<FlagFutureEntity>create(SpawnGroup.MONSTER, FlagFutureEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<FlagFutureEntity> FLAGFUTUREHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagfuture_hypnotized"),
			QuiltEntityTypeBuilder.<FlagFutureEntity>create(SpawnGroup.MONSTER, FlagFutureEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FutureZombieEntity> FUTURECONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "futurecone"),
			QuiltEntityTypeBuilder.<FutureZombieEntity>create(SpawnGroup.MONSTER, FutureZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FutureZombieEntity> FUTURECONEHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "futurecone_hypnotized"),
			QuiltEntityTypeBuilder.<FutureZombieEntity>create(SpawnGroup.MONSTER, FutureZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FutureZombieEntity> FUTUREBUCKET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "futurebucket"),
			QuiltEntityTypeBuilder.<FutureZombieEntity>create(SpawnGroup.MONSTER, FutureZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FutureZombieEntity> FUTUREBUCKETHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "futurebucket_hypnotized"),
			QuiltEntityTypeBuilder.<FutureZombieEntity>create(SpawnGroup.MONSTER, FutureZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FutureZombieEntity> HOLOHEAD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "holohead"),
			QuiltEntityTypeBuilder.<FutureZombieEntity>create(SpawnGroup.MONSTER, FutureZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FutureZombieEntity> HOLOHEADHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "holohead_hypnotized"),
			QuiltEntityTypeBuilder.<FutureZombieEntity>create(SpawnGroup.MONSTER, FutureZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<PokerEntity> POKER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerzombie"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerzombie_hypnotized"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<FlagPokerEntity> FLAGPOKER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagpoker"),
			QuiltEntityTypeBuilder.<FlagPokerEntity>create(SpawnGroup.MONSTER, FlagPokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<FlagPokerEntity> FLAGPOKERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagpoker_hypnotized"),
			QuiltEntityTypeBuilder.<FlagPokerEntity>create(SpawnGroup.CREATURE, FlagPokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<PokerEntity> POKERCONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokercone"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERCONEHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokercone_hypnotized"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERBUCKET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerbucket"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERBUCKETHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerbucket_hypnotized"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<PokerEntity> POKERPAWN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerpawn"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERPAWNHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerpawn_hypnotized"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERKNIGHT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerknight"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERKNIGHTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerknight_hypnotized"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERTOWER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokertower"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERTOWERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokertower_hypnotized"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERBISHOP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerbishop"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PokerEntity> POKERBISHOPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerbishop_hypnotized"),
			QuiltEntityTypeBuilder.<PokerEntity>create(SpawnGroup.MONSTER, PokerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<SargeantEntity> SARGEANT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeant"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<SargeantEntity> SARGEANTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeant_hypnotized"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<SargeantEntity> SARGEANTBOWL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeantcone"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<SargeantEntity> SARGEANTBOWLHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeantcone_hypnotized"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<SargeantEntity> SARGEANTHELMET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeantbucket"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<SargeantEntity> SARGEANTHELMETHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeantbucket_hypnotized"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<SargeantEntity> SARGEANTSHIELD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeantshield"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<SargeantEntity> SARGEANTSHIELDHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeantshield_hypnotized"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FlagSargeantEntity> FLAGSARGEANT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagsergeant"),
			QuiltEntityTypeBuilder.<FlagSargeantEntity>create(SpawnGroup.MONSTER, FlagSargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<FlagSargeantEntity> FLAGSARGEANTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagsergeant_hypnotized"),
			QuiltEntityTypeBuilder.<FlagSargeantEntity>create(SpawnGroup.MONSTER, FlagSargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<PumpkinZombieEntity> PUMPKINZOMBIE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pumpkinzombie"),
			QuiltEntityTypeBuilder.<PumpkinZombieEntity>create(SpawnGroup.MONSTER, PumpkinZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<PumpkinZombieEntity> PUMPKINZOMBIEHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pumpkinzombie_hypnotized"),
			QuiltEntityTypeBuilder.<PumpkinZombieEntity>create(SpawnGroup.MONSTER, PumpkinZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<PlasticHelmetEntity> CONEHEADGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "coneheadgear"),
			QuiltEntityTypeBuilder.<PlasticHelmetEntity>create(SpawnGroup.MONSTER, PlasticHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<PlasticHelmetEntity> POKERPAWNGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerpawngear"),
			QuiltEntityTypeBuilder.<PlasticHelmetEntity>create(SpawnGroup.MONSTER, PlasticHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<PlasticHelmetEntity> POKERKNIGHTGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerknightgear"),
			QuiltEntityTypeBuilder.<PlasticHelmetEntity>create(SpawnGroup.MONSTER, PlasticHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<PlasticHelmetEntity> POKERTOWERGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokertowergear"),
			QuiltEntityTypeBuilder.<PlasticHelmetEntity>create(SpawnGroup.MONSTER, PlasticHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<PlasticHelmetEntity> POKERBISHOPGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pokerbishopgear"),
			QuiltEntityTypeBuilder.<PlasticHelmetEntity>create(SpawnGroup.MONSTER, PlasticHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<PlasticHelmetEntity> KINGPIECEGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "kingpiecegear"),
			QuiltEntityTypeBuilder.<PlasticHelmetEntity>create(SpawnGroup.MONSTER, PlasticHelmetEntity::new).setDimensions(EntityDimensions.fixed(1f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> BUCKETGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bucketheadgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> HELMETGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "helmetgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.7f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> KNIGHTGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "knightgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> SOLDIERGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "soldiergear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<StoneHelmetEntity> PYRAMIDGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pyramidgear"),
			QuiltEntityTypeBuilder.<StoneHelmetEntity>create(SpawnGroup.MONSTER, StoneHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<StoneHelmetEntity> TOWERGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "towergear"),
			QuiltEntityTypeBuilder.<StoneHelmetEntity>create(SpawnGroup.MONSTER, StoneHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<StoneHelmetEntity> BOWLGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bowlgear"),
			QuiltEntityTypeBuilder.<StoneHelmetEntity>create(SpawnGroup.MONSTER, StoneHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<StoneHelmetEntity> BRICKGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "brickgear"),
			QuiltEntityTypeBuilder.<StoneHelmetEntity>create(SpawnGroup.MONSTER, StoneHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<CrystalHelmetEntity> HOLOGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "hologear"),
			QuiltEntityTypeBuilder.<CrystalHelmetEntity>create(SpawnGroup.MONSTER, CrystalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<CrystalHelmetEntity> CRYSTALSHOEGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "crystalshoegear"),
			QuiltEntityTypeBuilder.<CrystalHelmetEntity>create(SpawnGroup.MONSTER, CrystalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<StoneHelmetEntity> SARCOPHAGUS = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sarcophagus"),
			QuiltEntityTypeBuilder.<StoneHelmetEntity>create(SpawnGroup.MONSTER, StoneHelmetEntity::new).setDimensions(EntityDimensions.fixed(1f, 2.25f)).build()
	);
	public static final EntityType<MetalHelmetEntity> MEDALLIONGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "medalliongear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> FOOTBALLGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "footballgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> BERSERKERGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "berserkergear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);

	public static final EntityType<MetalHelmetEntity> DEFENSIVEENDGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "defensiveendgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(1.425f, 3.95f)).build()
	);
	public static final EntityType<MetalShieldEntity> SCREENDOORSHIELD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "screendoorshield"),
			QuiltEntityTypeBuilder.<MetalShieldEntity>create(SpawnGroup.MONSTER, MetalShieldEntity::new).setDimensions(EntityDimensions.fixed(0.85f, 1.8f)).build()
	);
	public static final EntityType<MetalShieldEntity> SERGEANTSHIELDGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sergeantshieldgear"),
			QuiltEntityTypeBuilder.<MetalShieldEntity>create(SpawnGroup.MONSTER, MetalShieldEntity::new).setDimensions(EntityDimensions.fixed(0.85f, 1.8f)).build()
	);
	public static final EntityType<MetalHelmetEntity> BLASTRONAUTGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "blastronautgear"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.9f, 2.55f)).build()
	);
	public static final EntityType<MetalHelmetEntity> BASSGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bassprop"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);
	public static final EntityType<MetalHelmetEntity> SCRAPIMPGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "scrapimpprop"),
			QuiltEntityTypeBuilder.<MetalHelmetEntity>create(SpawnGroup.MONSTER, MetalHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);

	public static final EntityType<PlantHelmetEntity> PUMPKINGEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pumpkingear"),
			QuiltEntityTypeBuilder.<PlantHelmetEntity>create(SpawnGroup.MONSTER, PlantHelmetEntity::new).setDimensions(EntityDimensions.fixed(0.8f, 1.95f)).build()
	);

	public static final EntityType<MetalHelmetProjEntity> METALHELMETPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(MOD_ID, "metalhelmetprojentity"),
			QuiltEntityTypeBuilder.<MetalHelmetProjEntity>create(SpawnGroup.MONSTER, MetalHelmetProjEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
	);

    public static final EntityType<FlagzombieEntity> FLAGZOMBIE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "flagzombie"),
            QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.MONSTER, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
	public static final EntityType<FlagzombieEntity> FLAGZOMBIE_G = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagzombie_g"),
			QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.MONSTER, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<FlagzombieEntity> FLAGZOMBIE_T = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagzombie_t"),
			QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.MONSTER, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
    public static final EntityType<FlagzombieEntity> FLAGZOMBIEHYPNO = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "flagzombie_hypnotized"),
            QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.CREATURE, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
	public static final EntityType<FlagzombieEntity> FLAGZOMBIE_GHYPNO= Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagzombie_g_hypnotized"),
			QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.CREATURE, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<FlagzombieEntity> FLAGZOMBIE_THYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flagzombie_t_hypnotized"),
			QuiltEntityTypeBuilder.<FlagzombieEntity>create(SpawnGroup.CREATURE, FlagzombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);


    public static final EntityType<PoleVaultingEntity> POLEVAULTING = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "polevaulting"),
            QuiltEntityTypeBuilder.<PoleVaultingEntity>create(SpawnGroup.MONSTER, PoleVaultingEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
	public static final EntityType<PoleVaultingEntity> POLEVAULTINGHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "polevaulting_hypnotized"),
			QuiltEntityTypeBuilder.<PoleVaultingEntity>create(SpawnGroup.MONSTER, PoleVaultingEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

    public static final EntityType<NewspaperEntity> NEWSPAPER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "newspaper"),
            QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );
	public static final EntityType<NewspaperEntity> NEWSPAPERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "newspaper_hypnotized"),
			QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<NewspaperShieldEntity> NEWSPAPERSHIELD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "newspapershield"),
			QuiltEntityTypeBuilder.<NewspaperShieldEntity>create(SpawnGroup.MONSTER, NewspaperShieldEntity::new).setDimensions(EntityDimensions.fixed(0.85f, 1.8f)).build()
	);

	public static final EntityType<NewspaperEntity> SUNDAYEDITION = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sundayedition"),
			QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<NewspaperEntity> SUNDAYEDITIONHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sundayedition_hypnotized"),
			QuiltEntityTypeBuilder.<NewspaperEntity>create(SpawnGroup.MONSTER, NewspaperEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<NewspaperShieldEntity> SUNDAYEDITIONSHIELD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sundayeditionshield"),
			QuiltEntityTypeBuilder.<NewspaperShieldEntity>create(SpawnGroup.MONSTER, NewspaperShieldEntity::new).setDimensions(EntityDimensions.fixed(0.85f, 1.8f)).build()
	);

	public static final EntityType<SargeantEntity> BOOKBURNER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bookburner"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<SargeantEntity> BOOKBURNERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bookburner_hypnotized"),
			QuiltEntityTypeBuilder.<SargeantEntity>create(SpawnGroup.MONSTER, SargeantEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<NewspaperShieldEntity> BOOKSHIELD = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bookshield"),
			QuiltEntityTypeBuilder.<NewspaperShieldEntity>create(SpawnGroup.MONSTER, NewspaperShieldEntity::new).setDimensions(EntityDimensions.fixed(0.85f, 1.8f)).build()
	);

	public static final EntityType<FlamingBookEntity> FLAMINGBOOK = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "flamingbook"),
			QuiltEntityTypeBuilder.<FlamingBookEntity>create(SpawnGroup.MONSTER, FlamingBookEntity::new).setDimensions(EntityDimensions.fixed(1f, 1f)).build()
	);

    public static final EntityType<FootballEntity> FOOTBALL = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "football"),
            QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
    );
	public static final EntityType<FootballEntity> BERSERKER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "berserker"),
			QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
	);
	public static final EntityType<FootballEntity> FOOTBALLHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "football_hypnotized"),
			QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
	);
	public static final EntityType<FootballEntity> BERSERKERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "berserker_hypnotized"),
			QuiltEntityTypeBuilder.<FootballEntity>create(SpawnGroup.MONSTER, FootballEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
	);

	public static final EntityType<BrowncoatEntity> TRASHCAN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "trashcan"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<BrowncoatEntity> TRASHCANHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "trashcan_hypnotized"),
			QuiltEntityTypeBuilder.<BrowncoatEntity>create(SpawnGroup.MONSTER, BrowncoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<MetalObstacleEntity> TRASHCANBIN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "trashcanbin"),
			QuiltEntityTypeBuilder.<MetalObstacleEntity>create(SpawnGroup.MONSTER, MetalObstacleEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.8f)).build()
	);

    public static final EntityType<DancingZombieEntity> DANCINGZOMBIE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "dancing_zombie"),
            QuiltEntityTypeBuilder.<DancingZombieEntity>create(SpawnGroup.MONSTER, DancingZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

	public static final EntityType<DancingZombieEntity> DANCINGZOMBIEHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "dancing_zombie_hypnotized"),
			QuiltEntityTypeBuilder.<DancingZombieEntity>create(SpawnGroup.MONSTER, DancingZombieEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
    public static final EntityType<BackupDancerEntity> BACKUPDANCER = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "backup_dancer"),
            QuiltEntityTypeBuilder.<BackupDancerEntity>create(SpawnGroup.MONSTER, BackupDancerEntity::new).setDimensions(EntityDimensions.fixed(0.6f, 1.85f)).build()
    );
    public static final EntityType<BackupDancerEntity> BACKUPDANCERHYPNO = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "backup_dancer_hypnotized"),
            QuiltEntityTypeBuilder.<BackupDancerEntity>create(SpawnGroup.CREATURE, BackupDancerEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
    );

	public static final EntityType<SnorkelEntity> SNORKEL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "snorkel"),
			QuiltEntityTypeBuilder.<SnorkelEntity>create(SpawnGroup.MONSTER, SnorkelEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<SnorkelEntity> SNORKELHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "snorkel_hypnotized"),
			QuiltEntityTypeBuilder.<SnorkelEntity>create(SpawnGroup.MONSTER, SnorkelEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<DolphinRiderEntity> DOLPHINRIDER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "dolphinrider"),
			QuiltEntityTypeBuilder.<DolphinRiderEntity>create(SpawnGroup.MONSTER, DolphinRiderEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<DolphinRiderEntity> DOLPHINRIDERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "dolphinrider_hypnotized"),
			QuiltEntityTypeBuilder.<DolphinRiderEntity>create(SpawnGroup.MONSTER, DolphinRiderEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<ZomboniEntity> ZOMBONI = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zomboni"),
			QuiltEntityTypeBuilder.<ZomboniEntity>create(SpawnGroup.MONSTER, ZomboniEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<ZomboniEntity> ZOMBONIHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zomboni_hypnotized"),
			QuiltEntityTypeBuilder.<ZomboniEntity>create(SpawnGroup.MONSTER, ZomboniEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<MetalVehicleEntity> ZOMBONIVEHICLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zombonivehicle"),
			QuiltEntityTypeBuilder.<MetalVehicleEntity>create(SpawnGroup.MONSTER, MetalVehicleEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.3f)).build()
	);

	public static final EntityType<BobsledRiderEntity> BOBSLED = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bobsled"),
			QuiltEntityTypeBuilder.<BobsledRiderEntity>create(SpawnGroup.MONSTER, BobsledRiderEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);
	public static final EntityType<BobsledRiderEntity> BOBSLEDHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bobsled_hypnotized"),
			QuiltEntityTypeBuilder.<BobsledRiderEntity>create(SpawnGroup.MONSTER, BobsledRiderEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.95f)).build()
	);

	public static final EntityType<MetalVehicleEntity> BOBSLEDVEHICLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bobsledvehicle"),
			QuiltEntityTypeBuilder.<MetalVehicleEntity>create(SpawnGroup.MONSTER, MetalVehicleEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.3f)).build()
	);

	public static final EntityType<GargantuarEntity> GARGANTUAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "gargantuar"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.95f)).build()
	);

	public static final EntityType<GargantuarEntity> GARGANTUARHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "gargantuar_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.CREATURE, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.95f)).build()
	);

	public static final EntityType<GargantuarEntity> MUMMYGARGANTUAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummygargantuar"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.95f)).build()
	);

	public static final EntityType<GargantuarEntity> MUMMYGARGANTUARHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummygargantuar_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.CREATURE, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.95f)).build()
	);

	public static final EntityType<GargantuarEntity> DEFENSIVEEND = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "defensiveend"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.85f)).build()
	);

	public static final EntityType<GargantuarEntity> DEFENSIVEENDHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "defensiveend_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.85f)).build()
	);

	public static final EntityType<GargantuarEntity> DEFENSIVEEND_NEWYEAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "defensiveend_newyear"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.85f)).build()
	);

	public static final EntityType<GargantuarEntity> DEFENSIVEEND_NEWYEARHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "defensiveend_newyear_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.85f)).build()
	);

	public static final EntityType<GargantuarEntity> CURSEDGARGOLITH = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cursedgargolith"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.95f)).build()
	);

	public static final EntityType<GargantuarEntity> CURSEDGARGOLITHHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cursedgargolith_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.CREATURE, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.95f)).build()
	);

	public static final EntityType<RockObstacleEntity> GARGOLITHOBSTACLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "gargolithobst"),
			QuiltEntityTypeBuilder.<RockObstacleEntity>create(SpawnGroup.MONSTER, RockObstacleEntity::new).setDimensions(EntityDimensions.fixed(0.95f, 3.95f)).build()
	);

	public static final EntityType<ScrapMechEntity> SCRAPMECH = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "scrapmech"),
			QuiltEntityTypeBuilder.<ScrapMechEntity>create(SpawnGroup.MONSTER, ScrapMechEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.95f)).build()
	);

	public static final EntityType<ScrapMechEntity> SCRAPMECHHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "scrapmech_hypnotized"),
			QuiltEntityTypeBuilder.<ScrapMechEntity>create(SpawnGroup.CREATURE, ScrapMechEntity::new).setDimensions(EntityDimensions.fixed(1.125f, 3.95f)).build()
	);

	public static final EntityType<LaserEntity> LASER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "laser"),
			QuiltEntityTypeBuilder.<LaserEntity>create(SpawnGroup.MONSTER, LaserEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
	);

	public static final EntityType<ImpEntity> IMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "imp"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> IMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "imp_hypnotized"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.CREATURE, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> MUMMYIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummyimp"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> MUMMYIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mummyimp_hypnotized"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.CREATURE, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> SCRAPIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "scrapimp"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> SCRAPIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "scrapimp_hypnotized"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.CREATURE, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> IMPTHROWER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "impthrower"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> IMPTHROWERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "impthrower_hypnotized"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.CREATURE, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> IMPDRAGON = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "impdragon"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> IMPDRAGONHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "impdragon_hypnotized"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.CREATURE, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<SuperFanImpEntity> SUPERFANIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "superfanimp"),
			QuiltEntityTypeBuilder.<SuperFanImpEntity>create(SpawnGroup.MONSTER, SuperFanImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<SuperFanImpEntity> SUPERFANIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "superfanimp_hypnotized"),
			QuiltEntityTypeBuilder.<SuperFanImpEntity>create(SpawnGroup.MONSTER, SuperFanImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<SuperFanImpEntity> NEWYEARIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "newyearimp"),
			QuiltEntityTypeBuilder.<SuperFanImpEntity>create(SpawnGroup.MONSTER, SuperFanImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<SuperFanImpEntity> NEWYEARIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "newyearimp_hypnotized"),
			QuiltEntityTypeBuilder.<SuperFanImpEntity>create(SpawnGroup.MONSTER, SuperFanImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);


	public static final EntityType<AnnouncerImpEntity> ANNOUNCERIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "announcerimp"),
			QuiltEntityTypeBuilder.<AnnouncerImpEntity>create(SpawnGroup.MONSTER, AnnouncerImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<AnnouncerImpEntity> ANNOUNCERIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "announcerimp_hypnotized"),
			QuiltEntityTypeBuilder.<AnnouncerImpEntity>create(SpawnGroup.CREATURE, AnnouncerImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);


	public static final EntityType<AnnouncerImpEntity> REDANNOUNCERIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "redannouncerimp"),
			QuiltEntityTypeBuilder.<AnnouncerImpEntity>create(SpawnGroup.MONSTER, AnnouncerImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<AnnouncerImpEntity> REDANNOUNCERIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "redannouncerimp_hypnotized"),
			QuiltEntityTypeBuilder.<AnnouncerImpEntity>create(SpawnGroup.CREATURE, AnnouncerImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);


	public static final EntityType<AnnouncerImpEntity> BLACKANNOUNCERIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "blackannouncerimp"),
			QuiltEntityTypeBuilder.<AnnouncerImpEntity>create(SpawnGroup.MONSTER, AnnouncerImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<AnnouncerImpEntity> BLACKANNOUNCERIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "blackannouncerimp_hypnotized"),
			QuiltEntityTypeBuilder.<AnnouncerImpEntity>create(SpawnGroup.CREATURE, AnnouncerImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<RockObstacleEntity> IMPTABLETOBSTACLE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "imptabletobst"),
			QuiltEntityTypeBuilder.<RockObstacleEntity>create(SpawnGroup.MONSTER, RockObstacleEntity::new).setDimensions(EntityDimensions.fixed(0.725f, 1f)).build()
	);

	public static final EntityType<ZombieKingEntity> ZOMBIEKING = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zombieking"),
			QuiltEntityTypeBuilder.<ZombieKingEntity>create(SpawnGroup.MONSTER, ZombieKingEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 1.8f)).build()
	);

	public static final EntityType<ZombieKingEntity> ZOMBIEKINGHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zombieking_hypnotized"),
			QuiltEntityTypeBuilder.<ZombieKingEntity>create(SpawnGroup.CREATURE, ZombieKingEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 1.8f)).build()
	);

	public static final EntityType<ZombieKingEntity> REDZOMBIEKING = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "redzombieking"),
			QuiltEntityTypeBuilder.<ZombieKingEntity>create(SpawnGroup.MONSTER, ZombieKingEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 1.8f)).build()
	);

	public static final EntityType<ZombieKingEntity> REDZOMBIEKINGHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "redzombieking_hypnotized"),
			QuiltEntityTypeBuilder.<ZombieKingEntity>create(SpawnGroup.CREATURE, ZombieKingEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 1.8f)).build()
	);

	public static final EntityType<ZombieKingEntity> BLACKZOMBIEKING = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "blackzombieking"),
			QuiltEntityTypeBuilder.<ZombieKingEntity>create(SpawnGroup.MONSTER, ZombieKingEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 1.8f)).build()
	);

	public static final EntityType<ZombieKingEntity> BLACKZOMBIEKINGHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "blackzombieking_hypnotized"),
			QuiltEntityTypeBuilder.<ZombieKingEntity>create(SpawnGroup.CREATURE, ZombieKingEntity::new).setDimensions(EntityDimensions.fixed(0.925f, 1.8f)).build()
	);


	public static final EntityType<LocustSwarmEntity> LOCUSTSWARM = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "locustswarm"),
			QuiltEntityTypeBuilder.<LocustSwarmEntity>create(SpawnGroup.MONSTER, LocustSwarmEntity::new).setDimensions(EntityDimensions.fixed(0.62f, 0.5f)).build()
	);

	public static final EntityType<JetpackEntity> JETPACK = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "jetpack"),
			QuiltEntityTypeBuilder.<JetpackEntity>create(SpawnGroup.MONSTER, JetpackEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 2.4f)).build()
	);

	public static final EntityType<JetpackEntity> JETPACKHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "jetpack_hypnotized"),
			QuiltEntityTypeBuilder.<JetpackEntity>create(SpawnGroup.MONSTER, JetpackEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 2.4f)).build()
	);

	public static final EntityType<JetpackEntity> BLASTRONAUT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "blastronaut"),
			QuiltEntityTypeBuilder.<JetpackEntity>create(SpawnGroup.MONSTER, JetpackEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 2.4f)).build()
	);

	public static final EntityType<JetpackEntity> BLASTRONAUTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "blastronaut_hypnotized"),
			QuiltEntityTypeBuilder.<JetpackEntity>create(SpawnGroup.MONSTER, JetpackEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 2.4f)).build()
	);

	public static final EntityType<HoverGoatEntity> HOVERGOAT = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "hovergoat3000"),
			QuiltEntityTypeBuilder.<HoverGoatEntity>create(SpawnGroup.MONSTER, HoverGoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<HoverGoatEntity> HOVERGOATHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "hovergoat3000_hypnotized"),
			QuiltEntityTypeBuilder.<HoverGoatEntity>create(SpawnGroup.MONSTER, HoverGoatEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<RoboConeEntity> ROBOCONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "robocone"),
			QuiltEntityTypeBuilder.<RoboConeEntity>create(SpawnGroup.MONSTER, RoboConeEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.25f)).build()
	);

	public static final EntityType<SoldierEntity> SOLDIER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "footsoldier"),
			QuiltEntityTypeBuilder.<SoldierEntity>create(SpawnGroup.MONSTER, SoldierEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<SoldierEntity> SOLDIERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "footsoldier_hypnotized"),
			QuiltEntityTypeBuilder.<SoldierEntity>create(SpawnGroup.MONSTER, SoldierEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<ZPGEntity> ZPG = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zpgproj"),
			QuiltEntityTypeBuilder.<ZPGEntity>create(SpawnGroup.MONSTER, ZPGEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
	);

	public static final EntityType<RocketEntity> ROCKETPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "rocketproj"),
			QuiltEntityTypeBuilder.<RocketEntity>create(SpawnGroup.MONSTER, RocketEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
	);

	public static final EntityType<BullyEntity> BULLY = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bully"),
			QuiltEntityTypeBuilder.<BullyEntity>create(SpawnGroup.MONSTER, BullyEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.2f)).build()
	);

	public static final EntityType<BullyEntity> BULLYHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bully_hypnotized"),
			QuiltEntityTypeBuilder.<BullyEntity>create(SpawnGroup.MONSTER, BullyEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.2f)).build()
	);

	public static final EntityType<ActionheroEntity> ACTIONHERO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "80sactionhero"),
			QuiltEntityTypeBuilder.<ActionheroEntity>create(SpawnGroup.MONSTER, ActionheroEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.2f)).build()
	);

	public static final EntityType<ActionheroEntity> ACTIONHEROHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "80sactionhero_hypnotized"),
			QuiltEntityTypeBuilder.<ActionheroEntity>create(SpawnGroup.MONSTER, ActionheroEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.2f)).build()
	);

	public static final EntityType<OctoEntity> OCTO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "octo"),
			QuiltEntityTypeBuilder.<OctoEntity>create(SpawnGroup.MONSTER, OctoEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.2f)).build()
	);

	public static final EntityType<OctoEntity> OCTOHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "octo_hypnotized"),
			QuiltEntityTypeBuilder.<OctoEntity>create(SpawnGroup.MONSTER, OctoEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.2f)).build()
	);

	public static final EntityType<ShootingOctoEntity> OCTOPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "octoproj"),
			QuiltEntityTypeBuilder.<ShootingOctoEntity>create(SpawnGroup.MONSTER, ShootingOctoEntity::new).setDimensions(EntityDimensions.fixed(1f, 2f)).build()
	);

	public static final EntityType<BasketballCarrierEntity> BASKETBALLCARRIER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "basketballcarrier"),
			QuiltEntityTypeBuilder.<BasketballCarrierEntity>create(SpawnGroup.MONSTER, BasketballCarrierEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.2f)).build()
	);

	public static final EntityType<BasketballCarrierEntity> BASKETBALLCARRIERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "basketballcarrier_hypnotized"),
			QuiltEntityTypeBuilder.<BasketballCarrierEntity>create(SpawnGroup.MONSTER, BasketballCarrierEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.2f)).build()
	);

	public static final EntityType<MetalObstacleEntity> BASKETBALLBIN = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "basketballbin"),
			QuiltEntityTypeBuilder.<MetalObstacleEntity>create(SpawnGroup.MONSTER, MetalObstacleEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2f)).build()
	);

	public static final EntityType<ShootingBasketballEntity> BASKETBALLPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "basketballproj"),
			QuiltEntityTypeBuilder.<ShootingBasketballEntity>create(SpawnGroup.MONSTER, ShootingBasketballEntity::new).setDimensions(EntityDimensions.fixed(1f, 1f)).build()
	);

	public static final EntityType<BoneProjEntity> BONEPROJ = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "boneproj"),
			QuiltEntityTypeBuilder.<BoneProjEntity>create(SpawnGroup.MONSTER, BoneProjEntity::new).setDimensions(EntityDimensions.fixed(1f, 1f)).build()
	);

	public static final EntityType<BassZombieEntity> BASS = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bass"),
			QuiltEntityTypeBuilder.<BassZombieEntity>create(SpawnGroup.MONSTER, BassZombieEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.8f)).build()
	);

	public static final EntityType<BassZombieEntity> BASSHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bass_hypnotized"),
			QuiltEntityTypeBuilder.<BassZombieEntity>create(SpawnGroup.MONSTER, BassZombieEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.8f)).build()
	);

	public static final EntityType<SpeakerVehicleEntity> SPEAKER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "speaker"),
			QuiltEntityTypeBuilder.<SpeakerVehicleEntity>create(SpawnGroup.MONSTER, SpeakerVehicleEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.2f)).build()
	);

	public static final EntityType<SoundwaveEntity> SOUNDWAVE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "soundwave"),
			QuiltEntityTypeBuilder.<SoundwaveEntity>create(SpawnGroup.MONSTER, SoundwaveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 0.5f)).build()
	);

	public static final EntityType<HawkerZombieEntity> HAWKERPUSHER = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "hawkerpusher"),
			QuiltEntityTypeBuilder.<HawkerZombieEntity>create(SpawnGroup.MONSTER, HawkerZombieEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.8f)).build()
	);

	public static final EntityType<HawkerZombieEntity> HAWKERPUSHERHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "hawkerpusher_hypnotized"),
			QuiltEntityTypeBuilder.<HawkerZombieEntity>create(SpawnGroup.MONSTER, HawkerZombieEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.8f)).build()
	);

	public static final EntityType<WoodObstacleEntity> HAWKERCART = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "hawkercart"),
			QuiltEntityTypeBuilder.<WoodObstacleEntity>create(SpawnGroup.MONSTER, WoodObstacleEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2f)).build()
	);

	public static final EntityType<FleshObstacleEntity> OCTOOBST = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "octoobst"),
			QuiltEntityTypeBuilder.<FleshObstacleEntity>create(SpawnGroup.MONSTER, FleshObstacleEntity::new).setDimensions(EntityDimensions.fixed(1f, 2f)).build()
	);


	public static final EntityType<ScientistEntity> SCIENTIST = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "scientist"),
			QuiltEntityTypeBuilder.<ScientistEntity>create(SpawnGroup.MONSTER, ScientistEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);

	public static final EntityType<ScientistEntity> SCIENTISTHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "scientist_hypnotized"),
			QuiltEntityTypeBuilder.<ScientistEntity>create(SpawnGroup.MONSTER, ScientistEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1.8f)).build()
	);
	public static final EntityType<MetalObstacleEntity> HEALSTATION = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "healstation"),
			QuiltEntityTypeBuilder.<MetalObstacleEntity>create(SpawnGroup.MONSTER, MetalObstacleEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2f)).build()
	);

	public static final EntityType<ZomblobEntity> ZOMBLOB = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zomblob"),
			QuiltEntityTypeBuilder.<ZomblobEntity>create(SpawnGroup.MONSTER, ZomblobEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.85f)).build()
	);

	public static final EntityType<ZomblobEntity> ZOMBLOBHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zomblob_hypnotized"),
			QuiltEntityTypeBuilder.<ZomblobEntity>create(SpawnGroup.MONSTER, ZomblobEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.85f)).build()
	);

	public static final EntityType<ZomblobEntity> ZOMBLOBBIG = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zomblobbig"),
			QuiltEntityTypeBuilder.<ZomblobEntity>create(SpawnGroup.MONSTER, ZomblobEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.4f)).build()
	);

	public static final EntityType<ZomblobEntity> ZOMBLOBBIGHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zomblobbig_hypnotized"),
			QuiltEntityTypeBuilder.<ZomblobEntity>create(SpawnGroup.MONSTER, ZomblobEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.4f)).build()
	);

	public static final EntityType<ZomblobEntity> ZOMBLOBSMALL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zomblobsmall"),
			QuiltEntityTypeBuilder.<ZomblobEntity>create(SpawnGroup.MONSTER, ZomblobEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1.85f)).build()
	);

	public static final EntityType<ZomblobEntity> ZOMBLOBSMALLHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zomblobsmall_hypnotized"),
			QuiltEntityTypeBuilder.<ZomblobEntity>create(SpawnGroup.MONSTER, ZomblobEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 1f)).build()
	);

	public static final EntityType<PiggyEntity> PIGGY = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "piggy"),
			QuiltEntityTypeBuilder.<PiggyEntity>create(SpawnGroup.MONSTER, PiggyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<PiggyEntity> PIGGYHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "piggy_hypnotized"),
			QuiltEntityTypeBuilder.<PiggyEntity>create(SpawnGroup.CREATURE, PiggyEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<GargantuarEntity> UNICORNGARGANTUAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "unicorngargantuar"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.MONSTER, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 3.95f)).build()
	);

	public static final EntityType<GargantuarEntity> UNICORNGARGANTUARHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "unicorngargantuar_hypnotized"),
			QuiltEntityTypeBuilder.<GargantuarEntity>create(SpawnGroup.CREATURE, GargantuarEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 3.95f)).build()
	);

	public static final EntityType<ImpEntity> BASSIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bassimp"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> BASSIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bassimp_hypnotized"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.CREATURE, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<PumpkinCarEntity> PUMPKINCAR = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "pumpkincar"),
			QuiltEntityTypeBuilder.<PumpkinCarEntity>create(SpawnGroup.MONSTER, PumpkinCarEntity::new).setDimensions(EntityDimensions.fixed(0.825f, 2.25f)).build()
	);

	public static final EntityType<ImpEntity> CINDERELLAIMP = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cinderellaimp"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.MONSTER, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);

	public static final EntityType<ImpEntity> CINDERELLAIMPHYPNO = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cinderellaimp_hypnotized"),
			QuiltEntityTypeBuilder.<ImpEntity>create(SpawnGroup.CREATURE, ImpEntity::new).setDimensions(EntityDimensions.fixed(0.625f, 1f)).build()
	);


    /////////////////////////////////////////////////////////////////////////////////////////////////

	public static final EntityType<SolarWinds> SOLARWINDS = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "solarwinds"),
			QuiltEntityTypeBuilder.<SolarWinds>create(SpawnGroup.MONSTER, SolarWinds::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<MissileToeTarget> MISSILETOETARGET = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "missiletoetarget"),
			QuiltEntityTypeBuilder.<MissileToeTarget>create(SpawnGroup.MONSTER, MissileToeTarget::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<ScorchedTile> SCORCHEDTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "scorchedtile"),
			QuiltEntityTypeBuilder.<ScorchedTile>create(SpawnGroup.MONSTER, ScorchedTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<IceTile> ICETILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "icetile"),
			QuiltEntityTypeBuilder.<IceTile>create(SpawnGroup.MONSTER, IceTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<OilTile> OILTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "oiltile"),
			QuiltEntityTypeBuilder.<OilTile>create(SpawnGroup.MONSTER, OilTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<CheeseTile> CHEESETILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cheesetile"),
			QuiltEntityTypeBuilder.<CheeseTile>create(SpawnGroup.MONSTER, CheeseTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<BananaTile> BANANAPEEL = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "bananatile"),
			QuiltEntityTypeBuilder.<BananaTile>create(SpawnGroup.MONSTER, BananaTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<SpringTile> SPRINGTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "springtile"),
			QuiltEntityTypeBuilder.<SpringTile>create(SpawnGroup.MONSTER, SpringTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<SnowTile> SNOWTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "snowtile"),
			QuiltEntityTypeBuilder.<SnowTile>create(SpawnGroup.MONSTER, SnowTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<WaterTile> WATERTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "watertile"),
			QuiltEntityTypeBuilder.<WaterTile>create(SpawnGroup.MONSTER, WaterTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<ShadowTile> SHADOWTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "shadowtile"),
			QuiltEntityTypeBuilder.<ShadowTile>create(SpawnGroup.MONSTER, ShadowTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<ShadowFullTile> SHADOWFULLTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "shadowfulltile"),
			QuiltEntityTypeBuilder.<ShadowFullTile>create(SpawnGroup.MONSTER, ShadowFullTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);


	public static final EntityType<CraterTile> CRATERTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "cratertile"),
			QuiltEntityTypeBuilder.<CraterTile>create(SpawnGroup.MONSTER, CraterTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);


	public static final EntityType<WeatherTile> WEATHERTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "weathertile"),
			QuiltEntityTypeBuilder.<WeatherTile>create(SpawnGroup.MONSTER, WeatherTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);
	public static final EntityType<TimeTile> TIMETILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "timetile"),
			QuiltEntityTypeBuilder.<TimeTile>create(SpawnGroup.MONSTER, TimeTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);


	public static final EntityType<RiftTile> RIFTTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "rifttile"),
			QuiltEntityTypeBuilder.<RiftTile>create(SpawnGroup.MONSTER, RiftTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<GoldTile> GOLDTILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "goldtile"),
			QuiltEntityTypeBuilder.<GoldTile>create(SpawnGroup.MONSTER, GoldTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<MariTile> MARITILE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "maritile"),
			QuiltEntityTypeBuilder.<MariTile>create(SpawnGroup.MONSTER, MariTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<RoseBudTile> ROSEBUDS = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "rosebuds"),
			QuiltEntityTypeBuilder.<RoseBudTile>create(SpawnGroup.MONSTER, RoseBudTile::new).setDimensions(EntityDimensions.fixed(1f, 0.05f)).build()
	);

	public static final EntityType<SunBombEntity> SUNBOMB = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "sunbomb"),
			QuiltEntityTypeBuilder.<SunBombEntity>create(SpawnGroup.MONSTER, SunBombEntity::new).setDimensions(EntityDimensions.fixed(2f, 1f)).build()
	);

	public static final EntityType<RockObstacleEntity> EGYPTTOMBSTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "egypttombstone"),
			QuiltEntityTypeBuilder.<RockObstacleEntity>create(SpawnGroup.MONSTER, RockObstacleEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	public static final EntityType<ZombieGraveEntity> ZOMBIEGRAVESTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "zombiegrave"),
			QuiltEntityTypeBuilder.<ZombieGraveEntity>create(SpawnGroup.MONSTER, ZombieGraveEntity::new).setDimensions(EntityDimensions.fixed(1f, 1f)).build()
	);

    public static final EntityType<BasicGraveEntity> BASICGRAVESTONE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "basicgrave"),
            QuiltEntityTypeBuilder.<BasicGraveEntity>create(SpawnGroup.MONSTER, BasicGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
    );

    public static final EntityType<NightGraveEntity> NIGHTGRAVESTONE = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(ModID, "nightgrave"),
            QuiltEntityTypeBuilder.<NightGraveEntity>create(SpawnGroup.MONSTER, NightGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
    );

	public static final EntityType<PoolGraveEntity> POOLGRAVESTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "poolgrave"),
			QuiltEntityTypeBuilder.<PoolGraveEntity>create(SpawnGroup.MONSTER, PoolGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	public static final EntityType<RoofGraveEntity> ROOFGRAVESTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "roofgrave"),
			QuiltEntityTypeBuilder.<RoofGraveEntity>create(SpawnGroup.MONSTER, RoofGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	public static final EntityType<EgyptGraveEntity> EGYPTGRAVESTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "egyptgrave"),
			QuiltEntityTypeBuilder.<EgyptGraveEntity>create(SpawnGroup.MONSTER, EgyptGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	public static final EntityType<FutureGraveEntity> FUTUREGRAVESTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "futuregrave"),
			QuiltEntityTypeBuilder.<FutureGraveEntity>create(SpawnGroup.MONSTER, FutureGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	public static final EntityType<DarkAgesGraveEntity> DARKAGESGRAVESTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "darkagesgrave"),
			QuiltEntityTypeBuilder.<DarkAgesGraveEntity>create(SpawnGroup.MONSTER, DarkAgesGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	public static final EntityType<FairyTaleGraveEntity> FAIRYTALEGRAVESTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "fairytalegrave"),
			QuiltEntityTypeBuilder.<FairyTaleGraveEntity>create(SpawnGroup.MONSTER, FairyTaleGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	public static final EntityType<MausoleumGraveEntity> MAUSOLEUMGRAVESTONE = Registry.register(
			Registries.ENTITY_TYPE,
			new Identifier(ModID, "mausoleumgrave"),
			QuiltEntityTypeBuilder.<MausoleumGraveEntity>create(SpawnGroup.MONSTER, MausoleumGraveEntity::new).setDimensions(EntityDimensions.fixed(0.5f, 1f)).build()
	);

	public static void Entities() {
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GARDEN, GardenEntity.createGardenAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GARDENCHALLENGE, GardenChallengeEntity.createGardenAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASHOOTER, PeashooterEntity.createPeashooterAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.COFFEEBEAN, CoffeeBeanEntity.createCoffeeBeanAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNFLOWER, SunflowerEntity.createSunflowerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CHERRYBOMB, CherrybombEntity.createCherrybombAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.WALLNUT, WallnutEntity.createWallnutAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POTATOMINE, PotatomineEntity.createPotatomineAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNOWPEA, SnowpeaEntity.createSnowpeaAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CHOMPER, ChomperEntity.createChomperAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CHESTER, ChesterEntity.createChesterAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.VAMPIREFLOWER, VampireFlowerEntity.createVampireFlowerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.REPEATER, RepeaterEntity.createRepeaterAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PUFFSHROOM, PuffshroomEntity.createPuffshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUMESHROOM, FumeshroomEntity.createFumeshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNSHROOM, SunshroomEntity.createSunshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MAGNETSHROOM, MagnetshroomEntity.createMagnetshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MAGNETOSHROOM, MagnetoShroomEntity.createMagnetoshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.OXYGAE, OxygaeEntity.createOxygaeAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUBBLEPAD, BubblePadEntity.createBubbleAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BREEZESHROOM, BreezeshroomEntity.createBreezeshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BURSTSHROOM, BurstshroomEntity.createBurstshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUPERCHOMPER, SuperChomperEntity.createSuperChomperAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GRAVEBUSTER, GravebusterEntity.createGravebusterAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HYPNOSHROOM, HypnoshroomEntity.createHypnoshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntity.createScaredyshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ICESHROOM, IceshroomEntity.createIceshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DOOMSHROOM, DoomshroomEntity.createDoomshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.LILYPAD, LilyPadEntity.createLilyPadAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SQUASH, SquashEntity.createSquashAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.THREEPEATER, ThreepeaterEntity.createThreepeaterAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TANGLE_KELP, TangleKelpEntity.createTangleKelpAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.JALAPENO, JalapenoEntity.createJalapenoAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FIRETRAIL, FireTrailEntity.createFireTrailAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SPIKEWEED, SpikeweedEntity.createSpikeweedAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TORCHWOOD, TorchwoodEntity.createTorchwoodAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TALLNUT, TallnutEntity.createTallnutAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SEASHROOM, SeashroomEntity.createSeashroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CABBAGEPULT, CabbagepultEntity.createCabbagePultAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GATLINGPEA, GatlingpeaEntity.createGatlingpeaAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TWINSUNFLOWER, TwinSunflowerEntity.createTwinSunflowerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GLOOMSHROOM, GloomshroomEntity.createGloomshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CATTAIL, CattailEntity.createCattailAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SPIKEROCK, SpikerockEntity.createSpikerockAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ICEBERGPULT, IcebergpultEntity.createIcebergPultAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BEET, BeetEntity.createBeetAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SHAMROCK, ShamrockEntity.createShamrockAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CHILLYPEPPER, ChillyPepperEntity.creatChillyPepperAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BEESHOOTER, BeeshooterEntity.createBeeshooterAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNOWQUEENPEA, SnowqueenpeaEntity.createSnowqueenpeaAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.RETROGATLING, RetroGatlingEntity.createRetroGatlingAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ACIDSHROOM, AcidshroomEntity.createAcidshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DANDELIONWEED, DandelionWeedEntity.createDandelionWeedAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PERFOOMSHROOM, PerfoomshroomEntity.createPerfoomshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BLOOMERANG, BloomerangEntity.createBloomerangAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ICEBERGLETTUCE, IcebergLettuceEntity.createIcebergLettuceAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SPRINGBEAN, SpringbeanEntity.createSpringBeanAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.COCONUTCANNON, CoconutCannonEntity.createCoconutCannonAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.LIGHTNINGREED, LightningReedEntity.createLightningReedAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEAPOD, PeapodEntity.createPeapodAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.EMPEACH, EMPeachEntity.createEMPeachAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEPPERPULT, PepperpultEntity.createPepperPultAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAMINGPEA, FlamingpeaEntity.createFlamingpeaAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ENDURIAN, EndurianEntity.createEndurianAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GOLDLEAF, GoldLeafEntity.createGoldLeafAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SHADOWSHROOM, ShadowShroomEntity.createShadowShroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MISSILETOE, MissileToeEntity.createMissileToeAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ELECTROPEA, ElectropeaEntity.createElectropeaAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEANUT, PeanutEntity.createPeanutAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GHOSTPEPPER, GhostpepperEntity.createGhostPepperAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.OLIVEPIT, OlivePitEntity.createOlivePitAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GLOOMVINE, GloomVineEntity.createGloomVineAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HEAVENLYPEACH, HeavenlyPeachEntity.createHeavenlyPeachAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MAGICSHROOM, MagicshroomEntity.createMagicshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MAGICHAT, MagichatEntity.createMagicHatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GAMBLESHROOM, GambleshroomEntity.createGambleshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GAMBLEHAT, GamblehatEntity.createGambleHatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SAUCER, SaucerEntity.createSaucerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DRIPPHYLLEIA, DripphylleiaEntity.createDripphylleiaAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PUMPKINWITCH, PumpkinWitchEntity.createPumpkinWitchAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TULIMPETER, TulimpeterEntity.createTulimpeterAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NARCISSUS, NarcissusEntity.createNarcissusAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMPATYENS, ImpatyensEntity.createImpatyensAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HAMMERFLOWER, HammerFlowerEntity.createHammerFlowerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.METEORHAMMER, MeteorHammerEntity.createMeteorHammerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FRISBLOOM, FrisbloomEntity.createFrisbloomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BEAUTYSHROOM, BeautyshroomEntity.createBeautyshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CHARMSHROOM, CharmshroomEntity.createCharmshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SMACKADAMIA, SmackadamiaEntity.createSmackadamiaAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOMBSEEDLING, BombSeedlingEntity.createBombSeedlingAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.OILYOLIVE, OilyOliveEntity.createOilyOliveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SPRINGPRINCESS, SpringPrincessEntity.createSpringPrincessAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZAPRICOT, ZapricotEntity.createZapricotAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BANANASAURUS, BananasaurusEntity.createBananasaurusAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.WEENIEBEANIE, WeenieBeanieEntity.createWeenieBeanieAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SMOOSHROOM, SmooshroomEntity.createSmooshroomAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NAVYBEAN, NavyBeanEntity.createNavyBeanAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ADMIRALNAVYBEAN, AdmiralNavyBeanEntity.createAdmiralNavyBeanAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.JUMPINGBEAN, JumpingBeanEntity.createJumpingBeanAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNFLOWERSEED, SunflowerSeedEntity.createSunflowerSeedAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BELLFLOWER, BellflowerEntity.createBellflowerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.KNIGHTPEA, KnightPeaEntity.createKnightPeashooterAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NIGHTCAP, NightcapEntity.createNightcapAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DOOMROSE, DoomRoseEntity.createDoomRoseAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DOGWOOD, DogwoodEntity.createDogwoodAttributes().build());


        /////////////////////////////////////////////////////////////////////////////////////////////////

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BROWNCOAT, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BROWNCOATHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CONEHEAD, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CONEHEADHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUCKETHEAD, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUCKETHEADHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BRICKHEAD, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BRICKHEADHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUMMERBASIC, BrowncoatEntity.createSummerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUMMERBASICHYPNO, BrowncoatEntity.createSummerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGSUMMER, FlagSummerEntity.createFlagSummerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGSUMMERHYPNO, FlagSummerEntity.createFlagSummerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUMMERCONEHEAD, BrowncoatEntity.createSummerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUMMERCONEHEADHYPNO, BrowncoatEntity.createSummerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUMMERBUCKETHEAD, BrowncoatEntity.createSummerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUMMERBUCKETHEADHYPNO, BrowncoatEntity.createSummerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCREENDOOR, BrowncoatEntity.createBrowncoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCREENDOORHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIEHYPNO, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE_G, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE_T, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE_GHYPNO, FlagzombieEntity.createFlagzombieZombieAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGZOMBIE_THYPNO, FlagzombieEntity.createFlagzombieZombieAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POLEVAULTING, PoleVaultingEntity.createPoleVaultingAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POLEVAULTINGHYPNO, PoleVaultingEntity.createPoleVaultingAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CONEHEADGEAR, PlasticHelmetEntity.createConeheadGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERPAWNGEAR, PlasticHelmetEntity.createPawnGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERKNIGHTGEAR, PlasticHelmetEntity.createKnightGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERTOWERGEAR, PlasticHelmetEntity.createTownGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERBISHOPGEAR, PlasticHelmetEntity.createBishopGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.KINGPIECEGEAR, PlasticHelmetEntity.createKingPieceGearAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BUCKETGEAR, MetalHelmetEntity.createBucketGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HELMETGEAR, MetalHelmetEntity.createHelmetGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.KNIGHTGEAR, MetalHelmetEntity.createKnightGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SOLDIERGEAR, MetalHelmetEntity.createSoldierGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MEDALLIONGEAR, MetalHelmetEntity.createMedallionGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FOOTBALLGEAR, MetalHelmetEntity.createFootballGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BERSERKERGEAR, MetalHelmetEntity.createBerserkerGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEENDGEAR, MetalHelmetEntity.createDefensiveEndGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BLASTRONAUTGEAR, MetalHelmetEntity.createBlastronautGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASSGEAR, MetalHelmetEntity.createBassGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCRAPIMPGEAR, MetalHelmetEntity.createScrapImpGearAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TOWERGEAR, StoneHelmetEntity.createTowerGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOWLGEAR, StoneHelmetEntity.createBowlGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PYRAMIDGEAR, StoneHelmetEntity.createPyramidGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BRICKGEAR, StoneHelmetEntity.createBrickGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARCOPHAGUS, StoneHelmetEntity.createSarcophagusAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HOLOGEAR, CrystalHelmetEntity.createHoloGearAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CRYSTALSHOEGEAR, CrystalHelmetEntity.createCrystalShoeGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWSPAPER, NewspaperEntity.createNewspaperAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWSPAPERHYPNO, NewspaperEntity.createNewspaperAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWSPAPERSHIELD, NewspaperShieldEntity.createNewspaperShieldAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNDAYEDITION, NewspaperEntity.createSundayEditionAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNDAYEDITIONHYPNO, NewspaperEntity.createSundayEditionAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNDAYEDITIONSHIELD, NewspaperShieldEntity.createSundayEditionShieldAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOOKBURNER, SargeantEntity.createBookBurnerAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOOKBURNERHYPNO, SargeantEntity.createBookBurnerAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOOKSHIELD, NewspaperShieldEntity.createSBookShieldAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCREENDOORSHIELD, MetalShieldEntity.createScreendoorShieldAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SERGEANTSHIELDGEAR, MetalShieldEntity.createSergeantShieldAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FOOTBALL, FootballEntity.createFootballAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FOOTBALLHYPNO, FootballEntity.createFootballAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BERSERKER, FootballEntity.createBerserkerAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BERSERKERHYPNO, FootballEntity.createBerserkerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TRASHCAN, BrowncoatEntity.createBrowncoatAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TRASHCANHYPNO, BrowncoatEntity.createBrowncoatAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TRASHCANBIN, MetalObstacleEntity.createTrashCanBinObstacleAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PUMPKINGEAR, PlantHelmetEntity.createPumpkinGearAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DANCINGZOMBIE, DancingZombieEntity.createDancingZombieAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DANCINGZOMBIEHYPNO, DancingZombieEntity.createDancingZombieAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BACKUPDANCER, BackupDancerEntity.createBackupDancerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BACKUPDANCERHYPNO, BackupDancerEntity.createBackupDancerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNORKEL, SnorkelEntity.createSnorkelAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNORKELHYPNO, SnorkelEntity.createSnorkelAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DOLPHINRIDER, DolphinRiderEntity.createDolphinRiderAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DOLPHINRIDERHYPNO, DolphinRiderEntity.createDolphinRiderAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBONI, ZomboniEntity.createZomboniAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBONIHYPNO, ZomboniEntity.createZomboniAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBONIVEHICLE, MetalVehicleEntity.createZomboniVehicleAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOBSLED, BobsledRiderEntity.createBobsledAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOBSLEDHYPNO, BobsledRiderEntity.createBobsledAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BOBSLEDVEHICLE, MetalVehicleEntity.createBobsledVehicleAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GARGANTUAR, GargantuarEntity.createGargantuarAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GARGANTUARHYPNO, GargantuarEntity.createGargantuarAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYGARGANTUAR, GargantuarEntity.createMummyGargantuarAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYGARGANTUARHYPNO, GargantuarEntity.createMummyGargantuarAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEEND, GargantuarEntity.createDefensiveendAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEENDHYPNO, GargantuarEntity.createDefensiveendAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEEND_NEWYEAR, GargantuarEntity.createDefensiveendAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO, GargantuarEntity.createDefensiveendAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.UNICORNGARGANTUAR, GargantuarEntity.createUnicornGargantuarAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.UNICORNGARGANTUARHYPNO, GargantuarEntity.createUnicornGargantuarAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CURSEDGARGOLITH, GargantuarEntity.createGargolithAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CURSEDGARGOLITHHYPNO, GargantuarEntity.createGargolithAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GARGOLITHOBSTACLE, RockObstacleEntity.createGargolithObstacleAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCRAPMECH, ScrapMechEntity.createScrapMechAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCRAPMECHHYPNO, ScrapMechEntity.createScrapMechAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMP, ImpEntity.createImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMPHYPNO, ImpEntity.createImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYIMP, ImpEntity.createImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYIMPHYPNO, ImpEntity.createImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCRAPIMP, ImpEntity.createScrapImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCRAPIMPHYPNO, ImpEntity.createScrapImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMPTHROWER, ImpEntity.createImpThrowAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMPTHROWERHYPNO, ImpEntity.createImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMPDRAGON, ImpEntity.createImpDragonAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMPDRAGONHYPNO, ImpEntity.createImpDragonAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUPERFANIMP, SuperFanImpEntity.createSuperFanImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUPERFANIMPHYPNO, SuperFanImpEntity.createSuperFanImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWYEARIMP, SuperFanImpEntity.createSuperFanImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NEWYEARIMPHYPNO, SuperFanImpEntity.createSuperFanImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASSIMP, ImpEntity.createBassImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASSIMPHYPNO, ImpEntity.createBassImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CINDERELLAIMP, ImpEntity.createCinderellaImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CINDERELLAIMPHYPNO, ImpEntity.createCinderellaImpAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PUMPKINCAR, PumpkinCarEntity.createPumpkinCarAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ANNOUNCERIMP, AnnouncerImpEntity.createAnnouncerImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ANNOUNCERIMPHYPNO, AnnouncerImpEntity.createAnnouncerImpAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.REDANNOUNCERIMP, AnnouncerImpEntity.createAnnouncerImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.REDANNOUNCERIMPHYPNO, AnnouncerImpEntity.createAnnouncerImpAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BLACKANNOUNCERIMP, AnnouncerImpEntity.createAnnouncerImpAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BLACKANNOUNCERIMPHYPNO, AnnouncerImpEntity.createAnnouncerImpAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.IMPTABLETOBSTACLE, RockObstacleEntity.createImpTabletObstaclesAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBIEKING, ZombieKingEntity.createZombieKingAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBIEKINGHYPNO, ZombieKingEntity.createZombieKingAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.REDZOMBIEKING, ZombieKingEntity.createZombieKingAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.REDZOMBIEKINGHYPNO, ZombieKingEntity.createZombieKingAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BLACKZOMBIEKING, ZombieKingEntity.createZombieKingAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BLACKZOMBIEKINGHYPNO, ZombieKingEntity.createZombieKingAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMY, MummyEntity.createMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYHYPNO, MummyEntity.createMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGMUMMY, FlagMummyEntity.createFlagMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGMUMMYHYPNO, FlagMummyEntity.createFlagMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYCONE, MummyEntity.createMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYCONEHYPNO, MummyEntity.createMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYBUCKET, MummyEntity.createMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MUMMYBUCKETHYPNO, MummyEntity.createMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TOMBRAISER, MummyEntity.createTombRaiserAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TOMBRAISERHYPNO, MummyEntity.createTombRaiserAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PYRAMIDHEAD, MummyEntity.createMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PYRAMIDHEADHYPNO, MummyEntity.createMummyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.UNDYINGPHARAOH, PharaohEntity.createUndyingPharaohAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.UNDYINGPHARAOHHYPNO, PharaohEntity.createUndyingPharaohAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PHARAOH, PharaohEntity.createPharaohAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PHARAOHHYPNO, PharaohEntity.createPharaohAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.EXPLORER, ExplorerEntity.createExplorerAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.EXPLORERHYPNO, ExplorerEntity.createExplorerAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TORCHLIGHT, ExplorerEntity.createTorchlightAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TORCHLIGHTHYPNO, ExplorerEntity.createTorchlightAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.JETPACK, JetpackEntity.createJetpackAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.JETPACKHYPNO, JetpackEntity.createJetpackAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BLASTRONAUT, JetpackEntity.createBlastronautAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BLASTRONAUTHYPNO, JetpackEntity.createBlastronautAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HOVERGOAT, HoverGoatEntity.createHoverGoatAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HOVERGOATHYPNO, HoverGoatEntity.createHoverGoatAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ROBOCONE, RoboConeEntity.createRoboconeAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASANT, PeasantEntity.createPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASANTHYPNO, PeasantEntity.createPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGPEASANT, FlagPeasantEntity.createFlagPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGPEASANTHYPNO, FlagPeasantEntity.createFlagPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASANTCONE, PeasantEntity.createPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASANTCONEHYPNO, PeasantEntity.createPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASANTBUCKET, PeasantEntity.createPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASANTBUCKETHYPNO, PeasantEntity.createPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASANTKNIGHT, PeasantEntity.createPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PEASANTKNIGHTHYPNO, PeasantEntity.createPeasantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUTUREZOMBIE, FutureZombieEntity.createFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUTUREHYPNO, FutureZombieEntity.createFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGFUTURE, FlagFutureEntity.createFlagFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGFUTUREHYPNO, FlagFutureEntity.createFlagFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUTURECONE, FutureZombieEntity.createFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUTURECONEHYPNO, FutureZombieEntity.createFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUTUREBUCKET, FutureZombieEntity.createFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUTUREBUCKETHYPNO, FutureZombieEntity.createFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HOLOHEAD, FutureZombieEntity.createFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HOLOHEADHYPNO, FutureZombieEntity.createFutureAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKER, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERHYPNO, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGPOKER, FlagPokerEntity.createFlagPokerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGPOKERHYPNO, FlagPokerEntity.createFlagPokerAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERCONE, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERCONEHYPNO, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERBUCKET, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERBUCKETHYPNO, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERPAWN, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERPAWNHYPNO, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERKNIGHT, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERKNIGHTHYPNO, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERTOWER, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERTOWERHYPNO, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERBISHOP, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POKERBISHOPHYPNO, PokerEntity.createPokerHeartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARGEANT, SargeantEntity.createSergeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARGEANTHYPNO, SargeantEntity.createSergeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARGEANTBOWL, SargeantEntity.createSergeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARGEANTBOWLHYPNO, SargeantEntity.createSergeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARGEANTHELMET, SargeantEntity.createSergeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARGEANTHELMETHYPNO, SargeantEntity.createSergeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARGEANTSHIELD, SargeantEntity.createSergeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SARGEANTSHIELDHYPNO, SargeantEntity.createSergeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGSARGEANT, FlagSargeantEntity.createFlagSargeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FLAGSARGEANTHYPNO, FlagSargeantEntity.createFlagSargeantAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PUMPKINZOMBIE, PumpkinZombieEntity.createPumpkinAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PUMPKINZOMBIEHYPNO, PumpkinZombieEntity.createPumpkinAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SOLDIER, SoldierEntity.createSoldierAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SOLDIERHYPNO, SoldierEntity.createSoldierAttributes().build());


		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BULLY, BullyEntity.createBullyAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BULLYHYPNO, BullyEntity.createBullyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ACTIONHERO, ActionheroEntity.createActionheroAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ACTIONHEROHYPNO, ActionheroEntity.createActionheroAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.OCTO, OctoEntity.createOctoAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.OCTOHYPNO, OctoEntity.createOctoAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASKETBALLCARRIER, BasketballCarrierEntity.createBasketballCarrierAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASKETBALLCARRIERHYPNO, BasketballCarrierEntity.createBasketballCarrierAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASKETBALLBIN, MetalObstacleEntity.createBasketBallBinObstacleAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASS, BassZombieEntity.createBassAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASSHYPNO, BassZombieEntity.createBassAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SPEAKER, SpeakerVehicleEntity.createSpeakerVehicleAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HAWKERPUSHER, HawkerZombieEntity.createHawkerPusherAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HAWKERPUSHERHYPNO, HawkerZombieEntity.createHawkerPusherAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HAWKERCART, WoodObstacleEntity.createHawkerCartAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.OCTOOBST, FleshObstacleEntity.createOctoObstAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCIENTIST, ScientistEntity.createScientistAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCIENTISTHYPNO, ScientistEntity.createScientistAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.HEALSTATION, MetalObstacleEntity.createHealStationAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBLOB, ZomblobEntity.createZomblobAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBLOBHYPNO, ZomblobEntity.createZomblobAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBLOBBIG, ZomblobEntity.createZomblobBigAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBLOBBIGHYPNO, ZomblobEntity.createZomblobBigAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBLOBSMALL, ZomblobEntity.createZomblobSmallAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBLOBSMALLHYPNO, ZomblobEntity.createZomblobSmallAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PIGGY, PiggyEntity.createZombiePiggyAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.PIGGYHYPNO, PiggyEntity.createZombiePiggyAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.LOCUSTSWARM, LocustSwarmEntity.createLocustSwarmAttributes().build());


        /////////////////////////////////////////////////////////////////////////////////////////////////

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SOLARWINDS, SolarWinds.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MISSILETOETARGET, MissileToeTarget.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SCORCHEDTILE, ScorchedTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ICETILE, IceTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.OILTILE, OilTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CHEESETILE, CheeseTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BANANAPEEL, BananaTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SPRINGTILE, SpringTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SNOWTILE, SnowTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.WATERTILE, WaterTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SHADOWTILE, ShadowTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SHADOWFULLTILE, ShadowFullTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.CRATERTILE, CraterTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.WEATHERTILE, CraterTile.createTileAttributes().build());
		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.TIMETILE, CraterTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.RIFTTILE, RiftTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.GOLDTILE, GoldTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MARITILE, MariTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ROSEBUDS, RoseBudTile.createTileAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.SUNBOMB, SunBombEntity.createSunBombAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.EGYPTTOMBSTONE, RockObstacleEntity.createEgyptTombstoneAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ZOMBIEGRAVESTONE, ZombieGraveEntity.createZombieGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.BASICGRAVESTONE, BasicGraveEntity.createBasicGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.NIGHTGRAVESTONE, NightGraveEntity.createNightGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.POOLGRAVESTONE, PoolGraveEntity.createPoolGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.ROOFGRAVESTONE, RoofGraveEntity.createRoofGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.EGYPTGRAVESTONE, EgyptGraveEntity.createEgyptGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FUTUREGRAVESTONE, FutureGraveEntity.createFutureGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.DARKAGESGRAVESTONE, DarkAgesGraveEntity.createDarkAgesGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.FAIRYTALEGRAVESTONE, FairyTaleGraveEntity.createFairyGraveAttributes().build());

		DefaultAttributeRegistry.DEFAULT_ATTRIBUTE_REGISTRY.put(PvZEntity.MAUSOLEUMGRAVESTONE, MausoleumGraveEntity.createMausoleumGraveAttributes().build());



	}
}
