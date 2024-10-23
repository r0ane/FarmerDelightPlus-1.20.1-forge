package fr.r0ane.farmerdelightplus.common.block;

import fr.r0ane.farmerdelightplus.AllItems;
import fr.r0ane.farmerdelightplus.common.item.WineBottleItem;
import fr.r0ane.farmerdelightplus.common.item.WineGlass;
import fr.r0ane.farmerdelightplus.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WineBottle extends Block {
    public static final IntegerProperty FILL = IntegerProperty.create("fill", 0, 4);
    private static final VoxelShape SHAPE = makeShape();

    public WineBottle(Properties pProperties) {
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
        shape = Shapes.join(shape, Shapes.box(0.375, 0.0, 0.375, 0.625, 0.625, 0.625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0.625, 0.4375, 0.5625, 0.8125, 0.5625), BooleanOp.OR);
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
