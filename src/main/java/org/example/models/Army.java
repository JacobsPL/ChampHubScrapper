package org.example.models;

import java.time.LocalDate;
import java.util.*;
import java.util.Collections;
public class Army {

    private final String armyName;
    private final Set<LocalDate> datesOfUsage;

    public Army(String armyName){
        this.armyName=armyName;
        datesOfUsage = new TreeSet<>();
    }

    public String getArmyName(){
        return this.armyName;
    }

    public boolean equals(String armyName){
        return this.armyName.equals(armyName);
    }

    public void addDate(LocalDate data){
        datesOfUsage.add(data);
    }

    public LocalDate getDateOfLastUsage(){
        return Collections.max(datesOfUsage);
    }

}
