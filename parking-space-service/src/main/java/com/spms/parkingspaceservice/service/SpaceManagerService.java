package com.spms.parkingspaceservice.service;

import com.spms.parkingspaceservice.model.ParkingSpace;
import com.spms.parkingspaceservice.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SpaceManagerService {

    // Must be declared as 'final' for @RequiredArgsConstructor to inject it
    private final ParkingSpaceRepository repository;

    public List<ParkingSpace> getAllSpaces() {
        return repository.findAll();
    }

    public List<ParkingSpace> getAvailableSpaces() {
        return repository.findByIsAvailableTrue();
    }

    public List<ParkingSpace> filterByLocation(String location) {
        return repository.findByLocationIgnoreCase(location);
    }

    public ParkingSpace updateSpaceStatus(String id, boolean status) {
        Optional<ParkingSpace> optionalSpace = repository.findById(id);

        if (optionalSpace.isPresent()) {
            ParkingSpace space = optionalSpace.get();
            space.setAvailable(status);
            return repository.save(space);
        }
        return null;
    }

    public ParkingSpace addSpace(ParkingSpace space) {
        return repository.save(space);
    }
}