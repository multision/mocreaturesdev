/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;


public class MoCModelSnail<T extends MoCEntitySnail> extends EntityModel<T> {

    ModelRenderer Head;
    ModelRenderer Antenna;
    ModelRenderer Body;
    ModelRenderer ShellUp;
    ModelRenderer ShellDown;
    ModelRenderer Tail;
    private boolean isHiding;
    private int type;

    public MoCModelSnail() {
        this.textureWidth = 32;
        this.textureHeight = 32;

        this.Head = new ModelRenderer(this, 0, 6);
        this.Head.addBox(-1F, 0F, -1F, 2, 2, 2);
        this.Head.setRotationPoint(0F, 21.8F, -1F);
        setRotation(this.Head, -0.4537856F, 0F, 0F);

        this.Antenna = new ModelRenderer(this, 8, 0);
        this.Antenna.addBox(-1.5F, 0F, -1F, 3, 2, 0);
        this.Antenna.setRotationPoint(0F, 19.4F, -1F);
        setRotation(this.Antenna, 0.0523599F, 0F, 0F);

        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(-1F, 0F, -1F, 2, 2, 4);
        this.Body.setRotationPoint(0F, 22F, 0F);

        this.ShellUp = new ModelRenderer(this, 12, 0);
        this.ShellUp.addBox(-1F, -3F, 0F, 2, 3, 3);
        this.ShellUp.setRotationPoint(0F, 22.3F, -0.2F);
        setRotation(this.ShellUp, 0.2268928F, 0F, 0F);

        this.ShellDown = new ModelRenderer(this, 12, 0);
        this.ShellDown.addBox(-1F, 0F, 0F, 2, 3, 3);
        this.ShellDown.setRotationPoint(0F, 21F, 0F);

        this.Tail = new ModelRenderer(this, 1, 2);
        this.Tail.addBox(-1F, 0F, 0F, 2, 1, 3);
        this.Tail.setRotationPoint(0F, 23F, 3F);

    }

    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.isHiding = entityIn.getIsHiding();
        this.type = entityIn.getTypeMoC();
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (this.isHiding && this.type < 5) {
            this.ShellDown.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        } else {
            this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Antenna.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.ShellUp.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
            this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn);
        }
    }

    private void setRotation(ModelRenderer model, float x, float y, float z) {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }

    public void setRotationAngles(MoCEntitySnail entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float tailMov = MathHelper.cos((ageInTicks * 0.3F)) * 0.8F;
        if (limbSwingAmount < 0.1F) {
            tailMov = 0F;
        }
        this.Tail.rotationPointZ = 2F + tailMov;
        this.ShellUp.rotateAngleX = 0.2268928F + (tailMov / 10F);
    }
}
