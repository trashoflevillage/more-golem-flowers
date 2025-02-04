package io.github.trashoflevillage.biome_golems.mixin;

import io.github.trashoflevillage.biome_golems.access.IronGolemEntityMixinAccess;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public class MobEntityMixin {
    @Inject(method = "initialize", at = @At("TAIL"))
    public void initialize(
            ServerWorldAccess world,
            LocalDifficulty difficulty,
            SpawnReason spawnReason,
            EntityData entityData,
            CallbackInfoReturnable<EntityData> cir
    ) {
        MobEntity entity = (MobEntity)(Object)this;
        if (entity instanceof IronGolemEntity golemEntity) {
            ((IronGolemEntityMixinAccess)golemEntity).initModData();
        }
    }
}
