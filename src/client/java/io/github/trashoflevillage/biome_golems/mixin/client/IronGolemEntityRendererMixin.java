package io.github.trashoflevillage.biome_golems.mixin.client;

import io.github.trashoflevillage.biome_golems.BiomeGolemsResource;
import io.github.trashoflevillage.biome_golems.access.IronGolemEntityMixinAccess;
import io.github.trashoflevillage.biome_golems.access.IronGolemRenderStateMixinAccess;
import io.github.trashoflevillage.biome_golems.entity.IronGolemPaleEyeFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IronGolemEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.IronGolemEntityModel;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntityRenderer.class)
public abstract class IronGolemEntityRendererMixin extends MobEntityRenderer<IronGolemEntity, IronGolemEntityRenderState, IronGolemEntityModel> {
    public IronGolemEntityRendererMixin(EntityRendererFactory.Context context, IronGolemEntityModel entityModel, float f) {
        super(context, entityModel, f);
    }

    @Inject(
            method = "getTexture(Lnet/minecraft/client/render/entity/state/IronGolemEntityRenderState;)Lnet/minecraft/util/Identifier;",
            at = @At("HEAD"),
            cancellable = true
    )
    public void getTexture(IronGolemEntityRenderState renderState, CallbackInfoReturnable<Identifier> cir) {
        IronGolemRenderStateMixinAccess customState = ((IronGolemRenderStateMixinAccess)renderState);
        String golemVariant = customState.getGolemVariant();
        Text customNameText = renderState.customName;
        String customName = "";
        if (customNameText != null) {
            customName = customNameText.getLiteralString();
            if (customName == null) customName = "";
        }

        if (customName.equalsIgnoreCase("armstrong")) {
            golemVariant = "armstrong";
        }

        if (golemVariant.equals("eyeblossom")) {
            if (customState.isNight()) {
                golemVariant = "open_eyeblossom";
            } else {
                golemVariant = "closed_eyeblossom";
            }
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
        IronGolemRenderStateMixinAccess customState = ((IronGolemRenderStateMixinAccess)ironGolemEntityRenderState);
        customState.setGolemVariant(((IronGolemEntityMixinAccess)ironGolemEntity).getGolemVariant());
        customState.setTime(ironGolemEntity.getWorld().getTimeOfDay());
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void init(EntityRendererFactory.Context context, CallbackInfo ci) {
        this.addFeature(new IronGolemPaleEyeFeatureRenderer<>(this));
    }
}