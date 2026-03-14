package org.example.controllers;

import org.example.models.Event;
import org.example.services.ProcessingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

@Controller
public class MainController {
    private final ProcessingService processingService;

    public MainController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @GetMapping("/")
    public String getIndexPage(){
        return "index";
    }

    @PostMapping("/process")
    public String process(@RequestParam("eventUrl") String eventUrl, Model model) throws Exception{
        //System.out.println("Weszło do kontrolera, eventUrl = " + eventUrl);
        Event event = processingService.createEvent(eventUrl);
        model.addAttribute("event", event);
        return "result";
    }
}
