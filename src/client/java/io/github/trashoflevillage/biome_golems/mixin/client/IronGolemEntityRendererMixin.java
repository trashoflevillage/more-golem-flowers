package io.github.trashoflevillage.biome_golems.mixin.client;

import io.github.trashoflevillage.biome_golems.BiomeGolemsResource;
import io.github.trashoflevillage.biome_golems.mixin.IronGolemEntityMixin;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntityRenderer.class)
public class IronGolemEntityRendererMixin {
    @Inject(method = "getTexture", at = @At("HEAD"), cancellable = true)
    public void getTexture (IronGolemEntity entity, CallbackInfoReturnable<Identifier> cir) {
        NbtCompound nbt = new NbtCompound();
        entity.writeNbt(nbt);

        String golemVariant = nbt.getString("golemVariant");

        if (!BiomeGolemsResource.golemTextureIdentifiers.containsKey(golemVariant)) {
            return;
        }
        cir.setReturnValue(BiomeGolemsResource.golemTextureIdentifiers.get(golemVariant));
    }
}
