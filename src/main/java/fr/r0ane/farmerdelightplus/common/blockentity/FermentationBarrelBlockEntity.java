package fr.r0ane.farmerdelightplus.common.blockentity;

import fr.r0ane.farmerdelightplus.AllBlockEntity;
import fr.r0ane.farmerdelightplus.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class FermentationBarrelBlockEntity extends BlockEntity {
    private int quantityA = 0;
    private int quantityB = 0;
    private String type;

    public FermentationBarrelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(AllBlockEntity.FERMENTATION_BARREL_BLOCK_ENTITY.get(), pPos, pBlockState);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        CompoundTag data = pTag.getCompound(Main.MODID);
        this.quantityA = data.getInt("QuantityA");
        this.quantityB = data.getInt("QuantityB");
        this.type = data.getString("Type");
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);

        var data = new CompoundTag();
        data.putInt("QuantityA", this.quantityA);
        data.putInt("QuantityB", this.quantityB);
        data.putString("Type", this.type);
        pTag.put(Main.MODID, data);
    }

    public void tick () {
        if (quantityA > 0 && quantityB < 5000 && level.random.nextFloat() < 1) {
            DecrementA(1);
            IncrementB(1);
        }
    }

    public void IncrementA (int n) {
        this.quantityA += n;
        setChanged();
    }

    public void IncrementB (int n) {
        this.quantityB += n;
        setChanged();
    }

    public void DecrementA (int n) {
        this.quantityA += -n;
        setChanged();
    }

    public void DecrementB (int n) {
        this.quantityB += -n;
        setChanged();
    }

    public int getQuantityA () {
        return this.quantityA;
    }

    public int getQuantityB () {
        return this.quantityB;
    }

    public String getHisType () {
        return this.type;
    }

    public void setHisType (String type) {
        this.type = type;
    }
}
