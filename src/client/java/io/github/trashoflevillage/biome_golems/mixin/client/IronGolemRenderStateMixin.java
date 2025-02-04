package io.github.trashoflevillage.biome_golems.mixin.client;

import io.github.trashoflevillage.biome_golems.access.IronGolemEntityMixinAccess;
import io.github.trashoflevillage.biome_golems.access.IronGolemRenderStateMixinAccess;
import io.github.trashoflevillage.biome_golems.mixin.IronGolemEntityMixin;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.entity.passive.IronGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntityRenderState.class)
public class IronGolemRenderStateMixin implements IronGolemRenderStateMixinAccess {
    @Unique
    String golemVariant = "dandelion";
    long time = 0;

    @Override
    public String getGolemVariant() {
        return golemVariant;
    }

    public void setGolemVariant(String golemVariant) {
        this.golemVariant = golemVariant;
    }

    @Override
    public boolean isNight() {
        return time > 13000;
    }

    public void setTime(long val) {
        time = val;
    }
}
