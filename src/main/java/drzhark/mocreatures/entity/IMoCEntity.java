/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.Random;
import java.util.UUID;

public interface IMoCEntity {

    void selectType();

    String getPetName();

    void setPetName(String name);

    int getOwnerPetId();

    void setOwnerPetId(int petId);

    UUID getOwnerId();

    boolean getIsTamed();

    boolean getIsAdult();

    boolean getIsGhost();

    void setAdult(boolean flag);

    boolean checkSpawningBiome();

    void performAnimation(int i);

    boolean renderName();

    int nameYOffset();

    void makeEntityJump();

    void makeEntityDive();

    float getSizeFactor();

    float getAdjustedYOffset();

    void setArmorType(int i);

    int getTypeMoC();

    void setTypeMoC(int i);

    float rollRotationOffset();

    float pitchRotationOffset();

    int getAge();

    void setAge(int i);

    float yawRotationOffset();

    float getAdjustedZOffset();

    float getAdjustedXOffset();

    ResourceLocation getTexture();

    boolean canAttackTarget(LivingEntity entity);

    boolean getIsSitting(); // is the entity sitting, for animations and AI

    boolean isNotScared(); //relentless creature that attacks others used for AI

    boolean isMovementCeased(); //to deactivate path / wander behavior AI

    boolean shouldAttackPlayers();

    double getDivingDepth();

    boolean isDiving();

    void forceEntityJump();

    int maxFlyingHeight();

    int minFlyingHeight();

    boolean isFlyer();

    boolean getIsFlying();
}
