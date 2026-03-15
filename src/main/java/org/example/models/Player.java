package org.example.models;

import java.util.*;

public class Player {

    private Set<String> armies = new TreeSet<>();
    private Map<String, Integer> armiesFrequency = new HashMap<>();
    private String username;
    private String playerId;

    private List<Army> armyList= new ArrayList<Army>();

    String playerBody;
    @Deprecated
    public Set<String> getArmies(){
        return armies;
    }

    @Deprecated
    public void addArmy(String army){
        armies.add(army);
    }

    public void addArmyToList(Army army){
        armyList.add(army);
    }

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



    public void calculateArmyChance(){

    }
}
