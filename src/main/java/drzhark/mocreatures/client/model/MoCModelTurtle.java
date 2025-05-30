/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;


public class MoCModelTurtle<T extends MoCEntityTurtle> extends EntityModel<T> {

    public boolean isHiding;
    public boolean upsidedown;
    public float swingProgress;
    ModelRenderer Shell;
    ModelRenderer ShellUp;
    ModelRenderer ShellTop;
    ModelRenderer Belly;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Head;
    ModelRenderer Tail;
    private boolean turtleHat;
    private boolean TMNT;
    private boolean isSwimming;

    public MoCModelTurtle() {
        this.Shell = new ModelRenderer(this, 28, 0);
        this.Shell.addBox(0F, 0F, 0F, 9, 1, 9);
        this.Shell.setRotationPoint(-4.5F, 19F, -4.5F);

        this.ShellUp = new ModelRenderer(this, 0, 22);
        this.ShellUp.addBox(0F, 0F, 0F, 8, 2, 8);
        this.ShellUp.setRotationPoint(-4F, 17F, -4F);

        this.ShellTop = new ModelRenderer(this, 40, 10);
        this.ShellTop.addBox(0F, 0F, 0F, 6, 1, 6);
        this.ShellTop.setRotationPoint(-3F, 16F, -3F);

        this.Belly = new ModelRenderer(this, 0, 12);
        this.Belly.addBox(0F, 0F, 0F, 8, 1, 8);
        this.Belly.setRotationPoint(-4F, 20F, -4F);

        this.Leg1 = new ModelRenderer(this, 0, 0);
        this.Leg1.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Leg1.setRotationPoint(3.5F, 20F, -3.5F);

        this.Leg2 = new ModelRenderer(this, 0, 9);
        this.Leg2.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Leg2.setRotationPoint(-3.5F, 20F, -3.5F);

        this.Leg3 = new ModelRenderer(this, 0, 0);
        this.Leg3.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Leg3.setRotationPoint(3.5F, 20F, 3.5F);

        this.Leg4 = new ModelRenderer(this, 0, 9);
        this.Leg4.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Leg4.setRotationPoint(-3.5F, 20F, 3.5F);

        this.Head = new ModelRenderer(this, 10, 0);
        this.Head.addBox(-1.5F, -1F, -4F, 3, 2, 4);
        this.Head.setRotationPoint(0F, 20F, -4.5F);

        this.Tail = new ModelRenderer(this, 0, 5);
        this.Tail.addBox(-1F, -1F, 0F, 2, 1, 3);
        this.Tail.setRotationPoint(0F, 21F, 4F);

    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.TMNT = entityIn.isTMNT();
        this.turtleHat = entityIn.getRidingEntity() != null;
        this.isSwimming = entityIn.isInWater();
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Shell.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.ShellUp.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (!this.TMNT) {
            this.ShellTop.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        this.Belly.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);
        if (this.upsidedown) {
            float f25 = this.swingProgress;
            float f26 = f25;
            if (f25 >= 0.8F) {
                f26 = (0.6F - (f25 - 0.8F));
            }
            if (f26 > 1.6F) {
                f26 = 1.6F;
            }
            if (f26 < -1.6F) {
                f26 = -1.6F;
            }
            this.Leg1.rotateAngleX = 0F - f26;
            this.Leg2.rotateAngleX = 0F + f26;
            this.Leg3.rotateAngleX = 0F + f26;
            this.Leg4.rotateAngleX = 0F - f26;
            this.Tail.rotateAngleY = 0F - f26;

        } else if (this.turtleHat) {
            this.Leg1.rotateAngleX = 0F;
            this.Leg2.rotateAngleX = 0F;
            this.Leg3.rotateAngleX = 0F;
            this.Leg4.rotateAngleX = 0F;
            this.Tail.rotateAngleY = 0F;
        } else if (this.isSwimming) {
            float swimmLegs = MathHelper.cos(limbSwing * 0.5F) * 6.0F * limbSwingAmount;
            this.Leg1.rotateAngleX = -1.2F;
            this.Leg1.rotateAngleY = -1.2F + swimmLegs;
            this.Leg2.rotateAngleX = -1.2F;
            this.Leg2.rotateAngleY = 1.2F - swimmLegs;
            this.Leg3.rotateAngleX = 1.2F;
            this.Leg4.rotateAngleX = 1.2F;//swimmLegs;
            this.Tail.rotateAngleY = 0F;
        } else {
            this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 2.0F) * 2.0F * limbSwingAmount;
            this.Leg2.rotateAngleX = MathHelper.cos((limbSwing * 2.0F) + 3.141593F) * 2.0F * limbSwingAmount;
            this.Leg3.rotateAngleX = MathHelper.cos((limbSwing * 2.0F) + 3.141593F) * 2.0F * limbSwingAmount;
            this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 2.0F) * 2.0F * limbSwingAmount;
            this.Tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * limbSwingAmount;

