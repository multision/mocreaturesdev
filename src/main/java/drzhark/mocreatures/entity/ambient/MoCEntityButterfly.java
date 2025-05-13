/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityButterfly extends MoCEntityInsect {

    private int fCounter;

    public MoCEntityButterfly(EntityType<? extends MoCEntityButterfly> type, World world) {
        super(type, world);
    }

    @Override
    public void livingTick() {
        super.livingTick();
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(10) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("butterfly_agalais_urticae.png");
            case 2:
                return MoCreatures.proxy.getModelTexture("butterfly_argyreus_hyperbius.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("butterfly_athyma_nefte.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("butterfly_catopsilia_pomona.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("butterfly_morpho_peleides.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("butterfly_vanessa_atalanta.png");
            case 8:
                return MoCreatures.proxy.getModelTexture("moth_camptogramma_bilineata.png");
            case 9:
                return MoCreatures.proxy.getModelTexture("moth_idia_aemula.png");
            case 10:
                return MoCreatures.proxy.getModelTexture("moth_thyatira_batis.png");
            default:
                return MoCreatures.proxy.getModelTexture("butterfly_pieris_rapae.png");
        }
    }

    public float tFloat() {
        if (!getIsFlying()) {
            return 0F;
        }
        if (++this.fCounter > 1000) {
            this.fCounter = 0;
        }

        return MathHelper.cos((this.fCounter * 0.1F)) * 0.2F;
    }

    @Override
    public float getSizeFactor() {
        if (getTypeMoC() < 8) {
            return 0.7F;
        }
        return 1.0F;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem().isIn(ItemTags.FLOWERS);
    }

    @Override
    public boolean isAttractedToLight() {
        return getTypeMoC() > 7;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.13F;
        }
        return 0.10F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.BUTTERFLY;
    }

    @Override
    public float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.1F;
    }
}
