package com.university.alsamixer.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.*;

import com.university.alsamixer.models.SoundCard;
import com.university.alsamixer.models.Device;
import com.university.alsamixer.services.AlsaMixerVolumeService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/sound-cards")
public class AlsaMixerVolumeController {
    private final AlsaMixerVolumeService alsaMixerVolumeService;

    @Autowired
    public AlsaMixerVolumeController(AlsaMixerVolumeService alsaMixerVolumeService) {
        this.alsaMixerVolumeService = alsaMixerVolumeService;
    }

    @GetMapping
    public List<SoundCard> getSoundCards() {
        return this.alsaMixerVolumeService.getSoundCards();
    }

    @PutMapping(path = "/{ID}/{channel}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateSoundCardDeviceValue(@RequestBody Device device,
                                           @PathVariable(name = "ID") int soundCardID,
                                           @PathVariable(name = "channel") String channel) {
       this.alsaMixerVolumeService.updateSoundCardDeviceValue(soundCardID, channel, device);
    }

    @PutMapping(path = "/mute/{ID}/{channel}")
    public List<SoundCard> toggleSoundCardDeviceMuteMode(@RequestBody Device device,
                                                         @PathVariable(name = "ID") int soundCardID,
                                                         @PathVariable(name = "channel") String channel) {
        this.alsaMixerVolumeService.toggleSoundCardDevice(soundCardID, channel);
        return this.alsaMixerVolumeService.getSoundCards();
    }
}
