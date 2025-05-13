/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderFishy extends MobRenderer<MoCEntityFishy, MoCModelFishy<MoCEntityFishy>> {

    public MoCRenderFishy(EntityRendererManager renderManagerIn, MoCModelFishy modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    public void render(MoCEntityFishy entityfishy, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entityfishy.getTypeMoC() == 0) { // && !MoCreatures.mc.isMultiplayerWorld())
            entityfishy.selectType();
        }
        super.render(entityfishy, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(MoCEntityFishy entityfishy, MatrixStack matrixStackIn, float f) {
        stretch(entityfishy, matrixStackIn);
        matrixStackIn.translate(0.0F, 0.3F, 0.0F);
    }

    protected void stretch(MoCEntityFishy entityfishy, MatrixStack matrixStackIn) {
        matrixStackIn.scale(entityfishy.getAge() * 0.01F, entityfishy.getAge() * 0.01F, entityfishy.getAge() * 0.01F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityFishy entityfishy) {
        return entityfishy.getTexture();
    }
}
