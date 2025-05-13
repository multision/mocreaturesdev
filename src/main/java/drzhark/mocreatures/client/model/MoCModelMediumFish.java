/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelMediumFish<T extends MoCEntityMediumFish> extends EntityModel<T> {

    //fields
    ModelRenderer Head;
    ModelRenderer LowerHead;
    ModelRenderer Nose;
    ModelRenderer MouthBottom;
    ModelRenderer MouthBottomB;
    ModelRenderer Body;
    ModelRenderer BackUp;
    ModelRenderer BackDown;
    ModelRenderer Tail;
    ModelRenderer TailFin;
    ModelRenderer RightPectoralFin;
    ModelRenderer LeftPectoralFin;
    ModelRenderer UpperFin;
    ModelRenderer LowerFin;
    ModelRenderer RightLowerFin;
    ModelRenderer LeftLowerFin;
    private MoCEntityMediumFish mediumFish;

    public MoCModelMediumFish() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 10);
        this.Head.addBox(-5F, 0F, -1.5F, 5, 3, 3);
        this.Head.setRotationPoint(-8F, 6F, 0F);
        setRotation(this.Head, 0F, 0F, -0.4461433F);

        this.LowerHead = new ModelRenderer(this, 0, 16);
        this.LowerHead.addBox(-4F, -3F, -1.5F, 4, 3, 3);
        this.LowerHead.setRotationPoint(-8F, 12F, 0F);
        setRotation(this.LowerHead, 0F, 0F, 0.3346075F);

        this.Nose = new ModelRenderer(this, 14, 17);
        this.Nose.addBox(-1F, -1F, -1F, 1, 3, 2);
        this.Nose.setRotationPoint(-11F, 8.2F, 0F);
        setRotation(this.Nose, 0F, 0F, 1.412787F);

        this.MouthBottom = new ModelRenderer(this, 16, 10);
        this.MouthBottom.addBox(-2F, -0.4F, -1F, 2, 1, 2);
        this.MouthBottom.setRotationPoint(-11.5F, 10F, 0F);
        setRotation(this.MouthBottom, 0F, 0F, 0.3346075F);

        this.MouthBottomB = new ModelRenderer(this, 16, 13);
        this.MouthBottomB.addBox(-1.5F, -2.4F, -0.5F, 1, 1, 1);
        this.MouthBottomB.setRotationPoint(-11.5F, 10F, 0F);
        setRotation(this.MouthBottomB, 0F, 0F, -0.7132579F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0F, -3F, -2F, 9, 6, 4);
        this.Body.setRotationPoint(-8F, 9F, 0F);

        this.BackUp = new ModelRenderer(this, 26, 0);
        this.BackUp.addBox(0F, 0F, -1.5F, 8, 3, 3);
        this.BackUp.setRotationPoint(1F, 6F, 0F);
        setRotation(this.BackUp, 0F, 0F, 0.1858931F);

        this.BackDown = new ModelRenderer(this, 26, 6);
        this.BackDown.addBox(0F, -3F, -1.5F, 8, 3, 3);
        this.BackDown.setRotationPoint(1F, 12F, 0F);
        setRotation(this.BackDown, 0F, 0F, -0.1919862F);

        this.Tail = new ModelRenderer(this, 48, 0);
        this.Tail.addBox(0F, -1.5F, -1F, 4, 3, 2);
        this.Tail.setRotationPoint(8F, 9F, 0F);

        this.TailFin = new ModelRenderer(this, 48, 5);
        this.TailFin.addBox(3F, -5.3F, 0F, 5, 11, 0);
        this.TailFin.setRotationPoint(8F, 9F, 0F);

        this.RightPectoralFin = new ModelRenderer(this, 28, 12);
        this.RightPectoralFin.addBox(0F, -2F, 0F, 5, 4, 0);
        this.RightPectoralFin.setRotationPoint(-6.5F, 10F, 2F);
        setRotation(this.RightPectoralFin, 0F, -0.8726646F, 0.185895F);

        this.LeftPectoralFin = new ModelRenderer(this, 38, 12);
        this.LeftPectoralFin.addBox(0F, -2F, 0F, 5, 4, 0);
        this.LeftPectoralFin.setRotationPoint(-6.5F, 10F, -2F);
        setRotation(this.LeftPectoralFin, 0F, 0.8726646F, 0.1858931F);

        this.UpperFin = new ModelRenderer(this, 0, 22);
        this.UpperFin.addBox(0F, -4F, 0F, 15, 4, 0);
        this.UpperFin.setRotationPoint(-7F, 6F, 0F);
        setRotation(this.UpperFin, 0F, 0F, 0.1047198F);

        this.LowerFin = new ModelRenderer(this, 46, 20);
        this.LowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.LowerFin.setRotationPoint(0F, 12F, 0F);
        setRotation(this.LowerFin, 0F, 0F, -0.1858931F);

        this.RightLowerFin = new ModelRenderer(this, 28, 16);
        this.RightLowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.RightLowerFin.setRotationPoint(-7F, 12F, 1F);
        setRotation(this.RightLowerFin, 0.5235988F, 0F, 0F);

        this.LeftLowerFin = new ModelRenderer(this, 46, 16);
        this.LeftLowerFin.addBox(0F, 0F, 0F, 9, 4, 0);
        this.LeftLowerFin.setRotationPoint(-7F, 12F, -1F);
        setRotation(this.LeftLowerFin, -0.5235988F, 0F, 0F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.mediumFish = entityIn;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        float yOffset = mediumFish.getAdjustedYOffset();
        float xOffset = mediumFish.getAdjustedXOffset();
        float zOffset = mediumFish.getAdjustedZOffset();
        matrixStackIn.push();
        matrixStackIn.translate(xOffset, yOffset, zOffset);
        matrixStackIn.rotate(Vector3f.YP.rotationDegrees(90.0F));
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LowerHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Nose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.MouthBottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.MouthBottomB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.BackUp.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.BackDown.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.TailFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightPectoralFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftPectoralFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.UpperFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LowerFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightLowerFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftLowerFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        /*
         * limbSwing = distance walked limbSwingAmount = speed 0 - 1 ageInTicks = timer
         */
        //TailA.rotateAngleY = MathHelper.cos(ageInTicks * 0.7F);
        float tailMov = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount * 0.6F;
        float finMov = MathHelper.cos(ageInTicks * 0.2F) * 0.4F;
        float mouthMov = MathHelper.cos(ageInTicks * 0.3F) * 0.2F;

        this.Tail.rotateAngleY = tailMov;
        this.TailFin.rotateAngleY = tailMov;

        this.LeftPectoralFin.rotateAngleY = 0.8726646F + finMov;
        this.RightPectoralFin.rotateAngleY = -0.8726646F - finMov;

        this.MouthBottom.rotateAngleZ = 0.3346075F + mouthMov;
        this.MouthBottomB.rotateAngleZ = -0.7132579F + mouthMov;
        //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);
    }
}
