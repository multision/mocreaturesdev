/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelJellyFish<T extends MoCEntityJellyFish> extends EntityModel<T> {

    ModelRenderer Top;
    ModelRenderer Head;
    ModelRenderer HeadSmall;
    ModelRenderer Body;
    ModelRenderer BodyCenter;
    ModelRenderer BodyBottom;
    ModelRenderer Side1;
    ModelRenderer Side2;
    ModelRenderer Side3;
    ModelRenderer Side4;
    ModelRenderer LegSmall1;
    ModelRenderer LegC1;
    ModelRenderer LegC2;
    ModelRenderer LegC3;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Leg5;
    ModelRenderer Leg6;
    ModelRenderer Leg7;
    ModelRenderer Leg8;
    ModelRenderer Leg9;
    private boolean glowing;
    private boolean outOfWater;
    private float limbSwingAmount;

    public MoCModelJellyFish() {
        this.textureWidth = 64;
        this.textureHeight = 16;

        this.Top = new ModelRenderer(this, 0, 10);
        this.Top.addBox(-2.5F, 0F, -2.5F, 5, 1, 5);
        this.Top.setRotationPoint(0F, 11F, 0F);

        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-4F, 0F, -4F, 8, 2, 8);
        this.Head.setRotationPoint(0F, 12F, 0F);

        this.HeadSmall = new ModelRenderer(this, 24, 0);
        this.HeadSmall.addBox(-2F, 0F, -2F, 4, 3, 4);
        this.HeadSmall.setRotationPoint(0F, 12.5F, 0F);

        this.Body = new ModelRenderer(this, 36, 0);
        this.Body.addBox(-3.5F, 0F, -3.5F, 7, 7, 7);
        this.Body.setRotationPoint(0F, 13.8F, 0F);

        this.BodyCenter = new ModelRenderer(this, 0, 0);
        this.BodyCenter.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.BodyCenter.setRotationPoint(0F, 15.5F, 0F);

        this.BodyBottom = new ModelRenderer(this, 20, 10);
        this.BodyBottom.addBox(-2F, 0F, -2F, 4, 2, 4);
        this.BodyBottom.setRotationPoint(0F, 18.3F, 0F);

        this.Side1 = new ModelRenderer(this, 20, 10);
        this.Side1.addBox(-2F, 5F, 0F, 4, 2, 4);
        this.Side1.setRotationPoint(0F, 12.5F, 0F);
        setRotation(this.Side1, -0.7679449F, 0F, 0F);

        this.Side2 = new ModelRenderer(this, 20, 10);
        this.Side2.addBox(-4F, 5F, -2F, 4, 2, 4);
        this.Side2.setRotationPoint(0F, 12.5F, 0F);
        setRotation(this.Side2, 0F, 0F, -0.7679449F);

        this.Side3 = new ModelRenderer(this, 20, 10);
        this.Side3.addBox(0F, 5F, -2F, 4, 2, 4);
        this.Side3.setRotationPoint(0F, 12.5F, 0F);
        setRotation(this.Side3, 0F, 0F, 0.7679449F);

        this.Side4 = new ModelRenderer(this, 20, 10);
        this.Side4.addBox(-2F, 5F, -4F, 4, 2, 4);
        this.Side4.setRotationPoint(0F, 12.5F, 0F);
        setRotation(this.Side4, 0.7679449F, 0F, 0F);

        this.LegSmall1 = new ModelRenderer(this, 60, 2);
        this.LegSmall1.addBox(-1F, 0F, -1F, 1, 3, 1);
        this.LegSmall1.setRotationPoint(0F, 18.5F, 0F);

        this.LegC1 = new ModelRenderer(this, 15, 10);
        this.LegC1.addBox(-1F, 0F, -1F, 1, 4, 1);
        this.LegC1.setRotationPoint(-0.5F, 15.5F, -0.5F);
        setRotation(this.LegC1, -0.2602503F, 0F, 0.1487144F);

        this.LegC2 = new ModelRenderer(this, 15, 10);
        this.LegC2.addBox(-1F, 0F, 0F, 1, 4, 1);
        this.LegC2.setRotationPoint(0.5F, 15.5F, -0.5F);
        setRotation(this.LegC2, 0.1487144F, 1.747395F, 0F);

        this.LegC3 = new ModelRenderer(this, 15, 10);
        this.LegC3.addBox(-1F, 0F, 0F, 1, 4, 1);
        this.LegC3.setRotationPoint(-0.5F, 15.5F, 0.5F);
        setRotation(this.LegC3, 0.1115358F, 0.3717861F, 0.2230717F);

        this.Leg1 = new ModelRenderer(this, 0, 10);
        this.Leg1.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        this.Leg1.setRotationPoint(0F, 20F, 2.5F);

        this.Leg2 = new ModelRenderer(this, 0, 10);
        this.Leg2.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        this.Leg2.setRotationPoint(0F, 20F, -2.5F);

        this.Leg3 = new ModelRenderer(this, 0, 10);
        this.Leg3.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        this.Leg3.setRotationPoint(2.5F, 20F, 0F);

        this.Leg4 = new ModelRenderer(this, 0, 10);
        this.Leg4.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        this.Leg4.setRotationPoint(-2.5F, 20F, 0F);

        this.Leg5 = new ModelRenderer(this, 0, 10);
        this.Leg5.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        this.Leg5.setRotationPoint(2F, 20F, 2F);
        setRotation(this.Leg5, 0F, 0.7853982F, 0F);

        this.Leg6 = new ModelRenderer(this, 0, 10);
        this.Leg6.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        this.Leg6.setRotationPoint(2F, 20F, -2F);
        setRotation(this.Leg6, 0F, 0.7853982F, 0F);

        this.Leg7 = new ModelRenderer(this, 0, 10);
        this.Leg7.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        this.Leg7.setRotationPoint(-2F, 20F, -2F);
        setRotation(this.Leg7, 0F, 0.7853982F, 0F);

        this.Leg8 = new ModelRenderer(this, 60, 0);
        this.Leg8.addBox(0F, 0F, 0F, 1, 5, 1);
        this.Leg8.setRotationPoint(0F, 18.5F, 0F);

        this.Leg9 = new ModelRenderer(this, 0, 10);
        this.Leg9.addBox(-0.5F, 0F, -0.5F, 1, 4, 1);
        this.Leg9.setRotationPoint(-2F, 20F, 2F);
        setRotation(this.Leg9, 0F, 0.7853982F, 0F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.glowing = entityIn.isGlowing();
        this.outOfWater = !entityIn.isInWater();
        this.limbSwingAmount = limbSwingAmount;
    }
    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();
        if (outOfWater) {
            matrixStackIn.translate(0F, 0.6F, -0.3F);
        } else {
            matrixStackIn.translate(0F, 0.2F, 0F);
            matrixStackIn.rotate(Vector3f.XN.rotationDegrees(this.limbSwingAmount * -60F));
        }
        RenderSystem.enableBlend();
        if (!glowing || outOfWater) {
            float transparency = 0.7F;
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(0.8F, 0.8F, 0.8F, transparency);
        } else {
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }
        this.Top.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.HeadSmall.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.BodyCenter.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.BodyBottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Side1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Side2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Side3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Side4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LegSmall1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LegC1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LegC2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LegC3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        RenderSystem.disableBlend();
        matrixStackIn.pop();
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float f6 = limbSwingAmount * 2.0F;
        if (f6 > 1.0F) {
            f6 = 1.0F;
        }

        this.LegSmall1.rotateAngleX = f6;
        this.LegC1.rotateAngleX = f6;
        this.LegC2.rotateAngleX = f6;
        this.LegC3.rotateAngleX = f6;
        this.Leg1.rotateAngleX = f6;
        this.Leg2.rotateAngleX = f6;
        this.Leg3.rotateAngleX = f6;
        this.Leg4.rotateAngleX = f6;
        this.Leg5.rotateAngleX = f6;
        this.Leg6.rotateAngleX = f6;
        this.Leg7.rotateAngleX = f6;
        this.Leg8.rotateAngleX = f6;
        this.Leg9.rotateAngleX = f6;
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}
