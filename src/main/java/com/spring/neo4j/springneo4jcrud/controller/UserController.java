package com.spring.neo4j.springneo4jcrud.controller;

import com.spring.neo4j.springneo4jcrud.Models.RecommendationResult;
import com.spring.neo4j.springneo4jcrud.repository.node.Place;
import com.spring.neo4j.springneo4jcrud.repository.node.User;
import com.spring.neo4j.springneo4jcrud.service.RecommendationService;
import com.spring.neo4j.springneo4jcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;
    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping

    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @PostMapping("/addBulkUsersFromS3")
    public String addBulkUsersFrmS3() {
        return userService.addUsersFromS3();
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/recommendation/{username}")
    public List<RecommendationResult> getRecommendations(@PathVariable String username) {
        try {
            // Fetch recommendations for the given user ID
            List<RecommendationResult> recommendedPlaces = recommendationService.recommendPlaces(username);

            // If no places are found, return an empty list or a not found status
            if (recommendedPlaces.isEmpty()) {
                return recommendedPlaces;  // Or, you could throw a 404 Not Found response
            }

            return recommendedPlaces;
        } catch (Exception e) {
            // Log the exception (optional) and return an appropriate response
            // Returning 400 Bad Request for any internal error
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error fetching recommendations", e);
        }
    }
}
