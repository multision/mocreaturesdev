/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;


public class MoCModelKittyBed<T extends MoCEntityKittyBed> extends EntityModel<T> {

    public boolean hasMilk;
    public boolean hasFood;
    public boolean pickedUp;
    public float milklevel;
    ModelRenderer TableL;
    ModelRenderer TableR;
    ModelRenderer Table_B;
    ModelRenderer FoodT;
    ModelRenderer FoodTraySide;
    ModelRenderer FoodTraySideB;
    ModelRenderer FoodTraySideC;
    ModelRenderer FoodTraySideD;
    ModelRenderer Milk;
    ModelRenderer PetFood;
    ModelRenderer Bottom;

    public MoCModelKittyBed() {
        float limbSwing = 0.0F;
        this.TableL = new ModelRenderer(this, 30, 8);
        this.TableL.addBox(-8F, 0.0F, 7F, 16, 6, 1, limbSwing);
        this.TableL.setRotationPoint(0.0F, 18F, 0.0F);
        this.TableR = new ModelRenderer(this, 30, 8);
        this.TableR.addBox(-8F, 18F, -8F, 16, 6, 1, limbSwing);
        this.TableR.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.Table_B = new ModelRenderer(this, 30, 0);
        this.Table_B.addBox(-8F, -3F, 0.0F, 16, 6, 1, limbSwing);
        this.Table_B.setRotationPoint(8F, 21F, 0.0F);
        this.Table_B.rotateAngleY = 1.5708F;
        this.FoodT = new ModelRenderer(this, 14, 0);
        this.FoodT.addBox(1.0F, 1.0F, 1.0F, 4, 1, 4, limbSwing);
        this.FoodT.setRotationPoint(-16F, 22F, 0.0F);
        this.FoodTraySide = new ModelRenderer(this, 0, 0);
        this.FoodTraySide.addBox(-16F, 21F, 5F, 5, 3, 1, limbSwing);
        this.FoodTraySide.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FoodTraySideB = new ModelRenderer(this, 0, 0);
        this.FoodTraySideB.addBox(-15F, 21F, 0.0F, 5, 3, 1, limbSwing);
        this.FoodTraySideB.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.FoodTraySideC = new ModelRenderer(this, 0, 0);
        this.FoodTraySideC.addBox(-3F, -1F, 0.0F, 5, 3, 1, limbSwing);
        this.FoodTraySideC.setRotationPoint(-16F, 22F, 2.0F);
        this.FoodTraySideC.rotateAngleY = 1.5708F;
        this.FoodTraySideD = new ModelRenderer(this, 0, 0);
        this.FoodTraySideD.addBox(-3F, -1F, 0.0F, 5, 3, 1, limbSwing);
        this.FoodTraySideD.setRotationPoint(-11F, 22F, 3F);
        this.FoodTraySideD.rotateAngleY = 1.5708F;
        this.Milk = new ModelRenderer(this, 14, 9);
        this.Milk.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, limbSwing);
        this.Milk.setRotationPoint(-15F, 21F, 1.0F);
        this.PetFood = new ModelRenderer(this, 0, 9);
        this.PetFood.addBox(0.0F, 0.0F, 0.0F, 4, 1, 4, limbSwing);
        this.PetFood.setRotationPoint(-15F, 21F, 1.0F);
        this.Bottom = new ModelRenderer(this, 16, 15);
        this.Bottom.addBox(-10F, 0.0F, -7F, 16, 1, 14, limbSwing);
        this.Bottom.setRotationPoint(2.0F, 23F, 0.0F);
    }

    @Override
    public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        this.TableL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.TableR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Table_B.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Bottom.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        if (!this.pickedUp) {
            this.FoodT.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.FoodTraySide.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.FoodTraySideB.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.FoodTraySideC.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            this.FoodTraySideD.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            if (this.hasMilk) {
                this.Milk.rotationPointY = 21F + this.milklevel;
                this.Milk.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
            if (this.hasFood) {
                this.PetFood.rotationPointY = 21F + this.milklevel;
                this.PetFood.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
            }
        }
    }
}
