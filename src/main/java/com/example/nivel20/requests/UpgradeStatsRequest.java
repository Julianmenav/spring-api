
package com.example.nivel20.requests;

public class UpgradeStatsRequest {
    private int strengthUpgrade;
    private int magicUpgrade;
    private int staminaUpgrade;

    public int getStrengthUpgrade() {
        return strengthUpgrade;
    }

    public int getMagicUpgrade() {
        return magicUpgrade;
    }

    public int getStaminaUpgrade() {
        return staminaUpgrade;
    }

    public int totalPoints() { return strengthUpgrade + magicUpgrade + staminaUpgrade; }

    @Override
    public String toString() {
        return "UpgradeStatsRequest{" +
                "strengthUpgrade=" + strengthUpgrade +
                ", magicUpgrade=" + magicUpgrade +
                ", staminaUpgrade=" + staminaUpgrade +
                '}';
    }
}
