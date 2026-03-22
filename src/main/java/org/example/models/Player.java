package org.example.models;

import java.util.*;

public class Player {

    private List<Army> armyList= new ArrayList<>();

    private String username;
    private String playerId;
    private Army recentArmy = null;
    String playerBody;

    //Constructor
    public Player(String playerId, String username){
        this.playerId = playerId;
        this.username = username;
    }

    // army List methods

    public List<Army> getArmyList(){
        return this.armyList;
    }
    public void addArmyToList(String army){
        for(Army armyToAdd : armyList){
            if(armyToAdd.equals(army)){
                return;
            }
        }
        Army newArmy = new Army(army);
        armyList.add(newArmy);
    }

    public Army getArmyFromList(String armyName){
        for(Army army : armyList){
            if(army.getArmyName().equals(armyName)){
                return army;
            }
        }
        return null;
    }

    // player variables
    public void setPlayerBody(String body){
        this.playerBody=body;
    }

    public String getPlayerBody(){
        return playerBody;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

    public String getPlayerId(){
        return playerId;
    }

    public void setPlayerId(String id){
        this.playerId = id;
    }

    // utility methods
    public void calculateMostRecentlyUsedArmy(){
        for(Army currentArmy: armyList){
            if (recentArmy == null) {
                recentArmy = currentArmy;
                continue;
            } else if (currentArmy.getDateOfLastUsage().isAfter(recentArmy.getDateOfLastUsage())) {
                recentArmy=currentArmy;
            }
        }
    }

    public String getRecentlyUsedArmy(){
        return recentArmy != null ? recentArmy.getArmyName() : "Brak armii";
    }
}
