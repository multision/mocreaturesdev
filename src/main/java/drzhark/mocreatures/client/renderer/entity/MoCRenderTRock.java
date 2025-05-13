/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderTRock extends EntityRenderer<MoCEntityThrowableRock> {

    public MoCRenderTRock(EntityRendererManager renderManagerIn) {
        super(renderManagerIn);
        this.shadowSize = 0.5F;
    }

    @Override
    public void render(MoCEntityThrowableRock entitytrock, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
        matrixStackIn.push();
        //matrixStackIn.translate(-0.5F, -0.55F, 0.5F);
        matrixStackIn.translate(-0.5F, 0F, 0.5F);
        matrixStackIn.rotate(Vector3f.YN.rotationDegrees(((100 - entitytrock.acceleration) / 10F) * 36F));
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        blockrendererdispatcher.renderBlock(entitytrock.getState(), matrixStackIn, bufferIn, packedLightIn, OverlayTexture.NO_OVERLAY);
        matrixStackIn.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityThrowableRock par1Entity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }
}
