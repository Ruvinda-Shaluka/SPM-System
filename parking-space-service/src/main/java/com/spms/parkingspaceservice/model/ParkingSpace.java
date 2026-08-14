package com.spms.parkingspaceservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_spaces")
public class ParkingSpace {

    @Id
    private String id; // We will use strings like "A1", "B2" as the primary key

    private String location;
    private boolean isAvailable;
    private String type;

    // Default constructor required by JPA
    public ParkingSpace() {}

    public ParkingSpace(String id, String location, boolean isAvailable, String type) {
        this.id = id;
        this.location = location;
        this.isAvailable = isAvailable;
        this.type = type;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public boolean isAvailable() { return isAvailable; }
    public void setAvailable(boolean available) { isAvailable = available; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}