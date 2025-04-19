package io.github.trashoflevillage.biomegolems.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.trashoflevillage.biomegolems.BiomeGolems;
import io.github.trashoflevillage.biomegolems.client.BiomeGolemsResource;
import io.github.trashoflevillage.biomegolems.access.IronGolemEntityMixinAccess;
import io.github.trashoflevillage.biomegolems.client.access.IronGolemRenderStateMixinAccess;
import io.github.trashoflevillage.biomegolems.client.entity.IronGolemOpenEyeblossomFeatureRenderer;
import io.github.trashoflevillage.biomegolems.client.entity.IronGolemPaleEyeFeatureRenderer;
import io.github.trashoflevillage.biomegolems.client.entity.IronGolemTextureRegistry;
import io.github.trashoflevillage.biomegolems.util.GolemType;
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
        Text customNameText = renderState.customName;
        String customName = "";
        if (customNameText != null) {
            customName = customNameText.getLiteralString();
            if (customName == null) customName = "";
        }

        GolemType type = ((IronGolemRenderStateMixinAccess)renderState).getGolemVariant();
        Identifier textureId;
        if (customName.equalsIgnoreCase("armstrong"))
            textureId = Identifier.of(BiomeGolems.MOD_ID, "textures/entity/iron_golem/armstrong.png");
        else
            textureId = IronGolemTextureRegistry.getTexture(type, renderState);

        cir.setReturnValue(textureId);
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
        this.addFeature(
                new IronGolemPaleEyeFeatureRenderer<>(
                        this,
                        (state, tickDelta) -> 1.0F
                )
        );
        this.addFeature(
                new IronGolemOpenEyeblossomFeatureRenderer<>(
                        this,
                        (state, tickDelta) -> 1.0F
                )
        );
    }
}