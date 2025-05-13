/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelFishy<T extends MoCEntityFishy> extends EntityModel<T> {

    public ModelRenderer Body;
    public ModelRenderer UpperFin;
    public ModelRenderer LowerFin;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;
    public ModelRenderer Tail;
    private MoCEntityFishy smallFish;

    public MoCModelFishy() {
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0.0F, 0.0F, -3.5F, 1, 5, 5, 0.0F);
        this.Body.setRotationPoint(0.0F, 18F, -1F);
        this.Body.rotateAngleX = 0.7853981F;
        this.Tail = new ModelRenderer(this, 12, 0);
        this.Tail.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        this.Tail.setRotationPoint(0.0F, 20.5F, 3F);
        this.Tail.rotateAngleX = 0.7853981F;
    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.smallFish = entityIn;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        float yOffset = this.smallFish.getAdjustedYOffset();
        float xOffset = this.smallFish.getAdjustedXOffset();
        float zOffset = this.smallFish.getAdjustedZOffset();
        matrixStackIn.push();
        matrixStackIn.translate(xOffset, yOffset, zOffset);
        this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        matrixStackIn.pop();
    }

    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.Tail.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
    }
}
