/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelOstrich;
import drzhark.mocreatures.entity.neutral.MoCEntityOstrich;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderOstrich extends MoCRenderMoC<MoCEntityOstrich, MoCModelOstrich<MoCEntityOstrich>> {

    public MoCRenderOstrich(EntityRendererManager renderManagerIn, MoCModelOstrich modelbase, float f) {
        super(renderManagerIn, modelbase, 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityOstrich entityostrich) {
        return entityostrich.getTexture();
    }

    protected void adjustHeight(MoCEntityOstrich entityliving, float FHeight, MatrixStack matrixStackIn) {
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityOstrich entityliving, MatrixStack matrixStackIn, float f) {
        MoCEntityOstrich entityostrich = entityliving;
        if (entityostrich.getTypeMoC() == 1) {
            stretch(entityostrich, matrixStackIn);
        }

        super.preRenderCallback(entityliving, matrixStackIn, f);

    }

    protected void stretch(MoCEntityOstrich entityostrich, MatrixStack matrixStackIn) {

        float f = entityostrich.getAge() * 0.01F;
        matrixStackIn.scale(f, f, f);
    }
}
