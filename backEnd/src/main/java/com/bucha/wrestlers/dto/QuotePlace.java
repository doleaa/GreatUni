package com.bucha.wrestlers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotePlace {
    @JsonProperty("PlaceId")
    private String placeId;
    @JsonProperty("Name")
    private String name;
    @JsonProperty("CityName")
    private String cityName;

    public QuotePlace() {}
}
