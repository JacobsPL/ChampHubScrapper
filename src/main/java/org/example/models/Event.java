package org.example.models;

import org.example.services.HttpHandler;
import org.example.services.JsonHandler;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Event {

    private String eventName;

    private String eventUrl;
    private List<Player> players;
    private String eventId;
    private String apiUrl;
    private String eventBodyPeople;
    private String eventBodyEventManagement;

    private String gameSystem;

    private Map<String,Integer> simplifiedArmyStats = new TreeMap<>();


    public Event(String eventUrl) throws IOException, InterruptedException {
        this.eventUrl = eventUrl;
        players = new ArrayList<>();
    }

    public void fillArmiesForPlayers() throws IOException, InterruptedException {
        JsonHandler jsonHandler = new JsonHandler();
        HttpHandler httpHandler = new HttpHandler();

        for (Player player: players) {
            player.setPlayerBody(httpHandler.fetchPlayerParingsBody(httpHandler.createPlayerDetailsUrl(player),
                    gameSystem));
            jsonHandler.fillArmyList(player,this);
        }
    }
    public void createSimplifyArmyStatistic(){ // to chyba całe jest do wyjebania i do użycia od nowa
        for (Player player: players){
            String playerBestArmy = player.getBestArmy(); // to trzeba zmienić żeby nie brało ostatnio użytą tylko tą o największym score
            boolean armyNotFound = true;
            for(Map.Entry<String,Integer> entry : simplifiedArmyStats.entrySet()){
                if(playerBestArmy.equals(entry.getKey())){
                    entry.setValue(entry.getValue()+1);
                    armyNotFound = false;
                }
            }
            if(armyNotFound){
                simplifiedArmyStats.put(player.getBestArmy(),1);
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
//        mapToBeSorted.clear();
//        mapToBeSorted.putAll(sorted);
    }

    public void setMostProbableArmyForEachPlayer(){
        for (Player player : players){
            player.calculateMostProbableArmy();
        }
    }


    public void extractGameSystem(){

        StringBuilder builder = new StringBuilder();
        int counter = 0;
        int stringCount = 0;
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
        gameSystem = builder.toString();
    }

    public Map<String, Integer> getSimplifiedArmyStats(){
        return simplifiedArmyStats;
    }
    public void fillPlayersDateArmyMap(){
        for(Player currentPlayer : getPlayers()){
            currentPlayer.filleArmyDateMap();
        }
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public List<Player> getPlayers(){
        return players;
    }

    public void addPlayerToList(Player player){
        players.add(player);
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
    public String getEventBodyPeople(){
        return eventBodyPeople;
    }
    public void setEventBodyEventManagement(String body) {this.eventBodyEventManagement = body;}

    public String getEventBodyEventManagement(){return this.eventBodyEventManagement;}

    public String getEventUrl(){
        return this.eventUrl;
    }

    public String getApiUrl(){
        return apiUrl;
    }
}
