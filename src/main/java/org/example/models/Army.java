package org.example.models;

import java.time.LocalDate;
import java.util.*;
import java.util.Collections;
public class Army {

    private final String armyName;

    public String getArmyName(){
        return this.armyName;
    }
    private int armyFrequency;

    private final Set<LocalDate> datesOfUsage;

    public Army(String armyName){
        this.armyName=armyName;
        this.armyFrequency++;
        datesOfUsage = new TreeSet<>();
    }

    public boolean equals(String armyName){
        return this.armyName.equals(armyName);
    }

    public void addFrequency(){
        this.armyFrequency++;
    }

    public void addDate(LocalDate data){
        datesOfUsage.add(data);
    }

    public LocalDate getDateOfLastUsage(){
        return Collections.max(datesOfUsage);
    }

}
