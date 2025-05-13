/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelPetScorpion;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderPetScorpion extends MoCRenderMoC<MoCEntityPetScorpion, MoCModelPetScorpion<MoCEntityPetScorpion>> {

    public MoCRenderPetScorpion(EntityRendererManager renderManagerIn, MoCModelPetScorpion modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    protected float getDeathMaxRotation(MoCEntityPetScorpion entityscorpion) {
        return 180.0F;
    }

    @Override
    protected void preRenderCallback(MoCEntityPetScorpion entityscorpion, MatrixStack matrixStackIn, float f) {
        /* TODO: Fix rider rotation
        if (entityscorpion.isOnLadder()) {
            rotateAnimal(entityscorpion);
        }
        */

        if (entityscorpion.getIsSitting()) {
            float factorY = 0.4F * (entityscorpion.getAge() / 100.0F);
            matrixStackIn.translate(0.0F, factorY, 0.0F);
        }

        if (!entityscorpion.getIsAdult()) {
            stretch(entityscorpion, matrixStackIn);
            if (entityscorpion.getRidingEntity() != null) {
                upsideDown(entityscorpion, matrixStackIn);
            }
        } else {
            adjustHeight(entityscorpion, matrixStackIn);
        }
    }

    protected void upsideDown(MoCEntityPetScorpion entityscorpion, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(-90.0F));
        matrixStackIn.translate(-1.5F, -0.5F, -2.5F);
    }

    protected void adjustHeight(MoCEntityPetScorpion entityscorpion, MatrixStack matrixStackIn) {
        matrixStackIn.translate(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(MoCEntityPetScorpion entityscorpion, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90.0F));
        matrixStackIn.translate(0.0F, 1.0F, 0.0F);
    }

    protected void stretch(MoCEntityPetScorpion entityscorpion, MatrixStack matrixStackIn) {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult()) {
            f = entityscorpion.getAge() * 0.01F;
        }
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityPetScorpion entityscorpion) {
        return entityscorpion.getTexture();
    }
}
