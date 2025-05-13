/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MoCModelDolphin<T extends MoCEntityDolphin> extends EntityModel<T> {

    public ModelRenderer UHead;
    public ModelRenderer DHead;
    public ModelRenderer RTail;
    public ModelRenderer LTail;
    public ModelRenderer PTail;
    public ModelRenderer Body;
    public ModelRenderer UpperFin;
    public ModelRenderer RTailFin;
    public ModelRenderer LTailFin;
    public ModelRenderer LowerFin;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;

    public MoCModelDolphin() {
        this.Body = new ModelRenderer(this, 4, 6);
        this.Body.addBox(0.0F, 0.0F, 0.0F, 6, 8, 18, 0.0F);
        this.Body.setRotationPoint(-3F, 17F, -4F);
        this.UHead = new ModelRenderer(this, 0, 0);
        this.UHead.addBox(0.0F, 0.0F, 0.0F, 5, 7, 8, 0.0F);
        this.UHead.setRotationPoint(-2.5F, 18F, -10.5F);
        this.DHead = new ModelRenderer(this, 50, 0);
        this.DHead.addBox(0.0F, 0.0F, 0.0F, 3, 3, 4, 0.0F);
        this.DHead.setRotationPoint(-1.5F, 21.5F, -14.5F);
        this.PTail = new ModelRenderer(this, 34, 9);
        this.PTail.addBox(0.0F, 0.0F, 0.0F, 5, 5, 10, 0.0F);
        this.PTail.setRotationPoint(-2.5F, 19F, 14F);
        this.UpperFin = new ModelRenderer(this, 4, 12);
        this.UpperFin.addBox(0.0F, 0.0F, 0.0F, 1, 4, 8, 0.0F);
        this.UpperFin.setRotationPoint(-0.5F, 18F, 2F);
        this.UpperFin.rotateAngleX = 0.7853981F;
        this.LTailFin = new ModelRenderer(this, 34, 0);
        this.LTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        this.LTailFin.setRotationPoint(-1F, 21.5F, 24F);
        this.LTailFin.rotateAngleY = 0.7853981F;
        this.RTailFin = new ModelRenderer(this, 34, 0);
        this.RTailFin.addBox(0.0F, 0.0F, 0.0F, 4, 1, 8, 0.3F);
        this.RTailFin.setRotationPoint(-2F, 21.5F, 21F);
        this.RTailFin.rotateAngleY = -0.7853981F;
        this.LeftFin = new ModelRenderer(this, 14, 0);
        this.LeftFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.LeftFin.setRotationPoint(3.0F, 24F, -1F);
        this.LeftFin.rotateAngleY = -0.5235988F;
        this.LeftFin.rotateAngleZ = 0.5235988F;
        this.RightFin = new ModelRenderer(this, 14, 0);
        this.RightFin.addBox(0.0F, 0.0F, 0.0F, 8, 1, 4, 0.0F);
        this.RightFin.setRotationPoint(-9F, 27.5F, 3F);
        this.RightFin.rotateAngleY = 0.5235988F;
        this.RightFin.rotateAngleZ = -0.5235988F;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.PTail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.UHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.DHead.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.UpperFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LTailFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RTailFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightFin.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.RTailFin.rotateAngleX = MathHelper.cos(limbSwing * 0.4F) * limbSwingAmount;
        this.LTailFin.rotateAngleX = MathHelper.cos(limbSwing * 0.4F) * limbSwingAmount;
    }
}
