/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityHellRat extends MoCEntityRat {

    private int textCounter;

    public MoCEntityHellRat(EntityType<? extends MoCEntityHellRat> type, World world) {
        super(type, world);
        //setSize(0.88F, 0.755F);
        //this.isImmuneToFire = true;
        experienceValue = 7;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityRat.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 40.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.325D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.5D).createMutableAttribute(Attributes.ARMOR, 7.0D);
    }

    @Override
    public void selectType() {
        setTypeMoC(4);
    }

    @Override
    public ResourceLocation getTexture() {
        if (this.rand.nextInt(2) == 0) {
            this.textCounter++;
        }
        if (this.textCounter < 10) {
            this.textCounter = 10;
        }
        if (this.textCounter > 29) {
            this.textCounter = 10;
        }
        String textNumber = String.valueOf(this.textCounter);
        textNumber = textNumber.substring(0, 1);
        return MoCreatures.proxy.getModelTexture("hell_rat" + textNumber + ".png");
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_HELL_RAT_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_HELL_RAT_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_HELL_RAT_AMBIENT.get();
    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable() {        return MoCLootTables.HELL_RAT;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = super.attackEntityAsMob(entityIn);

        if (flag && entityIn instanceof LivingEntity) {
            entityIn.setFire(5);
        }

        return flag;
    }

    @Override
    public void livingTick() {
        if (this.world.isRemote) {
            for (int i = 0; i < 2; ++i) {
                this.world.addParticle(ParticleTypes.FLAME, this.getPosX() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(), this.getPosY() + this.rand.nextDouble() * (double) this.getHeight(), this.getPosZ() + (this.rand.nextDouble() - 0.5D) * (double) this.getWidth(), 0.0D, 0.0D, 0.0D);
            }
        }
        super.livingTick();
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.485F;
    }
}
