package fr.r0ane.farmerdelightplus.util;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Util {
    public static void ConsumeItem (ItemStack pItemStack, Item pItem, Player pPlayer, InteractionHand pHand) {
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

    public static void ConsumeItem (ItemStack pItemStack, ItemStack pItem, Player pPlayer, InteractionHand pHand) {
        if (!pPlayer.isCreative()) {
            pItemStack.setCount(pItemStack.getCount() - 1);
            pPlayer.setItemInHand(pHand, pItemStack);

            if (pItemStack.isEmpty()) {
                pPlayer.setItemInHand(pHand, pItem);
            } else {
                pPlayer.addItem(pItem);
            }
        }
    }
}
