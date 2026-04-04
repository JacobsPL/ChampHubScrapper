package org.example.services;
import org.example.models.Event;
import org.example.models.Player;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class ProcessingService {

    private final JsonHandler jsonHandler;
    private final HttpHandler httpHandler;
    public ProcessingService(JsonHandler jsonHandler, HttpHandler httpHandler){
        this.jsonHandler=jsonHandler;
        this.httpHandler=httpHandler;
    }
    public Event createEvent(String eventUrl) throws IOException, InterruptedException {
        //Set up api event properties
        Event event = new Event(eventUrl);
        event.
                setEventId(httpHandler.
                        extractEventId(event.
                                getEventUrl()));
        event.extractGameSystem();
        event.
                setApiUrl(httpHandler.
                        createUserSubmissionApiUrl(event
                                .getEventUrl()));
        event.
                setEventBodyPeople(httpHandler.
                        FetchHttpBody(event.
                                getApiUrl()));
        event.
                setApiUrl(httpHandler.
                        createEventManagementApiUrl(event.
                                getEventUrl()));
        event.
                setEventBodyEventManagement(httpHandler.
                        FetchHttpBody(event
                                .getApiUrl()));


        // Setup Event properties - Event Name, Player List, Player Armies
        jsonHandler.fillEventName(event);
        jsonHandler.fillPlayerList(event);
        jsonHandler.fillArmiesForPlayers(event,httpHandler);
        event.fillPlayersDateArmyMap();
        event.setMostProbableArmyForEachPlayer();
        event.createSimplifyArmyStatistic();
        return event;
    }
}
