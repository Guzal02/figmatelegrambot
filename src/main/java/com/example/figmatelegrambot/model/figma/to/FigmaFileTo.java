package com.example.figmatelegrambot.model.figma.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FigmaFileTo {
    String key;
    String name;
    @JsonProperty("thumbnail_url")
    String thumbnailUrl;
    @JsonProperty("last_modified")
    String lastModified;
}
