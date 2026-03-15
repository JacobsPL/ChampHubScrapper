package org.example.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Army {

    private String armyName;
    private int armyFrequency;

    private List<LocalDate> datesOfUsage;

    public Army(String armyName){
        this.armyName=armyName;
        this.armyFrequency++;
        datesOfUsage = new ArrayList<LocalDate>();
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
