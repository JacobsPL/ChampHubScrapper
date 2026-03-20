package org.example.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.models.Event;
import org.example.models.Player;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDate;
public class JsonHandler {

    public void fillEventName(@NotNull Event event) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(event.getEventBodyEventManagement());
        event.setEventName(node.get("name").toString());
    }
    public void fillPlayerList(@NotNull Event event) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(event.getEventBodyPeople());
        JsonNode attendees = node.get("people");
            for(JsonNode attender : attendees) {
                JsonNode users = attender.get("user");
                if (isAprroved(attender)) {
                    event.addPlayerToList(new Player(users.get("id").asText(), users.get("displayName").asText()));
                }
            }
    }
    private boolean isAprroved(@NotNull JsonNode attender){
        JsonNode isApproved = attender.get("status");
        String status = isApproved.toString();
        status = status.substring(1,status.length()-1);
        return status.equals("APPROVED");
    }

    public void fillArmyList(@NotNull Player user, Event event) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(user.getPlayerBody());
        JsonNode events = node.get("data");
        for (JsonNode currentEvent : events) {
            LocalDate date = extractDate(getDateString(currentEvent));
            JsonNode pairings = getParingJson(currentEvent);
            for (JsonNode pairing : pairings) {
                String username = getUsername(pairing);
                String army = getArmy(pairing);
                for (Player player : event.getPlayers()) {
                    if (player.getUsername().equals(username)) {
                        player.addArmyToList(army);
                        player.getArmyFromList(army).addDate(date);
                    }
                }
            }
        }
    }

    private JsonNode getParingJson(JsonNode node){
        return node.get("pairings");
    }
    private String getDateString(JsonNode node){
        return node.get("endsAt").toString();
    }
    private String getUsername(JsonNode node){
        String username = node.path("pairingUser1").path("user").path("displayName").toString();
        if(!username.isEmpty()) username = username.substring(1,username.length()-1);
        return username;
    }
    private String getArmy(JsonNode node){
        String army = node.path("pairingUser1").path("army").path("name").toString();
        if(!army.isEmpty()) army = army.substring(1,army.length()-1);
        return army;
    }

    private LocalDate extractDate(String dateString){
        StringBuilder builder = new StringBuilder();
        int counter=1; //first char is " so is omitted
        while(dateString.charAt(counter)!='T'){
            builder.append(dateString.charAt(counter));
            counter++;
        }
        return LocalDate.parse(builder.toString());
    }
}

