/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.neutral.MoCEntityGoat;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelGoat<T extends MoCEntityGoat> extends EntityModel<T> {

    public int typeInt;
    public int attacking;
    public float age;
    public boolean bleat;
    public int legMov;
    public int earMov;
    public int tailMov;
    public int eatMov;
    public float prevTailAngleX;
    public float prevLEarAngleX;
    public float prevREarAngleX;
    public float prevMouthAngleX;
    ModelRenderer Leg1;
    ModelRenderer Leg2;
    ModelRenderer Leg3;
    ModelRenderer Leg4;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer LEar;
    ModelRenderer REar;
    ModelRenderer Head;
    ModelRenderer Nose;
    ModelRenderer Tongue;
    ModelRenderer Mouth;
    ModelRenderer RHorn1;
    ModelRenderer RHorn2;
    ModelRenderer RHorn3;
    ModelRenderer RHorn4;
    ModelRenderer RHorn5;
    ModelRenderer LHorn1;
    ModelRenderer LHorn2;
    ModelRenderer LHorn3;
    ModelRenderer LHorn4;
    ModelRenderer LHorn5;
    ModelRenderer Goatie;
    ModelRenderer Neck;
    ModelRenderer Tits;

    public MoCModelGoat() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Leg1 = new ModelRenderer(this, 0, 23);
        this.Leg1.addBox(-1F, 0F, -1F, 2, 7, 2);
        this.Leg1.setRotationPoint(2F, 17F, -6F);

        this.Leg2 = new ModelRenderer(this, 0, 23);
        this.Leg2.addBox(-1F, 0F, -1F, 2, 7, 2);
        this.Leg2.setRotationPoint(-2F, 17F, -6F);

        this.Leg3 = new ModelRenderer(this, 0, 23);
        this.Leg3.addBox(-1F, 0F, -1F, 2, 7, 2);
        this.Leg3.setRotationPoint(-2F, 17F, 6F);

        this.Leg4 = new ModelRenderer(this, 0, 23);
        this.Leg4.addBox(-1F, 0F, -1F, 2, 7, 2);
        this.Leg4.setRotationPoint(2F, 17F, 6F);

        this.Body = new ModelRenderer(this, 20, 8);
        this.Body.addBox(-3F, -4F, -8F, 6, 8, 16);
        this.Body.setRotationPoint(0F, 13F, 0F);

        this.Tail = new ModelRenderer(this, 22, 8);
        this.Tail.addBox(-1.5F, -1F, 0F, 3, 2, 4);
        this.Tail.setRotationPoint(0F, 10F, 8F);

        this.LEar = new ModelRenderer(this, 52, 8);
        this.LEar.addBox(1.5F, -2F, 0F, 2, 1, 1);
        this.LEar.setRotationPoint(0F, 8F, -12F);

        this.REar = new ModelRenderer(this, 52, 8);
        this.REar.addBox(-3.5F, -2F, 0F, 2, 1, 1);
        this.REar.setRotationPoint(0F, 8F, -12F);

        this.Head = new ModelRenderer(this, 52, 16);
        this.Head.addBox(-1.5F, -2F, -2F, 3, 5, 3);
        this.Head.setRotationPoint(0F, 8F, -12F);

        this.Nose = new ModelRenderer(this, 52, 10);
        this.Nose.addBox(-1.5F, -1F, -5F, 3, 3, 3);
        this.Nose.setRotationPoint(0F, 8F, -12F);

        this.Tongue = new ModelRenderer(this, 56, 5);
        this.Tongue.addBox(-0.5F, 2F, -5F, 1, 0, 3);
        this.Tongue.setRotationPoint(0F, 8F, -12F);

        this.Mouth = new ModelRenderer(this, 54, 0);
        this.Mouth.addBox(-1F, 2F, -5F, 2, 1, 3);
        this.Mouth.setRotationPoint(0F, 8F, -12F);

        this.RHorn1 = new ModelRenderer(this, 0, 0);
        this.RHorn1.addBox(-1.5F, -3F, -0.7F, 1, 1, 1, 0.1F);
        this.RHorn1.setRotationPoint(0F, 8F, -12F);

        this.RHorn2 = new ModelRenderer(this, 0, 0);
        this.RHorn2.addBox(-1.9F, -4F, -0.2F, 1, 1, 1);
        this.RHorn2.setRotationPoint(0F, 8F, -12F);

        this.RHorn3 = new ModelRenderer(this, 0, 0);
        this.RHorn3.addBox(-2.1F, -4.8F, 0.5F, 1, 1, 1, -0.05F);
        this.RHorn3.setRotationPoint(0F, 8F, -12F);

        this.RHorn4 = new ModelRenderer(this, 0, 0);
        this.RHorn4.addBox(-2.3F, -5.2F, 1.4F, 1, 1, 1, -0.1F);
        this.RHorn4.setRotationPoint(0F, 8F, -12F);

        this.RHorn5 = new ModelRenderer(this, 0, 0);
        this.RHorn5.addBox(-2.6F, -4.9F, 2.0F, 1, 1, 1, -0.15F);
        this.RHorn5.setRotationPoint(0F, 8F, -12F);

        this.LHorn1 = new ModelRenderer(this, 0, 0);
        this.LHorn1.addBox(0.5F, -3F, -0.7F, 1, 1, 1, 0.1F);
        this.LHorn1.setRotationPoint(0F, 8F, -12F);

        this.LHorn2 = new ModelRenderer(this, 0, 0);
        this.LHorn2.addBox(0.9F, -4F, -0.2F, 1, 1, 1);
        this.LHorn2.setRotationPoint(0F, 8F, -12F);

        this.LHorn3 = new ModelRenderer(this, 0, 0);
        this.LHorn3.addBox(1.2F, -4.9F, 0.5F, 1, 1, 1, -0.05F);
        this.LHorn3.setRotationPoint(0F, 8F, -12F);

        this.LHorn4 = new ModelRenderer(this, 0, 0);
        this.LHorn4.addBox(1.4F, -5.3F, 1.4F, 1, 1, 1, -0.1F);
        this.LHorn4.setRotationPoint(0F, 8F, -12F);

        this.LHorn5 = new ModelRenderer(this, 0, 0);
        this.LHorn5.addBox(1.7F, -4.9F, 2.1F, 1, 1, 1, -0.15F);
        this.LHorn5.setRotationPoint(0F, 8F, -12F);

        this.Goatie = new ModelRenderer(this, 52, 5);
        this.Goatie.addBox(-0.5F, 3F, -4F, 1, 2, 1);
        this.Goatie.setRotationPoint(0F, 8F, -12F);

        this.Neck = new ModelRenderer(this, 18, 14);
        this.Neck.addBox(-1.5F, -2.0F, -5F, 3, 4, 6, -0.2F);
        this.Neck.setRotationPoint(0F, 11F, -8F);
        this.Neck.rotateAngleX = -24 / 57.29578F;

        this.Tits = new ModelRenderer(this, 18, 0);
        this.Tits.addBox(-2.5F, 0F, -2F, 5, 1, 4);
        this.Tits.setRotationPoint(0F, 17F, 3F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Leg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Leg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Neck.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.typeInt > 1 && this.typeInt < 5) {
            this.Tits.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        matrixStackIn.push();
        if (this.attacking != 0) {
            float yOff = (this.attacking / 150F) - (1F / 5F);
            float zOff = (this.attacking / 450F) - (1F / 15F);
            matrixStackIn.translate(0.0F, yOff, -zOff);
        }
        this.LEar.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.REar.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Nose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.typeInt > 1) {
            if (this.age > 0.7) {
                this.RHorn1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LHorn1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
            if (this.age > 0.8) {
                this.RHorn2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LHorn2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }
        if (this.typeInt > 4) {
            if (this.age > 0.8) {
                this.RHorn3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LHorn3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
            if (this.age > 0.85) {
                this.RHorn4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LHorn4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
            if (this.age > 0.9) {
                this.RHorn5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
                this.LHorn5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }
        if (this.eatMov != 0 && !this.bleat) {
            matrixStackIn.translate(this.eatMov / 100F, 0.0F, 0.0F);
        }
        if (this.typeInt > 4 && this.age > 0.9) {
            this.Goatie.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
        this.Tongue.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Mouth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.Leg2.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.4F * limbSwingAmount;
        this.Leg3.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * 1.4F * limbSwingAmount;
        this.Leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        float baseAngle = (30 / 57.29578F) + (headPitch / 57.29578F);
        if (netHeadYaw > 20F) {
            netHeadYaw = 20F;
        }
        if (netHeadYaw < -20F) {
            netHeadYaw = -20F;
        }
        this.Head.rotateAngleY = (netHeadYaw / 57.29578F);
        this.Neck.rotateAngleX = -30 / 57.29578F;
        this.Head.rotateAngleX = baseAngle;
        if (this.bleat) {
            this.Head.rotateAngleX = -15 / 57.29578F;
        }
        if (this.attacking != 0) {
            this.Head.rotateAngleX = this.attacking / 57.29578F;
            this.Neck.rotateAngleX = ((1.33F * this.attacking) - 70) / 57.29578F;
            if (this.legMov != 0) {
                this.Leg1.rotateAngleX = this.legMov / 57.29578F;
            }
        }
        this.Nose.rotateAngleX = this.Head.rotateAngleX;
        this.Tongue.rotateAngleX = this.Head.rotateAngleX;
        this.Goatie.rotateAngleX = this.Head.rotateAngleX;
        this.RHorn1.rotateAngleX = this.Head.rotateAngleX;
        this.LHorn1.rotateAngleX = this.Head.rotateAngleX;
        this.RHorn2.rotateAngleX = this.Head.rotateAngleX;
        this.LHorn2.rotateAngleX = this.Head.rotateAngleX;
        this.RHorn3.rotateAngleX = this.Head.rotateAngleX;
        this.LHorn3.rotateAngleX = this.Head.rotateAngleX;
        this.RHorn4.rotateAngleX = this.Head.rotateAngleX;
        this.LHorn4.rotateAngleX = this.Head.rotateAngleX;
        this.RHorn5.rotateAngleX = this.Head.rotateAngleX;
        this.LHorn5.rotateAngleX = this.Head.rotateAngleX;
        if (this.bleat) {
            this.Tongue.rotateAngleX = -5 / 57.29578F;
            this.Goatie.rotateAngleX = 0.0F;
        }
        this.Nose.rotateAngleY = this.Head.rotateAngleY;
        this.Mouth.rotateAngleY = this.Head.rotateAngleY;
        this.Tongue.rotateAngleY = this.Head.rotateAngleY;
        this.LEar.rotateAngleY = this.Head.rotateAngleY;
        this.REar.rotateAngleY = this.Head.rotateAngleY;
        this.Goatie.rotateAngleY = this.Head.rotateAngleY;
        this.RHorn1.rotateAngleY = this.Head.rotateAngleY;
        this.LHorn1.rotateAngleY = this.Head.rotateAngleY;
        this.RHorn2.rotateAngleY = this.Head.rotateAngleY;
        this.LHorn2.rotateAngleY = this.Head.rotateAngleY;
        this.RHorn3.rotateAngleY = this.Head.rotateAngleY;
        this.LHorn3.rotateAngleY = this.Head.rotateAngleY;
        this.RHorn4.rotateAngleY = this.Head.rotateAngleY;
        this.LHorn4.rotateAngleY = this.Head.rotateAngleY;
        this.RHorn5.rotateAngleY = this.Head.rotateAngleY;
        this.LHorn5.rotateAngleY = this.Head.rotateAngleY;

        // Interpolation factor for smoother animations
        float interpolationFactor = 0.1F;

        // Tail movement with interpolation
        float targetTailAngleX = this.tailMov / 57.29578F;
        float interpolatedTailAngleX = this.prevTailAngleX + (targetTailAngleX - this.prevTailAngleX) * interpolationFactor;
        this.Tail.rotateAngleX = interpolatedTailAngleX;

        // Ears movement with interpolation
        float targetEarAngleX = (!this.bleat && this.attacking == 0) ? baseAngle + (this.earMov / 57.29578F) : this.Head.rotateAngleX;
        float interpolatedLEarAngleX = this.prevLEarAngleX + (targetEarAngleX - this.prevLEarAngleX) * interpolationFactor;
        float interpolatedREarAngleX = this.prevREarAngleX + (targetEarAngleX - this.prevREarAngleX) * interpolationFactor;
        this.LEar.rotateAngleX = interpolatedLEarAngleX;
        this.REar.rotateAngleX = interpolatedREarAngleX;

        // Mouth movement with interpolation
        float targetMouthAngleX = this.bleat ? 0.0F : (this.attacking != 0 ? this.attacking / 57.29578F : baseAngle);
        float interpolatedMouthAngleX = this.prevMouthAngleX + (targetMouthAngleX - this.prevMouthAngleX) * interpolationFactor;
        this.Mouth.rotateAngleX = interpolatedMouthAngleX;

        // Save the current values for the next frame's interpolation
        this.prevTailAngleX = interpolatedTailAngleX;
        this.prevLEarAngleX = interpolatedLEarAngleX;
        this.prevREarAngleX = interpolatedREarAngleX;
        this.prevMouthAngleX = interpolatedMouthAngleX;
    }
}
