package com.begodex.taskflow.repositories;

import com.begodex.taskflow.models.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository

public interface PlaceRepository extends JpaRepository<Place, Long> {

}
