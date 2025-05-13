/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelGrasshopper;
import drzhark.mocreatures.entity.ambient.MoCEntityGrasshopper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGrasshopper extends MoCRenderMoC<MoCEntityGrasshopper, MoCModelGrasshopper<MoCEntityGrasshopper>> {

    public MoCRenderGrasshopper(EntityRendererManager renderManagerIn, MoCModelGrasshopper modelbase) {
        super(renderManagerIn, modelbase, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityGrasshopper entity, MatrixStack matrixStackIn, float par2) {
        rotateGrasshopper(entity, matrixStackIn);
    }

    protected void rotateGrasshopper(MoCEntityGrasshopper entity, MatrixStack matrixStackIn) {
        if (!entity.isOnGround()) {
            if (entity.getMotion().getY() > 0.5D) {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees(35F));
            } else if (entity.getMotion().getY() < -0.5D) {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees(-35F));
            } else {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees((float) (entity.getMotion().getY() * 70D)));
            }
        }
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityGrasshopper par1Entity) {
        return par1Entity.getTexture();
    }
}
