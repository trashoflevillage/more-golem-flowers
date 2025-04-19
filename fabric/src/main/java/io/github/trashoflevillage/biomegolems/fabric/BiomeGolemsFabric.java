package io.github.trashoflevillage.biomegolems.fabric;

import net.fabricmc.api.ModInitializer;

import io.github.trashoflevillage.biomegolems.BiomeGolems;

public final class BiomeGolemsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        BiomeGolems.init();
    }
}
