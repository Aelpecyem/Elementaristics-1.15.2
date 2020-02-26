package de.aelpecyem.elementaristics.common.capability.magan;

import java.util.Map;

public interface IMaganCapability {
    float getMagan();
    void setMagan(float magan);

    boolean drainMagan(float magan);
    boolean fillMagan(float magan);

    float getMaxMagan();
    float getMaganRegenRate();

    void addExpansion(String name, float expansion);
    void addAcceleration(String name, float extraRegen);
    void removeExpansion(String name);
    void removeAcceleration(String name);

    boolean isDirty();
    boolean isVeryDirty();

    void setDirty(boolean dirty);
    void setVeryDirty(boolean veryDirty);

    Map<String, Float> getExpansions();

    Map<String, Float> getAccelerations();
}
