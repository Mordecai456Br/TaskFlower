package com.begodex.taskflow.controllers;

import com.begodex.taskflow.DTO.RaceRequestDTO;
import com.begodex.taskflow.models.Place;
import com.begodex.taskflow.models.race.Race;
import com.begodex.taskflow.services.PlaceService;
import com.begodex.taskflow.services.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("places")
public class PlaceController {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private RaceService raceService;


    @GetMapping
    public ResponseEntity<List<Place>> getAllPlace() {

        var places = placeService.getAllPlace();
        return ResponseEntity.ok(places);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Place> getPlace(@PathVariable("id") Long id) {

        var place = placeService.getPlaceById(id);
        return ResponseEntity.ok(place);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlace(@PathVariable("id") Long id) {

        placeService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @PostMapping
    public ResponseEntity<Long> savePlace(@RequestBody Place place) {

        placeService.saveOrUpdate(place);

        URI location = URI.create("/places/" + place.getId());
        return ResponseEntity.created(location).body(place.getId());
    }


    @PostMapping("/{placeId}/races")
    public ResponseEntity<Long> createRace(
            @PathVariable("placeId") Long placeId,
            @RequestBody RaceRequestDTO request) {

        Race saved = raceService.createRace(placeId, request);
        URI location = URI.create("/places/" + placeId + "/races/" + saved.getId());
        return ResponseEntity.created(location).body(saved.getId());
    }

    @GetMapping("/{placeId}/races")
    public ResponseEntity<List<Race>> findRacesByPlaceId(@PathVariable("placeId") Long placeId) {

        var races = raceService.findRacesByPlaceId(placeId);
        return ResponseEntity.ok(races.getBody());
    }


    @DeleteMapping("/{placeId}/races")
    public ResponseEntity<Void> deleteRacesByPlaceId(@PathVariable("placeId") Long placeId) {

        raceService.deleteRacesByPlaceId(placeId);
        return ResponseEntity.noContent().build();
    }
}
