package com.matilda.mats_bb.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class TrilobiteFossilBlock extends Block {
    public static final MapCodec<TrilobiteFossilBlock> CODEC = simpleCodec(TrilobiteFossilBlock::new);
    public static final int MIN_FOSSIL = 1;
    public static final int MAX_FOSSIL = 4;
    private static final VoxelShape ONE_FOSSIL = Block.box(6.0, 0.0, 5.0, 10.0, 2.0, 11.0);
    private static final VoxelShape MUlTIPLE_FOSSILS = Block.box(1.0, 0.0, 1.0, 15.0, 2.0, 15.0);
    public static final IntegerProperty FOSSILS = IntegerProperty.create("fossils",MIN_FOSSIL, MAX_FOSSIL);

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
        return !useContext.isSecondaryUseActive() && useContext.getItemInHand().is(this.asItem()) && state.getValue(FOSSILS) < 4
                ? true
                : super.canBeReplaced(state, useContext);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState blockstate = context.getLevel().getBlockState(context.getClickedPos());
        return blockstate.is(this)
                ? blockstate.setValue(FOSSILS, Integer.valueOf(Math.min(4, blockstate.getValue(FOSSILS) + 1)))
                : super.getStateForPlacement(context);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FOSSILS) > 1 ? MUlTIPLE_FOSSILS : ONE_FOSSIL;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FOSSILS);
    }
}
