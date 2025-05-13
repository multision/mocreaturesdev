/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelHorse;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHorse extends MoCRenderMoC<MoCEntityHorse, MoCModelHorse<MoCEntityHorse>> {

    public MoCRenderHorse(EntityRendererManager renderManagerIn, MoCModelHorse modelbase) {
        super(renderManagerIn, modelbase, 0.5F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityHorse entityhorse) {
        return entityhorse.getTexture();
    }

    protected void adjustHeight(MoCEntityHorse entityhorse, float FHeight, MatrixStack matrixStackIn) {
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityHorse entityhorse, MatrixStack matrixStackIn, float f) {
        if (!entityhorse.getIsAdult() || entityhorse.getTypeMoC() > 64) {
            stretch(entityhorse, matrixStackIn);
        }
        if (entityhorse.getIsGhost()) {
            adjustHeight(entityhorse, -0.3F + (entityhorse.tFloat() / 5F), matrixStackIn);
        }
        super.preRenderCallback(entityhorse, matrixStackIn, f);
    }

    protected void stretch(MoCEntityHorse entityhorse, MatrixStack matrixStackIn) {
        float sizeFactor = entityhorse.getAge() * 0.01F;
        if (entityhorse.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        if (entityhorse.getTypeMoC() > 64) //donkey
        {
            sizeFactor *= 0.9F;
        }
        matrixStackIn.scale(sizeFactor, sizeFactor, sizeFactor);
    }
}
