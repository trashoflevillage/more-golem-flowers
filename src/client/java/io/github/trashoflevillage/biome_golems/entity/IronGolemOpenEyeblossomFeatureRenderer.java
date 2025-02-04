package io.github.trashoflevillage.biome_golems.entity;

import io.github.trashoflevillage.biome_golems.BiomeGolems;
import io.github.trashoflevillage.biome_golems.access.IronGolemRenderStateMixinAccess;
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

public class IronGolemOpenEyeblossomFeatureRenderer<M extends EntityModel<IronGolemEntityRenderState>> extends FeatureRenderer<IronGolemEntityRenderState, M> {
    private static final Identifier EYEBLOSSOMS = Identifier.of(BiomeGolems.MOD_ID, "textures/entity/iron_golem/eyeblossoms.png");
    private final EmissiveFeatureRenderer.AnimationAlphaAdjuster<IronGolemEntityRenderState> animationAlphaAdjuster;

    public IronGolemOpenEyeblossomFeatureRenderer(
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
            if ((state.customName == null || !state.customName.getString().equalsIgnoreCase("armstrong"))
                    && customState.isNight() && customState.getGolemVariant().equals("eyeblossom")) {
                float h = this.animationAlphaAdjuster.apply(state, state.age);
                int j = ColorHelper.getArgb(MathHelper.floor(h * 255.0F), 255, 255, 255);
                renderModel(getContextModel(), EYEBLOSSOMS, matrices, vertexConsumers, 0xF000F0, state, j);
            }
        }
    }
}
