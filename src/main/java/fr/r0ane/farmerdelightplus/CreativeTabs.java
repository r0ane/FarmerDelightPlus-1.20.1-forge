package fr.r0ane.farmerdelightplus;

import fr.r0ane.farmerdelightplus.util.FillDataItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("farmerdelightplus",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AllItems.GRAPE.get()))
                    .title(Component.translatable("creativetab.farmerdelightplus"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(AllItems.GRAPE.get());
                        pOutput.accept(AllItems.BOWL_OF_CRUSHED_GRAPE.get());
                        pOutput.accept(FillDataItem.setFillTag(AllBlocks.WINE_BOTTLE.get().asItem().getDefaultInstance(), 0));
                        pOutput.accept(FillDataItem.setFillTag(AllBlocks.WINE_BOTTLE.get().asItem().getDefaultInstance(), 4));
                        pOutput.accept(AllItems.EMPTY_WINE_GLASS.get());
                        pOutput.accept(AllItems.WINE_GLASS.get());
                        pOutput.accept(AllItems.BOWL_OF_CRUSHED_POTATO.get());
                        pOutput.accept(AllItems.POTATO_SUGAR_BOTTLE.get());
                        pOutput.accept(FillDataItem.setFillTag(AllBlocks.VODKA_BOTTLE.get().asItem().getDefaultInstance(), 0));
                        pOutput.accept(FillDataItem.setFillTag(AllBlocks.VODKA_BOTTLE.get().asItem().getDefaultInstance(), 4));
                        pOutput.accept(AllItems.EMPTY_SHOT_GLASS.get());
                        pOutput.accept(AllItems.VODKA_SHOT_GLASS.get());
                        pOutput.accept(AllItems.BARLEY_SEED.get());
                        pOutput.accept(AllItems.BARLEY.get());
                        pOutput.accept(AllItems.BARLEY_SUGAR_BOTTLE.get());
                        pOutput.accept(AllItems.EMPTY_PINT_OF_BEER.get());
                        pOutput.accept(AllItems.PINT_OF_BEER.get());
                        pOutput.accept(AllItems.BEER_BOTTLE.get());
                        pOutput.accept(AllBlocks.FERMENTATION_BARREL.get());
                        pOutput.accept(AllItems.COCA_TREE_SEED.get());
                        pOutput.accept(AllItems.COCA_LEEF.get());
                        pOutput.accept(AllBlocks.FREEZER.get());
                        pOutput.accept(AllBlocks.STRAWBERRY.get());
                        pOutput.accept(AllItems.STRAWBERRY_ICE_CREAM_BOTTLE.get());
                        pOutput.accept(AllItems.VANILLA_ICE_CREAM_BOTTLE.get());
                        pOutput.accept(AllItems.CHOCOLATE_ICE_CREAM_BOTTLE.get());
                        pOutput.accept(AllItems.STRAWBERRY_ICE_CREAM_BALL.get());
                        pOutput.accept(AllItems.VANILLA_ICE_CREAM_BALL.get());
                        pOutput.accept(AllItems.CHOCOLATE_ICE_CREAM_BALL.get());
                        pOutput.accept(AllItems.ICE_CREAM_CONE.get());
                        pOutput.accept(AllItems.STRAWBERRY_ICE_CREAM.get());
                        pOutput.accept(AllItems.VANILLA_ICE_CREAM.get());
                        pOutput.accept(AllItems.CHOCOLATE_ICE_CREAM.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
