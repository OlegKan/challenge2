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
                .toObservable()
                .flatMapIterable(t -> t)
                .flatMap(dealResponse -> Maybe.zip(Maybe.just(dealResponse),
                        airlineRepository.get(dealResponse.flights.outbound.airline),
                        airlineRepository.get(dealResponse.flights.inbound.airline),
                        airportRepository.get(dealResponse.flights.outbound.start.airport),
                        airportRepository.get(dealResponse.flights.outbound.end.airport),
                        currencyRepository.get(dealResponse.currency),
                        hotelRepository.get(dealResponse.hotel.brand),
                        this::convert).toObservable())
                .toList();
    }

    private Deal convert(DealResponse dealResponse, Airline airline1, Airline airline2,
            Airport airport1, Airport airport2, Currency currency, Hotel hotel) {
        Lodging lodging = getLodging(dealResponse, hotel);
        Flights flights = getFlights(dealResponse, airline1, airline2, airport1, airport2);
        return new Deal(dealResponse.price, currency, flights, lodging);
    }

    private Lodging getLodging(DealResponse dealResponse, Hotel hotel) {
        return new Lodging(hotel, dealResponse.hotel.nights);
    }

    private Flights getFlights(DealResponse dealResponse, Airline airline1, Airline airline2,
            Airport airport1, Airport airport2) {

        Flight out = getFlight(dealResponse.flights.outbound, airline1, airport1, airport2);
        Flight in = getFlight(dealResponse.flights.inbound, airline2, airport2, airport1);

        return new Flights(out, in);
    }

    private Flight getFlight(DealResponse.Flight flight, Airline airline, Airport airport1,
            Airport airport2) {
        FlightData fd1 = new FlightData(flight.start.datetime, airport1);
        FlightData fd2 = new FlightData(flight.end.datetime, airport2);
        return new Flight(airline, fd1, fd2);
    }
}
