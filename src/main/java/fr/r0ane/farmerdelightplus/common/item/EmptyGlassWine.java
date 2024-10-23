package fr.r0ane.farmerdelightplus.common.item;

import fr.r0ane.farmerdelightplus.AllBlocks;
import fr.r0ane.farmerdelightplus.AllItems;
import fr.r0ane.farmerdelightplus.common.block.WineBottle;
import fr.r0ane.farmerdelightplus.util.Util;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class EmptyGlassWine extends Item {
    public EmptyGlassWine(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockState pBlock = pContext.getLevel().getBlockState(pContext.getClickedPos());
        if (pBlock.getBlock() == AllBlocks.WINE_BOTTLE.get())
        {
            int fill = pBlock.getValue(WineBottle.FILL);
            if (fill > 0)
            {
                Util.ConsumeItem(pContext.getItemInHand(), AllItems.WINE_GLASS.get(), pContext.getPlayer(), pContext.getHand());
                pContext.getLevel().setBlockAndUpdate(pContext.getClickedPos(), pBlock.setValue(WineBottle.FILL, fill - 1));
            }
        }
        return super.useOn(pContext);
    }
}
