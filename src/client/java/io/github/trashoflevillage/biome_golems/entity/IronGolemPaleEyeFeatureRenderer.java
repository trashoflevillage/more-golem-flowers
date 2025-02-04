package io.github.trashoflevillage.biome_golems.entity;

import io.github.trashoflevillage.biome_golems.BiomeGolems;
import io.github.trashoflevillage.biome_golems.access.IronGolemRenderStateMixinAccess;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EmissiveFeatureRenderer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.state.IronGolemEntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.Cracks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class IronGolemPaleEyeFeatureRenderer<S extends LivingEntityRenderState, M extends EntityModel<IronGolemEntityRenderState>> extends FeatureRenderer<IronGolemEntityRenderState, M> {
    private static final Identifier PALE_EYES = Identifier.of(BiomeGolems.MOD_ID, "textures/entity/iron_golem/pale_eyes.png");

    public IronGolemPaleEyeFeatureRenderer(
        FeatureRendererContext<IronGolemEntityRenderState, M> featureRendererContext
    ) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, IronGolemEntityRenderState state, float limbAngle, float limbDistance) {
        if (!state.invisible) {
            IronGolemRenderStateMixinAccess customState = ((IronGolemRenderStateMixinAccess)state);
            if (customState.getGolemVariant().equals("eyeblossom")) {
                renderModel(getContextModel(), PALE_EYES, matrices, vertexConsumers, light, state, -1);
            }
        }
    }
}
