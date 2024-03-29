package io.github.GrassyDev.pvzmod.registry.entity.variants.graves;

import java.util.Arrays;
import java.util.Comparator;

public enum RiftVariants {
	DEFAULT(0),
	BASS(1),
	GARGOLITH(2);

	private static final RiftVariants[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(RiftVariants::getId)).toArray(RiftVariants[]::new);
	private final int id;

	RiftVariants(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static RiftVariants byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
