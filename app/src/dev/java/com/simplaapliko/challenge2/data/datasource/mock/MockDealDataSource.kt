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

package com.simplaapliko.challenge2.data.datasource.mock

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.simplaapliko.challenge2.R
import com.simplaapliko.challenge2.data.datasource.DealDataSource
import com.simplaapliko.challenge2.data.datasource.response.DealResponse
import com.simplaapliko.challenge2.di.Utils
import io.reactivex.Single

class MockDealDataSource(context: Context, gson: Gson) : DealDataSource {

    private var list: List<DealResponse>

    init {
        val jsonData = Utils.rawResourceAsString(context, R.raw.deals)

        val type = object : TypeToken<List<DealResponse>>() {}.type
        list = gson.fromJson<List<DealResponse>>(jsonData, type)
    }

    override fun getAll(): Single<List<DealResponse>> {
        return Single.just(list)
    }
}
