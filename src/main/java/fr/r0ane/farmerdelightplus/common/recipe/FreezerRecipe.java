package fr.r0ane.farmerdelightplus.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.r0ane.farmerdelightplus.Main;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class FreezerRecipe implements Recipe<SimpleContainer> {
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final int maxProgress;
    private final ResourceLocation id;

    public FreezerRecipe(NonNullList<Ingredient> inputItems, ItemStack output, int maxProgress, ResourceLocation id) {
        this.inputItems = inputItems;
        this.output = output;
        this.maxProgress = maxProgress;
        this.id = id;
    }

    @Override
    public boolean matches(SimpleContainer simpleContainer, Level level) {
        if (level.isClientSide()) {
            return false;
        }

        return inputItems.get(0).test(simpleContainer.getItem(0));
    }

    @Override
    public ItemStack assemble(SimpleContainer simpleContainer, RegistryAccess registryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int i1) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return output.copy();
    }

    public int getMaxProgress() {
        return maxProgress;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static final class Type implements RecipeType<FreezerRecipe> {
        public static final Type INSTANCE = new Type();
        public static final String ID = "freezer";
    }

    public static final class Serializer implements RecipeSerializer<FreezerRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "freezer");

        @Override
        public FreezerRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(jsonObject, "result"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(jsonObject, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(1, Ingredient.EMPTY);

            for (int i = 0; i < ingredients.size(); i++) {
               inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int maxProgress = GsonHelper.getAsInt(jsonObject, "cooking_time");

            return new FreezerRecipe(inputs, output, maxProgress, resourceLocation);
        }

        @Override
        public @Nullable FreezerRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(friendlyByteBuf.readInt(), Ingredient.EMPTY);

            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(friendlyByteBuf));
            }

            ItemStack output = friendlyByteBuf.readItem();

            int maxProgress = friendlyByteBuf.readInt();

            return new FreezerRecipe(inputs, output, maxProgress, resourceLocation);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, FreezerRecipe freezerRecipe) {
            friendlyByteBuf.writeInt(freezerRecipe.getIngredients().size());
            friendlyByteBuf.writeInt(freezerRecipe.getMaxProgress());

            for (Ingredient ingredient : freezerRecipe.getIngredients()) {
                ingredient.toNetwork(friendlyByteBuf);
            }

            friendlyByteBuf.writeItemStack(freezerRecipe.getResultItem(null), false);
        }
    }
}
