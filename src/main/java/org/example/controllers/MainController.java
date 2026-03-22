package org.example.controllers;

import jakarta.servlet.http.HttpSession;
import org.example.models.Event;
import org.example.services.ProcessingService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.*;

@Controller
public class MainController {
    private final ProcessingService processingService;

    public MainController(ProcessingService processingService) {
        this.processingService = processingService;
    }

    @GetMapping("/")
    public String getIndexPage(Model model, HttpSession session){
        Map<String,String> sessionHistory = (Map<String,String>)session.getAttribute("history");
        model.addAttribute("history",sessionHistory);
        return "index";
    }

    @PostMapping("/result")
    public String process(@RequestParam("eventUrl") String eventUrl, Model model, HttpSession session) throws Exception{
        Event event = processingService.createEvent(eventUrl);

        // Tworzenie historii wyszukiwanych eventów
        // Jesli lista historii jest pusta(nie istnieje) - zainicjuj liste
        Map<String, String> sessionHistoryMap = (Map<String,String>)session.getAttribute("history");
        //List<String> sessionHistory = (List<String>)session.getAttribute("history");
        if(sessionHistoryMap == null){
            sessionHistoryMap = new HashMap<>();
        }

        model.addAttribute("event", event);
        sessionHistoryMap = addUniqueUrlToSessionHistory(event.getEventUrl(),event.getEventName(),sessionHistoryMap);
        session.setAttribute("history",sessionHistoryMap);
        return "result";
    }

    private Map<String,String> addUniqueUrlToSessionHistory(String url, String eventName, Map<String,String> historyMap){
        boolean flag = true;
        for (Map.Entry<String,String> current : historyMap.entrySet()){
            if (current.getKey().equals(url)){
                flag = false;
            }
        }
        if(flag){
            historyMap.put(url,eventName);
        }
        return  historyMap;
    }

}
