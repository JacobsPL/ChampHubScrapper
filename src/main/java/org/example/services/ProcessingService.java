package org.example.services;
import org.example.models.Event;
import org.example.models.Player;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ProcessingService {

    public Event createEvent(String eventUrl) throws IOException, InterruptedException {
        //Set up api event properties
        Event event = new Event(eventUrl);
        HttpHandler myHandler = new HttpHandler();
        event.
                setEventId(myHandler.
                        extractEventId(event.
                                getEventUrl()));
        event.extractGameSystem();
        event.
                setApiUrl(myHandler.
                        createUserSubmissionApiUrl(event
                                .getEventUrl()));
        event.
                setEventBodyPeople(myHandler.
                        FetchHttpBody(event.
                                getApiUrl()));
        event.
                setApiUrl(myHandler.
                        createEventManagementApiUrl(event.
                                getEventUrl()));
        event.
                setEventBodyEventManagement(myHandler.
                        FetchHttpBody(event
                                .getApiUrl()));


        // Setup Event properties - Event Name, Player List, Player Armies
        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.fillEventName(event);
        jsonHandler.fillPlayerList(event);
        event.fillArmiesForPlayers();
        event.fillPlayersDateArmyMap(); // kurwa działa xdd

//        for(Player player:event.getPlayers()){
//            player.calculateMostProbableArmy();
//        }
        event.setMostProbableArmyForEachPlayer();
        event.createSimplifyArmyStatistic();
        return event;
    }
}
