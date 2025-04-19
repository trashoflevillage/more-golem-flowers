package io.github.trashoflevillage.biomegolems.util;

import io.github.trashoflevillage.biomegolems.BiomeGolems;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public class ModTags {
    public static class Biomes {
        public static final TagKey<Biome> SPAWNS_ALLIUM_GOLEM = createTag("spawns_allium_golem");
        public static final TagKey<Biome> SPAWNS_BLUE_ORCHID_GOLEM = createTag("spawns_blue_orchid_golem");
        public static final TagKey<Biome> SPAWNS_CORNFLOWER_GOLEM = createTag("spawns_cornflower_golem");
        public static final TagKey<Biome> SPAWNS_DANDELION_GOLEM = createTag("spawns_dandelion_golem");
        public static final TagKey<Biome> SPAWNS_LILY_OF_THE_VALLEY_GOLEM = createTag("spawns_lily_of_the_valley_golem");
        public static final TagKey<Biome> SPAWNS_ORANGE_TULIP_GOLEM = createTag("spawns_orange_tulip_golem");
        public static final TagKey<Biome> SPAWNS_PINK_TULIP_GOLEM = createTag("spawns_pink_tulip_golem");
        public static final TagKey<Biome> SPAWNS_SUNFLOWER_GOLEM = createTag("spawns_sunflower_golem");
        public static final TagKey<Biome> SPAWNS_VINE_GOLEM = createTag("spawns_vine_golem");
        public static final TagKey<Biome> SPAWNS_WHITE_TULIP_GOLEM = createTag("spawns_white_tulip_golem");
        public static final TagKey<Biome> SPAWNS_DEAD_BUSH_GOLEM = createTag("spawns_dead_bush_golem");
        public static final TagKey<Biome> SPAWNS_EYEBLOSSOM_GOLEM = createTag("spawns_eyeblossom_golem");

        private static TagKey<Biome> createTag(String name) {
            return TagKey.of(RegistryKeys.BIOME, Identifier.of(BiomeGolems.MOD_ID, name));
        }
    }
}