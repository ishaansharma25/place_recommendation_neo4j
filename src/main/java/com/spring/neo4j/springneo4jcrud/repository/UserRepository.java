package com.spring.neo4j.springneo4jcrud.repository;

import com.spring.neo4j.springneo4jcrud.Models.RecommendationResult;
import com.spring.neo4j.springneo4jcrud.repository.node.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends Neo4jRepository<User, Long> {



    // Query to get recommendations based on common visited places
    @Query("MATCH (targetUser:User {name: $userId})-[:VISITED]->(commonPlace:Place)<-[:VISITED]-(otherUser:User)\n" +
            "WHERE targetUser <> otherUser\n" +
            "WITH targetUser, otherUser, collect(commonPlace) AS sharedPlaces\n" +
            "MATCH (otherUser)-[:VISITED]->(recommendedPlace:Place)\n" +
            "WHERE NOT (targetUser)-[:VISITED]->(recommendedPlace)\n" +
            "WITH recommendedPlace, count(DISTINCT otherUser) AS commonality\n" +
            "ORDER BY commonality DESC\n" +
            "WITH collect({place: recommendedPlace, commonality: commonality}) AS userRecommendations\n" +
            "UNWIND userRecommendations AS finalRecommendations\n" +
            "RETURN DISTINCT finalRecommendations.place AS place, finalRecommendations.commonality AS commonality\n" +
            "LIMIT 10\n")
    List<RecommendationResult> findRecommendationsBasedOnTaste(@Param("userId") String username);

//
//    @Query("MATCH (targetUser:User {id: $userId})-[:VISITED]->(commonPlace:Place)<-[:VISITED]-(otherUser:User) " +
//            "WHERE targetUser <> otherUser " +
//            "WITH targetUser, otherUser, collect(commonPlace) AS sharedPlaces " +
//            "MATCH (otherUser)-[:VISITED]->(recommendedPlace:Place) " +
//            "WHERE NOT (targetUser)-[:VISITED]->(recommendedPlace) " +
//            "WITH recommendedPlace, size(sharedPlaces) AS commonality " +
//            "ORDER BY commonality DESC " +
//            "WITH collect({place: recommendedPlace, rank: commonality}) AS userRecommendations " +
//            "UNWIND userRecommendations AS finalRecommendations " +
//            "RETURN DISTINCT finalRecommendations.place AS place, finalRecommendations.rank AS commonality " +
//            "LIMIT 10")
//    List<RecommendationResult> findRecommendationsBasedOnTaste(@Param("userId") Long userId);
//

    // Query to get the most popular places
    @Query("MATCH (p:Place)<-[:VISITED]-(u:User) " +
            "WITH p, count(u) AS visitCount " +
            "ORDER BY visitCount DESC " +
            "RETURN p AS place " +
            "LIMIT 10")
    List<RecommendationResult> findPopularPlaces();

    @Query("MATCH (u:User {name: $name}) RETURN u")
    Optional<User> findByName(String name);

    @Query("MATCH (u:User) WHERE id(u) = $id DETACH DELETE u")
    void deleteByUserId(Long id);
}
