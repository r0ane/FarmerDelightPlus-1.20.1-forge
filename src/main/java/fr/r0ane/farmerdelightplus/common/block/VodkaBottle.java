package fr.r0ane.farmerdelightplus.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class VodkaBottle extends Block {
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 4);
    private static final VoxelShape SHAPE = makeShape();

    public VodkaBottle(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)((BlockState)this.stateDefinition.any()).setValue(FILL, 4));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FILL);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        ItemStack item = pContext.getItemInHand();
        if (item.hasTag()) {
            return super.getStateForPlacement(pContext).setValue(FILL, item.getTag().getInt("fill"));
        }
        return super.getStateForPlacement(pContext);
    }

    public static VoxelShape makeShape() {
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.375, 0.0, 0.375, 0.625, 0.6875, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.6875, 0.4375, 0.5625, 0.875, 0.5625), BooleanOp.OR);
        return shape;
    }

    public int getFill (BlockState pState) {
        return pState.getValue(FILL);
    }

    /*@Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide()) {
            if (pPlayer.getItemInHand(pHand).getItem() == AllItems.EMPTY_WINE_GLASS.get() && getFill(pState) > 0)
            {
                Util.ConsumeItem(pPlayer.getItemInHand(pHand), AllItems.WINE_GLASS.get(), pPlayer, pHand);
                pLevel.setBlockAndUpdate(pPos, pState.setValue(FILL, getFill(pState) - 1));
            }
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        return InteractionResult.SUCCESS;
    }*/
}
