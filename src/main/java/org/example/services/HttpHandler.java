package org.example.services;

import org.example.models.Player;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpHandler {

    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

    public String FetchHttpBody(String url) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .header("User-Agent", "Mozilla/5.0").GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    String FetchUserDetailsBody(String url) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .header("accept","application/json, text/plain, */*")
                .header("accept-language","pl,en-US;q=0.9,en;q=0.8,de;q=0.7")
                .header("game-system","wh_ow")
                .header("locale","pl-PL")
                .header("origin","https://championshub.app")
                .header("priority","u=1, i")
                .header("referer","https://championshub.app/")
                .header("User-Agent", "Mozilla/5.0").GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String fetchPlayerParingsBody(String url) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest
                .newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json, text/plain, */*")
                .header("accept-language", "pl,en-US;q=0.9,en;q=0.8,de;q=0.7")
                .header("game-system", "wh_ow")
                .header("locale", "pl-PL")
                .header("origin", "https://championshub.app")
                .header("priority", "u=1, i")
                .header("referer", "https://championshub.app/")
                .header("user-agent", "Mozilla/5.0")
                .GET().build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String extractEventId(String url){
        StringBuilder eventId = new StringBuilder();
        int counter=0;

        for(int i = 0; i<url.length();i++){
            if (url.charAt(i)=='/'){
                counter++;
                if(counter==5){
                    i++;
                    while (i<url.length() && url.charAt(i)!='\0'){
                        eventId.append(url.charAt(i));
                        i++;
                    }
                    return eventId.toString();
                }
            }
        }
        //https://championshub.app/wh_ow/events/{eventId}/people
        return eventId.toString();
    }

    public String createUserSubmissionApiUrl(String eventUrl){
        return "https://api.championshub.app/api/submission/" + extractEventId(eventUrl);
    }

    public String createEventManagementApiUrl(String eventUrl){
        return "https://api.championshub.app/api/event-management/" + extractEventId(eventUrl);
    }

   public String createPlayerDetailsUrl(Player player){
        //https://api.championshub.app/api/ranking-elo/user-history/{playerId}?season=CURRENT
        return  "https://api.championshub.app/api/ranking-elo/user-history/"+player.getPlayerId()+"?season=CURRENT";
   };
}
