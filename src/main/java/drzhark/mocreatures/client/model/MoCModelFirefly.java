/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelFirefly<T extends MoCEntityFirefly> extends EntityModel<T> {

    //fields
    ModelRenderer Antenna;
    ModelRenderer RearLegs;
    ModelRenderer MidLegs;
    ModelRenderer Head;
    ModelRenderer Tail;
    ModelRenderer Abdomen;
    ModelRenderer FrontLegs;
    ModelRenderer RightShellOpen;
    ModelRenderer LeftShellOpen;
    ModelRenderer Thorax;
    ModelRenderer RightShell;
    ModelRenderer LeftShell;
    ModelRenderer LeftWing;
    ModelRenderer RightWing;
    private boolean flying;
    private boolean day;

    public MoCModelFirefly() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        this.Head.setRotationPoint(0F, 22.5F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Antenna = new ModelRenderer(this, 0, 7);
        this.Antenna.addBox(-1F, 0F, 0F, 2, 1, 0);
        this.Antenna.setRotationPoint(0F, 22.5F, -3F);
        setRotation(this.Antenna, -1.665602F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setRotationPoint(0F, 21F, -1F);
        setRotation(this.Thorax, 0F, 0F, 0F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Abdomen.setRotationPoint(0F, 22F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.Tail = new ModelRenderer(this, 8, 17);
        this.Tail.addBox(-1F, 0.5F, -1F, 2, 2, 1);
        this.Tail.setRotationPoint(0F, 21.3F, 1.5F);
        setRotation(this.Tail, 1.13023F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 7);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.FrontLegs.setRotationPoint(0F, 23F, -1.8F);
        setRotation(this.FrontLegs, -0.8328009F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 0, 9);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 2, 0);
        this.MidLegs.setRotationPoint(0F, 23F, -1.2F);
        setRotation(this.MidLegs, 1.070744F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 0, 9);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.RearLegs.setRotationPoint(0F, 23F, -0.4F);
        setRotation(this.RearLegs, 1.249201F, 0F, 0F);

        this.RightShellOpen = new ModelRenderer(this, 0, 12);
        this.RightShellOpen.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.RightShellOpen.setRotationPoint(-1F, 21F, -2F);
        setRotation(this.RightShellOpen, 1.22F, 0F, -0.6457718F);

        this.LeftShellOpen = new ModelRenderer(this, 0, 12);
        this.LeftShellOpen.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.LeftShellOpen.setRotationPoint(1F, 21F, -2F);
        setRotation(this.LeftShellOpen, 1.22F, 0F, 0.6457718F);

        this.RightShell = new ModelRenderer(this, 0, 12);
        this.RightShell.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.RightShell.setRotationPoint(-1F, 21F, -2F);
        setRotation(this.RightShell, 0.0174533F, 0F, -0.6457718F);

        this.LeftShell = new ModelRenderer(this, 0, 12);
        this.LeftShell.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.LeftShell.setRotationPoint(1F, 21F, -2F);
        setRotation(this.LeftShell, 0.0174533F, 0F, 0.6457718F);

        this.LeftWing = new ModelRenderer(this, 15, 12);
        this.LeftWing.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.LeftWing.setRotationPoint(1F, 21F, -1F);
        setRotation(this.LeftWing, 0F, 1.047198F, 0F);

        this.RightWing = new ModelRenderer(this, 15, 12);
        this.RightWing.addBox(-1F, 0F, 0F, 2, 0, 5);
        this.RightWing.setRotationPoint(-1F, 21F, -1F);
        setRotation(this.RightWing, 0F, -1.047198F, 0F);

    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.flying = (entityIn.getIsFlying() || entityIn.getMotion().getY() < -0.1D);
        this.day = !entityIn.getEntityWorld().isDaytime();
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Antenna.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RearLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.MidLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        this.Abdomen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.FrontLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Thorax.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        if (!this.flying) {
            this.RightShell.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.LeftShell.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            this.RightShellOpen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.LeftShellOpen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

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

        matrixStackIn.push();
        RenderSystem.enableBlend();
        if (!this.day) {
            float transparency = 0.4F;
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(0.8F, 0.8F, 0.8F, transparency);
        } else {
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }
        RenderSystem.disableBlend();
        matrixStackIn.pop();
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
            float WingRot = MathHelper.cos((ageInTicks * 1.8F)) * 0.8F;
            this.RightWing.rotateAngleZ = WingRot;
            this.LeftWing.rotateAngleZ = -WingRot;
            legMov = (limbSwingAmount * 1.5F);
            legMovB = legMov;
            frontLegAdj = 1.4F;

        } else {
            legMov = MathHelper.cos((limbSwing * 1.5F) + 3.141593F) * 2.0F * limbSwingAmount;
            legMovB = MathHelper.cos(limbSwing * 1.5F) * 2.0F * limbSwingAmount;
        }
        this.FrontLegs.rotateAngleX = -0.8328009F + frontLegAdj + legMov;
        this.MidLegs.rotateAngleX = 1.070744F + legMovB;
        this.RearLegs.rotateAngleX = 1.249201F + legMov;
    }
}
