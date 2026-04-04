package org.example.models;

import java.time.LocalDate;
import java.util.*;
import java.util.Collections;
public class Army {

    private double score;
    private final String armyName;
    private final Set<LocalDate> datesOfUsage;

    public Army(String armyName){
        this.armyName=armyName;
        datesOfUsage = new TreeSet<>(Comparator.reverseOrder());
        score=0;
    }

    public void addToScore(double addition){
        this.score+=addition;
    }
    public boolean equals(String armyName){
        return this.armyName.equals(armyName);
    }

    public void addDate(LocalDate data){
        datesOfUsage.add(data);
    }

    // Getters
    public double getScore() { return this.score;}
    public String getArmyName(){
        return this.armyName;
    }
    public Set<LocalDate> getDatesOfUsage(){
        return this.datesOfUsage;
    }

}
