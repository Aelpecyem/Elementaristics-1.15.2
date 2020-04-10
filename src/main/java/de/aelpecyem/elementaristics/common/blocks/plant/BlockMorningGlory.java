package de.aelpecyem.elementaristics.common.blocks.plant;

import de.aelpecyem.elementaristics.reg.ModBlocks;
import de.aelpecyem.elementaristics.reg.ModPotions;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IPlantable;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

//these are basically reversed vines
public class BlockMorningGlory extends FlowerBlock {
    public static final BooleanProperty NORTH = SixWayBlock.NORTH;
    public static final BooleanProperty EAST = SixWayBlock.EAST;
    public static final BooleanProperty SOUTH = SixWayBlock.SOUTH;
    public static final BooleanProperty WEST = SixWayBlock.WEST;
    public static final BooleanProperty CAN_GROW = BooleanProperty.create("can_grow");
    public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = (Map) SixWayBlock.FACING_TO_PROPERTY_MAP.entrySet().stream().filter((entry) -> entry.getKey() != Direction.UP && entry.getKey() != Direction.DOWN).collect(Util.toMapCollector());
    protected static final VoxelShape WEST_AABB = Block.makeCuboidShape(0, 0, 0, 1, 16, 16);
    protected static final VoxelShape EAST_AABB = Block.makeCuboidShape(15, 0, 0, 16, 16, 16);
    protected static final VoxelShape NORTH_AABB = Block.makeCuboidShape(0, 0, 0, 16, 16, 1);
    protected static final VoxelShape SOUTH_AABB = Block.makeCuboidShape(0, 0, 15, 16, 16, 16);


    public BlockMorningGlory() {
        super(Effects.BLINDNESS, 120, Properties.from(Blocks.VINE));
        this.setDefaultState(this.stateContainer.getBaseState()//.with(DOWN, false)
                .with(NORTH, false).with(EAST, false).with(SOUTH, false).with(WEST, false).with(CAN_GROW, true));
    }

    @Override
    public ActionResultType onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        System.out.println(player.getDisplayName());
        if (!world.isRemote()) {
            scheduledTick(state, (ServerWorld) world, pos, player.getRNG());
        }
        return super.onUse(state, world, pos, player, p_225533_5_, p_225533_6_);
    }

    public BlockState getPlantStateForPosition(ServerWorld world, BlockPos pos) {
        BlockState state = getDefaultState();
        for (Direction direction : FACING_TO_PROPERTY_MAP.keySet()) {
            if (canAttachPlant(world, pos, direction)) {
                state = state.with(FACING_TO_PROPERTY_MAP.get(direction), true);
            }
        }
        return getPlacementsActive(state) > 0 ? state.with(CAN_GROW, !(world.getBlockState(pos.down()).getBlock() instanceof BlockMorningGlory && world.getRandom().nextBoolean())) : Blocks.AIR.getDefaultState();
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockstate = this.getStateAttachedSides(state, world, pos);
        if (blockstate != state) { //rework all of this i guess
            if (blockstate.getBlock() instanceof AirBlock) {
                spawnDrops(state, world, pos);
                world.removeBlock(pos, false);
            } else if (this.isPosValid(blockstate, world, pos)) {
                world.setBlockState(pos, blockstate, 2);
            }
        } else if (state.get(CAN_GROW) && world.rand.nextInt(4) == 0 && world.isAreaLoaded(pos, 4)) {
            grow(state, world, pos, random);
        }
    }

    public void grow(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockPos placePos = pos.add(1 - random.nextInt(3), 1 - random.nextInt(3), 1 - random.nextInt(3));
        if (placePos != pos){
            if (world.isAirBlock(placePos) && world.getBlockState(placePos.down()).canSustainPlant(world, pos, Direction.UP, this))
                world.setBlockState(placePos, getPlantStateForPosition(world, placePos), 2);
        }

    }

    @Override
    public Effect getStewEffect() {
        return ModPotions.INTOXICATED.get();
    }

    public boolean isValidPosition(BlockState state, IWorldReader world, BlockPos pos) {
        return this.isPosValid(this.getStateAttachedSides(state, world, pos), world, pos);
    }

    private boolean isPosValid(BlockState state, IWorldReader world, BlockPos pos) {
        return this.getPlacementsActive(state) > 0 && isGroundValid(state, world, pos);
    }


    public static boolean isGroundValid(BlockState state, IWorldReader world, BlockPos pos) {
        return world.getBlockState(pos.down()).getBlock().canSustainPlant(world.getBlockState(pos.down()), world, pos, Direction.UP, (IPlantable) ModBlocks.morning_glory.get());
    }

    @Override
    public boolean canSustainPlant(BlockState state, IBlockReader world, BlockPos pos, Direction facing, IPlantable plantable) {
        return plantable instanceof BlockMorningGlory && state.get(CAN_GROW);
    }

    private int getPlacementsActive(BlockState state) {
        int i = 0;
        Iterator var3 = FACING_TO_PROPERTY_MAP.values().iterator();

        while (var3.hasNext()) {
            BooleanProperty booleanproperty = (BooleanProperty) var3.next();
            if (state.get(booleanproperty)) {
                ++i;
            }
        }

        return i;
    }

    private boolean canAttachPlant(IBlockReader world, BlockPos pos, Direction facing) {
        return facing.getAxis() != Direction.Axis.Y && canAttachTo(world, pos.offset(facing), facing);
    }

    public static boolean canAttachTo(IBlockReader world, BlockPos pos, Direction direction) {
        BlockState blockstate = world.getBlockState(pos);
        return Block.doesSideFillSquare(blockstate.getCollisionShape(world, pos), direction); //might not be the opposite?
    }

    private BlockState getStateAttachedSides(BlockState state, IBlockReader world, BlockPos pos) {
        BlockPos blockpos = pos.up();

        BlockState blockstate = null;
        Iterator var6 = Direction.Plane.HORIZONTAL.iterator();

        while (true) {
            Direction direction;
            BooleanProperty booleanproperty;
            do {
                if (!var6.hasNext()) {
                    return state;
                }

                direction = (Direction) var6.next();
                booleanproperty = getPropertyFor(direction);
            } while (!state.get(booleanproperty));

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

    @Override
    public BlockState updatePostPlacement(BlockState state, Direction direction, BlockState otherState, IWorld world, BlockPos pos, BlockPos otherPos) {
        BlockState blockstate = this.getStateAttachedSides(state, world, pos);
        return this.isPosValid(blockstate, world, pos) ? blockstate : Blocks.AIR.getDefaultState();
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

        for (int var7 = 0; var7 < var6; ++var7) {
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

    /**
     * @deprecated
     */
    public BlockState rotate(BlockState state, Rotation rotation) {
        switch (rotation) {
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

    /**
     * @deprecated
     */
    public BlockState mirror(BlockState state, Mirror mirror) {
        switch (mirror) {
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
