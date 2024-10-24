package fr.r0ane.farmerdelightplus.util;

import fr.r0ane.farmerdelightplus.AllBlocks;
import fr.r0ane.farmerdelightplus.AllItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class fItems {
    private String type;
    public Block FluidA;
    public Block FluidB;
    public Item ItemA;
    public Item GettingItemA;
    public Item GettingItemB;
    public Item ItemB;


    public fItems (String type) {
        this.type = type;
        ItemA = switch (this.type) {
            case "wine" -> AllItems.BOWL_OF_CRUSHED_GRAPE.get();
            case "vodka" -> AllItems.BOWL_OF_POTATO_SUGAR.get();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
        GettingItemA = switch (this.type) {
            case "wine" -> AllItems.EMPTY_WINE_GLASS.get();
            case "vodka" -> AllItems.EMPTY_SHOT_GLASS.get();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
        GettingItemB = switch (this.type) {
            case "wine" -> AllBlocks.WINE_BOTTLE.get().asItem();
            case "vodka" -> AllBlocks.VODKA_BOTTLE.get().asItem();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
        ItemB = switch (this.type) {
            case "wine" -> AllItems.WINE_GLASS.get();
            case "vodka" -> AllItems.VODKA_SHOT_GLASS.get();
            default -> throw new IllegalStateException("Invalid day: " + this.type);
        };
    }
}
