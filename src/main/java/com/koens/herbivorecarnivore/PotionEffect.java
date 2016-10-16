package com.koens.herbivorecarnivore;

import org.bukkit.potion.PotionEffectType;

public class PotionEffect {

    private PotionEffectType type;
    private int duration;
    private int level;

    public PotionEffect(PotionEffectType t, int d, int l) {
        this.type = t;
        this.duration = d;
        this.level = l;
    }

    public PotionEffectType getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public int getLevel() {
        return level;
    }
}
