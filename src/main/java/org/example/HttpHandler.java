package org.example;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class HttpBuilder {

    HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).build();

    HttpRequest request = HttpRequest.newBuilder().uri(URI.create("www.google.com")).header("User-Agent", "Mozilla/5.0").GET().build();

    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

    String body = response.body();


    public HttpBuilder() throws IOException, InterruptedException {
    }

}
