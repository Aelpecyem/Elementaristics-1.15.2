package de.aelpecyem.elementaristics.common.world.features;

import com.mojang.datafixers.Dynamic;
import de.aelpecyem.elementaristics.common.blocks.plant.BlockMorningGlory;
import de.aelpecyem.elementaristics.reg.ModBlocks;
import net.minecraft.block.LogBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.function.Function;

public class MorningGloryFeature<T extends NoFeatureConfig> extends Feature<T> {
    private static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public MorningGloryFeature(Function<Dynamic<?>, T> config) {
        super(config);
    }

    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGen, Random rand, BlockPos pos, T fc) {
        int blocksPlaced = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable(pos);
        boolean foundSoil = false;
        for (int i = 0; i < 5; i++){
            mutable.setPos(mutable.add(0, -i, 0));
            if (world.isAirBlock(mutable) && world.getBlockState(mutable.down()).canSustainPlant(world, pos, Direction.UP, (IPlantable) ModBlocks.morning_glory.get())){
                foundSoil = true;
                break;
            }
        }

        if (foundSoil) {
            int maxBlocks = 2 + world.getRandom().nextInt(6);
            for (BlockPos possiblePos : BlockPos.getAllInBoxMutable(mutable.add(-4, -1, -4), mutable.add(4, 2, 4))) {
                for (Direction direction : DIRECTIONS) {
                    if (world.getRandom().nextBoolean() && blocksPlaced < maxBlocks && world.isAirBlock(possiblePos) && world.getBlockState(possiblePos.down()).canSustainPlant(world, pos, Direction.UP, (IPlantable) ModBlocks.morning_glory.get()) && BlockMorningGlory.canAttachTo(world, possiblePos.offset(direction), direction.getOpposite()) && world.getBlockState(possiblePos.offset(direction)).getBlock() instanceof LogBlock) {
                        blocksPlaced++;
                        world.setBlockState(possiblePos, ModBlocks.morning_glory.get().getDefaultState().with(BlockMorningGlory.getPropertyFor(direction), true), 2);
                    }
                }
            }
        }
        return blocksPlaced > 0;
    }
}