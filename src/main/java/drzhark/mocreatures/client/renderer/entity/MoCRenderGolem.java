/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelGolem;
import drzhark.mocreatures.entity.hostile.MoCEntityGolem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGolem extends MoCRenderMoC<MoCEntityGolem, MoCModelGolem<MoCEntityGolem>> {

    public MoCRenderGolem(EntityRendererManager renderManagerIn, MoCModelGolem modelbase, float f) {
        super(renderManagerIn, modelbase, f);
        this.addLayer(new LayerMoCGolem(this));
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityGolem par1Entity) {
        return par1Entity.getTexture();
    }

    private class LayerMoCGolem extends LayerRenderer<MoCEntityGolem, MoCModelGolem<MoCEntityGolem>> {

        private final MoCRenderGolem mocRenderer;
        private final MoCModelGolem mocModel = new MoCModelGolem();

        public LayerMoCGolem(MoCRenderGolem render) {
            super(render);
            this.mocRenderer = render;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityGolem entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation effectTexture = entity.getEffectTexture();
            if (effectTexture != null) {
                float f = (float) entity.ticksExisted + partialTicks;
                this.mocModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
                this.getEntityModel().copyModelAttributesTo(this.mocModel);
                IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEnergySwirl(effectTexture, f * 0.01F, f * 0.01F));
                this.mocModel.setRotationAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                this.mocModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 0.5F, 0.5F, 0.5F, 1.0F);
            }
        }
    }
}
