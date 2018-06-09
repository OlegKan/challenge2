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

package com.simplaapliko.challenge2.di

import android.content.Context
import android.support.annotation.RawRes
import java.io.BufferedReader

class Utils {

    companion object {
        fun rawResourceAsString(context: Context, @RawRes resId: Int): String {
            val inputStream = context.resources.openRawResource(resId)
            return inputStream.bufferedReader().use(BufferedReader::readText)
        }
    }
}
