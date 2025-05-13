/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntityAnt;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelAnt<T extends MoCEntityAnt> extends EntityModel<T> {

    ModelRenderer Head;
    ModelRenderer Mouth;
    ModelRenderer RightAntenna;
    ModelRenderer LeftAntenna;
    ModelRenderer Thorax;
    ModelRenderer Abdomen;
    ModelRenderer MidLegs;
    ModelRenderer FrontLegs;
    ModelRenderer RearLegs;

    public MoCModelAnt() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 11);
        this.Head.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Head.setRotationPoint(0F, 21.9F, -1.3F);
        setRotation(this.Head, -2.171231F, 0F, 0F);

        this.Mouth = new ModelRenderer(this, 8, 10);
        this.Mouth.addBox(0F, 0F, 0F, 2, 1, 0);
        this.Mouth.setRotationPoint(-1F, 22.3F, -1.9F);
        setRotation(this.Mouth, -0.8286699F, 0F, 0F);

        this.RightAntenna = new ModelRenderer(this, 0, 6);
        this.RightAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.RightAntenna.setRotationPoint(-0.5F, 21.7F, -2.3F);
        setRotation(this.RightAntenna, -1.041001F, 0.7853982F, 0F);

        this.LeftAntenna = new ModelRenderer(this, 4, 6);
        this.LeftAntenna.addBox(-0.5F, 0F, -1F, 1, 0, 1);
        this.LeftAntenna.setRotationPoint(0.5F, 21.7F, -2.3F);
        setRotation(this.LeftAntenna, -1.041001F, -0.7853982F, 0F);

        this.Thorax = new ModelRenderer(this, 0, 0);
        this.Thorax.addBox(-0.5F, 1.5F, -1F, 1, 1, 2);
        this.Thorax.setRotationPoint(0F, 20F, -0.5F);

        this.Abdomen = new ModelRenderer(this, 8, 1);
        this.Abdomen.addBox(-0.5F, -0.2F, -1F, 1, 2, 1);
        this.Abdomen.setRotationPoint(0F, 21.5F, 0.3F);
        setRotation(this.Abdomen, 1.706911F, 0F, 0F);

        this.MidLegs = new ModelRenderer(this, 4, 8);
        this.MidLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.MidLegs.setRotationPoint(0F, 22F, -0.7F);
        setRotation(this.MidLegs, 0.5948578F, 0F, 0F);

        this.FrontLegs = new ModelRenderer(this, 0, 8);
        this.FrontLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.FrontLegs.setRotationPoint(0F, 22F, -0.8F);
        setRotation(this.FrontLegs, -0.6192304F, 0F, 0F);

        this.RearLegs = new ModelRenderer(this, 0, 8);
        this.RearLegs.addBox(-1F, 0F, 0F, 2, 3, 0);
        this.RearLegs.setRotationPoint(0F, 22F, 0F);
        setRotation(this.RearLegs, 0.9136644F, 0F, 0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Mouth.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RightAntenna.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.LeftAntenna.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Thorax.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Abdomen.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.MidLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.FrontLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RearLegs.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float legMov = MathHelper.cos((limbSwing) + 3.141593F) * limbSwingAmount;
        float legMovB = MathHelper.cos(limbSwing) * limbSwingAmount;
        this.FrontLegs.rotateAngleX = -0.6192304F + legMov;
        this.MidLegs.rotateAngleX = 0.5948578F + legMovB;
        this.RearLegs.rotateAngleX = 0.9136644F + legMov;
    }
}
