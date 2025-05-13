/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntityGrasshopper;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelGrasshopper<T extends MoCEntityGrasshopper> extends EntityModel<T> {

    ModelRenderer Head;
    ModelRenderer Antenna;
    ModelRenderer AntennaB;
    ModelRenderer Thorax;
    ModelRenderer Abdomen;
    ModelRenderer TailA;
    ModelRenderer TailB;
    ModelRenderer FrontLegs;
    ModelRenderer MidLegs;
    ModelRenderer ThighLeft;
    ModelRenderer ThighLeftB;
    ModelRenderer ThighRight;
    ModelRenderer ThighRightB;
    ModelRenderer LegLeft;
    ModelRenderer LegLeftB;
    ModelRenderer LegRight;
    ModelRenderer LegRightB;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;
    ModelRenderer FoldedWings;
    private boolean flying;

    public MoCModelGrasshopper() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-0.5F, 0F, -1F, 1, 1, 2);
        this.Head.setRotationPoint(0F, 22.5F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Antenna = new ModelRenderer(this, 0, 11);
        this.Antenna.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.Antenna.setRotationPoint(0F, 22.5F, -3F);
        setRotation(this.Antenna, -2.736346F, 0F, 0F);

        this.AntennaB = new ModelRenderer(this, 0, 9);
        this.AntennaB.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.AntennaB.setRotationPoint(0F, 20.7F, -3.8F);
        setRotation(this.AntennaB, 2.88506F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setRotationPoint(0F, 21F, -1F);
        setRotation(this.Thorax, 0F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-1F, 0F, -1F, 2, 3, 2);
        this.Abdomen.setRotationPoint(0F, 22F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.TailA = new ModelRenderer(this, 4, 9);
        this.TailA.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.TailA.setRotationPoint(0F, 22F, 2.8F);
        setRotation(this.TailA, 1.308687F, 0F, 0F);

        this.TailB = new ModelRenderer(this, 4, 7);
        this.TailB.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.TailB.setRotationPoint(0F, 23F, 2.8F);
        setRotation(this.TailB, 1.665602F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 7);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.FrontLegs.setRotationPoint(0F, 23F, -1.8F);
        setRotation(this.FrontLegs, -0.8328009F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 0, 13);
        this.MidLegs.addBox(-2F, 0F, 0F, 4, 2, 0);
        this.MidLegs.setRotationPoint(0F, 23F, -1.2F);
        setRotation(this.MidLegs, 1.070744F, 0F, 0F);

        this.ThighLeft = new ModelRenderer(this, 8, 5);
        this.ThighLeft.addBox(0F, -3F, 0F, 1, 3, 1);
        this.ThighLeft.setRotationPoint(0.5F, 23F, 0F);
        setRotation(this.ThighLeft, -0.4886922F, 0.2617994F, 0F);

        this.ThighLeftB = new ModelRenderer(this, 8, 5);
        this.ThighLeftB.addBox(0F, -3F, 0F, 1, 3, 1);
        this.ThighLeftB.setRotationPoint(0.5F, 22.5F, 0F);
        setRotation(this.ThighLeftB, -1.762782F, 0F, 0F);

        this.ThighRight = new ModelRenderer(this, 12, 5);
        this.ThighRight.addBox(-1F, -3F, 0F, 1, 3, 1);
        this.ThighRight.setRotationPoint(-0.5F, 23F, 0F);
        setRotation(this.ThighRight, -0.4886922F, -0.2617994F, 0F);

        this.ThighRightB = new ModelRenderer(this, 12, 5);
        this.ThighRightB.addBox(-1F, -3F, 0F, 1, 3, 1);
        this.ThighRightB.setRotationPoint(-0.5F, 22.5F, 0F);
        setRotation(this.ThighRightB, -1.762782F, 0F, 0F);

        this.LegLeft = new ModelRenderer(this, 0, 15);
        this.LegLeft.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegLeft.setRotationPoint(2F, 21F, 2.5F);

        this.LegLeftB = new ModelRenderer(this, 4, 15);
        this.LegLeftB.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegLeftB.setRotationPoint(1.5F, 23F, 2.9F);
        setRotation(this.LegLeftB, 1.249201F, 0F, 0F);

        this.LegRight = new ModelRenderer(this, 4, 15);
        this.LegRight.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegRight.setRotationPoint(-2F, 21F, 2.5F);

        this.LegRightB = new ModelRenderer(this, 4, 15);
        this.LegRightB.addBox(0F, 0F, -1F, 0, 3, 2);
        this.LegRightB.setRotationPoint(-1.5F, 23F, 2.9F);
        setRotation(this.LegRightB, 1.249201F, 0F, 0F);

        this.LeftWing = new ModelRenderer(this, 0, 30);
        this.LeftWing.addBox(0F, 0F, -1F, 6, 0, 2);
        this.LeftWing.setRotationPoint(0F, 20.9F, -1F);
        setRotation(this.LeftWing, 0F, -0.1919862F, 0F);

        this.RightWing = new ModelRenderer(this, 0, 28);
        this.RightWing.addBox(-6F, 0F, -1F, 6, 0, 2);
        this.RightWing.setRotationPoint(0F, 20.9F, -1F);
        setRotation(this.RightWing, 0F, 0.1919862F, 0F);

        this.FoldedWings = new ModelRenderer(this, 0, 26);
        this.FoldedWings.addBox(0F, 0F, -1F, 6, 0, 2);
        this.FoldedWings.setRotationPoint(0F, 20.9F, -2F);
        setRotation(this.FoldedWings, 0F, -1.570796F, 0F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.flying = (entityIn.getIsFlying() || entityIn.getMotion().getY() < -0.1D);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Antenna.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.AntennaB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Thorax.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Abdomen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.TailA.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.TailB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.FrontLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.MidLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (!this.flying) {
            this.ThighLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.ThighRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.LegLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.LegRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.FoldedWings.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        } else {
            this.ThighLeftB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.ThighRightB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.LegLeftB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.LegRightB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
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

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float legMov;
        float legMovB;

        float frontLegAdj = 0F;

        if (this.flying) {
            float WingRot = MathHelper.cos((ageInTicks * 2.0F)) * 0.7F;
            this.RightWing.rotateAngleZ = WingRot;
            this.LeftWing.rotateAngleZ = -WingRot;
            legMov = (limbSwingAmount * 1.5F);
            legMovB = legMov;
            frontLegAdj = 1.4F;

        } else {
            legMov = MathHelper.cos((limbSwing * 1.5F) + 3.141593F) * 2.0F * limbSwingAmount;
            legMovB = MathHelper.cos(limbSwing * 1.5F) * 2.0F * limbSwingAmount;
        }

        this.AntennaB.rotateAngleX = 2.88506F - legMov;

        this.FrontLegs.rotateAngleX = -0.8328009F + frontLegAdj + legMov;
        this.MidLegs.rotateAngleX = 1.070744F + legMovB;
    }
}
