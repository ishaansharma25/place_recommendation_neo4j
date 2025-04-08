PlaceIQ: Docker Setup Guide
This guide explains how to set up and run the PlaceIQ application using Docker and Docker Compose. Follow the steps below to create and launch Neo4j and Spring Boot instances.
Prerequisites
Ensure the following are installed on your system:
•	Docker: Install Docker
•	Docker Compose: Install Docker Compose
Step 1: Clone the Repository
	Code is in the folder present along with docker-compose.yml file.
Step 2: Build the Spring Boot Application Docker Image
1.	Navigate to the Spring Boot application directory:
2.	cd springboot-app
3.	Build the Docker image for the Spring Boot application:
4.	docker build -t placeiq-springboot .
5.	Return to the repository root directory:
6.	cd ..
Step 3: Start the Application with Docker Compose
1.	Run the following command to start the containers:
2.	docker-compose up
This will:
o	Pull and run a Neo4j image as the database service.
o	Run the PlaceIQ Spring Boot application as a microservice.
3.	Confirm the containers are running:
4.	docker ps
You should see two containers running: one for Neo4j and one for the Spring Boot application.
Step 4: Access the Services
Neo4j
1.	Open your web browser and navigate to the Neo4j browser:
2.	http://localhost:7474
3.	Log in using the default credentials:
o	Username: neo4j
o	Password: ishaansharma
4.	Change the password on the first login, as prompted.
Spring Boot Application
1.	The Spring Boot application will be accessible at:
2.	http://localhost:8080
3.	Use the API endpoints for uploading CSV files and querying recommendations. Refer to the API documentation for endpoint details.
Step 5: Stop the Services
1.	To stop the containers, use the following command: 
2.	docker-compose down
Optional: View Logs
To view logs from the running containers:
•	Neo4j logs: 
•	docker logs <neo4j_container_id>
•	Spring Boot logs: 
•	docker logs <springboot_container_id>

Opening the PlaceIQ HTML File
1.	Navigate to the folder where the PlaceIQ project files are located.
Ensure the graph_visualization.html file is present in the folder.
2.	Open the HTML file in a web browser:
o	On Windows/macOS:
	Locate the graph_visualization.html file in your file explorer.
	Double-click on it to open in your default browser.
o	Alternatively, right-click the file and select Open With to choose a specific browser (e.g., Google Chrome, Firefox).
3.	View the PlaceIQ interface in your browser and interact with the available features.




 






Additional Notes
•	Ensure that port 7474 for Neo4j and port 8080 for Spring Boot are not being used by other services on your machine.
•	You can modify the docker-compose.yml file to customize configurations like ports or environment variables.
Troubleshooting
•	If you encounter issues, check the Docker logs for detailed error messages.
•	Ensure that your Docker daemon is running.
Thank you for setting up PlaceIQ! For further assistance, feel free to reach out or check the project documentation.




![image](https://github.com/user-attachments/assets/6cacb93b-c374-4de4-8ea8-7969f206edfc)
