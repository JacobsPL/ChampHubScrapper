package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//import static org.example.Player.players;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Set up api event properties
        Event event = new Event();
        HttpHandler myHandler = new HttpHandler();
        event.setEventId(myHandler.extractEventId(HttpHandler.eventUrl));
        event.setApiUrl(myHandler.createUserSubmissionApiUrl());
        event.setEventBody(myHandler.FetchHttpBody(event.getApiUrl()));

        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.fillPlayerList(event);


        for(Player player: event.getPlayers()) {
            player.setPlayerBody(myHandler.fetchPlayerParingsBody(myHandler.createPlayerDetailsUrl(player)));
            jsonHandler.fillArmyList(player, event);
        }

        for (Player player: event.getPlayers()){
            System.out.print(player.getUsername()+" ");
            System.out.println(player.getArmies());
        }
    }
}