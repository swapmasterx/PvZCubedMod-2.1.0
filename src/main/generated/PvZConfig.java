package io.github.GrassyDev.pvzmod.config;

import blue.endless.jankson.Jankson;
import io.wispforest.owo.config.ConfigWrapper;
import io.wispforest.owo.config.Option;
import io.wispforest.owo.util.Observable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PvZConfig extends ConfigWrapper<io.github.GrassyDev.pvzmod.config.PvZConfigModel> {

    public final Keys keys = new Keys();

    private final Option<java.lang.Boolean> nestedSpawns_spawnPlants = this.optionForKey(this.keys.nestedSpawns_spawnPlants);
    private final Option<java.lang.Boolean> nestedSpawns_specialZombieSpawn = this.optionForKey(this.keys.nestedSpawns_specialZombieSpawn);
    private final Option<java.lang.Boolean> nestedSpawns_hoeAlternative = this.optionForKey(this.keys.nestedSpawns_hoeAlternative);
    private final Option<java.lang.Integer> nestedSpawns_hoeBreak = this.optionForKey(this.keys.nestedSpawns_hoeBreak);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_basicGv2 = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_basicGv2);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_basicGmin = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_basicGmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_basicGmax = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_basicGmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_nightGv2 = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_nightGv2);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_nightGmin = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_nightGmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_nightGmax = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_nightGmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_poolGv2 = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_poolGv2);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_poolGmin = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_poolGmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_poolGmax = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_poolGmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_roofGv2 = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_roofGv2);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_roofGmin = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_roofGmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_roofGmax = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_roofGmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_egyptG = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_egyptG);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_egyptGmin = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_egyptGmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_egyptGmax = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_egyptGmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_futureGv2 = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_futureGv2);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_futureGmin = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_futureGmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_futureGmax = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_futureGmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_darkagesGv2 = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_darkagesGv2);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_darkagesGmin = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_darkagesGmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_darkagesGmax = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_darkagesGmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_mausoleumG = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_mausoleumG);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_mausoleumGmin = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_mausoleumGmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedGraveSpawns_mausoleumGmax = this.optionForKey(this.keys.nestedSpawns_nestedGraveSpawns_mausoleumGmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_peashooterSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_peashooterSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_peashooterSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_peashooterSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_peashooterSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_peashooterSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_bellflowerSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_bellflowerSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_bellflowerSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_bellflowerSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_bellflowerSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_bellflowerSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_puffshroomSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_puffshroomSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_puffshroomSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_puffshroomSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_puffshroomSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_puffshroomSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_shadowshroomSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_shadowshroomSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_shadowshroomSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_shadowshroomSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_shadowshroomSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_shadowshroomSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_weeniebeanieSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_weeniebeanieSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_weeniebeanieSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_weeniebeanieSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_weeniebeanieSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_weeniebeanieSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_sunflowerseedSPv2 = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_sunflowerseedSPv2);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_sunflowerseedSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_sunflowerseedSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_sunflowerseedSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_sunflowerseedSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_lilypadSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_lilypadSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_lilypadSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_lilypadSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_lilypadSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_lilypadSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_bombseedlingSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_bombseedlingSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_bombseedlingSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_bombseedlingSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_bombseedlingSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_bombseedlingSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_smallnutSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_smallnutSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_smallnutSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_smallnutSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_smallnutSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_smallnutSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_loquatSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_loquatSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_loquatSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_loquatSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_loquatSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_loquatSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_icebergSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_icebergSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_icebergSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_icebergSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_icebergSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_icebergSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_zapricotSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_zapricotSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_zapricotSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_zapricotSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_zapricotSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_zapricotSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_buttonshroomSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_buttonshroomSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_buttonshroomSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_buttonshroomSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_buttonshroomSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_buttonshroomSPmax);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_solarwindSP = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_solarwindSP);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_solarwindSPmin = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_solarwindSPmin);
    private final Option<java.lang.Integer> nestedSpawns_nestedPlantSpawns_solarwindSPmax = this.optionForKey(this.keys.nestedSpawns_nestedPlantSpawns_solarwindSPmax);
    private final Option<java.lang.Boolean> nestedSeeds_infiniteSeeds = this.optionForKey(this.keys.nestedSeeds_infiniteSeeds);
    private final Option<java.lang.Boolean> nestedSeeds_instantRecharge = this.optionForKey(this.keys.nestedSeeds_instantRecharge);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_acidshrooomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_acidshrooomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_admiralnavybeanS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_admiralnavybeanS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_bananasaurusS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_bananasaurusS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_beautyshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_beautyshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_beeshooterS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_beeshooterS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_beetS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_beetS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_bellflowerS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_bellflowerS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_bloomerangS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_bloomerangS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_bombseedlingS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_bombseedlingS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_breezesroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_breezesroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_burstshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_burstshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_buttonshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_buttonshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_cabbagepultS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_cabbagepultS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_kernalpultS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_kernalpultS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_melonpultS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_melonpultS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_melonsliceS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_melonsliceS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_WintermelonS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_WintermelonS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_cattailS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_cattailS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_coffeeBeanS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_coffeeBeanS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_charmshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_charmshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_cherrybombS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_cherrybombS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_chesterS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_chesterS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_chillypepperS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_chillypepperS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_chomperS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_chomperS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_coconutS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_coconutS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_dandelionweedS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_dandelionweedS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_dogwoodS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_dogwoodS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_doomroseS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_doomroseS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_doomshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_doomshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_dripphylleiaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_dripphylleiaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_dropeaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_dropeaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_electropeaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_electropeaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_empeachS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_empeachS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_endurianS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_endurianS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_firepeaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_firepeaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_frisbloomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_frisbloomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_fumeshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_fumeshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_gambleshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_gambleshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_gatlingpeaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_gatlingpeaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_ghostpepperS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_ghostpepperS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_gloomvineS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_gloomvineS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_gloomshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_gloomshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_goldleafS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_goldleafS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_gravebusterS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_gravebusterS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_hammerflowerS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_hammerflowerS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_heavenlypeachS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_heavenlypeachS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_hypnoshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_hypnoshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_iceberglettuceS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_iceberglettuceS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_icebergpultS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_icebergpultS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_iceshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_iceshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_impatyensS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_impatyensS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_jalapenoS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_jalapenoS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_jumpingbeanS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_jumpingbeanS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_knightpeaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_knightpeaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_lightningreedS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_lightningreedS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_lilypadS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_lilypadS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_loquatS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_loquatS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_locococoS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_locococoS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_magicshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_magicshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_magnetshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_magnetshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_magnetoshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_magnetoshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_meteorhammerS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_meteorhammerS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_missileToeS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_missileToeS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_narcissusS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_narcissusS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_navybeanS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_navybeanS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_nightcapS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_nightcapS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_oilyOliveS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_oilyOliveS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_olivepitS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_olivepitS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_oxygaeS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_oxygaeS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_peanutS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_peanutS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_peapodS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_peapodS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_peashooterS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_peashooterS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_pepperpultS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_pepperpultS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_perfoomshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_perfoomshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_potatomineS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_potatomineS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_puffshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_puffshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_pumpkinwitchS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_pumpkinwitchS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_repeaterS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_repeaterS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_retrogatlingS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_retrogatlingS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_saucerS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_saucerS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_scaredyshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_scaredyshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_seapeaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_seapeaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_seashroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_seashroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_shadowShroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_shadowShroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_shamrockS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_shamrockS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_smackadamiaSv2 = this.optionForKey(this.keys.nestedSeeds_moreSeeds_smackadamiaSv2);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_smallnutS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_smallnutS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_smooshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_smooshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_snowpeaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_snowpeaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_snowqueenpeaS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_snowqueenpeaS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_spikerockS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_spikerockS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_spikeweedS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_spikeweedS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_springbeanS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_springbeanS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_springprincessS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_springprincessS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_squashS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_squashS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_sunflowerS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_sunflowerS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_sunflowerseedS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_sunflowerseedS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_sunshroomS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_sunshroomS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_superchomperS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_superchomperS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_tallnutSv2 = this.optionForKey(this.keys.nestedSeeds_moreSeeds_tallnutSv2);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_tanglekelpS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_tanglekelpS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_threepeaterS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_threepeaterS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_torchwoodS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_torchwoodS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_tulimpeterS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_tulimpeterS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_vampireflowerS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_vampireflowerS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_twinsunflowerS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_twinsunflowerS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_wintermelonS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_wintermelonS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_wallnutSv2 = this.optionForKey(this.keys.nestedSeeds_moreSeeds_wallnutSv2);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_weeniebeanieS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_weeniebeanieS);
    private final Option<java.lang.Float> nestedSeeds_moreSeeds_zapricotS = this.optionForKey(this.keys.nestedSeeds_moreSeeds_zapricotS);
    private final Option<java.lang.Float> nestedSun_sunflowerSec = this.optionForKey(this.keys.nestedSun_sunflowerSec);
    private final Option<java.lang.Float> nestedSun_sunflowerSecInitial = this.optionForKey(this.keys.nestedSun_sunflowerSecInitial);
    private final Option<java.lang.Boolean> nestedSun_sunflowerDropSun = this.optionForKey(this.keys.nestedSun_sunflowerDropSun);
    private final Option<java.lang.Float> nestedSun_twinSunflowerSec = this.optionForKey(this.keys.nestedSun_twinSunflowerSec);
    private final Option<java.lang.Float> nestedSun_sunshroomSec = this.optionForKey(this.keys.nestedSun_sunshroomSec);
    private final Option<java.lang.Float> nestedSun_sunshroomSecInitial = this.optionForKey(this.keys.nestedSun_sunshroomSecInitial);
    private final Option<java.lang.Float> nestedSun_sunshroomSunChance = this.optionForKey(this.keys.nestedSun_sunshroomSunChance);
    private final Option<java.lang.Float> nestedSun_sunshroomSun2ndChance = this.optionForKey(this.keys.nestedSun_sunshroomSun2ndChance);
    private final Option<java.lang.Float> nestedSun_goldtileSec = this.optionForKey(this.keys.nestedSun_goldtileSec);
    private final Option<java.lang.Float> nestedSun_sunseedSec = this.optionForKey(this.keys.nestedSun_sunseedSec);
    private final Option<java.lang.Float> nestedSun_zombiegraveSec = this.optionForKey(this.keys.nestedSun_zombiegraveSec);
    private final Option<java.lang.Float> nestedProjDMG_acidFumeDMG = this.optionForKey(this.keys.nestedProjDMG_acidFumeDMG);
    private final Option<java.lang.Float> nestedProjDMG_acidSporeDMG = this.optionForKey(this.keys.nestedProjDMG_acidSporeDMG);
    private final Option<java.lang.Float> nestedProjDMG_armorBubbleDMG = this.optionForKey(this.keys.nestedProjDMG_armorBubbleDMG);
    private final Option<java.lang.Float> nestedProjDMG_beespikeDMGv2 = this.optionForKey(this.keys.nestedProjDMG_beespikeDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_breezeDMG = this.optionForKey(this.keys.nestedProjDMG_breezeDMG);
    private final Option<java.lang.Float> nestedProjDMG_boomerangDMGv2 = this.optionForKey(this.keys.nestedProjDMG_boomerangDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_bubblesDMG = this.optionForKey(this.keys.nestedProjDMG_bubblesDMG);
    private final Option<java.lang.Float> nestedProjDMG_cabbageDMG = this.optionForKey(this.keys.nestedProjDMG_cabbageDMG);
    private final Option<java.lang.Float> nestedProjDMG_kernalDMG = this.optionForKey(this.keys.nestedProjDMG_kernalDMG);
    private final Option<java.lang.Float> nestedProjDMG_butterDMG = this.optionForKey(this.keys.nestedProjDMG_butterDMG);
    private final Option<java.lang.Float> nestedProjDMG_melonDMG = this.optionForKey(this.keys.nestedProjDMG_melonDMG);
    private final Option<java.lang.Float> nestedProjDMG_melonSDMG = this.optionForKey(this.keys.nestedProjDMG_melonSDMG);
    private final Option<java.lang.Float> nestedProjDMG_melonsliceDMG = this.optionForKey(this.keys.nestedProjDMG_melonsliceDMG);
    private final Option<java.lang.Float> nestedProjDMG_melonsliceSDMG = this.optionForKey(this.keys.nestedProjDMG_melonsliceSDMG);
    private final Option<java.lang.Float> nestedProjDMG_wintermelonDMG = this.optionForKey(this.keys.nestedProjDMG_wintermelonDMG);
    private final Option<java.lang.Float> nestedProjDMG_wintermelonSDMG = this.optionForKey(this.keys.nestedProjDMG_wintermelonSDMG);
    private final Option<java.lang.Float> nestedProjDMG_cardDMGv2 = this.optionForKey(this.keys.nestedProjDMG_cardDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_coconutDMGv2 = this.optionForKey(this.keys.nestedProjDMG_coconutDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_coconutSDMG = this.optionForKey(this.keys.nestedProjDMG_coconutSDMG);
    private final Option<java.lang.Float> nestedProjDMG_dropDMGv2 = this.optionForKey(this.keys.nestedProjDMG_dropDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_dropSDMG = this.optionForKey(this.keys.nestedProjDMG_dropSDMG);
    private final Option<java.lang.Float> nestedProjDMG_dyeDMG = this.optionForKey(this.keys.nestedProjDMG_dyeDMG);
    private final Option<java.lang.Float> nestedProjDMG_electricPeaDMG = this.optionForKey(this.keys.nestedProjDMG_electricPeaDMG);
    private final Option<java.lang.Float> nestedProjDMG_firepiercepeaDMG = this.optionForKey(this.keys.nestedProjDMG_firepiercepeaDMG);
    private final Option<java.lang.Float> nestedProjDMG_firepiercepeaSDMG = this.optionForKey(this.keys.nestedProjDMG_firepiercepeaSDMG);
    private final Option<java.lang.Float> nestedProjDMG_frisbeeDMG = this.optionForKey(this.keys.nestedProjDMG_frisbeeDMG);
    private final Option<java.lang.Float> nestedProjDMG_flamingPeaDMGv2 = this.optionForKey(this.keys.nestedProjDMG_flamingPeaDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_flamingPeaSDMG = this.optionForKey(this.keys.nestedProjDMG_flamingPeaSDMG);
    private final Option<java.lang.Float> nestedProjDMG_fumeDMG = this.optionForKey(this.keys.nestedProjDMG_fumeDMG);
    private final Option<java.lang.Float> nestedProjDMG_goldencardDMG = this.optionForKey(this.keys.nestedProjDMG_goldencardDMG);
    private final Option<java.lang.Float> nestedProjDMG_hypnoprojDMGv2 = this.optionForKey(this.keys.nestedProjDMG_hypnoprojDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_icebergDMGv2 = this.optionForKey(this.keys.nestedProjDMG_icebergDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_icebergSDMG = this.optionForKey(this.keys.nestedProjDMG_icebergSDMG);
    private final Option<java.lang.Float> nestedProjDMG_icespikeDMGv2 = this.optionForKey(this.keys.nestedProjDMG_icespikeDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_iceSpikeMultiplier = this.optionForKey(this.keys.nestedProjDMG_iceSpikeMultiplier);
    private final Option<java.lang.Float> nestedProjDMG_jingleDMGv2 = this.optionForKey(this.keys.nestedProjDMG_jingleDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_missileToeDMGNear = this.optionForKey(this.keys.nestedProjDMG_missileToeDMGNear);
    private final Option<java.lang.Float> nestedProjDMG_missileToeDMGFar = this.optionForKey(this.keys.nestedProjDMG_missileToeDMGFar);
    private final Option<java.lang.Float> nestedProjDMG_missileToeDMGRangeNear = this.optionForKey(this.keys.nestedProjDMG_missileToeDMGRangeNear);
    private final Option<java.lang.Float> nestedProjDMG_missileToeDMGRangeFar = this.optionForKey(this.keys.nestedProjDMG_missileToeDMGRangeFar);
    private final Option<java.lang.Float> nestedProjDMG_peaDMG = this.optionForKey(this.keys.nestedProjDMG_peaDMG);
    private final Option<java.lang.Float> nestedProjDMG_pepperDMGv2 = this.optionForKey(this.keys.nestedProjDMG_pepperDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_pepperSDMG = this.optionForKey(this.keys.nestedProjDMG_pepperSDMG);
    private final Option<java.lang.Float> nestedProjDMG_piercepeaDMG = this.optionForKey(this.keys.nestedProjDMG_piercepeaDMG);
    private final Option<java.lang.Float> nestedProjDMG_piercesporeDMG = this.optionForKey(this.keys.nestedProjDMG_piercesporeDMG);
    private final Option<java.lang.Float> nestedProjDMG_plasmaPeaDMG = this.optionForKey(this.keys.nestedProjDMG_plasmaPeaDMG);
    private final Option<java.lang.Float> nestedProjDMG_rainbowBulletDMG = this.optionForKey(this.keys.nestedProjDMG_rainbowBulletDMG);
    private final Option<java.lang.Float> nestedProjDMG_snowPeaDMG = this.optionForKey(this.keys.nestedProjDMG_snowPeaDMG);
    private final Option<java.lang.Float> nestedProjDMG_snowQueenPeaDMGv2 = this.optionForKey(this.keys.nestedProjDMG_snowQueenPeaDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_snowQueenPeaSDMG = this.optionForKey(this.keys.nestedProjDMG_snowQueenPeaSDMG);
    private final Option<java.lang.Float> nestedProjDMG_springDMG = this.optionForKey(this.keys.nestedProjDMG_springDMG);
    private final Option<java.lang.Float> nestedProjDMG_spikeDMGv2 = this.optionForKey(this.keys.nestedProjDMG_spikeDMGv2);
    private final Option<java.lang.Float> nestedProjDMG_spitDMG = this.optionForKey(this.keys.nestedProjDMG_spitDMG);
    private final Option<java.lang.Float> nestedProjDMG_smooshProjDMG = this.optionForKey(this.keys.nestedProjDMG_smooshProjDMG);
    private final Option<java.lang.Float> nestedProjDMG_sporeDMG = this.optionForKey(this.keys.nestedProjDMG_sporeDMG);
    private final Option<java.lang.Float> nestedProjDMG_swordDMG = this.optionForKey(this.keys.nestedProjDMG_swordDMG);
    private final Option<java.lang.Float> nestedProjDMG_basketBallDMG = this.optionForKey(this.keys.nestedProjDMG_basketBallDMG);
    private final Option<java.lang.Float> nestedProjDMG_laserDMG = this.optionForKey(this.keys.nestedProjDMG_laserDMG);
    private final Option<java.lang.Float> nestedProjDMG_soundwaveDMG = this.optionForKey(this.keys.nestedProjDMG_soundwaveDMG);
    private final Option<java.lang.Float> nestedProjDMG_zpgDMG = this.optionForKey(this.keys.nestedProjDMG_zpgDMG);
    private final Option<java.lang.Float> nestedProjDMG_rocketDMG = this.optionForKey(this.keys.nestedProjDMG_rocketDMG);
    private final Option<java.lang.Float> nestedGeneralZombie_zombieStep = this.optionForKey(this.keys.nestedGeneralZombie_zombieStep);
    private final Option<java.lang.Integer> nestedGeneralZombie_zombieBlockJump = this.optionForKey(this.keys.nestedGeneralZombie_zombieBlockJump);
    private final Option<java.lang.Double> nestedZombieHealth_zombieGraveH = this.optionForKey(this.keys.nestedZombieHealth_zombieGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_basicGraveH = this.optionForKey(this.keys.nestedZombieHealth_basicGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_nightGraveH = this.optionForKey(this.keys.nestedZombieHealth_nightGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_poolGraveH = this.optionForKey(this.keys.nestedZombieHealth_poolGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_egyptGraveH = this.optionForKey(this.keys.nestedZombieHealth_egyptGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_roofGraveH = this.optionForKey(this.keys.nestedZombieHealth_roofGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_futureGraveH = this.optionForKey(this.keys.nestedZombieHealth_futureGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_darkAgesGraveH = this.optionForKey(this.keys.nestedZombieHealth_darkAgesGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_fairytaleGraveH = this.optionForKey(this.keys.nestedZombieHealth_fairytaleGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_mausoleumGraveH = this.optionForKey(this.keys.nestedZombieHealth_mausoleumGraveH);
    private final Option<java.lang.Double> nestedZombieHealth_backupH = this.optionForKey(this.keys.nestedZombieHealth_backupH);
    private final Option<java.lang.Double> nestedZombieHealth_bassH = this.optionForKey(this.keys.nestedZombieHealth_bassH);
    private final Option<java.lang.Double> nestedZombieHealth_browncoatH = this.optionForKey(this.keys.nestedZombieHealth_browncoatH);
    private final Option<java.lang.Double> nestedZombieHealth_summerH = this.optionForKey(this.keys.nestedZombieHealth_summerH);
    private final Option<java.lang.Double> nestedZombieHealth_mummyH = this.optionForKey(this.keys.nestedZombieHealth_mummyH);
    private final Option<java.lang.Double> nestedZombieHealth_futureH = this.optionForKey(this.keys.nestedZombieHealth_futureH);
    private final Option<java.lang.Double> nestedZombieHealth_peasantH = this.optionForKey(this.keys.nestedZombieHealth_peasantH);
    private final Option<java.lang.Double> nestedZombieHealth_pokerheartH = this.optionForKey(this.keys.nestedZombieHealth_pokerheartH);
    private final Option<java.lang.Double> nestedZombieHealth_pokerspadeH = this.optionForKey(this.keys.nestedZombieHealth_pokerspadeH);
    private final Option<java.lang.Double> nestedZombieHealth_pokerclubH = this.optionForKey(this.keys.nestedZombieHealth_pokerclubH);
    private final Option<java.lang.Double> nestedZombieHealth_pokerdiamondH = this.optionForKey(this.keys.nestedZombieHealth_pokerdiamondH);
    private final Option<java.lang.Double> nestedZombieHealth_bobsledH = this.optionForKey(this.keys.nestedZombieHealth_bobsledH);
    private final Option<java.lang.Double> nestedZombieHealth_sargeantH = this.optionForKey(this.keys.nestedZombieHealth_sargeantH);
    private final Option<java.lang.Double> nestedZombieHealth_bullyH = this.optionForKey(this.keys.nestedZombieHealth_bullyH);
    private final Option<java.lang.Double> nestedZombieHealth_actionheroH = this.optionForKey(this.keys.nestedZombieHealth_actionheroH);
    private final Option<java.lang.Double> nestedZombieHealth_basketballH = this.optionForKey(this.keys.nestedZombieHealth_basketballH);
    private final Option<java.lang.Double> nestedZombieHealth_dancingH = this.optionForKey(this.keys.nestedZombieHealth_dancingH);
    private final Option<java.lang.Double> nestedZombieHealth_dolphinH = this.optionForKey(this.keys.nestedZombieHealth_dolphinH);
    private final Option<java.lang.Double> nestedZombieHealth_explorerH = this.optionForKey(this.keys.nestedZombieHealth_explorerH);
    private final Option<java.lang.Double> nestedZombieHealth_torchlightH = this.optionForKey(this.keys.nestedZombieHealth_torchlightH);
    private final Option<java.lang.Double> nestedZombieHealth_flagH = this.optionForKey(this.keys.nestedZombieHealth_flagH);
    private final Option<java.lang.Double> nestedZombieHealth_flagSummerH = this.optionForKey(this.keys.nestedZombieHealth_flagSummerH);
    private final Option<java.lang.Double> nestedZombieHealth_flagMummyH = this.optionForKey(this.keys.nestedZombieHealth_flagMummyH);
    private final Option<java.lang.Double> nestedZombieHealth_flagFuturetH = this.optionForKey(this.keys.nestedZombieHealth_flagFuturetH);
    private final Option<java.lang.Double> nestedZombieHealth_flagPeasantH = this.optionForKey(this.keys.nestedZombieHealth_flagPeasantH);
    private final Option<java.lang.Double> nestedZombieHealth_flagPokerH = this.optionForKey(this.keys.nestedZombieHealth_flagPokerH);
    private final Option<java.lang.Double> nestedZombieHealth_flagSargeantH = this.optionForKey(this.keys.nestedZombieHealth_flagSargeantH);
    private final Option<java.lang.Double> nestedZombieHealth_footballH = this.optionForKey(this.keys.nestedZombieHealth_footballH);
    private final Option<java.lang.Double> nestedZombieHealth_berserkerH = this.optionForKey(this.keys.nestedZombieHealth_berserkerH);
    private final Option<java.lang.Double> nestedZombieHealth_gargantuarH = this.optionForKey(this.keys.nestedZombieHealth_gargantuarH);
    private final Option<java.lang.Double> nestedZombieHealth_mummygargantuarH = this.optionForKey(this.keys.nestedZombieHealth_mummygargantuarH);
    private final Option<java.lang.Double> nestedZombieHealth_defensiveendH = this.optionForKey(this.keys.nestedZombieHealth_defensiveendH);
    private final Option<java.lang.Double> nestedZombieHealth_cursedgargolithH = this.optionForKey(this.keys.nestedZombieHealth_cursedgargolithH);
    private final Option<java.lang.Double> nestedZombieHealth_unicorngargantuarH = this.optionForKey(this.keys.nestedZombieHealth_unicorngargantuarH);
    private final Option<java.lang.Double> nestedZombieHealth_hawkerpusherH = this.optionForKey(this.keys.nestedZombieHealth_hawkerpusherH);
    private final Option<java.lang.Double> nestedZombieHealth_hoverGoatH = this.optionForKey(this.keys.nestedZombieHealth_hoverGoatH);
    private final Option<java.lang.Double> nestedZombieHealth_impH = this.optionForKey(this.keys.nestedZombieHealth_impH);
    private final Option<java.lang.Double> nestedZombieHealth_impdragonH = this.optionForKey(this.keys.nestedZombieHealth_impdragonH);
    private final Option<java.lang.Double> nestedZombieHealth_bassimpH = this.optionForKey(this.keys.nestedZombieHealth_bassimpH);
    private final Option<java.lang.Double> nestedZombieHealth_scrapimpH = this.optionForKey(this.keys.nestedZombieHealth_scrapimpH);
    private final Option<java.lang.Double> nestedZombieHealth_superFanH = this.optionForKey(this.keys.nestedZombieHealth_superFanH);
    private final Option<java.lang.Double> nestedZombieHealth_announcerH = this.optionForKey(this.keys.nestedZombieHealth_announcerH);
    private final Option<java.lang.Double> nestedZombieHealth_jetpackH = this.optionForKey(this.keys.nestedZombieHealth_jetpackH);
    private final Option<java.lang.Double> nestedZombieHealth_blastronautH = this.optionForKey(this.keys.nestedZombieHealth_blastronautH);
    private final Option<java.lang.Double> nestedZombieHealth_newspaperH = this.optionForKey(this.keys.nestedZombieHealth_newspaperH);
    private final Option<java.lang.Double> nestedZombieHealth_sundayH = this.optionForKey(this.keys.nestedZombieHealth_sundayH);
    private final Option<java.lang.Double> nestedZombieHealth_octoH = this.optionForKey(this.keys.nestedZombieHealth_octoH);
    private final Option<java.lang.Double> nestedZombieHealth_poleH = this.optionForKey(this.keys.nestedZombieHealth_poleH);
    private final Option<java.lang.Double> nestedZombieHealth_pharaohH = this.optionForKey(this.keys.nestedZombieHealth_pharaohH);
    private final Option<java.lang.Double> nestedZombieHealth_undyingPharaohH = this.optionForKey(this.keys.nestedZombieHealth_undyingPharaohH);
    private final Option<java.lang.Double> nestedZombieHealth_pumpkincarH = this.optionForKey(this.keys.nestedZombieHealth_pumpkincarH);
    private final Option<java.lang.Double> nestedZombieHealth_roboconeH = this.optionForKey(this.keys.nestedZombieHealth_roboconeH);
    private final Option<java.lang.Double> nestedZombieHealth_scrapmechH = this.optionForKey(this.keys.nestedZombieHealth_scrapmechH);
    private final Option<java.lang.Double> nestedZombieHealth_scientistH = this.optionForKey(this.keys.nestedZombieHealth_scientistH);
    private final Option<java.lang.Double> nestedZombieHealth_snorkelH = this.optionForKey(this.keys.nestedZombieHealth_snorkelH);
    private final Option<java.lang.Double> nestedZombieHealth_soldierH = this.optionForKey(this.keys.nestedZombieHealth_soldierH);
    private final Option<java.lang.Double> nestedZombieHealth_tombraiserH = this.optionForKey(this.keys.nestedZombieHealth_tombraiserH);
    private final Option<java.lang.Double> nestedZombieHealth_zombiekingH = this.optionForKey(this.keys.nestedZombieHealth_zombiekingH);
    private final Option<java.lang.Double> nestedZombieHealth_zombiepigH = this.optionForKey(this.keys.nestedZombieHealth_zombiepigH);
    private final Option<java.lang.Double> nestedZombieHealth_zomblobH = this.optionForKey(this.keys.nestedZombieHealth_zomblobH);
    private final Option<java.lang.Double> nestedZombieHealth_zomblobBH = this.optionForKey(this.keys.nestedZombieHealth_zomblobBH);
    private final Option<java.lang.Double> nestedZombieHealth_zomblobSH = this.optionForKey(this.keys.nestedZombieHealth_zomblobSH);
    private final Option<java.lang.Double> nestedZombieHealth_zomboniH = this.optionForKey(this.keys.nestedZombieHealth_zomboniH);
    private final Option<java.lang.Double> nestedZombieHealth_coneH = this.optionForKey(this.keys.nestedZombieHealth_coneH);
    private final Option<java.lang.Double> nestedZombieHealth_pokerpawngearH = this.optionForKey(this.keys.nestedZombieHealth_pokerpawngearH);
    private final Option<java.lang.Double> nestedZombieHealth_pokerknightgearH = this.optionForKey(this.keys.nestedZombieHealth_pokerknightgearH);
    private final Option<java.lang.Double> nestedZombieHealth_pokertowergearH = this.optionForKey(this.keys.nestedZombieHealth_pokertowergearH);
    private final Option<java.lang.Double> nestedZombieHealth_pokerbishopgearH = this.optionForKey(this.keys.nestedZombieHealth_pokerbishopgearH);
    private final Option<java.lang.Double> nestedZombieHealth_kingpiecegearH = this.optionForKey(this.keys.nestedZombieHealth_kingpiecegearH);
    private final Option<java.lang.Double> nestedZombieHealth_bucketH = this.optionForKey(this.keys.nestedZombieHealth_bucketH);
    private final Option<java.lang.Double> nestedZombieHealth_medallionH = this.optionForKey(this.keys.nestedZombieHealth_medallionH);
    private final Option<java.lang.Double> nestedZombieHealth_footballHelmH = this.optionForKey(this.keys.nestedZombieHealth_footballHelmH);
    private final Option<java.lang.Double> nestedZombieHealth_berserkerHelmH = this.optionForKey(this.keys.nestedZombieHealth_berserkerHelmH);
    private final Option<java.lang.Double> nestedZombieHealth_defensiveendHelmH = this.optionForKey(this.keys.nestedZombieHealth_defensiveendHelmH);
    private final Option<java.lang.Double> nestedZombieHealth_blastronautHelmH = this.optionForKey(this.keys.nestedZombieHealth_blastronautHelmH);
    private final Option<java.lang.Double> nestedZombieHealth_knightHelmH = this.optionForKey(this.keys.nestedZombieHealth_knightHelmH);
    private final Option<java.lang.Double> nestedZombieHealth_sargeanthelmetH = this.optionForKey(this.keys.nestedZombieHealth_sargeanthelmetH);
    private final Option<java.lang.Double> nestedZombieHealth_soldierhelmetH = this.optionForKey(this.keys.nestedZombieHealth_soldierhelmetH);
    private final Option<java.lang.Double> nestedZombieHealth_brickH = this.optionForKey(this.keys.nestedZombieHealth_brickH);
    private final Option<java.lang.Double> nestedZombieHealth_coneTowerH = this.optionForKey(this.keys.nestedZombieHealth_coneTowerH);
    private final Option<java.lang.Double> nestedZombieHealth_pyramidH = this.optionForKey(this.keys.nestedZombieHealth_pyramidH);
    private final Option<java.lang.Double> nestedZombieHealth_sarcophagusH = this.optionForKey(this.keys.nestedZombieHealth_sarcophagusH);
    private final Option<java.lang.Double> nestedZombieHealth_bowlH = this.optionForKey(this.keys.nestedZombieHealth_bowlH);
    private final Option<java.lang.Double> nestedZombieHealth_holoHelmetH = this.optionForKey(this.keys.nestedZombieHealth_holoHelmetH);
    private final Option<java.lang.Double> nestedZombieHealth_crystalshoeHelmetH = this.optionForKey(this.keys.nestedZombieHealth_crystalshoeHelmetH);
    private final Option<java.lang.Double> nestedZombieHealth_pumpkinH = this.optionForKey(this.keys.nestedZombieHealth_pumpkinH);
    private final Option<java.lang.Double> nestedZombieHealth_screendoorShieldH = this.optionForKey(this.keys.nestedZombieHealth_screendoorShieldH);
    private final Option<java.lang.Double> nestedZombieHealth_sergeantShieldH = this.optionForKey(this.keys.nestedZombieHealth_sergeantShieldH);
    private final Option<java.lang.Double> nestedZombieHealth_newspaperShieldH = this.optionForKey(this.keys.nestedZombieHealth_newspaperShieldH);
    private final Option<java.lang.Double> nestedZombieHealth_sundayShieldH = this.optionForKey(this.keys.nestedZombieHealth_sundayShieldH);
    private final Option<java.lang.Double> nestedZombieHealth_bookShieldH = this.optionForKey(this.keys.nestedZombieHealth_bookShieldH);
    private final Option<java.lang.Double> nestedZombieHealth_trashcanObstH = this.optionForKey(this.keys.nestedZombieHealth_trashcanObstH);
    private final Option<java.lang.Double> nestedZombieHealth_basketballObstH = this.optionForKey(this.keys.nestedZombieHealth_basketballObstH);
    private final Option<java.lang.Double> nestedZombieHealth_healstationObstH = this.optionForKey(this.keys.nestedZombieHealth_healstationObstH);
    private final Option<java.lang.Double> nestedZombieHealth_gargolithObstH = this.optionForKey(this.keys.nestedZombieHealth_gargolithObstH);
    private final Option<java.lang.Double> nestedZombieHealth_imptabletObstH = this.optionForKey(this.keys.nestedZombieHealth_imptabletObstH);
    private final Option<java.lang.Double> nestedZombieHealth_egyptTombstoneH = this.optionForKey(this.keys.nestedZombieHealth_egyptTombstoneH);
    private final Option<java.lang.Double> nestedZombieHealth_hawkerObstH = this.optionForKey(this.keys.nestedZombieHealth_hawkerObstH);
    private final Option<java.lang.Double> nestedZombieHealth_octoObstH = this.optionForKey(this.keys.nestedZombieHealth_octoObstH);
    private final Option<java.lang.Double> nestedZombieHealth_zomboniVH = this.optionForKey(this.keys.nestedZombieHealth_zomboniVH);
    private final Option<java.lang.Double> nestedZombieHealth_bobsledVH = this.optionForKey(this.keys.nestedZombieHealth_bobsledVH);
    private final Option<java.lang.Double> nestedZombieHealth_speakerVH = this.optionForKey(this.keys.nestedZombieHealth_speakerVH);

    private PvZConfig() {
        super(io.github.GrassyDev.pvzmod.config.PvZConfigModel.class);
    }

    private PvZConfig(Consumer<Jankson.Builder> janksonBuilder) {
        super(io.github.GrassyDev.pvzmod.config.PvZConfigModel.class, janksonBuilder);
    }

    public static PvZConfig createAndLoad() {
        var wrapper = new PvZConfig();
        wrapper.load();
        return wrapper;
    }

    public static PvZConfig createAndLoad(Consumer<Jankson.Builder> janksonBuilder) {
        var wrapper = new PvZConfig(janksonBuilder);
        wrapper.load();
        return wrapper;
    }

    public final NestedSpawns nestedSpawns = new NestedSpawns();
    public class NestedSpawns implements PvZSpawnNest {
        public boolean spawnPlants() {
            return nestedSpawns_spawnPlants.value();
        }

        public void spawnPlants(boolean value) {
            nestedSpawns_spawnPlants.set(value);
        }

        public boolean specialZombieSpawn() {
            return nestedSpawns_specialZombieSpawn.value();
        }

        public void specialZombieSpawn(boolean value) {
            nestedSpawns_specialZombieSpawn.set(value);
        }

        public boolean hoeAlternative() {
            return nestedSpawns_hoeAlternative.value();
        }

        public void hoeAlternative(boolean value) {
            nestedSpawns_hoeAlternative.set(value);
        }

        public int hoeBreak() {
            return nestedSpawns_hoeBreak.value();
        }

        public void hoeBreak(int value) {
            nestedSpawns_hoeBreak.set(value);
        }

        public final NestedGraveSpawns nestedGraveSpawns = new NestedGraveSpawns();
        public class NestedGraveSpawns implements PvZSpawnNestGrave {
            public int basicGv2() {
                return nestedSpawns_nestedGraveSpawns_basicGv2.value();
            }

            public void basicGv2(int value) {
                nestedSpawns_nestedGraveSpawns_basicGv2.set(value);
            }

            public int basicGmin() {
                return nestedSpawns_nestedGraveSpawns_basicGmin.value();
            }

            public void basicGmin(int value) {
                nestedSpawns_nestedGraveSpawns_basicGmin.set(value);
            }

            public int basicGmax() {
                return nestedSpawns_nestedGraveSpawns_basicGmax.value();
            }

            public void basicGmax(int value) {
                nestedSpawns_nestedGraveSpawns_basicGmax.set(value);
            }

            public int nightGv2() {
                return nestedSpawns_nestedGraveSpawns_nightGv2.value();
            }

            public void nightGv2(int value) {
                nestedSpawns_nestedGraveSpawns_nightGv2.set(value);
            }

            public int nightGmin() {
                return nestedSpawns_nestedGraveSpawns_nightGmin.value();
            }

            public void nightGmin(int value) {
                nestedSpawns_nestedGraveSpawns_nightGmin.set(value);
            }

            public int nightGmax() {
                return nestedSpawns_nestedGraveSpawns_nightGmax.value();
            }

            public void nightGmax(int value) {
                nestedSpawns_nestedGraveSpawns_nightGmax.set(value);
            }

            public int poolGv2() {
                return nestedSpawns_nestedGraveSpawns_poolGv2.value();
            }

            public void poolGv2(int value) {
                nestedSpawns_nestedGraveSpawns_poolGv2.set(value);
            }

            public int poolGmin() {
                return nestedSpawns_nestedGraveSpawns_poolGmin.value();
            }

            public void poolGmin(int value) {
                nestedSpawns_nestedGraveSpawns_poolGmin.set(value);
            }

            public int poolGmax() {
                return nestedSpawns_nestedGraveSpawns_poolGmax.value();
            }

            public void poolGmax(int value) {
                nestedSpawns_nestedGraveSpawns_poolGmax.set(value);
            }

            public int roofGv2() {
                return nestedSpawns_nestedGraveSpawns_roofGv2.value();
            }

            public void roofGv2(int value) {
                nestedSpawns_nestedGraveSpawns_roofGv2.set(value);
            }

            public int roofGmin() {
                return nestedSpawns_nestedGraveSpawns_roofGmin.value();
            }

            public void roofGmin(int value) {
                nestedSpawns_nestedGraveSpawns_roofGmin.set(value);
            }

            public int roofGmax() {
                return nestedSpawns_nestedGraveSpawns_roofGmax.value();
            }

            public void roofGmax(int value) {
                nestedSpawns_nestedGraveSpawns_roofGmax.set(value);
            }

            public int egyptG() {
                return nestedSpawns_nestedGraveSpawns_egyptG.value();
            }

            public void egyptG(int value) {
                nestedSpawns_nestedGraveSpawns_egyptG.set(value);
            }

            public int egyptGmin() {
                return nestedSpawns_nestedGraveSpawns_egyptGmin.value();
            }

            public void egyptGmin(int value) {
                nestedSpawns_nestedGraveSpawns_egyptGmin.set(value);
            }

            public int egyptGmax() {
                return nestedSpawns_nestedGraveSpawns_egyptGmax.value();
            }

            public void egyptGmax(int value) {
                nestedSpawns_nestedGraveSpawns_egyptGmax.set(value);
            }

            public int futureGv2() {
                return nestedSpawns_nestedGraveSpawns_futureGv2.value();
            }

            public void futureGv2(int value) {
                nestedSpawns_nestedGraveSpawns_futureGv2.set(value);
            }

            public int futureGmin() {
                return nestedSpawns_nestedGraveSpawns_futureGmin.value();
            }

            public void futureGmin(int value) {
                nestedSpawns_nestedGraveSpawns_futureGmin.set(value);
            }

            public int futureGmax() {
                return nestedSpawns_nestedGraveSpawns_futureGmax.value();
            }

            public void futureGmax(int value) {
                nestedSpawns_nestedGraveSpawns_futureGmax.set(value);
            }

            public int darkagesGv2() {
                return nestedSpawns_nestedGraveSpawns_darkagesGv2.value();
            }

            public void darkagesGv2(int value) {
                nestedSpawns_nestedGraveSpawns_darkagesGv2.set(value);
            }

            public int darkagesGmin() {
                return nestedSpawns_nestedGraveSpawns_darkagesGmin.value();
            }

            public void darkagesGmin(int value) {
                nestedSpawns_nestedGraveSpawns_darkagesGmin.set(value);
            }

            public int darkagesGmax() {
                return nestedSpawns_nestedGraveSpawns_darkagesGmax.value();
            }

            public void darkagesGmax(int value) {
                nestedSpawns_nestedGraveSpawns_darkagesGmax.set(value);
            }

            public int mausoleumG() {
                return nestedSpawns_nestedGraveSpawns_mausoleumG.value();
            }

            public void mausoleumG(int value) {
                nestedSpawns_nestedGraveSpawns_mausoleumG.set(value);
            }

            public int mausoleumGmin() {
                return nestedSpawns_nestedGraveSpawns_mausoleumGmin.value();
            }

            public void mausoleumGmin(int value) {
                nestedSpawns_nestedGraveSpawns_mausoleumGmin.set(value);
            }

            public int mausoleumGmax() {
                return nestedSpawns_nestedGraveSpawns_mausoleumGmax.value();
            }

            public void mausoleumGmax(int value) {
                nestedSpawns_nestedGraveSpawns_mausoleumGmax.set(value);
            }

        }
        public final NestedPlantSpawns nestedPlantSpawns = new NestedPlantSpawns();
        public class NestedPlantSpawns implements PvZSpawnNestPlant {
            public int peashooterSP() {
                return nestedSpawns_nestedPlantSpawns_peashooterSP.value();
            }

            public void peashooterSP(int value) {
                nestedSpawns_nestedPlantSpawns_peashooterSP.set(value);
            }

            public int peashooterSPmin() {
                return nestedSpawns_nestedPlantSpawns_peashooterSPmin.value();
            }

            public void peashooterSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_peashooterSPmin.set(value);
            }

            public int peashooterSPmax() {
                return nestedSpawns_nestedPlantSpawns_peashooterSPmax.value();
            }

            public void peashooterSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_peashooterSPmax.set(value);
            }

            public int bellflowerSP() {
                return nestedSpawns_nestedPlantSpawns_bellflowerSP.value();
            }

            public void bellflowerSP(int value) {
                nestedSpawns_nestedPlantSpawns_bellflowerSP.set(value);
            }

            public int bellflowerSPmin() {
                return nestedSpawns_nestedPlantSpawns_bellflowerSPmin.value();
            }

            public void bellflowerSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_bellflowerSPmin.set(value);
            }

            public int bellflowerSPmax() {
                return nestedSpawns_nestedPlantSpawns_bellflowerSPmax.value();
            }

            public void bellflowerSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_bellflowerSPmax.set(value);
            }

            public int puffshroomSP() {
                return nestedSpawns_nestedPlantSpawns_puffshroomSP.value();
            }

            public void puffshroomSP(int value) {
                nestedSpawns_nestedPlantSpawns_puffshroomSP.set(value);
            }

            public int puffshroomSPmin() {
                return nestedSpawns_nestedPlantSpawns_puffshroomSPmin.value();
            }

            public void puffshroomSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_puffshroomSPmin.set(value);
            }

            public int puffshroomSPmax() {
                return nestedSpawns_nestedPlantSpawns_puffshroomSPmax.value();
            }

            public void puffshroomSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_puffshroomSPmax.set(value);
            }

            public int shadowshroomSP() {
                return nestedSpawns_nestedPlantSpawns_shadowshroomSP.value();
            }

            public void shadowshroomSP(int value) {
                nestedSpawns_nestedPlantSpawns_shadowshroomSP.set(value);
            }

            public int shadowshroomSPmin() {
                return nestedSpawns_nestedPlantSpawns_shadowshroomSPmin.value();
            }

            public void shadowshroomSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_shadowshroomSPmin.set(value);
            }

            public int shadowshroomSPmax() {
                return nestedSpawns_nestedPlantSpawns_shadowshroomSPmax.value();
            }

            public void shadowshroomSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_shadowshroomSPmax.set(value);
            }

            public int weeniebeanieSP() {
                return nestedSpawns_nestedPlantSpawns_weeniebeanieSP.value();
            }

            public void weeniebeanieSP(int value) {
                nestedSpawns_nestedPlantSpawns_weeniebeanieSP.set(value);
            }

            public int weeniebeanieSPmin() {
                return nestedSpawns_nestedPlantSpawns_weeniebeanieSPmin.value();
            }

            public void weeniebeanieSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_weeniebeanieSPmin.set(value);
            }

            public int weeniebeanieSPmax() {
                return nestedSpawns_nestedPlantSpawns_weeniebeanieSPmax.value();
            }

            public void weeniebeanieSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_weeniebeanieSPmax.set(value);
            }

            public int sunflowerseedSPv2() {
                return nestedSpawns_nestedPlantSpawns_sunflowerseedSPv2.value();
            }

            public void sunflowerseedSPv2(int value) {
                nestedSpawns_nestedPlantSpawns_sunflowerseedSPv2.set(value);
            }

            public int sunflowerseedSPmin() {
                return nestedSpawns_nestedPlantSpawns_sunflowerseedSPmin.value();
            }

            public void sunflowerseedSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_sunflowerseedSPmin.set(value);
            }

            public int sunflowerseedSPmax() {
                return nestedSpawns_nestedPlantSpawns_sunflowerseedSPmax.value();
            }

            public void sunflowerseedSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_sunflowerseedSPmax.set(value);
            }

            public int lilypadSP() {
                return nestedSpawns_nestedPlantSpawns_lilypadSP.value();
            }

            public void lilypadSP(int value) {
                nestedSpawns_nestedPlantSpawns_lilypadSP.set(value);
            }

            public int lilypadSPmin() {
                return nestedSpawns_nestedPlantSpawns_lilypadSPmin.value();
            }

            public void lilypadSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_lilypadSPmin.set(value);
            }

            public int lilypadSPmax() {
                return nestedSpawns_nestedPlantSpawns_lilypadSPmax.value();
            }

            public void lilypadSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_lilypadSPmax.set(value);
            }

            public int bombseedlingSP() {
                return nestedSpawns_nestedPlantSpawns_bombseedlingSP.value();
            }

            public void bombseedlingSP(int value) {
                nestedSpawns_nestedPlantSpawns_bombseedlingSP.set(value);
            }

            public int bombseedlingSPmin() {
                return nestedSpawns_nestedPlantSpawns_bombseedlingSPmin.value();
            }

            public void bombseedlingSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_bombseedlingSPmin.set(value);
            }

            public int bombseedlingSPmax() {
                return nestedSpawns_nestedPlantSpawns_bombseedlingSPmax.value();
            }

            public void bombseedlingSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_bombseedlingSPmax.set(value);
            }

            public int smallnutSP() {
                return nestedSpawns_nestedPlantSpawns_smallnutSP.value();
            }

            public void smallnutSP(int value) {
                nestedSpawns_nestedPlantSpawns_smallnutSP.set(value);
            }

            public int smallnutSPmin() {
                return nestedSpawns_nestedPlantSpawns_smallnutSPmin.value();
            }

            public void smallnutSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_smallnutSPmin.set(value);
            }

            public int smallnutSPmax() {
                return nestedSpawns_nestedPlantSpawns_smallnutSPmax.value();
            }

            public void smallnutSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_smallnutSPmax.set(value);
            }

            public int loquatSP() {
                return nestedSpawns_nestedPlantSpawns_loquatSP.value();
            }

            public void loquatSP(int value) {
                nestedSpawns_nestedPlantSpawns_loquatSP.set(value);
            }

            public int loquatSPmin() {
                return nestedSpawns_nestedPlantSpawns_loquatSPmin.value();
            }

            public void loquatSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_loquatSPmin.set(value);
            }

            public int loquatSPmax() {
                return nestedSpawns_nestedPlantSpawns_loquatSPmax.value();
            }

            public void loquatSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_loquatSPmax.set(value);
            }

            public int icebergSP() {
                return nestedSpawns_nestedPlantSpawns_icebergSP.value();
            }

            public void icebergSP(int value) {
                nestedSpawns_nestedPlantSpawns_icebergSP.set(value);
            }

            public int icebergSPmin() {
                return nestedSpawns_nestedPlantSpawns_icebergSPmin.value();
            }

            public void icebergSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_icebergSPmin.set(value);
            }

            public int icebergSPmax() {
                return nestedSpawns_nestedPlantSpawns_icebergSPmax.value();
            }

            public void icebergSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_icebergSPmax.set(value);
            }

            public int zapricotSP() {
                return nestedSpawns_nestedPlantSpawns_zapricotSP.value();
            }

            public void zapricotSP(int value) {
                nestedSpawns_nestedPlantSpawns_zapricotSP.set(value);
            }

            public int zapricotSPmin() {
                return nestedSpawns_nestedPlantSpawns_zapricotSPmin.value();
            }

            public void zapricotSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_zapricotSPmin.set(value);
            }

            public int zapricotSPmax() {
                return nestedSpawns_nestedPlantSpawns_zapricotSPmax.value();
            }

            public void zapricotSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_zapricotSPmax.set(value);
            }

            public int buttonshroomSP() {
                return nestedSpawns_nestedPlantSpawns_buttonshroomSP.value();
            }

            public void buttonshroomSP(int value) {
                nestedSpawns_nestedPlantSpawns_buttonshroomSP.set(value);
            }

            public int buttonshroomSPmin() {
                return nestedSpawns_nestedPlantSpawns_buttonshroomSPmin.value();
            }

            public void buttonshroomSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_buttonshroomSPmin.set(value);
            }

            public int buttonshroomSPmax() {
                return nestedSpawns_nestedPlantSpawns_buttonshroomSPmax.value();
            }

            public void buttonshroomSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_buttonshroomSPmax.set(value);
            }

            public int solarwindSP() {
                return nestedSpawns_nestedPlantSpawns_solarwindSP.value();
            }

            public void solarwindSP(int value) {
                nestedSpawns_nestedPlantSpawns_solarwindSP.set(value);
            }

            public int solarwindSPmin() {
                return nestedSpawns_nestedPlantSpawns_solarwindSPmin.value();
            }

            public void solarwindSPmin(int value) {
                nestedSpawns_nestedPlantSpawns_solarwindSPmin.set(value);
            }

            public int solarwindSPmax() {
                return nestedSpawns_nestedPlantSpawns_solarwindSPmax.value();
            }

            public void solarwindSPmax(int value) {
                nestedSpawns_nestedPlantSpawns_solarwindSPmax.set(value);
            }

        }
    }
    public final NestedSeeds nestedSeeds = new NestedSeeds();
    public class NestedSeeds implements PvZSeedNest {
        public boolean infiniteSeeds() {
            return nestedSeeds_infiniteSeeds.value();
        }

        public void infiniteSeeds(boolean value) {
            nestedSeeds_infiniteSeeds.set(value);
        }

        public boolean instantRecharge() {
            return nestedSeeds_instantRecharge.value();
        }

        public void instantRecharge(boolean value) {
            nestedSeeds_instantRecharge.set(value);
        }

        public final MoreSeeds moreSeeds = new MoreSeeds();
        public class MoreSeeds implements PvZMoreSeeds {
            public float acidshrooomS() {
                return nestedSeeds_moreSeeds_acidshrooomS.value();
            }

            public void acidshrooomS(float value) {
                nestedSeeds_moreSeeds_acidshrooomS.set(value);
            }

            public float admiralnavybeanS() {
                return nestedSeeds_moreSeeds_admiralnavybeanS.value();
            }

            public void admiralnavybeanS(float value) {
                nestedSeeds_moreSeeds_admiralnavybeanS.set(value);
            }

            public float bananasaurusS() {
                return nestedSeeds_moreSeeds_bananasaurusS.value();
            }

            public void bananasaurusS(float value) {
                nestedSeeds_moreSeeds_bananasaurusS.set(value);
            }

            public float beautyshroomS() {
                return nestedSeeds_moreSeeds_beautyshroomS.value();
            }

            public void beautyshroomS(float value) {
                nestedSeeds_moreSeeds_beautyshroomS.set(value);
            }

            public float beeshooterS() {
                return nestedSeeds_moreSeeds_beeshooterS.value();
            }

            public void beeshooterS(float value) {
                nestedSeeds_moreSeeds_beeshooterS.set(value);
            }

            public float beetS() {
                return nestedSeeds_moreSeeds_beetS.value();
            }

            public void beetS(float value) {
                nestedSeeds_moreSeeds_beetS.set(value);
            }

            public float bellflowerS() {
                return nestedSeeds_moreSeeds_bellflowerS.value();
            }

            public void bellflowerS(float value) {
                nestedSeeds_moreSeeds_bellflowerS.set(value);
            }

            public float bloomerangS() {
                return nestedSeeds_moreSeeds_bloomerangS.value();
            }

            public void bloomerangS(float value) {
                nestedSeeds_moreSeeds_bloomerangS.set(value);
            }

            public float bombseedlingS() {
                return nestedSeeds_moreSeeds_bombseedlingS.value();
            }

            public void bombseedlingS(float value) {
                nestedSeeds_moreSeeds_bombseedlingS.set(value);
            }

            public float breezesroomS() {
                return nestedSeeds_moreSeeds_breezesroomS.value();
            }

            public void breezesroomS(float value) {
                nestedSeeds_moreSeeds_breezesroomS.set(value);
            }

            public float burstshroomS() {
                return nestedSeeds_moreSeeds_burstshroomS.value();
            }

            public void burstshroomS(float value) {
                nestedSeeds_moreSeeds_burstshroomS.set(value);
            }

            public float buttonshroomS() {
                return nestedSeeds_moreSeeds_buttonshroomS.value();
            }

            public void buttonshroomS(float value) {
                nestedSeeds_moreSeeds_buttonshroomS.set(value);
            }

            public float cabbagepultS() {
                return nestedSeeds_moreSeeds_cabbagepultS.value();
            }

            public void cabbagepultS(float value) {
                nestedSeeds_moreSeeds_cabbagepultS.set(value);
            }

            public float kernalpultS() {
                return nestedSeeds_moreSeeds_kernalpultS.value();
            }

            public void kernalpultS(float value) {
                nestedSeeds_moreSeeds_kernalpultS.set(value);
            }

            public float melonpultS() {
                return nestedSeeds_moreSeeds_melonpultS.value();
            }

            public void melonpultS(float value) {
                nestedSeeds_moreSeeds_melonpultS.set(value);
            }

            public float melonsliceS() {
                return nestedSeeds_moreSeeds_melonsliceS.value();
            }

            public void melonsliceS(float value) {
                nestedSeeds_moreSeeds_melonsliceS.set(value);
            }

            public float WintermelonS() {
                return nestedSeeds_moreSeeds_WintermelonS.value();
            }

            public void WintermelonS(float value) {
                nestedSeeds_moreSeeds_WintermelonS.set(value);
            }

            public float cattailS() {
                return nestedSeeds_moreSeeds_cattailS.value();
            }

            public void cattailS(float value) {
                nestedSeeds_moreSeeds_cattailS.set(value);
            }

            public float coffeeBeanS() {
                return nestedSeeds_moreSeeds_coffeeBeanS.value();
            }

            public void coffeeBeanS(float value) {
                nestedSeeds_moreSeeds_coffeeBeanS.set(value);
            }

            public float charmshroomS() {
                return nestedSeeds_moreSeeds_charmshroomS.value();
            }

            public void charmshroomS(float value) {
                nestedSeeds_moreSeeds_charmshroomS.set(value);
            }

            public float cherrybombS() {
                return nestedSeeds_moreSeeds_cherrybombS.value();
            }

            public void cherrybombS(float value) {
                nestedSeeds_moreSeeds_cherrybombS.set(value);
            }

            public float chesterS() {
                return nestedSeeds_moreSeeds_chesterS.value();
            }

            public void chesterS(float value) {
                nestedSeeds_moreSeeds_chesterS.set(value);
            }

            public float chillypepperS() {
                return nestedSeeds_moreSeeds_chillypepperS.value();
            }

            public void chillypepperS(float value) {
                nestedSeeds_moreSeeds_chillypepperS.set(value);
            }

            public float chomperS() {
                return nestedSeeds_moreSeeds_chomperS.value();
            }

            public void chomperS(float value) {
                nestedSeeds_moreSeeds_chomperS.set(value);
            }

            public float coconutS() {
                return nestedSeeds_moreSeeds_coconutS.value();
            }

            public void coconutS(float value) {
                nestedSeeds_moreSeeds_coconutS.set(value);
            }

            public float dandelionweedS() {
                return nestedSeeds_moreSeeds_dandelionweedS.value();
            }

            public void dandelionweedS(float value) {
                nestedSeeds_moreSeeds_dandelionweedS.set(value);
            }

            public float dogwoodS() {
                return nestedSeeds_moreSeeds_dogwoodS.value();
            }

            public void dogwoodS(float value) {
                nestedSeeds_moreSeeds_dogwoodS.set(value);
            }

            public float doomroseS() {
                return nestedSeeds_moreSeeds_doomroseS.value();
            }

            public void doomroseS(float value) {
                nestedSeeds_moreSeeds_doomroseS.set(value);
            }

            public float doomshroomS() {
                return nestedSeeds_moreSeeds_doomshroomS.value();
            }

            public void doomshroomS(float value) {
                nestedSeeds_moreSeeds_doomshroomS.set(value);
            }

            public float dripphylleiaS() {
                return nestedSeeds_moreSeeds_dripphylleiaS.value();
            }

            public void dripphylleiaS(float value) {
                nestedSeeds_moreSeeds_dripphylleiaS.set(value);
            }

            public float dropeaS() {
                return nestedSeeds_moreSeeds_dropeaS.value();
            }

            public void dropeaS(float value) {
                nestedSeeds_moreSeeds_dropeaS.set(value);
            }

            public float electropeaS() {
                return nestedSeeds_moreSeeds_electropeaS.value();
            }

            public void electropeaS(float value) {
                nestedSeeds_moreSeeds_electropeaS.set(value);
            }

            public float empeachS() {
                return nestedSeeds_moreSeeds_empeachS.value();
            }

            public void empeachS(float value) {
                nestedSeeds_moreSeeds_empeachS.set(value);
            }

            public float endurianS() {
                return nestedSeeds_moreSeeds_endurianS.value();
            }

            public void endurianS(float value) {
                nestedSeeds_moreSeeds_endurianS.set(value);
            }

            public float firepeaS() {
                return nestedSeeds_moreSeeds_firepeaS.value();
            }

            public void firepeaS(float value) {
                nestedSeeds_moreSeeds_firepeaS.set(value);
            }

            public float frisbloomS() {
                return nestedSeeds_moreSeeds_frisbloomS.value();
            }

            public void frisbloomS(float value) {
                nestedSeeds_moreSeeds_frisbloomS.set(value);
            }

            public float fumeshroomS() {
                return nestedSeeds_moreSeeds_fumeshroomS.value();
            }

            public void fumeshroomS(float value) {
                nestedSeeds_moreSeeds_fumeshroomS.set(value);
            }

            public float gambleshroomS() {
                return nestedSeeds_moreSeeds_gambleshroomS.value();
            }

            public void gambleshroomS(float value) {
                nestedSeeds_moreSeeds_gambleshroomS.set(value);
            }

            public float gatlingpeaS() {
                return nestedSeeds_moreSeeds_gatlingpeaS.value();
            }

            public void gatlingpeaS(float value) {
                nestedSeeds_moreSeeds_gatlingpeaS.set(value);
            }

            public float ghostpepperS() {
                return nestedSeeds_moreSeeds_ghostpepperS.value();
            }

            public void ghostpepperS(float value) {
                nestedSeeds_moreSeeds_ghostpepperS.set(value);
            }

            public float gloomvineS() {
                return nestedSeeds_moreSeeds_gloomvineS.value();
            }

            public void gloomvineS(float value) {
                nestedSeeds_moreSeeds_gloomvineS.set(value);
            }

            public float gloomshroomS() {
                return nestedSeeds_moreSeeds_gloomshroomS.value();
            }

            public void gloomshroomS(float value) {
                nestedSeeds_moreSeeds_gloomshroomS.set(value);
            }

            public float goldleafS() {
                return nestedSeeds_moreSeeds_goldleafS.value();
            }

            public void goldleafS(float value) {
                nestedSeeds_moreSeeds_goldleafS.set(value);
            }

            public float gravebusterS() {
                return nestedSeeds_moreSeeds_gravebusterS.value();
            }

            public void gravebusterS(float value) {
                nestedSeeds_moreSeeds_gravebusterS.set(value);
            }

            public float hammerflowerS() {
                return nestedSeeds_moreSeeds_hammerflowerS.value();
            }

            public void hammerflowerS(float value) {
                nestedSeeds_moreSeeds_hammerflowerS.set(value);
            }

            public float heavenlypeachS() {
                return nestedSeeds_moreSeeds_heavenlypeachS.value();
            }

            public void heavenlypeachS(float value) {
                nestedSeeds_moreSeeds_heavenlypeachS.set(value);
            }

            public float hypnoshroomS() {
                return nestedSeeds_moreSeeds_hypnoshroomS.value();
            }

            public void hypnoshroomS(float value) {
                nestedSeeds_moreSeeds_hypnoshroomS.set(value);
            }

            public float iceberglettuceS() {
                return nestedSeeds_moreSeeds_iceberglettuceS.value();
            }

            public void iceberglettuceS(float value) {
                nestedSeeds_moreSeeds_iceberglettuceS.set(value);
            }

            public float icebergpultS() {
                return nestedSeeds_moreSeeds_icebergpultS.value();
            }

            public void icebergpultS(float value) {
                nestedSeeds_moreSeeds_icebergpultS.set(value);
            }

            public float iceshroomS() {
                return nestedSeeds_moreSeeds_iceshroomS.value();
            }

            public void iceshroomS(float value) {
                nestedSeeds_moreSeeds_iceshroomS.set(value);
            }

            public float impatyensS() {
                return nestedSeeds_moreSeeds_impatyensS.value();
            }

            public void impatyensS(float value) {
                nestedSeeds_moreSeeds_impatyensS.set(value);
            }

            public float jalapenoS() {
                return nestedSeeds_moreSeeds_jalapenoS.value();
            }

            public void jalapenoS(float value) {
                nestedSeeds_moreSeeds_jalapenoS.set(value);
            }

            public float jumpingbeanS() {
                return nestedSeeds_moreSeeds_jumpingbeanS.value();
            }

            public void jumpingbeanS(float value) {
                nestedSeeds_moreSeeds_jumpingbeanS.set(value);
            }

            public float knightpeaS() {
                return nestedSeeds_moreSeeds_knightpeaS.value();
            }

            public void knightpeaS(float value) {
                nestedSeeds_moreSeeds_knightpeaS.set(value);
            }

            public float lightningreedS() {
                return nestedSeeds_moreSeeds_lightningreedS.value();
            }

            public void lightningreedS(float value) {
                nestedSeeds_moreSeeds_lightningreedS.set(value);
            }

            public float lilypadS() {
                return nestedSeeds_moreSeeds_lilypadS.value();
            }

            public void lilypadS(float value) {
                nestedSeeds_moreSeeds_lilypadS.set(value);
            }

            public float loquatS() {
                return nestedSeeds_moreSeeds_loquatS.value();
            }

            public void loquatS(float value) {
                nestedSeeds_moreSeeds_loquatS.set(value);
            }

            public float locococoS() {
                return nestedSeeds_moreSeeds_locococoS.value();
            }

            public void locococoS(float value) {
                nestedSeeds_moreSeeds_locococoS.set(value);
            }

            public float magicshroomS() {
                return nestedSeeds_moreSeeds_magicshroomS.value();
            }

            public void magicshroomS(float value) {
                nestedSeeds_moreSeeds_magicshroomS.set(value);
            }

            public float magnetshroomS() {
                return nestedSeeds_moreSeeds_magnetshroomS.value();
            }

            public void magnetshroomS(float value) {
                nestedSeeds_moreSeeds_magnetshroomS.set(value);
            }

            public float magnetoshroomS() {
                return nestedSeeds_moreSeeds_magnetoshroomS.value();
            }

            public void magnetoshroomS(float value) {
                nestedSeeds_moreSeeds_magnetoshroomS.set(value);
            }

            public float meteorhammerS() {
                return nestedSeeds_moreSeeds_meteorhammerS.value();
            }

            public void meteorhammerS(float value) {
                nestedSeeds_moreSeeds_meteorhammerS.set(value);
            }

            public float missileToeS() {
                return nestedSeeds_moreSeeds_missileToeS.value();
            }

            public void missileToeS(float value) {
                nestedSeeds_moreSeeds_missileToeS.set(value);
            }

            public float narcissusS() {
                return nestedSeeds_moreSeeds_narcissusS.value();
            }

            public void narcissusS(float value) {
                nestedSeeds_moreSeeds_narcissusS.set(value);
            }

            public float navybeanS() {
                return nestedSeeds_moreSeeds_navybeanS.value();
            }

            public void navybeanS(float value) {
                nestedSeeds_moreSeeds_navybeanS.set(value);
            }

            public float nightcapS() {
                return nestedSeeds_moreSeeds_nightcapS.value();
            }

            public void nightcapS(float value) {
                nestedSeeds_moreSeeds_nightcapS.set(value);
            }

            public float oilyOliveS() {
                return nestedSeeds_moreSeeds_oilyOliveS.value();
            }

            public void oilyOliveS(float value) {
                nestedSeeds_moreSeeds_oilyOliveS.set(value);
            }

            public float olivepitS() {
                return nestedSeeds_moreSeeds_olivepitS.value();
            }

            public void olivepitS(float value) {
                nestedSeeds_moreSeeds_olivepitS.set(value);
            }

            public float oxygaeS() {
                return nestedSeeds_moreSeeds_oxygaeS.value();
            }

            public void oxygaeS(float value) {
                nestedSeeds_moreSeeds_oxygaeS.set(value);
            }

            public float peanutS() {
                return nestedSeeds_moreSeeds_peanutS.value();
            }

            public void peanutS(float value) {
                nestedSeeds_moreSeeds_peanutS.set(value);
            }

            public float peapodS() {
                return nestedSeeds_moreSeeds_peapodS.value();
            }

            public void peapodS(float value) {
                nestedSeeds_moreSeeds_peapodS.set(value);
            }

            public float peashooterS() {
                return nestedSeeds_moreSeeds_peashooterS.value();
            }

            public void peashooterS(float value) {
                nestedSeeds_moreSeeds_peashooterS.set(value);
            }

            public float pepperpultS() {
                return nestedSeeds_moreSeeds_pepperpultS.value();
            }

            public void pepperpultS(float value) {
                nestedSeeds_moreSeeds_pepperpultS.set(value);
            }

            public float perfoomshroomS() {
                return nestedSeeds_moreSeeds_perfoomshroomS.value();
            }

            public void perfoomshroomS(float value) {
                nestedSeeds_moreSeeds_perfoomshroomS.set(value);
            }

            public float potatomineS() {
                return nestedSeeds_moreSeeds_potatomineS.value();
            }

            public void potatomineS(float value) {
                nestedSeeds_moreSeeds_potatomineS.set(value);
            }

            public float puffshroomS() {
                return nestedSeeds_moreSeeds_puffshroomS.value();
            }

            public void puffshroomS(float value) {
                nestedSeeds_moreSeeds_puffshroomS.set(value);
            }

            public float pumpkinwitchS() {
                return nestedSeeds_moreSeeds_pumpkinwitchS.value();
            }

            public void pumpkinwitchS(float value) {
                nestedSeeds_moreSeeds_pumpkinwitchS.set(value);
            }

            public float repeaterS() {
                return nestedSeeds_moreSeeds_repeaterS.value();
            }

            public void repeaterS(float value) {
                nestedSeeds_moreSeeds_repeaterS.set(value);
            }

            public float retrogatlingS() {
                return nestedSeeds_moreSeeds_retrogatlingS.value();
            }

            public void retrogatlingS(float value) {
                nestedSeeds_moreSeeds_retrogatlingS.set(value);
            }

            public float saucerS() {
                return nestedSeeds_moreSeeds_saucerS.value();
            }

            public void saucerS(float value) {
                nestedSeeds_moreSeeds_saucerS.set(value);
            }

            public float scaredyshroomS() {
                return nestedSeeds_moreSeeds_scaredyshroomS.value();
            }

            public void scaredyshroomS(float value) {
                nestedSeeds_moreSeeds_scaredyshroomS.set(value);
            }

            public float seapeaS() {
                return nestedSeeds_moreSeeds_seapeaS.value();
            }

            public void seapeaS(float value) {
                nestedSeeds_moreSeeds_seapeaS.set(value);
            }

            public float seashroomS() {
                return nestedSeeds_moreSeeds_seashroomS.value();
            }

            public void seashroomS(float value) {
                nestedSeeds_moreSeeds_seashroomS.set(value);
            }

            public float shadowShroomS() {
                return nestedSeeds_moreSeeds_shadowShroomS.value();
            }

            public void shadowShroomS(float value) {
                nestedSeeds_moreSeeds_shadowShroomS.set(value);
            }

            public float shamrockS() {
                return nestedSeeds_moreSeeds_shamrockS.value();
            }

            public void shamrockS(float value) {
                nestedSeeds_moreSeeds_shamrockS.set(value);
            }

            public float smackadamiaSv2() {
                return nestedSeeds_moreSeeds_smackadamiaSv2.value();
            }

            public void smackadamiaSv2(float value) {
                nestedSeeds_moreSeeds_smackadamiaSv2.set(value);
            }

            public float smallnutS() {
                return nestedSeeds_moreSeeds_smallnutS.value();
            }

            public void smallnutS(float value) {
                nestedSeeds_moreSeeds_smallnutS.set(value);
            }

            public float smooshroomS() {
                return nestedSeeds_moreSeeds_smooshroomS.value();
            }

            public void smooshroomS(float value) {
                nestedSeeds_moreSeeds_smooshroomS.set(value);
            }

            public float snowpeaS() {
                return nestedSeeds_moreSeeds_snowpeaS.value();
            }

            public void snowpeaS(float value) {
                nestedSeeds_moreSeeds_snowpeaS.set(value);
            }

            public float snowqueenpeaS() {
                return nestedSeeds_moreSeeds_snowqueenpeaS.value();
            }

            public void snowqueenpeaS(float value) {
                nestedSeeds_moreSeeds_snowqueenpeaS.set(value);
            }

            public float spikerockS() {
                return nestedSeeds_moreSeeds_spikerockS.value();
            }

            public void spikerockS(float value) {
                nestedSeeds_moreSeeds_spikerockS.set(value);
            }

            public float spikeweedS() {
                return nestedSeeds_moreSeeds_spikeweedS.value();
            }

            public void spikeweedS(float value) {
                nestedSeeds_moreSeeds_spikeweedS.set(value);
            }

            public float springbeanS() {
                return nestedSeeds_moreSeeds_springbeanS.value();
            }

            public void springbeanS(float value) {
                nestedSeeds_moreSeeds_springbeanS.set(value);
            }

            public float springprincessS() {
                return nestedSeeds_moreSeeds_springprincessS.value();
            }

            public void springprincessS(float value) {
                nestedSeeds_moreSeeds_springprincessS.set(value);
            }

            public float squashS() {
                return nestedSeeds_moreSeeds_squashS.value();
            }

            public void squashS(float value) {
                nestedSeeds_moreSeeds_squashS.set(value);
            }

            public float sunflowerS() {
                return nestedSeeds_moreSeeds_sunflowerS.value();
            }

            public void sunflowerS(float value) {
                nestedSeeds_moreSeeds_sunflowerS.set(value);
            }

            public float sunflowerseedS() {
                return nestedSeeds_moreSeeds_sunflowerseedS.value();
            }

            public void sunflowerseedS(float value) {
                nestedSeeds_moreSeeds_sunflowerseedS.set(value);
            }

            public float sunshroomS() {
                return nestedSeeds_moreSeeds_sunshroomS.value();
            }

            public void sunshroomS(float value) {
                nestedSeeds_moreSeeds_sunshroomS.set(value);
            }

            public float superchomperS() {
                return nestedSeeds_moreSeeds_superchomperS.value();
            }

            public void superchomperS(float value) {
                nestedSeeds_moreSeeds_superchomperS.set(value);
            }

            public float tallnutSv2() {
                return nestedSeeds_moreSeeds_tallnutSv2.value();
            }

            public void tallnutSv2(float value) {
                nestedSeeds_moreSeeds_tallnutSv2.set(value);
            }

            public float tanglekelpS() {
                return nestedSeeds_moreSeeds_tanglekelpS.value();
            }

            public void tanglekelpS(float value) {
                nestedSeeds_moreSeeds_tanglekelpS.set(value);
            }

            public float threepeaterS() {
                return nestedSeeds_moreSeeds_threepeaterS.value();
            }

            public void threepeaterS(float value) {
                nestedSeeds_moreSeeds_threepeaterS.set(value);
            }

            public float torchwoodS() {
                return nestedSeeds_moreSeeds_torchwoodS.value();
            }

            public void torchwoodS(float value) {
                nestedSeeds_moreSeeds_torchwoodS.set(value);
            }

            public float tulimpeterS() {
                return nestedSeeds_moreSeeds_tulimpeterS.value();
            }

            public void tulimpeterS(float value) {
                nestedSeeds_moreSeeds_tulimpeterS.set(value);
            }

            public float vampireflowerS() {
                return nestedSeeds_moreSeeds_vampireflowerS.value();
            }

            public void vampireflowerS(float value) {
                nestedSeeds_moreSeeds_vampireflowerS.set(value);
            }

            public float twinsunflowerS() {
                return nestedSeeds_moreSeeds_twinsunflowerS.value();
            }

            public void twinsunflowerS(float value) {
                nestedSeeds_moreSeeds_twinsunflowerS.set(value);
            }

            public float wintermelonS() {
                return nestedSeeds_moreSeeds_wintermelonS.value();
            }

            public void wintermelonS(float value) {
                nestedSeeds_moreSeeds_wintermelonS.set(value);
            }

            public float wallnutSv2() {
                return nestedSeeds_moreSeeds_wallnutSv2.value();
            }

            public void wallnutSv2(float value) {
                nestedSeeds_moreSeeds_wallnutSv2.set(value);
            }

            public float weeniebeanieS() {
                return nestedSeeds_moreSeeds_weeniebeanieS.value();
            }

            public void weeniebeanieS(float value) {
                nestedSeeds_moreSeeds_weeniebeanieS.set(value);
            }

            public float zapricotS() {
                return nestedSeeds_moreSeeds_zapricotS.value();
            }

            public void zapricotS(float value) {
                nestedSeeds_moreSeeds_zapricotS.set(value);
            }

        }
    }
    public final NestedSun nestedSun = new NestedSun();
    public class NestedSun implements PvZSunNest {
        public float sunflowerSec() {
            return nestedSun_sunflowerSec.value();
        }

        public void sunflowerSec(float value) {
            nestedSun_sunflowerSec.set(value);
        }

        public float sunflowerSecInitial() {
            return nestedSun_sunflowerSecInitial.value();
        }

        public void sunflowerSecInitial(float value) {
            nestedSun_sunflowerSecInitial.set(value);
        }

        public boolean sunflowerDropSun() {
            return nestedSun_sunflowerDropSun.value();
        }

        public void sunflowerDropSun(boolean value) {
            nestedSun_sunflowerDropSun.set(value);
        }

        public float twinSunflowerSec() {
            return nestedSun_twinSunflowerSec.value();
        }

        public void twinSunflowerSec(float value) {
            nestedSun_twinSunflowerSec.set(value);
        }

        public float sunshroomSec() {
            return nestedSun_sunshroomSec.value();
        }

        public void sunshroomSec(float value) {
            nestedSun_sunshroomSec.set(value);
        }

        public float sunshroomSecInitial() {
            return nestedSun_sunshroomSecInitial.value();
        }

        public void sunshroomSecInitial(float value) {
            nestedSun_sunshroomSecInitial.set(value);
        }

        public float sunshroomSunChance() {
            return nestedSun_sunshroomSunChance.value();
        }

        public void sunshroomSunChance(float value) {
            nestedSun_sunshroomSunChance.set(value);
        }

        public float sunshroomSun2ndChance() {
            return nestedSun_sunshroomSun2ndChance.value();
        }

        public void sunshroomSun2ndChance(float value) {
            nestedSun_sunshroomSun2ndChance.set(value);
        }

        public float goldtileSec() {
            return nestedSun_goldtileSec.value();
        }

        public void goldtileSec(float value) {
            nestedSun_goldtileSec.set(value);
        }

        public float sunseedSec() {
            return nestedSun_sunseedSec.value();
        }

        public void sunseedSec(float value) {
            nestedSun_sunseedSec.set(value);
        }

        public float zombiegraveSec() {
            return nestedSun_zombiegraveSec.value();
        }

        public void zombiegraveSec(float value) {
            nestedSun_zombiegraveSec.set(value);
        }

    }
    public final NestedProjDMG nestedProjDMG = new NestedProjDMG();
    public class NestedProjDMG implements PvZDMGNest {
        public float acidFumeDMG() {
            return nestedProjDMG_acidFumeDMG.value();
        }

        public void acidFumeDMG(float value) {
            nestedProjDMG_acidFumeDMG.set(value);
        }

        public float acidSporeDMG() {
            return nestedProjDMG_acidSporeDMG.value();
        }

        public void acidSporeDMG(float value) {
            nestedProjDMG_acidSporeDMG.set(value);
        }

        public float armorBubbleDMG() {
            return nestedProjDMG_armorBubbleDMG.value();
        }

        public void armorBubbleDMG(float value) {
            nestedProjDMG_armorBubbleDMG.set(value);
        }

        public float beespikeDMGv2() {
            return nestedProjDMG_beespikeDMGv2.value();
        }

        public void beespikeDMGv2(float value) {
            nestedProjDMG_beespikeDMGv2.set(value);
        }

        public float breezeDMG() {
            return nestedProjDMG_breezeDMG.value();
        }

        public void breezeDMG(float value) {
            nestedProjDMG_breezeDMG.set(value);
        }

        public float boomerangDMGv2() {
            return nestedProjDMG_boomerangDMGv2.value();
        }

        public void boomerangDMGv2(float value) {
            nestedProjDMG_boomerangDMGv2.set(value);
        }

        public float bubblesDMG() {
            return nestedProjDMG_bubblesDMG.value();
        }

        public void bubblesDMG(float value) {
            nestedProjDMG_bubblesDMG.set(value);
        }

        public float cabbageDMG() {
            return nestedProjDMG_cabbageDMG.value();
        }

        public void cabbageDMG(float value) {
            nestedProjDMG_cabbageDMG.set(value);
        }

        public float kernalDMG() {
            return nestedProjDMG_kernalDMG.value();
        }

        public void kernalDMG(float value) {
            nestedProjDMG_kernalDMG.set(value);
        }

        public float butterDMG() {
            return nestedProjDMG_butterDMG.value();
        }

        public void butterDMG(float value) {
            nestedProjDMG_butterDMG.set(value);
        }

        public float melonDMG() {
            return nestedProjDMG_melonDMG.value();
        }

        public void melonDMG(float value) {
            nestedProjDMG_melonDMG.set(value);
        }

        public float melonSDMG() {
            return nestedProjDMG_melonSDMG.value();
        }

        public void melonSDMG(float value) {
            nestedProjDMG_melonSDMG.set(value);
        }

        public float melonsliceDMG() {
            return nestedProjDMG_melonsliceDMG.value();
        }

        public void melonsliceDMG(float value) {
            nestedProjDMG_melonsliceDMG.set(value);
        }

        public float melonsliceSDMG() {
            return nestedProjDMG_melonsliceSDMG.value();
        }

        public void melonsliceSDMG(float value) {
            nestedProjDMG_melonsliceSDMG.set(value);
        }

        public float wintermelonDMG() {
            return nestedProjDMG_wintermelonDMG.value();
        }

        public void wintermelonDMG(float value) {
            nestedProjDMG_wintermelonDMG.set(value);
        }

        public float wintermelonSDMG() {
            return nestedProjDMG_wintermelonSDMG.value();
        }

        public void wintermelonSDMG(float value) {
            nestedProjDMG_wintermelonSDMG.set(value);
        }

        public float cardDMGv2() {
            return nestedProjDMG_cardDMGv2.value();
        }

        public void cardDMGv2(float value) {
            nestedProjDMG_cardDMGv2.set(value);
        }

        public float coconutDMGv2() {
            return nestedProjDMG_coconutDMGv2.value();
        }

        public void coconutDMGv2(float value) {
            nestedProjDMG_coconutDMGv2.set(value);
        }

        public float coconutSDMG() {
            return nestedProjDMG_coconutSDMG.value();
        }

        public void coconutSDMG(float value) {
            nestedProjDMG_coconutSDMG.set(value);
        }

        public float dropDMGv2() {
            return nestedProjDMG_dropDMGv2.value();
        }

        public void dropDMGv2(float value) {
            nestedProjDMG_dropDMGv2.set(value);
        }

        public float dropSDMG() {
            return nestedProjDMG_dropSDMG.value();
        }

        public void dropSDMG(float value) {
            nestedProjDMG_dropSDMG.set(value);
        }

        public float dyeDMG() {
            return nestedProjDMG_dyeDMG.value();
        }

        public void dyeDMG(float value) {
            nestedProjDMG_dyeDMG.set(value);
        }

        public float electricPeaDMG() {
            return nestedProjDMG_electricPeaDMG.value();
        }

        public void electricPeaDMG(float value) {
            nestedProjDMG_electricPeaDMG.set(value);
        }

        public float firepiercepeaDMG() {
            return nestedProjDMG_firepiercepeaDMG.value();
        }

        public void firepiercepeaDMG(float value) {
            nestedProjDMG_firepiercepeaDMG.set(value);
        }

        public float firepiercepeaSDMG() {
            return nestedProjDMG_firepiercepeaSDMG.value();
        }

        public void firepiercepeaSDMG(float value) {
            nestedProjDMG_firepiercepeaSDMG.set(value);
        }

        public float frisbeeDMG() {
            return nestedProjDMG_frisbeeDMG.value();
        }

        public void frisbeeDMG(float value) {
            nestedProjDMG_frisbeeDMG.set(value);
        }

        public float flamingPeaDMGv2() {
            return nestedProjDMG_flamingPeaDMGv2.value();
        }

        public void flamingPeaDMGv2(float value) {
            nestedProjDMG_flamingPeaDMGv2.set(value);
        }

        public float flamingPeaSDMG() {
            return nestedProjDMG_flamingPeaSDMG.value();
        }

        public void flamingPeaSDMG(float value) {
            nestedProjDMG_flamingPeaSDMG.set(value);
        }

        public float fumeDMG() {
            return nestedProjDMG_fumeDMG.value();
        }

        public void fumeDMG(float value) {
            nestedProjDMG_fumeDMG.set(value);
        }

        public float goldencardDMG() {
            return nestedProjDMG_goldencardDMG.value();
        }

        public void goldencardDMG(float value) {
            nestedProjDMG_goldencardDMG.set(value);
        }

        public float hypnoprojDMGv2() {
            return nestedProjDMG_hypnoprojDMGv2.value();
        }

        public void hypnoprojDMGv2(float value) {
            nestedProjDMG_hypnoprojDMGv2.set(value);
        }

        public float icebergDMGv2() {
            return nestedProjDMG_icebergDMGv2.value();
        }

        public void icebergDMGv2(float value) {
            nestedProjDMG_icebergDMGv2.set(value);
        }

        public float icebergSDMG() {
            return nestedProjDMG_icebergSDMG.value();
        }

        public void icebergSDMG(float value) {
            nestedProjDMG_icebergSDMG.set(value);
        }

        public float icespikeDMGv2() {
            return nestedProjDMG_icespikeDMGv2.value();
        }

        public void icespikeDMGv2(float value) {
            nestedProjDMG_icespikeDMGv2.set(value);
        }

        public float iceSpikeMultiplier() {
            return nestedProjDMG_iceSpikeMultiplier.value();
        }

        public void iceSpikeMultiplier(float value) {
            nestedProjDMG_iceSpikeMultiplier.set(value);
        }

        public float jingleDMGv2() {
            return nestedProjDMG_jingleDMGv2.value();
        }

        public void jingleDMGv2(float value) {
            nestedProjDMG_jingleDMGv2.set(value);
        }

        public float missileToeDMGNear() {
            return nestedProjDMG_missileToeDMGNear.value();
        }

        public void missileToeDMGNear(float value) {
            nestedProjDMG_missileToeDMGNear.set(value);
        }

        public float missileToeDMGFar() {
            return nestedProjDMG_missileToeDMGFar.value();
        }

        public void missileToeDMGFar(float value) {
            nestedProjDMG_missileToeDMGFar.set(value);
        }

        public float missileToeDMGRangeNear() {
            return nestedProjDMG_missileToeDMGRangeNear.value();
        }

        public void missileToeDMGRangeNear(float value) {
            nestedProjDMG_missileToeDMGRangeNear.set(value);
        }

        public float missileToeDMGRangeFar() {
            return nestedProjDMG_missileToeDMGRangeFar.value();
        }

        public void missileToeDMGRangeFar(float value) {
            nestedProjDMG_missileToeDMGRangeFar.set(value);
        }

        public float peaDMG() {
            return nestedProjDMG_peaDMG.value();
        }

        public void peaDMG(float value) {
            nestedProjDMG_peaDMG.set(value);
        }

        public float pepperDMGv2() {
            return nestedProjDMG_pepperDMGv2.value();
        }

        public void pepperDMGv2(float value) {
            nestedProjDMG_pepperDMGv2.set(value);
        }

        public float pepperSDMG() {
            return nestedProjDMG_pepperSDMG.value();
        }

        public void pepperSDMG(float value) {
            nestedProjDMG_pepperSDMG.set(value);
        }

        public float piercepeaDMG() {
            return nestedProjDMG_piercepeaDMG.value();
        }

        public void piercepeaDMG(float value) {
            nestedProjDMG_piercepeaDMG.set(value);
        }

        public float piercesporeDMG() {
            return nestedProjDMG_piercesporeDMG.value();
        }

        public void piercesporeDMG(float value) {
            nestedProjDMG_piercesporeDMG.set(value);
        }

        public float plasmaPeaDMG() {
            return nestedProjDMG_plasmaPeaDMG.value();
        }

        public void plasmaPeaDMG(float value) {
            nestedProjDMG_plasmaPeaDMG.set(value);
        }

        public float rainbowBulletDMG() {
            return nestedProjDMG_rainbowBulletDMG.value();
        }

        public void rainbowBulletDMG(float value) {
            nestedProjDMG_rainbowBulletDMG.set(value);
        }

        public float snowPeaDMG() {
            return nestedProjDMG_snowPeaDMG.value();
        }

        public void snowPeaDMG(float value) {
            nestedProjDMG_snowPeaDMG.set(value);
        }

        public float snowQueenPeaDMGv2() {
            return nestedProjDMG_snowQueenPeaDMGv2.value();
        }

        public void snowQueenPeaDMGv2(float value) {
            nestedProjDMG_snowQueenPeaDMGv2.set(value);
        }

        public float snowQueenPeaSDMG() {
            return nestedProjDMG_snowQueenPeaSDMG.value();
        }

        public void snowQueenPeaSDMG(float value) {
            nestedProjDMG_snowQueenPeaSDMG.set(value);
        }

        public float springDMG() {
            return nestedProjDMG_springDMG.value();
        }

        public void springDMG(float value) {
            nestedProjDMG_springDMG.set(value);
        }

        public float spikeDMGv2() {
            return nestedProjDMG_spikeDMGv2.value();
        }

        public void spikeDMGv2(float value) {
            nestedProjDMG_spikeDMGv2.set(value);
        }

        public float spitDMG() {
            return nestedProjDMG_spitDMG.value();
        }

        public void spitDMG(float value) {
            nestedProjDMG_spitDMG.set(value);
        }

        public float smooshProjDMG() {
            return nestedProjDMG_smooshProjDMG.value();
        }

        public void smooshProjDMG(float value) {
            nestedProjDMG_smooshProjDMG.set(value);
        }

        public float sporeDMG() {
            return nestedProjDMG_sporeDMG.value();
        }

        public void sporeDMG(float value) {
            nestedProjDMG_sporeDMG.set(value);
        }

        public float swordDMG() {
            return nestedProjDMG_swordDMG.value();
        }

        public void swordDMG(float value) {
            nestedProjDMG_swordDMG.set(value);
        }

        public float basketBallDMG() {
            return nestedProjDMG_basketBallDMG.value();
        }

        public void basketBallDMG(float value) {
            nestedProjDMG_basketBallDMG.set(value);
        }

        public float laserDMG() {
            return nestedProjDMG_laserDMG.value();
        }

        public void laserDMG(float value) {
            nestedProjDMG_laserDMG.set(value);
        }

        public float soundwaveDMG() {
            return nestedProjDMG_soundwaveDMG.value();
        }

        public void soundwaveDMG(float value) {
            nestedProjDMG_soundwaveDMG.set(value);
        }

        public float zpgDMG() {
            return nestedProjDMG_zpgDMG.value();
        }

        public void zpgDMG(float value) {
            nestedProjDMG_zpgDMG.set(value);
        }

        public float rocketDMG() {
            return nestedProjDMG_rocketDMG.value();
        }

        public void rocketDMG(float value) {
            nestedProjDMG_rocketDMG.set(value);
        }

    }
    public final NestedGeneralZombie nestedGeneralZombie = new NestedGeneralZombie();
    public class NestedGeneralZombie implements PvZZombieGeneral {
        public float zombieStep() {
            return nestedGeneralZombie_zombieStep.value();
        }

        public void zombieStep(float value) {
            nestedGeneralZombie_zombieStep.set(value);
        }

        public int zombieBlockJump() {
            return nestedGeneralZombie_zombieBlockJump.value();
        }

        public void zombieBlockJump(int value) {
            nestedGeneralZombie_zombieBlockJump.set(value);
        }

    }
    public final NestedZombieHealth nestedZombieHealth = new NestedZombieHealth();
    public class NestedZombieHealth implements PvZZombieHealth {
        public double zombieGraveH() {
            return nestedZombieHealth_zombieGraveH.value();
        }

        public void zombieGraveH(double value) {
            nestedZombieHealth_zombieGraveH.set(value);
        }

        public double basicGraveH() {
            return nestedZombieHealth_basicGraveH.value();
        }

        public void basicGraveH(double value) {
            nestedZombieHealth_basicGraveH.set(value);
        }

        public double nightGraveH() {
            return nestedZombieHealth_nightGraveH.value();
        }

        public void nightGraveH(double value) {
            nestedZombieHealth_nightGraveH.set(value);
        }

        public double poolGraveH() {
            return nestedZombieHealth_poolGraveH.value();
        }

        public void poolGraveH(double value) {
            nestedZombieHealth_poolGraveH.set(value);
        }

        public double egyptGraveH() {
            return nestedZombieHealth_egyptGraveH.value();
        }

        public void egyptGraveH(double value) {
            nestedZombieHealth_egyptGraveH.set(value);
        }

        public double roofGraveH() {
            return nestedZombieHealth_roofGraveH.value();
        }

        public void roofGraveH(double value) {
            nestedZombieHealth_roofGraveH.set(value);
        }

        public double futureGraveH() {
            return nestedZombieHealth_futureGraveH.value();
        }

        public void futureGraveH(double value) {
            nestedZombieHealth_futureGraveH.set(value);
        }

        public double darkAgesGraveH() {
            return nestedZombieHealth_darkAgesGraveH.value();
        }

        public void darkAgesGraveH(double value) {
            nestedZombieHealth_darkAgesGraveH.set(value);
        }

        public double fairytaleGraveH() {
            return nestedZombieHealth_fairytaleGraveH.value();
        }

        public void fairytaleGraveH(double value) {
            nestedZombieHealth_fairytaleGraveH.set(value);
        }

        public double mausoleumGraveH() {
            return nestedZombieHealth_mausoleumGraveH.value();
        }

        public void mausoleumGraveH(double value) {
            nestedZombieHealth_mausoleumGraveH.set(value);
        }

        public double backupH() {
            return nestedZombieHealth_backupH.value();
        }

        public void backupH(double value) {
            nestedZombieHealth_backupH.set(value);
        }

        public double bassH() {
            return nestedZombieHealth_bassH.value();
        }

        public void bassH(double value) {
            nestedZombieHealth_bassH.set(value);
        }

        public double browncoatH() {
            return nestedZombieHealth_browncoatH.value();
        }

        public void browncoatH(double value) {
            nestedZombieHealth_browncoatH.set(value);
        }

        public double summerH() {
            return nestedZombieHealth_summerH.value();
        }

        public void summerH(double value) {
            nestedZombieHealth_summerH.set(value);
        }

        public double mummyH() {
            return nestedZombieHealth_mummyH.value();
        }

        public void mummyH(double value) {
            nestedZombieHealth_mummyH.set(value);
        }

        public double futureH() {
            return nestedZombieHealth_futureH.value();
        }

        public void futureH(double value) {
            nestedZombieHealth_futureH.set(value);
        }

        public double peasantH() {
            return nestedZombieHealth_peasantH.value();
        }

        public void peasantH(double value) {
            nestedZombieHealth_peasantH.set(value);
        }

        public double pokerheartH() {
            return nestedZombieHealth_pokerheartH.value();
        }

        public void pokerheartH(double value) {
            nestedZombieHealth_pokerheartH.set(value);
        }

        public double pokerspadeH() {
            return nestedZombieHealth_pokerspadeH.value();
        }

        public void pokerspadeH(double value) {
            nestedZombieHealth_pokerspadeH.set(value);
        }

        public double pokerclubH() {
            return nestedZombieHealth_pokerclubH.value();
        }

        public void pokerclubH(double value) {
            nestedZombieHealth_pokerclubH.set(value);
        }

        public double pokerdiamondH() {
            return nestedZombieHealth_pokerdiamondH.value();
        }

        public void pokerdiamondH(double value) {
            nestedZombieHealth_pokerdiamondH.set(value);
        }

        public double bobsledH() {
            return nestedZombieHealth_bobsledH.value();
        }

        public void bobsledH(double value) {
            nestedZombieHealth_bobsledH.set(value);
        }

        public double sargeantH() {
            return nestedZombieHealth_sargeantH.value();
        }

        public void sargeantH(double value) {
            nestedZombieHealth_sargeantH.set(value);
        }

        public double bullyH() {
            return nestedZombieHealth_bullyH.value();
        }

        public void bullyH(double value) {
            nestedZombieHealth_bullyH.set(value);
        }

        public double actionheroH() {
            return nestedZombieHealth_actionheroH.value();
        }

        public void actionheroH(double value) {
            nestedZombieHealth_actionheroH.set(value);
        }

        public double basketballH() {
            return nestedZombieHealth_basketballH.value();
        }

        public void basketballH(double value) {
            nestedZombieHealth_basketballH.set(value);
        }

        public double dancingH() {
            return nestedZombieHealth_dancingH.value();
        }

        public void dancingH(double value) {
            nestedZombieHealth_dancingH.set(value);
        }

        public double dolphinH() {
            return nestedZombieHealth_dolphinH.value();
        }

        public void dolphinH(double value) {
            nestedZombieHealth_dolphinH.set(value);
        }

        public double explorerH() {
            return nestedZombieHealth_explorerH.value();
        }

        public void explorerH(double value) {
            nestedZombieHealth_explorerH.set(value);
        }

        public double torchlightH() {
            return nestedZombieHealth_torchlightH.value();
        }

        public void torchlightH(double value) {
            nestedZombieHealth_torchlightH.set(value);
        }

        public double flagH() {
            return nestedZombieHealth_flagH.value();
        }

        public void flagH(double value) {
            nestedZombieHealth_flagH.set(value);
        }

        public double flagSummerH() {
            return nestedZombieHealth_flagSummerH.value();
        }

        public void flagSummerH(double value) {
            nestedZombieHealth_flagSummerH.set(value);
        }

        public double flagMummyH() {
            return nestedZombieHealth_flagMummyH.value();
        }

        public void flagMummyH(double value) {
            nestedZombieHealth_flagMummyH.set(value);
        }

        public double flagFuturetH() {
            return nestedZombieHealth_flagFuturetH.value();
        }

        public void flagFuturetH(double value) {
            nestedZombieHealth_flagFuturetH.set(value);
        }

        public double flagPeasantH() {
            return nestedZombieHealth_flagPeasantH.value();
        }

        public void flagPeasantH(double value) {
            nestedZombieHealth_flagPeasantH.set(value);
        }

        public double flagPokerH() {
            return nestedZombieHealth_flagPokerH.value();
        }

        public void flagPokerH(double value) {
            nestedZombieHealth_flagPokerH.set(value);
        }

        public double flagSargeantH() {
            return nestedZombieHealth_flagSargeantH.value();
        }

        public void flagSargeantH(double value) {
            nestedZombieHealth_flagSargeantH.set(value);
        }

        public double footballH() {
            return nestedZombieHealth_footballH.value();
        }

        public void footballH(double value) {
            nestedZombieHealth_footballH.set(value);
        }

        public double berserkerH() {
            return nestedZombieHealth_berserkerH.value();
        }

        public void berserkerH(double value) {
            nestedZombieHealth_berserkerH.set(value);
        }

        public double gargantuarH() {
            return nestedZombieHealth_gargantuarH.value();
        }

        public void gargantuarH(double value) {
            nestedZombieHealth_gargantuarH.set(value);
        }

        public double mummygargantuarH() {
            return nestedZombieHealth_mummygargantuarH.value();
        }

        public void mummygargantuarH(double value) {
            nestedZombieHealth_mummygargantuarH.set(value);
        }

        public double defensiveendH() {
            return nestedZombieHealth_defensiveendH.value();
        }

        public void defensiveendH(double value) {
            nestedZombieHealth_defensiveendH.set(value);
        }

        public double cursedgargolithH() {
            return nestedZombieHealth_cursedgargolithH.value();
        }

        public void cursedgargolithH(double value) {
            nestedZombieHealth_cursedgargolithH.set(value);
        }

        public double unicorngargantuarH() {
            return nestedZombieHealth_unicorngargantuarH.value();
        }

        public void unicorngargantuarH(double value) {
            nestedZombieHealth_unicorngargantuarH.set(value);
        }

        public double hawkerpusherH() {
            return nestedZombieHealth_hawkerpusherH.value();
        }

        public void hawkerpusherH(double value) {
            nestedZombieHealth_hawkerpusherH.set(value);
        }

        public double hoverGoatH() {
            return nestedZombieHealth_hoverGoatH.value();
        }

        public void hoverGoatH(double value) {
            nestedZombieHealth_hoverGoatH.set(value);
        }

        public double impH() {
            return nestedZombieHealth_impH.value();
        }

        public void impH(double value) {
            nestedZombieHealth_impH.set(value);
        }

        public double impdragonH() {
            return nestedZombieHealth_impdragonH.value();
        }

        public void impdragonH(double value) {
            nestedZombieHealth_impdragonH.set(value);
        }

        public double bassimpH() {
            return nestedZombieHealth_bassimpH.value();
        }

        public void bassimpH(double value) {
            nestedZombieHealth_bassimpH.set(value);
        }

        public double scrapimpH() {
            return nestedZombieHealth_scrapimpH.value();
        }

        public void scrapimpH(double value) {
            nestedZombieHealth_scrapimpH.set(value);
        }

        public double superFanH() {
            return nestedZombieHealth_superFanH.value();
        }

        public void superFanH(double value) {
            nestedZombieHealth_superFanH.set(value);
        }

        public double announcerH() {
            return nestedZombieHealth_announcerH.value();
        }

        public void announcerH(double value) {
            nestedZombieHealth_announcerH.set(value);
        }

        public double jetpackH() {
            return nestedZombieHealth_jetpackH.value();
        }

        public void jetpackH(double value) {
            nestedZombieHealth_jetpackH.set(value);
        }

        public double blastronautH() {
            return nestedZombieHealth_blastronautH.value();
        }

        public void blastronautH(double value) {
            nestedZombieHealth_blastronautH.set(value);
        }

        public double newspaperH() {
            return nestedZombieHealth_newspaperH.value();
        }

        public void newspaperH(double value) {
            nestedZombieHealth_newspaperH.set(value);
        }

        public double sundayH() {
            return nestedZombieHealth_sundayH.value();
        }

        public void sundayH(double value) {
            nestedZombieHealth_sundayH.set(value);
        }

        public double octoH() {
            return nestedZombieHealth_octoH.value();
        }

        public void octoH(double value) {
            nestedZombieHealth_octoH.set(value);
        }

        public double poleH() {
            return nestedZombieHealth_poleH.value();
        }

        public void poleH(double value) {
            nestedZombieHealth_poleH.set(value);
        }

        public double pharaohH() {
            return nestedZombieHealth_pharaohH.value();
        }

        public void pharaohH(double value) {
            nestedZombieHealth_pharaohH.set(value);
        }

        public double undyingPharaohH() {
            return nestedZombieHealth_undyingPharaohH.value();
        }

        public void undyingPharaohH(double value) {
            nestedZombieHealth_undyingPharaohH.set(value);
        }

        public double pumpkincarH() {
            return nestedZombieHealth_pumpkincarH.value();
        }

        public void pumpkincarH(double value) {
            nestedZombieHealth_pumpkincarH.set(value);
        }

        public double roboconeH() {
            return nestedZombieHealth_roboconeH.value();
        }

        public void roboconeH(double value) {
            nestedZombieHealth_roboconeH.set(value);
        }

        public double scrapmechH() {
            return nestedZombieHealth_scrapmechH.value();
        }

        public void scrapmechH(double value) {
            nestedZombieHealth_scrapmechH.set(value);
        }

        public double scientistH() {
            return nestedZombieHealth_scientistH.value();
        }

        public void scientistH(double value) {
            nestedZombieHealth_scientistH.set(value);
        }

        public double snorkelH() {
            return nestedZombieHealth_snorkelH.value();
        }

        public void snorkelH(double value) {
            nestedZombieHealth_snorkelH.set(value);
        }

        public double soldierH() {
            return nestedZombieHealth_soldierH.value();
        }

        public void soldierH(double value) {
            nestedZombieHealth_soldierH.set(value);
        }

        public double tombraiserH() {
            return nestedZombieHealth_tombraiserH.value();
        }

        public void tombraiserH(double value) {
            nestedZombieHealth_tombraiserH.set(value);
        }

        public double zombiekingH() {
            return nestedZombieHealth_zombiekingH.value();
        }

        public void zombiekingH(double value) {
            nestedZombieHealth_zombiekingH.set(value);
        }

        public double zombiepigH() {
            return nestedZombieHealth_zombiepigH.value();
        }

        public void zombiepigH(double value) {
            nestedZombieHealth_zombiepigH.set(value);
        }

        public double zomblobH() {
            return nestedZombieHealth_zomblobH.value();
        }

        public void zomblobH(double value) {
            nestedZombieHealth_zomblobH.set(value);
        }

        public double zomblobBH() {
            return nestedZombieHealth_zomblobBH.value();
        }

        public void zomblobBH(double value) {
            nestedZombieHealth_zomblobBH.set(value);
        }

        public double zomblobSH() {
            return nestedZombieHealth_zomblobSH.value();
        }

        public void zomblobSH(double value) {
            nestedZombieHealth_zomblobSH.set(value);
        }

        public double zomboniH() {
            return nestedZombieHealth_zomboniH.value();
        }

        public void zomboniH(double value) {
            nestedZombieHealth_zomboniH.set(value);
        }

        public double coneH() {
            return nestedZombieHealth_coneH.value();
        }

        public void coneH(double value) {
            nestedZombieHealth_coneH.set(value);
        }

        public double pokerpawngearH() {
            return nestedZombieHealth_pokerpawngearH.value();
        }

        public void pokerpawngearH(double value) {
            nestedZombieHealth_pokerpawngearH.set(value);
        }

        public double pokerknightgearH() {
            return nestedZombieHealth_pokerknightgearH.value();
        }

        public void pokerknightgearH(double value) {
            nestedZombieHealth_pokerknightgearH.set(value);
        }

        public double pokertowergearH() {
            return nestedZombieHealth_pokertowergearH.value();
        }

        public void pokertowergearH(double value) {
            nestedZombieHealth_pokertowergearH.set(value);
        }

        public double pokerbishopgearH() {
            return nestedZombieHealth_pokerbishopgearH.value();
        }

        public void pokerbishopgearH(double value) {
            nestedZombieHealth_pokerbishopgearH.set(value);
        }

        public double kingpiecegearH() {
            return nestedZombieHealth_kingpiecegearH.value();
        }

        public void kingpiecegearH(double value) {
            nestedZombieHealth_kingpiecegearH.set(value);
        }

        public double bucketH() {
            return nestedZombieHealth_bucketH.value();
        }

        public void bucketH(double value) {
            nestedZombieHealth_bucketH.set(value);
        }

        public double medallionH() {
            return nestedZombieHealth_medallionH.value();
        }

        public void medallionH(double value) {
            nestedZombieHealth_medallionH.set(value);
        }

        public double footballHelmH() {
            return nestedZombieHealth_footballHelmH.value();
        }

        public void footballHelmH(double value) {
            nestedZombieHealth_footballHelmH.set(value);
        }

        public double berserkerHelmH() {
            return nestedZombieHealth_berserkerHelmH.value();
        }

        public void berserkerHelmH(double value) {
            nestedZombieHealth_berserkerHelmH.set(value);
        }

        public double defensiveendHelmH() {
            return nestedZombieHealth_defensiveendHelmH.value();
        }

        public void defensiveendHelmH(double value) {
            nestedZombieHealth_defensiveendHelmH.set(value);
        }

        public double blastronautHelmH() {
            return nestedZombieHealth_blastronautHelmH.value();
        }

        public void blastronautHelmH(double value) {
            nestedZombieHealth_blastronautHelmH.set(value);
        }

        public double knightHelmH() {
            return nestedZombieHealth_knightHelmH.value();
        }

        public void knightHelmH(double value) {
            nestedZombieHealth_knightHelmH.set(value);
        }

        public double sargeanthelmetH() {
            return nestedZombieHealth_sargeanthelmetH.value();
        }

        public void sargeanthelmetH(double value) {
            nestedZombieHealth_sargeanthelmetH.set(value);
        }

        public double soldierhelmetH() {
            return nestedZombieHealth_soldierhelmetH.value();
        }

        public void soldierhelmetH(double value) {
            nestedZombieHealth_soldierhelmetH.set(value);
        }

        public double brickH() {
            return nestedZombieHealth_brickH.value();
        }

        public void brickH(double value) {
            nestedZombieHealth_brickH.set(value);
        }

        public double coneTowerH() {
            return nestedZombieHealth_coneTowerH.value();
        }

        public void coneTowerH(double value) {
            nestedZombieHealth_coneTowerH.set(value);
        }

        public double pyramidH() {
            return nestedZombieHealth_pyramidH.value();
        }

        public void pyramidH(double value) {
            nestedZombieHealth_pyramidH.set(value);
        }

        public double sarcophagusH() {
            return nestedZombieHealth_sarcophagusH.value();
        }

        public void sarcophagusH(double value) {
            nestedZombieHealth_sarcophagusH.set(value);
        }

        public double bowlH() {
            return nestedZombieHealth_bowlH.value();
        }

        public void bowlH(double value) {
            nestedZombieHealth_bowlH.set(value);
        }

        public double holoHelmetH() {
            return nestedZombieHealth_holoHelmetH.value();
        }

        public void holoHelmetH(double value) {
            nestedZombieHealth_holoHelmetH.set(value);
        }

        public double crystalshoeHelmetH() {
            return nestedZombieHealth_crystalshoeHelmetH.value();
        }

        public void crystalshoeHelmetH(double value) {
            nestedZombieHealth_crystalshoeHelmetH.set(value);
        }

        public double pumpkinH() {
            return nestedZombieHealth_pumpkinH.value();
        }

        public void pumpkinH(double value) {
            nestedZombieHealth_pumpkinH.set(value);
        }

        public double screendoorShieldH() {
            return nestedZombieHealth_screendoorShieldH.value();
        }

        public void screendoorShieldH(double value) {
            nestedZombieHealth_screendoorShieldH.set(value);
        }

        public double sergeantShieldH() {
            return nestedZombieHealth_sergeantShieldH.value();
        }

        public void sergeantShieldH(double value) {
            nestedZombieHealth_sergeantShieldH.set(value);
        }

        public double newspaperShieldH() {
            return nestedZombieHealth_newspaperShieldH.value();
        }

        public void newspaperShieldH(double value) {
            nestedZombieHealth_newspaperShieldH.set(value);
        }

        public double sundayShieldH() {
            return nestedZombieHealth_sundayShieldH.value();
        }

        public void sundayShieldH(double value) {
            nestedZombieHealth_sundayShieldH.set(value);
        }

        public double bookShieldH() {
            return nestedZombieHealth_bookShieldH.value();
        }

        public void bookShieldH(double value) {
            nestedZombieHealth_bookShieldH.set(value);
        }

        public double trashcanObstH() {
            return nestedZombieHealth_trashcanObstH.value();
        }

        public void trashcanObstH(double value) {
            nestedZombieHealth_trashcanObstH.set(value);
        }

        public double basketballObstH() {
            return nestedZombieHealth_basketballObstH.value();
        }

        public void basketballObstH(double value) {
            nestedZombieHealth_basketballObstH.set(value);
        }

        public double healstationObstH() {
            return nestedZombieHealth_healstationObstH.value();
        }

        public void healstationObstH(double value) {
            nestedZombieHealth_healstationObstH.set(value);
        }

        public double gargolithObstH() {
            return nestedZombieHealth_gargolithObstH.value();
        }

        public void gargolithObstH(double value) {
            nestedZombieHealth_gargolithObstH.set(value);
        }

        public double imptabletObstH() {
            return nestedZombieHealth_imptabletObstH.value();
        }

        public void imptabletObstH(double value) {
            nestedZombieHealth_imptabletObstH.set(value);
        }

        public double egyptTombstoneH() {
            return nestedZombieHealth_egyptTombstoneH.value();
        }

        public void egyptTombstoneH(double value) {
            nestedZombieHealth_egyptTombstoneH.set(value);
        }

        public double hawkerObstH() {
            return nestedZombieHealth_hawkerObstH.value();
        }

        public void hawkerObstH(double value) {
            nestedZombieHealth_hawkerObstH.set(value);
        }

        public double octoObstH() {
            return nestedZombieHealth_octoObstH.value();
        }

        public void octoObstH(double value) {
            nestedZombieHealth_octoObstH.set(value);
        }

        public double zomboniVH() {
            return nestedZombieHealth_zomboniVH.value();
        }

        public void zomboniVH(double value) {
            nestedZombieHealth_zomboniVH.set(value);
        }

        public double bobsledVH() {
            return nestedZombieHealth_bobsledVH.value();
        }

        public void bobsledVH(double value) {
            nestedZombieHealth_bobsledVH.set(value);
        }

        public double speakerVH() {
            return nestedZombieHealth_speakerVH.value();
        }

        public void speakerVH(double value) {
            nestedZombieHealth_speakerVH.set(value);
        }

    }
    public interface PvZSpawnNest {
        boolean spawnPlants();
        void spawnPlants(boolean value);
        boolean specialZombieSpawn();
        void specialZombieSpawn(boolean value);
        boolean hoeAlternative();
        void hoeAlternative(boolean value);
        int hoeBreak();
        void hoeBreak(int value);
    }
    public interface PvZSpawnNestGrave {
        int basicGv2();
        void basicGv2(int value);
        int basicGmin();
        void basicGmin(int value);
        int basicGmax();
        void basicGmax(int value);
        int nightGv2();
        void nightGv2(int value);
        int nightGmin();
        void nightGmin(int value);
        int nightGmax();
        void nightGmax(int value);
        int poolGv2();
        void poolGv2(int value);
        int poolGmin();
        void poolGmin(int value);
        int poolGmax();
        void poolGmax(int value);
        int roofGv2();
        void roofGv2(int value);
        int roofGmin();
        void roofGmin(int value);
        int roofGmax();
        void roofGmax(int value);
        int egyptG();
        void egyptG(int value);
        int egyptGmin();
        void egyptGmin(int value);
        int egyptGmax();
        void egyptGmax(int value);
        int futureGv2();
        void futureGv2(int value);
        int futureGmin();
        void futureGmin(int value);
        int futureGmax();
        void futureGmax(int value);
        int darkagesGv2();
        void darkagesGv2(int value);
        int darkagesGmin();
        void darkagesGmin(int value);
        int darkagesGmax();
        void darkagesGmax(int value);
        int mausoleumG();
        void mausoleumG(int value);
        int mausoleumGmin();
        void mausoleumGmin(int value);
        int mausoleumGmax();
        void mausoleumGmax(int value);
    }
    public interface PvZSpawnNestPlant {
        int peashooterSP();
        void peashooterSP(int value);
        int peashooterSPmin();
        void peashooterSPmin(int value);
        int peashooterSPmax();
        void peashooterSPmax(int value);
        int bellflowerSP();
        void bellflowerSP(int value);
        int bellflowerSPmin();
        void bellflowerSPmin(int value);
        int bellflowerSPmax();
        void bellflowerSPmax(int value);
        int puffshroomSP();
        void puffshroomSP(int value);
        int puffshroomSPmin();
        void puffshroomSPmin(int value);
        int puffshroomSPmax();
        void puffshroomSPmax(int value);
        int shadowshroomSP();
        void shadowshroomSP(int value);
        int shadowshroomSPmin();
        void shadowshroomSPmin(int value);
        int shadowshroomSPmax();
        void shadowshroomSPmax(int value);
        int weeniebeanieSP();
        void weeniebeanieSP(int value);
        int weeniebeanieSPmin();
        void weeniebeanieSPmin(int value);
        int weeniebeanieSPmax();
        void weeniebeanieSPmax(int value);
        int sunflowerseedSPv2();
        void sunflowerseedSPv2(int value);
        int sunflowerseedSPmin();
        void sunflowerseedSPmin(int value);
        int sunflowerseedSPmax();
        void sunflowerseedSPmax(int value);
        int lilypadSP();
        void lilypadSP(int value);
        int lilypadSPmin();
        void lilypadSPmin(int value);
        int lilypadSPmax();
        void lilypadSPmax(int value);
        int bombseedlingSP();
        void bombseedlingSP(int value);
        int bombseedlingSPmin();
        void bombseedlingSPmin(int value);
        int bombseedlingSPmax();
        void bombseedlingSPmax(int value);
        int smallnutSP();
        void smallnutSP(int value);
        int smallnutSPmin();
        void smallnutSPmin(int value);
        int smallnutSPmax();
        void smallnutSPmax(int value);
        int loquatSP();
        void loquatSP(int value);
        int loquatSPmin();
        void loquatSPmin(int value);
        int loquatSPmax();
        void loquatSPmax(int value);
        int icebergSP();
        void icebergSP(int value);
        int icebergSPmin();
        void icebergSPmin(int value);
        int icebergSPmax();
        void icebergSPmax(int value);
        int zapricotSP();
        void zapricotSP(int value);
        int zapricotSPmin();
        void zapricotSPmin(int value);
        int zapricotSPmax();
        void zapricotSPmax(int value);
        int buttonshroomSP();
        void buttonshroomSP(int value);
        int buttonshroomSPmin();
        void buttonshroomSPmin(int value);
        int buttonshroomSPmax();
        void buttonshroomSPmax(int value);
        int solarwindSP();
        void solarwindSP(int value);
        int solarwindSPmin();
        void solarwindSPmin(int value);
        int solarwindSPmax();
        void solarwindSPmax(int value);
    }
    public interface PvZSeedNest {
        boolean infiniteSeeds();
        void infiniteSeeds(boolean value);
        boolean instantRecharge();
        void instantRecharge(boolean value);
    }
    public interface PvZMoreSeeds {
        float acidshrooomS();
        void acidshrooomS(float value);
        float admiralnavybeanS();
        void admiralnavybeanS(float value);
        float bananasaurusS();
        void bananasaurusS(float value);
        float beautyshroomS();
        void beautyshroomS(float value);
        float beeshooterS();
        void beeshooterS(float value);
        float beetS();
        void beetS(float value);
        float bellflowerS();
        void bellflowerS(float value);
        float bloomerangS();
        void bloomerangS(float value);
        float bombseedlingS();
        void bombseedlingS(float value);
        float breezesroomS();
        void breezesroomS(float value);
        float burstshroomS();
        void burstshroomS(float value);
        float buttonshroomS();
        void buttonshroomS(float value);
        float cabbagepultS();
        void cabbagepultS(float value);
        float kernalpultS();
        void kernalpultS(float value);
        float melonpultS();
        void melonpultS(float value);
        float melonsliceS();
        void melonsliceS(float value);
        float WintermelonS();
        void WintermelonS(float value);
        float cattailS();
        void cattailS(float value);
        float coffeeBeanS();
        void coffeeBeanS(float value);
        float charmshroomS();
        void charmshroomS(float value);
        float cherrybombS();
        void cherrybombS(float value);
        float chesterS();
        void chesterS(float value);
        float chillypepperS();
        void chillypepperS(float value);
        float chomperS();
        void chomperS(float value);
        float coconutS();
        void coconutS(float value);
        float dandelionweedS();
        void dandelionweedS(float value);
        float dogwoodS();
        void dogwoodS(float value);
        float doomroseS();
        void doomroseS(float value);
        float doomshroomS();
        void doomshroomS(float value);
        float dripphylleiaS();
        void dripphylleiaS(float value);
        float dropeaS();
        void dropeaS(float value);
        float electropeaS();
        void electropeaS(float value);
        float empeachS();
        void empeachS(float value);
        float endurianS();
        void endurianS(float value);
        float firepeaS();
        void firepeaS(float value);
        float frisbloomS();
        void frisbloomS(float value);
        float fumeshroomS();
        void fumeshroomS(float value);
        float gambleshroomS();
        void gambleshroomS(float value);
        float gatlingpeaS();
        void gatlingpeaS(float value);
        float ghostpepperS();
        void ghostpepperS(float value);
        float gloomvineS();
        void gloomvineS(float value);
        float gloomshroomS();
        void gloomshroomS(float value);
        float goldleafS();
        void goldleafS(float value);
        float gravebusterS();
        void gravebusterS(float value);
        float hammerflowerS();
        void hammerflowerS(float value);
        float heavenlypeachS();
        void heavenlypeachS(float value);
        float hypnoshroomS();
        void hypnoshroomS(float value);
        float iceberglettuceS();
        void iceberglettuceS(float value);
        float icebergpultS();
        void icebergpultS(float value);
        float iceshroomS();
        void iceshroomS(float value);
        float impatyensS();
        void impatyensS(float value);
        float jalapenoS();
        void jalapenoS(float value);
        float jumpingbeanS();
        void jumpingbeanS(float value);
        float knightpeaS();
        void knightpeaS(float value);
        float lightningreedS();
        void lightningreedS(float value);
        float lilypadS();
        void lilypadS(float value);
        float loquatS();
        void loquatS(float value);
        float locococoS();
        void locococoS(float value);
        float magicshroomS();
        void magicshroomS(float value);
        float magnetshroomS();
        void magnetshroomS(float value);
        float magnetoshroomS();
        void magnetoshroomS(float value);
        float meteorhammerS();
        void meteorhammerS(float value);
        float missileToeS();
        void missileToeS(float value);
        float narcissusS();
        void narcissusS(float value);
        float navybeanS();
        void navybeanS(float value);
        float nightcapS();
        void nightcapS(float value);
        float oilyOliveS();
        void oilyOliveS(float value);
        float olivepitS();
        void olivepitS(float value);
        float oxygaeS();
        void oxygaeS(float value);
        float peanutS();
        void peanutS(float value);
        float peapodS();
        void peapodS(float value);
        float peashooterS();
        void peashooterS(float value);
        float pepperpultS();
        void pepperpultS(float value);
        float perfoomshroomS();
        void perfoomshroomS(float value);
        float potatomineS();
        void potatomineS(float value);
        float puffshroomS();
        void puffshroomS(float value);
        float pumpkinwitchS();
        void pumpkinwitchS(float value);
        float repeaterS();
        void repeaterS(float value);
        float retrogatlingS();
        void retrogatlingS(float value);
        float saucerS();
        void saucerS(float value);
        float scaredyshroomS();
        void scaredyshroomS(float value);
        float seapeaS();
        void seapeaS(float value);
        float seashroomS();
        void seashroomS(float value);
        float shadowShroomS();
        void shadowShroomS(float value);
        float shamrockS();
        void shamrockS(float value);
        float smackadamiaSv2();
        void smackadamiaSv2(float value);
        float smallnutS();
        void smallnutS(float value);
        float smooshroomS();
        void smooshroomS(float value);
        float snowpeaS();
        void snowpeaS(float value);
        float snowqueenpeaS();
        void snowqueenpeaS(float value);
        float spikerockS();
        void spikerockS(float value);
        float spikeweedS();
        void spikeweedS(float value);
        float springbeanS();
        void springbeanS(float value);
        float springprincessS();
        void springprincessS(float value);
        float squashS();
        void squashS(float value);
        float sunflowerS();
        void sunflowerS(float value);
        float sunflowerseedS();
        void sunflowerseedS(float value);
        float sunshroomS();
        void sunshroomS(float value);
        float superchomperS();
        void superchomperS(float value);
        float tallnutSv2();
        void tallnutSv2(float value);
        float tanglekelpS();
        void tanglekelpS(float value);
        float threepeaterS();
        void threepeaterS(float value);
        float torchwoodS();
        void torchwoodS(float value);
        float tulimpeterS();
        void tulimpeterS(float value);
        float vampireflowerS();
        void vampireflowerS(float value);
        float twinsunflowerS();
        void twinsunflowerS(float value);
        float wintermelonS();
        void wintermelonS(float value);
        float wallnutSv2();
        void wallnutSv2(float value);
        float weeniebeanieS();
        void weeniebeanieS(float value);
        float zapricotS();
        void zapricotS(float value);
    }
    public interface PvZSunNest {
        float sunflowerSec();
        void sunflowerSec(float value);
        float sunflowerSecInitial();
        void sunflowerSecInitial(float value);
        boolean sunflowerDropSun();
        void sunflowerDropSun(boolean value);
        float twinSunflowerSec();
        void twinSunflowerSec(float value);
        float sunshroomSec();
        void sunshroomSec(float value);
        float sunshroomSecInitial();
        void sunshroomSecInitial(float value);
        float sunshroomSunChance();
        void sunshroomSunChance(float value);
        float sunshroomSun2ndChance();
        void sunshroomSun2ndChance(float value);
        float goldtileSec();
        void goldtileSec(float value);
        float sunseedSec();
        void sunseedSec(float value);
        float zombiegraveSec();
        void zombiegraveSec(float value);
    }
    public interface PvZDMGNest {
        float acidFumeDMG();
        void acidFumeDMG(float value);
        float acidSporeDMG();
        void acidSporeDMG(float value);
        float armorBubbleDMG();
        void armorBubbleDMG(float value);
        float beespikeDMGv2();
        void beespikeDMGv2(float value);
        float breezeDMG();
        void breezeDMG(float value);
        float boomerangDMGv2();
        void boomerangDMGv2(float value);
        float bubblesDMG();
        void bubblesDMG(float value);
        float cabbageDMG();
        void cabbageDMG(float value);
        float kernalDMG();
        void kernalDMG(float value);
        float butterDMG();
        void butterDMG(float value);
        float melonDMG();
        void melonDMG(float value);
        float melonSDMG();
        void melonSDMG(float value);
        float melonsliceDMG();
        void melonsliceDMG(float value);
        float melonsliceSDMG();
        void melonsliceSDMG(float value);
        float wintermelonDMG();
        void wintermelonDMG(float value);
        float wintermelonSDMG();
        void wintermelonSDMG(float value);
        float cardDMGv2();
        void cardDMGv2(float value);
        float coconutDMGv2();
        void coconutDMGv2(float value);
        float coconutSDMG();
        void coconutSDMG(float value);
        float dropDMGv2();
        void dropDMGv2(float value);
        float dropSDMG();
        void dropSDMG(float value);
        float dyeDMG();
        void dyeDMG(float value);
        float electricPeaDMG();
        void electricPeaDMG(float value);
        float firepiercepeaDMG();
        void firepiercepeaDMG(float value);
        float firepiercepeaSDMG();
        void firepiercepeaSDMG(float value);
        float frisbeeDMG();
        void frisbeeDMG(float value);
        float flamingPeaDMGv2();
        void flamingPeaDMGv2(float value);
        float flamingPeaSDMG();
        void flamingPeaSDMG(float value);
        float fumeDMG();
        void fumeDMG(float value);
        float goldencardDMG();
        void goldencardDMG(float value);
        float hypnoprojDMGv2();
        void hypnoprojDMGv2(float value);
        float icebergDMGv2();
        void icebergDMGv2(float value);
        float icebergSDMG();
        void icebergSDMG(float value);
        float icespikeDMGv2();
        void icespikeDMGv2(float value);
        float iceSpikeMultiplier();
        void iceSpikeMultiplier(float value);
        float jingleDMGv2();
        void jingleDMGv2(float value);
        float missileToeDMGNear();
        void missileToeDMGNear(float value);
        float missileToeDMGFar();
        void missileToeDMGFar(float value);
        float missileToeDMGRangeNear();
        void missileToeDMGRangeNear(float value);
        float missileToeDMGRangeFar();
        void missileToeDMGRangeFar(float value);
        float peaDMG();
        void peaDMG(float value);
        float pepperDMGv2();
        void pepperDMGv2(float value);
        float pepperSDMG();
        void pepperSDMG(float value);
        float piercepeaDMG();
        void piercepeaDMG(float value);
        float piercesporeDMG();
        void piercesporeDMG(float value);
        float plasmaPeaDMG();
        void plasmaPeaDMG(float value);
        float rainbowBulletDMG();
        void rainbowBulletDMG(float value);
        float snowPeaDMG();
        void snowPeaDMG(float value);
        float snowQueenPeaDMGv2();
        void snowQueenPeaDMGv2(float value);
        float snowQueenPeaSDMG();
        void snowQueenPeaSDMG(float value);
        float springDMG();
        void springDMG(float value);
        float spikeDMGv2();
        void spikeDMGv2(float value);
        float spitDMG();
        void spitDMG(float value);
        float smooshProjDMG();
        void smooshProjDMG(float value);
        float sporeDMG();
        void sporeDMG(float value);
        float swordDMG();
        void swordDMG(float value);
        float basketBallDMG();
        void basketBallDMG(float value);
        float laserDMG();
        void laserDMG(float value);
        float soundwaveDMG();
        void soundwaveDMG(float value);
        float zpgDMG();
        void zpgDMG(float value);
        float rocketDMG();
        void rocketDMG(float value);
    }
    public interface PvZZombieGeneral {
        float zombieStep();
        void zombieStep(float value);
        int zombieBlockJump();
        void zombieBlockJump(int value);
    }
    public interface PvZZombieHealth {
        double zombieGraveH();
        void zombieGraveH(double value);
        double basicGraveH();
        void basicGraveH(double value);
        double nightGraveH();
        void nightGraveH(double value);
        double poolGraveH();
        void poolGraveH(double value);
        double egyptGraveH();
        void egyptGraveH(double value);
        double roofGraveH();
        void roofGraveH(double value);
        double futureGraveH();
        void futureGraveH(double value);
        double darkAgesGraveH();
        void darkAgesGraveH(double value);
        double fairytaleGraveH();
        void fairytaleGraveH(double value);
        double mausoleumGraveH();
        void mausoleumGraveH(double value);
        double backupH();
        void backupH(double value);
        double bassH();
        void bassH(double value);
        double browncoatH();
        void browncoatH(double value);
        double summerH();
        void summerH(double value);
        double mummyH();
        void mummyH(double value);
        double futureH();
        void futureH(double value);
        double peasantH();
        void peasantH(double value);
        double pokerheartH();
        void pokerheartH(double value);
        double pokerspadeH();
        void pokerspadeH(double value);
        double pokerclubH();
        void pokerclubH(double value);
        double pokerdiamondH();
        void pokerdiamondH(double value);
        double bobsledH();
        void bobsledH(double value);
        double sargeantH();
        void sargeantH(double value);
        double bullyH();
        void bullyH(double value);
        double actionheroH();
        void actionheroH(double value);
        double basketballH();
        void basketballH(double value);
        double dancingH();
        void dancingH(double value);
        double dolphinH();
        void dolphinH(double value);
        double explorerH();
        void explorerH(double value);
        double torchlightH();
        void torchlightH(double value);
        double flagH();
        void flagH(double value);
        double flagSummerH();
        void flagSummerH(double value);
        double flagMummyH();
        void flagMummyH(double value);
        double flagFuturetH();
        void flagFuturetH(double value);
        double flagPeasantH();
        void flagPeasantH(double value);
        double flagPokerH();
        void flagPokerH(double value);
        double flagSargeantH();
        void flagSargeantH(double value);
        double footballH();
        void footballH(double value);
        double berserkerH();
        void berserkerH(double value);
        double gargantuarH();
        void gargantuarH(double value);
        double mummygargantuarH();
        void mummygargantuarH(double value);
        double defensiveendH();
        void defensiveendH(double value);
        double cursedgargolithH();
        void cursedgargolithH(double value);
        double unicorngargantuarH();
        void unicorngargantuarH(double value);
        double hawkerpusherH();
        void hawkerpusherH(double value);
        double hoverGoatH();
        void hoverGoatH(double value);
        double impH();
        void impH(double value);
        double impdragonH();
        void impdragonH(double value);
        double bassimpH();
        void bassimpH(double value);
        double scrapimpH();
        void scrapimpH(double value);
        double superFanH();
        void superFanH(double value);
        double announcerH();
        void announcerH(double value);
        double jetpackH();
        void jetpackH(double value);
        double blastronautH();
        void blastronautH(double value);
        double newspaperH();
        void newspaperH(double value);
        double sundayH();
        void sundayH(double value);
        double octoH();
        void octoH(double value);
        double poleH();
        void poleH(double value);
        double pharaohH();
        void pharaohH(double value);
        double undyingPharaohH();
        void undyingPharaohH(double value);
        double pumpkincarH();
        void pumpkincarH(double value);
        double roboconeH();
        void roboconeH(double value);
        double scrapmechH();
        void scrapmechH(double value);
        double scientistH();
        void scientistH(double value);
        double snorkelH();
        void snorkelH(double value);
        double soldierH();
        void soldierH(double value);
        double tombraiserH();
        void tombraiserH(double value);
        double zombiekingH();
        void zombiekingH(double value);
        double zombiepigH();
        void zombiepigH(double value);
        double zomblobH();
        void zomblobH(double value);
        double zomblobBH();
        void zomblobBH(double value);
        double zomblobSH();
        void zomblobSH(double value);
        double zomboniH();
        void zomboniH(double value);
        double coneH();
        void coneH(double value);
        double pokerpawngearH();
        void pokerpawngearH(double value);
        double pokerknightgearH();
        void pokerknightgearH(double value);
        double pokertowergearH();
        void pokertowergearH(double value);
        double pokerbishopgearH();
        void pokerbishopgearH(double value);
        double kingpiecegearH();
        void kingpiecegearH(double value);
        double bucketH();
        void bucketH(double value);
        double medallionH();
        void medallionH(double value);
        double footballHelmH();
        void footballHelmH(double value);
        double berserkerHelmH();
        void berserkerHelmH(double value);
        double defensiveendHelmH();
        void defensiveendHelmH(double value);
        double blastronautHelmH();
        void blastronautHelmH(double value);
        double knightHelmH();
        void knightHelmH(double value);
        double sargeanthelmetH();
        void sargeanthelmetH(double value);
        double soldierhelmetH();
        void soldierhelmetH(double value);
        double brickH();
        void brickH(double value);
        double coneTowerH();
        void coneTowerH(double value);
        double pyramidH();
        void pyramidH(double value);
        double sarcophagusH();
        void sarcophagusH(double value);
        double bowlH();
        void bowlH(double value);
        double holoHelmetH();
        void holoHelmetH(double value);
        double crystalshoeHelmetH();
        void crystalshoeHelmetH(double value);
        double pumpkinH();
        void pumpkinH(double value);
        double screendoorShieldH();
        void screendoorShieldH(double value);
        double sergeantShieldH();
        void sergeantShieldH(double value);
        double newspaperShieldH();
        void newspaperShieldH(double value);
        double sundayShieldH();
        void sundayShieldH(double value);
        double bookShieldH();
        void bookShieldH(double value);
        double trashcanObstH();
        void trashcanObstH(double value);
        double basketballObstH();
        void basketballObstH(double value);
        double healstationObstH();
        void healstationObstH(double value);
        double gargolithObstH();
        void gargolithObstH(double value);
        double imptabletObstH();
        void imptabletObstH(double value);
        double egyptTombstoneH();
        void egyptTombstoneH(double value);
        double hawkerObstH();
        void hawkerObstH(double value);
        double octoObstH();
        void octoObstH(double value);
        double zomboniVH();
        void zomboniVH(double value);
        double bobsledVH();
        void bobsledVH(double value);
        double speakerVH();
        void speakerVH(double value);
    }
    public static class Keys {
        public final Option.Key nestedSpawns_spawnPlants = new Option.Key("nestedSpawns.spawnPlants");
        public final Option.Key nestedSpawns_specialZombieSpawn = new Option.Key("nestedSpawns.specialZombieSpawn");
        public final Option.Key nestedSpawns_hoeAlternative = new Option.Key("nestedSpawns.hoeAlternative");
        public final Option.Key nestedSpawns_hoeBreak = new Option.Key("nestedSpawns.hoeBreak");
        public final Option.Key nestedSpawns_nestedGraveSpawns_basicGv2 = new Option.Key("nestedSpawns.nestedGraveSpawns.basicGv2");
        public final Option.Key nestedSpawns_nestedGraveSpawns_basicGmin = new Option.Key("nestedSpawns.nestedGraveSpawns.basicGmin");
        public final Option.Key nestedSpawns_nestedGraveSpawns_basicGmax = new Option.Key("nestedSpawns.nestedGraveSpawns.basicGmax");
        public final Option.Key nestedSpawns_nestedGraveSpawns_nightGv2 = new Option.Key("nestedSpawns.nestedGraveSpawns.nightGv2");
        public final Option.Key nestedSpawns_nestedGraveSpawns_nightGmin = new Option.Key("nestedSpawns.nestedGraveSpawns.nightGmin");
        public final Option.Key nestedSpawns_nestedGraveSpawns_nightGmax = new Option.Key("nestedSpawns.nestedGraveSpawns.nightGmax");
        public final Option.Key nestedSpawns_nestedGraveSpawns_poolGv2 = new Option.Key("nestedSpawns.nestedGraveSpawns.poolGv2");
        public final Option.Key nestedSpawns_nestedGraveSpawns_poolGmin = new Option.Key("nestedSpawns.nestedGraveSpawns.poolGmin");
        public final Option.Key nestedSpawns_nestedGraveSpawns_poolGmax = new Option.Key("nestedSpawns.nestedGraveSpawns.poolGmax");
        public final Option.Key nestedSpawns_nestedGraveSpawns_roofGv2 = new Option.Key("nestedSpawns.nestedGraveSpawns.roofGv2");
        public final Option.Key nestedSpawns_nestedGraveSpawns_roofGmin = new Option.Key("nestedSpawns.nestedGraveSpawns.roofGmin");
        public final Option.Key nestedSpawns_nestedGraveSpawns_roofGmax = new Option.Key("nestedSpawns.nestedGraveSpawns.roofGmax");
        public final Option.Key nestedSpawns_nestedGraveSpawns_egyptG = new Option.Key("nestedSpawns.nestedGraveSpawns.egyptG");
        public final Option.Key nestedSpawns_nestedGraveSpawns_egyptGmin = new Option.Key("nestedSpawns.nestedGraveSpawns.egyptGmin");
        public final Option.Key nestedSpawns_nestedGraveSpawns_egyptGmax = new Option.Key("nestedSpawns.nestedGraveSpawns.egyptGmax");
        public final Option.Key nestedSpawns_nestedGraveSpawns_futureGv2 = new Option.Key("nestedSpawns.nestedGraveSpawns.futureGv2");
        public final Option.Key nestedSpawns_nestedGraveSpawns_futureGmin = new Option.Key("nestedSpawns.nestedGraveSpawns.futureGmin");
        public final Option.Key nestedSpawns_nestedGraveSpawns_futureGmax = new Option.Key("nestedSpawns.nestedGraveSpawns.futureGmax");
        public final Option.Key nestedSpawns_nestedGraveSpawns_darkagesGv2 = new Option.Key("nestedSpawns.nestedGraveSpawns.darkagesGv2");
        public final Option.Key nestedSpawns_nestedGraveSpawns_darkagesGmin = new Option.Key("nestedSpawns.nestedGraveSpawns.darkagesGmin");
        public final Option.Key nestedSpawns_nestedGraveSpawns_darkagesGmax = new Option.Key("nestedSpawns.nestedGraveSpawns.darkagesGmax");
        public final Option.Key nestedSpawns_nestedGraveSpawns_mausoleumG = new Option.Key("nestedSpawns.nestedGraveSpawns.mausoleumG");
        public final Option.Key nestedSpawns_nestedGraveSpawns_mausoleumGmin = new Option.Key("nestedSpawns.nestedGraveSpawns.mausoleumGmin");
        public final Option.Key nestedSpawns_nestedGraveSpawns_mausoleumGmax = new Option.Key("nestedSpawns.nestedGraveSpawns.mausoleumGmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_peashooterSP = new Option.Key("nestedSpawns.nestedPlantSpawns.peashooterSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_peashooterSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.peashooterSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_peashooterSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.peashooterSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_bellflowerSP = new Option.Key("nestedSpawns.nestedPlantSpawns.bellflowerSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_bellflowerSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.bellflowerSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_bellflowerSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.bellflowerSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_puffshroomSP = new Option.Key("nestedSpawns.nestedPlantSpawns.puffshroomSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_puffshroomSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.puffshroomSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_puffshroomSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.puffshroomSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_shadowshroomSP = new Option.Key("nestedSpawns.nestedPlantSpawns.shadowshroomSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_shadowshroomSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.shadowshroomSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_shadowshroomSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.shadowshroomSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_weeniebeanieSP = new Option.Key("nestedSpawns.nestedPlantSpawns.weeniebeanieSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_weeniebeanieSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.weeniebeanieSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_weeniebeanieSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.weeniebeanieSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_sunflowerseedSPv2 = new Option.Key("nestedSpawns.nestedPlantSpawns.sunflowerseedSPv2");
        public final Option.Key nestedSpawns_nestedPlantSpawns_sunflowerseedSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.sunflowerseedSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_sunflowerseedSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.sunflowerseedSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_lilypadSP = new Option.Key("nestedSpawns.nestedPlantSpawns.lilypadSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_lilypadSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.lilypadSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_lilypadSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.lilypadSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_bombseedlingSP = new Option.Key("nestedSpawns.nestedPlantSpawns.bombseedlingSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_bombseedlingSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.bombseedlingSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_bombseedlingSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.bombseedlingSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_smallnutSP = new Option.Key("nestedSpawns.nestedPlantSpawns.smallnutSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_smallnutSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.smallnutSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_smallnutSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.smallnutSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_loquatSP = new Option.Key("nestedSpawns.nestedPlantSpawns.loquatSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_loquatSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.loquatSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_loquatSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.loquatSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_icebergSP = new Option.Key("nestedSpawns.nestedPlantSpawns.icebergSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_icebergSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.icebergSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_icebergSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.icebergSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_zapricotSP = new Option.Key("nestedSpawns.nestedPlantSpawns.zapricotSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_zapricotSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.zapricotSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_zapricotSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.zapricotSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_buttonshroomSP = new Option.Key("nestedSpawns.nestedPlantSpawns.buttonshroomSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_buttonshroomSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.buttonshroomSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_buttonshroomSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.buttonshroomSPmax");
        public final Option.Key nestedSpawns_nestedPlantSpawns_solarwindSP = new Option.Key("nestedSpawns.nestedPlantSpawns.solarwindSP");
        public final Option.Key nestedSpawns_nestedPlantSpawns_solarwindSPmin = new Option.Key("nestedSpawns.nestedPlantSpawns.solarwindSPmin");
        public final Option.Key nestedSpawns_nestedPlantSpawns_solarwindSPmax = new Option.Key("nestedSpawns.nestedPlantSpawns.solarwindSPmax");
        public final Option.Key nestedSeeds_infiniteSeeds = new Option.Key("nestedSeeds.infiniteSeeds");
        public final Option.Key nestedSeeds_instantRecharge = new Option.Key("nestedSeeds.instantRecharge");
        public final Option.Key nestedSeeds_moreSeeds_acidshrooomS = new Option.Key("nestedSeeds.moreSeeds.acidshrooomS");
        public final Option.Key nestedSeeds_moreSeeds_admiralnavybeanS = new Option.Key("nestedSeeds.moreSeeds.admiralnavybeanS");
        public final Option.Key nestedSeeds_moreSeeds_bananasaurusS = new Option.Key("nestedSeeds.moreSeeds.bananasaurusS");
        public final Option.Key nestedSeeds_moreSeeds_beautyshroomS = new Option.Key("nestedSeeds.moreSeeds.beautyshroomS");
        public final Option.Key nestedSeeds_moreSeeds_beeshooterS = new Option.Key("nestedSeeds.moreSeeds.beeshooterS");
        public final Option.Key nestedSeeds_moreSeeds_beetS = new Option.Key("nestedSeeds.moreSeeds.beetS");
        public final Option.Key nestedSeeds_moreSeeds_bellflowerS = new Option.Key("nestedSeeds.moreSeeds.bellflowerS");
        public final Option.Key nestedSeeds_moreSeeds_bloomerangS = new Option.Key("nestedSeeds.moreSeeds.bloomerangS");
        public final Option.Key nestedSeeds_moreSeeds_bombseedlingS = new Option.Key("nestedSeeds.moreSeeds.bombseedlingS");
        public final Option.Key nestedSeeds_moreSeeds_breezesroomS = new Option.Key("nestedSeeds.moreSeeds.breezesroomS");
        public final Option.Key nestedSeeds_moreSeeds_burstshroomS = new Option.Key("nestedSeeds.moreSeeds.burstshroomS");
        public final Option.Key nestedSeeds_moreSeeds_buttonshroomS = new Option.Key("nestedSeeds.moreSeeds.buttonshroomS");
        public final Option.Key nestedSeeds_moreSeeds_cabbagepultS = new Option.Key("nestedSeeds.moreSeeds.cabbagepultS");
        public final Option.Key nestedSeeds_moreSeeds_kernalpultS = new Option.Key("nestedSeeds.moreSeeds.kernalpultS");
        public final Option.Key nestedSeeds_moreSeeds_melonpultS = new Option.Key("nestedSeeds.moreSeeds.melonpultS");
        public final Option.Key nestedSeeds_moreSeeds_melonsliceS = new Option.Key("nestedSeeds.moreSeeds.melonsliceS");
        public final Option.Key nestedSeeds_moreSeeds_WintermelonS = new Option.Key("nestedSeeds.moreSeeds.WintermelonS");
        public final Option.Key nestedSeeds_moreSeeds_cattailS = new Option.Key("nestedSeeds.moreSeeds.cattailS");
        public final Option.Key nestedSeeds_moreSeeds_coffeeBeanS = new Option.Key("nestedSeeds.moreSeeds.coffeeBeanS");
        public final Option.Key nestedSeeds_moreSeeds_charmshroomS = new Option.Key("nestedSeeds.moreSeeds.charmshroomS");
        public final Option.Key nestedSeeds_moreSeeds_cherrybombS = new Option.Key("nestedSeeds.moreSeeds.cherrybombS");
        public final Option.Key nestedSeeds_moreSeeds_chesterS = new Option.Key("nestedSeeds.moreSeeds.chesterS");
        public final Option.Key nestedSeeds_moreSeeds_chillypepperS = new Option.Key("nestedSeeds.moreSeeds.chillypepperS");
        public final Option.Key nestedSeeds_moreSeeds_chomperS = new Option.Key("nestedSeeds.moreSeeds.chomperS");
        public final Option.Key nestedSeeds_moreSeeds_coconutS = new Option.Key("nestedSeeds.moreSeeds.coconutS");
        public final Option.Key nestedSeeds_moreSeeds_dandelionweedS = new Option.Key("nestedSeeds.moreSeeds.dandelionweedS");
        public final Option.Key nestedSeeds_moreSeeds_dogwoodS = new Option.Key("nestedSeeds.moreSeeds.dogwoodS");
        public final Option.Key nestedSeeds_moreSeeds_doomroseS = new Option.Key("nestedSeeds.moreSeeds.doomroseS");
        public final Option.Key nestedSeeds_moreSeeds_doomshroomS = new Option.Key("nestedSeeds.moreSeeds.doomshroomS");
        public final Option.Key nestedSeeds_moreSeeds_dripphylleiaS = new Option.Key("nestedSeeds.moreSeeds.dripphylleiaS");
        public final Option.Key nestedSeeds_moreSeeds_dropeaS = new Option.Key("nestedSeeds.moreSeeds.dropeaS");
        public final Option.Key nestedSeeds_moreSeeds_electropeaS = new Option.Key("nestedSeeds.moreSeeds.electropeaS");
        public final Option.Key nestedSeeds_moreSeeds_empeachS = new Option.Key("nestedSeeds.moreSeeds.empeachS");
        public final Option.Key nestedSeeds_moreSeeds_endurianS = new Option.Key("nestedSeeds.moreSeeds.endurianS");
        public final Option.Key nestedSeeds_moreSeeds_firepeaS = new Option.Key("nestedSeeds.moreSeeds.firepeaS");
        public final Option.Key nestedSeeds_moreSeeds_frisbloomS = new Option.Key("nestedSeeds.moreSeeds.frisbloomS");
        public final Option.Key nestedSeeds_moreSeeds_fumeshroomS = new Option.Key("nestedSeeds.moreSeeds.fumeshroomS");
        public final Option.Key nestedSeeds_moreSeeds_gambleshroomS = new Option.Key("nestedSeeds.moreSeeds.gambleshroomS");
        public final Option.Key nestedSeeds_moreSeeds_gatlingpeaS = new Option.Key("nestedSeeds.moreSeeds.gatlingpeaS");
        public final Option.Key nestedSeeds_moreSeeds_ghostpepperS = new Option.Key("nestedSeeds.moreSeeds.ghostpepperS");
        public final Option.Key nestedSeeds_moreSeeds_gloomvineS = new Option.Key("nestedSeeds.moreSeeds.gloomvineS");
        public final Option.Key nestedSeeds_moreSeeds_gloomshroomS = new Option.Key("nestedSeeds.moreSeeds.gloomshroomS");
        public final Option.Key nestedSeeds_moreSeeds_goldleafS = new Option.Key("nestedSeeds.moreSeeds.goldleafS");
        public final Option.Key nestedSeeds_moreSeeds_gravebusterS = new Option.Key("nestedSeeds.moreSeeds.gravebusterS");
        public final Option.Key nestedSeeds_moreSeeds_hammerflowerS = new Option.Key("nestedSeeds.moreSeeds.hammerflowerS");
        public final Option.Key nestedSeeds_moreSeeds_heavenlypeachS = new Option.Key("nestedSeeds.moreSeeds.heavenlypeachS");
        public final Option.Key nestedSeeds_moreSeeds_hypnoshroomS = new Option.Key("nestedSeeds.moreSeeds.hypnoshroomS");
        public final Option.Key nestedSeeds_moreSeeds_iceberglettuceS = new Option.Key("nestedSeeds.moreSeeds.iceberglettuceS");
        public final Option.Key nestedSeeds_moreSeeds_icebergpultS = new Option.Key("nestedSeeds.moreSeeds.icebergpultS");
        public final Option.Key nestedSeeds_moreSeeds_iceshroomS = new Option.Key("nestedSeeds.moreSeeds.iceshroomS");
        public final Option.Key nestedSeeds_moreSeeds_impatyensS = new Option.Key("nestedSeeds.moreSeeds.impatyensS");
        public final Option.Key nestedSeeds_moreSeeds_jalapenoS = new Option.Key("nestedSeeds.moreSeeds.jalapenoS");
        public final Option.Key nestedSeeds_moreSeeds_jumpingbeanS = new Option.Key("nestedSeeds.moreSeeds.jumpingbeanS");
        public final Option.Key nestedSeeds_moreSeeds_knightpeaS = new Option.Key("nestedSeeds.moreSeeds.knightpeaS");
        public final Option.Key nestedSeeds_moreSeeds_lightningreedS = new Option.Key("nestedSeeds.moreSeeds.lightningreedS");
        public final Option.Key nestedSeeds_moreSeeds_lilypadS = new Option.Key("nestedSeeds.moreSeeds.lilypadS");
        public final Option.Key nestedSeeds_moreSeeds_loquatS = new Option.Key("nestedSeeds.moreSeeds.loquatS");
        public final Option.Key nestedSeeds_moreSeeds_locococoS = new Option.Key("nestedSeeds.moreSeeds.locococoS");
        public final Option.Key nestedSeeds_moreSeeds_magicshroomS = new Option.Key("nestedSeeds.moreSeeds.magicshroomS");
        public final Option.Key nestedSeeds_moreSeeds_magnetshroomS = new Option.Key("nestedSeeds.moreSeeds.magnetshroomS");
        public final Option.Key nestedSeeds_moreSeeds_magnetoshroomS = new Option.Key("nestedSeeds.moreSeeds.magnetoshroomS");
        public final Option.Key nestedSeeds_moreSeeds_meteorhammerS = new Option.Key("nestedSeeds.moreSeeds.meteorhammerS");
        public final Option.Key nestedSeeds_moreSeeds_missileToeS = new Option.Key("nestedSeeds.moreSeeds.missileToeS");
        public final Option.Key nestedSeeds_moreSeeds_narcissusS = new Option.Key("nestedSeeds.moreSeeds.narcissusS");
        public final Option.Key nestedSeeds_moreSeeds_navybeanS = new Option.Key("nestedSeeds.moreSeeds.navybeanS");
        public final Option.Key nestedSeeds_moreSeeds_nightcapS = new Option.Key("nestedSeeds.moreSeeds.nightcapS");
        public final Option.Key nestedSeeds_moreSeeds_oilyOliveS = new Option.Key("nestedSeeds.moreSeeds.oilyOliveS");
        public final Option.Key nestedSeeds_moreSeeds_olivepitS = new Option.Key("nestedSeeds.moreSeeds.olivepitS");
        public final Option.Key nestedSeeds_moreSeeds_oxygaeS = new Option.Key("nestedSeeds.moreSeeds.oxygaeS");
        public final Option.Key nestedSeeds_moreSeeds_peanutS = new Option.Key("nestedSeeds.moreSeeds.peanutS");
        public final Option.Key nestedSeeds_moreSeeds_peapodS = new Option.Key("nestedSeeds.moreSeeds.peapodS");
        public final Option.Key nestedSeeds_moreSeeds_peashooterS = new Option.Key("nestedSeeds.moreSeeds.peashooterS");
        public final Option.Key nestedSeeds_moreSeeds_pepperpultS = new Option.Key("nestedSeeds.moreSeeds.pepperpultS");
        public final Option.Key nestedSeeds_moreSeeds_perfoomshroomS = new Option.Key("nestedSeeds.moreSeeds.perfoomshroomS");
        public final Option.Key nestedSeeds_moreSeeds_potatomineS = new Option.Key("nestedSeeds.moreSeeds.potatomineS");
        public final Option.Key nestedSeeds_moreSeeds_puffshroomS = new Option.Key("nestedSeeds.moreSeeds.puffshroomS");
        public final Option.Key nestedSeeds_moreSeeds_pumpkinwitchS = new Option.Key("nestedSeeds.moreSeeds.pumpkinwitchS");
        public final Option.Key nestedSeeds_moreSeeds_repeaterS = new Option.Key("nestedSeeds.moreSeeds.repeaterS");
        public final Option.Key nestedSeeds_moreSeeds_retrogatlingS = new Option.Key("nestedSeeds.moreSeeds.retrogatlingS");
        public final Option.Key nestedSeeds_moreSeeds_saucerS = new Option.Key("nestedSeeds.moreSeeds.saucerS");
        public final Option.Key nestedSeeds_moreSeeds_scaredyshroomS = new Option.Key("nestedSeeds.moreSeeds.scaredyshroomS");
        public final Option.Key nestedSeeds_moreSeeds_seapeaS = new Option.Key("nestedSeeds.moreSeeds.seapeaS");
        public final Option.Key nestedSeeds_moreSeeds_seashroomS = new Option.Key("nestedSeeds.moreSeeds.seashroomS");
        public final Option.Key nestedSeeds_moreSeeds_shadowShroomS = new Option.Key("nestedSeeds.moreSeeds.shadowShroomS");
        public final Option.Key nestedSeeds_moreSeeds_shamrockS = new Option.Key("nestedSeeds.moreSeeds.shamrockS");
        public final Option.Key nestedSeeds_moreSeeds_smackadamiaSv2 = new Option.Key("nestedSeeds.moreSeeds.smackadamiaSv2");
        public final Option.Key nestedSeeds_moreSeeds_smallnutS = new Option.Key("nestedSeeds.moreSeeds.smallnutS");
        public final Option.Key nestedSeeds_moreSeeds_smooshroomS = new Option.Key("nestedSeeds.moreSeeds.smooshroomS");
        public final Option.Key nestedSeeds_moreSeeds_snowpeaS = new Option.Key("nestedSeeds.moreSeeds.snowpeaS");
        public final Option.Key nestedSeeds_moreSeeds_snowqueenpeaS = new Option.Key("nestedSeeds.moreSeeds.snowqueenpeaS");
        public final Option.Key nestedSeeds_moreSeeds_spikerockS = new Option.Key("nestedSeeds.moreSeeds.spikerockS");
        public final Option.Key nestedSeeds_moreSeeds_spikeweedS = new Option.Key("nestedSeeds.moreSeeds.spikeweedS");
        public final Option.Key nestedSeeds_moreSeeds_springbeanS = new Option.Key("nestedSeeds.moreSeeds.springbeanS");
        public final Option.Key nestedSeeds_moreSeeds_springprincessS = new Option.Key("nestedSeeds.moreSeeds.springprincessS");
        public final Option.Key nestedSeeds_moreSeeds_squashS = new Option.Key("nestedSeeds.moreSeeds.squashS");
        public final Option.Key nestedSeeds_moreSeeds_sunflowerS = new Option.Key("nestedSeeds.moreSeeds.sunflowerS");
        public final Option.Key nestedSeeds_moreSeeds_sunflowerseedS = new Option.Key("nestedSeeds.moreSeeds.sunflowerseedS");
        public final Option.Key nestedSeeds_moreSeeds_sunshroomS = new Option.Key("nestedSeeds.moreSeeds.sunshroomS");
        public final Option.Key nestedSeeds_moreSeeds_superchomperS = new Option.Key("nestedSeeds.moreSeeds.superchomperS");
        public final Option.Key nestedSeeds_moreSeeds_tallnutSv2 = new Option.Key("nestedSeeds.moreSeeds.tallnutSv2");
        public final Option.Key nestedSeeds_moreSeeds_tanglekelpS = new Option.Key("nestedSeeds.moreSeeds.tanglekelpS");
        public final Option.Key nestedSeeds_moreSeeds_threepeaterS = new Option.Key("nestedSeeds.moreSeeds.threepeaterS");
        public final Option.Key nestedSeeds_moreSeeds_torchwoodS = new Option.Key("nestedSeeds.moreSeeds.torchwoodS");
        public final Option.Key nestedSeeds_moreSeeds_tulimpeterS = new Option.Key("nestedSeeds.moreSeeds.tulimpeterS");
        public final Option.Key nestedSeeds_moreSeeds_vampireflowerS = new Option.Key("nestedSeeds.moreSeeds.vampireflowerS");
        public final Option.Key nestedSeeds_moreSeeds_twinsunflowerS = new Option.Key("nestedSeeds.moreSeeds.twinsunflowerS");
        public final Option.Key nestedSeeds_moreSeeds_wintermelonS = new Option.Key("nestedSeeds.moreSeeds.wintermelonS");
        public final Option.Key nestedSeeds_moreSeeds_wallnutSv2 = new Option.Key("nestedSeeds.moreSeeds.wallnutSv2");
        public final Option.Key nestedSeeds_moreSeeds_weeniebeanieS = new Option.Key("nestedSeeds.moreSeeds.weeniebeanieS");
        public final Option.Key nestedSeeds_moreSeeds_zapricotS = new Option.Key("nestedSeeds.moreSeeds.zapricotS");
        public final Option.Key nestedSun_sunflowerSec = new Option.Key("nestedSun.sunflowerSec");
        public final Option.Key nestedSun_sunflowerSecInitial = new Option.Key("nestedSun.sunflowerSecInitial");
        public final Option.Key nestedSun_sunflowerDropSun = new Option.Key("nestedSun.sunflowerDropSun");
        public final Option.Key nestedSun_twinSunflowerSec = new Option.Key("nestedSun.twinSunflowerSec");
        public final Option.Key nestedSun_sunshroomSec = new Option.Key("nestedSun.sunshroomSec");
        public final Option.Key nestedSun_sunshroomSecInitial = new Option.Key("nestedSun.sunshroomSecInitial");
        public final Option.Key nestedSun_sunshroomSunChance = new Option.Key("nestedSun.sunshroomSunChance");
        public final Option.Key nestedSun_sunshroomSun2ndChance = new Option.Key("nestedSun.sunshroomSun2ndChance");
        public final Option.Key nestedSun_goldtileSec = new Option.Key("nestedSun.goldtileSec");
        public final Option.Key nestedSun_sunseedSec = new Option.Key("nestedSun.sunseedSec");
        public final Option.Key nestedSun_zombiegraveSec = new Option.Key("nestedSun.zombiegraveSec");
        public final Option.Key nestedProjDMG_acidFumeDMG = new Option.Key("nestedProjDMG.acidFumeDMG");
        public final Option.Key nestedProjDMG_acidSporeDMG = new Option.Key("nestedProjDMG.acidSporeDMG");
        public final Option.Key nestedProjDMG_armorBubbleDMG = new Option.Key("nestedProjDMG.armorBubbleDMG");
        public final Option.Key nestedProjDMG_beespikeDMGv2 = new Option.Key("nestedProjDMG.beespikeDMGv2");
        public final Option.Key nestedProjDMG_breezeDMG = new Option.Key("nestedProjDMG.breezeDMG");
        public final Option.Key nestedProjDMG_boomerangDMGv2 = new Option.Key("nestedProjDMG.boomerangDMGv2");
        public final Option.Key nestedProjDMG_bubblesDMG = new Option.Key("nestedProjDMG.bubblesDMG");
        public final Option.Key nestedProjDMG_cabbageDMG = new Option.Key("nestedProjDMG.cabbageDMG");
        public final Option.Key nestedProjDMG_kernalDMG = new Option.Key("nestedProjDMG.kernalDMG");
        public final Option.Key nestedProjDMG_butterDMG = new Option.Key("nestedProjDMG.butterDMG");
        public final Option.Key nestedProjDMG_melonDMG = new Option.Key("nestedProjDMG.melonDMG");
        public final Option.Key nestedProjDMG_melonSDMG = new Option.Key("nestedProjDMG.melonSDMG");
        public final Option.Key nestedProjDMG_melonsliceDMG = new Option.Key("nestedProjDMG.melonsliceDMG");
        public final Option.Key nestedProjDMG_melonsliceSDMG = new Option.Key("nestedProjDMG.melonsliceSDMG");
        public final Option.Key nestedProjDMG_wintermelonDMG = new Option.Key("nestedProjDMG.wintermelonDMG");
        public final Option.Key nestedProjDMG_wintermelonSDMG = new Option.Key("nestedProjDMG.wintermelonSDMG");
        public final Option.Key nestedProjDMG_cardDMGv2 = new Option.Key("nestedProjDMG.cardDMGv2");
        public final Option.Key nestedProjDMG_coconutDMGv2 = new Option.Key("nestedProjDMG.coconutDMGv2");
        public final Option.Key nestedProjDMG_coconutSDMG = new Option.Key("nestedProjDMG.coconutSDMG");
        public final Option.Key nestedProjDMG_dropDMGv2 = new Option.Key("nestedProjDMG.dropDMGv2");
        public final Option.Key nestedProjDMG_dropSDMG = new Option.Key("nestedProjDMG.dropSDMG");
        public final Option.Key nestedProjDMG_dyeDMG = new Option.Key("nestedProjDMG.dyeDMG");
        public final Option.Key nestedProjDMG_electricPeaDMG = new Option.Key("nestedProjDMG.electricPeaDMG");
        public final Option.Key nestedProjDMG_firepiercepeaDMG = new Option.Key("nestedProjDMG.firepiercepeaDMG");
        public final Option.Key nestedProjDMG_firepiercepeaSDMG = new Option.Key("nestedProjDMG.firepiercepeaSDMG");
        public final Option.Key nestedProjDMG_frisbeeDMG = new Option.Key("nestedProjDMG.frisbeeDMG");
        public final Option.Key nestedProjDMG_flamingPeaDMGv2 = new Option.Key("nestedProjDMG.flamingPeaDMGv2");
        public final Option.Key nestedProjDMG_flamingPeaSDMG = new Option.Key("nestedProjDMG.flamingPeaSDMG");
        public final Option.Key nestedProjDMG_fumeDMG = new Option.Key("nestedProjDMG.fumeDMG");
        public final Option.Key nestedProjDMG_goldencardDMG = new Option.Key("nestedProjDMG.goldencardDMG");
        public final Option.Key nestedProjDMG_hypnoprojDMGv2 = new Option.Key("nestedProjDMG.hypnoprojDMGv2");
        public final Option.Key nestedProjDMG_icebergDMGv2 = new Option.Key("nestedProjDMG.icebergDMGv2");
        public final Option.Key nestedProjDMG_icebergSDMG = new Option.Key("nestedProjDMG.icebergSDMG");
        public final Option.Key nestedProjDMG_icespikeDMGv2 = new Option.Key("nestedProjDMG.icespikeDMGv2");
        public final Option.Key nestedProjDMG_iceSpikeMultiplier = new Option.Key("nestedProjDMG.iceSpikeMultiplier");
        public final Option.Key nestedProjDMG_jingleDMGv2 = new Option.Key("nestedProjDMG.jingleDMGv2");
        public final Option.Key nestedProjDMG_missileToeDMGNear = new Option.Key("nestedProjDMG.missileToeDMGNear");
        public final Option.Key nestedProjDMG_missileToeDMGFar = new Option.Key("nestedProjDMG.missileToeDMGFar");
        public final Option.Key nestedProjDMG_missileToeDMGRangeNear = new Option.Key("nestedProjDMG.missileToeDMGRangeNear");
        public final Option.Key nestedProjDMG_missileToeDMGRangeFar = new Option.Key("nestedProjDMG.missileToeDMGRangeFar");
        public final Option.Key nestedProjDMG_peaDMG = new Option.Key("nestedProjDMG.peaDMG");
        public final Option.Key nestedProjDMG_pepperDMGv2 = new Option.Key("nestedProjDMG.pepperDMGv2");
        public final Option.Key nestedProjDMG_pepperSDMG = new Option.Key("nestedProjDMG.pepperSDMG");
        public final Option.Key nestedProjDMG_piercepeaDMG = new Option.Key("nestedProjDMG.piercepeaDMG");
        public final Option.Key nestedProjDMG_piercesporeDMG = new Option.Key("nestedProjDMG.piercesporeDMG");
        public final Option.Key nestedProjDMG_plasmaPeaDMG = new Option.Key("nestedProjDMG.plasmaPeaDMG");
        public final Option.Key nestedProjDMG_rainbowBulletDMG = new Option.Key("nestedProjDMG.rainbowBulletDMG");
        public final Option.Key nestedProjDMG_snowPeaDMG = new Option.Key("nestedProjDMG.snowPeaDMG");
        public final Option.Key nestedProjDMG_snowQueenPeaDMGv2 = new Option.Key("nestedProjDMG.snowQueenPeaDMGv2");
        public final Option.Key nestedProjDMG_snowQueenPeaSDMG = new Option.Key("nestedProjDMG.snowQueenPeaSDMG");
        public final Option.Key nestedProjDMG_springDMG = new Option.Key("nestedProjDMG.springDMG");
        public final Option.Key nestedProjDMG_spikeDMGv2 = new Option.Key("nestedProjDMG.spikeDMGv2");
        public final Option.Key nestedProjDMG_spitDMG = new Option.Key("nestedProjDMG.spitDMG");
        public final Option.Key nestedProjDMG_smooshProjDMG = new Option.Key("nestedProjDMG.smooshProjDMG");
        public final Option.Key nestedProjDMG_sporeDMG = new Option.Key("nestedProjDMG.sporeDMG");
        public final Option.Key nestedProjDMG_swordDMG = new Option.Key("nestedProjDMG.swordDMG");
        public final Option.Key nestedProjDMG_basketBallDMG = new Option.Key("nestedProjDMG.basketBallDMG");
        public final Option.Key nestedProjDMG_laserDMG = new Option.Key("nestedProjDMG.laserDMG");
        public final Option.Key nestedProjDMG_soundwaveDMG = new Option.Key("nestedProjDMG.soundwaveDMG");
        public final Option.Key nestedProjDMG_zpgDMG = new Option.Key("nestedProjDMG.zpgDMG");
        public final Option.Key nestedProjDMG_rocketDMG = new Option.Key("nestedProjDMG.rocketDMG");
        public final Option.Key nestedGeneralZombie_zombieStep = new Option.Key("nestedGeneralZombie.zombieStep");
        public final Option.Key nestedGeneralZombie_zombieBlockJump = new Option.Key("nestedGeneralZombie.zombieBlockJump");
        public final Option.Key nestedZombieHealth_zombieGraveH = new Option.Key("nestedZombieHealth.zombieGraveH");
        public final Option.Key nestedZombieHealth_basicGraveH = new Option.Key("nestedZombieHealth.basicGraveH");
        public final Option.Key nestedZombieHealth_nightGraveH = new Option.Key("nestedZombieHealth.nightGraveH");
        public final Option.Key nestedZombieHealth_poolGraveH = new Option.Key("nestedZombieHealth.poolGraveH");
        public final Option.Key nestedZombieHealth_egyptGraveH = new Option.Key("nestedZombieHealth.egyptGraveH");
        public final Option.Key nestedZombieHealth_roofGraveH = new Option.Key("nestedZombieHealth.roofGraveH");
        public final Option.Key nestedZombieHealth_futureGraveH = new Option.Key("nestedZombieHealth.futureGraveH");
        public final Option.Key nestedZombieHealth_darkAgesGraveH = new Option.Key("nestedZombieHealth.darkAgesGraveH");
        public final Option.Key nestedZombieHealth_fairytaleGraveH = new Option.Key("nestedZombieHealth.fairytaleGraveH");
        public final Option.Key nestedZombieHealth_mausoleumGraveH = new Option.Key("nestedZombieHealth.mausoleumGraveH");
        public final Option.Key nestedZombieHealth_backupH = new Option.Key("nestedZombieHealth.backupH");
        public final Option.Key nestedZombieHealth_bassH = new Option.Key("nestedZombieHealth.bassH");
        public final Option.Key nestedZombieHealth_browncoatH = new Option.Key("nestedZombieHealth.browncoatH");
        public final Option.Key nestedZombieHealth_summerH = new Option.Key("nestedZombieHealth.summerH");
        public final Option.Key nestedZombieHealth_mummyH = new Option.Key("nestedZombieHealth.mummyH");
        public final Option.Key nestedZombieHealth_futureH = new Option.Key("nestedZombieHealth.futureH");
        public final Option.Key nestedZombieHealth_peasantH = new Option.Key("nestedZombieHealth.peasantH");
        public final Option.Key nestedZombieHealth_pokerheartH = new Option.Key("nestedZombieHealth.pokerheartH");
        public final Option.Key nestedZombieHealth_pokerspadeH = new Option.Key("nestedZombieHealth.pokerspadeH");
        public final Option.Key nestedZombieHealth_pokerclubH = new Option.Key("nestedZombieHealth.pokerclubH");
        public final Option.Key nestedZombieHealth_pokerdiamondH = new Option.Key("nestedZombieHealth.pokerdiamondH");
        public final Option.Key nestedZombieHealth_bobsledH = new Option.Key("nestedZombieHealth.bobsledH");
        public final Option.Key nestedZombieHealth_sargeantH = new Option.Key("nestedZombieHealth.sargeantH");
        public final Option.Key nestedZombieHealth_bullyH = new Option.Key("nestedZombieHealth.bullyH");
        public final Option.Key nestedZombieHealth_actionheroH = new Option.Key("nestedZombieHealth.actionheroH");
        public final Option.Key nestedZombieHealth_basketballH = new Option.Key("nestedZombieHealth.basketballH");
        public final Option.Key nestedZombieHealth_dancingH = new Option.Key("nestedZombieHealth.dancingH");
        public final Option.Key nestedZombieHealth_dolphinH = new Option.Key("nestedZombieHealth.dolphinH");
        public final Option.Key nestedZombieHealth_explorerH = new Option.Key("nestedZombieHealth.explorerH");
        public final Option.Key nestedZombieHealth_torchlightH = new Option.Key("nestedZombieHealth.torchlightH");
        public final Option.Key nestedZombieHealth_flagH = new Option.Key("nestedZombieHealth.flagH");
        public final Option.Key nestedZombieHealth_flagSummerH = new Option.Key("nestedZombieHealth.flagSummerH");
        public final Option.Key nestedZombieHealth_flagMummyH = new Option.Key("nestedZombieHealth.flagMummyH");
        public final Option.Key nestedZombieHealth_flagFuturetH = new Option.Key("nestedZombieHealth.flagFuturetH");
        public final Option.Key nestedZombieHealth_flagPeasantH = new Option.Key("nestedZombieHealth.flagPeasantH");
        public final Option.Key nestedZombieHealth_flagPokerH = new Option.Key("nestedZombieHealth.flagPokerH");
        public final Option.Key nestedZombieHealth_flagSargeantH = new Option.Key("nestedZombieHealth.flagSargeantH");
        public final Option.Key nestedZombieHealth_footballH = new Option.Key("nestedZombieHealth.footballH");
        public final Option.Key nestedZombieHealth_berserkerH = new Option.Key("nestedZombieHealth.berserkerH");
        public final Option.Key nestedZombieHealth_gargantuarH = new Option.Key("nestedZombieHealth.gargantuarH");
        public final Option.Key nestedZombieHealth_mummygargantuarH = new Option.Key("nestedZombieHealth.mummygargantuarH");
        public final Option.Key nestedZombieHealth_defensiveendH = new Option.Key("nestedZombieHealth.defensiveendH");
        public final Option.Key nestedZombieHealth_cursedgargolithH = new Option.Key("nestedZombieHealth.cursedgargolithH");
        public final Option.Key nestedZombieHealth_unicorngargantuarH = new Option.Key("nestedZombieHealth.unicorngargantuarH");
        public final Option.Key nestedZombieHealth_hawkerpusherH = new Option.Key("nestedZombieHealth.hawkerpusherH");
        public final Option.Key nestedZombieHealth_hoverGoatH = new Option.Key("nestedZombieHealth.hoverGoatH");
        public final Option.Key nestedZombieHealth_impH = new Option.Key("nestedZombieHealth.impH");
        public final Option.Key nestedZombieHealth_impdragonH = new Option.Key("nestedZombieHealth.impdragonH");
        public final Option.Key nestedZombieHealth_bassimpH = new Option.Key("nestedZombieHealth.bassimpH");
        public final Option.Key nestedZombieHealth_scrapimpH = new Option.Key("nestedZombieHealth.scrapimpH");
        public final Option.Key nestedZombieHealth_superFanH = new Option.Key("nestedZombieHealth.superFanH");
        public final Option.Key nestedZombieHealth_announcerH = new Option.Key("nestedZombieHealth.announcerH");
        public final Option.Key nestedZombieHealth_jetpackH = new Option.Key("nestedZombieHealth.jetpackH");
        public final Option.Key nestedZombieHealth_blastronautH = new Option.Key("nestedZombieHealth.blastronautH");
        public final Option.Key nestedZombieHealth_newspaperH = new Option.Key("nestedZombieHealth.newspaperH");
        public final Option.Key nestedZombieHealth_sundayH = new Option.Key("nestedZombieHealth.sundayH");
        public final Option.Key nestedZombieHealth_octoH = new Option.Key("nestedZombieHealth.octoH");
        public final Option.Key nestedZombieHealth_poleH = new Option.Key("nestedZombieHealth.poleH");
        public final Option.Key nestedZombieHealth_pharaohH = new Option.Key("nestedZombieHealth.pharaohH");
        public final Option.Key nestedZombieHealth_undyingPharaohH = new Option.Key("nestedZombieHealth.undyingPharaohH");
        public final Option.Key nestedZombieHealth_pumpkincarH = new Option.Key("nestedZombieHealth.pumpkincarH");
        public final Option.Key nestedZombieHealth_roboconeH = new Option.Key("nestedZombieHealth.roboconeH");
        public final Option.Key nestedZombieHealth_scrapmechH = new Option.Key("nestedZombieHealth.scrapmechH");
        public final Option.Key nestedZombieHealth_scientistH = new Option.Key("nestedZombieHealth.scientistH");
        public final Option.Key nestedZombieHealth_snorkelH = new Option.Key("nestedZombieHealth.snorkelH");
        public final Option.Key nestedZombieHealth_soldierH = new Option.Key("nestedZombieHealth.soldierH");
        public final Option.Key nestedZombieHealth_tombraiserH = new Option.Key("nestedZombieHealth.tombraiserH");
        public final Option.Key nestedZombieHealth_zombiekingH = new Option.Key("nestedZombieHealth.zombiekingH");
        public final Option.Key nestedZombieHealth_zombiepigH = new Option.Key("nestedZombieHealth.zombiepigH");
        public final Option.Key nestedZombieHealth_zomblobH = new Option.Key("nestedZombieHealth.zomblobH");
        public final Option.Key nestedZombieHealth_zomblobBH = new Option.Key("nestedZombieHealth.zomblobBH");
        public final Option.Key nestedZombieHealth_zomblobSH = new Option.Key("nestedZombieHealth.zomblobSH");
        public final Option.Key nestedZombieHealth_zomboniH = new Option.Key("nestedZombieHealth.zomboniH");
        public final Option.Key nestedZombieHealth_coneH = new Option.Key("nestedZombieHealth.coneH");
        public final Option.Key nestedZombieHealth_pokerpawngearH = new Option.Key("nestedZombieHealth.pokerpawngearH");
        public final Option.Key nestedZombieHealth_pokerknightgearH = new Option.Key("nestedZombieHealth.pokerknightgearH");
        public final Option.Key nestedZombieHealth_pokertowergearH = new Option.Key("nestedZombieHealth.pokertowergearH");
        public final Option.Key nestedZombieHealth_pokerbishopgearH = new Option.Key("nestedZombieHealth.pokerbishopgearH");
        public final Option.Key nestedZombieHealth_kingpiecegearH = new Option.Key("nestedZombieHealth.kingpiecegearH");
        public final Option.Key nestedZombieHealth_bucketH = new Option.Key("nestedZombieHealth.bucketH");
        public final Option.Key nestedZombieHealth_medallionH = new Option.Key("nestedZombieHealth.medallionH");
        public final Option.Key nestedZombieHealth_footballHelmH = new Option.Key("nestedZombieHealth.footballHelmH");
        public final Option.Key nestedZombieHealth_berserkerHelmH = new Option.Key("nestedZombieHealth.berserkerHelmH");
        public final Option.Key nestedZombieHealth_defensiveendHelmH = new Option.Key("nestedZombieHealth.defensiveendHelmH");
        public final Option.Key nestedZombieHealth_blastronautHelmH = new Option.Key("nestedZombieHealth.blastronautHelmH");
        public final Option.Key nestedZombieHealth_knightHelmH = new Option.Key("nestedZombieHealth.knightHelmH");
        public final Option.Key nestedZombieHealth_sargeanthelmetH = new Option.Key("nestedZombieHealth.sargeanthelmetH");
        public final Option.Key nestedZombieHealth_soldierhelmetH = new Option.Key("nestedZombieHealth.soldierhelmetH");
        public final Option.Key nestedZombieHealth_brickH = new Option.Key("nestedZombieHealth.brickH");
        public final Option.Key nestedZombieHealth_coneTowerH = new Option.Key("nestedZombieHealth.coneTowerH");
        public final Option.Key nestedZombieHealth_pyramidH = new Option.Key("nestedZombieHealth.pyramidH");
        public final Option.Key nestedZombieHealth_sarcophagusH = new Option.Key("nestedZombieHealth.sarcophagusH");
        public final Option.Key nestedZombieHealth_bowlH = new Option.Key("nestedZombieHealth.bowlH");
        public final Option.Key nestedZombieHealth_holoHelmetH = new Option.Key("nestedZombieHealth.holoHelmetH");
        public final Option.Key nestedZombieHealth_crystalshoeHelmetH = new Option.Key("nestedZombieHealth.crystalshoeHelmetH");
        public final Option.Key nestedZombieHealth_pumpkinH = new Option.Key("nestedZombieHealth.pumpkinH");
        public final Option.Key nestedZombieHealth_screendoorShieldH = new Option.Key("nestedZombieHealth.screendoorShieldH");
        public final Option.Key nestedZombieHealth_sergeantShieldH = new Option.Key("nestedZombieHealth.sergeantShieldH");
        public final Option.Key nestedZombieHealth_newspaperShieldH = new Option.Key("nestedZombieHealth.newspaperShieldH");
        public final Option.Key nestedZombieHealth_sundayShieldH = new Option.Key("nestedZombieHealth.sundayShieldH");
        public final Option.Key nestedZombieHealth_bookShieldH = new Option.Key("nestedZombieHealth.bookShieldH");
        public final Option.Key nestedZombieHealth_trashcanObstH = new Option.Key("nestedZombieHealth.trashcanObstH");
        public final Option.Key nestedZombieHealth_basketballObstH = new Option.Key("nestedZombieHealth.basketballObstH");
        public final Option.Key nestedZombieHealth_healstationObstH = new Option.Key("nestedZombieHealth.healstationObstH");
        public final Option.Key nestedZombieHealth_gargolithObstH = new Option.Key("nestedZombieHealth.gargolithObstH");
        public final Option.Key nestedZombieHealth_imptabletObstH = new Option.Key("nestedZombieHealth.imptabletObstH");
        public final Option.Key nestedZombieHealth_egyptTombstoneH = new Option.Key("nestedZombieHealth.egyptTombstoneH");
        public final Option.Key nestedZombieHealth_hawkerObstH = new Option.Key("nestedZombieHealth.hawkerObstH");
        public final Option.Key nestedZombieHealth_octoObstH = new Option.Key("nestedZombieHealth.octoObstH");
        public final Option.Key nestedZombieHealth_zomboniVH = new Option.Key("nestedZombieHealth.zomboniVH");
        public final Option.Key nestedZombieHealth_bobsledVH = new Option.Key("nestedZombieHealth.bobsledVH");
        public final Option.Key nestedZombieHealth_speakerVH = new Option.Key("nestedZombieHealth.speakerVH");
    }
}

