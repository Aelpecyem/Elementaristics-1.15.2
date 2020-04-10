package de.aelpecyem.elementaristics.common.world.features;

import com.mojang.datafixers.Dynamic;
import de.aelpecyem.elementaristics.common.blocks.plant.BlockMorningGlory;
import de.aelpecyem.elementaristics.reg.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateProvidingFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.function.Function;

public class WeirdShapesFeature<T extends BlockStateFeatureConfig> extends Feature<T> {

    public WeirdShapesFeature(Function<Dynamic<?>, T> config) {
        super(config);
    }

    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkGen, Random rand, BlockPos pos, T fc) {
        if (rand.nextInt(10) == 0){
            return placePyramid(world, pos, rand, fc);
        }else if (rand.nextBoolean()){
            return placeColumn(world, pos, rand, fc);
        }
        return placeCube(world, pos, rand, fc);
    }

    public boolean placeCube(IWorld world, BlockPos pos, Random rand, T fc){
        int width = 2 + rand.nextInt(8);
        BlockState blockState;
        for (int x = width; x >= 0; x--) {
            for (int y = width; y >= 0; y--) {
                for (int z = width; z >= 0; z--) {
                    if (x > 0 && x < width && z > 0 && z < width && y > 0 && y < width) {
                        if (x > 1 && x < width - 1 && z > 1 && z < width - 1 && y > 1 && y < width - 1)
                            blockState = ModBlocks.grey_matter.get().getDefaultState();
                        else
                            blockState = ModBlocks.soil_hardened.get().getDefaultState();
                    } else {
                        blockState = fc.state;
                    }
                    world.setBlockState(pos.add(width / 2F - x, y, width / 2F - z), blockState, 2);
                }
            }
        }
        return true;
    }

    public boolean placeColumn(IWorld world, BlockPos pos, Random rand, T fc){
        int width = 2 + rand.nextInt(3);
        int height = width * 5;
        BlockState blockState;
        for (int x = width; x >= 0; x--) {
            for (int y = height; y >= 0; y--) {
                for (int z = width; z >= 0; z--) {
                    if (x > 0 && x < width && z > 0 && z < width && y > 0 && y < height) {
                        if (x > 1 && x < width - 1 && z > 1 && z < width - 1 && y > 1 && y < height - 1)
                            blockState = ModBlocks.grey_matter.get().getDefaultState();
                        else
                            blockState = ModBlocks.soil_hardened.get().getDefaultState();
                    } else {
                        blockState = fc.state;
                    }
                    world.setBlockState(pos.add(width / 2F - x, y, width / 2F - z), blockState, 2);
                }
            }
        }
        return true;
    }

    public boolean placePyramid(IWorld world, BlockPos pos, Random rand, T fc){
        int size = rand.nextInt(6) * 2 + 3;
        BlockPos.Mutable placerPos = new BlockPos.Mutable(pos);
        BlockState blockState;

        while (size > 0) {
            for (int x = 0; x < size; x++) {
                for (int z = 0; z < size; z++) {
                   if(x > 0 && x < size -1 && z > 0 && z < size - 1){
                       blockState = ModBlocks.soil_hardened.get().getDefaultState();
                   }else{
                       blockState = fc.state;
                   }
                    world.setBlockState(placerPos.add(x, 0, z), blockState, 2);
                }
            }
            placerPos.setPos(placerPos.add(1, 1, 1));
            size -= 2;
        }
        return true;
    }
}