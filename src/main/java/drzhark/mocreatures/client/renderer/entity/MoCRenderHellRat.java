/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelRat;
import drzhark.mocreatures.entity.hostile.MoCEntityHellRat;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat<MoCEntityHellRat, MoCModelRat<MoCEntityHellRat>> {

    public MoCRenderHellRat(EntityRendererManager renderManagerIn, MoCModelRat modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    protected void stretch(MoCEntityHellRat entityhellrat, MatrixStack matrixStackIn) {
        float f = 1.3F;
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityHellRat entityhellrat) {
        return entityhellrat.getTexture();
    }
}
