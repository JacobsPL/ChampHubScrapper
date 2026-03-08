package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Event {

    private String eventName;
    private List<Player> players;

    // TO DO
    private Map<String, Integer> armyFrequency;

    private String eventId;
    private String apiUrl;
    private String eventBody;


    public Event(){
        players = new ArrayList<>();
        armyFrequency = new HashMap<>();
    }
    // TO DO
    void fillPlayerList() throws JsonProcessingException {
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.fillPlayerList(this);
    }

    List<Player> getPlayers(){
        return players;
    }

   void addPlayerToList(Player player){
        players.add(player);
   }


    void setEventId(String eventId){
        this.eventId=eventId;
    }

    void setApiUrl(String apiUrl){
        this.apiUrl=apiUrl;
    }

    void setEventBody(String body){
        this.eventBody=body;
    }

    String getApiUrl(){
        return apiUrl;
    }

    String getEventBody(){
        return eventBody;
    }
}
