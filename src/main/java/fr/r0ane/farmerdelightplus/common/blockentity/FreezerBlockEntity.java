package fr.r0ane.farmerdelightplus.common.blockentity;

import fr.r0ane.farmerdelightplus.AllBlockEntity;
import fr.r0ane.farmerdelightplus.common.menu.FreezerMenu;
import fr.r0ane.farmerdelightplus.common.menu.FreezerScreen;
import fr.r0ane.farmerdelightplus.common.recipe.FreezerRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class FreezerBlockEntity extends BlockEntity implements MenuProvider {
    private final ItemStackHandler itemHandler = new ItemStackHandler(3);

    private static final int INPUT_SLOT = 0;
    private static final int ICE_SLOT = 1;
    private static final int OUTPUT_SLOT = 2;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int iceProgress = 0;
    private int maxProgress = 250;
    private int maxIceProgress = 125;

    public int getMaxIceProgress() {
        return maxIceProgress;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        if (cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }
        return super.getCapability(cap);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public FreezerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(AllBlockEntity.FREEZER_BLOCK_ENTITY.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> progress;
                    case 1 -> maxProgress;
                    case 2 -> iceProgress * 100000 + maxIceProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int i1) {
                switch (i) {
                    case 0 -> progress = i1;
                    case 1 -> maxProgress = i1;
                };
            }

            @Override
            public int getCount() {
                return 3;
            }
        };

    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i=0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.farmerdelightplus.freezer");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("freezer.progress", progress);
        pTag.putInt("freezer.iceProgress", iceProgress);
        pTag.putInt("freezer.maxIceProgress", maxIceProgress);

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("freezer.progress");
        iceProgress = pTag.getInt("freezer.iceProgress");
        maxIceProgress = pTag.getInt("freezer.maxIceProgress");
    }

    public void tick () {
        if(hasRecipe()) {
            if (getProgress() == 0) {
                setMaxProgress();
            }
            if (getIceProgress() == 0 && !level.getBiome(getBlockPos()).is(Tags.Biomes.IS_COLD)) {
                if (enhanceIceProgress()) {
                    consumeIce();
                }
            }
            if (getIceProgress() > 0 || level.getBiome(getBlockPos()).is(Tags.Biomes.IS_COLD)) {
                increaseCraftingProgress();
                setChanged(level, getBlockPos(), getBlockState());
                if (getProgress() == getMaxProgress()) {
                    craftItem();
                    resetProgress();
                }
            } else {
                resetProgress();
            }
        } else {
            resetProgress();
        }

        if (getIceProgress() > 0) {
            decreaseIceProgress();
        }
    }

    private void setMaxProgress() {
        Optional<FreezerRecipe> recipe = getCurrentRecipe();
        maxProgress = recipe.get().getMaxProgress();
    }

    public void craftItem() {
        Optional<FreezerRecipe> recipe = getCurrentRecipe();
        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        ItemStack inputSlot = itemHandler.getStackInSlot(INPUT_SLOT);
        ItemStack outputSlot = itemHandler.getStackInSlot(OUTPUT_SLOT);

        itemHandler.setStackInSlot(INPUT_SLOT, new ItemStack(inputSlot.getItem(), inputSlot.getCount() - 1));
        itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(result.getItem(), outputSlot.getCount() + 1));

        if (inputSlot.hasCraftingRemainingItem()) {
            if (inputSlot.getCount() == 1) {
                itemHandler.setStackInSlot(INPUT_SLOT, inputSlot.getCraftingRemainingItem());
            } else {
                Block.popResource(level, getBlockPos().above(), inputSlot.getCraftingRemainingItem());
            }
        }
    }

    public boolean hasRecipe() {
        Optional<FreezerRecipe> recipe = getCurrentRecipe();

        if (recipe.isEmpty()) {
            return false;
        }

        ItemStack result = recipe.get().getResultItem(getLevel().registryAccess());

        return recipe.isPresent() && canInsertAmountIntoOutputSlot(result.getCount()) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private Optional<FreezerRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(FreezerRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() || this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item);
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    public void increaseCraftingProgress() {
        progress++;
    }

    public void decreaseIceProgress() {
        if (!level.getBiome(getBlockPos()).is(Tags.Biomes.IS_COLD)) {
            iceProgress += -1;
        }
    }

    public boolean enhanceIceProgress() {
        ItemStack iceSlot = itemHandler.getStackInSlot(ICE_SLOT);
        iceProgress = iceSlot.is(Blocks.ICE.asItem()) ? 125 : iceProgress;
        iceProgress = iceSlot.is(Blocks.PACKED_ICE.asItem()) ? 1500 : iceProgress;
        iceProgress = iceSlot.is(Blocks.BLUE_ICE.asItem()) ? 20000 : iceProgress;
        maxIceProgress = iceProgress;
        return iceSlot.is(Blocks.ICE.asItem()) || iceSlot.is(Blocks.PACKED_ICE.asItem()) || iceSlot.is(Blocks.BLUE_ICE.asItem());
    }

    public void consumeIce() {
        ItemStack iceSlot = itemHandler.getStackInSlot(ICE_SLOT);
        itemHandler.setStackInSlot(ICE_SLOT, new ItemStack(iceSlot.getItem(), iceSlot.getCount() - 1));
    }

    public int getProgress() {
        return progress;
    }

    public int getIceProgress() {
        return iceProgress;
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    public void resetProgress() {
        progress = 0;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new FreezerMenu(i, inventory, this, this.data);
    }
}
