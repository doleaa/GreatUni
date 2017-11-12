package com.bucha.wrestlers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote {
    @JsonProperty("QuoteId")
    private Integer quoteId;
    @JsonProperty("MinPrice")
    private Integer minPrice;
    @JsonProperty("OutboundLeg")
    private Leg outboundLeg;
    @JsonProperty("InboundLeg")
    private Leg inboundLeg;

    public Quote() {}
}
