package com.spms.parkingspaceservice.controller;

import com.spms.parkingspaceservice.dto.ParkingSpaceRequest;
import com.spms.parkingspaceservice.dto.ParkingSpaceResponse;
import com.spms.parkingspaceservice.dto.SpaceStatusUpdateRequest;
import com.spms.parkingspaceservice.service.SpaceManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spaces")
@RequiredArgsConstructor
public class ParkingSpaceController {

    private final SpaceManagerService spaceManagerService;

    @GetMapping
    public ResponseEntity<List<ParkingSpaceResponse>> getAllSpaces() {
        return ResponseEntity.ok(spaceManagerService.getAllSpaces());
    }

    @GetMapping("/available")
    public ResponseEntity<List<ParkingSpaceResponse>> getAvailableSpaces() {
        return ResponseEntity.ok(spaceManagerService.getAvailableSpaces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingSpaceResponse> getSpaceById(@PathVariable String id) {
        return ResponseEntity.ok(spaceManagerService.getSpaceById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ParkingSpaceResponse>> filterSpaces(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String zone) {
        if (city != null && zone != null) {
            return ResponseEntity.ok(spaceManagerService.filterByCityAndZone(city, zone));
        } else if (city != null) {
            return ResponseEntity.ok(spaceManagerService.filterByCity(city));
        } else if (zone != null) {
            return ResponseEntity.ok(spaceManagerService.filterByZone(zone));
        } else if (location != null) {
            return ResponseEntity.ok(spaceManagerService.filterByLocation(location));
        }
        return ResponseEntity.ok(spaceManagerService.getAllSpaces());
    }

    @GetMapping("/city/{city}")
    public ResponseEntity<List<ParkingSpaceResponse>> getByCity(@PathVariable String city) {
        return ResponseEntity.ok(spaceManagerService.filterByCity(city));
    }

    @GetMapping("/zone/{zone}")
    public ResponseEntity<List<ParkingSpaceResponse>> getByZone(@PathVariable String zone) {
        return ResponseEntity.ok(spaceManagerService.filterByZone(zone));
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<ParkingSpaceResponse>> getByOwner(@PathVariable String ownerId) {
        return ResponseEntity.ok(spaceManagerService.getSpacesByOwner(ownerId));
    }

    @PostMapping
    public ResponseEntity<ParkingSpaceResponse> addSpace(@Valid @RequestBody ParkingSpaceRequest request) {
        ParkingSpaceResponse response = spaceManagerService.addSpace(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParkingSpaceResponse> updateSpace(
            @PathVariable String id,
            @Valid @RequestBody ParkingSpaceRequest request) {
        return ResponseEntity.ok(spaceManagerService.updateSpace(id, request));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ParkingSpaceResponse> updateStatus(
            @PathVariable String id,
            @RequestParam(required = false) Boolean isAvailable,
            @RequestBody(required = false) SpaceStatusUpdateRequest body) {
        boolean status = isAvailable != null ? isAvailable : (body != null && body.isAvailable());
        return ResponseEntity.ok(spaceManagerService.updateSpaceStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpace(@PathVariable String id) {
        spaceManagerService.deleteSpace(id);
        return ResponseEntity.noContent().build();
    }
}