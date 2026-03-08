package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Player {

    static List<Player> players = new ArrayList<>(); // to trzeba zrobić nie statycznie

    Set<String> armies = new TreeSet<>();
    String username;
    String playerId;


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
