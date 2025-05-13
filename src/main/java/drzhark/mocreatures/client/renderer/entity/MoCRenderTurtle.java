/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelTurtle;
import drzhark.mocreatures.entity.passive.MoCEntityTurtle;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderTurtle extends MoCRenderMoC<MoCEntityTurtle, MoCModelTurtle<MoCEntityTurtle>> {

    public MoCModelTurtle turtly;

    public MoCRenderTurtle(EntityRendererManager renderManagerIn, MoCModelTurtle modelbase, float f) {
        super(renderManagerIn, modelbase, f);
        this.turtly = modelbase;
    }

    @Override
    protected void preRenderCallback(MoCEntityTurtle entityturtle, MatrixStack matrixStackIn, float f) {
        this.turtly.upsidedown = entityturtle.getIsUpsideDown();
        this.turtly.swingProgress = entityturtle.swingProgress;
        this.turtly.isHiding = entityturtle.getIsHiding();

        if (!entityturtle.world.isRemote && (entityturtle.getRidingEntity() != null)) {

            matrixStackIn.translate(0.0F, 1.3F, 0.0F);

        }
        if (entityturtle.getIsHiding()) {
            adjustHeight(entityturtle, 0.15F * entityturtle.getAge() * 0.01F, matrixStackIn);
        } else if (!entityturtle.getIsHiding() && !entityturtle.getIsUpsideDown() && !entityturtle.areEyesInFluid(FluidTags.WATER)) {
            adjustHeight(entityturtle, 0.05F * entityturtle.getAge() * 0.01F, matrixStackIn);
        }
        if (entityturtle.getIsUpsideDown()) {
            rotateAnimal(entityturtle, matrixStackIn);
        }

        stretch(entityturtle, matrixStackIn);

    }

    protected void rotateAnimal(MoCEntityTurtle entityturtle, MatrixStack matrixStackIn) {
        //matrixStackIn.rotate(Vector3f.XN.rotationDegrees(180F)); //head up 180
        //matrixStackIn.rotate(Vector3f.YN.rotationDegrees(180F)); //head around 180

        float f = entityturtle.swingProgress * 10F * entityturtle.getFlipDirection();
        float f2 = entityturtle.swingProgress / 30 * entityturtle.getFlipDirection();
        matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(180F + f));
        matrixStackIn.translate(0.0F - f2, 0.5F * entityturtle.getAge() * 0.01F, 0.0F);
    }

    protected void adjustHeight(MoCEntityTurtle entityturtle, float height, MatrixStack matrixStackIn) {
        matrixStackIn.translate(0.0F, height, 0.0F);
    }

    protected void stretch(MoCEntityTurtle entityturtle, MatrixStack matrixStackIn) {
        float f = entityturtle.getAge() * 0.01F;
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityTurtle entityturtle) {
        return entityturtle.getTexture();
    }
}
