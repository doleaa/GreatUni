package com.bucha.wrestlers.resources;


import com.bucha.wrestlers.dto.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
import java.util.Map;
import java.util.stream.Collectors;

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

        List<OutInPair> outInPairs = getInOutMap(originPlaces, destinationPlaces);
        ImmutableList.Builder<QuoteDates> quoteDatesBuilder = ImmutableList.builder();
        outInPairs.forEach(pair ->
                quoteDatesBuilder.add(getQuoteDates(pair.getOut(), pair.getIn(), outDate, inDate))
        );

        ImmutableList.Builder<Offer> allOffersBuilder = ImmutableList.builder();
        quoteDatesBuilder.build().forEach(quoteDate ->
            allOffersBuilder.addAll(extractOffersFromQuoteDates(quoteDate))
        );

        return Response.ok(allOffersBuilder.build()).build();
    }

    private List<Offer> extractOffersFromQuoteDates(QuoteDates quoteDates) {
        List<Quote> inQuotes = quoteDates.getDates().getInboundDates().stream()
                .flatMap(date -> {
                    List<Integer> quoteIds = date.getQuoteIds();
                    return quoteDates.getQuotes().stream()
                            .filter(quote ->
                                quoteIds.contains(quote.getQuoteId()) && quote.getOutboundLeg() == null
                            );
                }).collect(Collectors.toList());

        List<Quote> outQuotes = quoteDates.getDates().getOutboundDates().stream()
                .flatMap(date -> {
                    List<Integer> quoteIds = date.getQuoteIds();
                    return quoteDates.getQuotes().stream()
                            .filter(quote ->
                                    quoteIds.contains(quote.getQuoteId()) && quote.getInboundLeg() == null
                            );
                }).collect(Collectors.toList());

        List<Quote> outInQuotes = quoteDates.getQuotes().stream()
                .filter(quote -> quote.getInboundLeg() != null && quote.getOutboundLeg() != null)
                .collect(Collectors.toList());

        ImmutableList.Builder<Offer> offerBuilder = ImmutableList.builder();

        outInQuotes.forEach(outInQuote ->
                offerBuilder.add(
                        Offer.builder()
                            .outboundLeg(OfferLeg.builder()
                                    .airline(extractAirline(quoteDates,
                                            outInQuote.getOutboundLeg().getCarrierIds().size() > 0 ?
                                                    outInQuote.getOutboundLeg().getCarrierIds().get(0) : 0)
                                    )
                                    .date(outInQuote.getOutboundLeg().getDepartureDate())
                                    .from(extractPlace(quoteDates, outInQuote.getOutboundLeg().getOriginId()))
                                    .to(extractPlace(quoteDates, outInQuote.getOutboundLeg().getDestinationId()))
                                    .build()
                            )
                            .returnLeg(OfferLeg.builder()
                                    .airline(extractAirline(quoteDates,
                                            outInQuote.getInboundLeg().getCarrierIds().size() > 0 ?
                                                    outInQuote.getInboundLeg().getCarrierIds().get(0) : 0)
                                    )
                                    .date(outInQuote.getInboundLeg().getDepartureDate())
                                    .from(extractPlace(quoteDates, outInQuote.getInboundLeg().getOriginId()))
                                    .to(extractPlace(quoteDates, outInQuote.getInboundLeg().getDestinationId()))
                                    .build()
                            )
                            .totalPrice(outInQuote.getMinPrice())
                            .build()
                )
        );

        outQuotes.forEach(outQuote ->
            inQuotes.forEach(inQuote ->
                offerBuilder.add(
                        Offer.builder()
                                .outboundLeg(OfferLeg.builder()
                                        .airline(extractAirline(quoteDates,
                                                outQuote.getOutboundLeg().getCarrierIds().get(0))
                                        )
                                        .date(outQuote.getOutboundLeg().getDepartureDate())
                                        .from(extractPlace(quoteDates, outQuote.getOutboundLeg().getOriginId()))
                                        .to(extractPlace(quoteDates, outQuote.getOutboundLeg().getDestinationId()))
                                        .build()
                                )
                                .returnLeg(OfferLeg.builder()
                                        .airline(extractAirline(quoteDates,
                                                inQuote.getInboundLeg().getCarrierIds().get(0))
                                        )
                                        .date(inQuote.getInboundLeg().getDepartureDate())
                                        .from(extractPlace(quoteDates, inQuote.getInboundLeg().getOriginId()))
                                        .to(extractPlace(quoteDates, inQuote.getInboundLeg().getDestinationId()))
                                        .build()
                                )
                                .totalPrice(outQuote.getMinPrice() + inQuote.getMinPrice())
                                .build()
                )
            )
        );

        return offerBuilder.build();
    }

    private String extractPlace(QuoteDates quoteDates, String placeId) {

        return quoteDates.getPlaces().stream()
                .filter(place -> place.getPlaceId().equals(placeId))
                .collect(Collectors.toList())
                .get(0).getName();
    }

    private String extractAirline(QuoteDates quoteDates, Integer carrierId){
        if (carrierId.equals(0)) {
            return "Airline Name Not Available";
        }
        return quoteDates.getCarriers().stream()
                .filter(carrier -> carrier.getCarrierId().equals(carrierId))
                .collect(Collectors.toList())
                .get(0).getName();
    }

    private List<OutInPair> getInOutMap(Places outPlaces, Places inPlaces) {
        ImmutableList.Builder<OutInPair> allInOut = ImmutableList.builder();

        outPlaces.getPlaces().forEach(outPlace ->
            inPlaces.getPlaces().forEach(inPlace ->
                allInOut.add(OutInPair.builder()
                        .out(outPlace.getPlaceId())
                        .in(inPlace.getPlaceId())
                        .build())
            )
        );

        return allInOut.build();
    }

    @SneakyThrows
    private QuoteDates getQuoteDates(String origin, String destination, String outDate, String inDate) {
        String url = "http://partners.api.skyscanner.net/apiservices/browsedates/v1.0/UK/GBP/en-GB" +
                "/%s/%s/%s/%s?apiKey=%s";

        HttpGet getRequest = new HttpGet(String.format(url, origin, destination, outDate, inDate, API_KEY));
        getRequest.addHeader("accept", "application/json");

        HttpResponse response = client.execute(getRequest);

        try {
            return getObjectMapper().readValue(response.getEntity().getContent(), QuoteDates.class);
        } catch (Exception e) {
            return null;
        }
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
