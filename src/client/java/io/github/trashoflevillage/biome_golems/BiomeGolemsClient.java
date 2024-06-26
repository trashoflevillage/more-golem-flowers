package io.github.trashoflevillage.biome_golems;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;

public class BiomeGolemsClient implements ClientModInitializer {
    public static BiomeGolemsResource RESOURCE = new BiomeGolemsResource();
    @Override
    public void onInitializeClient() {
        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(RESOURCE);
    }
}