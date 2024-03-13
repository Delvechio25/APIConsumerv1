package org.example;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class SongConsumer {

    public static void main(String[] args) {
        String url = "https://guilhermeonrails.github.io/api-csharp-songs/songs.json";
        String titleToFind = "Wish You Were Here"; // Substitua isso pelo título que você deseja encontrar
        Song song = fetchSongByTitle(url, titleToFind);

        if (song != null) {
            System.out.println("Música encontrada: " + song);
        } else {
            System.out.println("Música não encontrada.");
        }
    }

    public static Song fetchSongByTitle(String url, String titleToFind) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONArray jsonArray = new JSONArray(response.body());

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.has("title") ? jsonObject.getString("title") : "Unknown";
                if (title.equals(titleToFind)) {
                    return new Song(
                            title,
                            jsonObject.has("artist") ? jsonObject.getString("artist") : "Unknown",
                            jsonObject.has("album") ? jsonObject.getString("album") : "Unknown",
                            jsonObject.has("year") ? jsonObject.getString("year") : "Unknown"
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
