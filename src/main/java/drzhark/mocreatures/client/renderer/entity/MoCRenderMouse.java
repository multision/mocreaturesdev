/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelMouse;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderMouse extends MoCRenderMoC<MoCEntityMouse, MoCModelMouse<MoCEntityMouse>> {

    public MoCRenderMouse(EntityRendererManager renderManagerIn, MoCModelMouse modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityMouse entitymouse, MatrixStack matrixStackIn, float partialTickTime) {
        stretch(matrixStackIn);
        // When mice are picked up
        if (entitymouse.upsideDown()) {
            upsideDown(matrixStackIn);
        }

        if (entitymouse.isOnLadder()) {
            rotateAnimal(matrixStackIn);
        }
    }

    protected void rotateAnimal(MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90.0F));
        matrixStackIn.translate(0.0F, 0.4F, 0.0F);
    }

    protected void stretch(MatrixStack matrixStackIn) {
        float f = 0.6F;
        matrixStackIn.scale(f, f, f);
    }

    protected void upsideDown(MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(-90.0F));
        matrixStackIn.translate(-0.55F, 0.0F, 0.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityMouse entitymouse) {
        return entitymouse.getTexture();
    }
}
