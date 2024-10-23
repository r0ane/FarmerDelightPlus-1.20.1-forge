package fr.r0ane.farmerdelightplus;

import fr.r0ane.farmerdelightplus.common.item.EmptyGlassWine;
import fr.r0ane.farmerdelightplus.common.item.WineGlass;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Main.MODID);

    public static final RegistryObject<Item> GRAPES = ITEMS.register("grapes",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BOWL_OF_CRUSHED_GRAPES = ITEMS.register("bowl_of_crushed_grapes",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WINE_GLASS = ITEMS.register("wine_glass",
            () -> new WineGlass(new Item.Properties().food(WineGlass.FOOD)));

    public static final RegistryObject<Item> EMPTY_WINE_GLASS = ITEMS.register("empty_wine_glass",
            () -> new EmptyGlassWine(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
