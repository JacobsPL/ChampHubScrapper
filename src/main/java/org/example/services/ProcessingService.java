package org.example.services;
import org.example.models.Event;
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
        event.
                setApiUrl(myHandler.
                        createUserSubmissionApiUrl(event
                                .getEventUrl()));
        event.
                setEventBody(myHandler.
                        FetchHttpBody(event.
                                getApiUrl()));

        JsonHandler jsonHandler = new JsonHandler();
        jsonHandler.fillPlayerList(event);
        event.fillArmiesForPlayers();
        return event;
    }
}
