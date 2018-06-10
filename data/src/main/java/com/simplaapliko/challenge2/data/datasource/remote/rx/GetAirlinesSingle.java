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

package com.simplaapliko.challenge2.data.datasource.remote.rx;

import com.google.gson.Gson;
import com.simplaapliko.challenge2.data.datasource.response.AirlineResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetAirlinesSingle implements SingleOnSubscribe<List<AirlineResponse.AirlineEntity>> {

    private static final String URL = "https://raw.githubusercontent.com/OlegKan/challenge2/master/assets/json/airlines.json";

    private final OkHttpClient okHttpClient;
    private final Gson gson;

    public static Single<List<AirlineResponse.AirlineEntity>> create(OkHttpClient okHttpClient,
            Gson gson) {
        return Single.create(new GetAirlinesSingle(okHttpClient, gson));
    }

    private GetAirlinesSingle(OkHttpClient okHttpClient, Gson gson) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
    }

    @Override
    public void subscribe(SingleEmitter<List<AirlineResponse.AirlineEntity>> emitter) {

        Request request = new Request.Builder().url(URL).build();

        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (!emitter.isDisposed()) {
                            emitter.onError(e);
                        }
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {

                        if (!response.isSuccessful()) {
                            if (!emitter.isDisposed()) {
                                emitter.onError(new IOException("Unexpected code " + response));
                            }
                        } else {
                            String jsonData = response.body().string();
                            AirlineResponse item = gson.fromJson(jsonData, AirlineResponse.class);

                            List<AirlineResponse.AirlineEntity> list = new ArrayList<>();

                            list.add(item.aa);
                            list.add(item.as);
                            list.add(item.b6);
                            list.add(item.dl);
                            list.add(item.ua);
                            list.add(item.vx);

                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(list);
                            }
                        }
                    }
                });
    }
}
