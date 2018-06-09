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
import com.simplaapliko.challenge2.domain.model.Hotel;

public class HotelResponse {

    @SerializedName("marriott") public HotelEntity marriott;
    @SerializedName("novotel") public HotelEntity novotel;
    @SerializedName("hyatt") public HotelEntity hyatt;

    public class HotelEntity {
        @SerializedName("_id") public String id;
        @SerializedName("name") public String name;
        @SerializedName("rating") public Double rating;
        @SerializedName("image_url") public String imageUrl;

        public Hotel toHotel() {
            return new Hotel(id, name, rating, imageUrl);
        }
    }
}
