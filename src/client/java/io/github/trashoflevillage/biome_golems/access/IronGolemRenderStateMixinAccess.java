package io.github.trashoflevillage.biome_golems.access;

public interface IronGolemRenderStateMixinAccess {
    String getGolemVariant();
    void setGolemVariant(String variant);
    boolean isNight();
    void setTime(long val);
}
