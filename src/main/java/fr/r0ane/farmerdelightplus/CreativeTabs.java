package fr.r0ane.farmerdelightplus;

import fr.r0ane.farmerdelightplus.common.item.WineBottleItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Main.MODID);

    public static final RegistryObject<CreativeModeTab> TUTORIAL_TAB = CREATIVE_MODE_TABS.register("farmerdelightplus",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(AllItems.GRAPES.get()))
                    .title(Component.translatable("creativetab.farmerdelightplus"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(AllItems.GRAPES.get());
                        pOutput.accept(AllItems.BOWL_OF_CRUSHED_GRAPES.get());
                        pOutput.accept(WineBottleItem.setFillTag(AllBlocks.WINE_BOTTLE.get().asItem().getDefaultInstance(), 0));
                        pOutput.accept(WineBottleItem.setFillTag(AllBlocks.WINE_BOTTLE.get().asItem().getDefaultInstance(), 4));
                        pOutput.accept(AllItems.EMPTY_WINE_GLASS.get());
                        pOutput.accept(AllItems.WINE_GLASS.get());
                        pOutput.accept(AllBlocks.FERMENTATION_BARREL.get());
                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
