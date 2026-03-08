package org.example;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

import static org.example.Player.players;

public class JsonHandler {

    boolean isAprroved(JsonNode attender){
        JsonNode isApproved = attender.get("status");
        String status = isApproved.toString();
        status = status.substring(1,status.length()-1);
        return status.equals("APPROVED");
    }
    void fillPlayerList(String json) throws JsonProcessingException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        JsonNode attendees = node.get("people");
            for(JsonNode attender : attendees) {
                JsonNode users = attender.get("user");
                if (isAprroved(attender)) {
                    players.add(new Player(users.get("id").asText(), users.get("displayName").asText()));
                }
            }
    }

    void fillArmyList(String json) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(json);
        JsonNode events = node.get("data");

        for (JsonNode event : events) {
            JsonNode parings = event.get("pairings");
            for (JsonNode pairing : parings) {
                String username = pairing.path("pairingUser1").path("user").path("displayName").toString();
                if(!username.isEmpty()) username = username.substring(1,username.length()-1);
                String army = pairing.path("pairingUser1").path("army").path("name").toString();
                if(!army.isEmpty()) army = army.substring(1,army.length()-1);
                for (Player player : players) {
                    if (player.username.equals(username)) {
                        player.armies.add(army);
                    }
                }
            }
        }
    }
}

