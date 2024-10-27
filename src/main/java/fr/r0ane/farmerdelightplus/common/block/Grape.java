package fr.r0ane.farmerdelightplus.common.block;

import com.mojang.datafixers.TypeRewriteRule;
import fr.r0ane.farmerdelightplus.AllBlocks;
import fr.r0ane.farmerdelightplus.AllItems;
import fr.r0ane.farmerdelightplus.common.item.Food;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Overwrite;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModSounds;
import vectorwing.farmersdelight.common.tag.ModTags;

public class Grape extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    public static final BooleanProperty ROPELOGGED = BooleanProperty.create("ropelogged");
    public static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
    public static final int MAX_HEIGHT = 3;

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public Grape(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(this.getAgeProperty(), 0)).setValue(ROPELOGGED, false));
    }

    @Override
    protected ItemLike getBaseSeedId() {
        return AllItems.GRAPE.get();
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
        pBuilder.add(ROPELOGGED);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        int age = pState.getValue(AGE);
        if (age != getMaxAge() && pPlayer.getItemInHand(pHand).is(Items.BONE_MEAL)) {
            return InteractionResult.PASS;
        }
        if (age == getMaxAge()) {
            int count = pLevel.random.nextInt(1,3);
            popResource(pLevel, pPos, new ItemStack(AllItems.GRAPE.get(), count));

            pLevel.setBlockAndUpdate(pPos, pState.setValue(AGE, 0));
            pLevel.playSound(null, pPos, ModSounds.ITEM_TOMATO_PICK_FROM_BUSH.get(), SoundSource.BLOCKS, 1.0F, 0.8F + pLevel.random.nextFloat() * 0.4F);
            return InteractionResult.SUCCESS;
        }
        else {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pLevel.isAreaLoaded(pPos, 1)) {
            if (pLevel.getRawBrightness(pPos, 0) >= 9) {
                int age = this.getAge(pState);
                if (age < this.getMaxAge()) {
                    float speed = getGrowthSpeed(this, pLevel, pPos);
                    if (ForgeHooks.onCropsGrowPre(pLevel, pPos, pState, pRandom.nextInt((int)(25.0F / speed) + 1) == 0)) {
                        pLevel.setBlock(pPos, (BlockState)pState.setValue(this.getAgeProperty(), age + 1), 2);
                        ForgeHooks.onCropsGrowPost(pLevel, pPos, pState);
                    }
                }

                this.attemptRopeClimb(pLevel, pPos, pRandom);
            }

        }
    }

    @Override
    public void performBonemeal(ServerLevel pLevel, RandomSource pRandom, BlockPos pPos, BlockState pState) {
        this.attemptRopeClimb(pLevel, pPos, pRandom);
        super.performBonemeal(pLevel, pRandom, pPos, pState);
    }

    @Override
    public void growCrops(Level pLevel, BlockPos pPos, BlockState pState) {
        int i = this.getAge(pState) + this.getBonemealAgeIncrease(pLevel);
        int j = this.getMaxAge();
        if (i > j) {
            i = j;
        }

        pLevel.setBlock(pPos, this.getStateForAge(i).setValue(ROPELOGGED, pState.getValue(ROPELOGGED)), 2);
    }

    @Override
    protected int getBonemealAgeIncrease(Level pLevel) {
        return pLevel.random.nextInt(1, 3);
    }

    public void attemptRopeClimb(ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pRandom.nextFloat() < 0.3F) {
            BlockPos posAbove = pPos.above();
            BlockState stateAbove = pLevel.getBlockState(posAbove);
            if (stateAbove.is(ModBlocks.ROPE.get())) {
                int vineHeight;
                for (vineHeight = 1; pLevel.getBlockState(pPos.below(vineHeight)).is(AllBlocks.GRAPE.get()); ++vineHeight) {}

                if (vineHeight < 3) {
                    pLevel.setBlock(posAbove, AllBlocks.GRAPE.get().defaultBlockState().setValue(ROPELOGGED, true), 2);
                }
            }
        }
    }

    @Override
    public BlockState updateShape(BlockState pState, Direction pFacing, BlockState pFacingState, LevelAccessor pLevel, BlockPos pCurrentPos, BlockPos pFacingPos) {
        if (!pState.canSurvive(pLevel, pCurrentPos)) {
            pLevel.destroyBlock(pCurrentPos, true);
            if (pState.getValue(ROPELOGGED)) {
                return super.updateShape(ModBlocks.ROPE.get().defaultBlockState(), pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            } else {
                return super.updateShape(Blocks.AIR.defaultBlockState(), pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
            }
        }
        return super.updateShape(pState, pFacing, pFacingState, pLevel, pCurrentPos, pFacingPos);
    }

    @Override
    public void playerDestroy(Level pLevel, Player pPlayer, BlockPos pPos, BlockState pState, @Nullable BlockEntity pBlockEntity, ItemStack pTool) {
        super.playerDestroy(pLevel, pPlayer, pPos, pState, pBlockEntity, pTool);
        if (pState.getValue(ROPELOGGED)) {
            pLevel.setBlockAndUpdate(pPos, ModBlocks.ROPE.get().defaultBlockState());
        }
    }

    @Override
    protected boolean mayPlaceOn(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
        return pState.is(AllBlocks.GRAPE.get()) || pState.is(ModBlocks.RICH_SOIL_FARMLAND.get()) || pState.is(Blocks.FARMLAND);
    }

    @Override
    public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
        return state.getValue(ROPELOGGED);
    }
}
