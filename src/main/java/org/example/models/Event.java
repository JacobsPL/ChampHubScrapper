package org.example.models;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Event {

    private String eventName;
    private String eventUrl;
    private Map<String,Player> playersMap;
    private String eventId;
    private String apiUrl;
    private String eventBodyPeople;
    private String eventBodyEventManagement;
    private String gameSystem;

    private Map<String,Integer> simplifiedArmyStats = new TreeMap<>();


    public Event(String eventUrl) {
        this.eventUrl = eventUrl;
        playersMap = new HashMap<>();
    }

    public void createSimplifyArmyStatistic(){
        for (Player player: playersMap.values()){
            String playerBestArmy = player.getMostProbableArmy();
            boolean armyNotFound = true;
            for(Map.Entry<String,Integer> entry : simplifiedArmyStats.entrySet()){
                if(playerBestArmy.equals(entry.getKey())){
                    entry.setValue(entry.getValue()+1);
                    armyNotFound = false;
                }
            }
            if(armyNotFound){
                simplifiedArmyStats.put(player.getMostProbableArmy(),1);
            }
        }
        simplifiedArmyStats=sortMap(simplifiedArmyStats);
    }

    private Map<String, Integer> sortMap(Map <String, Integer> mapToBeSorted){
        return mapToBeSorted.entrySet()
                .stream()
                .sorted(Map.Entry.<String,Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a,b)->a,
                        LinkedHashMap::new));
    }


    public void extractGameSystem(){
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for(int i = 0; i<eventUrl.length(); i++){
            if(eventUrl.charAt(i)=='/'){
                counter++;
                if(counter==3){
                    i++;
                    while (i<eventUrl.length() && eventUrl.charAt(i)!='/'){
                        builder.append(eventUrl.charAt(i));
                        i++;
                    }
                    gameSystem = builder.toString();
                }
            }
        }
    }

    public void addPlayerToMap(String username, Player player){
        playersMap.put(username,player);
    }

   //Setters
   public void setEventName(String eventName) {
       this.eventName = eventName;
   }
    public void setEventId(String eventId){
        this.eventId=eventId;
    }

    public void setApiUrl(String apiUrl){
        this.apiUrl=apiUrl;
    }

    public void setEventBodyPeople(String body){
        this.eventBodyPeople =body;
    }

    public void setEventBodyEventManagement(String body) {this.eventBodyEventManagement = body;}

    public void setMostProbableArmyForEachPlayer(){
        for (Player player : playersMap.values()){
            player.calculateMostProbableArmy();
        }
    }

    // Geters

    public Map<String,Player> getPlayersMap(){
        return playersMap;
    }
    public String getEventBodyEventManagement(){return this.eventBodyEventManagement;}

    public String getEventUrl(){
        return this.eventUrl;
    }

    public String getApiUrl(){
        return apiUrl;
    }
    public String getEventBodyPeople(){
        return eventBodyPeople;
    }
    public String getEventName() {
        return eventName;
    }

    //Used in resul.html
    public Map<String, Integer> getSimplifiedArmyStats(){
        return simplifiedArmyStats;
    }
    public void fillPlayersDateArmyMap(){
        for(Player currentPlayer : playersMap.values()){
            currentPlayer.filleArmyDateMap();
        }
    }

    public String getGameSystem(){
        return this.gameSystem;
    }
}
