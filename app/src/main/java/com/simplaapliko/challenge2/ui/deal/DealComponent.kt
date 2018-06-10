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

import com.simplaapliko.challenge2.di.ActivityScope
import com.simplaapliko.challenge2.domain.model.Deal
import com.simplaapliko.challenge2.domain.repository.DealRepository
import com.simplaapliko.challenge2.rx.RxSchedulers
import dagger.Provides
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [(DealComponent.Module::class)])
interface DealComponent {

    fun inject(activity: DealActivity)

    @dagger.Module
    class Module(private val activity: DealActivity, private val deal: Deal) {

        @Provides
        @ActivityScope
        internal fun provideOverviewView(): DealContract.View {
            return activity
        }

        @Provides
        @ActivityScope
        internal fun provideOverviewPresenter(rxSchedulers: RxSchedulers,
            repository: DealRepository, view: DealContract.View): DealContract.Presenter {
            return DealPresenter(rxSchedulers, repository, view, deal)
        }
    }
}
