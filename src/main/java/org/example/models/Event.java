package org.example.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.services.HttpHandler;
import org.example.services.JsonHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Event {

    private String eventName;

    private String eventUrl;
    private List<Player> players;
    private String eventId;
    private String apiUrl;
    private String eventBody;


    public Event(String eventUrl) throws IOException, InterruptedException {
        this.eventUrl = eventUrl;
        players = new ArrayList<>();
    }

    public void fillArmiesForPlayers() throws IOException, InterruptedException {
        JsonHandler jsonHandler = new JsonHandler();
        HttpHandler httpHandler = new HttpHandler();

        for (Player player: players) {
            player.setPlayerBody(httpHandler.fetchPlayerParingsBody(httpHandler.createPlayerDetailsUrl(player)));
            jsonHandler.fillArmyList(player,this);
        }
    }
// TO DO
//    public Map<String, Integer> getArmyStatistic(){
//        for (Player player: players){
//            List<Army> armies = player.getArmyList();
//            for (Army army:armies) {
//                String armyName = army.getArmyName();
//
//            }
//        }
//    }

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

    public void setEventBody(String body){
        this.eventBody=body;
    }

    public String getApiUrl(){
        return apiUrl;
    }

    public String getEventBody(){
        return eventBody;
    }

    public String getEventUrl(){
        return this.eventUrl;
    }
}
