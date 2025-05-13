/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.hunter.MoCEntityFox;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;


public class MoCModelFox<T extends MoCEntityFox> extends EntityModel<T> {

    public ModelRenderer Body;
    public ModelRenderer Leg1;
    public ModelRenderer Leg2;
    public ModelRenderer Leg3;
    public ModelRenderer Leg4;
    public ModelRenderer Snout;
    public ModelRenderer Head;
    public ModelRenderer Tail;
    public ModelRenderer Ears;

    public MoCModelFox() {
        byte byte0 = 8;
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(1.0F, 0.0F, 0.0F, 6, 6, 12, 0.0F);
        this.Body.setRotationPoint(-4F, 10F, -6F);
        this.Head = new ModelRenderer(this, 0, 20);
        this.Head.addBox(-2F, -3F, -4F, 6, 6, 4, 0.0F);
        this.Head.setRotationPoint(-1F, 11F, -6F);
        this.Snout = new ModelRenderer(this, 20, 20);
        this.Snout.addBox(0.0F, 1.0F, -7F, 2, 2, 4, 0.0F);
        this.Snout.setRotationPoint(-1F, 11F, -6F);
        this.Ears = new ModelRenderer(this, 50, 20);
        this.Ears.addBox(-2F, -6F, -2F, 6, 4, 1, 0.0F);
        this.Ears.setRotationPoint(-1F, 11F, -6F);
        this.Tail = new ModelRenderer(this, 32, 20);
        this.Tail.addBox(-4F, -5F, -2F, 3, 3, 8, 0.0F);
        this.Tail.setRotationPoint(2.5F, 15F, 5F);
        this.Tail.rotateAngleX = -0.5235988F;
        this.Leg1 = new ModelRenderer(this, 0, 0);
        this.Leg1.addBox(-1F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg1.setRotationPoint(-2F, 24 - byte0, 5F);
        this.Leg2 = new ModelRenderer(this, 0, 0);
        this.Leg2.addBox(-1F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg2.setRotationPoint(1.0F, 24 - byte0, 5F);
        this.Leg3 = new ModelRenderer(this, 0, 0);
        this.Leg3.addBox(-1F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg3.setRotationPoint(-2F, 24 - byte0, -4F);
        this.Leg4 = new ModelRenderer(this, 0, 0);
        this.Leg4.addBox(-1F, 0.0F, -2F, 3, byte0, 3, 0.0F);
        this.Leg4.setRotationPoint(1.0F, 24 - byte0, -4F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Snout.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Ears.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Head.rotateAngleY = netHeadYaw / 57.29578F;
        this.Head.rotateAngleX = headPitch / 57.29578F;
        this.Snout.rotateAngleY = this.Head.rotateAngleY;//netHeadYaw / 57.29578F;
        this.Snout.rotateAngleX = this.Head.rotateAngleX;//headPitch / 57.29578F;
        //Snout.rotationPointX = 0.0F + ((netHeadYaw / 57.29578F) * 0.8F);
        this.Ears.rotateAngleY = this.Head.rotateAngleY;//netHeadYaw / 57.29578F;
        this.Ears.rotateAngleX = this.Head.rotateAngleX;//headPitch / 57.29578F;
        //Ears.rotationPointX = 0.0F + ((netHeadYaw / 57.29578F) * 2.5F);
        this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.Leg2.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.4F * limbSwingAmount;
        this.Leg3.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.4F * limbSwingAmount;
        this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
