package io.github.trashoflevillage.biomegolems.mixin.client;

import io.github.trashoflevillage.biomegolems.client.access.IronGolemRenderStateMixinAccess;
import io.github.trashoflevillage.biomegolems.util.GolemType;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(IronGolemEntityRenderState.class)
public class IronGolemRenderStateMixin implements IronGolemRenderStateMixinAccess {
    @Unique
    GolemType golemVariant = GolemType.DANDELION;
    @Unique
    long time = 0;

    @Override
    public GolemType getGolemVariant() {
        return golemVariant;
    }

    public void setGolemVariant(GolemType golemVariant) {
        this.golemVariant = golemVariant;
    }

    @Override
    public boolean isNight() {
        return (time % 24000) > 13000;
    }

    public void setTime(long val) {
        time = val;
    }
}
