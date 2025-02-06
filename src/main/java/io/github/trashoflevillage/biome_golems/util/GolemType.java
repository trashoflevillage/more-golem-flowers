package io.github.trashoflevillage.biome_golems.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

public enum GolemType {
    ALLIUM("allium", (n) -> Blocks.ALLIUM),
    BLUE_ORCHID("blue_orchid", (n) -> Blocks.BLUE_ORCHID),
    CORNFLOWER("cornflower", (n) -> Blocks.CORNFLOWER),
    DANDELION("dandelion", (n) -> Blocks.DANDELION),
    DEAD_BUSH("dead_bush", (n) -> Blocks.DEAD_BUSH),
    EYEBLOSSOM("eyeblossom", (n) -> {
        if (n) return Blocks.OPEN_EYEBLOSSOM;
        return Blocks.CLOSED_EYEBLOSSOM;
    }),
    LILY_OF_THE_VALLEY("lily_of_the_Valley", (n) -> Blocks.LILY_OF_THE_VALLEY),
    ORANGE_TULIP("orange_tulip", (n) -> Blocks.ORANGE_TULIP),
    PINK_TULIP("pink_tulip", (n) -> Blocks.PINK_TULIP),
    POPPY("poppy", (n) -> Blocks.POPPY),
    SUNFLOWER("sunflower", (n) -> Blocks.SUNFLOWER),
    VINE("vine", (n) -> Blocks.VINE),
    WHITE_TULIP("white_tulip", (n) -> Blocks.WHITE_TULIP)
    ;

    final String name;
    final GolemFlowerFactory flowerFactory;

    GolemType(String name, GolemFlowerFactory flowerFactory) {
        this.name = name;
        this.flowerFactory = flowerFactory;
    }

    public String toString() {
        return this.name;
    }

    public static GolemType fromString(String name) {
        switch (name) {
            case "allium": return GolemType.ALLIUM;
            case "blue_orchid": return GolemType.BLUE_ORCHID;
            case "cornflower": return GolemType.CORNFLOWER;
            case "dandelion": return GolemType.DANDELION;
            case "dead_bush": return GolemType.DEAD_BUSH;
            case "eyeblossom": return GolemType.EYEBLOSSOM;
            case "lily_of_the_Valley": return GolemType.LILY_OF_THE_VALLEY;
            case "orange_tulip": return GolemType.ORANGE_TULIP;
            case "pink_tulip": return GolemType.PINK_TULIP;
            case "sunflower": return GolemType.SUNFLOWER;
            case "vine": return GolemType.VINE;
            case "white_tulip": return GolemType.WHITE_TULIP;
            default: return GolemType.POPPY;
        }
    }

    interface GolemFlowerFactory {
        Block getFlower(boolean isNight);
    }
}