// content from Transaction.java block// src/main/java/com/example/swarm/model/Transaction.java
package com.example.swarm.model;

public class Transaction {
    private TransactionType type;
    private int id;
    private Coordinates coordinates;
    private int priority;      // for targets
    private String targetType; // for targets

    public Transaction(TransactionType type, int id, Coordinates coordinates,
                       int priority, String targetType) {
        this.type = type;
        this.id = id;
        this.coordinates = coordinates;
        this.priority = priority;
        this.targetType = targetType;
    }

    public TransactionType getType() { return type; }
    public int getId() { return id; }
    public Coordinates getCoordinates() { return coordinates; }
    public int getPriority() { return priority; }
    public String getTargetType() { return targetType; }
}
