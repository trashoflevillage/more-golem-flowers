package io.github.trashoflevillage.biomegolems.neoforge.client;

import io.github.trashoflevillage.biomegolems.BiomeGolems;
import io.github.trashoflevillage.biomegolems.client.BiomeGolemsClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(value = BiomeGolems.MOD_ID, dist = Dist.CLIENT)
public class BiomeGolemsNeoForgeClient {
    public BiomeGolemsNeoForgeClient(IEventBus modBus) {
        BiomeGolemsClient.init();
    }
}
