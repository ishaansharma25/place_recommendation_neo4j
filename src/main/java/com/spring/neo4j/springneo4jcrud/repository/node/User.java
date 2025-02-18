package com.spring.neo4j.springneo4jcrud.repository.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("User")  // This indicates the Node type in Neo4j
public class User {

    @Id  // Indicates the primary key in Neo4j
    @GeneratedValue
    private Long userId;

    private String name;

    @Relationship(type = "VISITED")  // Indicates a relationship type in Neo4j
    private List<Place> places;  // Assuming Place is another @Node class
}
