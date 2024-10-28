package fr.r0ane.farmerdelightplus;

import com.simibubi.create.foundation.ponder.instruction.EmitParticlesInstruction;
import fr.r0ane.farmerdelightplus.common.item.*;
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
            () -> new ItemNameBlockItem(AllBlocks.BUDDING_GRAPE.get(), new Item.Properties().food(Food.GRAPE)));

    public static final RegistryObject<Item> BOWL_OF_CRUSHED_GRAPE = ITEMS.register("bowl_of_crushed_grape",
            () -> new Food(new Item.Properties().craftRemainder(Items.BOWL).food(Food.CRUSHED_GRAPE)));

    public static final RegistryObject<Item> EMPTY_WINE_GLASS = ITEMS.register("empty_wine_glass",
            () -> new EmptyGlassWine(new Item.Properties()));

    public static final RegistryObject<Item> WINE_GLASS = ITEMS.register("wine_glass",
            () -> new Drink(new Item.Properties().craftRemainder(EMPTY_WINE_GLASS.get()).food(Drink.WINE)));

    public static final RegistryObject<Item> EMPTY_SHOT_GLASS = ITEMS.register("empty_shot_glass",
            () -> new EmptyShotGlass(new Item.Properties()));

    public static final RegistryObject<Item> VODKA_SHOT_GLASS = ITEMS.register("vodka_shot_glass",
            () -> new Drink(new Item.Properties().craftRemainder(EMPTY_SHOT_GLASS.get()).food(Drink.VODKA)));

    public static final RegistryObject<Item> BOWL_OF_CRUSHED_POTATO = ITEMS.register("bowl_of_crushed_potato",
            () -> new Food(new Item.Properties().craftRemainder(Items.BOWL).food(Food.CRUSHED_POTATO)));

    public static final RegistryObject<Item> POTATO_SUGAR_BOTTLE = ITEMS.register("potato_sugar_bottle",
            () -> new Drink(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(Drink.SUGAR_BOTTLE)));

    public static final RegistryObject<Item> COCA_TREE_SEED = ITEMS.register("coca_tree_seed",
            () -> new ItemNameBlockItem(AllBlocks.COCA_TREE.get(), new Item.Properties()));

    public static final RegistryObject<Item> COCA_LEEF = ITEMS.register("coca_leaf",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARLEY_SEED = ITEMS.register("barley_seed",
            () -> new ItemNameBlockItem(AllBlocks.BARLEY.get(), new Item.Properties()));

    public static final RegistryObject<Item> BARLEY = ITEMS.register("barley",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BARLEY_SUGAR_BOTTLE = ITEMS.register("barley_sugar_bottle",
            () -> new Drink(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(Drink.SUGAR_BOTTLE)));

    public static final RegistryObject<Item> BEER_BOTTLE = ITEMS.register("beer_bottle",
            () -> new Drink(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(Drink.BEER_BOTTLE)));

    public static final RegistryObject<Item> EMPTY_PINT_OF_BEER = ITEMS.register("empty_pint_of_beer",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PINT_OF_BEER = ITEMS.register("pint_of_beer",
            () -> new Drink(new Item.Properties().craftRemainder(EMPTY_PINT_OF_BEER.get()).food(Drink.PINT_OF_BEER)));

    public static final RegistryObject<Item> ICE_CREAM_CONE = ITEMS.register("ice_cream_cone",
            () -> new Food(new Item.Properties().food(Food.CONE)));

    public static final RegistryObject<Item> STRAWBERRY_ICE_CREAM_BOTTLE = ITEMS.register("strawberry_ice_cream_bottle",
            () -> new Drink(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(Drink.ICE_CREAM)));

    public static final RegistryObject<Item> STRAWBERRY_ICE_CREAM_BALL = ITEMS.register("strawberry_ice_cream_ball",
            () -> new Food(new Item.Properties().food(Food.ICE_CREAM)));

    public static final RegistryObject<Item> STRAWBERRY_ICE_CREAM = ITEMS.register("strawberry_ice_cream",
            () -> new Food(new Item.Properties().craftRemainder(ICE_CREAM_CONE.get()).food(Food.ICE_CREAM)));

    public static final RegistryObject<Item> VANILLA_ICE_CREAM_BOTTLE = ITEMS.register("vanilla_ice_cream_bottle",
            () -> new Drink(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(Drink.ICE_CREAM)));

    public static final RegistryObject<Item> VANILLA_ICE_CREAM_BALL = ITEMS.register("vanilla_ice_cream_ball",
            () -> new Food(new Item.Properties().food(Food.ICE_CREAM)));

    public static final RegistryObject<Item> VANILLA_ICE_CREAM = ITEMS.register("vanilla_ice_cream",
            () -> new Food(new Item.Properties().craftRemainder(ICE_CREAM_CONE.get()).food(Food.ICE_CREAM)));

    public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM_BOTTLE = ITEMS.register("chocolate_ice_cream_bottle",
            () -> new Drink(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).food(Drink.ICE_CREAM)));

    public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM_BALL = ITEMS.register("chocolate_ice_cream_ball",
            () -> new Food(new Item.Properties().food(Food.ICE_CREAM)));

    public static final RegistryObject<Item> CHOCOLATE_ICE_CREAM = ITEMS.register("chocolate_ice_cream",
            () -> new Food(new Item.Properties().craftRemainder(ICE_CREAM_CONE.get()).food(Food.ICE_CREAM)));

    public static final RegistryObject<Item> STRAWBERRY = ITEMS.register("strawberry",
            () -> new ItemNameBlockItem(AllBlocks.STRAWBERRY.get(), new Item.Properties().food(Food.STRAWBERRY)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
