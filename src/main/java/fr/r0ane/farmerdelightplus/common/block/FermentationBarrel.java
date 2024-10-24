package fr.r0ane.farmerdelightplus.common.block;

import fr.r0ane.farmerdelightplus.AllItems;
import fr.r0ane.farmerdelightplus.common.blockentity.FermentationBarrelBlockEntity;
import fr.r0ane.farmerdelightplus.util.FillDataItem;
import fr.r0ane.farmerdelightplus.util.Util;
import fr.r0ane.farmerdelightplus.util.fItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public class FermentationBarrel extends BaseEntityBlock {
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.values());
    public FermentationBarrel (Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new FermentationBarrelBlockEntity(pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder);
        pBuilder.add(FACING);
    }

    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext) {
        return (BlockState)this.defaultBlockState().setValue(FACING, pContext.getNearestLookingDirection().getOpposite());
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if (!pLevel.isClientSide())
        {
            ItemStack pItem = pPlayer.getItemInHand(pHand);
            BlockEntity be = pLevel.getBlockEntity(pPos);
            if (be instanceof FermentationBarrelBlockEntity pBlockEntity)
            {
                if (pBlockEntity.getHisType() == null)
                {
                    pBlockEntity.setHisType(
                            pItem.getItem() == AllItems.BOWL_OF_CRUSHED_GRAPE.get() ? "wine" :
                                    pItem.getItem() == AllItems.BOWL_OF_POTATO_SUGAR.get() ? "vodka" : null
                    );
                }
                if (!(pBlockEntity.getHisType() == null))
                {
                    onUse(pBlockEntity, pItem, pPlayer, pHand, pBlockEntity.getHisType());
                    pPlayer.sendSystemMessage(
                            Component.literal("Grape Juice quantity: " + pBlockEntity.getQuantityA() + "ml\n" + pBlockEntity.getHisType() + " Quantity: " + pBlockEntity.getQuantityB() + "ml")
                    );
                }
                return InteractionResult.sidedSuccess(pLevel.isClientSide());
            }
        }
        return InteractionResult.SUCCESS;
    }

    private void onUse (FermentationBarrelBlockEntity pBlockEntity, ItemStack pItem, Player player, InteractionHand pHand, String pType) {
        fItems i = new fItems(pType);
        if (pBlockEntity.getQuantityA() + pBlockEntity.getQuantityB() <= 4900 && pItem.getItem() == i.ItemA)
        {
            pBlockEntity.IncrementA(100);
            Util.ConsumeItem(pItem, Items.BOWL.asItem(), player, pHand);
        }
        else if (pBlockEntity.getQuantityB() >= 250 && pItem.getItem() == i.GettingItemA)
        {
            pBlockEntity.DecrementB(250);
            Util.ConsumeItem(pItem, i.ItemB, player, pHand);
        }
        else if (pBlockEntity.getQuantityB() >= 1000 && pItem.getItem() == i.GettingItemB && pItem.getTag().getInt("fill") == 0)
        {
            pBlockEntity.DecrementB(1000);
            Util.ConsumeItem(pItem, FillDataItem.setFillTag(i.GettingItemB.getDefaultInstance(), 4), player, pHand);
        }
    }

    private void consumeItem (ItemStack pItemStack, Item pItem, Player pPlayer, InteractionHand pHand) {
        if (!pPlayer.isCreative()) {
            pItemStack.setCount(pItemStack.getCount() - 1);
            pPlayer.setItemInHand(pHand, pItemStack);

            if (pItemStack.isEmpty()) {
                pPlayer.setItemInHand(pHand, pItem.getDefaultInstance());
            } else {
                pPlayer.addItem(pItem.getDefaultInstance());
            }
        }
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return pLevel.isClientSide ? null : (level0, pos0, state0, blockEntity) -> ((FermentationBarrelBlockEntity)blockEntity).tick();
    }
}
