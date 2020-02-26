package de.aelpecyem.elementaristics.data.generators;

import de.aelpecyem.elementaristics.lib.Constants;
import net.minecraft.block.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.client.model.generators.ModelFile;

import javax.annotation.Nonnull;

public class BlockstateProvider extends BlockStateProvider {
    public BlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Constants.MOD_ID, exFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Elementaristics Blockstates";
    }

    @Override
    protected void registerStatesAndModels() {
        Registry.BLOCK.stream().filter(b -> Constants.MOD_ID.equals(b.getRegistryName().getNamespace()))
                .forEach(b -> {
                    String name = b.getRegistryName().getPath();
                    if (b instanceof SlabBlock) {
                        ModelFile file = models().getExistingFile(new ResourceLocation(Constants.MOD_ID, "block/" + name));
                        ModelFile fullFile = models().getExistingFile(new ResourceLocation(Constants.MOD_ID, "block/" + name.substring(0, name.length() - "_slab".length())));
                        getVariantBuilder(b)
                                .partialState().with(SlabBlock.TYPE, SlabType.BOTTOM).setModels(new ConfiguredModel(file))
                                .partialState().with(SlabBlock.TYPE, SlabType.TOP).setModels(new ConfiguredModel(file, 180, 0, true))
                                .partialState().with(SlabBlock.TYPE, SlabType.DOUBLE).setModels(new ConfiguredModel(fullFile));
                    } else if (b instanceof StairsBlock) {
                        ModelFile stair = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name));
                        ModelFile inner = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_inner"));
                        ModelFile outer = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_outer"));
                        stairsBlock((StairsBlock) b, stair, inner, outer);
                    } else if (b instanceof WallBlock) {
                        ModelFile post = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_post"));
                        ModelFile side = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_side"));
                        wallBlock((WallBlock) b, post, side);
                    } else if (b instanceof FenceBlock) {
                        ModelFile post = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_post"));
                        ModelFile side = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_side"));
                        fourWayBlock((FenceBlock) b, post, side);
                    } else if (b instanceof FenceGateBlock) {
                        ModelFile gate = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name));
                        ModelFile gateOpen = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_open"));
                        ModelFile wall = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_wall"));
                        ModelFile wallOpen = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_wall_open"));
                        fenceGateBlock((FenceGateBlock) b, gate, gateOpen, wall, wallOpen);
                    } else if (b instanceof PaneBlock) {
                        ModelFile post = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_post"));
                        ModelFile side = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_side"));
                        ModelFile sideAlt = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_side_alt"));
                        ModelFile noSide = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_noside"));
                        ModelFile noSideAlt = models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name + "_noside_alt"));
                        paneBlock((PaneBlock) b, post, side, sideAlt, noSide, noSideAlt);
                    } else {
                        simpleBlock(b, models().getExistingFile(new ResourceLocation(Constants.MOD_ID,"block/" + name)));
                    }
                });
    }
}
