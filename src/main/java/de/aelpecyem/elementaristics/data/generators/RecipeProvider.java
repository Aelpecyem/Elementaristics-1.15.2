package de.aelpecyem.elementaristics.data.generators;

import de.aelpecyem.elementaristics.lib.Constants;
import de.aelpecyem.elementaristics.reg.ModBlocks;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import java.util.function.Consumer;

public class RecipeProvider extends net.minecraft.data.RecipeProvider{
    public RecipeProvider(DataGenerator dataGenerator) {
        super(dataGenerator);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        registerMiscRecipes(consumer);
        registerConversions(consumer);
    }

    private void registerMiscRecipes(Consumer<IFinishedRecipe> consumer){
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.stone_enriched.get(), 8)
                .patternLine("xxx")
                .patternLine("x#x")
                .patternLine("xxx")
                .key('x', Blocks.COBBLESTONE)
                .key('#', Tags.Items.GEMS_QUARTZ)
                .setGroup(Constants.MOD_ID)
                .addCriterion("has_item", InventoryChangeTrigger.Instance.forItems(Items.QUARTZ))
                .addCriterion("has_block", InventoryChangeTrigger.Instance.forItems(Blocks.COBBLESTONE))
                .build(consumer);

        brick(ModBlocks.stonebricks_enriched.get(), ModBlocks.smooth_stone_enriched.get()).build(consumer);

        stairs(ModBlocks.stone_enriched_stairs.get(), ModBlocks.stone_enriched.get()).build(consumer);
        stairs(ModBlocks.stonebricks_enriched_stairs.get(), ModBlocks.stonebricks_enriched.get()).build(consumer);
        stairs(ModBlocks.smooth_stone_enriched_stairs.get(), ModBlocks.smooth_stone_enriched.get()).build(consumer);

        slabShape(ModBlocks.stone_enriched_slab.get(), ModBlocks.stone_enriched.get()).build(consumer);
        slabShape(ModBlocks.stonebricks_enriched_slab.get(), ModBlocks.stonebricks_enriched.get()).build(consumer);
        slabShape(ModBlocks.smooth_stone_enriched_slab.get(), ModBlocks.smooth_stone_enriched.get()).build(consumer);
    }

    private void registerConversions(Consumer<IFinishedRecipe> consumer){
        recombineSlab(consumer, ModBlocks.stone_enriched.get(), ModBlocks.stone_enriched_slab.get());
        recombineSlab(consumer, ModBlocks.stonebricks_enriched.get(), ModBlocks.stonebricks_enriched_slab.get());
        recombineSlab(consumer, ModBlocks.smooth_stone_enriched.get(), ModBlocks.smooth_stone_enriched_slab.get());
    }


    private ShapedRecipeBuilder compression(IItemProvider output, Tag<Item> input) {
        return ShapedRecipeBuilder.shapedRecipe(output)
                .setGroup(Constants.MOD_ID)
                .key('I', input)
                .patternLine("III")
                .patternLine("III")
                .patternLine("III")
                .addCriterion("has_item", hasItem(input));
    }

    private ShapedRecipeBuilder brick(IItemProvider output, IItemProvider input) {
        return ShapedRecipeBuilder.shapedRecipe(output, 4)
                .setGroup(Constants.MOD_ID)
                .addCriterion("has_item", hasItem(input))
                .key('Q', input)
                .patternLine("QQ")
                .patternLine("QQ");
    }

    private ShapedRecipeBuilder stairs(IItemProvider output, IItemProvider input) {
        return ShapedRecipeBuilder.shapedRecipe(output, 4)
                .addCriterion("has_item", hasItem(input))
                .setGroup(Constants.MOD_ID)
                .key('Q', input)
                .patternLine("  Q")
                .patternLine(" QQ")
                .patternLine("QQQ");
    }

    private ShapedRecipeBuilder slabShape(IItemProvider output, IItemProvider input) {
        return ShapedRecipeBuilder.shapedRecipe(output, 6)
                .addCriterion("has_item", hasItem(input))
                .setGroup(Constants.MOD_ID)
                .key('Q', input)
                .patternLine("QQQ");
    }

    private void deconstruct(Consumer<IFinishedRecipe> consumer, IItemProvider output, IItemProvider input, String name) {
        ShapelessRecipeBuilder.shapelessRecipe(output, 9)
                .setGroup(Constants.MOD_ID)
                .addCriterion("has_item", hasItem(output))
                .addIngredient(input)
                .build(consumer, new ResourceLocation(Constants.MOD_ID, "conversions/" + name));
    }

    private void deconstruct(Consumer<IFinishedRecipe> consumer, IItemProvider output, Tag<Item> input, String name) {
        ShapelessRecipeBuilder.shapelessRecipe(output, 9)
                .addCriterion("has_item", hasItem(output))
                .setGroup(Constants.MOD_ID)
                .addIngredient(input)
                .build(consumer, new ResourceLocation(Constants.MOD_ID, "conversions/" + name));
    }

    private void recombineSlab(Consumer<IFinishedRecipe> consumer, IItemProvider fullBlock, IItemProvider slab) {
        ShapedRecipeBuilder.shapedRecipe(fullBlock)
                .setGroup(Constants.MOD_ID)
                .key('Q', slab)
                .patternLine("Q")
                .patternLine("Q")
                .addCriterion("has_item", hasItem(fullBlock))
                .build(consumer, new ResourceLocation(Constants.MOD_ID, "slab_recombine/" + fullBlock.asItem().getRegistryName().getPath() + "_recombine"));
    }

    @Override
    public String getName() {
        return "Elementaristics crafting recipes";
    }
}
