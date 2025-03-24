package io.github.trashoflevillage.biome_golems.entity;

import io.github.trashoflevillage.biome_golems.BiomeGolems;
import io.github.trashoflevillage.biome_golems.access.IronGolemEntityMixinAccess;
import io.github.trashoflevillage.biome_golems.access.IronGolemRenderStateMixinAccess;
import io.github.trashoflevillage.biome_golems.util.GolemType;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class IronGolemTextureRegistry {
    private static final HashMap<Identifier, GolemTextureFactory> registeredTextures = new HashMap<>();

    public static void register(Identifier id, GolemTextureFactory factory) {
        registeredTextures.put(id, factory);
    }

    public static Identifier getTexture(GolemType type, IronGolemEntityRenderState renderState) {
        Identifier id = type.getId();
        if (registeredTextures.containsKey(type.getId()))
            return registeredTextures.get(type.getId()).get(type, renderState);
        else return Identifier.of(id.getNamespace(), "textures/entity/iron_golem/" + id.getPath() + ".png");
    }

    public interface GolemTextureFactory {
        Identifier get(GolemType type, IronGolemEntityRenderState renderState);
    }

    public static void registerSpecialTextures() {
        register(Identifier.of(BiomeGolems.MOD_ID, "eyeblossom"), (type, renderState) -> {
            if (((IronGolemRenderStateMixinAccess)renderState).isNight()) {
                return Identifier.of(BiomeGolems.MOD_ID, "textures/entity/iron_golem/open_eyeblossom.png");
            }
            return Identifier.of(BiomeGolems.MOD_ID, "textures/entity/iron_golem/closed_eyeblossom.png");
        });
        register(Identifier.of(BiomeGolems.MOD_ID, "dandelion"),
                (type, renderState) -> Identifier.ofVanilla("textures/entity/iron_golem/iron_golem.png"));
    }
}
