package de.aelpecyem.elementaristics.common.world.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.*;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class DappledTreeFeature<T extends HugeTreeFeatureConfig> extends AbstractTreeFeature<T> {

    public DappledTreeFeature(Function<Dynamic<?>, T> config) {
        super(config);
    }
    //todo
    @Override
    public boolean generate(IWorldGenerationReader iWorldGenerationReader, Random random, BlockPos blockPos, Set<BlockPos> set, Set<BlockPos> set1, MutableBoundingBox mutableBoundingBox, T hugeTreeFeatureConfig) {
        int height = random.nextInt(5) + random.nextInt(4) + hugeTreeFeatureConfig.baseHeight;
        int j = blockPos.getX();
        int k = blockPos.getY();
        int l = blockPos.getZ();
        if (k >= 1 && k + height + 1 < iWorldGenerationReader.getMaxHeight()) {
            BlockPos blockpos = blockPos.down();
            if (!isSoil(iWorldGenerationReader, blockpos, hugeTreeFeatureConfig.getSapling())) {
                return false;
            } else if (!this.canPlaceTree(iWorldGenerationReader, blockPos, height)) {
                return false;
            } else {
                this.setDirtAt(iWorldGenerationReader, blockpos, blockPos);
                this.setDirtAt(iWorldGenerationReader, blockpos.east(), blockPos);
                this.setDirtAt(iWorldGenerationReader, blockpos.south(), blockPos);
                this.setDirtAt(iWorldGenerationReader, blockpos.south().east(), blockPos);
                Direction direction = Direction.Plane.HORIZONTAL.random(random);
                int i1 = height - random.nextInt(4);
                int j1 = 2 - random.nextInt(3);
                int k1 = j;
                int l1 = l;
                int i2 = k + height - 1;

                int l3;
                int k4;
                for(l3 = 0; l3 < height; ++l3) {
                    if (l3 >= i1 && j1 > 0) {
                        k1 += direction.getXOffset();
                        l1 += direction.getZOffset();
                        --j1;
                    }

                    k4 = k + l3;
                    BlockPos blockpos1 = new BlockPos(k1, k4, l1);
                    if (isAirOrLeaves(iWorldGenerationReader, blockpos1)) {
                        this.setLogBlockState(iWorldGenerationReader, random, blockpos1, set, mutableBoundingBox, hugeTreeFeatureConfig);
                        this.setLogBlockState(iWorldGenerationReader, random, blockpos1.east(), set, mutableBoundingBox, hugeTreeFeatureConfig);
                        this.setLogBlockState(iWorldGenerationReader, random, blockpos1.south(), set, mutableBoundingBox, hugeTreeFeatureConfig);
                        this.setLogBlockState(iWorldGenerationReader, random, blockpos1.east().south(), set, mutableBoundingBox, hugeTreeFeatureConfig);
                    }
                }

                for(l3 = -2; l3 <= 0; ++l3) {
                    for(k4 = -2; k4 <= 0; ++k4) {
                        int l4 = -1;
                        this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + l3, i2 + l4, l1 + k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                        this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(1 + k1 - l3, i2 + l4, l1 + k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                        this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + l3, i2 + l4, 1 + l1 - k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                        this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(1 + k1 - l3, i2 + l4, 1 + l1 - k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                        if ((l3 > -2 || k4 > -1) && (l3 != -1 || k4 != -2)) {
                            l4 = 1;
                            this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + l3, i2 + l4, l1 + k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                            this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(1 + k1 - l3, i2 + l4, l1 + k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                            this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + l3, i2 + l4, 1 + l1 - k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                            this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(1 + k1 - l3, i2 + l4, 1 + l1 - k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                        }
                    }
                }

                if (random.nextBoolean()) {
                    this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1, i2 + 2, l1), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                    this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + 1, i2 + 2, l1), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                    this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + 1, i2 + 2, l1 + 1), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                    this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1, i2 + 2, l1 + 1), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                }

                for(l3 = -3; l3 <= 4; ++l3) {
                    for(k4 = -3; k4 <= 4; ++k4) {
                        if ((l3 != -3 || k4 != -3) && (l3 != -3 || k4 != 4) && (l3 != 4 || k4 != -3) && (l3 != 4 || k4 != 4) && (Math.abs(l3) < 3 || Math.abs(k4) < 3)) {
                            this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + l3, i2, l1 + k4), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                        }
                    }
                }

                for(l3 = -1; l3 <= 2; ++l3) {
                    for(k4 = -1; k4 <= 2; ++k4) {
                        if ((l3 < 0 || l3 > 1 || k4 < 0 || k4 > 1) && random.nextInt(3) <= 0) {
                            int i5 = random.nextInt(3) + 2;

                            int k5;
                            for(k5 = 0; k5 < i5; ++k5) {
                                this.setLogBlockState(iWorldGenerationReader, random, new BlockPos(j + l3, i2 - k5 - 1, l + k4), set, mutableBoundingBox, hugeTreeFeatureConfig);
                            }

                            int l5;
                            for(k5 = -1; k5 <= 1; ++k5) {
                                for(l5 = -1; l5 <= 1; ++l5) {
                                    this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + l3 + k5, i2, l1 + k4 + l5), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                                }
                            }

                            for(k5 = -2; k5 <= 2; ++k5) {
                                for(l5 = -2; l5 <= 2; ++l5) {
                                    if (Math.abs(k5) != 2 || Math.abs(l5) != 2) {
                                        this.setLeavesBlockState(iWorldGenerationReader, random, new BlockPos(k1 + l3 + k5, i2 - 1, l1 + k4 + l5), set1, mutableBoundingBox, hugeTreeFeatureConfig);
                                    }
                                }
                            }
                        }
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    private boolean canPlaceTree(IWorldGenerationBaseReader p_214615_1_, BlockPos p_214615_2_, int p_214615_3_) {
        int i = p_214615_2_.getX();
        int j = p_214615_2_.getY();
        int k = p_214615_2_.getZ();
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

        for(int l = 0; l <= p_214615_3_ + 1; ++l) {
            int i1 = 1;
            if (l == 0) {
                i1 = 0;
            }

            if (l >= p_214615_3_ - 1) {
                i1 = 2;
            }

            for(int j1 = -i1; j1 <= i1; ++j1) {
                for(int k1 = -i1; k1 <= i1; ++k1) {
                    if (!func_214587_a(p_214615_1_, blockpos$mutable.setPos(i + j1, j + l, k + k1))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}