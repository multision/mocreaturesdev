/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelSnake;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderSnake extends MoCRenderMoC<MoCEntitySnake, MoCModelSnake<MoCEntitySnake>> {

    public MoCRenderSnake(EntityRendererManager renderManagerIn, MoCModelSnake modelbase, float f) {
        super(renderManagerIn, modelbase, 0.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntitySnake par1Entity) {
        return par1Entity.getTexture();
    }

    protected void adjustHeight(MoCEntitySnake entitysnake, float FHeight, MatrixStack matrixStackIn) {
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntitySnake entitysnake, MatrixStack matrixStackIn, float f) {
        stretch(entitysnake, matrixStackIn);

        /*
         * if(mod_mocreatures.mc.isMultiplayerWorld() &&
         * (entitysnake.pickedUp())) { matrixStackIn.translate(0.0F, 1.4F, 0.0F); }
         */

        if (entitysnake.pickedUp())// && entitysnake.getSizeF() < 0.6F)
        {
            float xOff = (entitysnake.getSizeF() - 1.0F);
            if (xOff > 0.0F) {
                xOff = 0.0F;
            }
            if (entitysnake.world.isRemote) {
                matrixStackIn.translate(xOff, 0.0F, 0F);
            } else {
                matrixStackIn.translate(xOff, 0F, 0.0F);
                //-0.5 puts it in the right shoulder
            }
            /*
             * //if(small) //works for small snakes matrixStackIn.rotate(Vector3f.XP.rotationDegrees(20F));
             * if(mod_mocreatures.mc.isMultiplayerWorld()) {
             * matrixStackIn.translate(-0.5F, 1.4F, 0F); } else {
             * matrixStackIn.translate(0.7F, 0F, 1.2F); }
             */
        }

        if (entitysnake.areEyesInFluid(FluidTags.WATER)) {
            adjustHeight(entitysnake, -0.25F, matrixStackIn);
        }

        super.preRenderCallback(entitysnake, matrixStackIn, f);
    }

    protected void stretch(MoCEntitySnake entitysnake, MatrixStack matrixStackIn) {
        float f = entitysnake.getSizeF();
        matrixStackIn.scale(f, f, f);
    }

    /*
     * @Override protected void preRenderCallback(MobEntity entityliving, MatrixStack matrixStackIn,
     * float f) { MoCEntitySnake entitysnake = (MoCEntitySnake) entityliving;
     * //tempSnake.textPos = entitysnake.type - 1; if (entitysnake.type <4) {
     * tempSnake.textPos = 0; }else { tempSnake.textPos = 1; }
     * super.preRenderCallback(entityliving, MatrixStack matrixStackIn, f); } private MoCModelSnake
     * tempSnake;
     */
}
