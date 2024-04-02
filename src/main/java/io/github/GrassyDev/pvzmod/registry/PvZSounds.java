package io.github.GrassyDev.pvzmod.registry;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class PvZSounds {

	public static SoundEvent LOOTNUGGETEVENT = registerSoundEvent("pvzmod:loot.nugget");
	public static SoundEvent LOOTDIAMONDEVENT = registerSoundEvent("pvzmod:loot.diamond");
	public static SoundEvent LOOTGIFTDEVENT = registerSoundEvent("pvzmod:loot.gift");

	public static SoundEvent PEASHOOTEVENT = registerSoundEvent("pvzmod:pea.shoot");

	public static SoundEvent PEAHITEVENT = registerSoundEvent("pvzmod:pea.hit");

	public static SoundEvent FIREPEAHITEVENT = registerSoundEvent("pvzmod:flaming.pea.hit");

	public static SoundEvent CONEHITEVENT = registerSoundEvent("pvzmod:cone.hit");

	public static SoundEvent BUCKETHITEVENT = registerSoundEvent("pvzmod:bucket.hit");

	public static SoundEvent STONEHITEVENT = registerSoundEvent("pvzmod:stone.hit");
	public static SoundEvent SNOWPEASHOOTEVENT = registerSoundEvent("pvzmod:snowpea.shoot");

	public static SoundEvent SNOWPEAHITEVENT = registerSoundEvent("pvzmod:snowpea.hit");
	public static SoundEvent MUSHROOMSHOOTEVENT = registerSoundEvent("pvzmod:mushroom.shoot");
	public static SoundEvent FUMESHROOMSHOOTEVENT = registerSoundEvent("pvzmod:fumeshroom.shoot");
	public static SoundEvent MECHSHOOTEVENT = registerSoundEvent("pvzmod:mech.shoot");
	public static SoundEvent MECHWINDUPEVENT = registerSoundEvent("pvzmod:mech.windup");
	public static SoundEvent BASSPLAYEVENT = registerSoundEvent("pvzmod:bass.play");
	public static SoundEvent GHOSTPEPPERPLANTEVENT = registerSoundEvent("pvzmod:ghostpepper.plant");
	public static SoundEvent MAGNETATTRACTEVENT = registerSoundEvent("pvzmod:magnet.attract");
	public static SoundEvent BLOOMERANGSHOOTEVENT = registerSoundEvent("pvzmod:bloomerang.shoot");
	public static SoundEvent LIGHTNINGSHOOTEVENT = registerSoundEvent("pvzmod:lightning.shoot");
	public static SoundEvent BOOMERANGAMBIENTEVENT = registerSoundEvent("pvzmod:boomerang.ambient");
	public static SoundEvent ZPGAMBIENTEVENT = registerSoundEvent("pvzmod:zpg.ambient");
	public static SoundEvent MAGICHATAPPEAREVENT = registerSoundEvent("pvzmod:magichat.appear");
	public static SoundEvent MAGICHATZOMBIEEVENT = registerSoundEvent("pvzmod:magichat.zombie");
	public static SoundEvent CHERRYBOMBEXPLOSIONEVENT = registerSoundEvent("pvzmod:cherrybomb.explosion");
	public static SoundEvent JALAPENOEXPLOSIONEVENT = registerSoundEvent("pvzmod:jalapeno.explosion");
	public static SoundEvent POTATOMINEEXPLOSIONEVENT = registerSoundEvent("pvzmod:potatomine.explosion");
	public static SoundEvent DOOMSHROOMEXPLOSIONEVENT = registerSoundEvent("pvzmod:doomshroom.explosion");
	public static SoundEvent ICEBERGEXPLOSIONEVENT = registerSoundEvent("pvzmod:iceberg.explosion");
	public static SoundEvent EMPEACHEXPLOSIONEVENT = registerSoundEvent("pvzmod:empeach.explosion");

	public static SoundEvent POISONSPLASHEVENT = registerSoundEvent("pvzmod:poison.splash");

	public static SoundEvent MECHALARMEVENT = registerSoundEvent("pvzmod:mech.alarm");

	public static SoundEvent SHROOMCHARGEEVENT = registerSoundEvent("pvzmod:smoosh.charge");

	public static SoundEvent SHROOMHITEVENT = registerSoundEvent("pvzmod:smoosh.smash");

	public static SoundEvent SQUASHHUMEVENT = registerSoundEvent("pvzmod:squash.hm");

	public static SoundEvent CHOMPERBITEVENT = registerSoundEvent("pvzmod:chomper.chomp");

	public static SoundEvent GRAVEBUSTEREATINGEVENT = registerSoundEvent("pvzmod:gravebuster.eating");

	public static SoundEvent HYPNOTIZINGEVENT = registerSoundEvent("pvzmod:hypnoshroom.hypnotizing");

	public static SoundEvent GRAVERISINGEVENT = registerSoundEvent("pvzmod:grave.rising");

	public static SoundEvent ENTITYRISINGEVENT = registerSoundEvent("pvzmod:entity.rising");

	public static SoundEvent PLANTPLANTEDEVENT = registerSoundEvent("pvzmod:plant.planted");

	public static SoundEvent SUNDROPEVENT = registerSoundEvent("pvzmod:sun.drop");

	public static SoundEvent ZOMBIEBITEEVENT = registerSoundEvent("pvzmod:zombie.bite");

	public static SoundEvent POPLIMBEVENT = registerSoundEvent("pvzmod:pop.limb");
	public static SoundEvent POPHEADEVENT = registerSoundEvent("pvzmod:pop.head");
	public static SoundEvent POLEVAULTEVENT = registerSoundEvent("pvzmod:polevaulting.vault");

	public static SoundEvent DOLPHINJUMPEVENT = registerSoundEvent("pvzmod:dolphin.jump");

	public static SoundEvent SOLDIERJUMPEVENT = registerSoundEvent("pvzmod:soldier.jump");

	public static SoundEvent GARGANTUARSMASHEVENT = registerSoundEvent("pvzmod:gargantuar.smash");

	public static SoundEvent SPEAKERCRASHEVENT = registerSoundEvent("pvzmod:bass.crash");

	public static SoundEvent SOLDIERPREPEVENT = registerSoundEvent("pvzmod:soldier.prep");

	public static SoundEvent TOMBRAISERLICKEVENT = registerSoundEvent("pvzmod:tombraiser.lick");

	public static SoundEvent SOLDIERZPGEVENT = registerSoundEvent("pvzmod:soldier.zpg");

	public static SoundEvent ROCKETRIDEJINGLEEVENT = registerSoundEvent("pvzmod:rocketride.jingle");

	public static SoundEvent IMPLAUNCHEVENT = registerSoundEvent("pvzmod:imp.launch");

	public static SoundEvent IMPANNOUNCEREVENT = registerSoundEvent("pvzmod:imp.announcer");
	public static SoundEvent IMPANNOUNCERALTEVENT = registerSoundEvent("pvzmod:imp.announcer.alt");

	public static SoundEvent KNIGHTTRANSFORMEVENT = registerSoundEvent("pvzmod:knight.transform");

	public static SoundEvent ZOMBIEMOANEVENT = registerSoundEvent("pvzmod:zombie.moan");
	public static SoundEvent DOLPHINWATEREVENT = registerSoundEvent("pvzmod:dolphin.water");

	public static SoundEvent GARGANTUARMOANEVENT = registerSoundEvent("pvzmod:gargantuar.moan");

	public static SoundEvent IMPMOANEVENT = registerSoundEvent("pvzmod:imp.moan");

	public static SoundEvent NEWSPAPERANGRYEVENT = registerSoundEvent("pvzmod:newspaper.angry");

	public static SoundEvent ZOMBIEDANCINGEVENT = registerSoundEvent("pvzmod:zombie.dancing");

	public static SoundEvent SILENCEVENET = registerSoundEvent("pvzmod:silence");

	private static SoundEvent registerSoundEvent(String name) {
		Identifier id = new Identifier(PvZCubed.MOD_ID, name);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static void registerSounds() {
		PvZCubed.LOGGER.info("Registering Sounds for " + PvZCubed.MOD_ID);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.LOOTNUGGET, LOOTNUGGETEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.LOOTDIAMOND, LOOTDIAMONDEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.LOOTGIFT, LOOTGIFTDEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.PEASHOOT, PEASHOOTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.PEAHIT, PEAHITEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.FIREPEAHIT, FIREPEAHITEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.CONEHIT, CONEHITEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.BUCKETHIT, BUCKETHITEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.STONEHIT, STONEHITEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SNOWPEASHOOT, SNOWPEASHOOTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SNOWPEAHIT, SNOWPEAHITEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.MUSHROOMSHOOT, MUSHROOMSHOOTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.FUMESHROOMSHOOT, FUMESHROOMSHOOTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.MECHSHOOT, MECHSHOOTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.MECHWINDUP, MECHWINDUPEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.BASSPLAY, BASSPLAYEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.MAGNETATTRACT, MAGNETATTRACTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.BLOOMERANGSHOOT, BLOOMERANGSHOOTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.LIGHTNINGSHOOT, LIGHTNINGSHOOTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.BOOMERANGAMBIENT, BOOMERANGAMBIENTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.ZPGAMBIENT, ZPGAMBIENTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.MAGICHATAPPEAR, MAGICHATAPPEAREVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.MAGICHATZOMBIE, MAGICHATZOMBIEEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.CHERRYBOMBEXPLOSION, CHERRYBOMBEXPLOSIONEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.JALAPENOEXPLOSION, JALAPENOEXPLOSIONEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.POTATOMINEEXPLOSION, POTATOMINEEXPLOSIONEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.DOOMSHROOMEXPLOSION, DOOMSHROOMEXPLOSIONEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.ICEBERGEXPLOSION, ICEBERGEXPLOSIONEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.EMPEACHEXPLOSION, EMPEACHEXPLOSIONEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.POISONSPLASH, POISONSPLASHEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.MECHALARM, MECHALARMEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SHROOMHIT, SHROOMHITEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SHROOMCHARGE, SHROOMCHARGEEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SQUASHHUM, SQUASHHUMEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.CHOMPERBITE, CHOMPERBITEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.GRAVEBUSTEREATING, GRAVEBUSTEREATINGEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.HYPNOTIZING, HYPNOTIZINGEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.GRAVERISING, GRAVERISINGEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.ENTITYRISING, ENTITYRISINGEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.PLANTPLANTED, PLANTPLANTEDEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SUNDROP, SUNDROPEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.ZOMBIEBITE, ZOMBIEBITEEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.POPLIMB, POPLIMBEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.POPHEAD, POPHEADEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.POLEVAULT, POLEVAULTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.DOLPHINJUMP, DOLPHINJUMPEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SOLDIERJUMP, SOLDIERJUMPEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.GARGANTUARSMASH, GARGANTUARSMASHEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SPEAKERCRASH, SPEAKERCRASHEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.TOMBRAISERLICK, TOMBRAISERLICKEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SOLDIERPREP, SOLDIERPREPEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SOLDIERZPG, SOLDIERZPGEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.ROCKETRIDEJINGLE, ROCKETRIDEJINGLEEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.IMPLAUNCH, IMPLAUNCHEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.IMPANNOUNCER, IMPANNOUNCEREVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.IMPANNOUNCERALT, IMPANNOUNCERALTEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.KNIGHTTRANSFORM, KNIGHTTRANSFORMEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.ZOMBIEMOAN, ZOMBIEMOANEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.IMPMOAN, IMPMOANEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.NEWSPAPERANGRY, NEWSPAPERANGRYEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.DOLPHINWATER, DOLPHINWATEREVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.GARGANTUARMOAN, GARGANTUARMOANEVENT);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.SILENCE, SILENCEVENET);
//		Registry.register(Registries.SOUND_EVENT, PvZSounds.ZOMBIEDANCING, ZOMBIEDANCINGEVENT);

	}
}
