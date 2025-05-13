/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelKitty<T extends MoCEntityKitty> extends EntityModel<T> {

    private final ModelRenderer body;
    public boolean isSitting;
    public boolean isSwinging;
    public float swingProgress;
    public int kittystate;
    public ModelRenderer[] headParts;
    public ModelRenderer tail;
    public ModelRenderer rightArm;
    public ModelRenderer leftArm;
    public ModelRenderer rightLeg;
    public ModelRenderer leftLeg;

    public MoCModelKitty() {
        this(0.0F);
    }

    public MoCModelKitty(float limbSwing) {
        this(limbSwing, 0.0F);
    }

    public MoCModelKitty(float limbSwing, float limbSwingAmount) {
        this.headParts = new ModelRenderer[10];
        this.headParts[0] = new ModelRenderer(this, 16, 0);
        this.headParts[0].addBox(-2F, -5F, -3F, 1, 1, 1, limbSwing);
        this.headParts[0].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[1] = new ModelRenderer(this, 16, 0);
        this.headParts[1].mirror = true;
        this.headParts[1].addBox(1.0F, -5F, -3F, 1, 1, 1, limbSwing);
        this.headParts[1].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[2] = new ModelRenderer(this, 20, 0);
        this.headParts[2].addBox(-2.5F, -4F, -3F, 2, 1, 1, limbSwing);
        this.headParts[2].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[3] = new ModelRenderer(this, 20, 0);
        this.headParts[3].mirror = true;
        this.headParts[3].addBox(0.5F, -4F, -3F, 2, 1, 1, limbSwing);
        this.headParts[3].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[4] = new ModelRenderer(this, 40, 0);
        this.headParts[4].addBox(-4F, -1.5F, -5F, 3, 3, 1, limbSwing);
        this.headParts[4].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[5] = new ModelRenderer(this, 40, 0);
        this.headParts[5].mirror = true;
        this.headParts[5].addBox(1.0F, -1.5F, -5F, 3, 3, 1, limbSwing);
        this.headParts[5].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[6] = new ModelRenderer(this, 21, 6);
        this.headParts[6].addBox(-1F, -1F, -5F, 2, 2, 1, limbSwing);
        this.headParts[6].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[7] = new ModelRenderer(this, 50, 0);
        this.headParts[7].addBox(-2.5F, 0.5F, -1F, 5, 4, 1, limbSwing);
        this.headParts[7].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[8] = new ModelRenderer(this, 60, 0);
        this.headParts[8].addBox(-1.5F, -2F, -4.1F, 3, 1, 1, limbSwing);
        this.headParts[8].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.headParts[9] = new ModelRenderer(this, 1, 1);
        this.headParts[9].addBox(-2.5F, -3F, -4F, 5, 4, 4, limbSwing);
        this.headParts[9].setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.body = new ModelRenderer(this, 20, 0);
        this.body.addBox(-2.5F, -2F, -0F, 5, 5, 10, limbSwing);
        this.body.setRotationPoint(0.0F, 0.0F + limbSwingAmount, -2F);
        this.rightArm = new ModelRenderer(this, 0, 9);
        this.rightArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, limbSwing);
        this.rightArm.setRotationPoint(-1.5F, 3F + limbSwingAmount, -1F);
        this.leftArm = new ModelRenderer(this, 0, 9);
        this.leftArm.mirror = true;
        this.leftArm.addBox(-1F, 0.0F, -1F, 2, 6, 2, limbSwing);
        this.leftArm.setRotationPoint(1.5F, 3F + limbSwingAmount, -1F);
        this.rightLeg = new ModelRenderer(this, 8, 9);
        this.rightLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, limbSwing);
        this.rightLeg.setRotationPoint(-1.5F, 3F + limbSwingAmount, 7F);
        this.leftLeg = new ModelRenderer(this, 8, 9);
        this.leftLeg.mirror = true;
        this.leftLeg.addBox(-1F, 0.0F, -1F, 2, 6, 2, limbSwing);
        this.leftLeg.setRotationPoint(1.5F, 3F + limbSwingAmount, 7F);
        this.tail = new ModelRenderer(this, 16, 9);
        this.tail.mirror = true;
        this.tail.addBox(-0.5F, -8F, -1F, 1, 8, 1, limbSwing);
        this.tail.setRotationPoint(0.0F, -0.5F + limbSwingAmount, 7.5F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.isSitting = entityIn.getIsSitting();
        this.isSwinging = entityIn.getIsSwinging();
        this.swingProgress = entityIn.swingProgress;
        this.kittystate = entityIn.getKittyState();
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();
        if (this.isSitting) {
            matrixStackIn.translate(0.0F, 0.25F, 0.0F);
            this.tail.rotateAngleZ = 0.0F;
            this.tail.rotateAngleX = -2.3F;
        }
        for (int i = 0; i < 7; i++) {
            this.headParts[i].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        if (this.kittystate > 2) {
            this.headParts[7].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        if (this.kittystate == 12) {
            this.headParts[8].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        this.headParts[9].render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.isSitting) {
            matrixStackIn.translate(0.0F, 0.0625F, 0.0625F);
            float f6 = -1.570796F;
            this.rightArm.rotateAngleX = f6;
            this.leftArm.rotateAngleX = f6;
            this.rightLeg.rotateAngleX = f6;
            this.leftLeg.rotateAngleX = f6;
            this.rightLeg.rotateAngleY = 0.1F;
            this.leftLeg.rotateAngleY = -0.1F;
        }
        this.rightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.headParts[9].rotateAngleY = netHeadYaw / 57.29578F;
        this.headParts[9].rotateAngleX = headPitch / 57.29578F;
        for (int i = 0; i < 9; i++) {
            this.headParts[i].rotateAngleY = this.headParts[9].rotateAngleY;
            this.headParts[i].rotateAngleX = this.headParts[9].rotateAngleX;
        }
        this.rightArm.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;
        this.rightLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.4F * limbSwingAmount;
        this.rightLeg.rotateAngleY = 0.0F;
        this.leftLeg.rotateAngleY = 0.0F;
        if (this.isSwinging) {
            this.rightArm.rotateAngleX = -2F + this.swingProgress;
            this.rightArm.rotateAngleY = 2.25F - (this.swingProgress * 2.0F);
        } else {
            this.rightArm.rotateAngleY = 0.0F;
        }
        this.leftArm.rotateAngleY = 0.0F;
        this.tail.rotateAngleX = -0.5F;
        this.tail.rotateAngleZ = this.leftLeg.rotateAngleX * 0.625F;
    }
}
