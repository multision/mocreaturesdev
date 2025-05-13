/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelBunny;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBunny extends MoCRenderMoC<MoCEntityBunny, MoCModelBunny<MoCEntityBunny>> {

    public MoCRenderBunny(EntityRendererManager renderManagerIn, MoCModelBunny modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityBunny entitybunny) {
        return entitybunny.getTexture();
    }

    @Override
    protected void preRenderCallback(MoCEntityBunny entitybunny, MatrixStack matrixStackIn, float f) {
        if (!entitybunny.getIsAdult()) {
            stretch(entitybunny, matrixStackIn);
        }
        rotBunny(entitybunny, matrixStackIn);
        adjustOffsets(entitybunny.getAdjustedXOffset(), entitybunny.getAdjustedYOffset(), entitybunny.getAdjustedZOffset(), matrixStackIn);
    }

    protected void rotBunny(MoCEntityBunny entitybunny, MatrixStack matrixStackIn) {
        if (!entitybunny.isOnGround() && (entitybunny.getRidingEntity() == null)) {
            if (entitybunny.getMotion().getY() > 0.5D) {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees(35F));
            } else if (entitybunny.getMotion().getY() < -0.5D) {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees(-35F));
            } else {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees((float) (entitybunny.getMotion().getY() * 70D)));
            }
        }
    }

    protected void stretch(MoCEntityBunny entitybunny, MatrixStack matrixStackIn) {
        float f = entitybunny.getAge() * 0.01F;
        matrixStackIn.scale(f, f, f);
    }
}
