package de.aelpecyem.elementaristics.reg;

import com.google.common.collect.ImmutableList;
import de.aelpecyem.elementaristics.common.world.biomes.DecadentQuagsBiome;
import de.aelpecyem.elementaristics.common.world.biomes.DreamyThicketBiome;
import de.aelpecyem.elementaristics.common.world.biomes.MindMeadowsBiome;
import de.aelpecyem.elementaristics.common.world.biomes.ModBiome;
import de.aelpecyem.elementaristics.common.world.dimensions.MindModDimension;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.MultipleRandomFeatureConfig;
import net.minecraft.world.gen.feature.MultipleWithChanceRandomFeatureConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModWorld {
    //TODO: continue the Mind later (see Roadmap)
    public static final ResourceLocation MIND_DIMENSION_ID = new ResourceLocation(Constants.MOD_ID, Constants.WorldNames.MIND_DIMENSION);

    @ObjectHolder(Constants.MOD_ID + ":" + Constants.WorldNames.MIND_DIMENSION) public static ModDimension MIND_DIMENSION;

    public static DimensionType MIND;

    @ObjectHolder(Constants.MOD_ID + ":" + Constants.WorldNames.MIND_MEADOWS) public static Biome MIND_MEADOWS;
    @ObjectHolder(Constants.MOD_ID + ":" + Constants.WorldNames.DREAMY_THICKET) public static Biome DREAMY_THICKET;
    @ObjectHolder(Constants.MOD_ID + ":" + Constants.WorldNames.DECADENT_QUAGS) public static Biome DECADENT_QUAGS;


    @SubscribeEvent
    public static void registerModDimensions(final RegistryEvent.Register<ModDimension> event) {
        event.getRegistry().register(new MindModDimension().setRegistryName(MIND_DIMENSION_ID));
    }

    @SubscribeEvent
    public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(new MindMeadowsBiome().setRegistryName(Constants.WorldNames.MIND_MEADOWS));
        event.getRegistry().register(new DreamyThicketBiome().setRegistryName(Constants.WorldNames.DREAMY_THICKET));
        event.getRegistry().register(new DecadentQuagsBiome().setRegistryName(Constants.WorldNames.DECADENT_QUAGS));
    }

    public static void addGrass(Biome biome){
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(ModBiome.GRASS_CONFIG));
    }

    public static void addStandardFlora(Biome biome){
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_RANDOM_SELECTOR.configure(new MultipleWithChanceRandomFeatureConfig(ImmutableList.of(Feature.field_227248_z_.configure(ModBiome.NEUROTIC_WEEDS_CONFIG)), 0)).createDecoratedFeature(Placement.COUNT_HEIGHTMAP_32.configure(new FrequencyConfig(5))));
    }

    public static void addDenseGrass(Biome biome){
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.field_227248_z_.configure(ModBiome.DENSE_GRASS_CONFIG).createDecoratedFeature(Placement.COUNT_HEIGHTMAP_DOUBLE.configure(new FrequencyConfig(25))));
    }
    public static void addDenseTrees(Biome biome){
        biome.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Feature.RANDOM_SELECTOR.configure(new MultipleRandomFeatureConfig(ImmutableList.of(Feature.HUGE_BROWN_MUSHROOM.configure(DefaultBiomeFeatures.HUGE_BROWN_MUSHROOM_CONFIG).withChance(0.025F), Feature.HUGE_RED_MUSHROOM.configure(DefaultBiomeFeatures.HUGE_RED_MUSHROOM_CONFIG).withChance(0.05F), Feature.DARK_OAK_TREE.configure(DefaultBiomeFeatures.DARK_OAK_TREE_CONFIG).withChance(0.6666667F), Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.BIRCH_TREE_CONFIG).withChance(0.2F), Feature.FANCY_TREE.configure(DefaultBiomeFeatures.FANCY_TREE_CONFIG).withChance(0.1F)), Feature.NORMAL_TREE.configure(DefaultBiomeFeatures.OAK_TREE_CONFIG))).createDecoratedFeature(Placement.DARK_OAK_TREE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    }
}
