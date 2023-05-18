package com.university.alsamixer.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.university.alsamixer.models.SoundCard;
import com.university.alsamixer.models.Device;
import com.university.alsamixer.amixer_utils.*;
import com.university.alsamixer.sound_card_building_process.SoundCardBuilder;
import com.university.alsamixer.sound_card_building_process.SoundCardDirector;

@Service
public class AlsaMixerVolumeService {
    private final PlatformHelper platformHelper;
    private SoundCardDirector soundCardDirector;

    @Autowired
    public AlsaMixerVolumeService(PlatformHelper platformHelper) {
        this.platformHelper = platformHelper;
    }

    public List<SoundCard> getSoundCards() {
        this.soundCardDirector = new SoundCardDirector(new SoundCardBuilder());
        List<SoundCard> soundCards = new ArrayList<>();

        String soundCardsCommand = AMixerCommands.getSoundCards();
        String soundCardsCommandOutput = this.platformHelper.executeAMixerCommand(soundCardsCommand);

        String[] soundCardLines = soundCardsCommandOutput.split("\n");
        for(String soundCardLine: soundCardLines) {
            SoundCard soundCard = this.soundCardDirector.directSoundCardBasicDataBuildingProcess(soundCardLine);
            if(soundCard != null) {
                soundCards.add(soundCard);

                String soundCardDevicesCommand =
                        AMixerCommands.getSoundCardDevices(soundCard.getId());
                String soundCardDevicesCommandOutput =
                        this.platformHelper.executeAMixerCommand(soundCardDevicesCommand);

                String[] deviceLines = soundCardDevicesCommandOutput.split("\n");
                for(String deviceLine: deviceLines) {
                    this.soundCardDirector.directSoundCardDevicesBuildingProcess(deviceLine);
                }
            }
        }
        return soundCards;
    }

    public void updateSoundCardDeviceValue(int soundCardID, String channel, Device device) {
        String soundCardDeviceSettingCommand = AMixerCommands.setSoundCardDeviceValue(soundCardID,
                channel, device.getVolume());

        this.platformHelper.executeAMixerCommand(soundCardDeviceSettingCommand);
    }

    public void toggleSoundCardDevice(int soundCardID, String channel) {
        String soundCardDeviceToggleCommand = AMixerCommands.toggleSoundCardDevice(soundCardID, channel);

        this.platformHelper.executeAMixerCommand(soundCardDeviceToggleCommand);
    }
}
