/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelEgg;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderEgg extends MobRenderer<MoCEntityEgg, MoCModelEgg<MoCEntityEgg>> {

    public MoCRenderEgg(EntityRendererManager renderManagerIn, MoCModelEgg modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityEgg entityegg, MatrixStack matrixStackIn, float f) {
        stretch(entityegg, matrixStackIn);
        super.preRenderCallback(entityegg, matrixStackIn, f);

    }

    protected void stretch(MoCEntityEgg entityegg, MatrixStack matrixStackIn) {
        float f = entityegg.getSize() * 0.01F;
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityEgg entityegg) {
        return entityegg.getTexture();
    }
}
