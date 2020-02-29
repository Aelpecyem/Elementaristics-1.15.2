package de.aelpecyem.elementaristics.client.render.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.aelpecyem.elementaristics.common.entity.EntityPlayerDummy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.culling.ClippingHelperImpl;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.ArrowLayer;
import net.minecraft.client.renderer.entity.layers.BipedArmorLayer;
import net.minecraft.client.renderer.entity.layers.HeadLayer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public class RenderPlayerDummy extends LivingRenderer<EntityPlayerDummy, PlayerModel<EntityPlayerDummy>> {
    public RenderPlayerDummy(EntityRendererManager manager) {
        super(manager, new PlayerModel<>(0, false), 0.5F);
        this.addLayer(new BipedArmorLayer(this, new BipedModel(0.5F), new BipedModel(1.0F)));
        this.addLayer(new ArrowLayer(this));
        this.addLayer(new HeadLayer(this));
    }

    @Override
    public boolean shouldRender(EntityPlayerDummy livingEntity, ClippingHelperImpl camera, double camX, double camY, double camZ) {
        return livingEntity.getPlayer() != null && super.shouldRender(livingEntity, camera, camX, camY, camZ);
    }

    @Override
    public void render(EntityPlayerDummy playerDummy, float p_225623_2_, float p_225623_3_, MatrixStack p_225623_4_, IRenderTypeBuffer p_225623_5_, int p_225623_6_) {
        if (playerDummy.getPlayer() instanceof AbstractClientPlayerEntity){
            entityModel = new PlayerModel<>(0, !DefaultPlayerSkin.getSkinType(playerDummy.getPlayerUUID()).equals("default"));
            entityModel.isChild = false;
        }
        super.render(playerDummy, p_225623_2_, p_225623_3_, p_225623_4_, p_225623_5_, p_225623_6_);
    }

    @Nullable
    @Override
    public ResourceLocation getEntityTexture(EntityPlayerDummy playerDummyEntity) {
        if (playerDummyEntity.getPlayer() != null){
            Minecraft mc = Minecraft.getInstance();
            if (!(mc.getRenderViewEntity() instanceof AbstractClientPlayerEntity))
                return DefaultPlayerSkin.getDefaultSkinLegacy();
            return ((AbstractClientPlayerEntity) mc.getRenderViewEntity()).getLocationSkin();
        }
        return null;
    }

    @Override
    protected boolean canRenderName(EntityPlayerDummy entity) {
        return entity.hasCustomName();
    }
}
