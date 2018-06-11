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

package com.simplaapliko.challenge2.ui.deals

import com.simplaapliko.challenge2.domain.model.Currency
import com.simplaapliko.challenge2.domain.model.Deal
import io.reactivex.Observable

interface DealsContract {

    interface Navigator {
        fun goToDealScreen(model: Deal)
    }

    interface Presenter {
        fun init()

        fun destroy()
    }

    interface View {
        fun hideProgress()

        fun showProgress()

        fun setEmptyMessageVisibility(visible: Boolean?)

        fun displayDeals(items: List<Deal>)

        fun displayCurrencies(items: List<Currency>)

        fun setSelectedCurrency(): Currency

        fun showMessage(message: String)

        fun onDealClick(): Observable<Deal>

        fun onCurrencyChange(): Observable<Currency>
    }
}
