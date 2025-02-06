package io.github.trashoflevillage.biome_golems.access;

import io.github.trashoflevillage.biome_golems.util.GolemType;

public interface IronGolemRenderStateMixinAccess {
    GolemType getGolemVariant();
    void setGolemVariant(GolemType variant);
    boolean isNight();
    void setTime(long val);
}
