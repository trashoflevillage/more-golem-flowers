package io.github.trashoflevillage.biomegolems;

import io.github.trashoflevillage.biomegolems.util.GolemType;

public final class BiomeGolems {
    public static final String MOD_ID = "biome_golems";

    public static void init() {
        GolemType.registerAll();
    }
}
