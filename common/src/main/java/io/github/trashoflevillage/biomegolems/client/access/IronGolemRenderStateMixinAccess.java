package io.github.trashoflevillage.biomegolems.client.access;

import io.github.trashoflevillage.biomegolems.util.GolemType;

public interface IronGolemRenderStateMixinAccess {
    GolemType getGolemVariant();
    void setGolemVariant(GolemType variant);
    boolean isNight();
    void setTime(long val);
}

