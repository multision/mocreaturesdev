/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.entity.hunter.MoCEntityBear;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBear extends MoCRenderMoC<MoCEntityBear, MoCModelBear<MoCEntityBear>> {

    public MoCRenderBear(EntityRendererManager renderManagerIn, MoCModelBear modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityBear entitybear, MatrixStack matrixStackIn, float f) {
        stretch(matrixStackIn, entitybear);
        super.preRenderCallback(entitybear, matrixStackIn, f);

    }

    protected void stretch(MatrixStack matrixStackIn, MoCEntityBear entitybear) {
        float sizeFactor = entitybear.getAge() * 0.01F;
        if (entitybear.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        sizeFactor *= entitybear.getBearSize();
        matrixStackIn.scale(sizeFactor, sizeFactor, sizeFactor);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityBear entitybear) {
        return entitybear.getTexture();
    }
}
