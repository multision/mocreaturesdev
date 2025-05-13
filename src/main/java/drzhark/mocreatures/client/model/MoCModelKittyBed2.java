/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;


public class MoCModelKittyBed2<T extends MoCEntityKittyBed> extends EntityModel<T> {

    ModelRenderer Sheet;

    public MoCModelKittyBed2() {
        float limbSwing = 0.0F;
        this.Sheet = new ModelRenderer(this, 0, 15);
        this.Sheet.addBox(0.0F, 0.0F, 0.0F, 16, 3, 14, limbSwing);
        this.Sheet.setRotationPoint(-8F, 21F, -7F);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.Sheet.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
