package org.example.models;

import org.example.services.HttpHandler;
import org.example.services.JsonHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Event {

    private String eventName;

    private String eventUrl;
    private List<Player> players;
    private String eventId;
    private String apiUrl;
    private String eventBodyPeople;
    private String eventBodyEventManagement;


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
