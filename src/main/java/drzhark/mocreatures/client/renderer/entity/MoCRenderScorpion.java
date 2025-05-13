/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.hostile.MoCEntityScorpion;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderScorpion extends MoCRenderMoC<MoCEntityScorpion, MoCModelScorpion<MoCEntityScorpion>> {

    public MoCRenderScorpion(EntityRendererManager renderManagerIn, MoCModelScorpion modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    protected float getDeathMaxRotation(MoCEntityScorpion entityscorpion) {
        return 180.0F;
    }

    @Override
    protected void preRenderCallback(MoCEntityScorpion entityscorpion, MatrixStack matrixStackIn, float f) {
        /* TODO: Fix rider rotation
        if (entityscorpion.isOnLadder()) {
            rotateAnimal(entityscorpion);
        }
        */

        if (!entityscorpion.getIsAdult()) {
            stretch(entityscorpion, matrixStackIn);
        } else {
            adjustHeight(entityscorpion, matrixStackIn);
        }
    }

    protected void adjustHeight(MoCEntityScorpion entityscorpion, MatrixStack matrixStackIn) {
        matrixStackIn.translate(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(MatrixStack matrixStackIn, MoCEntityScorpion entityscorpion) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90.0F));
        matrixStackIn.translate(0.0F, 1.0F, 0.0F);
    }

    protected void stretch(MoCEntityScorpion entityscorpion, MatrixStack matrixStackIn) {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult()) {
            f = entityscorpion.getAge() * 0.01F;
        }
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityScorpion entityscorpion) {
        return entityscorpion.getTexture();
    }
}
