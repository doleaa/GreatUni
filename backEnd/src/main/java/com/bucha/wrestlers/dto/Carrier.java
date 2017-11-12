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
public class Carrier {
    @JsonProperty("CarrierId")
    private Integer carrierId;
    @JsonProperty("Name")
    private String name;

    public Carrier() {}
}
