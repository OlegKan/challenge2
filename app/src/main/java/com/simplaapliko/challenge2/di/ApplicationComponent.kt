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

import com.simplaapliko.challenge2.App
import com.simplaapliko.challenge2.ui.deal.DealComponent
import com.simplaapliko.challenge2.ui.deals.DealsComponent
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        (ApplicationModule::class),
        (DataModule::class),
        (NetworkModule::class),
        (UtilsModule::class)
    ]
)
interface ApplicationComponent {
    fun inject(app: App)

    fun plus(module: DealComponent.Module): DealComponent

    fun plus(module: DealsComponent.Module): DealsComponent
}
