package org.example.models;

import java.util.*;

public class Player {

    private Set<String> armies = new TreeSet<>();
    private Map<String, Integer> armiesFrequency = new HashMap<>();
    private List<Army> armyList= new ArrayList<>();

    private String username;
    private String playerId;
    String playerBody;

    // armies methods
    @Deprecated
    public Set<String> getArmies(){
        return armies;
    }

    @Deprecated
    public void addArmy(String army){
        armies.add(army);
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


    // army frequency methods
    @Deprecated
    public Map<String, Integer> getArmiesFrequency() {
        return armiesFrequency;
    }

    @Deprecated
    public void addArmyAndFrequency(String army) {
        for(Map.Entry<String, Integer> entry : armiesFrequency.entrySet()){
            if(entry.getKey().equals(army)){
                entry.setValue(entry.getValue()+1);
                return;
            }
        }
        armiesFrequency.put(army,1);
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

    public Player(String playerId, String username){
        this.playerId = playerId;
        this.username = username;
    }

    public void calculateArmyChance(){

    }
}
