/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hostile.MoCEntityWerewolf;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelWerehuman<T extends MoCEntityWerewolf> extends BipedModel<T> {

    public MoCModelWerehuman() {
        //TODO 4.1 FIX
        super(0.0F);
    }
}
