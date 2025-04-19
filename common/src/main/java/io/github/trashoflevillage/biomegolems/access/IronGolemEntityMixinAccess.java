package io.github.trashoflevillage.biomegolems.access;

import io.github.trashoflevillage.biomegolems.util.GolemType;

public interface IronGolemEntityMixinAccess {
    GolemType getGolemVariant();
    void initModData();
}
