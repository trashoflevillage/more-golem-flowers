package io.github.trashoflevillage.biome_golems;

import io.github.trashoflevillage.biome_golems.entity.IronGolemTextureRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class BiomeGolemsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        IronGolemTextureRegistry.registerSpecialTextures();
    }
}