package com.example.swarm.model;

public class Target {
    private int id;
    private TargetType type;
    private int priority;
    private double killProbability;
    private boolean neutralized;
    private Coordinates location;

    public Target(int id, TargetType type, int priority, Coordinates location) {
        this.id = id;
        this.type = type;
        this.priority = priority;
        this.location = location;
    }

    public int getId() { return id; }
    public TargetType getType() { return type; }
    public int getPriority() { return priority; }
    public double getKillProbability() { return killProbability; }
    public boolean isNeutralized() { return neutralized; }
    public Coordinates getLocation() { return location; }

    public void setKillProbability(double killProbability) {
        this.killProbability = killProbability;
    }

    public void setNeutralized(boolean neutralized) {
        this.neutralized = neutralized;
    }
}