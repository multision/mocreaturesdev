/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.hostile.MoCEntityMiniGolem;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelMiniGolem<T extends MoCEntityMiniGolem> extends EntityModel<T> {

    private final float radianF = 57.29578F;
    ModelRenderer Head;
    ModelRenderer HeadRed;
    ModelRenderer Body;
    ModelRenderer BodyRed;
    ModelRenderer LeftShoulder;
    ModelRenderer LeftArm;
    ModelRenderer LeftArmRingA;
    ModelRenderer LeftArmRingB;
    ModelRenderer RightShoulder;
    ModelRenderer RightArm;
    ModelRenderer RightArmRingA;
    ModelRenderer RightArmRingB;
    ModelRenderer RightLeg;
    ModelRenderer RightFoot;
    ModelRenderer LeftLeg;
    ModelRenderer LeftFoot;
    private boolean angry;

    public MoCModelMiniGolem() {
        this.textureWidth = 64;
        this.textureHeight = 64;

        this.Head = new ModelRenderer(this, 30, 0);
        this.Head.addBox(-3F, -3F, -3F, 6, 3, 6);
        this.Head.setRotationPoint(0F, 8F, 0F);
        setRotation(this.Head, 0F, -0.7853982F, 0F);

        this.HeadRed = new ModelRenderer(this, 30, 29);
        this.HeadRed.addBox(-3F, -3F, -3F, 6, 3, 6);
        this.HeadRed.setRotationPoint(0F, 8F, 0F);
        setRotation(this.HeadRed, 0F, -0.7853982F, 0F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-5F, -10F, -5F, 10, 10, 10);
        this.Body.setRotationPoint(0F, 18F, 0F);

        this.BodyRed = new ModelRenderer(this, 0, 28);
        this.BodyRed.addBox(-5F, -10F, -5F, 10, 10, 10);
        this.BodyRed.setRotationPoint(0F, 18F, 0F);

        this.LeftShoulder = new ModelRenderer(this, 0, 4);
        this.LeftShoulder.addBox(0F, -1F, -1F, 1, 2, 2);
        this.LeftShoulder.setRotationPoint(5F, 11F, 0F);

        this.LeftArm = new ModelRenderer(this, 0, 48);
        this.LeftArm.addBox(1F, -2F, -2F, 4, 12, 4);
        this.LeftArm.setRotationPoint(5F, 11F, 0F);

        this.LeftArmRingA = new ModelRenderer(this, 20, 20);
        this.LeftArmRingA.addBox(0.5F, 1F, -2.5F, 5, 3, 5);
        this.LeftArmRingA.setRotationPoint(5F, 11F, 0F);

        this.LeftArmRingB = new ModelRenderer(this, 20, 20);
        this.LeftArmRingB.addBox(0.5F, 5F, -2.5F, 5, 3, 5);
        this.LeftArmRingB.setRotationPoint(5F, 11F, 0F);

        this.RightShoulder = new ModelRenderer(this, 0, 0);
        this.RightShoulder.addBox(-1F, -1F, -1F, 1, 2, 2);
        this.RightShoulder.setRotationPoint(-5F, 11F, 0F);

        this.RightArm = new ModelRenderer(this, 16, 48);
        this.RightArm.addBox(-5F, -2F, -2F, 4, 12, 4);
        this.RightArm.setRotationPoint(-5F, 11F, 0F);

        this.RightArmRingA = new ModelRenderer(this, 0, 20);
        this.RightArmRingA.addBox(-5.5F, 1F, -2.5F, 5, 3, 5);
        this.RightArmRingA.setRotationPoint(-5F, 11F, 0F);

        this.RightArmRingB = new ModelRenderer(this, 0, 20);
        this.RightArmRingB.addBox(-5.5F, 5F, -2.5F, 5, 3, 5);
        this.RightArmRingB.setRotationPoint(-5F, 11F, 0F);

        this.RightLeg = new ModelRenderer(this, 40, 9);
        this.RightLeg.addBox(-2.5F, 0F, -2F, 4, 6, 4);
        this.RightLeg.setRotationPoint(-2F, 18F, 0F);

        this.RightFoot = new ModelRenderer(this, 15, 22);
        this.RightFoot.addBox(-2.5F, 5F, -3F, 4, 1, 1);
        this.RightFoot.setRotationPoint(-2F, 18F, 0F);

        this.LeftLeg = new ModelRenderer(this, 40, 19);
        this.LeftLeg.addBox(-1.5F, 0F, -2F, 4, 6, 4);
        this.LeftLeg.setRotationPoint(2F, 18F, 0F);

        this.LeftFoot = new ModelRenderer(this, 15, 20);
        this.LeftFoot.addBox(-1.5F, 5F, -3F, 4, 1, 1);
        this.LeftFoot.setRotationPoint(2F, 18F, 0F);

    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.angry = entityIn.isAggressive();
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (angry) {
            this.HeadRed.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.BodyRed.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }

        this.LeftShoulder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftArmRingA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftArmRingB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightShoulder.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightArm.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightArmRingA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightArmRingB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftFoot.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float hRotY = netHeadYaw / 57.29578F;
        float RLegXRot = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 0.8F * limbSwingAmount;
        float LLegXRot = MathHelper.cos(limbSwing * 0.6662F) * 0.8F * limbSwingAmount;

        this.RightLeg.rotateAngleX = RLegXRot;
        this.RightFoot.rotateAngleX = RLegXRot;
        this.LeftLeg.rotateAngleX = LLegXRot;
        this.LeftFoot.rotateAngleX = LLegXRot;

        this.Head.rotateAngleY = -0.7853982F + hRotY;
        this.HeadRed.rotateAngleY = -0.7853982F + hRotY;

        if (entityIn.getHasRock()) {
            this.LeftShoulder.rotateAngleZ = 0F;
            this.LeftShoulder.rotateAngleX = -180F / this.radianF;
            this.RightShoulder.rotateAngleZ = 0F;
            this.RightShoulder.rotateAngleX = -180F / this.radianF;
        } else {
            this.LeftShoulder.rotateAngleZ = (MathHelper.cos(ageInTicks * 0.09F) * 0.05F) - 0.05F;
            this.LeftShoulder.rotateAngleX = RLegXRot;
            this.RightShoulder.rotateAngleZ = -(MathHelper.cos(ageInTicks * 0.09F) * 0.05F) + 0.05F;
            this.RightShoulder.rotateAngleX = LLegXRot;
        }

        this.RightArm.rotateAngleX = this.RightArmRingA.rotateAngleX = this.RightArmRingB.rotateAngleX = this.RightShoulder.rotateAngleX;
        this.RightArm.rotateAngleZ = this.RightArmRingA.rotateAngleZ = this.RightArmRingB.rotateAngleZ = this.RightShoulder.rotateAngleZ;

        this.LeftArm.rotateAngleX = this.LeftArmRingA.rotateAngleX = this.LeftArmRingB.rotateAngleX = this.LeftShoulder.rotateAngleX;
        this.LeftArm.rotateAngleZ = this.LeftArmRingA.rotateAngleZ = this.LeftArmRingB.rotateAngleZ = this.LeftShoulder.rotateAngleZ;

        //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);
    }
}
