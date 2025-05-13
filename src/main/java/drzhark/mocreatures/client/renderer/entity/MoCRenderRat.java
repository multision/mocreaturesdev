/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelRat;
import drzhark.mocreatures.entity.hostile.MoCEntityRat;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderRat<T extends MoCEntityRat, M extends MoCModelRat<T>> extends MobRenderer<T, M> {

    public MoCRenderRat(EntityRendererManager renderManagerIn, M modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    public void render(T entityrat, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityrat, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(T entityrat, MatrixStack matrixStackIn, float f) {
        stretch(entityrat, matrixStackIn);
        if (entityrat.isOnLadder()) {
            rotateAnimal(entityrat, matrixStackIn);
        }
    }

    protected void rotateAnimal(T entityrat, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90.0F));
        matrixStackIn.translate(0.0F, 0.4F, 0.0F);
    }

    protected void stretch(T entityrat, MatrixStack matrixStackIn) {
        float f = 0.8F;
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(T entityrat) {
        return entityrat.getTexture();
    }
}
