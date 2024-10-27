package fr.r0ane.farmerdelightplus.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Food extends Item {
    public static final FoodProperties GRAPE = new FoodProperties.Builder().nutrition(4).build();
    public static final FoodProperties CRUSHED_GRAPE = new FoodProperties.Builder().nutrition(4).build();
    public static final FoodProperties CRUSHED_POTATO = new FoodProperties.Builder().nutrition(2).build();

    public Food(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        super.finishUsingItem(pStack, pLevel, pLivingEntity);
        if (pLivingEntity instanceof ServerPlayer $$3) {
            CriteriaTriggers.CONSUME_ITEM.trigger($$3, pStack);
            $$3.awardStat(Stats.ITEM_USED.get(this));
        }

        if (this.asItem().hasCraftingRemainingItem())
        {
            if (pStack.isEmpty()) {
                return new ItemStack(this.asItem().getCraftingRemainingItem());
            } else {
                if (pLivingEntity instanceof Player && !((Player) pLivingEntity).getAbilities().instabuild) {
                    ItemStack $$4 = new ItemStack(this.asItem().getCraftingRemainingItem());
                    Player $$5 = (Player) pLivingEntity;
                    if (!$$5.getInventory().add($$4)) {
                        $$5.drop($$4, false);
                    }
                }
            }
        }
        return pStack;
    }
}
