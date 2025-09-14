package io.github.GrassyDev.pvzmod.component;

import com.mojang.serialization.Codec;
import io.github.GrassyDev.pvzmod.PvZCubed;
import net.minecraft.component.DataComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModComponents {
	public static void initialize() {
		PvZCubed.LOGGER.info("Registering {} components", PvZCubed.MOD_ID);
		// Technically this method can stay empty, but some developers like to notify
		// the console, that certain parts of the mod have been successfully initialized

	}
	public static final DataComponentType<Float> STACK = Registry.register(
		Registries.DATA_COMPONENT_TYPE,
		Identifier.of(PvZCubed.MOD_ID, "stack"),
		DataComponentType.<Float>builder().codec(Codec.FLOAT).build()
	);
}
