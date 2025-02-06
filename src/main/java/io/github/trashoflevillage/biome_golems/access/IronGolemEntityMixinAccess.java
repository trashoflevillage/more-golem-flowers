package io.github.trashoflevillage.biome_golems.access;

import io.github.trashoflevillage.biome_golems.util.GolemType;

public interface IronGolemEntityMixinAccess {
    GolemType getGolemVariant();
    void initModData();
}
