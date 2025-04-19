package io.github.trashoflevillage.biomegolems.fabric.client;

import io.github.trashoflevillage.biomegolems.client.BiomeGolemsClient;
import net.fabricmc.api.ClientModInitializer;

public final class BiomeGolemsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BiomeGolemsClient.init();
    }
}
