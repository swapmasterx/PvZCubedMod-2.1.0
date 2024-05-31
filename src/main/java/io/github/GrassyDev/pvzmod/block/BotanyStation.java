package io.github.GrassyDev.pvzmod.block;

import com.mojang.serialization.MapCodec;
import io.github.GrassyDev.pvzmod.block.entity.BotanyStationBlockEntity;
import io.github.GrassyDev.pvzmod.block.entity.ModBlockEntities;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class BotanyStation extends BlockWithEntity implements BlockEntityProvider {
    private static final VoxelShape SHAPE = Block.createCuboidShape(1, 0, 1, 15, 16, 15);

	public static final MapCodec<BotanyStation> CODEC = BotanyStation.method_54094(BotanyStation::new);
	public Settings getProperties() {
		return this.settings;
	}
	@Override
	protected MapCodec<? extends BlockWithEntity> getCodec() {

		return CODEC;
	}
	public static final DirectionProperty FACING;

	static {
		FACING = HorizontalFacingBlock.FACING;
	}
	@Override
	public BlockState rotate(BlockState state, BlockRotation rotation) {
		return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
	}
	@Override
	public BlockState mirror(BlockState state, BlockMirror mirror) {
		return state.rotate(mirror.getRotation((Direction)state.get(FACING)));
	}

	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(FACING);
	}
	public BotanyStation(Settings settings) {
		super(settings);
		this.setDefaultState((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)));
    }
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
	}
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {

		return BlockRenderType.MODEL;
    }


    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {

        return new BotanyStationBlockEntity(pos, state);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof BotanyStationBlockEntity) {
                ItemScatterer.spawn(world, pos, (BotanyStationBlockEntity)blockEntity);
                world.updateComparators(pos,this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) {
            NamedScreenHandlerFactory screenHandlerFactory = ((BotanyStationBlockEntity) world.getBlockEntity(pos));

            if (screenHandlerFactory != null) {
                player.openHandledScreen(screenHandlerFactory);
            }
        }

        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return checkType(type, ModBlockEntities.BOTANY_STATION_BLOCK_ENTITY,
                (world1, blockPos, blockState, blockEntity) -> blockEntity.tick(world1, blockPos, blockState, blockEntity));
    }
}
