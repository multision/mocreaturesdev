/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SaplingBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

public class MoCEntityEnt extends MoCEntityAnimal {

    private static  final Block[] tallgrass = new Block[]{Blocks.GRASS, Blocks.FERN};
    private static  final Block[] double_plant = new Block[]{Blocks.SUNFLOWER, Blocks.LILAC, Blocks.ROSE_BUSH, Blocks.PEONY, Blocks.TALL_GRASS, Blocks.LARGE_FERN};
    private static  final Block[] red_flower = new Block[]{Blocks.POPPY, Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.RED_TULIP, Blocks.ORANGE_TULIP, Blocks.WHITE_TULIP, Blocks.PINK_TULIP, Blocks.OXEYE_DAISY};
    public MoCEntityEnt(EntityType<? extends MoCEntityEnt> type, World world) {
        super(type, world);
        //setSize(1.4F, 7F);
        this.stepHeight = 2F;
        experienceValue = 10;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityAnimal.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 60.0D).createMutableAttribute(Attributes.ARMOR, 7.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 7.5D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D).createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(2) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getTypeMoC() == 2) {
            return MoCreatures.proxy.getModelTexture("ent_birch.png");
        }
        return MoCreatures.proxy.getModelTexture("ent_oak.png");
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return experienceValue;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (damagesource.getTrueSource() != null && damagesource.getTrueSource() instanceof PlayerEntity) {
            PlayerEntity ep = (PlayerEntity) damagesource.getTrueSource();
            ItemStack currentItem = ep.inventory.getCurrentItem();
            Item itemheld = currentItem.getItem();
            if (itemheld instanceof AxeItem) {
                this.world.getDifficulty();
                if (super.shouldAttackPlayers()) {
                    setAttackTarget(ep);

                }
                return super.attackEntityFrom(damagesource, i);
            }
        }
        if (damagesource.isFireDamage()) {
            return super.attackEntityFrom(damagesource, i);
        }
        return false;
    }

    @Override
    protected void dropSpecialItems(DamageSource source, int looting, boolean recentlyHitIn) {
        int i = this.rand.nextInt(3);
        int qty = this.rand.nextInt(12) + 4;
        if (getTypeMoC() == 2) {
            if (i == 0) {
                entityDropItem(new ItemStack(Blocks.BIRCH_LOG, qty), 0.0F);
                return;
            }
            if (i == 1) {
                entityDropItem(new ItemStack(Items.STICK, qty), 0.0F);
                return;

            }
            entityDropItem(new ItemStack(Blocks.BIRCH_SAPLING, qty), 0.0F);
        } else {
            if (i == 0) {
                entityDropItem(new ItemStack(Blocks.OAK_LOG, qty), 0.0F);
                return;
            }
            if (i == 1) {
                entityDropItem(new ItemStack(Items.STICK, qty), 0.0F);
                return;

            }
            entityDropItem(new ItemStack(Blocks.OAK_SAPLING, qty), 0.0F);
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_ENT_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_ENT_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_ENT_AMBIENT.get();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {

            if (this.getAttackTarget() == null && this.rand.nextInt(500) == 0) {
                plantOnFertileGround();
            }

            if (this.rand.nextInt(100) == 0) {
                atractCritter();
            }
        }
    }

    /**
     * Makes small creatures follow the Ent
     */
    private void atractCritter() {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(8D, 3D, 8D));
        int n = this.rand.nextInt(3) + 1;
        int j = 0;
        for (Entity entity : list) {
            if (entity instanceof AnimalEntity && entity.getWidth() < 0.6F && entity.getHeight() < 0.6F) {
                AnimalEntity entityanimal = (AnimalEntity) entity;
                if (entityanimal.getAttackTarget() == null && !MoCTools.isTamed(entityanimal)) {
                    Path pathentity = entityanimal.getNavigator().pathfind(this, 0);
                    entityanimal.setAttackTarget(this);
                    entityanimal.getNavigator().setPath(pathentity, 1D);
                    j++;
                    if (j > n) {
                        return;
                    }
                }

            }
        }
    }

    private void plantOnFertileGround() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()), MathHelper.floor(this.getPosZ()));
        Block blockUnderFeet = this.world.getBlockState(pos.down()).getBlock();
        Block blockOnFeet = this.world.getBlockState(pos).getBlock();

        if (Blocks.DIRT.matchesBlock(blockUnderFeet)) {
            Block block = Blocks.GRASS;
            BlockEvent.BreakEvent event = null;
            if (!this.world.isRemote) {
                event =
                        new BlockEvent.BreakEvent(this.world, pos, block.getDefaultState(), FakePlayerFactory.get((ServerWorld) this.world,
                                MoCreatures.MOCFAKEPLAYER));
            }
            if (event != null && !event.isCanceled()) {
                this.world.setBlockState(pos.down(), block.getDefaultState(), 3);
                return;
            }
            return;
        }

        if (Blocks.GRASS_BLOCK.matchesBlock(blockUnderFeet) && blockOnFeet == Blocks.AIR) {
            BlockState iblockstate = getBlockStateToBePlanted();
            int plantChance = 3;
            if (iblockstate.getBlock() instanceof SaplingBlock) {
                plantChance = 10;
            }
            //boolean cantPlant = false;
            // check perms first
            for (int x = -1; x < 2; x++) {
                for (int z = -1; z < 2; z++) {
                    BlockPos pos1 = new BlockPos(MathHelper.floor(this.getPosX() + x), MathHelper.floor(this.getPosY()), MathHelper.floor(this.getPosZ() + z));
                    //BlockEvent.BreakEvent event = null;
                    //if (!this.world.isRemote) {
                    //    event =
                    //            new BlockEvent.BreakEvent(this.world, pos1, iblockstate, FakePlayerFactory.get((ServerWorld) this.world,
                    //                    MoCreatures.MOCFAKEPLAYER));
                    //}
                    //cantPlant = (event != null && event.isCanceled());
                    Block blockToPlant = this.world.getBlockState(pos1).getBlock();
                    //if (!cantPlant && this.rand.nextInt(plantChance) == 0 && blockToPlant == Blocks.AIR) {
                    if (this.rand.nextInt(plantChance) == 0 && blockToPlant == Blocks.AIR) {
                        this.world.setBlockState(pos1, iblockstate, 3);
                    }
                }
            }
        }

    }

    /**
     * Returns a random blockState
     *
     * @return Any of the flowers, mushrooms, grass and saplings
     */
    private BlockState getBlockStateToBePlanted() {
        Block blockID;
        switch (this.rand.nextInt(20)) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                blockID = tallgrass[this.rand.nextInt(tallgrass.length)];
                break;
            case 8:
            case 9:
            case 10:
                blockID = double_plant[this.rand.nextInt(double_plant.length)];
                break;
            case 11:
            case 12:
            case 13:
                blockID = Blocks.DANDELION;
                break;
            case 14:
            case 15:
            case 16:
                blockID = red_flower[this.rand.nextInt(red_flower.length)];
                break;
            case 17:
                blockID = Blocks.BROWN_MUSHROOM;
                break;
            case 18:
                blockID = Blocks.RED_MUSHROOM;
                break;
            case 19:
                blockID = Blocks.OAK_SAPLING;
                if (getTypeMoC() == 2) {
                    blockID = Blocks.BIRCH_SAPLING;
                }
                break;

            default:
                blockID = Blocks.DEAD_BUSH;
        }
        return blockID.getDefaultState();

    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (this.attackTime <= 0 && (f < 2.5D) && (entity.getBoundingBox().maxY > getBoundingBox().minY)
                && (entity.getBoundingBox().minY < getBoundingBox().maxY)) {
            attackTime = 200;
            this.world.playSoundAtEntity(this, "mocreatures:goatsmack", 1.0F, 1.0F + ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F));
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 3);
            MoCTools.bigSmack(this, entity, 2F);
        }
    }*/

    @Override
    public void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOAT_SMACK.get());
        MoCTools.bigSmack(this, entityIn, 1F);
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getHeight() * 0.73F;
    }
}
