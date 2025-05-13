/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelButterfly;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderButterfly extends MoCRenderInsect<MoCEntityButterfly, MoCModelButterfly<MoCEntityButterfly>> {

    public MoCRenderButterfly(EntityRendererManager renderManagerIn, MoCModelButterfly modelbase) {
        super(renderManagerIn, modelbase);

    }

    @Override
    protected void preRenderCallback(MoCEntityButterfly entitybutterfly, MatrixStack matrixStackIn, float par2) {
        if (entitybutterfly.isOnAir() || !entitybutterfly.isOnGround()) {
            adjustHeight(entitybutterfly, entitybutterfly.tFloat(), matrixStackIn);
        }
        if (entitybutterfly.climbing()) {
            rotateAnimal(entitybutterfly, matrixStackIn);
        }
        stretch(entitybutterfly, matrixStackIn);
    }

    protected void adjustHeight(MoCEntityButterfly entitybutterfly, float FHeight, MatrixStack matrixStackIn) {
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityButterfly entitybutterfly) {
        return entitybutterfly.getTexture();
    }
}
