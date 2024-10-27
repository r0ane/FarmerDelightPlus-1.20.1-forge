package fr.r0ane.farmerdelightplus.common.item;

import fr.r0ane.farmerdelightplus.AllBlocks;
import fr.r0ane.farmerdelightplus.AllItems;
import fr.r0ane.farmerdelightplus.common.block.WineBottle;
import fr.r0ane.farmerdelightplus.util.Util;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class Drink extends Item {
    public static final FoodProperties WINE = new FoodProperties.Builder().nutrition(1).alwaysEat().build();
    public static final FoodProperties BEER_BOTTLE = new FoodProperties.Builder().nutrition(8).alwaysEat().build();
    public static final FoodProperties PINT_OF_BEER = new FoodProperties.Builder().nutrition(2).alwaysEat().build();
    public static final FoodProperties VODKA = new FoodProperties.Builder().nutrition(1).alwaysEat().build();
    public static final FoodProperties SUGAR_BOTTLE = new FoodProperties.Builder().nutrition(1).alwaysEat().build();

    public Drink(Properties pProperties) {
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

    public UseAnim getUseAnimation(ItemStack pStack) {
        return UseAnim.DRINK;
    }
}
