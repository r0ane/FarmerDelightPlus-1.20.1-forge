package fr.r0ane.farmerdelightplus;

import fr.r0ane.farmerdelightplus.common.blockentity.FermentationBarrelBlockEntity;
import fr.r0ane.farmerdelightplus.common.blockentity.FreezerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class AllBlockEntity {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Main.MODID);

    public static final RegistryObject<BlockEntityType<FermentationBarrelBlockEntity>> FERMENTATION_BARREL_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("fermentation_barrel_block_entity",
                    () -> BlockEntityType.Builder.of(FermentationBarrelBlockEntity::new, AllBlocks.FERMENTATION_BARREL.get()).build(null));

    public static final RegistryObject<BlockEntityType<FreezerBlockEntity>> FREEZER_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("freezer_block_entity",
                    () -> BlockEntityType.Builder.of(FreezerBlockEntity::new, AllBlocks.FREEZER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
