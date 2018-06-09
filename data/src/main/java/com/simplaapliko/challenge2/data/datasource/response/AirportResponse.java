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

package com.simplaapliko.challenge2.data.datasource.response;

import com.google.gson.annotations.SerializedName;
import com.simplaapliko.challenge2.domain.model.Airport;

public class AirportResponse {

    @SerializedName("SAN") public AirportEntity san;
    @SerializedName("IAD") public AirportEntity iad;
    @SerializedName("SEA") public AirportEntity sea;
    @SerializedName("JFK") public AirportEntity jfk;
    @SerializedName("ATL") public AirportEntity atl;
    @SerializedName("BKK") public AirportEntity bkk;
    @SerializedName("CNX") public AirportEntity cnx;
    @SerializedName("HKT") public AirportEntity hkt;
    @SerializedName("NRT") public AirportEntity nrt;
    @SerializedName("UKB") public AirportEntity ukb;
    @SerializedName("TXL") public AirportEntity txl;
    @SerializedName("LHR") public AirportEntity lhr;

    public static class AirportEntity {

        @SerializedName("_id") public String id;
        @SerializedName("name") public String name;
        @SerializedName("city") public String city;

        public Airport toAirport() {
            return new Airport(id, name, city);
        }
    }
}
