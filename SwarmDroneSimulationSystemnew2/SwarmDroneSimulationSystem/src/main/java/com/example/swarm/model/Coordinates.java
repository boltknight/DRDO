package com.example.swarm.model;

public class Coordinates {
    private double x;
    private double y;

    public Coordinates() {}

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() { return x; }
    public double getY() { return y; }

    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
}