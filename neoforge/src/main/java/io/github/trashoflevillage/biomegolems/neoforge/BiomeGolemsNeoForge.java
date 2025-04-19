package io.github.trashoflevillage.biomegolems.neoforge;

import net.neoforged.fml.common.Mod;

import io.github.trashoflevillage.biomegolems.BiomeGolems;

@Mod(BiomeGolems.MOD_ID)
public final class BiomeGolemsNeoForge {
    public BiomeGolemsNeoForge() {
        // Run our common setup.
        BiomeGolems.init();
    }
}
