package fr.r0ane.farmerdelightplus;

import fr.r0ane.farmerdelightplus.common.block.*;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.function.Supplier;

public class AllBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Main.MODID);

    public static final RegistryObject<FermentationBarrel> FERMENTATION_BARREL = registerBlock("fermentation_barrel",
            () -> new FermentationBarrel(BlockBehaviour.Properties.copy(Blocks.BARREL)));

    public static final RegistryObject<WineBottle> WINE_BOTTLE = registerBlock("wine_bottle",
            () -> new WineBottle(BlockBehaviour.Properties.copy(Blocks.GLASS)));

    public static final RegistryObject<VodkaBottle> VODKA_BOTTLE = registerBlock("vodka_bottle",
            () -> new VodkaBottle(BlockBehaviour.Properties.copy(Blocks.GLASS)));

    public static final RegistryObject<CocaSapling> COCA_TREE = BLOCKS.register("coca_tree",
            () -> new CocaSapling(BlockBehaviour.Properties.copy(Blocks.WHEAT)));

    public static final RegistryObject<Freezer> FREEZER = registerBlock("freezer",
            () -> new Freezer(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK)));

    public static final RegistryObject<Grape> GRAPE = BLOCKS.register("grape",
            () -> new Grape(BlockBehaviour.Properties.of().noCollission().randomTicks().instabreak().sound(SoundType.CROP).pushReaction(PushReaction.DESTROY)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return AllItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
