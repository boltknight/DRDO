// content from Drone.java block// src/main/java/com/example/swarm/model/Drone.java
package com.example.swarm.model;

public class Drone {
    private int id;
    private Coordinates position;

    public Drone(int id, Coordinates position) {
        this.id = id;
        this.position = position;
    }

    public int getId() { return id; }
    public Coordinates getPosition() { return position; }
    public void setPosition(Coordinates position) { this.position = position; }
}
