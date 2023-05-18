package com.university.alsamixer.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public final class SoundCard {
    private int id;
    private String name;
    private List<Device> devices = new ArrayList<>();

    public void addDevice(Device device) {
        this.devices.add(device);
    }

    @Override
    public String toString() {
        return "Sound Card ID: " + this.id + ", Sound Card Name: " + this.name + "\n"
                + this.devices;
    }
}
