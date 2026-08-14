package com.spms.parkingspaceservice.repository;

import com.spms.parkingspaceservice.model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, String> {

    // Spring Data JPA automatically writes the SQL for these based on the method names!
    List<ParkingSpace> findByIsAvailableTrue();

    List<ParkingSpace> findByLocationIgnoreCase(String location);
}