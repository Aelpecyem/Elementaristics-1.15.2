package de.aelpecyem.elementaristics.data.generators;

import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModBlocks;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;

import java.util.function.Consumer;

public class SmeltingProvider extends net.minecraft.data.RecipeProvider{
    public SmeltingProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.blastingRecipe(Ingredient.fromItems(ModBlocks.stone_enriched.get()), ModBlocks.smooth_stone_enriched.get(), 0.1f, 200)
                .addCriterion("has_item", hasItem(ModBlocks.stone_enriched.get()))
                .build(consumer, new ResourceLocation(Constants.MOD_ID, "smelting/" + "stone_enriched"));
    }

    @Override
    public String getName() {
        return "Elementaristics smelting recipes";
    }
}
