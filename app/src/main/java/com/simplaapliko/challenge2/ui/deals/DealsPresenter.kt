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

import com.simplaapliko.challenge2.domain.model.Deal
import com.simplaapliko.challenge2.domain.repository.DealRepository
import com.simplaapliko.challenge2.rx.RxSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class DealsPresenter internal constructor(private val rxSchedulers: RxSchedulers,
    private val repository: DealRepository, private val view: DealsContract.View,
    private val navigator: DealsContract.Navigator) : DealsContract.Presenter {

    private val disposables = CompositeDisposable()
    private var getAllDealsDisposable: Disposable? = null

    override fun init() {
        bindView()

        refreshData()
    }

    private fun refreshData() {
        view.showProgress()
        if (getAllDealsDisposable != null && !getAllDealsDisposable!!.isDisposed) {
            getAllDealsDisposable!!.dispose()
            disposables.delete(getAllDealsDisposable!!)
        }
        getAllDealsDisposable = repository.getAll()
            .compose(rxSchedulers.getComputationToMainTransformerSingle())
            .subscribe({ t -> this.handleGetAllDealsSuccess(t) },
                { t -> this.handleGetAllDealsError(t) })
        disposables.add(getAllDealsDisposable!!)
    }

    private fun handleGetAllDealsSuccess(data: List<Deal>) {
        view.hideProgress()
        view.displayDeals(data)
    }

    private fun handleGetAllDealsError(throwable: Throwable) {
        view.hideProgress()
        view.showMessage(throwable.localizedMessage)
    }

    private fun bindView() {
        val showProfile = view.onDealClick()
            .observeOn(rxSchedulers.getMainThreadScheduler())
            .throttleFirst(500, TimeUnit.MILLISECONDS)
            .subscribe({ t -> handleDealClickAction(t) }, { t -> handleUnknownError(t) })
        disposables.add(showProfile)
    }

    private fun handleDealClickAction(deal: Deal) {
        navigator.goToDealScreen(deal)
    }

    private fun handleUnknownError(throwable: Throwable) {
        // for demo purposes only
        view.showMessage(throwable.localizedMessage)
    }

    override fun destroy() {
        disposables.dispose()
    }
}
