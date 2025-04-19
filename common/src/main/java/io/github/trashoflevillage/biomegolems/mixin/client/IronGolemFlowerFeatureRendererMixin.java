package io.github.trashoflevillage.biomegolems.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.trashoflevillage.biomegolems.client.access.IronGolemRenderStateMixinAccess;
import io.github.trashoflevillage.biomegolems.util.GolemType;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.IronGolemFlowerFeatureRenderer;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(IronGolemFlowerFeatureRenderer.class)
public class IronGolemFlowerFeatureRendererMixin {
    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/IronGolemEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;")
    )
    public BlockState getFlowerBlockstate(BlockState original, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, IronGolemEntityRenderState ironGolemEntityRenderState, float f, float g) {
        IronGolemRenderStateMixinAccess customState = (IronGolemRenderStateMixinAccess)ironGolemEntityRenderState;
        Block flower = customState.getGolemVariant().getFlower(customState.isNight());
        return flower.getDefaultState();
    }
}
