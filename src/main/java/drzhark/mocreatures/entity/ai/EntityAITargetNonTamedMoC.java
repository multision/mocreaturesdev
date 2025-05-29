package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class EntityAITargetNonTamedMoC<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    private final CreatureEntity tameable;

    public EntityAITargetNonTamedMoC(CreatureEntity creature, Class<T> classTarget, boolean checkSight) {
        super(creature, classTarget, checkSight);
        this.tameable = creature;
    }

    public boolean shouldExecute() {
        return this.tameable instanceof IMoCTameable && !((IMoCTameable) this.tameable).getIsTamed() && super.shouldExecute();
    }
}