///*
// * GNU GENERAL PUBLIC LICENSE Version 3
// */
//package drzhark.mocreatures.dimension;
//
//import drzhark.mocreatures.dimension.worldgen.MoCWorldGenPortal;
//import net.minecraft.block.Blocks;
//import net.minecraft.entity.Entity;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.util.math.MathHelper;
//import net.minecraft.world.Teleporter;
//import net.minecraft.world.World;
//import net.minecraft.world.server.ServerWorld;
//
//import java.util.Random;
//
//public class MoCDirectTeleporter extends Teleporter {
//
//    private boolean portalDone;
//
//    public MoCDirectTeleporter(ServerWorld par1WorldServer) {
//        super(par1WorldServer);
//    }
//
//    @Override
//    public void placeInPortal(Entity par1Entity, float rotationYaw) {
//        int var9 = MathHelper.floor(par1Entity.getPosX());
//        int var10 = MathHelper.floor(par1Entity.getPosY()) - 1;
//        int var11 = MathHelper.floor(par1Entity.getPosZ());
//        par1Entity.setLocationAndAngles(var9, var10, var11, par1Entity.rotationYaw, 0.0F);
//        par1Entity.getMotion().getX() = par1Entity.getMotion().getY() = par1Entity.getMotion().getZ() = 0.0D;
//    }
//
//    public void createPortal(World par1World, Random par2Random) {
//        MoCWorldGenPortal myPortal = new MoCWorldGenPortal(Blocks.QUARTZ_BLOCK, 2, Blocks.QUARTZ_STAIRS, 0, Blocks.QUARTZ_BLOCK, 1, Blocks.QUARTZ_BLOCK, 0);
//        for (int i = 0; i < 14; i++) {
//            if (!this.portalDone) {
//                int randPosY = 58 + i;
//                this.portalDone = myPortal.generate(par1World, par2Random, new BlockPos(0, randPosY, 0));
//            }
//        }
//    }
//}
