package org.example.models;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Player {

    private List<Army> armyList= new ArrayList<>();
    private Map<LocalDate, String> dateArmyMap = new LinkedHashMap<>();
    // coś tutaj jest nie tak - nie dodaje się kolejna armia jeśli jest inna data
    // dodaje do mapy nieunikalne wartości dlatego nadpisuję dane i biorę tylko ostatnią danę - to psuje mi algorytm
    // TO DO: przemyśleć czy w ogóle potrzebuję mapy - może warto zamienić to po prostu na listę
    private String username;
    private String playerId;
    private Army bestArmy = null;
    String playerBody;

    //Constructor
    public Player(String playerId, String username){
        this.playerId = playerId;
        this.username = username;
    }

    public void calculateMostProbableArmy(){
        int k = 0;
        int t = 3; //współczynnik wygaszania
        // calculate army score for each army
        for(Map.Entry<LocalDate,String> currentEntry : dateArmyMap.entrySet()){
            double weight = Math.exp((double) -k /t);
            for(Army currentArmy : armyList){
                if(currentArmy.getArmyName().equals(currentEntry.getValue())){
                    currentArmy.addToScore(weight);
                }
            }
            k++;
        }
        // choose the best army and assigne it to the player
        Army bestArmy = null;
        double bestScore = 0;
        for(Army currentArmy : armyList) {
            if (currentArmy.getScore() > bestScore) {
                bestArmy = currentArmy;
                bestScore = currentArmy.getScore();
            }
        }
        this.bestArmy = bestArmy;
    }

    public void filleArmyDateMap(){
        for(Army current : armyList){
            for (LocalDate data : current.getDatesOfUsage()){
                dateArmyMap.put(data,current.getArmyName());
            }
        }
        dateArmyMap = sortMap(dateArmyMap); // czyli mamy posortowaną mapę <Army,LocalDate> pod algotyrm
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
//        map.clear();
//        map.putAll(sorted);
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
//    public void calculateMostRecentlyUsedArmy(){
//        for(Army currentArmy: armyList){
//            if (bestArmy == null) {
//                bestArmy = currentArmy;
//                continue;
//            } else if (currentArmy.getDateOfLastUsage().isAfter(bestArmy.getDateOfLastUsage())) {
//                bestArmy =currentArmy;
//            }
//        }
//    }

    public String getBestArmy(){
        return bestArmy != null ? bestArmy.getArmyName() : "NOWY GRACZ";
    }
}
