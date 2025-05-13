/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelFirefly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderFirefly extends MoCRenderInsect<MoCEntityFirefly, MoCModelFirefly<MoCEntityFirefly>> {

    public MoCRenderFirefly(EntityRendererManager renderManagerIn, MoCModelFirefly modelbase) {
        super(renderManagerIn, modelbase);
        this.addLayer(new LayerMoCFirefly(this));
    }

    @Override
    protected void preRenderCallback(MoCEntityFirefly entityfirefly, MatrixStack matrixStackIn, float par2) {
        if (entityfirefly.getIsFlying()) {
            rotateFirefly(entityfirefly, matrixStackIn);
        } else if (entityfirefly.climbing()) {
            rotateAnimal(entityfirefly, matrixStackIn);
        }

    }

    protected void rotateFirefly(MoCEntityFirefly entityfirefly, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(40F));

    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityFirefly entityfirefly) {
        return entityfirefly.getTexture();
    }

    private class LayerMoCFirefly extends LayerRenderer<MoCEntityFirefly, MoCModelFirefly<MoCEntityFirefly>> {
        private final MoCRenderFirefly mocRenderer;
        private final MoCModelFirefly mocModel = new MoCModelFirefly();

        public LayerMoCFirefly(MoCRenderFirefly p_i46112_1_) {
            super(p_i46112_1_);
            this.mocRenderer = p_i46112_1_;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityFirefly entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            this.setTailBrightness(matrixStackIn, entitylivingbaseIn, partialTicks);
            this.mocModel.copyModelAttributesTo(this.mocRenderer.getEntityModel());
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityCutoutNoCull(MoCreatures.proxy.getModelTexture("firefly_glow.png")));
            this.mocModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.mocModel.render(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }

        protected void setTailBrightness(MatrixStack matrixStackIn, MoCEntityFirefly entityliving, float par3) {
            float var4 = 1.0F;
            RenderSystem.enableBlend();
            RenderSystem.disableAlphaTest();
            RenderSystem.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, var4);
        }
    }
}
