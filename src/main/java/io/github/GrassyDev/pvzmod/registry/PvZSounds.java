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
	public static final Identifier POTATOMINEEXPLOSION = new Identifier("pvzmod:potatomine.explosion");
	public static SoundEvent POTATOMINEEXPLOSIONEVENT = registerSoundEvent(POTATOMINEEXPLOSION);
	public static final Identifier DOOMSHROOMEXPLOSION = new Identifier("pvzmod:doomshroom.explosion");
	public static SoundEvent DOOMSHROOMEXPLOSIONEVENT = registerSoundEvent(DOOMSHROOMEXPLOSION);
	public static final Identifier ICEBERGEXPLOSION = new Identifier("pvzmod:iceberg.explosion");
	public static SoundEvent ICEBERGEXPLOSIONEVENT = registerSoundEvent(ICEBERGEXPLOSION);
	public static final Identifier EMPEACHEXPLOSION = new Identifier("pvzmod:empeach.explosion");
	public static SoundEvent EMPEACHEXPLOSIONEVENT = registerSoundEvent(EMPEACHEXPLOSION);

	public static final Identifier POISONSPLASH = new Identifier("pvzmod:poison.splash");
	public static SoundEvent POISONSPLASHEVENT = registerSoundEvent(POISONSPLASH);

	public static final Identifier MECHALARM = new Identifier("pvzmod:mech.alarm");
	public static SoundEvent MECHALARMEVENT = registerSoundEvent(MECHALARM);

	public static final Identifier SHROOMCHARGE = new Identifier("pvzmod:smoosh.charge");
	public static SoundEvent SHROOMCHARGEEVENT = registerSoundEvent(SHROOMCHARGE);

	public static final Identifier SHROOMHIT = new Identifier("pvzmod:smoosh.smash");
	public static SoundEvent SHROOMHITEVENT = registerSoundEvent(SHROOMHIT);

	public static final Identifier SQUASHHUM = new Identifier("pvzmod:squash.hm");
	public static SoundEvent SQUASHHUMEVENT = registerSoundEvent(SQUASHHUM);

	public static final Identifier CHOMPERBITE = new Identifier("pvzmod:chomper.chomp");
	public static SoundEvent CHOMPERBITEVENT = registerSoundEvent(CHOMPERBITE);

	public static final Identifier GRAVEBUSTEREATING = new Identifier("pvzmod:gravebuster.eating");
	public static SoundEvent GRAVEBUSTEREATINGEVENT = registerSoundEvent(GRAVEBUSTEREATING);

	public static final Identifier HYPNOTIZING = new Identifier("pvzmod:hypnoshroom.hypnotizing");
	public static SoundEvent HYPNOTIZINGEVENT = registerSoundEvent(HYPNOTIZING);

	public static final Identifier GRAVERISING = new Identifier("pvzmod:grave.rising");
	public static SoundEvent GRAVERISINGEVENT = registerSoundEvent(GRAVERISING);

	public static final Identifier ENTITYRISING = new Identifier("pvzmod:entity.rising");
	public static SoundEvent ENTITYRISINGEVENT = registerSoundEvent(ENTITYRISING);

	public static final Identifier PLANTPLANTED = new Identifier("pvzmod:plant.planted");
	public static SoundEvent PLANTPLANTEDEVENT = registerSoundEvent(PLANTPLANTED);

	public static final Identifier SUNDROP = new Identifier("pvzmod:sun.drop");
	public static SoundEvent SUNDROPEVENT = registerSoundEvent(SUNDROP);

	public static final Identifier ZOMBIEBITE = new Identifier("pvzmod:zombie.bite");
	public static SoundEvent ZOMBIEBITEEVENT = registerSoundEvent(ZOMBIEBITE);

	public static final Identifier POPLIMB = new Identifier("pvzmod:pop.limb");
	public static SoundEvent POPLIMBEVENT = registerSoundEvent(POPLIMB);

	public static final Identifier POPHEAD = new Identifier("pvzmod:pop.head");
	public static SoundEvent POPHEADEVENT = registerSoundEvent(POPHEAD);

	public static final Identifier POLEVAULT = new Identifier("pvzmod:polevaulting.vault");
	public static SoundEvent POLEVAULTEVENT = registerSoundEvent(POLEVAULT);

	public static final Identifier DOLPHINJUMP = new Identifier("pvzmod:dolphin.jump");
	public static SoundEvent DOLPHINJUMPEVENT = registerSoundEvent(DOLPHINJUMP);

	public static final Identifier SOLDIERJUMP = new Identifier("pvzmod:soldier.jump");
	public static SoundEvent SOLDIERJUMPEVENT = registerSoundEvent(SOLDIERJUMP);

	public static final Identifier GARGANTUARSMASH = new Identifier("pvzmod:gargantuar.smash");
	public static SoundEvent GARGANTUARSMASHEVENT = registerSoundEvent(GARGANTUARSMASH);

	public static final Identifier SPEAKERCRASH = new Identifier("pvzmod:bass.crash");
	public static SoundEvent SPEAKERCRASHEVENT = registerSoundEvent(SPEAKERCRASH);

	public static final Identifier SOLDIERPREP = new Identifier("pvzmod:soldier.prep");
	public static SoundEvent SOLDIERPREPEVENT = registerSoundEvent(SOLDIERPREP);

	public static final Identifier TOMBRAISERLICK = new Identifier("pvzmod:tombraiser.lick");
	public static SoundEvent TOMBRAISERLICKEVENT = registerSoundEvent(TOMBRAISERLICK);

	public static final Identifier SOLDIERZPG = new Identifier("pvzmod:soldier.zpg");
	public static SoundEvent SOLDIERZPGEVENT = registerSoundEvent(SOLDIERZPG);

	public static final Identifier ROCKETRIDEJINGLE = new Identifier("pvzmod:rocketride.jingle");
	public static SoundEvent ROCKETRIDEJINGLEEVENT = registerSoundEvent(ROCKETRIDEJINGLE);


	public static final Identifier IMPLAUNCH = new Identifier("pvzmod:imp.launch");
	public static SoundEvent IMPLAUNCHEVENT = registerSoundEvent(IMPLAUNCH);

	public static final Identifier IMPANNOUNCER = new Identifier("pvzmod:imp.announcer");
	public static SoundEvent IMPANNOUNCEREVENT = registerSoundEvent(IMPANNOUNCER);

	public static final Identifier IMPANNOUNCERALT = new Identifier("pvzmod:imp.announcer.alt");
	public static SoundEvent IMPANNOUNCERALTEVENT = registerSoundEvent(IMPANNOUNCERALT);

	public static final Identifier KNIGHTTRANSFORM = new Identifier("pvzmod:knight.transform");
	public static SoundEvent KNIGHTTRANSFORMEVENT = registerSoundEvent(KNIGHTTRANSFORM);

	public static final Identifier ZOMBIEMOAN = new Identifier("pvzmod:zombie.moan");
	public static SoundEvent ZOMBIEMOANEVENT = registerSoundEvent(ZOMBIEMOAN);

	public static final Identifier DOLPHINWATER = new Identifier("pvzmod:dolphin.water");
	public static SoundEvent DOLPHINWATEREVENT = registerSoundEvent(DOLPHINWATER);

	public static final Identifier GARGANTUARMOAN = new Identifier("pvzmod:gargantuar.moan");
	public static SoundEvent GARGANTUARMOANEVENT = registerSoundEvent(GARGANTUARMOAN);

	public static final Identifier IMPMOAN = new Identifier("pvzmod:imp.moan");
	public static SoundEvent IMPMOANEVENT = registerSoundEvent(IMPMOAN);

	public static final Identifier NEWSPAPERANGRY = new Identifier("pvzmod:newspaper.angry");
	public static SoundEvent NEWSPAPERANGRYEVENT = registerSoundEvent(NEWSPAPERANGRY);

	public static final Identifier ZOMBIEDANCING = new Identifier("pvzmod:zombie.dancing");
	public static SoundEvent ZOMBIEDANCINGEVENT = registerSoundEvent(ZOMBIEDANCING);

	public static final Identifier SILENCE = new Identifier("pvzmod:silence");
	public static SoundEvent SILENCEVENET = registerSoundEvent(SILENCE);

	private static SoundEvent registerSoundEvent(String name) {
		Identifier id = new Identifier(PvZCubed.MOD_ID, name);
		return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
	}

	public static void registerSounds() {
		PvZCubed.LOGGER.info("Registering Sounds for " + PvZCubed.MOD_ID);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.LOOTNUGGET, LOOTNUGGETEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.LOOTDIAMOND, LOOTDIAMONDEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.LOOTGIFT, LOOTGIFTDEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.PEASHOOT, PEASHOOTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.PEAHIT, PEAHITEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.FIREPEAHIT, FIREPEAHITEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.CONEHIT, CONEHITEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.BUCKETHIT, BUCKETHITEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.STONEHIT, STONEHITEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SNOWPEASHOOT, SNOWPEASHOOTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SNOWPEAHIT, SNOWPEAHITEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.MUSHROOMSHOOT, MUSHROOMSHOOTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.FUMESHROOMSHOOT, FUMESHROOMSHOOTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.MECHSHOOT, MECHSHOOTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.MECHWINDUP, MECHWINDUPEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.BASSPLAY, BASSPLAYEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.MAGNETATTRACT, MAGNETATTRACTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.BLOOMERANGSHOOT, BLOOMERANGSHOOTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.LIGHTNINGSHOOT, LIGHTNINGSHOOTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.BOOMERANGAMBIENT, BOOMERANGAMBIENTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.ZPGAMBIENT, ZPGAMBIENTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.MAGICHATAPPEAR, MAGICHATAPPEAREVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.MAGICHATZOMBIE, MAGICHATZOMBIEEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.CHERRYBOMBEXPLOSION, CHERRYBOMBEXPLOSIONEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.JALAPENOEXPLOSION, JALAPENOEXPLOSIONEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.POTATOMINEEXPLOSION, POTATOMINEEXPLOSIONEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.DOOMSHROOMEXPLOSION, DOOMSHROOMEXPLOSIONEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.ICEBERGEXPLOSION, ICEBERGEXPLOSIONEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.EMPEACHEXPLOSION, EMPEACHEXPLOSIONEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.POISONSPLASH, POISONSPLASHEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.MECHALARM, MECHALARMEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SHROOMHIT, SHROOMHITEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SHROOMCHARGE, SHROOMCHARGEEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SQUASHHUM, SQUASHHUMEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.CHOMPERBITE, CHOMPERBITEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.GRAVEBUSTEREATING, GRAVEBUSTEREATINGEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.HYPNOTIZING, HYPNOTIZINGEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.GRAVERISING, GRAVERISINGEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.ENTITYRISING, ENTITYRISINGEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.PLANTPLANTED, PLANTPLANTEDEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SUNDROP, SUNDROPEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.ZOMBIEBITE, ZOMBIEBITEEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.POPLIMB, POPLIMBEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.POPHEAD, POPHEADEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.POLEVAULT, POLEVAULTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.DOLPHINJUMP, DOLPHINJUMPEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SOLDIERJUMP, SOLDIERJUMPEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.GARGANTUARSMASH, GARGANTUARSMASHEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SPEAKERCRASH, SPEAKERCRASHEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.TOMBRAISERLICK, TOMBRAISERLICKEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SOLDIERPREP, SOLDIERPREPEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SOLDIERZPG, SOLDIERZPGEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.ROCKETRIDEJINGLE, ROCKETRIDEJINGLEEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.IMPLAUNCH, IMPLAUNCHEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.IMPANNOUNCER, IMPANNOUNCEREVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.IMPANNOUNCERALT, IMPANNOUNCERALTEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.KNIGHTTRANSFORM, KNIGHTTRANSFORMEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.ZOMBIEMOAN, ZOMBIEMOANEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.IMPMOAN, IMPMOANEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.NEWSPAPERANGRY, NEWSPAPERANGRYEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.DOLPHINWATER, DOLPHINWATEREVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.GARGANTUARMOAN, GARGANTUARMOANEVENT);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.SILENCE, SILENCEVENET);
		Registry.register(Registries.SOUND_EVENT, PvZSounds.ZOMBIEDANCING, ZOMBIEDANCINGEVENT);

	}
}
