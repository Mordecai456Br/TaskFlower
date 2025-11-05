package com.begodex.taskflow.services;

import com.begodex.taskflow.DTO.RaceRequestDTO;
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
public class RaceService {

    @Autowired
    RaceRepository raceRepository;

    @Autowired
    PlaceRepository placeRepository;

    public List<Race> getAllRaces() {

        return new ArrayList<>(raceRepository.findAll());

    }

    public Race getRacesById(Long raceId) {
        return raceRepository.findById(raceId)
                .orElseThrow(() -> new EntityNotFoundException("Race", raceId));
    }

    @Transactional
    public void delete(Long raceId) {

        if (!raceRepository.existsById(raceId)){
            throw new EntityNotFoundException("Race", raceId);
        }
        raceRepository.deleteById(raceId);

    }

    @Transactional
    public Race createRace(Long placeId, RaceRequestDTO request) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new EntityNotFoundException("Place", placeId));
        Race race = new Race();
        race.setName(request.getName());
        race.setDateRace(request.getDateRace());
        race.setPlace(place);
        return raceRepository.save(race);
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
        return true; // operação concluída
    }

}