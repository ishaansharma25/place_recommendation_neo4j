package com.spring.neo4j.springneo4jcrud.repository;

import com.spring.neo4j.springneo4jcrud.repository.node.Place;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlaceRepository extends Neo4jRepository<Place, Long> {
    @Query("MATCH (p:Place {name: $name}) RETURN p")
    Optional<Place> findByName(String name);
}

