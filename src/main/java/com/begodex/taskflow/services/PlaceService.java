package com.begodex.taskflow.services;

import com.begodex.taskflow.exceptions.EntityNotFoundException;
import com.begodex.taskflow.models.Place;
import com.begodex.taskflow.models.race.Race;
import com.begodex.taskflow.repositories.PlaceRepository;
import com.begodex.taskflow.repositories.RaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;


@Service
public class PlaceService {

    @Autowired
    PlaceRepository placeRepository;

    @Autowired
    RaceRepository raceRepository;

    public List<Place> getAllPlace() {

        return new ArrayList<>(placeRepository.findAll());

    }

    public Place getPlaceById(long placeId) {
        return placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place", placeId));
    }

    @Transactional
    public void saveOrUpdate(Place place) {

        placeRepository.save(place);

    }

    @Transactional
    public void delete(long placeId) {
        if (!placeRepository.existsById(placeId)) {
            throw new EntityNotFoundException("Place", placeId);
        }
        placeRepository.deleteById(placeId);
    }

    public ResponseEntity<List<Race>> findRacesByPlaceId(@PathVariable(value = "placeId") Long placeId) {

        if (!placeRepository.existsById(placeId)) {
            throw new EntityNotFoundException("Place", placeId);
        }

        List<Race> corridas = raceRepository.findByPlaceId(placeId);

        return ResponseEntity.ok(corridas);
    }

    @Transactional
    public boolean deleteRacesByPlaceId(Long placeId) {
        // se o local não existe, retorna false (nada feito)
        if (!placeRepository.existsById(placeId)) {
            throw new EntityNotFoundException("Place", placeId);
        }

        // deleta todas as taskflow ligadas ao placeId
        raceRepository.deleteByPlaceId(placeId);
        return true;
    }

}