package fr.r0ane.farmerdelightplus.common.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class WineBottleItem extends BlockItem {
    public WineBottleItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    public static ItemStack setFillTag(ItemStack pItemStack, int fill) {
        CompoundTag pCompoundTag = new CompoundTag();
        pCompoundTag.putInt("fill", fill);
        pCompoundTag.putInt("CustomModelData", fill);
        pItemStack.setTag(pCompoundTag);
        return pItemStack;
    }

    public static int getFill(ItemStack pItemStack) {
        return pItemStack.getTag().getInt("fill");
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        if (pPlayer.getItemInHand(pUsedHand).hasTag())
        {
            pPlayer.sendSystemMessage(Component.literal("" + getFill(pPlayer.getItemInHand(pUsedHand))));
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
