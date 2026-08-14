package com.spms.parkingspaceservice.service;

import com.spms.parkingspaceservice.dto.ParkingSpaceRequest;
import com.spms.parkingspaceservice.dto.ParkingSpaceResponse;
import com.spms.parkingspaceservice.model.ParkingSpace;
import com.spms.parkingspaceservice.repository.ParkingSpaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SpaceManagerService {

    private final ParkingSpaceRepository repository;

    public List<ParkingSpaceResponse> getAllSpaces() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ParkingSpaceResponse> getAvailableSpaces() {
        return repository.findByIsAvailableTrue().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ParkingSpaceResponse getSpaceById(String id) {
        ParkingSpace space = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parking space not found with id: " + id));
        return mapToResponse(space);
    }

    public List<ParkingSpaceResponse> filterByLocation(String location) {
        return repository.findByLocationIgnoreCaseContaining(location).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ParkingSpaceResponse> filterByCity(String city) {
        return repository.findByCityIgnoreCase(city).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ParkingSpaceResponse> filterByZone(String zone) {
        return repository.findByZoneIgnoreCase(zone).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ParkingSpaceResponse> getSpacesByOwner(String ownerId) {
        return repository.findByOwnerId(ownerId).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ParkingSpaceResponse> filterByCityAndZone(String city, String zone) {
        return repository.findByCityIgnoreCaseAndZoneIgnoreCase(city, zone).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public ParkingSpaceResponse updateSpaceStatus(String id, boolean status) {
        ParkingSpace space = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parking space not found with id: " + id));

        space.setAvailable(status);
        ParkingSpace saved = repository.save(space);
        return mapToResponse(saved);
    }

    public ParkingSpaceResponse addSpace(ParkingSpaceRequest request) {
        if (repository.existsById(request.getId())) {
            throw new IllegalArgumentException("Parking space ID already exists: " + request.getId());
        }

        ParkingSpace space = ParkingSpace.builder()
                .id(request.getId())
                .location(request.getLocation())
                .city(request.getCity())
                .zone(request.getZone())
                .ownerId(request.getOwnerId())
                .type(request.getType() != null ? request.getType() : "STANDARD")
                .pricePerHour(request.getPricePerHour())
                .isAvailable(request.isAvailable())
                .totalSlots(request.getTotalSlots() != null ? request.getTotalSlots() : 1)
                .description(request.getDescription())
                .imageUrl(request.getImageUrl())
                .build();

        ParkingSpace saved = repository.save(space);
        return mapToResponse(saved);
    }

    public ParkingSpaceResponse updateSpace(String id, ParkingSpaceRequest request) {
        ParkingSpace space = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parking space not found with id: " + id));

        space.setLocation(request.getLocation());
        space.setCity(request.getCity());
        space.setZone(request.getZone());
        if (request.getType() != null) {
            space.setType(request.getType());
        }
        space.setPricePerHour(request.getPricePerHour());
        space.setAvailable(request.isAvailable());
        if (request.getTotalSlots() != null) {
            space.setTotalSlots(request.getTotalSlots());
        }
        if (request.getDescription() != null) {
            space.setDescription(request.getDescription());
        }
        if (request.getImageUrl() != null) {
            space.setImageUrl(request.getImageUrl());
        }

        ParkingSpace saved = repository.save(space);
        return mapToResponse(saved);
    }

    public void deleteSpace(String id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Parking space not found with id: " + id);
        }
        repository.deleteById(id);
    }

    private ParkingSpaceResponse mapToResponse(ParkingSpace space) {
        return ParkingSpaceResponse.builder()
                .id(space.getId())
                .location(space.getLocation())
                .city(space.getCity())
                .zone(space.getZone())
                .ownerId(space.getOwnerId())
                .type(space.getType())
                .pricePerHour(space.getPricePerHour())
                .isAvailable(space.isAvailable())
                .totalSlots(space.getTotalSlots())
                .description(space.getDescription())
                .imageUrl(space.getImageUrl())
                .createdAt(space.getCreatedAt())
                .updatedAt(space.getUpdatedAt())
                .build();
    }
}