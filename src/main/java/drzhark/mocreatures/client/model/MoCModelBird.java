/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.MathHelper;

public class MoCModelBird<T extends MoCEntityBird> extends EntityModel<T> {

    public ModelRenderer head;
    public ModelRenderer body;
    public ModelRenderer leftleg;
    public ModelRenderer rightleg;
    public ModelRenderer rwing;
    public ModelRenderer lwing;
    public ModelRenderer beak;
    public ModelRenderer tail;

    public MoCModelBird() {
        byte byte0 = 16;
        this.head = new ModelRenderer(this, 0, 0);
        this.head.addBox(-1.5F, -3F, -2F, 3, 3, 3, 0.0F);
        this.head.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.beak = new ModelRenderer(this, 14, 0);
        this.beak.addBox(-0.5F, -1.5F, -3F, 1, 1, 2, 0.0F);
        this.beak.setRotationPoint(0.0F, -1 + byte0, -4F);
        this.body = new ModelRenderer(this, 0, 9);
        this.body.addBox(-2F, -4F, -3F, 4, 8, 4, 0.0F);
        this.body.setRotationPoint(0.0F, byte0, 0.0F);
        this.body.rotateAngleX = 1.047198F;
        this.leftleg = new ModelRenderer(this, 26, 0);
        this.leftleg.addBox(-1F, 0.0F, -4F, 3, 4, 3);
        this.leftleg.setRotationPoint(-2F, 3 + byte0, 1.0F);
        this.rightleg = new ModelRenderer(this, 26, 0);
        this.rightleg.addBox(-1F, 0.0F, -4F, 3, 4, 3);
        this.rightleg.setRotationPoint(1.0F, 3 + byte0, 1.0F);
        this.rwing = new ModelRenderer(this, 24, 13);
        this.rwing.addBox(-1F, 0.0F, -3F, 1, 5, 5);
        this.rwing.setRotationPoint(-2F, -2 + byte0, 0.0F);
        this.lwing = new ModelRenderer(this, 24, 13);
        this.lwing.addBox(0.0F, 0.0F, -3F, 1, 5, 5);
        this.lwing.setRotationPoint(2.0F, -2 + byte0, 0.0F);
        this.tail = new ModelRenderer(this, 0, 23);
        this.tail.addBox(-6F, 5F, 2.0F, 4, 1, 4, 0.0F);
        this.tail.setRotationPoint(4F, -3 + byte0, 0.0F);
        this.tail.rotateAngleX = 0.261799F;
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.beak.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.body.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.leftleg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rightleg.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.rwing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.lwing.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.rotateAngleX = -(headPitch / 2.0F / 57.29578F);
        //head.rotateAngleY = netHeadYaw / 2.0F / 57.29578F; //fixed SMP bug
        this.head.rotateAngleY = netHeadYaw / 57.29578F;
        this.beak.rotateAngleY = this.head.rotateAngleY;

        if (entityIn.isOnAir() && entityIn.getRidingEntity() == null) {
            this.leftleg.rotateAngleX = 1.4F;
            this.rightleg.rotateAngleX = 1.4F;
        } else {
            this.leftleg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount;
            this.rightleg.rotateAngleX = MathHelper.cos((limbSwing * 0.6662F) + 3.141593F) * limbSwingAmount;
        }
        this.rwing.rotateAngleZ = ageInTicks;
        this.lwing.rotateAngleZ = -ageInTicks;
    }
}
