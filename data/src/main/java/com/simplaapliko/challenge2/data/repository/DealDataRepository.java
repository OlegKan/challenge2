/*
 * Copyright (C) 2018 Oleg Kan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplaapliko.challenge2.data.repository;

import com.simplaapliko.challenge2.data.datasource.DealDataSource;
import com.simplaapliko.challenge2.data.datasource.response.DealResponse;
import com.simplaapliko.challenge2.domain.model.Airline;
import com.simplaapliko.challenge2.domain.model.Airport;
import com.simplaapliko.challenge2.domain.model.Currency;
import com.simplaapliko.challenge2.domain.model.Deal;
import com.simplaapliko.challenge2.domain.model.DealFull;
import com.simplaapliko.challenge2.domain.model.Flight;
import com.simplaapliko.challenge2.domain.model.FlightData;
import com.simplaapliko.challenge2.domain.model.Flights;
import com.simplaapliko.challenge2.domain.model.Hotel;
import com.simplaapliko.challenge2.domain.model.Lodging;
import com.simplaapliko.challenge2.domain.repository.AirlineRepository;
import com.simplaapliko.challenge2.domain.repository.AirportRepository;
import com.simplaapliko.challenge2.domain.repository.CurrencyRepository;
import com.simplaapliko.challenge2.domain.repository.DealRepository;
import com.simplaapliko.challenge2.domain.repository.HotelRepository;

import org.jetbrains.annotations.NotNull;

import java.util.Date;
import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.Single;

public class DealDataRepository implements DealRepository {

    private final AirlineRepository airlineRepository;
    private final AirportRepository airportRepository;
    private final CurrencyRepository currencyRepository;
    private final DealDataSource dealDataSource;
    private final HotelRepository hotelRepository;

    public DealDataRepository(AirlineRepository airlineRepository,
            AirportRepository airportRepository, CurrencyRepository currencyRepository,
            DealDataSource dealDataSource, HotelRepository hotelRepository) {
        this.airlineRepository = airlineRepository;
        this.airportRepository = airportRepository;
        this.currencyRepository = currencyRepository;
        this.dealDataSource = dealDataSource;
        this.hotelRepository = hotelRepository;
    }

    @NotNull
    @Override
    public Single<List<Deal>> getAll() {
        return dealDataSource.getAll()
                .flattenAsObservable(t -> t)
                .flatMapMaybe(this::getAdditionalData)
                .toList();
    }

    @NotNull
    @Override
    public Maybe<DealFull> getDeal(@NotNull Deal deal) {
        return Maybe.zip(Maybe.just(deal),
                airlineRepository.get(deal.getOutboundAirlineId()),
                airlineRepository.get(deal.getInboundAirlineId()),
                airportRepository.get(deal.getOutboundStartAirportId()),
                airportRepository.get(deal.getOutboundEndAirportId()),
                this::convert);
    }

    private Maybe<Deal> getAdditionalData(DealResponse dealResponse) {
        return Maybe.zip(Maybe.just(dealResponse),
                currencyRepository.get(dealResponse.currency),
                hotelRepository.get(dealResponse.hotel.brand),
                this::convert);
    }

    private DealFull convert(Deal deal, Airline airline1, Airline airline2, Airport airport1,
            Airport airport2) {
        Flights flights = getFlights(deal, airline1, airline2, airport1, airport2);

        return new DealFull(deal.getPrice(), deal.getCurrency(), flights, deal.getLodging());
    }

    private Deal convert(DealResponse dealResponse, Currency currency, Hotel hotel) {
        Lodging lodging = new Lodging(hotel, dealResponse.hotel.nights);

        DealResponse.Flight outbound = dealResponse.flights.outbound;
        DealResponse.Flight inbound = dealResponse.flights.inbound;

        String outboundAirlineId = outbound.airline;
        String outboundStartAirportId = outbound.start.airport;
        Date outboundStartDate = outbound.start.datetime;
        String outboundEndAirportId = outbound.end.airport;
        Date outboundEndDate = outbound.end.datetime;
        String inboundAirlineId = inbound.airline;
        String inboundStartAirportId = inbound.start.airport;
        Date inboundStartDate = inbound.start.datetime;
        String inboundEndAirportId = inbound.end.airport;
        Date inboundEndDate = inbound.end.datetime;

        return new Deal(dealResponse.price, currency, outboundAirlineId, outboundStartAirportId,
                outboundStartDate, outboundEndAirportId, outboundEndDate, inboundAirlineId,
                inboundStartAirportId, inboundStartDate, inboundEndAirportId, inboundEndDate,
                lodging);
    }

    private Flights getFlights(Deal deal, Airline airline1, Airline airline2,
            Airport airport1, Airport airport2) {

        Flight out = getFlight(deal.getOutboundStartDate(), deal.getOutboundEndDate(), airline1,
                airport1, airport2);
        Flight in = getFlight(deal.getInboundStartDate(), deal.getInboundEndDate(), airline2,
                airport2, airport1);

        return new Flights(out, in);
    }

    private Flight getFlight(Date start, Date end, Airline airline, Airport airport1,
            Airport airport2) {
        FlightData fd1 = new FlightData(start, airport1);
        FlightData fd2 = new FlightData(end, airport2);
        return new Flight(airline, fd1, fd2);
    }
}
