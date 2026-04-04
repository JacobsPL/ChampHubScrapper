package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Event;
import org.example.models.Player;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Service
public class JsonHandler {

    private ObjectMapper mapper;

    public JsonHandler(ObjectMapper mapper){
        this.mapper=mapper;
    }
    public void fillEventName(@NotNull Event event) throws JsonProcessingException{
        JsonNode node = mapper.readTree(event.getEventBodyEventManagement());
        event.setEventName(node.get("name").asText());
    }
    public void fillPlayerList(@NotNull Event event) throws JsonProcessingException {
        JsonNode node = mapper.readTree(event.getEventBodyPeople());
        JsonNode attendees = node.get("people");
            for(JsonNode attender : attendees) {
                JsonNode users = attender.get("user");
                if (isAprroved(attender)) {
                    Player player = new Player(users.get("id").asText(), users.get("displayName").asText());
                    event.addPlayerToMap(player.getUsername(), player);
                }
            }
    }
    private boolean isAprroved(@NotNull JsonNode attender){
        JsonNode isApproved = attender.get("status");
        String status = isApproved.asText();
        return status.equals("APPROVED");
    }

    public void fillArmyList(@NotNull Player user, Event event) throws JsonProcessingException {
        JsonNode node = mapper.readTree(user.getPlayerBody());
        JsonNode events = node.get("data");
        for (JsonNode currentEvent : events) {
            LocalDate date = extractDate(getDateString(currentEvent));
            JsonNode pairings = getParingJson(currentEvent);
            for (JsonNode pairing : pairings) {
                boolean isPairing1 = true;
                String username = getUsername(pairing, isPairing1);
                // current player is sometimes in "Paring1" and other timer in "Paring2"
                // This is for ensuring we always get current player
                if(!username.equals(user.getUsername())){
                    isPairing1 = false;
                }
                String army = getArmy(pairing, isPairing1);
                if(army == null || army.isBlank()){
                    continue;
                };
                user.addArmyToList(army);
                user.getArmyFromList(army).addDate(date);
            }
        }
    }

    public void fillArmiesForPlayers(Event event, HttpHandler httpHandler) throws IOException, InterruptedException {
        for (Player player: event.getPlayersMap().values()) {
            player.setPlayerBody(httpHandler.fetchPlayerParingsBody(httpHandler.createPlayerDetailsUrl(player),
                    event.getGameSystem()));
            fillArmyList(player,event);
        }
    }
    private LocalDate extractDate(String dateString){
        return OffsetDateTime.parse(dateString).toLocalDate();
    }
    // Getters
    private JsonNode getParingJson(JsonNode node){
        return node.get("pairings");
    }
    private String getDateString(JsonNode node){
        return node.get("endsAt").asText();
    }
    private String getUsername(JsonNode node, boolean isParing1){
        String paring;
        if(isParing1){
            paring = "pairingUser1";
        }else{
            paring = "pairingUser2";
        }
        return node.path(paring).path("user").path("displayName").asText();
    }
    private String getArmy(JsonNode node, boolean isParing1){
        String paring;
        if(isParing1){
            paring = "pairingUser1";
        }else{
            paring = "pairingUser2";
        }
        return node.path(paring).path("army").path("name").asText();
    }
}

