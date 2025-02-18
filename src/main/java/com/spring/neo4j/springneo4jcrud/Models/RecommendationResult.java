package com.spring.neo4j.springneo4jcrud.Models;

import com.spring.neo4j.springneo4jcrud.repository.node.Place;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationResult {
    private Place place;        // Recommended place
    private Double commonality;    // Number of shared places
}

