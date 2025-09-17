package io.github.GrassyDev.pvzmod.registry.entity.statuseffects;

import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Holder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class StatusHolder {

    public static final Holder<StatusEffect> ICE_HOLDER = register("ice", PvZCubed.ICE);
    public static final Holder<StatusEffect> POISON_HOLDER = register("pvzpoison", PvZCubed.PVZPOISON);;
    public static final Holder<StatusEffect> ACID_HOLDER = register("acid", PvZCubed.ACID);
    public static final Holder<StatusEffect> FROZEN_HOLDER = register("frozen", PvZCubed.FROZEN);
    public static final Holder<StatusEffect> STUN_HOLDER = register("stun", PvZCubed.STUN);
    public static final Holder<StatusEffect> BOUNCED_HOLDER = register("bounced", PvZCubed.BOUNCED);
    public static final Holder<StatusEffect> WARM_HOLDER = register("warm", PvZCubed.WARM);
    public static final Holder<StatusEffect> DISABLE_HOLDER = register("disable", PvZCubed.DISABLE);
    public static final Holder<StatusEffect> WET_HOLDER = register("wet", PvZCubed.WET);
    public static final Holder<StatusEffect> BARK_HOLDER = register("bark", PvZCubed.BARK);
    public static final Holder<StatusEffect> CHEESE_HOLDER = register("cheese", PvZCubed.CHEESE);
    public static final Holder<StatusEffect> GENERICSLOW_HOLDER = register("genericslow", PvZCubed.GENERICSLOW);
    public static final Holder<StatusEffect> SHADOW_HOLDER = register("shadow", PvZCubed.SHADOW);
    public static final Holder<StatusEffect> MARIGOLD_HOLDER = register("marigold", PvZCubed.MARIGOLD);
    private static Holder<StatusEffect> register(String path, StatusEffect effect) {
        return Registry.registerHolder(Registries.STATUS_EFFECT, Identifier.of("pvzmod", path), effect);
    }
}
