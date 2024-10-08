package com.matilda.mats_bb.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;


public class TrilobiteFossilBlock extends Block implements SimpleWaterloggedBlock{
    public static final MapCodec<TrilobiteFossilBlock> CODEC = simpleCodec(TrilobiteFossilBlock::new);
    public static final int MIN_FOSSIL = 1;
    public static final int MAX_FOSSIL = 3;
    private static final VoxelShape ONE_FOSSIL_Z = Block.box(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
    private static final VoxelShape ONE_FOSSIL_X = Block.box(5.0, 0.0, 6.0, 11.0, 2.0, 10.0);
    private static final VoxelShape MUlTIPLE_FOSSILS = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);
    public static final IntegerProperty FOSSILS = IntegerProperty.create("fossils",MIN_FOSSIL, MAX_FOSSIL);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    @Override
    public MapCodec<TrilobiteFossilBlock> codec() {
        return CODEC;
    }

    public TrilobiteFossilBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FOSSILS, Integer.valueOf(1)));
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext useContext) {
        return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && state.getValue(FOSSILS) < MAX_FOSSIL
                ? true
                : super.canBeReplaced(state, useContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        //Thank you Supplementaries dev(s) for making a directional block that lets you place multiple of itself on the same block <3
        BlockState blockState = context.getLevel().getBlockState(context.getClickedPos());
        if (blockState.is(this)) {
            return blockState.setValue(FOSSILS, Math.min(3, blockState.getValue(FOSSILS) + 1));
        }
        boolean flag = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        for (Direction direction : context.getNearestLookingDirections()) {
            BlockState blockstate;
            blockstate = this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());

            if (blockstate.canSurvive(context.getLevel(), context.getClickedPos())) {
                return blockstate.setValue(WATERLOGGED, flag);
            }
        }
        return null;
    }

    @Override
    protected BlockState updateShape(
            BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos
    ) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return super.updateShape(state, direction, neighborState, level, pos, neighborPos);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FOSSILS) > 1 ? MUlTIPLE_FOSSILS : state.getValue(FACING).getAxis() == Direction.Axis.X ? ONE_FOSSIL_X : ONE_FOSSIL_Z;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FOSSILS, WATERLOGGED, FACING);
    }
}
