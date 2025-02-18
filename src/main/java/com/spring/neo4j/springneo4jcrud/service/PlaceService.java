package com.spring.neo4j.springneo4jcrud.service;

import com.spring.neo4j.springneo4jcrud.repository.PlaceRepository;
import com.spring.neo4j.springneo4jcrud.repository.node.Place;
import org.springframework.stereotype.Service;

@Service
public class PlaceService {

    private PlaceRepository placeRepository;

    public Place saveOrUpdatePlace(Place place) {
        // Check if place with the same id already exists
        Place existingPlace = placeRepository.findById(place.getPlaceId()).orElse(null);
        if (existingPlace != null) {
            // If exists, update the name or other fields
            existingPlace.setName(place.getName());
            return placeRepository.save(existingPlace);
        } else {
            // If not, create a new place
            return placeRepository.save(place);
        }
    }
}

