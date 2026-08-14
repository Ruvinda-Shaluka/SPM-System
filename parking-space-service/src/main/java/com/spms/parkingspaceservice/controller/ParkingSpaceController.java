package com.spms.parkingspaceservice.controller;

import com.spms.parkingspaceservice.model.ParkingSpace;
import com.spms.parkingspaceservice.service.SpaceManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spaces")
@RequiredArgsConstructor
public class ParkingSpaceController {

    // Must be declared as 'final' for @RequiredArgsConstructor to inject it
    private final SpaceManagerService spaceManagerService;

    @GetMapping
    public List<ParkingSpace> getAllSpaces() {
        return spaceManagerService.getAllSpaces();
    }

    @GetMapping("/available")
    public List<ParkingSpace> getAvailableSpaces() {
        return spaceManagerService.getAvailableSpaces();
    }

    @GetMapping("/filter")
    public List<ParkingSpace> filterByLocation(@RequestParam String location) {
        return spaceManagerService.filterByLocation(location);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ParkingSpace> updateStatus(@PathVariable String id, @RequestParam boolean isAvailable) {
        ParkingSpace updatedSpace = spaceManagerService.updateSpaceStatus(id, isAvailable);
        if (updatedSpace != null) {
            return ResponseEntity.ok(updatedSpace);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ParkingSpace> addSpace(@RequestBody ParkingSpace space) {
        return ResponseEntity.ok(spaceManagerService.addSpace(space));
    }
}