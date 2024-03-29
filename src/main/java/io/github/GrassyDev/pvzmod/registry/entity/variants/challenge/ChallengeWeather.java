package io.github.GrassyDev.pvzmod.registry.entity.variants.challenge;

import java.util.Arrays;
import java.util.Comparator;

public enum ChallengeWeather {
	CLOUD(0),
	RAIN(1),
	THUNDER(2);

	private static final ChallengeWeather[] BY_ID = Arrays.stream(values()).sorted(Comparator.
			comparingInt(ChallengeWeather::getId)).toArray(ChallengeWeather[]::new);
	private final int id;

	ChallengeWeather(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static ChallengeWeather byId(int id) {
		return BY_ID[id % BY_ID.length];
	}
}
