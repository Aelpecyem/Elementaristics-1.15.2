package de.aelpecyem.elementaristics.common.world.dimensions.layer;

import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.LongFunction;
import java.util.function.Supplier;

//Credits to Androssa; taken from https://github.com/Andromander/Gaia-Dimension/blob/1.15.x/src/main/java/androsa/gaiadimension/world/layer/GaiaLayerUtil.java
public class LayerCreator {
    private static final List<LazyInt> CACHES = new ArrayList<>();
    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> makeLayers(LongFunction<C> contextFactory) {
        IAreaFactory<T> biomes = new MindLayer().apply(contextFactory.apply(1));
        biomes = ZoomLayer.FUZZY.apply(contextFactory.apply(1000), biomes);
        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1001), biomes);
        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1002), biomes);
        biomes = ZoomLayer.FUZZY.apply(contextFactory.apply(1003), biomes);
        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1004), biomes);
        biomes = ZoomLayer.NORMAL.apply(contextFactory.apply(1005), biomes);
        biomes = SmoothLayer.INSTANCE.apply(contextFactory.apply(1006), biomes);
        return biomes;
    }

    public static Layer makeLayers(long seed) {
        IAreaFactory<LazyArea> areaFactory = makeLayers((contextSeed) -> new LazyAreaLayerContext(25, seed, contextSeed));
        return new Layer(areaFactory);
    }

    public static class MindLayer implements IAreaTransformer0 {

        private static final int SPECIAL_BIOME_CHANCE = 12;
        protected LazyInt[] commonBiomes = new LazyInt[]{
                new LazyInt(() -> Registry.BIOME.getId(ModWorld.MIND_MEADOWS)),
                new LazyInt(() -> Registry.BIOME.getId(ModWorld.DREAMY_THICKET))
        };
        protected LazyInt[] rareBiomes = (new LazyInt[]{
                new LazyInt(() -> Registry.BIOME.getId(ModWorld.DECADENT_QUAGS))
        });

        public MindLayer() { }

        @Override
        public int apply(INoiseRandom iNoiseRandom, int rand1, int rand2) {
            if (iNoiseRandom.random(SPECIAL_BIOME_CHANCE) == 0) {
                return rareBiomes[iNoiseRandom.random(rareBiomes.length)].getAsInt();
            }
            else {
                return commonBiomes[iNoiseRandom.random(commonBiomes.length)].getAsInt();
            }
        }
    }

    public static class LazyInt implements IntSupplier {

        private final IntSupplier generator;
        private volatile int value;
        private volatile boolean resolved = false;

        public LazyInt(IntSupplier intSupplier) {
            generator = intSupplier;
        }

        public LazyInt(Supplier<Integer> intSupplier) {
            this((IntSupplier)intSupplier::get);
        }

        @Override
        public int getAsInt() {
            if (!resolved) {
                synchronized (this) {
                    if (!resolved) {
                        value = generator.getAsInt();
                        resolved = true;
                    }
                }
            }
            return value;
        }

        public synchronized void invalidate() {
            resolved = false;
        }
    }
}
