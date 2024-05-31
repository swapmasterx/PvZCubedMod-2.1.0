package io.github.GrassyDev.pvzmod.registry.entity.damage;

import net.minecraft.registry.Registry;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class PvZDamageTypes {

    public static final RegistryKey<DamageType> HYPNO_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("pvzmod", "hypnodamage"));
    public static final RegistryKey<DamageType> ELECTRIC_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("pvzmod", "electricdamage"));
	public static final RegistryKey<DamageType> SELF_TERMINATE_DAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("pvzmod", "selfdeathdamage"));

	public static final RegistryKey<DamageType> GENERIC_ANTI_IFRAME = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("pvzmod", "generic_no_iframe"));
	public static final RegistryKey<DamageType> VANILLA_ARMOR_PEN = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("pvzmod", "vanilla_armor_pen"));
	public static final RegistryKey<DamageType> CRAZYDAVEDAMAGE = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, new Identifier("pvzmod", "davesshovel"));

    public static DamageSource of(World world, RegistryKey<DamageType> key) {
        return new DamageSource(world.getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).getHolderOrThrow(key));
    }
}
