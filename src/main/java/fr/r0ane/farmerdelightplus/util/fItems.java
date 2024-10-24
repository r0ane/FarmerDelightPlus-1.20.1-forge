package fr.r0ane.farmerdelightplus.util;

import com.mojang.datafixers.TypeRewriteRule;
import fr.r0ane.farmerdelightplus.AllBlocks;
import fr.r0ane.farmerdelightplus.AllItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class fItems {
    private String type;
    public Block FluidA;
    public Block FluidB;
    public Item ItemA;
    public Item GettingItemA;
    public ItemStack GettingItemB;
    public Item ItemB;
    public ItemStack ItemC;


    public fItems (String type) {
        this.type = type;
        ItemA = switch (this.type) {
            case "wine" -> AllItems.BOWL_OF_CRUSHED_GRAPE.get();
            case "vodka" -> AllItems.POTATO_SUGAR_BOTTLE.get();
            case "beer" -> AllItems.BARLEY_SUGAR_BOTTLE.get();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
        GettingItemA = switch (this.type) {
            case "wine" -> AllItems.EMPTY_WINE_GLASS.get();
            case "vodka" -> AllItems.EMPTY_SHOT_GLASS.get();
            case "beer" -> AllItems.EMPTY_PINT_OF_BEER.get();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
        GettingItemB = switch (this.type) {
            case "wine" -> FillDataItem.setFillTag(AllBlocks.WINE_BOTTLE.get().asItem().getDefaultInstance(), 0);
            case "vodka" -> FillDataItem.setFillTag(AllBlocks.VODKA_BOTTLE.get().asItem().getDefaultInstance(), 0);
            case "beer" -> Items.GLASS_BOTTLE.getDefaultInstance();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
        ItemB = switch (this.type) {
            case "wine" -> AllItems.WINE_GLASS.get();
            case "vodka" -> AllItems.VODKA_SHOT_GLASS.get();
            case "beer" -> AllItems.PINT_OF_BEER.get();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
        ItemC = switch (this.type) {
            case "wine" -> FillDataItem.setFillTag(AllBlocks.WINE_BOTTLE.get().asItem().getDefaultInstance(), 4);
            case "vodka" -> FillDataItem.setFillTag(AllBlocks.VODKA_BOTTLE.get().asItem().getDefaultInstance(), 4);
            case "beer" -> AllItems.BEER_BOTTLE.get().getDefaultInstance();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
    }
}
