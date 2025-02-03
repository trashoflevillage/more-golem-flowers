package io.github.trashoflevillage.biome_golems.mixin.client;

import io.github.trashoflevillage.biome_golems.BiomeGolemsResource;
import io.github.trashoflevillage.biome_golems.access.IronGolemEntityMixinAccess;
import io.github.trashoflevillage.biome_golems.access.IronGolemRenderStateMixinAccess;
import io.github.trashoflevillage.biome_golems.mixin.IronGolemEntityMixin;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntityRenderer.class)
public class IronGolemEntityRendererMixin {
    @Inject(
            method = "getTexture(Lnet/minecraft/client/render/entity/state/IronGolemEntityRenderState;)Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getTexture (IronGolemEntityRenderState renderState, CallbackInfoReturnable<Identifier> cir) {
        String golemVariant = ((IronGolemRenderStateMixinAccess)renderState).getGolemVariant();
        Text customNameText = renderState.customName;
        String customName = "";
        if (customNameText != null) {
            customName = customNameText.getLiteralString();
            if (customName == null) customName = "";
        }

        if (customName.equalsIgnoreCase("armstrong")) {
            golemVariant = "armstrong";
        }

        if (!BiomeGolemsResource.golemTextureIdentifiers.containsKey(golemVariant)) {
            return;
        }
        cir.setReturnValue(BiomeGolemsResource.golemTextureIdentifiers.get(golemVariant));
    }

    @Inject(
            method = "updateRenderState(Lnet/minecraft/entity/passive/IronGolemEntity;Lnet/minecraft/client/render/entity/state/IronGolemEntityRenderState;F)V",
            at = @At("TAIL")
    )
    public void updateRenderState(IronGolemEntity ironGolemEntity, IronGolemEntityRenderState ironGolemEntityRenderState, float f, CallbackInfo ci) {
        ((IronGolemRenderStateMixinAccess)ironGolemEntityRenderState).setGolemVariant(((IronGolemEntityMixinAccess)ironGolemEntity).getGolemVariant());
    }
}