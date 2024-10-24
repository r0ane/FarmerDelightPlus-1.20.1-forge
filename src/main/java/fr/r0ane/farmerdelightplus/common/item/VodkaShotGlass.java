package fr.r0ane.farmerdelightplus.common.item;

import fr.r0ane.farmerdelightplus.AllItems;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class VodkaShotGlass extends Item {
    public static final FoodProperties FOOD = new FoodProperties.Builder().nutrition(1).alwaysEat().build();

    public VodkaShotGlass(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack pStack, Level pLevel, LivingEntity pLivingEntity) {
        super.finishUsingItem(pStack, pLevel, pLivingEntity);
        if (pLivingEntity instanceof ServerPlayer $$3) {
            CriteriaTriggers.CONSUME_ITEM.trigger($$3, pStack);
            $$3.awardStat(Stats.ITEM_USED.get(this));
        }

        if (pStack.isEmpty()) {
            return new ItemStack(AllItems.EMPTY_SHOT_GLASS.get());
        } else {
            if (pLivingEntity instanceof Player && !((Player)pLivingEntity).getAbilities().instabuild) {
                ItemStack $$4 = new ItemStack(AllItems.EMPTY_SHOT_GLASS.get());
                Player $$5 = (Player)pLivingEntity;
                if (!$$5.getInventory().add($$4)) {
                    $$5.drop($$4, false);
                }
            }

            return pStack;
        }
    }

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }
}
