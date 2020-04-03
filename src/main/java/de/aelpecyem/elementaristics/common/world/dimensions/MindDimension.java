package de.aelpecyem.elementaristics.common.world.dimensions;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderSkybox;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.client.IRenderHandler;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;

public class MindDimension extends Dimension {
    public MindDimension(World world, DimensionType type) {
        super(world, type, 0F);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return new MindBiomeChunkGenerator(world, new MindBiomeProvider(new MindBiomeProviderSettings(world.getWorldInfo())), new MindGenerationSettings());
    }

    @Nullable
    @Override
    public IRenderHandler getCloudRenderer() {
        return super.getCloudRenderer();
    }

    @Nullable
    @Override
    public IRenderHandler getSkyRenderer() {
        return (i, v, clientWorld, minecraft) -> {
            //todo fancy shit here maybe
        };
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        return null;
    }

    @Override
    public int getActualHeight() {
        return 512;
    }

    @Override
    public SleepResult canSleepAt(PlayerEntity player, BlockPos pos) {
        return SleepResult.DENY;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0.5F;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public boolean hasSkyLight() {
        return true;
    }

    @Override
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return new Vec3d(0, 0, 0);
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }
}
