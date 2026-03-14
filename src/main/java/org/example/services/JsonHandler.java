package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Event;
import org.example.models.Player;
import org.jetbrains.annotations.NotNull;

//import static org.example.models.Player.players;

public class JsonHandler {

    boolean isAprroved(@NotNull JsonNode attender){
        JsonNode isApproved = attender.get("status");
        String status = isApproved.toString();
        status = status.substring(1,status.length()-1);
        return status.equals("APPROVED");
    }
    public void fillPlayerList(@NotNull Event event) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(event.getEventBody());
        JsonNode attendees = node.get("people");
            for(JsonNode attender : attendees) {
                JsonNode users = attender.get("user");
                if (isAprroved(attender)) {
                    event.addPlayerToList(new Player(users.get("id").asText(), users.get("displayName").asText()));
                }
            }
    }

    public void fillArmyList(Player user, Event event) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(user.getPlayerBody());
        JsonNode events = node.get("data");

        for (JsonNode currentEvent : events) {
            JsonNode parings = currentEvent.get("pairings");
            for (JsonNode pairing : parings) {
                String username = pairing.path("pairingUser1").path("user").path("displayName").toString();
                if(!username.isEmpty()) username = username.substring(1,username.length()-1);
                String army = pairing.path("pairingUser1").path("army").path("name").toString();
                if(!army.isEmpty()) army = army.substring(1,army.length()-1);
                for (Player player : event.getPlayers()) {
                    if (player.getUsername().equals(username)) {
                        player.addArmyAndFrequency(army);
                    }
                }
            }
        }
    }
}

