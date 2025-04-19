package io.github.trashoflevillage.biomegolems.client.entity;

import io.github.trashoflevillage.biomegolems.BiomeGolems;
import io.github.trashoflevillage.biomegolems.client.access.IronGolemRenderStateMixinAccess;
import io.github.trashoflevillage.biomegolems.util.GolemType;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EmissiveFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class IronGolemPaleEyeFeatureRenderer<M extends EntityModel<IronGolemEntityRenderState>> extends FeatureRenderer<IronGolemEntityRenderState, M> {
    private static final Identifier PALE_EYES = Identifier.of(BiomeGolems.MOD_ID, "textures/entity/iron_golem/pale_eyes.png");
    private final EmissiveFeatureRenderer.AnimationAlphaAdjuster<IronGolemEntityRenderState> animationAlphaAdjuster;

    public IronGolemPaleEyeFeatureRenderer(
            FeatureRendererContext<IronGolemEntityRenderState, M> featureRendererContext,
            EmissiveFeatureRenderer.AnimationAlphaAdjuster<IronGolemEntityRenderState> animationAlphaAdjuster
    ) {
        super(featureRendererContext);
        this.animationAlphaAdjuster = animationAlphaAdjuster;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, IronGolemEntityRenderState state, float limbAngle, float limbDistance) {
        if (!state.invisible) {
            IronGolemRenderStateMixinAccess customState = ((IronGolemRenderStateMixinAccess)state);
            if (customState.getGolemVariant() == GolemType.EYEBLOSSOM) {
                float h = this.animationAlphaAdjuster.apply(state, state.age);
                int j = ColorHelper.getArgb(MathHelper.floor(h * 255.0F), 255, 255, 255);
                renderModel(getContextModel(), PALE_EYES, matrices, vertexConsumers, 0xF000F0, state, j);
            }
        }
    }
}
