/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MoCModelEgg<T extends MoCEntityEgg> extends EntityModel<T> {

    public ModelRenderer Egg;
    ModelRenderer Egg1;
    ModelRenderer Egg2;
    ModelRenderer Egg3;
    ModelRenderer Egg4;
    ModelRenderer Egg5;

    public MoCModelEgg() {
        //textureWidth = 64;
        //textureHeight = 32;

        this.Egg1 = new ModelRenderer(this, 0, 0);
        this.Egg1.addBox(0F, 0F, 0F, 3, 3, 3);
        this.Egg1.setRotationPoint(0F, 20F, 0F);

        this.Egg2 = new ModelRenderer(this, 10, 0);
        this.Egg2.addBox(0F, 0F, 0F, 2, 1, 2);
        this.Egg2.setRotationPoint(0.5F, 19.5F, 0.5F);

        this.Egg3 = new ModelRenderer(this, 30, 0);
        this.Egg3.addBox(0F, 0F, 0F, 2, 1, 2);
        this.Egg3.setRotationPoint(0.5F, 22.5F, 0.5F);

        this.Egg4 = new ModelRenderer(this, 24, 0);
        this.Egg4.addBox(0F, 0F, 0F, 1, 2, 2);
        this.Egg4.setRotationPoint(-0.5F, 20.5F, 0.5F);

        this.Egg5 = new ModelRenderer(this, 18, 0);
        this.Egg5.addBox(0F, 0F, 0F, 1, 2, 2);
        this.Egg5.setRotationPoint(2.5F, 20.5F, 0.5F);

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Egg1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Egg2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Egg3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Egg4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Egg5.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        //super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, f5);
    }
}
