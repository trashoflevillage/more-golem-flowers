package io.github.trashoflevillage.biomegolems.util;

import io.github.trashoflevillage.biomegolems.BiomeGolems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class GolemType {
    private static final HashMap<Identifier, GolemType> registeredTypes = new HashMap<>();

    public static final GolemType ALLIUM = registerGolemType("allium", new GolemType((n) -> Blocks.ALLIUM, ModTags.Biomes.SPAWNS_ALLIUM_GOLEM));
    public static final GolemType BLUE_ORCHID = registerGolemType("blue_orchid", new GolemType((n) -> Blocks.BLUE_ORCHID, ModTags.Biomes.SPAWNS_BLUE_ORCHID_GOLEM));
    public static final GolemType CORNFLOWER = registerGolemType("cornflower", new GolemType((n) -> Blocks.CORNFLOWER, ModTags.Biomes.SPAWNS_CORNFLOWER_GOLEM));
    public static final GolemType DANDELION = registerGolemType("dandelion", new GolemType((n) -> Blocks.DANDELION, ModTags.Biomes.SPAWNS_DANDELION_GOLEM));
    public static final GolemType DEAD_BUSH = registerGolemType("dead_bush", new GolemType((n) -> Blocks.DEAD_BUSH, ModTags.Biomes.SPAWNS_DEAD_BUSH_GOLEM));
    public static final GolemType EYEBLOSSOM = registerGolemType("eyeblossom", new GolemType((n) -> n ? Blocks.OPEN_EYEBLOSSOM : Blocks.CLOSED_EYEBLOSSOM, ModTags.Biomes.SPAWNS_EYEBLOSSOM_GOLEM));
    public static final GolemType LILY_OF_THE_VALLEY = registerGolemType("lily_of_the_valley", new GolemType((n) -> Blocks.LILY_OF_THE_VALLEY, ModTags.Biomes.SPAWNS_LILY_OF_THE_VALLEY_GOLEM));
    public static final GolemType ORANGE_TULIP = registerGolemType("orange_tulip", new GolemType((n) -> Blocks.ORANGE_TULIP, ModTags.Biomes.SPAWNS_ORANGE_TULIP_GOLEM));
    public static final GolemType PINK_TULIP = registerGolemType("pink_tulip", new GolemType((n) -> Blocks.PINK_TULIP, ModTags.Biomes.SPAWNS_PINK_TULIP_GOLEM));
    public static final GolemType POPPY = registerGolemType("poppy", new GolemType((n) -> Blocks.POPPY, null));
    public static final GolemType SUNFLOWER = registerGolemType("sunflower", new GolemType((n) -> Blocks.SUNFLOWER, ModTags.Biomes.SPAWNS_SUNFLOWER_GOLEM));
    public static final GolemType VINE = registerGolemType("vine", new GolemType((n) -> Blocks.VINE, ModTags.Biomes.SPAWNS_VINE_GOLEM));
    public static final GolemType WHITE_TULIP = registerGolemType("white_tulip", new GolemType((n) -> Blocks.WHITE_TULIP, ModTags.Biomes.SPAWNS_WHITE_TULIP_GOLEM));

    private final Function<Boolean, Block> flowerFactory;
    private final TagKey<Biome> biomeTag;

    public GolemType(Function<Boolean, Block> flowerStateFactory, TagKey<Biome> biomeTag) {
        this.flowerFactory = flowerStateFactory;
        this.biomeTag = biomeTag;
    }

    public Block getFlower(boolean isNight) {
        return flowerFactory.apply(isNight);
    }

    public boolean shouldSpawnInBiome(RegistryEntry<Biome> biome) {
        return biome.isIn(biomeTag);
    }

    public Identifier getId() {
        if (registeredTypes.containsValue(this)) {
            for (Map.Entry<Identifier, GolemType> i : registeredTypes.entrySet()) {
                if (i.getValue() == this) return i.getKey();
            }
        }
        return null;
    }

    public static GolemType get(Identifier id) {
        return registeredTypes.getOrDefault(id, GolemType.POPPY);
    }

    public static GolemType getTypeForBiome(RegistryEntry<Biome> biome) {
        for (Map.Entry<Identifier, GolemType> i : registeredTypes.entrySet()) {
            GolemType type = i.getValue();
            if (type.biomeTag != null && biome.isIn(type.biomeTag)) {
                return type;
            }
        }
        return POPPY;
    }

    public static List<GolemType> getAllTypes() {
        List<GolemType> types = List.of();
        for (Map.Entry<Identifier, GolemType> i : registeredTypes.entrySet()) {
            types.add(i.getValue());
        }
        return types;
    }

    public static GolemType registerGolemType(Identifier id, GolemType type) {
        registeredTypes.put(id, type);
        return type;
    }

    private static GolemType registerGolemType(String name, GolemType type) {
        return registerGolemType(Identifier.of(BiomeGolems.MOD_ID, name), type);
    }

    public static void registerAll() {}
}
