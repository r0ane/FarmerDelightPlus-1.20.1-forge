package fr.r0ane.farmerdelightplus;

import fr.r0ane.farmerdelightplus.common.item.EmptyGlassWine;
import fr.r0ane.farmerdelightplus.common.item.EmptyShotGlass;
import fr.r0ane.farmerdelightplus.common.item.VodkaShotGlass;
import fr.r0ane.farmerdelightplus.common.item.WineGlass;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<Item> GRAPE = ITEMS.register("grape",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BOWL_OF_CRUSHED_GRAPE = ITEMS.register("bowl_of_crushed_grape",
            () -> new Item(new Item.Properties().craftRemainder(Items.BOWL)));

    public static final RegistryObject<Item> WINE_GLASS = ITEMS.register("wine_glass",
            () -> new WineGlass(new Item.Properties().food(WineGlass.FOOD)));

    public static final RegistryObject<Item> EMPTY_WINE_GLASS = ITEMS.register("empty_wine_glass",
            () -> new EmptyGlassWine(new Item.Properties()));

    public static final RegistryObject<Item> VODKA_SHOT_GLASS = ITEMS.register("vodka_shot_glass",
            () -> new VodkaShotGlass(new Item.Properties().food(WineGlass.FOOD)));

    public static final RegistryObject<Item> EMPTY_SHOT_GLASS = ITEMS.register("empty_shot_glass",
            () -> new EmptyShotGlass(new Item.Properties()));

    public static final RegistryObject<Item> BOWL_OF_CRUSHED_POTATO = ITEMS.register("bowl_of_crushed_potato",
            () -> new Item(new Item.Properties().craftRemainder(Items.BOWL)));

    public static final RegistryObject<Item> POTATO_SUGAR_BOTTLE = ITEMS.register("potato_sugar_bottle",
            () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));

    public static final RegistryObject<Item> COCA_TREE_SEED = ITEMS.register("coca_tree_seed",
            () -> new ItemNameBlockItem(AllBlocks.COCA_TREE.get(), new Item.Properties()));

    public static final RegistryObject<Item> COCA_LEEF = ITEMS.register("coca_leaf",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARLEY = ITEMS.register("barley",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARLEY_SUGAR_BOTTLE = ITEMS.register("barley_sugar_bottle",
            () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE)));

    public static final RegistryObject<Item> BEER_BOTTLE = ITEMS.register("beer_bottle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> EMPTY_PINT_OF_BEER = ITEMS.register("empty_pint_of_beer",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PINT_OF_BEER = ITEMS.register("pint_of_beer",
            () -> new Item(new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
