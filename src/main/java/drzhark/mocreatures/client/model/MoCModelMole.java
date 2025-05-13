/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityMole;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelMole<T extends MoCEntityMole> extends EntityModel<T> {

    ModelRenderer Nose;
    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Back;
    ModelRenderer Tail;
    ModelRenderer LLeg;
    ModelRenderer LFingers;
    ModelRenderer RLeg;
    ModelRenderer RFingers;
    ModelRenderer LRearLeg;
    ModelRenderer RRearLeg;
    private float yOffset;

    public MoCModelMole() {
        this.textureWidth = 64;
        this.textureHeight = 32;

        this.Nose = new ModelRenderer(this, 0, 25);
        this.Nose.addBox(-1F, 0F, -4F, 2, 2, 3);
        this.Nose.setRotationPoint(0F, 20F, -6F);
        setRotation(this.Nose, 0.2617994F, 0F, 0F);

        this.Head = new ModelRenderer(this, 0, 18);
        this.Head.addBox(-3F, -2F, -2F, 6, 4, 3);
        this.Head.setRotationPoint(0F, 20F, -6F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-5F, 0F, 0F, 10, 6, 10);
        this.Body.setRotationPoint(0F, 17F, -6F);

        this.Back = new ModelRenderer(this, 18, 16);
        this.Back.addBox(-4F, -3F, 0F, 8, 5, 4);
        this.Back.setRotationPoint(0F, 21F, 4F);

        this.Tail = new ModelRenderer(this, 52, 8);
        this.Tail.addBox(-0.5F, 0F, 1F, 1, 1, 5);
        this.Tail.setRotationPoint(0F, 21F, 6F);
        setRotation(this.Tail, -0.3490659F, 0F, 0F);

        this.LLeg = new ModelRenderer(this, 10, 25);
        this.LLeg.addBox(0F, -2F, -1F, 6, 4, 2);
        this.LLeg.setRotationPoint(4F, 21F, -4F);
        setRotation(this.LLeg, 0F, 0F, 0.2268928F);

        this.LFingers = new ModelRenderer(this, 44, 8);
        this.LFingers.addBox(5F, -2F, 1F, 1, 4, 1);
        this.LFingers.setRotationPoint(4F, 21F, -4F);
        setRotation(this.LFingers, 0F, 0F, 0.2268928F);

        this.RLeg = new ModelRenderer(this, 26, 25);
        this.RLeg.addBox(-6F, -2F, -1F, 6, 4, 2);
        this.RLeg.setRotationPoint(-4F, 21F, -4F);
        setRotation(this.RLeg, 0F, 0F, -0.2268928F);

        this.RFingers = new ModelRenderer(this, 48, 8);
        this.RFingers.addBox(-6F, -2F, 1F, 1, 4, 1);
        this.RFingers.setRotationPoint(-4F, 21F, -4F);
        setRotation(this.RFingers, 0F, 0F, -0.2268928F);

        this.LRearLeg = new ModelRenderer(this, 36, 0);
        this.LRearLeg.addBox(0F, -2F, -1F, 2, 3, 5);
        this.LRearLeg.setRotationPoint(3F, 22F, 5F);
        setRotation(this.LRearLeg, -0.2792527F, 0.5235988F, 0F);

        this.RRearLeg = new ModelRenderer(this, 50, 0);
        this.RRearLeg.addBox(-2F, -2F, -1F, 2, 3, 5);
        this.RRearLeg.setRotationPoint(-3F, 22F, 5F);
        setRotation(this.RRearLeg, -0.2792527F, -0.5235988F, 0F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.yOffset = entityIn.getAdjustedYOffset();
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.push();
        matrixStackIn.translate(0F, yOffset, 0F);
        this.Nose.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Back.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LFingers.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RFingers.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LRearLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RRearLeg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);

        this.Head.rotateAngleY = netHeadYaw / 57.29578F;
        this.Head.rotateAngleX = headPitch / 57.29578F;
        this.Nose.rotateAngleX = 0.2617994F + this.Head.rotateAngleX;
        this.Nose.rotateAngleY = this.Head.rotateAngleY;

        float RLegXRot = MathHelper.cos((limbSwing) + 3.141593F) * 0.8F * limbSwingAmount;
        float LLegXRot = MathHelper.cos(limbSwing) * 0.8F * limbSwingAmount;

        this.RLeg.rotateAngleY = RLegXRot;
        this.RFingers.rotateAngleY = this.RLeg.rotateAngleY;
        this.LLeg.rotateAngleY = LLegXRot;
        this.LFingers.rotateAngleY = this.LLeg.rotateAngleY;
        this.RRearLeg.rotateAngleY = -0.5235988F + LLegXRot;
        this.LRearLeg.rotateAngleY = 0.5235988F + RLegXRot;

        this.Tail.rotateAngleZ = this.LLeg.rotateAngleX * 0.625F;
    }
}
