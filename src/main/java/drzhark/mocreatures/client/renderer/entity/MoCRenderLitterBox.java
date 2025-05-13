/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderLitterBox extends MobRenderer<MoCEntityLitterBox, MoCModelLitterBox<MoCEntityLitterBox>> {

    public MoCModelLitterBox litterbox;

    public MoCRenderLitterBox(EntityRendererManager renderManagerIn, MoCModelLitterBox modellitterbox, float f) {
        super(renderManagerIn, modellitterbox, f);
        this.litterbox = modellitterbox;
    }

    @Override
    protected void preRenderCallback(MoCEntityLitterBox entitylitterbox, MatrixStack matrixStackIn, float f) {
        this.litterbox.usedlitter = entitylitterbox.getUsedLitter();
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityLitterBox entitylitterbox) {
        return entitylitterbox.getTexture();
    }
}
