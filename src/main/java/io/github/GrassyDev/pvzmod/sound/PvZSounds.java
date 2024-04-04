package io.github.GrassyDev.pvzmod.sound;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class PvZSounds {

	public static SoundEvent LOOTNUGGETEVENT = registerSoundEvent("loot_nugget");
	public static SoundEvent LOOTDIAMONDEVENT = registerSoundEvent("loot_diamond");
	public static SoundEvent LOOTGIFTDEVENT = registerSoundEvent("loot_gift");

	public static SoundEvent PEASHOOTEVENT = registerSoundEvent("pea_shoot");

	public static SoundEvent PEAHITEVENT = registerSoundEvent("pea_hit");

	public static SoundEvent FIREPEAHITEVENT = registerSoundEvent("flaming_pea_hit");

	public static SoundEvent CONEHITEVENT = registerSoundEvent("cone_hit");

	public static SoundEvent BUCKETHITEVENT = registerSoundEvent("bucket_hit");

	public static SoundEvent STONEHITEVENT = registerSoundEvent("stone_hit");
	public static SoundEvent SNOWPEASHOOTEVENT = registerSoundEvent("snowpea_shoot");

	public static SoundEvent SNOWPEAHITEVENT = registerSoundEvent("snowpea_hit");
	public static SoundEvent MUSHROOMSHOOTEVENT = registerSoundEvent("mushroom_shoot");
	public static SoundEvent FUMESHROOMSHOOTEVENT = registerSoundEvent("fumeshroom_shoot");
	public static SoundEvent MECHSHOOTEVENT = registerSoundEvent("mech_shoot");
	public static SoundEvent MECHWINDUPEVENT = registerSoundEvent("mech_windup");
	public static SoundEvent BASSPLAYEVENT = registerSoundEvent("bass_play");
	public static SoundEvent GHOSTPEPPERPLANTEVENT = registerSoundEvent("ghostpepper_plant");
	public static SoundEvent MAGNETATTRACTEVENT = registerSoundEvent("magnet_attract");
	public static SoundEvent BLOOMERANGSHOOTEVENT = registerSoundEvent("bloomerang_shoot");
	public static SoundEvent LIGHTNINGSHOOTEVENT = registerSoundEvent("lightning_shoot");
	public static SoundEvent BOOMERANGAMBIENTEVENT = registerSoundEvent("boomerang_ambient");
	public static SoundEvent ZPGAMBIENTEVENT = registerSoundEvent("zpg_ambient");
	public static SoundEvent MAGICHATAPPEAREVENT = registerSoundEvent("magichat_appear");
	public static SoundEvent MAGICHATZOMBIEEVENT = registerSoundEvent("magichat_zombie");
	public static SoundEvent CHERRYBOMBEXPLOSIONEVENT = registerSoundEvent("cherrybomb_explosion");
	public static SoundEvent JALAPENOEXPLOSIONEVENT = registerSoundEvent("jalapeno_explosion");
	public static SoundEvent POTATOMINEEXPLOSIONEVENT = registerSoundEvent("potatomine_explosion");
	public static SoundEvent DOOMSHROOMEXPLOSIONEVENT = registerSoundEvent("doomshroom_explosion");
	public static SoundEvent ICEBERGEXPLOSIONEVENT = registerSoundEvent("iceberg_explosion");
	public static SoundEvent EMPEACHEXPLOSIONEVENT = registerSoundEvent("empeach_explosion");

	public static SoundEvent POISONSPLASHEVENT = registerSoundEvent("poison_splash");

	public static SoundEvent MECHALARMEVENT = registerSoundEvent("mech_alarm");

	public static SoundEvent SHROOMCHARGEEVENT = registerSoundEvent("smoosh_charge");

	public static SoundEvent SHROOMHITEVENT = registerSoundEvent("smoosh_smash");

	public static SoundEvent SQUASHHUMEVENT = registerSoundEvent("squash_hm");

	public static SoundEvent CHOMPERBITEVENT = registerSoundEvent("chomper_chomp");

	public static SoundEvent GRAVEBUSTEREATINGEVENT = registerSoundEvent("gravebuster_eating");

	public static SoundEvent HYPNOTIZINGEVENT = registerSoundEvent("hypnoshroom_hypnotizing");

	public static SoundEvent GRAVERISINGEVENT = registerSoundEvent("grave_rising");

	public static SoundEvent ENTITYRISINGEVENT = registerSoundEvent("entity_rising");

	public static SoundEvent PLANTPLANTEDEVENT = registerSoundEvent("plant_planted");

	public static SoundEvent SUNDROPEVENT = registerSoundEvent("sun_drop");

	public static SoundEvent ZOMBIEBITEEVENT = registerSoundEvent("zombie_bite");

	public static SoundEvent POPLIMBEVENT = registerSoundEvent("pop_limb");
	public static SoundEvent POPHEADEVENT = registerSoundEvent("pop_head");
	public static SoundEvent POLEVAULTEVENT = registerSoundEvent("polevaulting_vault");

	public static SoundEvent DOLPHINJUMPEVENT = registerSoundEvent("dolphin_jump");

	public static SoundEvent SOLDIERJUMPEVENT = registerSoundEvent("soldier_jump");

	public static SoundEvent GARGANTUARSMASHEVENT = registerSoundEvent("gargantuar_smash");

	public static SoundEvent SPEAKERCRASHEVENT = registerSoundEvent("bass_crash");

	public static SoundEvent SOLDIERPREPEVENT = registerSoundEvent("soldier_prep");

	public static SoundEvent TOMBRAISERLICKEVENT = registerSoundEvent("tombraiser_lick");

	public static SoundEvent SOLDIERZPGEVENT = registerSoundEvent("soldier_zpg");

	public static SoundEvent ROCKETRIDEJINGLEEVENT = registerSoundEvent("rocketride_jingle");

	public static SoundEvent IMPLAUNCHEVENT = registerSoundEvent("imp_launch");

	public static SoundEvent IMPANNOUNCEREVENT = registerSoundEvent("imp_announcer");
	public static SoundEvent IMPANNOUNCERALTEVENT = registerSoundEvent("imp_announcer_alt");

	public static SoundEvent KNIGHTTRANSFORMEVENT = registerSoundEvent("knight_transform");

	public static SoundEvent ZOMBIEMOANEVENT = registerSoundEvent("zombie_moan");
	public static SoundEvent DOLPHINWATEREVENT = registerSoundEvent("dolphin_water");

	public static SoundEvent GARGANTUARMOANEVENT = registerSoundEvent("gargantuar_moan");

	public static SoundEvent IMPMOANEVENT = registerSoundEvent("imp_moan");

	public static SoundEvent NEWSPAPERANGRYEVENT = registerSoundEvent("newspaper_angry");

	public static SoundEvent ZOMBIEDANCINGEVENT = registerSoundEvent("zombie_dancing");

	public static SoundEvent SILENCEVENET = registerSoundEvent("silence");


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
