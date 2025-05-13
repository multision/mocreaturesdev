/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;


public class MoCModelLitterBox<T extends MoCEntityLitterBox> extends EntityModel<T> {

    public boolean usedlitter;
    ModelRenderer Table1;
    ModelRenderer Table3;
    ModelRenderer Table2;
    ModelRenderer Litter;
    ModelRenderer Table4;
    ModelRenderer Bottom;
    ModelRenderer LitterUsed;

    public MoCModelLitterBox() {
        float limbSwing = 0.0F;
        this.Table1 = new ModelRenderer(this, 30, 0);
        this.Table1.addBox(-8F, 0.0F, 7F, 16, 6, 1, limbSwing);
        this.Table1.setRotationPoint(0.0F, 18F, 0.0F);
        this.Table3 = new ModelRenderer(this, 30, 0);
        this.Table3.addBox(-8F, 18F, -8F, 16, 6, 1, limbSwing);
        this.Table3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Table2 = new ModelRenderer(this, 30, 0);
        this.Table2.addBox(-8F, -3F, 0.0F, 16, 6, 1, limbSwing);
        this.Table2.setRotationPoint(8F, 21F, 0.0F);
        this.Table2.rotateAngleY = 1.5708F;
        this.Litter = new ModelRenderer(this, 0, 15);
        this.Litter.addBox(0.0F, 0.0F, 0.0F, 16, 2, 14, limbSwing);
        this.Litter.setRotationPoint(-8F, 21F, -7F);
        this.Table4 = new ModelRenderer(this, 30, 0);
        this.Table4.addBox(-8F, -3F, 0.0F, 16, 6, 1, limbSwing);
        this.Table4.setRotationPoint(-9F, 21F, 0.0F);
        this.Table4.rotateAngleY = 1.5708F;
        this.LitterUsed = new ModelRenderer(this, 16, 15);
        this.LitterUsed.addBox(0.0F, 0.0F, 0.0F, 16, 2, 14, limbSwing);
        this.LitterUsed.setRotationPoint(-8F, 21F, -7F);
        this.Bottom = new ModelRenderer(this, 16, 15);
        this.Bottom.addBox(-10F, 0.0F, -7F, 16, 1, 14, limbSwing);
        this.Bottom.setRotationPoint(2.0F, 23F, 0.0F);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Table1.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Table3.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Table2.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Table4.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Bottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (this.usedlitter) {
            this.LitterUsed.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        } else {
            this.Litter.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        }
    }
}
