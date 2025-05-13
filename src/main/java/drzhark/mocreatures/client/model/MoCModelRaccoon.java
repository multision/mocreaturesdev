/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.hunter.MoCEntityRaccoon;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelRaccoon<T extends MoCEntityRaccoon> extends EntityModel<T> {

    private final float radianF = 57.29578F;
    ModelRenderer Head;
    ModelRenderer Snout;
    ModelRenderer RightEar;
    ModelRenderer LeftEar;
    ModelRenderer LeftSideburn;
    ModelRenderer RightSideburn;
    ModelRenderer RightRearFoot;
    ModelRenderer Neck;
    ModelRenderer Body;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer RightFrontLegA;
    ModelRenderer RightFrontLegB;
    ModelRenderer RightFrontFoot;
    ModelRenderer LeftFrontLegA;
    ModelRenderer LeftFrontLegB;
    ModelRenderer LeftFrontFoot;
    ModelRenderer RightRearLegA;
    ModelRenderer RightRearLegB;
    ModelRenderer LeftRearLegB;
    ModelRenderer LeftRearLegA;
    ModelRenderer LeftRearFoot;

    public MoCModelRaccoon() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.Head = new ModelRenderer(this, 38, 21);
        this.Head.addBox(-4F, -3.5F, -6.5F, 8, 6, 5);
        this.Head.setRotationPoint(0F, 17F, -4F);

        /*
         * RightSideburn = new ModelRenderer(this, 0, 32);
         * RightSideburn.addBox(-6.7F, -1.5F, -3.6F, 3, 4, 4);
         * RightSideburn.setRotationPoint(0F, 17F, -4F);
         * setRotation(RightSideburn, 0F, -0.5235988F, 0F); LeftSideburn = new
         * ModelRenderer(this, 0, 40); LeftSideburn.addBox(3.7F, -1.5F, -3.6F,
         * 3, 4, 4); LeftSideburn.setRotationPoint(0F, 17F, -4F);
         * setRotation(LeftSideburn, 0F, 0.5235988F, 0F);
         */

        this.RightSideburn = new ModelRenderer(this, 0, 32);
        this.RightSideburn.addBox(-3F, -2F, -2F, 3, 4, 4);
        this.RightSideburn.setRotationPoint(-2.5F, 0.5F, -3.2F);
        setRotation(this.RightSideburn, 0F, -0.5235988F, 0F);
        this.Head.addChild(this.RightSideburn);

        this.LeftSideburn = new ModelRenderer(this, 0, 40);
        this.LeftSideburn.addBox(0F, -2F, -2F, 3, 4, 4);
        this.LeftSideburn.setRotationPoint(2.5F, 0.5F, -3.2F);
        setRotation(this.LeftSideburn, 0F, 0.5235988F, 0F);
        this.Head.addChild(this.LeftSideburn);

        this.Snout = new ModelRenderer(this, 24, 25);
        this.Snout.addBox(-1.5F, -0.5F, -10.5F, 3, 3, 4);
        this.Snout.setRotationPoint(0F, 17F, -4F);

        this.RightEar = new ModelRenderer(this, 24, 22);
        this.RightEar.addBox(-4F, -5.5F, -3.5F, 3, 2, 1);
        this.RightEar.setRotationPoint(0F, 17F, -4F);

        this.LeftEar = new ModelRenderer(this, 24, 18);
        this.LeftEar.addBox(1F, -5.5F, -3.5F, 3, 2, 1);
        this.LeftEar.setRotationPoint(0F, 17F, -4F);

        this.RightRearFoot = new ModelRenderer(this, 46, 0);
        this.RightRearFoot.addBox(-5F, 5F, -2F, 3, 1, 3);
        this.RightRearFoot.setRotationPoint(0F, 18F, 4F);

        this.Neck = new ModelRenderer(this, 46, 4);
        this.Neck.addBox(-2.5F, -2F, -3F, 5, 4, 3);
        this.Neck.setRotationPoint(0F, 17F, -4F);
        setRotation(this.Neck, -0.4461433F, 0F, 0F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-3F, 0F, -3F, 6, 6, 12);
        this.Body.setRotationPoint(0F, 15F, -2F);

        this.TailA = new ModelRenderer(this, 0, 3);
        this.TailA.addBox(-1.5F, -6F, -1.5F, 3, 6, 3);
        this.TailA.setRotationPoint(0F, 16.5F, 6.5F);
        setRotation(this.TailA, -2.024582F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 24, 3);
        this.TailB.addBox(-1.5F, -11F, 0.3F, 3, 6, 3);
        this.TailB.setRotationPoint(0F, 16.5F, 6.5F);
        setRotation(this.TailB, -1.689974F, 0F, 0F);

        this.RightFrontLegA = new ModelRenderer(this, 36, 0);
        this.RightFrontLegA.addBox(-4F, -1F, -1F, 2, 5, 3);
        this.RightFrontLegA.setRotationPoint(0F, 18F, -4F);
        setRotation(this.RightFrontLegA, 0.5205006F, 0F, 0F);

        this.RightFrontLegB = new ModelRenderer(this, 46, 11);
        this.RightFrontLegB.addBox(-3.5F, 1F, 2F, 2, 4, 2);
        this.RightFrontLegB.setRotationPoint(0F, 18F, -4F);
        setRotation(this.RightFrontLegB, -0.3717861F, 0F, 0F);

        this.RightFrontFoot = new ModelRenderer(this, 46, 0);
        this.RightFrontFoot.addBox(-4F, 5F, -1F, 3, 1, 3);
        this.RightFrontFoot.setRotationPoint(0F, 18F, -4F);

        this.LeftFrontLegA = new ModelRenderer(this, 36, 8);
        this.LeftFrontLegA.addBox(2F, -1F, -1F, 2, 5, 3);
        this.LeftFrontLegA.setRotationPoint(0F, 18F, -4F);
        setRotation(this.LeftFrontLegA, 0.5205006F, 0F, 0F);

        this.LeftFrontLegB = new ModelRenderer(this, 54, 11);
        this.LeftFrontLegB.addBox(1.5F, 1F, 2F, 2, 4, 2);
        this.LeftFrontLegB.setRotationPoint(0F, 18F, -4F);
        setRotation(this.LeftFrontLegB, -0.3717861F, 0F, 0F);

        this.LeftFrontFoot = new ModelRenderer(this, 46, 0);
        this.LeftFrontFoot.addBox(1F, 5F, -1F, 3, 1, 3);
        this.LeftFrontFoot.setRotationPoint(0F, 18F, -4F);

        this.RightRearLegA = new ModelRenderer(this, 12, 18);
        this.RightRearLegA.addBox(-5F, -2F, -3F, 2, 5, 4);
        this.RightRearLegA.setRotationPoint(0F, 18F, 4F);
        setRotation(this.RightRearLegA, 0.9294653F, 0F, 0F);

        this.RightRearLegB = new ModelRenderer(this, 0, 27);
        this.RightRearLegB.addBox(-4.5F, 2F, -5F, 2, 2, 3);
        this.RightRearLegB.setRotationPoint(0F, 18F, 4F);
        setRotation(this.RightRearLegB, 0.9294653F, 0F, 0F);

        this.LeftRearLegB = new ModelRenderer(this, 10, 27);
        this.LeftRearLegB.addBox(2.5F, 2F, -5F, 2, 2, 3);
        this.LeftRearLegB.setRotationPoint(0F, 18F, 4F);
        setRotation(this.LeftRearLegB, 0.9294653F, 0F, 0F);

        this.LeftRearLegA = new ModelRenderer(this, 0, 18);
        this.LeftRearLegA.addBox(3F, -2F, -3F, 2, 5, 4);
        this.LeftRearLegA.setRotationPoint(0F, 18F, 4F);
        setRotation(this.LeftRearLegA, 0.9294653F, 0F, 0F);

        this.LeftRearFoot = new ModelRenderer(this, 46, 0);
        this.LeftRearFoot.addBox(2F, 5F, -2F, 3, 1, 3);
        this.LeftRearFoot.setRotationPoint(0F, 18F, 4F);

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Snout.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightEar.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftEar.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        //LeftSideburn.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        //RightSideburn.renderWithRotation(f5);
        this.RightRearFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.TailA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.TailB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightFrontLegA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightFrontLegB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightFrontFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftFrontLegA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftFrontLegB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftFrontFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightRearLegA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightRearLegB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftRearLegB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftRearLegA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftRearFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.rotateAngleY = netHeadYaw / 57.29578F;
        this.Head.rotateAngleX = headPitch / 57.29578F;
        this.Snout.rotateAngleY = this.Head.rotateAngleY;
        this.Snout.rotateAngleX = this.Head.rotateAngleX;
        this.RightEar.rotateAngleX = this.Head.rotateAngleX;
        this.RightEar.rotateAngleY = this.Head.rotateAngleY;
        this.LeftEar.rotateAngleX = this.Head.rotateAngleX;
        this.LeftEar.rotateAngleY = this.Head.rotateAngleY;
        //RightSideburn.rotateAngleX = Head.rotateAngleX;
        //RightSideburn.rotateAngleY = (-30F/radianF) + Head.rotateAngleY;
        //LeftSideburn.rotateAngleX = Head.rotateAngleX;
        //LeftSideburn.rotateAngleY = (30F/radianF) + Head.rotateAngleY;

        float RLegXRot = MathHelper.cos((limbSwing) + 3.141593F) * 0.8F * limbSwingAmount;
        float LLegXRot = MathHelper.cos(limbSwing) * 0.8F * limbSwingAmount;

        this.RightFrontLegA.rotateAngleX = (30F / this.radianF) + RLegXRot;
        this.LeftFrontLegA.rotateAngleX = (30F / this.radianF) + LLegXRot;
        this.RightRearLegA.rotateAngleX = (53F / this.radianF) + LLegXRot;
        this.LeftRearLegA.rotateAngleX = (53F / this.radianF) + RLegXRot;

        this.RightFrontLegB.rotateAngleX = (-21F / this.radianF) + RLegXRot;
        this.RightFrontFoot.rotateAngleX = RLegXRot;
        this.LeftFrontLegB.rotateAngleX = (-21F / this.radianF) + LLegXRot;
        this.LeftFrontFoot.rotateAngleX = LLegXRot;

        this.RightRearLegB.rotateAngleX = (53F / this.radianF) + LLegXRot;
        this.RightRearFoot.rotateAngleX = LLegXRot;
        this.LeftRearLegB.rotateAngleX = (53F / this.radianF) + RLegXRot;
        this.LeftRearFoot.rotateAngleX = RLegXRot;

        this.TailA.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;
        this.TailB.rotateAngleY = this.TailA.rotateAngleY;
    }
}
