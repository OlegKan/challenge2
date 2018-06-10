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
import com.simplaapliko.challenge2.data.datasource.response.AirportResponse;

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

public class GetAirportsSingle implements SingleOnSubscribe<List<AirportResponse.AirportEntity>> {

    private static final String URL = "https://raw.githubusercontent.com/OlegKan/challenge2/master/assets/json/airports.json";

    private final OkHttpClient okHttpClient;
    private final Gson gson;

    public static Single<List<AirportResponse.AirportEntity>> create(OkHttpClient okHttpClient, Gson gson) {
        return Single.create(new GetAirportsSingle(okHttpClient, gson));
    }

    private GetAirportsSingle(OkHttpClient okHttpClient, Gson gson) {
        this.okHttpClient = okHttpClient;
        this.gson = gson;
    }

    @Override
    public void subscribe(SingleEmitter<List<AirportResponse.AirportEntity>> emitter) {

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
                            AirportResponse item = gson.fromJson(jsonData, AirportResponse.class);

                            List<AirportResponse.AirportEntity> list = new ArrayList<>();

                            list.add(item.atl);
                            list.add(item.bkk);
                            list.add(item.cnx);
                            list.add(item.hkt);
                            list.add(item.iad);
                            list.add(item.jfk);
                            list.add(item.lhr);
                            list.add(item.nrt);
                            list.add(item.san);
                            list.add(item.sea);
                            list.add(item.txl);
                            list.add(item.ukb);

                            if (!emitter.isDisposed()) {
                                emitter.onSuccess(list);
                            }
                        }
                    }
                });
    }
}
