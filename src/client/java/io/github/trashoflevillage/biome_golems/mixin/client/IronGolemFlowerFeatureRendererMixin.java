package io.github.trashoflevillage.biome_golems.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.trashoflevillage.biome_golems.access.IronGolemRenderStateMixinAccess;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.IronGolemFlowerFeatureRenderer;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.HashMap;

@Mixin(IronGolemFlowerFeatureRenderer.class)
public class IronGolemFlowerFeatureRendererMixin {
    @ModifyExpressionValue(
            method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/IronGolemEntityRenderState;FF)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;")
    )
    public BlockState getFlowerBlockstate(BlockState original, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, IronGolemEntityRenderState ironGolemEntityRenderState, float f, float g) {
        IronGolemRenderStateMixinAccess customState = (IronGolemRenderStateMixinAccess)ironGolemEntityRenderState;
        Block flower;
        if (customState.getGolemVariant().equals("eyeblossom")) {
            if (customState.isNight()) flower = Blocks.OPEN_EYEBLOSSOM;
            else flower = Blocks.CLOSED_EYEBLOSSOM;
        } else flower = Registries.BLOCK.get(Identifier.ofVanilla(customState.getGolemVariant()));
        if (flower == null) flower = Blocks.POPPY;
        return flower.getDefaultState();
    }
}
