package fr.r0ane.farmerdelightplus;

import fr.r0ane.farmerdelightplus.common.block.FermentationBarrel;
import fr.r0ane.farmerdelightplus.common.block.WineBottle;
import fr.r0ane.farmerdelightplus.common.item.WineBottleItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class AllBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final RegistryObject<FermentationBarrel> FERMENTATION_BARREL = registerBlock("fermentation_barrel",
            () -> new FermentationBarrel(BlockBehaviour.Properties.copy(Blocks.BARREL)));

    public static final RegistryObject<WineBottle> WINE_BOTTLE = registerWineBottleBlock("wine_bottle",
            () -> new WineBottle(BlockBehaviour.Properties.copy(Blocks.GLASS)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return AllItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static <T extends Block> RegistryObject<T> registerWineBottleBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerWineBottleBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerWineBottleBlockItem(String name, RegistryObject<T> block) {
        return AllItems.ITEMS.register(name, () -> new WineBottleItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
