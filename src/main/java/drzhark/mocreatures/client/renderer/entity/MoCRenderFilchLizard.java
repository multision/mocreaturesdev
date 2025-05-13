/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelFilchLizard;
import drzhark.mocreatures.entity.passive.MoCEntityFilchLizard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

// Courtesy of Daveyx0, permission given
public class MoCRenderFilchLizard extends MobRenderer<MoCEntityFilchLizard, MoCModelFilchLizard<MoCEntityFilchLizard>> {

    public MoCRenderFilchLizard(EntityRendererManager renderManagerIn, MoCModelFilchLizard modelBase, float f) {
        super(renderManagerIn, modelBase, f);
        this.addLayer(new LayerHeldItemCustom(this));
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityFilchLizard entity) {
        return entity.getTexture();
    }

    private class LayerHeldItemCustom extends LayerRenderer<MoCEntityFilchLizard, MoCModelFilchLizard<MoCEntityFilchLizard>> {
        protected final MoCRenderFilchLizard livingEntityRenderer;

        public LayerHeldItemCustom(MoCRenderFilchLizard livingEntityRendererIn) {
            super(livingEntityRendererIn);
            this.livingEntityRenderer = livingEntityRendererIn;
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityFilchLizard entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ItemStack itemStack = entity.getHeldItemMainhand();
            if (!itemStack.isEmpty()) {
                matrixStackIn.push();
                if (this.livingEntityRenderer.getEntityModel().isChild) {
                    matrixStackIn.translate(0.0F, 0.625F, 0.0F);
                    matrixStackIn.rotate(Vector3f.XN.rotationDegrees(-20.0F));
                    matrixStackIn.scale(0.5F, 0.5F, 0.5F);
                }
                if (!entity.getHeldItemMainhand().isEmpty()) {
                    this.renderHeldItemLizard(matrixStackIn, entity, itemStack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, bufferIn, packedLightIn);
                }
                matrixStackIn.pop();
            }
        }

        public void renderHeldItemLizard(MatrixStack matrixStackIn, LivingEntity entity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, IRenderTypeBuffer bufferIn, int packedLightIn) {
            if (!itemStack.isEmpty()) {
                matrixStackIn.push();
                if (entity.isSneaking()) {
                    matrixStackIn.translate(0.0F, 0.2F, 0.0F);
                }
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(180.0F));
                matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(20.0F));
                matrixStackIn.translate(-0.55F, -1.0F, -0.05F);
                Minecraft.getInstance().getFirstPersonRenderer().renderItemSide(entity, itemStack, transformType, true, matrixStackIn, bufferIn, packedLightIn);
                matrixStackIn.pop();
            }
        }

        public boolean shouldCombineTextures() {
            return false;
        }
    }
}
