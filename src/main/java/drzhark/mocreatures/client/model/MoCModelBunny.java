/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MoCModelBunny<T extends MoCEntityBunny> extends EntityModel<T> {

    public ModelRenderer part1;
    public ModelRenderer part2;
    public ModelRenderer part3;
    public ModelRenderer part4;
    public ModelRenderer part5;
    public ModelRenderer part6;
    public ModelRenderer part7;
    public ModelRenderer part8;
    public ModelRenderer part9;
    public ModelRenderer part10;
    public ModelRenderer part11;

    public MoCModelBunny() {
        byte byte0 = 16;
        this.part1 = new ModelRenderer(this, 0, 0);
        this.part1.addBox(-2F, -1F, -4F, 4, 4, 6, 0.0F);
        this.part1.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part8 = new ModelRenderer(this, 14, 0);
        this.part8.addBox(-2F, -5F, -3F, 1, 4, 2, 0.0F);
        this.part8.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part9 = new ModelRenderer(this, 14, 0);
        this.part9.addBox(1.0F, -5F, -3F, 1, 4, 2, 0.0F);
        this.part9.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part10 = new ModelRenderer(this, 20, 0);
        this.part10.addBox(-4F, 0.0F, -3F, 2, 3, 2, 0.0F);
        this.part10.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part11 = new ModelRenderer(this, 20, 0);
        this.part11.addBox(2.0F, 0.0F, -3F, 2, 3, 2, 0.0F);
        this.part11.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.part2 = new ModelRenderer(this, 0, 10);
        this.part2.addBox(-3F, -4F, -3F, 6, 8, 6, 0.0F);
        this.part2.setRotationPoint(0.0F, byte0, 0.0F);
        this.part3 = new ModelRenderer(this, 0, 24);
        this.part3.addBox(-2F, 4F, -2F, 4, 3, 4, 0.0F);
        this.part3.setRotationPoint(0.0F, byte0, 0.0F);
        this.part4 = new ModelRenderer(this, 24, 16);
        this.part4.addBox(-2F, 0.0F, -1F, 2, 2, 2);
        this.part4.setRotationPoint(3F, 3 + byte0, -3F);
        this.part5 = new ModelRenderer(this, 24, 16);
        this.part5.addBox(0.0F, 0.0F, -1F, 2, 2, 2);
        this.part5.setRotationPoint(-3F, 3 + byte0, -3F);
        this.part6 = new ModelRenderer(this, 16, 24);
        this.part6.addBox(-2F, 0.0F, -4F, 2, 2, 4);
        this.part6.setRotationPoint(3F, 3 + byte0, 4F);
        this.part7 = new ModelRenderer(this, 16, 24);
        this.part7.addBox(0.0F, 0.0F, -4F, 2, 2, 4);
        this.part7.setRotationPoint(-3F, 3 + byte0, 4F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.part1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part8.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part9.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part10.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part11.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part6.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.part7.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.part1.rotateAngleX = -(headPitch / 57.29578F);
        this.part1.rotateAngleY = netHeadYaw / 57.29578F;

        this.part8.rotateAngleX = this.part1.rotateAngleX;
        this.part8.rotateAngleY = this.part1.rotateAngleY;
        this.part9.rotateAngleX = this.part1.rotateAngleX;
        this.part9.rotateAngleY = this.part1.rotateAngleY;
        this.part10.rotateAngleX = this.part1.rotateAngleX;
        this.part10.rotateAngleY = this.part1.rotateAngleY;
        this.part11.rotateAngleX = this.part1.rotateAngleX;
        this.part11.rotateAngleY = this.part1.rotateAngleY;
        this.part2.rotateAngleX = 1.570796F;
        this.part3.rotateAngleX = 1.570796F;
        if (entityIn.getRidingEntity() == null) {
            this.part4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
            this.part6.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.2F * limbSwingAmount;
            this.part5.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.0F * limbSwingAmount;
            this.part7.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.2F * limbSwingAmount;
        }
    }
}
