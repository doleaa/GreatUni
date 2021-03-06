package com.bucha.wrestlers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Leg {
    @JsonProperty("CarrierIds")
    private List<Integer> carrierIds;
    @JsonProperty("OriginId")
    private String originId;
    @JsonProperty("DestinationId")
    private String destinationId;
    @JsonProperty("DepartureDate")
    private String departureDate;

    public Leg(){}
}
