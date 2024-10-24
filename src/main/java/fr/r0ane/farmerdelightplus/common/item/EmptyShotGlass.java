package fr.r0ane.farmerdelightplus.common.item;

import fr.r0ane.farmerdelightplus.AllBlocks;
import fr.r0ane.farmerdelightplus.AllItems;
import fr.r0ane.farmerdelightplus.common.block.VodkaBottle;
import fr.r0ane.farmerdelightplus.common.block.WineBottle;
import fr.r0ane.farmerdelightplus.util.Util;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;

public class EmptyShotGlass extends Item {
    public EmptyShotGlass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockState pBlock = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (pBlock.getBlock() == AllBlocks.VODKA_BOTTLE.get())
        {
            int fill = pBlock.getValue(VodkaBottle.FILL);
            if (fill > 0)
            {
                Util.ConsumeItem(pContext.getItemInHand(), AllItems.VODKA_SHOT_GLASS.get(), pContext.getPlayer(), pContext.getHand());
                pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos(), pBlock.setValue(VodkaBottle.FILL, fill - 1));
            }
        }
        return super.useOn(pContext);
    }
}
