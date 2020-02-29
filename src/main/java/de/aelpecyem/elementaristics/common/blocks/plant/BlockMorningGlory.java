package de.aelpecyem.elementaristics.common.blocks.plant;

import de.aelpecyem.elementaristics.reg.ModBlocks;
import de.aelpecyem.elementaristics.reg.ModPotions;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
//these are basically reversed vines
public class BlockMorningGlory extends FlowerBlock{
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = (Map)SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((entry) -> entry.getKey() != Direction.UP && entry.getKey() != Direction.DOWN).collect(Util.toMapCollector());
    protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(0, 0, 0, 1, 16, 16);
    protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(15, 0, 0, 16, 16, 16);
    protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0, 0, 0, 16, 16, 1);
    protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0, 0, 15, 16, 16, 16);


    public BlockMorningGlory() {
        super(Effects.BLINDNESS, 120, Properties.from(Blocks.VINE));
        this.setDefaultState(this.stateContainer.getBaseState()//.with(DOWN, false)
                .with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(CAN_GROW, true));
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockstate = this.getStateAttachedSites(state, world, pos);
        if (blockstate != state) {
            if (this.isPosValid(blockstate, world, pos)) {
                world.setBlockState(pos, blockstate, 2);
            } else {
                spawnDrops(state, world, pos);
                world.removeBlock(pos, false);
            }
        } else if (state.get(CAN_GROW) && world.rand.nextInt(4) == 0 && world.isAreaLoaded(pos, 4)) {
            Direction direction = Direction.random(random);

            BlockPos blockpos = pos.up();
            BlockPos blockpos4;
            BlockState blockstate1;
            Direction direction2;
            if (direction.getAxis().isHorizontal() && !state.get(getPropertyFor(direction))) {
                if (this.func_196539_a(world, pos)) {
                    blockpos4 = pos.offset(direction);
                    blockstate1 = world.getBlockState(blockpos4);
                    if (blockstate1.isAir(world, blockpos4)) {
                        direction2 = direction.rotateY();
                        Direction direction4 = direction.rotateYCCW();
                        boolean flag = state.get(getPropertyFor(direction2));
                        boolean flag1 = state.get(getPropertyFor(direction4));
                        BlockPos blockpos2 = blockpos4.offset(direction2);
                        BlockPos blockpos3 = blockpos4.offset(direction4);
                        if (flag && canAttachTo(world, blockpos2, direction2)) {
                            world.setBlockState(blockpos4, this.getDefaultState().with(getPropertyFor(direction2), true).with(CAN_GROW, world.rand.nextBoolean()), 2);
                        } else if (flag1 && canAttachTo(world, blockpos3, direction4)) {
                            world.setBlockState(blockpos4, this.getDefaultState().with(getPropertyFor(direction4), true), 2);
                        } else {
                            Direction direction1 = direction.getOpposite();
                            if (flag && world.isAirBlock(blockpos2) && canAttachTo(world, pos.offset(direction2), direction1)) {
                                world.setBlockState(blockpos2, this.getDefaultState().with(getPropertyFor(direction1), true).with(CAN_GROW, world.rand.nextBoolean()), 2);
                            } else if (flag1 && world.isAirBlock(blockpos3) && canAttachTo(world, pos.offset(direction4), direction1)) {
                                world.setBlockState(blockpos3, this.getDefaultState().with(getPropertyFor(direction1), true).with(CAN_GROW, world.rand.nextBoolean()), 2);
                            }
                        }
                    } else if (canAttachTo(world, blockpos4, direction)) {
                        world.setBlockState(pos, state.with(getPropertyFor(direction), true).with(CAN_GROW, world.rand.nextBoolean()), 2);
                    }
                }
            } else {
                if (direction == Direction.UP && pos.getY() < 255) {
                    if (world.isAirBlock(blockpos)) {
                        if (!this.func_196539_a(world, pos)) {
                            return;
                        }

                        BlockState blockstate4 = state.with(CAN_GROW, world.rand.nextBoolean());
                        Iterator var18 = Direction.Plane.HORIZONTAL.iterator();

                        while(true) {
                            do {
                                if (!var18.hasNext()) {
                                    if (this.hasWallAttachedto(blockstate4)) {
                                        world.setBlockState(blockpos, blockstate4, 2);
                                    }
                                    return;
                                }

                                direction2 = (Direction)var18.next();
                            } while(!random.nextBoolean() && canAttachTo(world, blockpos.offset(direction2), Direction.UP));

                            blockstate4 = blockstate4.with(getPropertyFor(direction2), false);
                        }
                    }
                }

                if (pos.getY() < 255) {
                    blockpos4 = pos.up();
                    blockstate1 = world.getBlockState(blockpos4);
                    if (blockstate1.isAir(world, blockpos4) || blockstate1.getBlock() == this) {
                        BlockState blockstate2 = blockstate1.isAir(world, blockpos4) ? this.getDefaultState() : blockstate1;
                        BlockState blockstate3 = this.func_196544_a(state, blockstate2, random);
                        if (blockstate2 != blockstate3 && this.hasWallAttachedto(blockstate3)) {
                            world.setBlockState(blockpos4, blockstate3.with(CAN_GROW, world.rand.nextBoolean()), 2);
                        }
                    }
                }
            }
        }

    }

    @Override
    public Effect getStewEffect() {
        return ModPotions.intoxicated;
    }

    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return this.isPosValid(this.getStateAttachedSites(state, world, pos), world, pos);
    }

    private boolean isPosValid(BlockState state, IWorldReader world, BlockPos pos) {
        return this.getPlacementsActive(state) > 0 && isGroundValid(state, world, pos);
    }

    public static boolean isGroundValid(BlockState state, IWorldReader world, BlockPos pos){
        return world.getBlockState(pos.down()).getBlock().canSustainPlant(state, world, pos, Direction.UP, (IPlantable) ModBlocks.morning_glory);
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return state.getBlock() instanceof BlockMorningGlory;
    }

    private int getPlacementsActive(BlockState state) {
        int i = 0;
        Iterator var3 = FACING_TO_PROPERTY_MAP.values().iterator();

        while(var3.hasNext()) {
            BooleanProperty booleanproperty = (BooleanProperty)var3.next();
            if (state.get(booleanproperty)) {
                ++i;
            }
        }

        return i;
    }

    private boolean canAttachPlant(IBlockReader world, BlockPos pos, Direction facing) {
        if (facing == Direction.DOWN) {
            return false;
        } else {
            BlockPos blockpos = pos.offset(facing);
            if (canAttachTo(world, blockpos, facing)) {
                return true;
            } else if (facing.getAxis() == Direction.Axis.Y) {
                return false;
            } else {
                BooleanProperty booleanproperty = FACING_TO_PROPERTY_MAP.get(facing);
                BlockState blockstate = world.getBlockState(pos.up());
                return blockstate.getBlock() == this && blockstate.get(booleanproperty);
            }
        }
    }

    public static boolean canAttachTo(IBlockReader world, BlockPos pos, Direction direction) {
        BlockState blockstate = world.getBlockState(pos);
        return Block.doesSideFillSquare(blockstate.getCollisionShape(world, pos), direction.getOpposite());
    }

    private BlockState getStateAttachedSites(BlockState state, IBlockReader world, BlockPos pos) {
        BlockPos blockpos = pos.up();

        BlockState blockstate = null;
        Iterator var6 = Direction.Plane.HORIZONTAL.iterator();

        while(true) {
            Direction direction;
            BooleanProperty booleanproperty;
            do {
                if (!var6.hasNext()) {
                    return state;
                }

                direction = (Direction)var6.next();
                booleanproperty = getPropertyFor(direction);
            } while(!state.get(booleanproperty));

            boolean flag = this.canAttachPlant(world, pos, direction);
            if (!flag) {
                if (blockstate == null) {
                    blockstate = world.getBlockState(blockpos);
                }

                flag = blockstate.getBlock() == this && blockstate.get(booleanproperty);
            }

            state = state.with(booleanproperty, flag);
        }
    }

    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState otherState, IWorld world, BlockPos pos, BlockPos otherPos) {
        if (direction == Direction.DOWN) { //TODO HERE might be UP instead
            return super.updatePostPlacement(state, direction, otherState, world, pos, otherPos);
        } else {
            BlockState blockstate = this.getStateAttachedSites(state, world, pos);
            return !this.isPosValid(blockstate, world, pos) ? Blocks.AIR.getDefaultState() : blockstate;
        }
    }

    private BlockState func_196544_a(BlockState state, BlockState otherState, Random random) {
        Iterator var4 = Direction.Plane.HORIZONTAL.iterator();

        while(var4.hasNext()) {
            Direction direction = (Direction)var4.next();
            if (random.nextBoolean()) {
                BooleanProperty booleanproperty = getPropertyFor(direction);
                if (state.get(booleanproperty)) {
                    otherState = otherState.with(booleanproperty, true);
                }
            }
        }

        return otherState;
    }

    private boolean hasWallAttachedto(BlockState state) {
        return state.get(NORTH) || state.get(EAST) || state.get(SOUTH) || state.get(WEST);
    }

    private boolean func_196539_a(IBlockReader world, BlockPos pos) {
        Iterable<BlockPos> iterable = BlockPos.getAllInBoxMutable(pos.getX() - 4, pos.getY() - 1, pos.getZ() - 4, pos.getX() + 4, pos.getY() + 1, pos.getZ() + 4);
        int j = 5;
        Iterator var6 = iterable.iterator();

        while(var6.hasNext()) {
            BlockPos blockpos = (BlockPos)var6.next();
            if (world.getBlockState(blockpos).getBlock() == this) {
                --j;
                if (j <= 0) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isReplaceable(BlockState p_196253_1_, BlockItemUseContext p_196253_2_) {
        BlockState blockstate = p_196253_2_.getWorld().getBlockState(p_196253_2_.getPos());
        if (blockstate.getBlock() == this) {
            return this.getPlacementsActive(blockstate) < FACING_TO_PROPERTY_MAP.size();
        } else {
            return super.isReplaceable(p_196253_1_, p_196253_2_);
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext p_196258_1_) {
        BlockState blockstate = p_196258_1_.getWorld().getBlockState(p_196258_1_.getPos());
        boolean flag = blockstate.getBlock() == this;
        BlockState blockstate1 = flag ? blockstate : this.getDefaultState();
        Direction[] var5 = p_196258_1_.getNearestLookingDirections();
        int var6 = var5.length;

        for(int var7 = 0; var7 < var6; ++var7) {
            Direction direction = var5[var7];
            if (direction != Direction.UP && direction != Direction.DOWN) {
                BooleanProperty booleanproperty = getPropertyFor(direction);
                boolean flag1 = flag && blockstate.get(booleanproperty);
                if (!flag1 && this.canAttachPlant(p_196258_1_.getWorld(), p_196258_1_.getPos(), direction)) {
                    return blockstate1.with(booleanproperty, true);
                }
            }
        }

        return flag ? blockstate1 : null;
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(CAN_GROW,
                NORTH, EAST, SOUTH, WEST);
    }

    /** @deprecated */
    public BlockState rotate(BlockState state, Rotation rotation) {
        switch(rotation) {
            case CLOCKWISE_180:
                return state.with(NORTH, state.get(SOUTH)).with(EAST, state.get(WEST)).with(SOUTH, state.get(NORTH)).with(WEST, state.get(EAST));
            case COUNTERCLOCKWISE_90:
                return state.with(NORTH, state.get(EAST)).with(EAST, state.get(SOUTH)).with(SOUTH, state.get(WEST)).with(WEST, state.get(NORTH));
            case CLOCKWISE_90:
                return state.with(NORTH, state.get(WEST)).with(EAST, state.get(NORTH)).with(SOUTH, state.get(EAST)).with(WEST, state.get(SOUTH));
            default:
                return state;
        }
    }

    /** @deprecated */
    public BlockState mirror(BlockState state, Mirror mirror) {
        switch(mirror) {
            case LEFT_RIGHT:
                return (state.with(NORTH, state.get(SOUTH))).with(SOUTH, state.get(NORTH));
            case FRONT_BACK:
                return (state.with(EAST, state.get(WEST))).with(WEST, state.get(EAST));
            default:
                return super.mirror(state, mirror);
        }
    }

    public static BooleanProperty getPropertyFor(Direction facing) {
        return FACING_TO_PROPERTY_MAP.get(facing);
    }

    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext ctx) {
        VoxelShape voxelshape = VoxelShapes.empty();
        if (state.get(NORTH)) {
            voxelshape = VoxelShapes.or(voxelshape, NORTH_AABB);
        }
        if (state.get(EAST)) {
            voxelshape = VoxelShapes.or(voxelshape, EAST_AABB);
        }
        if (state.get(SOUTH)) {
            voxelshape = VoxelShapes.or(voxelshape, SOUTH_AABB);
        }
        if (state.get(WEST)) {
            voxelshape = VoxelShapes.or(voxelshape, WEST_AABB);
        }

        return voxelshape;
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.NONE;
    }

    public boolean isLadder(BlockState state, IWorldReader world, BlockPos pos, LivingEntity entity) {
        return true;
    }


}
