package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Player {

    private Set<String> armies = new TreeSet<>();
    private String username;
    private String playerId;

    String playerBody;

    Set<String> getArmies(){
        return armies;
    }

    void addArmy(String army){
        armies.add(army);
    }

    void setPlayerBody(String body){
        this.playerBody=body;
    }

    String getPlayerBody(){
        return playerBody;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String getUsername(){
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

    public void addArmyToList(String army){
        while (armies.iterator().hasNext()){
            if(armies.iterator().next().equals(army)){
                return;
            }
        }
        armies.add(army);
    }



}
