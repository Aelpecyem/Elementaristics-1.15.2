package de.aelpecyem.elementaristics.common.capability.magan;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class MaganCapability implements IMaganCapability{
    public static final float MAX_MAGAN_BASE = 100;
    public static final float MAGAN_REGEN_BASE = 0.1F;

    private float magan;
    private Map<String, Float> expansions = new HashMap<>();
    private Map<String, Float> accelerations = new HashMap<>();
    private boolean dirty;
    private boolean veryDirty;

    @Override
    public float getMagan() {
        return magan;
    }

    @Override
    public void setMagan(float magan) {
        this.magan = Math.min(magan, getMaxMagan());
        if (magan < 0) this.magan = 0;
        setDirty(true);
    }

    @Override
    public boolean drainMagan(float magan) {
        boolean flag = getMagan() >= magan;
        setMagan(getMagan() - magan);
        return flag;
    }

    //returns if magan is full afterwards
    @Override
    public boolean fillMagan(float magan) {
        boolean flag = getMagan() + magan >= getMaxMagan();
        setMagan(getMagan() + magan);
        return flag;
    }

    @Override
    public float getMaxMagan() {
        float totalValue = MAX_MAGAN_BASE;
        for (Float f : expansions.values()){
            totalValue += f;
        }
        return totalValue;
    }

    @Override
    public float getMaganRegenRate() {
        float totalValue = MAGAN_REGEN_BASE;
        for (Float f : accelerations.values()){
            totalValue += f;
        }
        return totalValue;
    }

    @Override
    public void addExpansion(String name, float expansion) {
        expansions.put(name, expansion);
        setVeryDirty(true);
    }

    @Override
    public void addAcceleration(String name, float extraRegen) {
        accelerations.put(name, extraRegen);
        setVeryDirty(true);
    }

    @Override
    public void removeExpansion(String name) {
        expansions.remove(name);
        setVeryDirty(true);
    }

    @Override
    public void removeAcceleration(String name) {
        accelerations.remove(name);
        setVeryDirty(true);
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public boolean isVeryDirty() {
        return veryDirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public void setVeryDirty(boolean veryDirty) {
        this.veryDirty = veryDirty;
    }

    public Map<String, Float> getExpansions() {
        return expansions;
    }

    public Map<String, Float> getAccelerations() {
        return accelerations;
    }

    public static class Util {
        public static IMaganCapability getCapability(PlayerEntity player){
            return player.getCapability(Provider.MAGAN_CAPABILITY, null).orElse(null);
        }

        public static CompoundNBT writeNBT(IMaganCapability cap){
            CompoundNBT nbt = new CompoundNBT();
            nbt.putFloat("Magan", cap.getMagan());

            ListNBT expansionList = new ListNBT();
            nbt.put("Expansions", expansionList);
            cap.getExpansions().entrySet().stream().forEach(entry -> addNewCouple(entry, expansionList));

            ListNBT accelerationList = new ListNBT();
            nbt.put("Accelerations", accelerationList);
            cap.getExpansions().entrySet().stream().forEach(entry -> addNewCouple(entry, accelerationList));

            nbt.putBoolean("Dirty", cap.isDirty());
            nbt.putBoolean("VeryDirty", cap.isVeryDirty());
            return nbt;
        }

        public static IMaganCapability readNBT(IMaganCapability cap, CompoundNBT data){
            cap.setMagan(data.getFloat("Magan"));

            cap.getExpansions().clear();
            data.getList("Expansions", Constants.NBT.TAG_COMPOUND).forEach(s -> loadExpansionCouple(cap, s));

            cap.getAccelerations().clear();
            data.getList("Accelerations", Constants.NBT.TAG_COMPOUND).forEach(s -> loadAccelerationCouple(cap, s));

            cap.setDirty(data.getBoolean("Dirty"));
            cap.setVeryDirty(data.getBoolean("VeryDirty"));
            return cap;
        }


        private static void addNewCouple(Map.Entry<String, Float> entry, ListNBT list) {
            CompoundNBT couple = new CompoundNBT();
            couple.putString("Id", entry.getKey());
            couple.putFloat("Increase", entry.getValue());
            list.add(couple);
        }

        private static void loadExpansionCouple(IMaganCapability magan, INBT s) {
            CompoundNBT tag = (CompoundNBT) s;
            magan.addExpansion(tag.getString("Id"), tag.getFloat("Increase"));
        }

        private static void loadAccelerationCouple(IMaganCapability magan, INBT s) {
            CompoundNBT tag = (CompoundNBT) s;
            magan.addAcceleration(tag.getString("Id"), tag.getFloat("Increase"));
        }
    }

    public static class Storage implements Capability.IStorage<IMaganCapability>{
        @Nullable
        @Override
        public INBT writeNBT(Capability<IMaganCapability> capability, IMaganCapability instance, Direction direction) {
            return Util.writeNBT(instance);
        }

        @Override
        public void readNBT(Capability<IMaganCapability> capability, IMaganCapability instance, Direction direction, INBT inbt) {
            Util.readNBT(instance, (CompoundNBT) inbt);
        }
    }

    public static class Provider implements ICapabilitySerializable<INBT> {
        @CapabilityInject(IMaganCapability.class)
        public static final Capability<IMaganCapability> MAGAN_CAPABILITY = null;

        private LazyOptional<IMaganCapability> instance = LazyOptional.of(MAGAN_CAPABILITY::getDefaultInstance);

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction direction) {
            return capability == MAGAN_CAPABILITY ? instance.cast() : LazyOptional.empty();
        }

        @Override
        public INBT serializeNBT() {
            return MAGAN_CAPABILITY.getStorage().writeNBT(MAGAN_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null);
        }

        @Override
        public void deserializeNBT(INBT inbt) {
            MAGAN_CAPABILITY.getStorage().readNBT(MAGAN_CAPABILITY, this.instance.orElseThrow(() -> new IllegalArgumentException("LazyOptional must not be empty!")), null, inbt);
        }
    }
}
