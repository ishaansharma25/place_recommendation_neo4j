package com.spring.neo4j.springneo4jcrud.service;

import com.spring.neo4j.springneo4jcrud.Models.RecommendationResult;
import com.spring.neo4j.springneo4jcrud.repository.PlaceRepository;
import com.spring.neo4j.springneo4jcrud.repository.UserRepository;
import com.spring.neo4j.springneo4jcrud.repository.node.Place;
import com.spring.neo4j.springneo4jcrud.repository.node.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;


    public List<RecommendationResult> recommendPlaces(String username) {
        // Fetch ranked recommendations based on the target user's taste
        List<RecommendationResult> recommendations = userRepository.findRecommendationsBasedOnTaste(username);

        // Extract and return the recommended places along with their commonality scores
        return recommendations.stream()
                .map(rec -> new RecommendationResult(rec.getPlace(), rec.getCommonality()))
                .collect(Collectors.toList());
    }

}

