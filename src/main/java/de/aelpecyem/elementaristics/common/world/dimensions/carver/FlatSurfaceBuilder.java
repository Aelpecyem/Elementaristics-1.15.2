package de.aelpecyem.elementaristics.common.world.dimensions.carver;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;
import java.util.function.Function;

public class FlatSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {

    public FlatSurfaceBuilder(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> config) {
        super(config);
    }

    @Override
    public void buildSurface(Random random, IChunk iChunk, Biome biome, int chunkX, int chunkZ, int maxHeight, double heightVar, BlockState defBlock, BlockState defFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        BlockPos.Mutable placerPos = new BlockPos.Mutable();
        int x = chunkX & 15;
        int z = chunkZ & 15;

        for(int y = maxHeight; y >= 0; --y) {
            placerPos.setPos(x, y, z);
            BlockState stateInPos = iChunk.getBlockState(placerPos);
            if (placerPos.getY() > 60){
                iChunk.setBlockState(placerPos, AIR, false);
            }else if (placerPos.getY() == 60){
                iChunk.setBlockState(placerPos, config.getTop(), false);
            }else if (stateInPos.isAir()){
                iChunk.setBlockState(placerPos, config.getUnder(), false);
            }
        }
    }
}
