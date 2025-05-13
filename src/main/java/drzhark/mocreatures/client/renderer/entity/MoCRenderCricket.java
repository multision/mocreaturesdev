/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelCricket;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderCricket extends MoCRenderMoC<MoCEntityCricket, MoCModelCricket<MoCEntityCricket>> {

    public MoCRenderCricket(EntityRendererManager renderManagerIn, MoCModelCricket modelbase) {
        super(renderManagerIn, modelbase, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityCricket entitycricket, MatrixStack matrixStackIn, float par2) {
        rotateCricket(entitycricket, matrixStackIn);
    }

    protected void rotateCricket(MoCEntityCricket entitycricket, MatrixStack matrixStackIn) {
        if (!entitycricket.isOnGround()) {
            if (entitycricket.getMotion().getY() > 0.5D) {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees(35F));
            } else if (entitycricket.getMotion().getY() < -0.5D) {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees(-35F));
            } else {
                matrixStackIn.rotate(Vector3f.XN.rotationDegrees((float) (entitycricket.getMotion().getY() * 70D)));
            }
        }
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityCricket par1Entity) {
        return par1Entity.getTexture();
    }
}
