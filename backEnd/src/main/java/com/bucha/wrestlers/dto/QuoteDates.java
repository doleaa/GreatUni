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
public class QuoteDates {
    @JsonProperty("Dates")
    private Dates dates;
    @JsonProperty("Quotes")
    private List<Quote> quotes;
    @JsonProperty("Places")
    private List<QuotePlace> places;
    @JsonProperty("Carriers")
    private List<Carrier> carriers;

    public QuoteDates() {}
}
