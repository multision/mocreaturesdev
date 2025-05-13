/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model.legacy;

import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import net.minecraft.client.renderer.entity.model.QuadrupedModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCLegacyModelBigCat1<T extends MoCEntityBigCat> extends QuadrupedModel<T> {

    public MoCLegacyModelBigCat1() {
        super(12, 0.0F, false, 8.0F, 4.0F, 2.0F, 2.0F, 24);
        this.headModel = new ModelRenderer(this, 20, 0);
        this.headModel.addBox(-7F, -8F, -2F, 14, 14, 8, 0.0F);
        this.headModel.setRotationPoint(0.0F, 4F, -8F);
        this.body = new ModelRenderer(this, 20, 0);
        this.body.addBox(-6F, -11F, -8F, 12, 10, 10, 0.0F);
        this.body.setRotationPoint(0.0F, 5F, 2.0F);
    }
}
