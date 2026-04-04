package org.example.models;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Player {

    private List<Army> armyList= new ArrayList<>();
    private Map<LocalDate, String> dateArmyMap = new LinkedHashMap<>();
    private String username;
    private String playerId;
    private Army mostProbableArmy = null;
    String playerBody;

    //Constructor
    public Player(String playerId, String username){
        this.playerId = playerId;
        this.username = username;
    }

    public void calculateMostProbableArmy(){
        int k = 0;
        int t = 3; //extinction factor
        // calculate army score for each army
        for(Map.Entry<LocalDate,String> currentEntry : dateArmyMap.entrySet()){
            double weight = Math.exp((double) -k /t);
            if(k==0){weight=+0.5;}
            for(Army currentArmy : armyList){
                if(currentArmy.getArmyName().equals(currentEntry.getValue())){
                    currentArmy.addToScore(weight);
                }
            }
            k++;
        }
        // choose the best army and assignee it to the player
        double bestScore = 0;
        for(Army currentArmy : armyList) {
            if (currentArmy.getScore() > bestScore) {
                this.mostProbableArmy = currentArmy;
                bestScore = currentArmy.getScore();
            }
        }
    }

    public void filleArmyDateMap(){
        for(Army current : armyList){
            for (LocalDate data : current.getDatesOfUsage()){
                dateArmyMap.put(data,current.getArmyName());
            }
        }
        dateArmyMap = sortMap(dateArmyMap); // sorted map <Army,LocalDate> for the further use in alghoritm
    }

    private Map<LocalDate, String> sortMap(Map<LocalDate, String> map){
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.<LocalDate,String>comparingByKey().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b)->a,
                        LinkedHashMap::new));
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

    // Setters
    public void setPlayerBody(String body){
        this.playerBody=body;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPlayerId(String id){
        this.playerId = id;
    }

    // Getters
    public String getMostProbableArmy(){
        return mostProbableArmy != null ? mostProbableArmy.getArmyName() : "NOWY GRACZ";
    }
    public String getPlayerId(){
        return playerId;
    }
    public String getUsername(){
        return username;
    }
    public String getPlayerBody(){
        return playerBody;
    }
    public Army getArmyFromList(String armyName){
        for(Army army : armyList){
            if(army.getArmyName().equals(armyName)){
                return army;
            }
        }
        return null;
    }
    public List<Army> getArmyList(){
        return this.armyList;
    }
}
