/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityDeer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MoCModelDeer<T extends MoCEntityDeer> extends EntityModel<T> {

    public ModelRenderer Body;
    public ModelRenderer Neck;
    public ModelRenderer Head;
    public ModelRenderer Leg1;
    public ModelRenderer Leg2;
    public ModelRenderer Leg3;
    public ModelRenderer Leg4;
    public ModelRenderer Tail;
    public ModelRenderer LEar;
    public ModelRenderer REar;
    public ModelRenderer LeftAntler;
    public ModelRenderer RightAntler;

    public MoCModelDeer() {
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-1.5F, -6F, -9.5F, 3, 3, 6, 0.0F);
        this.Head.setRotationPoint(1.0F, 11.5F, -4.5F);
        this.Neck = new ModelRenderer(this, 0, 9);
        this.Neck.addBox(-2F, -2F, -6F, 4, 4, 6, 0.0F);
        this.Neck.setRotationPoint(1.0F, 11.5F, -4.5F);
        this.Neck.rotateAngleX = -0.7853981F;
        this.LEar = new ModelRenderer(this, 0, 0);
        this.LEar.addBox(-4F, -7.5F, -5F, 2, 3, 1, 0.0F);
        this.LEar.setRotationPoint(1.0F, 11.5F, -4.5F);
        this.LEar.rotateAngleZ = 0.7853981F;
        this.REar = new ModelRenderer(this, 0, 0);
        this.REar.addBox(2.0F, -7.5F, -5F, 2, 3, 1, 0.0F);
        this.REar.setRotationPoint(1.0F, 11.5F, -4.5F);
        this.REar.rotateAngleZ = -0.7853981F;
        this.LeftAntler = new ModelRenderer(this, 54, 0);
        this.LeftAntler.addBox(0.0F, -14F, -7F, 1, 8, 4, 0.0F);
        this.LeftAntler.setRotationPoint(1.0F, 11.5F, -4.5F);
        this.LeftAntler.rotateAngleZ = 0.2094395F;
        this.RightAntler = new ModelRenderer(this, 54, 0);
        this.RightAntler.addBox(0.0F, -14F, -7F, 1, 8, 4, 0.0F);
        this.RightAntler.setRotationPoint(1.0F, 11.5F, -4.5F);
        this.RightAntler.rotateAngleZ = -0.2094395F;
        this.Body = new ModelRenderer(this, 24, 12);
        this.Body.addBox(-2F, -3F, -6F, 6, 6, 14, 0.0F);
        this.Body.setRotationPoint(0.0F, 13F, 0.0F);
        this.Leg1 = new ModelRenderer(this, 9, 20);
        this.Leg1.addBox(-1F, 0.0F, -1F, 2, 8, 2, 0.0F);
        this.Leg1.setRotationPoint(3F, 16F, -4F);
        this.Leg2 = new ModelRenderer(this, 0, 20);
        this.Leg2.addBox(-1F, 0.0F, -1F, 2, 8, 2, 0.0F);
        this.Leg2.setRotationPoint(-1F, 16F, -4F);
        this.Leg3 = new ModelRenderer(this, 9, 20);
        this.Leg3.addBox(-1F, 0.0F, -1F, 2, 8, 2, 0.0F);
        this.Leg3.setRotationPoint(3F, 16F, 6F);
        this.Leg4 = new ModelRenderer(this, 0, 20);
        this.Leg4.addBox(-1F, 0.0F, -1F, 2, 8, 2, 0.0F);
        this.Leg4.setRotationPoint(-1F, 16F, 6F);
        this.Tail = new ModelRenderer(this, 50, 20);
        this.Tail.addBox(-1.5F, -1F, 0.0F, 3, 2, 4, 0.0F);
        this.Tail.setRotationPoint(1.0F, 11F, 7F);
        this.Tail.rotateAngleX = 0.7854F;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LEar.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.REar.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftAntler.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightAntler.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.Leg2.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.4F * limbSwingAmount;
        this.Leg3.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.4F * limbSwingAmount;
        this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
