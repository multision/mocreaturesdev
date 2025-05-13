/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelBird;
import drzhark.mocreatures.entity.passive.MoCEntityBird;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBird extends MoCRenderMoC<MoCEntityBird, MoCModelBird<MoCEntityBird>> {

    public MoCRenderBird(EntityRendererManager renderManagerIn, MoCModelBird modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity par1Entity) {
        return ((MoCEntityBird) par1Entity).getTexture();
    }

    @Override
    protected float handleRotationFloat(MoCEntityBird entitybird, float f) {
        float f1 = entitybird.winge + ((entitybird.wingb - entitybird.winge) * f);
        float f2 = entitybird.wingd + ((entitybird.wingc - entitybird.wingd) * f);
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    @Override
    protected void preRenderCallback(MoCEntityBird entitybird, MatrixStack matrixStackIn, float f) {
        if (!entitybird.world.isRemote && (entitybird.getRidingEntity() != null)) {
            matrixStackIn.translate(0.0F, 1.3F, 0.0F);
        }
    }
}
