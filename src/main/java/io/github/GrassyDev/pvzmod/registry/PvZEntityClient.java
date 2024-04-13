package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.registry.entity.environment.bananatile.BananaTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.cheesetile.CheeseTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.cratertile.CraterTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.goldtile.GoldTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.icetile.IceTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.maritile.MariTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.oiltile.OilTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.rifttile.RiftTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.rosebuds.RoseBudEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.scorchedtile.ScorchedTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowFullTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.shadowtile.ShadowTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.snowtile.SnowTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.solarwinds.SolarWindsTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.springtile.SpringTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.sunbomb.SunBombEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.target.missiletoe.MissileToeTargetRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.environment.watertile.WaterTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.basicgrave.BasicGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.darkagesgrave.DarkAgesGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.egyptgravestone.EgyptGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.fairytaleforest.FairyTaleGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.futuregrave.FutureGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.mausoleum.MausoleumGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.nightgrave.NightGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.poolgrave.PoolGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.gravestones.roofgrave.RoofGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.garden.GardenEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.GardenChallengeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.timetile.TimeTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.miscentity.gardenchallenge.weathertile.WeatherTileRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom.GamblehatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.og.gambleshroom.GambleshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.cherrybomb.CherrybombEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.chomper.ChomperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.peashooter.PeashooterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.potatomine.PotatomineEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.repeater.RepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.snowpea.SnowpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.sunflower.SunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.day.wallnutentity.WallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.fog.magnetshroom.MagnetshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.fog.seashroom.SeashroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.doomshroom.DoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.fumeshroom.FumeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.gravebuster.GravebusterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.hypnoshroom.HypnoshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.iceshroom.IceshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.puffshroom.PuffshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.scaredyshroom.ScaredyshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.night.sunshroom.SunshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.FireTrailEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.jalapeno.JalapenoEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.lilypad.LilypadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.spikeweed.SpikeweedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.squash.SquashEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tallnut.TallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.tanglekelp.TangleKelpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.threepeater.ThreepeaterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.pool.torchwood.TorchwoodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.cabbagepult.CabbagepultEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.coffeebean.CoffeeBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.roof.icebergpult.IcebergpultEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.cattail.CattailEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gatlingpea.GatlingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.gloomshroom.GloomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.spikerock.SpikerockEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1.upgrades.twinsunflower.TwinSunflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.OxygaeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.endless.oxygen.bubble.BubblePadEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.breezeshroom.BreezeshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.burstshroom.BurstshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz1c.social.superchomper.SuperChomperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.bloomerang.BloomerangEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.iceberglettuce.IcebergLettuceEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.farfuture.empeach.EMPeachEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.frostbitecaves.pepperpult.PepperpultEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.electropea.ElectropeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.flamingpea.FlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.ghostpepper.GhostPepperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.gloomvine.GloomVineEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.missiletoe.MissileToeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.olivepit.OlivePitEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.gemium.peanut.PeanutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.endurian.EndurianEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.lostcity.goldleaf.GoldLeafEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.modernday.shadowshroom.ShadowShroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.coconutcannon.CoconutCannonEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.pirateseas.springbean.SpringbeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.lightningreed.LightningReedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.wildwest.peapod.PeapodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2.ancientegypt.frisbloom.FrisbloomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.beautyshroom.BeautyshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.charm.charmshroom.CharmshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.icepea.dropea.DropeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2as.magnet.MagnetoShroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.fairytale.springprincess.SpringPrincessEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.hammerflower.HammerFlowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.impatyens.ImpatyensEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom.MagichatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.magicshroom.MagicshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.meteorhammer.MeteorHammerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.narcissus.NarcissusEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.pumpkinwitch.PumpkinWitchEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.generic.tulimpeter.TulimpeterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.heian.dripphylleia.DripphylleiaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.kongfu.heavenlypeach.HeavenlyPeachEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.renaissance.oilyolive.OilyOliveEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.loquat.LoquatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz2c.skycity.saucer.SaucerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvz3.devour.dogwood.DogwoodEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beeshooter.BeeshooterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.beet.BeetEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.chillypepper.ChillyPepperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.shamrock.ShamrockEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzadventures.snowqueenpea.SnowqueenpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.doomrose.DoomRoseEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.knightpea.KnightPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.nightcap.NightcapEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzbfn.heroes.seapea.SeapeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.chester.ChesterEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.retrogatling.RetroGatlingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.heroes.plants.vampireflower.VampireFlowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.potted.acidshroom.AcidshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.potted.perfoomshroom.PerfoomshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzgw.summon.plants.dandelionweed.DandelionWeedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smackadamia.SmackadamiaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.guardian.smallnut.SmallnutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.bombseedling.BombSeedlingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.buttonshroom.ButtonshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.kabloom.zapricot.ZapricotEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.megagrow.bananasaurus.BananasaurusEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.admiralnavybean.AdmiralNavyBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.jumpingbean.JumpingBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.navybean.NavyBeanEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.smooshroom.SmooshroomEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.smarty.weeniebeanie.WeenieBeanieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.bellflower.BellflowerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.plants.plantentity.pvzheroes.solar.sunflowerseed.SunflowerSeedEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.armor.MetalHelmetProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.groundbounce.GroundBounceEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.peanut.PeanutBowlingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.ground.wallnut.WallnutBowlingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.cabbage.ShootingCabbageEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.iceberg.ShootingIcebergEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.peanut.PeaNutProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pepper.ShootingPepperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.pumpkinproj.ShootingPumpkinEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smallnut.SmallNutProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.lobbed.smooshproj.SmooshProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.acidfume.AcidFumeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.acidspore.AcidSporeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.bark.BarkRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.boomerang.ShootingBoomerangEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.breeze.BreezeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.bubbles.BubbleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.card.ShootingCardEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.electricpea.ShootingElectricpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.frisbee.ShootingFrisbeeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.fume.FumeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.jingle.JingleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercespore.PierceSporeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.FirePiercePeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.pierce.piercingpea.PiercePeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.sky.missiletoeproj.MissileToeProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike.ShootingBeeSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.beespike.ShootingPowerBeeSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.icespike.ShootingIceSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.icespike.ShootingPowerIceSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.spike.ShootingPowerSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.spike.ShootingSpikeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword.ShootingPowerSwordEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.spiked.sword.ShootingSwordEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.armorbubble.ArmorBubbleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.coconut.CoconutEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dropea.ShootingDropEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.dye.ShootingDyeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.flamingpea.ShootingFlamingpeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.hypnoproj.HypnoProjRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.pea.ShootingPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.plasmapea.ShootingPlasmapeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.rainbowbullet.RainbowBulletEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowpea.ShootingSnowPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.snowqueenpea.ShootingSnowqueenPeaEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spit.SpitEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.straight.spore.SporeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.banana.BananaProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.cheese.CheeseProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.plants.tile.springproj.SpringProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.basketball.ShootingBasketballEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.bone.BoneProjEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.flamingbook.FlamingBookEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.laser.LaserRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.octo.ShootingOctoEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.rocket.RocketEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.soundwave.SoundwaveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.projectileentity.zombies.zpg.ZPGEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.miscentity.locustswarm.LocustswarmEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basic.BullyEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.bully.basketballcarrier.BasketballCarrierEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.oc.pumpkincar.PumpkinCarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.backupdancer.BackupDancerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.bobsledteam.BobsledEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.browncoat.modernday.BrowncoatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.dancingzombie.DancingZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.dolphinrider.DolphinRiderEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.flagzombie.modernday.FlagzombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.football.FootballEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.gargantuar.modernday.GargantuarEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.imp.modernday.ImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.newspaper.NewspaperEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.polevaulting.PoleVaultingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.snorkel.SnorkelEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz1.zomboni.ZomboniEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.darkages.PeasantEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.future.FutureZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.browncoat.mummy.MummyEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.explorer.ExplorerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.darkages.FlagPeasantEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.future.FlagFutureEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.mummy.FlagMummyEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.flagzombie.summer.FlagSummerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.announcer.AnnouncerImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.imp.superfan.SuperFanImpEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.jetpack.JetpackEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.octo.OctoEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.pharaoh.PharaohEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2.zombieking.ZombieKingEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.bass.BassZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.browncoat.fairytale.PokerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.flagzombie.fairytale.FlagPokerEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2c.pumpkinzombie.PumpkinZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.browncoat.sargeant.SargeantEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.flagzombie.sargeant.FlagSargeantEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.piggy.PiggyEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvz2o.hawker.zombie.HawkerZombieEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.actionhero.ActionheroEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzbfn.zmech.ScrapMechEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.hovergoat.HoverGoatEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.scientist.ScientistEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzgw.soldier.SoldierEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieentity.pvzh.zomblob.ZomblobEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiegrave.ZombieGraveRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.MetalVehicleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.metallicvehicle.speakervehicle.SpeakerVehicleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombiemachines.robocone.RoboConeEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.crystalhelmet.CrystalHelmetEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.fleshobstacle.FleshObstacleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallichelmet.MetalHelmetEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicobstacle.MetalObstacleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.metallicshield.MetalShieldEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.papershield.NewspaperShieldEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.planthelmet.PlantHelmetEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plantobstacle.WoodObstacleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.plastichelmet.PlasticHelmetEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.rockobstacle.RockObstacleEntityRenderer;
import io.github.GrassyDev.pvzmod.registry.entity.zombies.zombieprops.stonehelmet.StoneHelmetEntityRenderer;
import io.github.GrassyDev.pvzmod.screen.BotanyStationScreen;
import io.github.GrassyDev.pvzmod.screen.ModScreenHandlers;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

