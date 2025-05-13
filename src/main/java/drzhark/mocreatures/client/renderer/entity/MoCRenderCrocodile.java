/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelCrocodile;
import drzhark.mocreatures.entity.hunter.MoCEntityCrocodile;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.entity.MobEntity;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderCrocodile extends MobRenderer<MoCEntityCrocodile, MoCModelCrocodile<MoCEntityCrocodile>> {

    public MoCModelCrocodile croc;

    public MoCRenderCrocodile(EntityRendererManager renderManagerIn, MoCModelCrocodile modelbase, float f) {
        super(renderManagerIn, modelbase, f);
        this.croc = modelbase;
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityCrocodile entitycrocodile) {
        return entitycrocodile.getTexture();
    }

    @Override
    public void render(MoCEntityCrocodile entitycrocodile, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entitycrocodile, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(MoCEntityCrocodile entitycrocodile, MatrixStack matrixStackIn, float f) {
        this.croc.biteProgress = entitycrocodile.biteProgress;
        this.croc.swimming = entitycrocodile.isSwimming();
        this.croc.resting = entitycrocodile.getIsSitting();
        if (entitycrocodile.isSpinning()) {
            spinCroc(entitycrocodile, (MobEntity) entitycrocodile.getRidingEntity(), matrixStackIn);
        }
        stretch(entitycrocodile, matrixStackIn);
        if (entitycrocodile.getIsSitting()) {
            if (!entitycrocodile.areEyesInFluid(FluidTags.WATER)) {
                adjustHeight(entitycrocodile, 0.2F, matrixStackIn);
            } else {
                //adjustHeight(entitycrocodile, 0.1F);
            }

        }
        // if(!entitycrocodile.getIsAdult()) { }
    }

    protected void rotateAnimal(MoCEntityCrocodile entitycrocodile, MatrixStack matrixStackIn) {

        //float f = entitycrocodile.swingProgress *10F *entitycrocodile.getFlipDirection();
        //float f2 = entitycrocodile.swingProgress /30 *entitycrocodile.getFlipDirection();
        //matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(180F + f));
        //matrixStackIn.translate(0.0F-f2, 0.5F, 0.0F);
    }

    protected void adjustHeight(MoCEntityCrocodile entitycrocodile, float FHeight, MatrixStack matrixStackIn) {
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    protected void spinCroc(MoCEntityCrocodile entitycrocodile, MobEntity prey, MatrixStack matrixStackIn) {
        int intSpin = entitycrocodile.spinInt;
        int direction = 1;
        if (intSpin > 40) {
            intSpin -= 40;
            direction = -1;
        }
        int intEndSpin = intSpin;
        if (intSpin >= 20) {
            intEndSpin = (20 - (intSpin - 20));
        }
        if (intEndSpin == 0) {
            intEndSpin = 1;
        }
        float f3 = (((intEndSpin) - 1.0F) / 20F) * 1.6F;
        f3 = MathHelper.sqrt(f3);
        if (f3 > 1.0F) {
            f3 = 1.0F;
        }
        f3 *= direction;
        matrixStackIn.rotate(Vector3f.ZP.rotationDegrees(f3 * 90F));

        if (prey != null) {
            prey.deathTime = intEndSpin;
        }
    }

    protected void stretch(MoCEntityCrocodile entitycrocodile, MatrixStack matrixStackIn) {
        // float f = 1.3F;
        float f = entitycrocodile.getAge() * 0.01F;
        // if(!entitycrocodile.getIsAdult()) { f = entitycrocodile.age; }
        matrixStackIn.scale(f, f, f);
    }
}
