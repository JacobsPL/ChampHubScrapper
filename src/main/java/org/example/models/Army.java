package org.example.models;

import java.time.LocalDate;
import java.util.*;

public class Army {

    private String armyName;

    public String getArmyName(){
        return this.armyName;
    }
    private int armyFrequency;

    private Set<LocalDate> datesOfUsage;

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

}
