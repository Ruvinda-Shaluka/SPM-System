package com.spms.parkingspaceservice.repository;

import com.spms.parkingspaceservice.model.ParkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSpaceRepository extends JpaRepository<ParkingSpace, String> {

    List<ParkingSpace> findByIsAvailableTrue();

    List<ParkingSpace> findByLocationIgnoreCaseContaining(String location);

    List<ParkingSpace> findByCityIgnoreCase(String city);

    List<ParkingSpace> findByZoneIgnoreCase(String zone);

    List<ParkingSpace> findByOwnerId(String ownerId);

    List<ParkingSpace> findByCityIgnoreCaseAndZoneIgnoreCase(String city, String zone);

    List<ParkingSpace> findByCityIgnoreCaseAndIsAvailableTrue(String city);
}