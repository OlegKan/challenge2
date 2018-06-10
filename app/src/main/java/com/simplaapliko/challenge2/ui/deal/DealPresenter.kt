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

package com.simplaapliko.challenge2.ui.deal

import com.simplaapliko.challenge2.domain.model.Deal
import com.simplaapliko.challenge2.domain.model.DealFull
import com.simplaapliko.challenge2.domain.repository.DealRepository
import com.simplaapliko.challenge2.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable

class DealPresenter(private val rxSchedulers: RxSchedulers,
    private val repository: DealRepository, private val view: DealContract.View,
    private val deal: Deal) :
    DealContract.Presenter {

    private val disposables = CompositeDisposable()

    override fun init() {
        view.showProgress()

        val disposable = repository.getDeal(deal)
            .compose(rxSchedulers.getComputationToMainTransformerMaybe())
            .subscribe({ t -> this.handleGetDealSuccess(t) },
                { t -> this.handleGetDealError(t) })
        disposables.add(disposable)
    }

    private fun handleGetDealSuccess(data: DealFull) {
        view.hideProgress()
        view.displayDeal(data)
    }

    private fun handleGetDealError(throwable: Throwable) {
        view.hideProgress()
        view.showMessage(throwable.localizedMessage)
    }

    override fun destroy() {
        disposables.dispose()
    }
}
