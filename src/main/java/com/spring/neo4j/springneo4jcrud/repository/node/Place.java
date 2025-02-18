package com.spring.neo4j.springneo4jcrud.repository.node;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

import org.springframework.data.neo4j.core.schema.Node;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Node("Place")  // This indicates that the Place class represents a Node of type "Place"
public class Place {

    @Id
    @GeneratedValue
    private Long placeId;
    private String name;
    public Place(String name){
        this.name = name;
    }
}

