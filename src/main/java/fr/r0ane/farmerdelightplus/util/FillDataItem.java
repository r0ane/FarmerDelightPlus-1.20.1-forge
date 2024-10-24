package fr.r0ane.farmerdelightplus.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class FillDataItem {
    public static ItemStack setFillTag(ItemStack pItemStack, int fill) {
        CompoundTag pCompoundTag = new CompoundTag();
        pCompoundTag.putInt("fill", fill);
        pCompoundTag.putInt("CustomModelData", fill);
        pItemStack.setTag(pCompoundTag);
        return pItemStack;
    }
}
