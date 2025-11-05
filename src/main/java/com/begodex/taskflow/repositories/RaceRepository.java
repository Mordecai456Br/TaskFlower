package com.begodex.taskflow.repositories;

import com.begodex.taskflow.models.race.Race;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;



@Repository
public interface RaceRepository extends JpaRepository<Race, Long> {

    List<Race> findByPlaceId(Long placeId);

    @Transactional
    void deleteByPlaceId(Long placeId);

        }
