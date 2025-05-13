/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelWolf;
import drzhark.mocreatures.entity.hostile.MoCEntityWWolf;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWWolf extends MobRenderer<MoCEntityWWolf, MoCModelWolf<MoCEntityWWolf>> {

    public MoCRenderWWolf(EntityRendererManager renderManagerIn, MoCModelWolf modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityWWolf par1Entity) {
        return par1Entity.getTexture();
    }
}
