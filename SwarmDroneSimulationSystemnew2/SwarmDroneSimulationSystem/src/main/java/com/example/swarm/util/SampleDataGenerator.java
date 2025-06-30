package com.example.swarm.util;

import com.example.swarm.model.*;
import com.example.swarm.model.TransactionType;

import java.util.Arrays;
import java.util.List;

public class SampleDataGenerator {

    public static List<Transaction> generateSampleTransactions() {
        return Arrays.asList( new Transaction[] {
                new Transaction(TransactionType.CREATE_DRONE, 1, new Coordinates(0, 0), 0, null),
                new Transaction(TransactionType.CREATE_DRONE, 2, new Coordinates(50, 50), 0, null),
                new Transaction(TransactionType.CREATE_DRONE, 3, new Coordinates(100, 50), 0, null),

                new Transaction(TransactionType.CREATE_TARGET, 101, new Coordinates(550, 350), 3, TargetType.RADAR),
                new Transaction(TransactionType.CREATE_TARGET, 102, new Coordinates(460, 220), 2, TargetType.SAM),
                new Transaction(TransactionType.CREATE_TARGET, 103, new Coordinates(240, 120), 1, TargetType.ARTILLERY)}
        );
    }
}