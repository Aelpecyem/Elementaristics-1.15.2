package de.aelpecyem.elementaristics.data.generators;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.advancements.criterion.EnchantmentPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.MinMaxBounds;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.MatchTool;
import net.minecraft.world.storage.loot.conditions.SurvivesExplosion;
import net.minecraft.world.storage.loot.functions.CopyNbt;
import net.minecraft.world.storage.loot.functions.ExplosionDecay;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class BlockLootProvider implements IDataProvider{
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;
    private final Map<Block, Function<Block, LootTable.Builder>> functionTable = new HashMap<>();

    public BlockLootProvider(DataGenerator generator) {
        this.generator = generator;
        for (Block b : ForgeRegistries.BLOCKS) {
            if (!Constants.MOD_ID.equals(b.getRegistryName().getNamespace()))
                continue;
            if (b instanceof SlabBlock)
                functionTable.put(b, BlockLootProvider::genSlab);
        }
    }

    @Override
    public void act(DirectoryCache cache) throws IOException {
        Map<ResourceLocation, LootTable.Builder> tables = new HashMap<>();

        for (Block b : ForgeRegistries.BLOCKS) {
            if (!Constants.MOD_ID.equals(b.getRegistryName().getNamespace()))
                continue;
            Function<Block, LootTable.Builder> func = functionTable.getOrDefault(b, BlockLootProvider::genRegular);
            tables.put(b.getRegistryName(), func.apply(b));
        }

        for (Map.Entry<ResourceLocation, LootTable.Builder> e : tables.entrySet()) {
            Path path = getPath(generator.getOutputFolder(), e.getKey());
            IDataProvider.save(GSON, cache, LootTableManager.toJson(e.getValue().setParameterSet(LootParameterSets.BLOCK).build()), path);
        }
    }

    private static Path getPath(Path root, ResourceLocation id) {
        return root.resolve("data/" + id.getNamespace() + "/loot_tables/blocks/" + id.getPath() + ".json");
    }

    private static LootTable.Builder empty(Block b) {
        return LootTable.builder();
    }

    private static LootTable.Builder genCopyNbt(Block b, String... tags) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
        CopyNbt.Builder func = CopyNbt.func_215881_a(CopyNbt.Source.BLOCK_ENTITY);
        for (String tag : tags) {
            func = func.func_216056_a(tag, "BlockEntityTag." + tag);
        }
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(SurvivesExplosion.builder())
                .acceptFunction(func);
        return LootTable.builder().addLootPool(pool);
    }

    private static LootTable.Builder genSlab(Block b) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(b)
                .acceptFunction(SetCount.builder(ConstantRange.of(2))
                        .acceptCondition(BlockStateProperty.builder(b).func_227567_a_(StatePropertiesPredicate.Builder.create().exactMatch(SlabBlock.TYPE, SlabType.DOUBLE))))
                .acceptFunction(ExplosionDecay.builder());
        return LootTable.builder().addLootPool(LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry));
    }

    private static LootTable.Builder genSilkTouch(Block b, IItemProvider breakDrop) {
        ItemPredicate.Builder silkPred = ItemPredicate.Builder.create()
                .enchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.IntBound.atLeast(1)));
        LootEntry.Builder<?> silk = ItemLootEntry.builder(b)
                .acceptCondition(MatchTool.builder(silkPred));
        LootEntry.Builder<?> dirt = ItemLootEntry.builder(breakDrop)
                .acceptCondition(SurvivesExplosion.builder());
        LootEntry.Builder<?> entry = AlternativesLootEntry.builder(silk, dirt);
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry);
        return LootTable.builder().addLootPool(pool);
    }

    private static LootTable.Builder genRegular(Block b) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(SurvivesExplosion.builder());
        return LootTable.builder().addLootPool(pool);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Elementaristics block loot tables";
    }
}