import net.minecraft.client.gui.screen.ingame.HandledScreens;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

//@Environment(EnvType.CLIENT)
public class PvZEntityClient implements ClientModInitializer {

	@Override
	public void onInitializeClient(ModContainer mod) {

		HandledScreens.register(ModScreenHandlers.BOTANY_STATION_SCREEN_HANDLER, BotanyStationScreen::new);

		EntityRendererRegistry.register(PvZEntity.GARDEN, GardenEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GARDENCHALLENGE, GardenChallengeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEASHOOTER, PeashooterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.COFFEEBEAN, CoffeeBeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNFLOWER, SunflowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHERRYBOMB, CherrybombEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.WALLNUT, WallnutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POTATOMINE, PotatomineEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEA, SnowpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHOMPER, ChomperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHESTER, ChesterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.VAMPIREFLOWER, VampireFlowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.REPEATER, RepeaterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PUFFSHROOM, PuffshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNSHROOM, SunshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUMESHROOM, FumeshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.OXYGAE, OxygaeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUBBLEPAD, BubblePadEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BREEZESHROOM, BreezeshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BURSTSHROOM, BurstshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUPERCHOMPER, SuperChomperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GRAVEBUSTER, GravebusterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HYPNOSHROOM, HypnoshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCAREDYSHROOM, ScaredyshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICESHROOM, IceshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DOOMSHROOM, DoomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LILYPAD, LilypadEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SQUASH, SquashEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.THREEPEATER, ThreepeaterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TANGLE_KELP, TangleKelpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JALAPENO, JalapenoEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FIRETRAIL, FireTrailEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIKEWEED, SpikeweedEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TORCHWOOD, TorchwoodEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TALLNUT, TallnutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SEASHROOM, SeashroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MAGNETSHROOM, MagnetshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MAGNETOSHROOM, MagnetoShroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CABBAGEPULT, CabbagepultEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GATLINGPEA, GatlingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TWINSUNFLOWER, TwinSunflowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GLOOMSHROOM, GloomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CATTAIL, CattailEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIKEROCK, SpikerockEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICEBERGPULT, IcebergpultEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BEET, BeetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SHAMROCK, ShamrockEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHILLYPEPPER, ChillyPepperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BEESHOOTER, BeeshooterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEA, SnowqueenpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.RETROGATLING, RetroGatlingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ACIDSHROOM, AcidshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DANDELIONWEED, DandelionWeedEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PERFOOMSHROOM, PerfoomshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BLOOMERANG, BloomerangEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICEBERGLETTUCE, IcebergLettuceEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPRINGBEAN, SpringbeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.COCONUTCANNON, CoconutCannonEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LIGHTNINGREED, LightningReedEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEAPOD, PeapodEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.EMPEACH, EMPeachEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEPPERPULT, PepperpultEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FLAMINGPEA, FlamingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ENDURIAN, EndurianEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GOLDLEAF, GoldLeafEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SHADOWSHROOM, ShadowShroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MISSILETOE, MissileToeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ELECTROPEA, ElectropeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEANUT, PeanutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GHOSTPEPPER, GhostPepperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.OLIVEPIT, OlivePitEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GLOOMVINE, GloomVineEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HEAVENLYPEACH, HeavenlyPeachEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MAGICSHROOM, MagicshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MAGICHAT, MagichatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GAMBLESHROOM, GambleshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GAMBLEHAT, GamblehatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LOQUAT, LoquatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SAUCER, SaucerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.OILYOLIVE, OilyOliveEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPRINGPRINCESS, SpringPrincessEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DRIPPHYLLEIA, DripphylleiaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PUMPKINWITCH, PumpkinWitchEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TULIMPETER, TulimpeterEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NARCISSUS, NarcissusEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.IMPATYENS, ImpatyensEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HAMMERFLOWER, HammerFlowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.METEORHAMMER, MeteorHammerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DROPEA, DropeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FRISBLOOM, FrisbloomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BEAUTYSHROOM, BeautyshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHARMSHROOM, CharmshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SMALLNUT, SmallnutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SMACKADAMIA, SmackadamiaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUTTONSHROOM, ButtonshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BOMBSEEDLING, BombSeedlingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZAPRICOT, ZapricotEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BANANASAURUS, BananasaurusEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.WEENIEBEANIE, WeenieBeanieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SMOOSHROOM, SmooshroomEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NAVYBEAN, NavyBeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ADMIRALNAVYBEAN, AdmiralNavyBeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JUMPINGBEAN, JumpingBeanEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNFLOWERSEED, SunflowerSeedEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BELLFLOWER, BellflowerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.KNIGHTPEA, KnightPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SEAPEA, SeapeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NIGHTCAP, NightcapEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DOOMROSE, DoomRoseEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DOGWOOD, DogwoodEntityRenderer::new);


		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.PEA, ShootingPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIT, SpitEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWPEAPROJ, ShootingSnowPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWQUEENPEAPROJ, ShootingSnowqueenPeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PIERCEPEA, PiercePeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FIREPIERCEPEA, FirePiercePeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPIKEPROJ, ShootingSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POWERSPIKE, ShootingPowerSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SWORDPROJ, ShootingSwordEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POWERSWORDPROJ, ShootingPowerSwordEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POWERICESPIKE, ShootingPowerIceSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POWERBEESPIKE, ShootingPowerBeeSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.RAINBOWBULLET, RainbowBulletEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BEESPIKE, ShootingBeeSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICESPIKEPROJ, ShootingIceSpikeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FIREPEA, ShootingFlamingpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PLASMAPEA, ShootingPlasmapeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ELECTRICPEA, ShootingElectricpeaEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPORE, SporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ACIDSPORE, AcidSporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUME, FumeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BREEZE, BreezeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ACIDFUME, AcidFumeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CABBAGE, ShootingCabbageEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICEBERG, ShootingIcebergEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SMOOSHPROJ, SmooshProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MISSILETOEPROJ, MissileToeProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BANANAPROJ, BananaProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHEESEPROJ, CheeseProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BOOMERANGPROJ, ShootingBoomerangEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CARDPROJ, ShootingCardEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.COCONUTPROJ, CoconutEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEPPERPROJ, ShootingPepperEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPRINGPROJ, SpringProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PUMPKINPROJ, ShootingPumpkinEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HYPNOPROJ, HypnoProjRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUBBLE, BubbleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ARMORBUBBLE, ArmorBubbleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DROP, ShootingDropEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DYEPROJ, ShootingDyeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FRISBEEPROJ, ShootingFrisbeeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GROUNDBOUNCE, GroundBounceEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.WALLNUTBOWLING, WallnutBowlingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEANUTBOWLING, PeanutBowlingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SMALLNUTPROJ, SmallNutProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEANUTPROJ, PeaNutProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JINGLE, JingleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PIERCESPORE, PierceSporeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BARK, BarkRenderer::new);


		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.BROWNCOAT, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BROWNCOATHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.CONEHEAD, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.CONEHEADHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BUCKETHEAD, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BUCKETHEADHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BRICKHEAD, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BRICKHEADHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SCREENDOOR, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SCREENDOORHYPNO, BrowncoatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUMMERBASIC, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUMMERBASICHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGSUMMER, FlagSummerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGSUMMERHYPNO, FlagSummerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUMMERCONEHEAD, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUMMERCONEHEADHYPNO, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUMMERBUCKETHEAD, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUMMERBUCKETHEADHYPNO, BrowncoatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIEHYPNO, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE_T, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE_THYPNO, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE_G, FlagzombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGZOMBIE_GHYPNO, FlagzombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CONEHEADGEAR, PlasticHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERPAWNGEAR, PlasticHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERKNIGHTGEAR, PlasticHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERTOWERGEAR, PlasticHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERBISHOPGEAR, PlasticHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.KINGPIECEGEAR, PlasticHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POLEVAULTING, PoleVaultingEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POLEVAULTINGHYPNO, PoleVaultingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BUCKETGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HELMETGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MEDALLIONGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BERSERKERGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FOOTBALLGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEENDGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BLASTRONAUTGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.KNIGHTGEAR, MetalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SOLDIERGEAR, MetalHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASSGEAR, MetalHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCRAPIMPGEAR, MetalHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TOWERGEAR, StoneHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BOWLGEAR, StoneHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BRICKGEAR, StoneHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PYRAMIDGEAR, StoneHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARCOPHAGUS, StoneHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HOLOGEAR, CrystalHelmetEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.CRYSTALSHOEGEAR, CrystalHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PUMPKINGEAR, PlantHelmetEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NEWSPAPER, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.NEWSPAPERHYPNO, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.NEWSPAPERSHIELD, NewspaperShieldEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNDAYEDITION, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUNDAYEDITIONHYPNO, NewspaperEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUNDAYEDITIONSHIELD, NewspaperShieldEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BOOKBURNER, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BOOKBURNERHYPNO, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BOOKSHIELD, NewspaperShieldEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAMINGBOOK, FlamingBookEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCREENDOORSHIELD, MetalShieldEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SERGEANTSHIELDGEAR, MetalShieldEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FOOTBALL, FootballEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BERSERKER, FootballEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FOOTBALLHYPNO, FootballEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BERSERKERHYPNO, FootballEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TRASHCAN, BrowncoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.TRASHCANHYPNO, BrowncoatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TRASHCANBIN, MetalObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.METALHELMETPROJ, MetalHelmetProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIE, DancingZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DANCINGZOMBIEHYPNO, DancingZombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BACKUPDANCER, BackupDancerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BACKUPDANCERHYPNO, BackupDancerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNORKEL, SnorkelEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SNORKELHYPNO, SnorkelEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DOLPHINRIDER, DolphinRiderEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DOLPHINRIDERHYPNO, DolphinRiderEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZOMBONI, ZomboniEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ZOMBONIHYPNO, ZomboniEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZOMBONIVEHICLE, MetalVehicleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BOBSLED, BobsledEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BOBSLEDHYPNO, BobsledEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BOBSLEDVEHICLE, MetalVehicleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GARGANTUAR, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.GARGANTUARHYPNO, GargantuarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MUMMYGARGANTUAR, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYGARGANTUARHYPNO, GargantuarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.UNICORNGARGANTUAR, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.UNICORNGARGANTUARHYPNO, GargantuarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CURSEDGARGOLITH, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.CURSEDGARGOLITHHYPNO, GargantuarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GARGOLITHOBSTACLE, RockObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCRAPMECH, ScrapMechEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SCRAPMECHHYPNO, ScrapMechEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LASER, LaserRenderer::new);

		EntityRendererRegistry.register(PvZEntity.IMP, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.IMPHYPNO, ImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MUMMYIMP, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYIMPHYPNO, ImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASSIMP, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BASSIMPHYPNO, ImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CINDERELLAIMP, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.CINDERELLAIMPHYPNO, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PUMPKINCAR, PumpkinCarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCRAPIMP, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SCRAPIMPHYPNO, ImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.IMPTHROWER, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.IMPTHROWERHYPNO, ImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.IMPDRAGON, ImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.IMPDRAGONHYPNO, ImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MUMMY, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYHYPNO, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGMUMMY, FlagMummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGMUMMYHYPNO, FlagMummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYCONE, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYCONEHYPNO, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYBUCKET, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.MUMMYBUCKETHYPNO, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.TOMBRAISER, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.TOMBRAISERHYPNO, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PYRAMIDHEAD, MummyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PYRAMIDHEADHYPNO, MummyEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.UNDYINGPHARAOH, PharaohEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.UNDYINGPHARAOHHYPNO, PharaohEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PHARAOH, PharaohEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PHARAOHHYPNO, PharaohEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.EXPLORER, ExplorerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.EXPLORERHYPNO, ExplorerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.TORCHLIGHT, ExplorerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.TORCHLIGHTHYPNO, ExplorerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.JETPACK, JetpackEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.JETPACKHYPNO, JetpackEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BLASTRONAUT, JetpackEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BLASTRONAUTHYPNO, JetpackEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HOVERGOAT, HoverGoatEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HOVERGOATHYPNO, HoverGoatEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ROBOCONE, RoboConeEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PEASANT, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTHYPNO, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGPEASANT, FlagPeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGPEASANTHYPNO, FlagPeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTCONE, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTCONEHYPNO, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTBUCKET, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTBUCKETHYPNO, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTKNIGHT, PeasantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PEASANTKNIGHTHYPNO, PeasantEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUTUREZOMBIE, FutureZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FUTUREHYPNO, FutureZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGFUTURE, FlagFutureEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGFUTUREHYPNO, FlagFutureEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FUTURECONE, FutureZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FUTURECONEHYPNO, FutureZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FUTUREBUCKET, FutureZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FUTUREBUCKETHYPNO, FutureZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HOLOHEAD, FutureZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HOLOHEADHYPNO, FutureZombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POKER, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERHYPNO, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGPOKER, FlagPokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGPOKERHYPNO, FlagPokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERCONE, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERCONEHYPNO, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERBUCKET, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERBUCKETHYPNO, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERPAWN, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERPAWNHYPNO, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERKNIGHT, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERKNIGHTHYPNO, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERTOWER, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERTOWERHYPNO, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERBISHOP, PokerEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.POKERBISHOPHYPNO, PokerEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SARGEANT, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARGEANTHYPNO, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARGEANTBOWL, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARGEANTBOWLHYPNO, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARGEANTHELMET, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARGEANTHELMETHYPNO, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARGEANTSHIELD, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SARGEANTSHIELDHYPNO, SargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGSARGEANT, FlagSargeantEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.FLAGSARGEANTHYPNO, FlagSargeantEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PUMPKINZOMBIE, PumpkinZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PUMPKINZOMBIEHYPNO, PumpkinZombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ANNOUNCERIMP, AnnouncerImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ANNOUNCERIMPHYPNO, AnnouncerImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.REDANNOUNCERIMP, AnnouncerImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.REDANNOUNCERIMPHYPNO, AnnouncerImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BLACKANNOUNCERIMP, AnnouncerImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BLACKANNOUNCERIMPHYPNO, AnnouncerImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.IMPTABLETOBSTACLE, RockObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZOMBIEKING, ZombieKingEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ZOMBIEKINGHYPNO, ZombieKingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.REDZOMBIEKING, ZombieKingEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.REDZOMBIEKINGHYPNO, ZombieKingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BLACKZOMBIEKING, ZombieKingEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BLACKZOMBIEKINGHYPNO, ZombieKingEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUPERFANIMP, SuperFanImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SUPERFANIMPHYPNO, SuperFanImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NEWYEARIMP, SuperFanImpEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.NEWYEARIMPHYPNO, SuperFanImpEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DEFENSIVEEND, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEENDHYPNO, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEEND_NEWYEAR, GargantuarEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.DEFENSIVEEND_NEWYEARHYPNO, GargantuarEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SOLDIER, SoldierEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SOLDIERHYPNO, SoldierEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZPG, ZPGEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ROCKETPROJ, RocketEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BULLY, BullyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BULLYHYPNO, BullyEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ACTIONHERO, ActionheroEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ACTIONHEROHYPNO, ActionheroEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.OCTO, OctoEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.OCTOHYPNO, OctoEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.OCTOPROJ, ShootingOctoEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASKETBALLCARRIER, BasketballCarrierEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BASKETBALLCARRIERHYPNO, BasketballCarrierEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASKETBALLBIN, MetalObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASKETBALLPROJ, ShootingBasketballEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BONEPROJ, BoneProjEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASS, BassZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.BASSHYPNO, BassZombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPEAKER, SpeakerVehicleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SOUNDWAVE, SoundwaveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HAWKERPUSHER, HawkerZombieEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.HAWKERPUSHERHYPNO, HawkerZombieEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HAWKERCART, WoodObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.OCTOOBST, FleshObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCIENTIST, ScientistEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.SCIENTISTHYPNO, ScientistEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.HEALSTATION, MetalObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZOMBLOB, ZomblobEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ZOMBLOBHYPNO, ZomblobEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ZOMBLOBSMALL, ZomblobEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ZOMBLOBSMALLHYPNO, ZomblobEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ZOMBLOBBIG, ZomblobEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.ZOMBLOBBIGHYPNO, ZomblobEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.PIGGY, PiggyEntityRenderer::new);
		EntityRendererRegistry.register(PvZEntity.PIGGYHYPNO, PiggyEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.LOCUSTSWARM, LocustswarmEntityRenderer::new);

		/////////////////////////////////////////////////////////////////////////////////////////////////

		EntityRendererRegistry.register(PvZEntity.SOLARWINDS, SolarWindsTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MISSILETOETARGET, MissileToeTargetRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SCORCHEDTILE, ScorchedTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ICETILE, IceTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.OILTILE, OilTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CHEESETILE, CheeseTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BANANAPEEL, BananaTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SPRINGTILE, SpringTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SNOWTILE, SnowTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.WATERTILE, WaterTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SHADOWTILE, ShadowTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SHADOWFULLTILE, ShadowFullTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.CRATERTILE, CraterTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.WEATHERTILE, WeatherTileRenderer::new);
		EntityRendererRegistry.register(PvZEntity.TIMETILE, TimeTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.RIFTTILE, RiftTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.GOLDTILE, GoldTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MARITILE, MariTileRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ROSEBUDS, RoseBudEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.SUNBOMB, SunBombEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.EGYPTTOMBSTONE, RockObstacleEntityRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ZOMBIEGRAVESTONE, ZombieGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.BASICGRAVESTONE, BasicGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.NIGHTGRAVESTONE, NightGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.POOLGRAVESTONE, PoolGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.ROOFGRAVESTONE, RoofGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.EGYPTGRAVESTONE, EgyptGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FUTUREGRAVESTONE, FutureGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.DARKAGESGRAVESTONE, DarkAgesGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.FAIRYTALEGRAVESTONE, FairyTaleGraveRenderer::new);

		EntityRendererRegistry.register(PvZEntity.MAUSOLEUMGRAVESTONE, MausoleumGraveRenderer::new);


	}
}
