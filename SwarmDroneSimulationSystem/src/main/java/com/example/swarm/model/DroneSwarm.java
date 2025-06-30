// content from DroneSwarm.java block// src/main/java/com/example/swarm/model/DroneSwarm.java
package com.example.swarm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DroneSwarm {
    private final List<Drone> drones = new ArrayList<>();
    private final List<Target> targets = new ArrayList<>();
    private double cumulativeKillProbability;

    public void initializeFromTransactions(List<Transaction> transactions) {
        drones.clear();
        targets.clear();
        for (Transaction tx : transactions) {
            switch (tx.getType()) {
                case CREATE_DRONE -> drones.add(new Drone(tx.getId(), tx.getCoordinates()));
                case CREATE_TARGET -> targets.add(new Target(
                        tx.getId(),
                        tx.getTargetType(),
                        tx.getPriority(),
                        tx.getCoordinates()));
            }
        }
    }

    public void executeSwarmOperation() {
        // Simple placeholder: move each drone towards the nearest target
        for (Drone d : drones) {
            Target nearest = targets.stream()
                    .filter(t -> !t.isNeutralized())
                    .min((t1, t2) -> Double.compare(
                            distance(d.getPosition(), t1.getLocation()),
                            distance(d.getPosition(), t2.getLocation())))
                    .orElse(null);
            if (nearest != null) {
                d.setPosition(nearest.getLocation());
            }
        }
    }

    public void calculateKillProbability() {
        Random rand = new Random();
        for (Target t : targets) {
            double p = Math.min(1.0, 0.25 + 0.1 * t.getPriority() + rand.nextDouble() * 0.15);
            t.setKillProbability(p);
        }
        cumulativeKillProbability = 1.0;
        for (Target t : targets) {
            cumulativeKillProbability *= (1.0 - t.getKillProbability());
        }
        cumulativeKillProbability = 1.0 - cumulativeKillProbability;
    }

    public boolean simulateMissionOutcome() {
        Random rand = new Random();
        boolean allNeutralized = true;
        for (Target t : targets) {
            boolean neutralized = rand.nextDouble() <= t.getKillProbability();
            t.setNeutralized(neutralized);
            if (!neutralized) {
                allNeutralized = false;
            }
        }
        return allNeutralized;
    }

    public List<Target> getTargets() { return targets; }
    public double getCumulativeKillProbability() { return cumulativeKillProbability; }

    private double distance(Coordinates a, Coordinates b) {
        double dx = a.getX() - b.getX();
        double dy = a.getY() - b.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
