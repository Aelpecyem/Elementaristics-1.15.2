package de.aelpecyem.elementaristics.reg;

import de.aelpecyem.elementaristics.common.world.biomes.FieldsDecadenceBiome;
import de.aelpecyem.elementaristics.common.world.biomes.FieldsPassionBiome;
import de.aelpecyem.elementaristics.common.world.biomes.FieldsReasonBiome;
import de.aelpecyem.elementaristics.common.world.biomes.ModBiome;
import de.aelpecyem.elementaristics.common.world.dimensions.MindModDimension;
import de.aelpecyem.elementaristics.common.world.dimensions.carver.FlatSurfaceBuilder;
import de.aelpecyem.elementaristics.common.world.features.DappledTreeFeature;
import de.aelpecyem.elementaristics.common.world.features.MorningGloryFeature;
import de.aelpecyem.elementaristics.common.world.features.WeirdShapesFeature;
import de.aelpecyem.elementaristics.lib.Config;
import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static net.minecraftforge.common.BiomeDictionary.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModWorld {
    public static final DeferredRegister<Biome> BIOMES = new DeferredRegister<>(ForgeRegistries.BIOMES, Constants.MOD_ID);
    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = new DeferredRegister<>(ForgeRegistries.SURFACE_BUILDERS, Constants.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = new DeferredRegister<>(ForgeRegistries.FEATURES, Constants.MOD_ID);
    public static final DeferredRegister<ModDimension> DIMENSIONS = new DeferredRegister<>(ForgeRegistries.MOD_DIMENSIONS, Constants.MOD_ID);

    public static final ResourceLocation MIND_DIMENSION_ID = new ResourceLocation(Constants.MOD_ID, Constants.MIND_DIMENSION);
    public static DimensionType MIND;

    public static final RegistryObject<ModDimension> MIND_DIMENSION = DIMENSIONS.register("mind", () -> new MindModDimension());


    public static final RegistryObject<Feature<NoFeatureConfig>> MORNING_GLORY = FEATURES.register("morning_glory_feature", () -> new MorningGloryFeature<>(NoFeatureConfig::deserialize));
    public static final RegistryObject<Feature<BlockStateFeatureConfig>> WEIRD_SHAPES = FEATURES.register("weird_cubes", () -> new WeirdShapesFeature<>(BlockStateFeatureConfig::deserialize));
    public static final RegistryObject<Feature<HugeTreeFeatureConfig>> DAPPLED_TREES = FEATURES.register("dappled_trees", () -> new DappledTreeFeature<>(HugeTreeFeatureConfig::deserialize));

    public static final RegistryObject<Biome> FIELDS_REASON = BIOMES.register("fields_reason", () -> new FieldsReasonBiome());
    public static final RegistryObject<Biome> FIELDS_PASSION = BIOMES.register("fields_passion", () -> new FieldsPassionBiome());
    public static final RegistryObject<Biome> FIELDS_DECADENCE = BIOMES.register("fields_decadence", () -> new FieldsDecadenceBiome());

    public static final SurfaceBuilder<SurfaceBuilderConfig> s_flat = new FlatSurfaceBuilder(SurfaceBuilderConfig::deserialize);
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> FLAT = SURFACE_BUILDERS.register("flat", () -> s_flat);



    public static void addBiomeFeatures(){
        Biomes.DARK_FOREST_HILLS.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MORNING_GLORY.get().configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.CHANCE_TOP_SOLID_HEIGHTMAP.configure(new ChanceConfig(Config.MORNING_GLORY_FREQUENCY.get()))));
        Biomes.DARK_FOREST.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, MORNING_GLORY.get().configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.CHANCE_TOP_SOLID_HEIGHTMAP.configure(new ChanceConfig(Config.MORNING_GLORY_FREQUENCY.get()))));

        for (Biome biome : ForgeRegistries.BIOMES.getValues()) {
            if (biome instanceof ModBiome) {
                ((ModBiome)biome).addFeatures();
            }
        }
    }

    public static void addBiomeTypes(){
        addTypes(FIELDS_REASON.get(), Type.COLD);
        addTypes(FIELDS_PASSION.get(), Type.MAGICAL);
        addTypes(FIELDS_DECADENCE.get(), Type.WET, Type.SPOOKY);
    }
}
