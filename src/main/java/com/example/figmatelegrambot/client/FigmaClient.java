package com.example.figmatelegrambot.client;

import com.example.figmatelegrambot.model.figma.to.FigmaFilesTo;
import com.example.figmatelegrambot.model.figma.to.FigmaTeamTo;
import com.example.figmatelegrambot.util.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
@Data
@Component
public class FigmaClient {

    public static final String TOKEN_NAME = "X-FIGMA-TOKEN";
    public static final String TOKEN_VALUE = "figd_fprc-j0XsHV99o4rAJcNWxpbf2ngAGA5a9WlPCoV";

    public <T> T response(final Class<T> aClass, final URI uri) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .setHeader(TOKEN_NAME, TOKEN_VALUE)
                .build();

        log.info("FigmaAPI request :: URl: {}, Request: {}", uri, request);

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(httpResponse -> {
                            System.out.println(httpResponse);
                            return httpResponse.body();
                        }
                )
                .thenApply(body -> {
                    try {
                        log.info("Figma-API response :: URI: {}, body: {}", uri, body);
                        return JsonUtils.fromJson(body, aClass);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                })
                .join();
    }

    public FigmaTeamTo getTeamProjects(String teamId) {
        return response(FigmaTeamTo.class, URI.create("https://api.figma.com/v1/teams/" + teamId + "/projects"));
    }

    public FigmaFilesTo getProjectFiles(String projectId) {
        return response(FigmaFilesTo.class, URI.create("https://api.figma.com/v1/projects/" + projectId + "/files"));
    }

}
