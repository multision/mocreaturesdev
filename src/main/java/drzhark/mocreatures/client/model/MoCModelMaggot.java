/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelMaggot<T extends MoCEntityMaggot> extends EntityModel<T> {

    ModelRenderer Head;
    ModelRenderer Body;
    ModelRenderer Tail;
    ModelRenderer Tailtip;
    private float limbSwing;
    private float limbSwingAmount;

    public MoCModelMaggot() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 11);
        this.Head.addBox(-1F, -1F, -2F, 2, 2, 2);
        this.Head.setRotationPoint(0F, 23F, -2F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-1.5F, -2F, 0F, 3, 3, 4);
        this.Body.setRotationPoint(0F, 23F, -2F);

        this.Tail = new ModelRenderer(this, 0, 7);
        this.Tail.addBox(-1F, -1F, 0F, 2, 2, 2);
        this.Tail.setRotationPoint(0F, 23F, 2F);

        this.Tailtip = new ModelRenderer(this, 8, 7);
        this.Tailtip.addBox(-0.5F, 0F, 0F, 1, 1, 1);
        this.Tailtip.setRotationPoint(0F, 23F, 4F);
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.limbSwing = limbSwing;
        this.limbSwingAmount = limbSwingAmount;
    }
    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        //limbSwingAmount = movement speed!
        //ageInTicks = timer!
        //System.out.println("ageInTicks = " + ageInTicks);

        matrixStackIn.push();
        RenderSystem.enableBlend();
        //float transparency = 0.9F;
        RenderSystem.defaultBlendFunc();
        //RenderSystem.color4f(1.2F, 1.2F, 1.2F, transparency);
        float f9 = -(MathHelper.cos(this.limbSwing * 3F)) * this.limbSwingAmount * 2F;
        //matrixStackIn.scale(1.0F, 1.0F, 1.0F + (limbSwingAmount * 3F));
        matrixStackIn.scale(1.0F, 1.0F, 1.0F + (f9));

        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tailtip.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        RenderSystem.disableBlend();
        matrixStackIn.pop();

    }

    @SuppressWarnings("unused")
    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
