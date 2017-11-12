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
public class Place {
    @JsonProperty("PlaceId")
    private String placeId;
    @JsonProperty("PlaceName")
    private String placeName;
    @JsonProperty("CountryId")
    private String countryId;
    @JsonProperty("RegionId")
    private String regionId;
    @JsonProperty("CityId")
    private String cityId;
    @JsonProperty("CountryName")
    private String countryName;

    public Place () {}
}
