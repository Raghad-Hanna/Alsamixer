package com.university.alsamixer.sound_card_building_process;

import java.util.HashSet;
import java.util.Set;

import com.university.alsamixer.models.SoundCard;

public class SoundCardDirector {
    private SoundCardBuilder soundCardBuilder;
    private final Set<Integer> uniqueSoundCardsIDs;

    public SoundCardDirector(SoundCardBuilder soundCardBuilder) {
        this.uniqueSoundCardsIDs = new HashSet<>();
        this.soundCardBuilder = soundCardBuilder;
    }

    public SoundCard directSoundCardBasicDataBuildingProcess(String soundCardLine) {
        if(soundCardLine.contains("card")) {
            Integer soundCardID = this.soundCardBuilder.getSoundCardID(soundCardLine);
            boolean unique = this.uniqueSoundCardsIDs.add(soundCardID);
            if (unique) {
                this.soundCardBuilder.reset();
                SoundCard soundCard = this.soundCardBuilder.withIDAndName(soundCardLine).build();
                return soundCard;
            }
        }
        return null;
    }

    public void directSoundCardDevicesBuildingProcess(String deviceLine) {
        if(deviceLine.contains("Simple mixer control")) {
            this.soundCardBuilder.withDeviceName(deviceLine);
        }
        else if(deviceLine.contains("Mono") || deviceLine.contains("Front Left") || deviceLine.contains("Front Right")) {
            this.soundCardBuilder.withDeviceValue(deviceLine);
        }
        else // we don't know if the Limits line exists or not
            // so we must pass every other line to determine the device enable mode
            this.soundCardBuilder.withDeviceEnabledMode(deviceLine);
    }
}
