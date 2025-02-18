package com.spring.neo4j.springneo4jcrud.service;

import com.spring.neo4j.springneo4jcrud.AmazonS3Config;
import com.spring.neo4j.springneo4jcrud.repository.PlaceRepository;
import com.spring.neo4j.springneo4jcrud.repository.UserRepository;
import com.spring.neo4j.springneo4jcrud.repository.node.Place;
import com.spring.neo4j.springneo4jcrud.repository.node.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final String BUCKET_NAME = "advancedatabase";
    private static final String FILE_NAME = "ADBAssignment.xlsx";

    // Inject your repositories and services here
    private final UserRepository userRepository;
    private final PlaceRepository placeRepository;
    @Autowired
    private AmazonS3Config amazonS3;

    @Autowired
    private S3Client s3Client;


    public UserService(UserRepository userRepository, PlaceRepository placeRepository) {
        this.userRepository = userRepository;
        this.placeRepository = placeRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User addUser(User user) {
        // Check if the user already exists in the database based on a unique identifier, e.g., name
        Optional<User> existingUser = userRepository.findByName(user.getName());
        if (existingUser.isPresent()) {
            // User exists, reuse the existing user
            User existing = existingUser.get();

            // Ensure the user's places are updated
            for (Place newPlace : user.getPlaces()) {
                Optional<Place> existingPlace = placeRepository.findByName(newPlace.getName());
                if (existingPlace.isPresent()) {
                    // Place exists, add the existing place to the user's place list
                    if (!existing.getPlaces().contains(existingPlace.get())) {
                        existing.getPlaces().add(existingPlace.get());
                    }
                } else {
                    // Place does not exist, create and add a new place
                    Place savedPlace = placeRepository.save(newPlace);
                    existing.getPlaces().add(savedPlace);
                }
            }

            // Save the updated user and return
            return userRepository.save(existing);
        } else {
            // User does not exist, handle places for the new user
            for (int i = 0; i < user.getPlaces().size(); i++) {
                Place newPlace = user.getPlaces().get(i);
                Optional<Place> existingPlace = placeRepository.findByName(newPlace.getName());
                if (existingPlace.isPresent()) {
                    // Use the existing place
                    user.getPlaces().set(i, existingPlace.get());
                } else {
                    // Save the new place
                    Place savedPlace = placeRepository.save(newPlace);
                    user.getPlaces().set(i, savedPlace);
                }
            }

            // Save and return the new user
            return userRepository.save(user);
        }
    }


    public User updateUser(User user) {
        Optional<User> userFromDB = userRepository.findById(user.getUserId());

        if(userFromDB.isPresent()) {
            User userFromDBVal = userFromDB.get();
            userFromDBVal.setPlaces(user.getPlaces());
            userFromDBVal.setName(user.getName());
            return userRepository.save(userFromDBVal);
        } else {
            return null;
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteByUserId(id);
    }



    public String addUsersFromS3() {
        List<User> users = new ArrayList<>();

        try {
            // Retrieve the Excel file from S3
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(BUCKET_NAME)
                    .key(FILE_NAME)
                    .build();

            // Retrieving the S3 object from S3 bucket
            ResponseInputStream<GetObjectResponse> inputStream = s3Client.getObject(getObjectRequest);

            // Parse the Excel file
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);  // Assuming data is in the first sheet

            // Iterate through the rows and extract data
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    // Skip the header row
                    continue;
                }

                String name = row.getCell(0).getStringCellValue();  // Assuming name is in the first column
                List<Place> places = new ArrayList<>();

                // Assuming place names are comma-separated in the second column
                String placesData = row.getCell(1).getStringCellValue();
                if (placesData != null && !placesData.isEmpty()) {
                    for (String placeName : placesData.split(",")) {
                       places.add(new Place(placeName.trim()));
                    }
                }

                // Create a User object
                User user = new User(null, name, places);
                try{
                    addUser(user);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

            }

            workbook.close();
            inputStream.close();

            // Return the last added user for confirmation (optional)
            return "Bulk Data added from AWS S3 successfully";

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error processing the file from S3", e);
        }
    }
}
