package io.github.trashoflevillage.biome_golems;

import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class BiomeGolemsResource implements SimpleSynchronousResourceReloadListener {
    public static HashMap<String, Identifier> golemTextureIdentifiers = new HashMap<>();

    @Override
    public Identifier getFabricId() {
        return Identifier.of(BiomeGolems.MOD_ID, "biome_golems");
    }

    @Override
    public void reload(ResourceManager manager) {
        String[] textures = new String[] {
                "allium",
                "blue_orchid",
                "cornflower",
                "lily_of_the_valley",
                "orange_tulip",
                "pink_tulip",
                "white_tulip",
                "vine",
                "sunflower",
                "poppy",
                "armstrong",
                "dead_bush"
        };
        TextureManager textureManager = MinecraftClient.getInstance().getTextureManager();
        for (String i : textures) {
            Identifier id = Identifier.of(BiomeGolems.MOD_ID, "textures/entity/iron_golem/" + i + ".png");
            ResourceTexture texture = new ResourceTexture(id);
            textureManager.registerTexture(id, texture);
            golemTextureIdentifiers.put(i, id);
        }
    }
}
