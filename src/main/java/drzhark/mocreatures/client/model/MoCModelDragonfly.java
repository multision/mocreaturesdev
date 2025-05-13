/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelDragonfly<T extends MoCEntityDragonfly> extends EntityModel<T> {

    ModelRenderer Abdomen;
    ModelRenderer FrontLegs;
    ModelRenderer RAntenna;
    ModelRenderer LAntenna;
    ModelRenderer RearLegs;
    ModelRenderer MidLegs;
    ModelRenderer Mouth;
    ModelRenderer WingRearRight;
    ModelRenderer Thorax;
    ModelRenderer WingFrontRight;
    ModelRenderer WingFrontLeft;
    ModelRenderer WingRearLeft;
    ModelRenderer Head;

    public MoCModelDragonfly() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 4);
        this.Head.addBox(-1F, 0F, -1F, 2, 1, 2);
        this.Head.setRotationPoint(0F, 21F, -2F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.RAntenna = new ModelRenderer(this, 0, 7);
        this.RAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.RAntenna.setRotationPoint(-0.5F, 19.7F, -2.3F);
        setRotation(this.RAntenna, -1.041001F, 0.7853982F, 0F);

        this.LAntenna = new ModelRenderer(this, 4, 7);
        this.LAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.LAntenna.setRotationPoint(0.5F, 19.7F, -2.3F);
        setRotation(this.LAntenna, -1.041001F, -0.7853982F, 0F);

        this.Mouth = new ModelRenderer(this, 0, 11);
        this.Mouth.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Mouth.setRotationPoint(0F, 21.1F, -2.3F);
        setRotation(this.Mouth, -2.171231F, 0F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Thorax.setRotationPoint(0F, 20F, -1F);

        this.Abdomen = new ModelRenderer(this, 8, 0);
        this.Abdomen.addBox(-0.5F, 0F, -1F, 1, 7, 1);
        this.Abdomen.setRotationPoint(0F, 20.5F, 0F);
        setRotation(this.Abdomen, 1.427659F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 8);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.FrontLegs.setRotationPoint(0F, 21.5F, -1.8F);
        setRotation(this.FrontLegs, 0.1487144F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 4, 8);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.MidLegs.setRotationPoint(0F, 22F, -1.2F);
        setRotation(this.MidLegs, 0.5948578F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 8, 8);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.RearLegs.setRotationPoint(0F, 22F, -0.4F);
        setRotation(this.RearLegs, 1.070744F, 0F, 0F);

        this.WingFrontRight = new ModelRenderer(this, 0, 28);
        this.WingFrontRight.addBox(-7F, 0F, -1F, 7, 0, 2);
        this.WingFrontRight.setRotationPoint(-1F, 20F, -1F);
        setRotation(this.WingFrontRight, 0F, -0.1396263F, 0.0872665F);

        this.WingFrontLeft = new ModelRenderer(this, 0, 30);
        this.WingFrontLeft.addBox(0F, 0F, -1F, 7, 0, 2);
        this.WingFrontLeft.setRotationPoint(1F, 20F, -1F);
        setRotation(this.WingFrontLeft, 0F, 0.1396263F, -0.0872665F);

        this.WingRearRight = new ModelRenderer(this, 0, 24);
        this.WingRearRight.addBox(-7F, 0F, -1F, 7, 0, 2);
        this.WingRearRight.setRotationPoint(-1F, 20F, -1F);
        setRotation(this.WingRearRight, 0F, 0.3490659F, -0.0872665F);

        this.WingRearLeft = new ModelRenderer(this, 0, 26);
        this.WingRearLeft.addBox(0F, 0F, -1F, 7, 0, 2);
        this.WingRearLeft.setRotationPoint(1F, 20F, -1F);
        setRotation(this.WingRearLeft, 0F, -0.3490659F, 0.0872665F);

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Abdomen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.FrontLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RAntenna.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LAntenna.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RearLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.MidLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Mouth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Thorax.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);

        matrixStackIn.push();
        RenderSystem.enableBlend();
        float transparency = 0.6F;
        RenderSystem.defaultBlendFunc();
        RenderSystem.color4f(0.8F, 0.8F, 0.8F, transparency);
        //matrixStackIn.scale(1.3F, 1.0F, 1.3F);
        this.WingRearRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.WingFrontRight.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.WingFrontLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.WingRearLeft.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        RenderSystem.disableBlend();
        matrixStackIn.pop();

    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);

        /*
         * limbSwing = distance walked limbSwingAmount = speed 0 - 1 ageInTicks = timer
         */

        float WingRot = 0F;
        float legMov;
        float legMovB;

        if (entityIn.getIsFlying() || entityIn.getMotion().getY() < -0.1D) {
            WingRot = MathHelper.cos((ageInTicks * 2.0F)) * 0.5F;
            legMov = (limbSwingAmount * 1.5F);
            legMovB = legMov;
        } else {
            legMov = MathHelper.cos((limbSwing * 1.5F) + 3.141593F) * 2.0F * limbSwingAmount;
            legMovB = MathHelper.cos(limbSwing * 1.5F) * 2.0F * limbSwingAmount;
        }

        this.WingFrontRight.rotateAngleZ = WingRot;
        this.WingRearLeft.rotateAngleZ = WingRot;

        this.WingFrontLeft.rotateAngleZ = -WingRot;
        this.WingRearRight.rotateAngleZ = -WingRot;

        this.FrontLegs.rotateAngleX = 0.1487144F + legMov;
        this.MidLegs.rotateAngleX = 0.5948578F + legMovB;
        this.RearLegs.rotateAngleX = 1.070744F + legMov;
    }
}
