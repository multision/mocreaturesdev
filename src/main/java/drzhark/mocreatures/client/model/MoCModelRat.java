/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.hostile.MoCEntityRat;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;


public class MoCModelRat<T extends MoCEntityRat> extends EntityModel<T> {

    public ModelRenderer Head;

    public ModelRenderer EarR;

    public ModelRenderer EarL;

    public ModelRenderer WhiskerR;

    public ModelRenderer WhiskerL;

    public ModelRenderer Body;

    public ModelRenderer Tail;

    public ModelRenderer FrontL;

    public ModelRenderer FrontR;

    public ModelRenderer RearL;

    public ModelRenderer RearR;

    public ModelRenderer BodyF;

    public MoCModelRat() {
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-1.5F, -1.0F, -6.0F, 3, 4, 6, 0.0F);
        this.Head.setRotationPoint(0.0F, 19.0F, -6.0F);
        this.EarR = new ModelRenderer(this, 16, 26);
        this.EarR.addBox(-3.5F, -3.0F, -2.0F, 3, 3, 1, 0.0F);
        this.EarR.setRotationPoint(0.0F, 19F, -6.0F);
        this.EarL = new ModelRenderer(this, 24, 26);
        this.EarL.addBox(0.5F, -3.0F, -2.0F, 3, 3, 1, 0.0F);
        this.EarL.setRotationPoint(0.0F, 19F, -6.0F);
        this.WhiskerR = new ModelRenderer(this, 24, 16);
        this.WhiskerR.addBox(-4.5F, -1.0F, -6.0F, 3, 3, 1, 0.0F);
        this.WhiskerR.setRotationPoint(0.0F, 19.0F, -6.0F);
        this.WhiskerL = new ModelRenderer(this, 24, 20);
        this.WhiskerL.addBox(1.5F, -1.0F, -6.0F, 3, 3, 1, 0.0F);
        this.WhiskerL.setRotationPoint(0.0F, 19.0F, -6.0F);
        this.Body = new ModelRenderer(this, 24, 0);
        this.Body.addBox(-4.0F, -3.0F, -3.0F, 8, 8, 8, 0.0F);
        this.Body.setRotationPoint(0.0F, 20F, 2.0F);
        this.Body.rotateAngleX = 1.570796F;
        this.Tail = new ModelRenderer(this, 56, 0);
        this.Tail.addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2, 0.0F);
        this.Tail.setRotationPoint(0.0F, 20F, 7.0F);
        this.Tail.rotateAngleX = 1.570796F;
        this.FrontL = new ModelRenderer(this, 0, 18);
        this.FrontL.addBox(-2.0F, 0.0F, -3.0F, 2, 1, 4, 0.0F);
        this.FrontL.setRotationPoint(3.0F, 23.0F, -5.0F);
        this.FrontR = new ModelRenderer(this, 0, 18);
        this.FrontR.addBox(0.0F, 0.0F, -3.0F, 2, 1, 4, 0.0F);
        this.FrontR.setRotationPoint(-3.0F, 23.0F, -5.0F);
        this.RearL = new ModelRenderer(this, 0, 24);
        this.RearL.addBox(-2.0F, 0.0F, -4.0F, 2, 1, 5, 0.0F);
        this.RearL.setRotationPoint(4.0F, 23.0F, 4.0F);
        this.RearR = new ModelRenderer(this, 0, 24);
        this.RearR.addBox(0.0F, 0.0F, -4.0F, 2, 1, 5, 0.0F);
        this.RearR.setRotationPoint(-4.0F, 23.0F, 4.0F);
        this.BodyF = new ModelRenderer(this, 32, 16);
        this.BodyF.addBox(-3.0F, -3.0F, -7.0F, 6, 6, 6, 0.0F);
        this.BodyF.setRotationPoint(0.0F, 20F, 0.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.EarR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.EarL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.WhiskerR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.WhiskerL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.FrontL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.FrontR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RearL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RearR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.BodyF.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.rotateAngleX = -(headPitch / 57.29578F);
        this.Head.rotateAngleY = netHeadYaw / 57.29578F;
        this.EarR.rotateAngleX = this.Head.rotateAngleX;
        this.EarR.rotateAngleY = this.Head.rotateAngleY;
        this.EarL.rotateAngleX = this.Head.rotateAngleX;
        this.EarL.rotateAngleY = this.Head.rotateAngleY;
        this.WhiskerR.rotateAngleX = this.Head.rotateAngleX;
        this.WhiskerR.rotateAngleY = this.Head.rotateAngleY;
        this.WhiskerL.rotateAngleX = this.Head.rotateAngleX;
        this.WhiskerL.rotateAngleY = this.Head.rotateAngleY;
        this.FrontL.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;
        this.RearL.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 0.8F * limbSwingAmount;
        this.RearR.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.6F * limbSwingAmount;
        this.FrontR.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 0.8F * limbSwingAmount;
        this.Tail.rotateAngleY = this.FrontL.rotateAngleX * 0.625F;
    }
}
