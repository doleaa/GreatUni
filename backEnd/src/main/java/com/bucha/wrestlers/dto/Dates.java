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
public class Dates {
    @JsonProperty("OutboundDates")
    private List<Date> outboundDates;
    @JsonProperty("InboundDates")
    private List<Date> inboundDates;

    public Dates() {}
}
