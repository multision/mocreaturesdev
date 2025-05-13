/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import drzhark.mocreatures.client.model.MoCModelWraith;
import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWraith extends MobRenderer<MoCEntityWraith, MoCModelWraith<MoCEntityWraith>> {

    public MoCRenderWraith(EntityRendererManager renderManagerIn, MoCModelWraith modelbiped, float f) {
        super(renderManagerIn, modelbiped, f);
    }

    @Override
    public void render(MoCEntityWraith wraith, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        boolean flag = wraith.isGlowing();
        matrixStackIn.push();
        RenderSystem.enableBlend();
        if (!flag) {
            float transparency = 0.6F;
            RenderSystem.defaultBlendFunc();
            RenderSystem.color4f(0.8F, 0.8F, 0.8F, transparency);
        } else {
            RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
        }
        super.render(wraith, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        RenderSystem.disableBlend();
        matrixStackIn.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityWraith wraith) {
        return wraith.getTexture();
    }
}
