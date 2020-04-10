package de.aelpecyem.elementaristics.common.entity;

import de.aelpecyem.elementaristics.reg.ModEntities;
import de.aelpecyem.elementaristics.reg.ModPotions;
import de.aelpecyem.elementaristics.reg.ModWorld;
import net.minecraft.command.impl.ForceLoadCommand;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.server.management.PreYggdrasilConverter;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.ChunkLoader;
import net.minecraft.world.chunk.storage.ChunkLoaderUtil;
import net.minecraft.world.server.ChunkManager;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityPlayerDummy extends CreatureEntity {
    private static final DataParameter<String> PLAYER_UUID = EntityDataManager.createKey(EntityPlayerDummy.class, DataSerializers.STRING);
    public Direction bedDirection = Direction.EAST;
    public EntityPlayerDummy(World world) {
        super(ModEntities.PLAYER_DUMMY.get(), world);
    }


    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PLAYER_UUID, "");
    }

    @Override
    public void tick() {
        if (isServerWorld()){
            ((ServerWorld)world).getChunkProvider().registerTicket(TicketType.PLAYER, new ChunkPos(getPosition()), 10, new ChunkPos(getPosition()));
        }
        if (world.isRemote) {
            System.out.println(getActivePotionEffect(ModPotions.INTOXICATED.get()));
            if (getPlayer() == null) {
                System.out.println("mama mia");
                remove(false);
            } else if (getPlayer().dimension == ModWorld.MIND && getActivePotionEffect(ModPotions.INTOXICATED.get()) != null) {
                setPose(Pose.SLEEPING);
            } else {
                System.out.println("oofio");
                remove(false);
            }
        }
        super.tick();
    }

    @Nullable
    @Override
    public Direction getBedDirection() {
        return bedDirection; //this will change later
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (getPlayer() != null){
            bringPlayerBack();
        }
        return super.attackEntityFrom(source, amount);
    }

    public void bringPlayerBack(){
        PlayerEntity player = getPlayer();
        if (player.dimension != dimension) player.dimension = dimension;
        player.setPositionAndRotation(getPositionVec().x, getPositionVec().y, getPositionVec().z, rotationYaw, rotationPitch);
        player.setHealth(getHealth());
        remove();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
    }

    public UUID getPlayerUUID() {
        return this.dataManager.get(PLAYER_UUID).isEmpty() ? null : UUID.fromString(dataManager.get(PLAYER_UUID));
    }

    public void setPlayerUUID(UUID uuid){
        this.dataManager.set(PLAYER_UUID, uuid != null ? uuid.toString() : "");
    }

    public PlayerEntity getPlayer(){
        return getPlayerUUID() != null ? world.getPlayerByUuid(getPlayerUUID()) : null;
    }

    public void setPlayer(PlayerEntity player){
        if (player == null) setPlayerUUID(null);

        this.dataManager.set(PLAYER_UUID, player.getUniqueID().toString());
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        if (this.getPlayerUUID() == null) {
            compound.putString("OwnerUUID", "");
        } else {
            compound.putString("OwnerUUID", this.getPlayerUUID().toString());
        }
        compound.putInt("BedDirection", bedDirection.getIndex());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        String s;
        if (compound.contains("OwnerUUID", 8)) {
            s = compound.getString("OwnerUUID");
        } else {
            String s1 = compound.getString("Owner");
            s = PreYggdrasilConverter.convertMobOwnerIfNeeded(this.getServer(), s1);
        }
        if (!s.isEmpty()) {
            this.setPlayerUUID(UUID.fromString(s));
        }
        bedDirection = Direction.byIndex(compound.getInt("BedDirection"));
    }

    public static void createDummyAndSendPlayerAway(World world, PlayerEntity player){
        EffectInstance playerEffect = player.getActivePotionEffect(ModPotions.INTOXICATED.get());
        if (playerEffect != null) {
            EffectInstance effect = new EffectInstance(ModPotions.INTOXICATED.get(), playerEffect.getDuration() * (playerEffect.getAmplifier() + 1), 0, true, true);
            player.removeActivePotionEffect(ModPotions.INTOXICATED.get());
            EntityPlayerDummy dummy = new EntityPlayerDummy(player.world);
            dummy.bedDirection = player.getBedDirection();
            dummy.setPosition(player.getPositionVec().x, player.getPositionVec().y, player.getPositionVec().z);
            player.changeDimension(ModWorld.MIND);
            dummy.setPlayer(player);
            dummy.addPotionEffect(effect);
            world.addEntity(dummy);
            if (dummy.isServerWorld()){
                ((ServerWorld)dummy.world).forceChunk(dummy.chunkCoordX, dummy.chunkCoordZ, true);// .getChunkProvider().registerTicket(TicketType.FORCED, new ChunkPos(dummy.getPosition()), 0, new ChunkPos(dummy.getPosition()));
            }
        }
    }
}
