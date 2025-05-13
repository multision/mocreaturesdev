/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderInsect<T extends MoCEntityInsect, M extends EntityModel<T>> extends MoCRenderMoC<T, M> {

    public MoCRenderInsect(EntityRendererManager renderManagerIn, M modelbase) {
        super(renderManagerIn, modelbase, 0.0F);

    }

    @Override
    protected void preRenderCallback(T entityinsect, MatrixStack matrixStackIn, float par2) {
        if (entityinsect.climbing()) {
            rotateAnimal(entityinsect, matrixStackIn);
        }

        stretch(entityinsect, matrixStackIn);
    }

    protected void rotateAnimal(T entityinsect, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90F));
    }

    protected void stretch(T entityinsect, MatrixStack matrixStackIn) {
        float sizeFactor = entityinsect.getSizeFactor();
        matrixStackIn.scale(sizeFactor, sizeFactor, sizeFactor);
    }
}
