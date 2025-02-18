package com.spring.neo4j.springneo4jcrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.neo4j.config.EnableNeo4jAuditing;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;

@SpringBootApplication
@EnableNeo4jAuditing
@EnableNeo4jRepositories(basePackages = "com.spring.neo4j.springneo4jcrud.repository")
public class SpringNeo4jCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringNeo4jCrudApplication.class, args);
	}

}
