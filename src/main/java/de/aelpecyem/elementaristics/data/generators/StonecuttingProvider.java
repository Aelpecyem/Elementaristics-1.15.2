package de.aelpecyem.elementaristics.data.generators;

import com.google.gson.JsonObject;
import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class StonecuttingProvider extends RecipeProvider {
    public StonecuttingProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        consumer.accept(stonecutting(ModBlocks.smooth_stone_enriched, ModBlocks.stonebricks_enriched));
        consumer.accept(stonecutting(ModBlocks.stone_enriched, ModBlocks.stone_enriched_slab, 2));
        consumer.accept(stonecutting(ModBlocks.stone_enriched, ModBlocks.stone_enriched_stairs));
        consumer.accept(stonecutting(ModBlocks.stonebricks_enriched, ModBlocks.stonebricks_enriched_slab, 2));
        consumer.accept(stonecutting(ModBlocks.stonebricks_enriched, ModBlocks.stonebricks_enriched_stairs));
        consumer.accept(stonecutting(ModBlocks.smooth_stone_enriched, ModBlocks.smooth_stone_enriched_slab, 2));
        consumer.accept(stonecutting(ModBlocks.smooth_stone_enriched, ModBlocks.smooth_stone_enriched_stairs));
    }

    @Nonnull
    @Override
    public String getName() {
        return "Elementaristics stonecutting recipes";
    }

    private static ResourceLocation idFor(IItemProvider a, IItemProvider b) {
        return new ResourceLocation(Constants.MOD_ID,"stonecutting/" + a.asItem().getRegistryName().getPath() + "_to_" + b.asItem().getRegistryName().getPath());
    }

    private static IFinishedRecipe stonecutting(IItemProvider input, IItemProvider output) {
        return stonecutting(input, output, 1);
    }

    private static IFinishedRecipe stonecutting(IItemProvider input, IItemProvider output, int count) {
        return new Result(idFor(input, output), IRecipeSerializer.STONECUTTING, Ingredient.fromItems(input), output.asItem(), count);
    }

    private static IFinishedRecipe azulejoStonecutting(List<? extends IItemProvider> inputs, IItemProvider output) {
        Ingredient input = Ingredient.fromItems(inputs.stream().filter(obj -> output != obj).toArray(IItemProvider[]::new));
        return new Result(new ResourceLocation(Constants.MOD_ID, "stonecutting/" + output.asItem().getRegistryName().getPath()), IRecipeSerializer.STONECUTTING, input, output.asItem(), 1);
    }

    // Wrapper without advancements
    public static class Result extends SingleItemRecipeBuilder.Result {
        public Result(ResourceLocation id, IRecipeSerializer<?> serializer, Ingredient input, Item result, int count) {
            super(id, serializer, "", input, result, count, null, null);
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }
}
