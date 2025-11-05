package com.begodex.taskflow.controllers;

import com.begodex.taskflow.DTO.RaceRequestDTO;
import com.begodex.taskflow.exceptions.EntityNotFoundException;
import com.begodex.taskflow.models.Place;
import com.begodex.taskflow.models.race.Race;
import com.begodex.taskflow.repositories.PlaceRepository;
import com.begodex.taskflow.repositories.RaceRepository;
import com.begodex.taskflow.services.PlaceService;
import com.begodex.taskflow.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("races")
public class RaceController {

    @Autowired
    RaceService raceService;

    @Autowired
    PlaceService placeService;

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    PlaceRepository placeRepository;


    @GetMapping
    public ResponseEntity<List<Race>> getAllRaces() {
        List<Race> races = raceService.getAllRaces();
        return ResponseEntity.ok(races);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRace(@PathVariable Long id) {
        Race race = raceService.getRacesById(id);
        return ResponseEntity.ok(race);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRaceById(@PathVariable("id") Long id) {
        boolean deleted = raceService.deleteRacesByPlaceId(id);
       return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<Long> createRace(@RequestBody RaceRequestDTO request) {
        Place place = placeRepository.findById(request.getPlaceId())
                .orElseThrow(() -> new EntityNotFoundException("Place", request.getPlaceId()));

        Race race = new Race();
        race.setName(request.getName());
        race.setDateRace(request.getDateRace());
        race.setPlace(place);

        Race savedRace = raceRepository.save(race);
        return ResponseEntity.ok(savedRace.getId());
    }



}