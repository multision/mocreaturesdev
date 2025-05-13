/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntityFly;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelFly<T extends MoCEntityFly> extends EntityModel<T> {

    ModelRenderer FrontLegs;
    ModelRenderer RearLegs;
    ModelRenderer MidLegs;
    ModelRenderer FoldedWings;
    ModelRenderer Head;
    ModelRenderer Tail;
    ModelRenderer Abdomen;
    ModelRenderer RightWing;
    ModelRenderer Thorax;
    ModelRenderer LeftWing;
    private boolean flying;

    public MoCModelFly() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        this.Head.setRotationPoint(0F, 21.5F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setRotationPoint(0F, 20.5F, -1F);
        setRotation(this.Thorax, 0F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Abdomen.setRotationPoint(0F, 21.5F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.Tail = new ModelRenderer(this, 10, 2);
        this.Tail.addBox(-1F, 0F, -1F, 1, 1, 1);
        this.Tail.setRotationPoint(0.5F, 21.2F, 1.5F);
        setRotation(this.Tail, 1.427659F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 7);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.FrontLegs.setRotationPoint(0F, 22.5F, -1.8F);
        setRotation(this.FrontLegs, 0.1487144F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 0, 11);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.RearLegs.setRotationPoint(0F, 22.5F, -0.4F);
        setRotation(this.RearLegs, 1.070744F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 0, 9);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.MidLegs.setRotationPoint(0F, 22.5F, -1.2F);
        setRotation(this.MidLegs, 0.5948578F, 0F, 0F);

        /*
         * RightWing = new ModelRenderer(this, 10, 8); RightWing.addBox(-4F,
         * -2F, 0F, 3, 4, 0); RightWing.setRotationPoint(0F, 14.5F, -1F);
         * LeftWing = new ModelRenderer(this, 4, 8); LeftWing.addBox(1F, -2F,
         * 0F, 3, 4, 0); LeftWing.setRotationPoint(0F, 14.5F, -1F);
         */

        this.LeftWing = new ModelRenderer(this, 4, 4);
        this.LeftWing.addBox(-1F, 0F, 0.5F, 2, 0, 4);
        this.LeftWing.setRotationPoint(0F, 20.4F, -1F);
        setRotation(this.LeftWing, 0F, 1.047198F, 0F);

        this.RightWing = new ModelRenderer(this, 4, 4);
        this.RightWing.addBox(-1F, 0F, 0.5F, 2, 0, 4);
        this.RightWing.setRotationPoint(0F, 20.4F, -1F);
        setRotation(this.RightWing, 0F, -1.047198F, 0F);

        this.FoldedWings = new ModelRenderer(this, 4, 4);
        this.FoldedWings.addBox(-1F, 0F, 0F, 2, 0, 4);
        this.FoldedWings.setRotationPoint(0F, 20.5F, -2F);
        setRotation(this.FoldedWings, 0.0872665F, 0F, 0F);

    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.flying = (entityIn.getIsFlying() || entityIn.getMotion().getY() < -0.1D);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.FrontLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RearLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.MidLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Abdomen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Thorax.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (!this.flying) {
            this.FoldedWings.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            matrixStackIn.push();
            RenderSystem.enableBlend();
            float transparency = 0.6F;
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(0.8F, 0.8F, 0.8F, transparency);
            this.LeftWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.RightWing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            RenderSystem.disableBlend();
            matrixStackIn.pop();
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    /*
     * public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw,
     * float headPitch, float f5) { super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);
     * float WingRot = MathHelper.cos((ageInTicks * 3.0F)) * 0.7F;
     * RightWing.rotateAngleZ = WingRot; LeftWing.rotateAngleZ = -WingRot; float
     * legMov = (limbSwingAmount*1.5F); FrontLegs.rotateAngleX = 0.1487144F + legMov;
     * MidLegs.rotateAngleX = 0.5948578F + legMov; RearLegs.rotateAngleX =
     * 1.070744F + legMov; }
     */

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);
        float WingRot = MathHelper.cos((ageInTicks * 3.0F)) * 0.7F;
        this.RightWing.rotateAngleZ = WingRot;
        this.LeftWing.rotateAngleZ = -WingRot;
        float legMov;
        float legMovB;

        if (this.flying) {
            legMov = (limbSwingAmount * 1.5F);
            legMovB = legMov;
        } else {
            legMov = MathHelper.cos((limbSwing * 1.5F) + 3.141593F) * 2.0F * limbSwingAmount;
            legMovB = MathHelper.cos(limbSwing * 1.5F) * 2.0F * limbSwingAmount;
        }

        this.FrontLegs.rotateAngleX = 0.1487144F + legMov;
        this.MidLegs.rotateAngleX = 0.5948578F + legMovB;
        this.RearLegs.rotateAngleX = 1.070744F + legMov;
    }
}
