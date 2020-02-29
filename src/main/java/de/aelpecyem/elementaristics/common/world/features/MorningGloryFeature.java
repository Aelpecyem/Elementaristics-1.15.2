package de.aelpecyem.elementaristics.common.world.features;

import com.mojang.datafixers.Dynamic;
import de.aelpecyem.elementaristics.common.blocks.plant.BlockMorningGlory;
import de.aelpecyem.elementaristics.reg.ModBlocks;
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

public class MorningGloryFeature extends Feature<NoFeatureConfig> {
    private static final Direction[] DIRECTIONS = {Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};

    public MorningGloryFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> config) {
        super(config);
    }

    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGen, Random rand, BlockPos pos, NoFeatureConfig fc) {
        int blocksPlaced = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable(pos);
        boolean foundSoil = false;
        for (int i = 0; i < 5; i++){
            mutable.setPos(mutable.add(0, -i, 0));
            if (world.isAirBlock(mutable) && world.getBlockState(mutable.down()).canSustainPlant(world, pos, Direction.UP, (IPlantable) ModBlocks.morning_glory)){
                foundSoil = true;
                break;
            }
        }

        if (foundSoil) {
            int maxBlocks = 2 + world.getRandom().nextInt(6);
            for (BlockPos possiblePos : BlockPos.getAllInBoxMutable(mutable.add(-3, -1, -3), mutable.add(3, 2, 3))) {
                for (Direction direction : DIRECTIONS) {
                    if (world.getRandom().nextBoolean() && blocksPlaced < maxBlocks && world.isAirBlock(possiblePos) && world.getBlockState(possiblePos.down()).canSustainPlant(world, pos, Direction.UP, (IPlantable) ModBlocks.morning_glory) && BlockMorningGlory.canAttachTo(world, possiblePos.offset(direction), direction.getOpposite())) {
                        blocksPlaced++;
                        world.setBlockState(possiblePos, ModBlocks.morning_glory.getDefaultState().with(BlockMorningGlory.getPropertyFor(direction), true), 2);
                    }
                }
            }
        }
        return blocksPlaced > 0;
    }
}