            this.Leg1.rotateAngleY = 0F;
            this.Leg2.rotateAngleY = 0F;
        }

        if (this.isHiding && !this.isSwimming) {
            this.Head.rotateAngleX = 0F;
            this.Head.rotateAngleY = 0F;

            //Leg1.setRotationPoint(2.9F, 18.5F, -2.9F);
            this.Leg1.rotationPointX = 2.9F;
            this.Leg1.rotationPointY = 18.5F;
            this.Leg1.rotationPointZ = -2.9F;

            //Leg2.setRotationPoint(-2.9F, 18.5F, -2.9F);
            this.Leg2.rotationPointX = -2.9F;
            this.Leg2.rotationPointY = 18.5F;
            this.Leg2.rotationPointZ = -2.9F;

            //Leg3.setRotationPoint(2.9F, 18.5F, 2.9F);
            this.Leg3.rotationPointX = 2.9F;
            this.Leg3.rotationPointY = 18.5F;
            this.Leg3.rotationPointZ = 2.9F;

            //Leg4.setRotationPoint(-2.9F, 18.5F, 2.9F);
            this.Leg4.rotationPointX = -2.9F;
            this.Leg4.rotationPointY = 18.5F;
            this.Leg4.rotationPointZ = 2.9F;

            //Head.setRotationPoint(0F, 20F, -1F);
            //Head.rotationPointX = 0F;
            this.Head.rotationPointY = 19.5F;
            this.Head.rotationPointZ = -1F;

            //Tail.setRotationPoint(0F, 21F, 2F);
            //Tail.rotationPointX = 0F;
            //Tail.rotationPointY = 21F;
            this.Tail.rotationPointZ = 2F;
        } else {
            this.Head.rotateAngleX = headPitch / 57.29578F;
            this.Head.rotateAngleY = netHeadYaw / 57.29578F;

            //Leg1.setRotationPoint(3.5F, 20F, -3.5F);
            this.Leg1.rotationPointX = 3.5F;
            this.Leg1.rotationPointY = 20F;
            this.Leg1.rotationPointZ = -3.5F;

            //Leg2.setRotationPoint(-3.5F, 20F, -3.5F);
            this.Leg2.rotationPointX = -3.5F;
            this.Leg2.rotationPointY = 20F;
            this.Leg2.rotationPointZ = -3.5F;

            //Leg3.setRotationPoint(3.5F, 20F, 3.5F);
            this.Leg3.rotationPointX = 3.5F;
            this.Leg3.rotationPointY = 20F;
            this.Leg3.rotationPointZ = 3.5F;

            //Leg4.setRotationPoint(-3.5F, 20F, 3.5F);
            this.Leg4.rotationPointX = -3.5F;
            this.Leg4.rotationPointY = 20F;
            this.Leg4.rotationPointZ = 3.5F;

            //Head.setRotationPoint(0F, 20F, -4.5F);
            //Head.rotationPointX = 0F;
            this.Head.rotationPointY = 20F;
            this.Head.rotationPointZ = -4.5F;

            //Tail.setRotationPoint(0F, 21F, 4F);
            //Tail.rotationPointX = 0F;
            //Tail.rotationPointY = 21F;
            this.Tail.rotationPointZ = 4F;
        }
    }
}
