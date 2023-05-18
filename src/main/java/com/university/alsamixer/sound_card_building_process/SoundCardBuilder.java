package com.university.alsamixer.sound_card_building_process;

import com.university.alsamixer.models.SoundCard;
import com.university.alsamixer.models.Device;

public class SoundCardBuilder {
    private SoundCard soundCard;

    public void reset() {
        this.soundCard = new SoundCard();
    }

    public SoundCardBuilder withIDAndName(String soundCardLine) {
        Integer soundCardID = this.getSoundCardID(soundCardLine);
        this.soundCard.setId(soundCardID);

        String soundCardNameSubLine = soundCardLine.split(":")[1];
        String soundCardName = soundCardNameSubLine.substring(1).split(" ")[0];
        this.soundCard.setName(soundCardName);

        return this;
    }

    public Integer getSoundCardID(String soundCardLine) {
        String soundCardIDSubLine = soundCardLine.split(":")[0];
        int soundCardID = Integer.parseInt(soundCardIDSubLine.substring(5));
        return soundCardID;
    }

    public SoundCardBuilder withDeviceName(String deviceLine) {
        String deviceName = deviceLine.split("'")[1];
        this.soundCard.addDevice(new Device(deviceName, 0.0));
        return this;
    }

    public SoundCardBuilder withDeviceValue(String deviceLine) {
        // The case of a stereo device whose (Mono:) line is empty,
        if(deviceLine.trim().length() <= 5)
            return this;

        // The case of a line starting with [Playback/Capture channels], we don't give a shit about it
        if(deviceLine.contains("Playback channels") || deviceLine.contains("Capture channels"))
            return this;

        // The case of a disabled device
        if(deviceLine.contains("Mono: Playback [on]") || deviceLine.contains("Mono: Playback [off]")
        || deviceLine.contains("Mono: Capture [on]") || deviceLine.contains("Mono: Capture [off]")
        || deviceLine.contains("Front Left: Playback [on]") || deviceLine.contains("Front Left: Playback [off]")
        || deviceLine.contains("Front Left: Capture [on]") || deviceLine.contains("Front Left: Capture [off]")
        || deviceLine.contains("Front Right: Playback [on]") || deviceLine.contains("Front Right: Playback [off]")
        || deviceLine.contains("Front Right: Capture [on]") || deviceLine.contains("Front Right: Capture [off]"))
            return this;

        // The case of a device whose current line contains the device value, whether it's mono or stereo
        String deviceValueSubLine = deviceLine.split(":")[1];

        // Getting the device we want to build whose name is already set
        int soundCardDevicesCount = this.soundCard.getDevices().size();
        // Returning if no device is found
        if(soundCardDevicesCount == 0)
            return this;

        Device currentDevice = this.soundCard.getDevices().get(soundCardDevicesCount - 1);

        // Extracting the device volume
        String deviceValueAsString = deviceValueSubLine.split("\\[")[1];
        StringBuilder deviceValueAsStringBuilder = new StringBuilder(deviceValueAsString.split("]")[0]);

        // deviceValueAsStringBuilder format is currently: <number>%
        // Deleting the %
        deviceValueAsStringBuilder.deleteCharAt(deviceValueAsStringBuilder.length() - 1);

        // We're left with the <number>, changing it from a String type to a numeric type (double)
        double deviceValue = Double.parseDouble(deviceValueAsStringBuilder.toString());

        // Setting the volume on the current device
        currentDevice.setVolume(deviceValue);

        // Extracting the mute mode
        String deviceMuteMode = deviceLine.split("\\[")[deviceLine.split("\\[").length - 1];

        // Setting the mute mode on the current device
        if(deviceMuteMode.contains("on"))
            // 1 -> not mute
            currentDevice.setMute(1);
        // 0 -> mute
        else if(deviceMuteMode.contains("off"))
            currentDevice.setMute(0);
        else
            // 2 -> not mutable
            currentDevice.setMute(2);

        return this;
    }

    public SoundCardBuilder withDeviceEnabledMode(String deviceLine) {
        if(this.soundCard == null)
            return this;

        // Getting the device we want to build whose name is already set
        int soundCardDevicesCount = this.soundCard.getDevices().size();
        // Returning if no device is found
        if(soundCardDevicesCount == 0)
            return this;

        Device currentDevice = this.soundCard.getDevices().get(soundCardDevicesCount - 1);

        if(deviceLine.contains("Limits")) {
            currentDevice.setEnabled(true);
        }
        else {
            currentDevice.setEnabled(false);
            currentDevice.setMute(2);
        }

        return this;
    }

    public SoundCard build() {
        return this.soundCard;
    }
}
