package com.example.swarm.model;

import java.util.List;

public class Mission {
    private String name;
    private List<Target> targets;

    public Mission(String name, List<Target> targets) {
        this.name = name;
        this.targets = targets;
    }

    public String getName() { return name; }
    public List<Target> getTargets() { return targets; }
}