package com.university.alsamixer.amixer_utils;

public final class AMixerCommands {
    public static String getSoundCards() {
        return "aplay -l";
    }

    public static String getSoundCardDevices(int soundCardID) {
        return "amixer -c " + soundCardID;
    }

    public static String setSoundCardDeviceValue(int soundCardID,
                                                        String deviceName, double deviceValue) {
        return "amixer -c " + soundCardID + " set " + "'" + deviceName + "'" + " " + deviceValue + "%";
    }

    public static String toggleSoundCardDevice(int soundCardID, String deviceName) {
        return "amixer -c " + soundCardID + " set " + "'" + deviceName + "'" + " toggle";
    }
}
