package de.aelpecyem.elementaristics.common.blocks.mind;

import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockGreyMatter extends Block {
    public BlockGreyMatter() {
        super(Block.Properties.from(Blocks.GRAVEL).lightValue(8));
    }

    @Override
    public void harvestBlock(World world, PlayerEntity player, BlockPos pos, BlockState block, @Nullable TileEntity tile, ItemStack stack) {
        super.harvestBlock(world, player, pos, block, tile, stack);
        if (player.dimension == ModWorld.MIND) {
            player.attackEntityFrom(Constants.DamageSources.HEART_ATTACK, 2F);
        }
    }
}
