package com.university.alsamixer.models;

import lombok.Data;

@Data
public class Device {
    private String name;
    private Double volume = -1D;
    private boolean enabled;
    // 0 -> mute
    // 1 -> not mute
    // 2 -> not mutable
    private int mute;

    public Device(String name, Double volume) {
        this.name = name;
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "Device Name: " + this.name + ", Device Volume: " + this.volume
                + ", Device Is Muted: " + this.mute + "\n";
    }
}
