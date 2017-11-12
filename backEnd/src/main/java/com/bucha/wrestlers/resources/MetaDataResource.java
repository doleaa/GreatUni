package com.bucha.wrestlers.resources;


import com.bucha.wrestlers.dto.Country;
import com.bucha.wrestlers.dto.Place;
import com.bucha.wrestlers.dto.Places;
import com.bucha.wrestlers.dto.QuoteDates;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.File;
import java.util.List;

@Path("/metadata")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class MetaDataResource extends BaseBackEndResource {
    private static final String API_KEY = "ha687286341157939535667372815141";
    private static final TypeReference COUNTRY_LIST_TYPE = new TypeReference<List<Country>>(){};
    private final DefaultHttpClient client;

    @GET
    @Path("/countries")
    @SneakyThrows
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCountries() {
        List<Country> countries = getObjectMapper()
                .readValue(new File("backEnd/src/main/resources/countries.json"), COUNTRY_LIST_TYPE);
        return Response.ok(countries).build();
    }

    @GET
    @Path("/places")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getPlacesInCountry(@QueryParam("country") String country) {
        Places resp = getSuggestedPlaces(country);

        return Response.ok(resp).build();
    }

    @GET
    @Path("/single/offers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @SneakyThrows
    public Response getSingleOffers(
            @QueryParam("origin") String origin,
            @QueryParam("destination") String destination,
            @QueryParam("outDate") String outDate,
            @QueryParam("inDate") String inDate) {

        Places originPlaces = getSuggestedPlaces(origin);
        Places destinationPlaces = getSuggestedPlaces(destination);

        QuoteDates resp = getAllOffers(
                originPlaces.getPlaces().get(0).getPlaceId(),
                destinationPlaces.getPlaces().get(0).getPlaceId(),
                outDate,
                inDate
        );

        return Response.ok(resp).build();
    }

    @SneakyThrows
    private QuoteDates getAllOffers(String origin, String destination, String outDate, String inDate) {
        String url = "http://partners.api.skyscanner.net/apiservices/browsedates/v1.0/UK/GBP/en-GB" +
                "/%s/%s/%s/%s?apiKey=%s";

        HttpGet getRequest = new HttpGet(String.format(url, origin, destination, outDate, inDate, API_KEY));
        getRequest.addHeader("accept", "application/json");

        HttpResponse response = client.execute(getRequest);

        return getObjectMapper().readValue(response.getEntity().getContent(), QuoteDates.class);
    }

    @SneakyThrows
    private Places getSuggestedPlaces(String placeQuery) {
        String url = "http://partners.api.skyscanner.net/apiservices/autosuggest/v1.0/UK/GBP/en-GB" +
                "?apikey=%s&query=%s";

        HttpGet getRequest = new HttpGet(String.format(url, API_KEY, placeQuery));
        getRequest.addHeader("accept", "application/json");

        HttpResponse response = client.execute(getRequest);

        return getObjectMapper().readValue(response.getEntity().getContent(), Places.class);
    }

    private ObjectMapper getObjectMapper(){
        return new ObjectMapper();
    }
}
