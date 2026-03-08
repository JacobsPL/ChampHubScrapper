package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Player.players;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        HttpHandler myHandler = new HttpHandler();
        String eventId = myHandler.extractEventId(HttpHandler.eventUrl);
        String apiUrl = myHandler.createUserSubmissionApiUrl();
        String body = myHandler.FetchHttpBody(apiUrl);

        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.fillPlayerList(body);


        for(Player player: players) {
            body = myHandler.fetchPlayerParingsBody(myHandler.createPlayerDetailsUrl(player));
            jsonHandler.fillArmyList(body);
        }

//        for (Player player: players){
//            System.out.print(player.username+" ");
//            System.out.println(player.armies);
//        }
    }
}